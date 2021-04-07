package Objects;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import core.*;

//https://www.youtube.com/watch?v=JBGCCAv76YI
public class BasicEnemy extends GameObject{
    private double basicSpeed;
    private GameObject player;
    private double health = 10.0;
    Image basicImage = new Image("resources/Enemy.png");//just for an example not the actual image of the basic enemy

    private  boolean enemyCollide = false;
    private int endChangeDirection = 0;

    public BasicEnemy(int posX, int posY, ObjectHandler handler) {
        super(posX, posY, handler);
        this.setId(ID.BasicEnemy);
        this.setImage(basicImage);
        width = (int) basicImage.getWidth();
        height = (int) basicImage.getHeight();
        this.player = handler.findPlayer();
        this.basicSpeed = 1;
    }

    @Override
    public void tick() {
        endChangeDirection++;

        if(!enemyCollide || endChangeDirection >= 15){
            move();//if enemy is not collided or change direction counter is greater than 15 then move normally
        }

        position = new Point2D(position.getX() + velX*basicSpeed, position.getY() + velY*basicSpeed);
        //unit vector directing to the player

    }

    @Override
    public void collisionCode(ID id, GameObject gameObj) {
        if(id == ID.Bullet){
            //remove both objects since the bullet found its target and the basic enemy is dead
            handler.removeObject(gameObj);
            handler.removeObject( this);

        }
        if(id == ID.Barrier){
            endChangeDirection = 0;
            enemyCollide = true;
            changeDirection();
           //if the enemy moves into a barrier then change direction
        }
        if(id == ID.BasicEnemy){
            endChangeDirection = 0;
            enemyCollide = true;
            changeDirection();
            //change direction from colliding with the enemy
        }
        if(id == ID.BossEnemy){
            endChangeDirection = 0;
            enemyCollide = true;
            changeDirection();
            //change direction from colliding with the enemy
        }
        if(id == ID.BasicEnemy){
            endChangeDirection = 0;
            enemyCollide = true;
            changeDirection();
            //change direction from colliding with the enemy
        }
        if(id == ID.Player){
            endChangeDirection = 0;
            enemyCollide = true;
            changeDirection();

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





    }
    public void changeDirection(){
        velX = - velX;
        velY = - velY;
    }

}
