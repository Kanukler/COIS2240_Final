package Objects;

import core.GameObject;
import core.ID;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class EnemyBullet extends GameObject {
    private GameObject firer;
    Image bulletImage = new Image("resources/EnemyBullet.png");
    public EnemyBullet(core.ObjectHandler handler, double velX, double velY, GameObject firer){
        this.firer = firer;//to get the positions of the enemy that fired the bullet

        this.handler = handler;

        this.position = new Point2D(this.firer.getPosition().getX() + (double)(this.firer.getWidth() - this.width)/2,
                this.firer.getPosition().getY() + (double) (this.firer.getHeight() - this.height)/2 );
        this.setId(ID.EnemyBullet);

        this.setImage(bulletImage);
        width = (int) bulletImage.getWidth();
        height = (int) bulletImage.getHeight();

        this.velX = velX;
        this.velY = velY;

    }

    @Override
    public void tick() {
        position = new Point2D(position.getX() + velX, position.getY() + velY);
    }

    @Override
    public void collisionCode(ID id, GameObject gameObj) {

    }

}
