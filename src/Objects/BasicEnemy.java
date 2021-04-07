package Objects;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import core.*;

//https://www.youtube.com/watch?v=JBGCCAv76YI
public class BasicEnemy extends GameObject{
    private double basicSpeed;
    private GameObject player;
    private double health = 10.0;
    Image basicImage = new Image("resources/Enemy.png");

    public BasicEnemy(int posX, int posY, ObjectHandler handler) {
        super(posX, posY, handler);
        this.setId(ID.BasicEnemy);
        this.setImage(basicImage);
        width = (int) basicImage.getWidth();
        height = (int) basicImage.getHeight();
        this.player = handler.findPlayer();
        this.basicSpeed = 4;
    }

    @Override
    public void tick() {

        move();
        position = new Point2D(position.getX() + velX, position.getY() + velY);//unit vector directing to the player

    }

    public void checkCollision() {

    }

    @Override
    public void collisionCode(ID id, GameObject gameObj) {
        if(id == ID.Bullet){
            System.out.println("ouch");
            handler.removeObject(gameObj);
            handler.removeObject( this);

        }
        if(id == ID.Barrier){
            System.out.println("Barrier");//if the enemy moves into a barrier then change direction
        }
        if(id == ID.SingleFireEnemy){
            changeDirection();
        }
        if(id == ID.BasicEnemy){
            changeDirection();
        }
        if(id == ID.Player){
            changeDirection();
            System.out.println("collide");
        }


    }

    public void move(){

        double mdX = player.getPosition().getX() - this.getPosition().getX();
        double mdY = player.getPosition().getY() - this.getPosition().getY();
        double mHyp = Math.sqrt(mdX * mdX + mdY * mdY);
        //Use mnx and mny to modify velocity direction.
        double mux = mdX / mHyp;
        double muy = mdY / mHyp;

        velX = mux*basicSpeed;
        velY = muy*basicSpeed;

        // if enemy collides with the barrier then adjust the movement


    }
    public void changeDirection(){

    }

}
