package Objects;

import core.GameObject;
import core.ID;
import core.ObjectHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public class Obstacle extends GameObject {

    private Image img = new Image("resources/img.png");

    public Obstacle(int posX, int posY, ObjectHandler handler) {
        super(posX, posY, handler);
        this.setImage(img);
        width = (int) img.getWidth();
        height = (int) img.getHeight();
    }

    @Override
    public void tick() {

    }

    @Override
    public void collisionCode(ID id, GameObject object) {
        if(id == ID.Player || id == ID.BasicEnemy || id == ID.SingleFireEnemy || id == ID.BossEnemy){
            System.out.println("Collide");
            barrier(object);
        }

        if(id == ID.Bullet || id == ID.EnemyBullet){
            handler.removeObject(object);
        }

    }



    /**Experimental pixel perfect barrier, not as smooth as standard code*/
    private void expBarrier(GameObject object){
        if (!this.getBounds().intersects(object.bStepX())) {

            //object.setPosition(new Point2D(object.getPosition().getX() - object.getVelX(), object.getPosition().getY()));

            while(this.getBounds().intersects(object.getBounds())){
                object.setPosition(new Point2D(object.getPosition().getX() - object.getVelX()/Math.abs(object.getVelX()),
                        object.getPosition().getY()
                ));
            }

        }

        else if (!this.getBounds().intersects(object.bStepY())) {

            //object.setPosition(new Point2D(object.getPosition().getX(), object.getPosition().getY() - object.getVelY()));

            System.out.println("\nThese:");

            while(this.getBounds().intersects(object.getBounds())){
                object.setPosition(new Point2D(object.getPosition().getX(),
                        object.getPosition().getY() - object.getVelY()/Math.abs(object.getVelY())
                ));

                System.out.println(object.getPosition());

            }
        }

    }

}
