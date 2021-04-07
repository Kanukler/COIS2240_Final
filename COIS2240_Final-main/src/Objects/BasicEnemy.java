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
    private Object Bullet;
    private Object BasicEnemy;

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

        move();
        position = new Point2D(position.getX() + velX*basicSpeed, position.getY() + velY*basicSpeed);//unit vector directing to the player

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
        if(id == ID.leftWall){
           changeDirection(); //if the enemy moves into a barrier then change direction
        } else if (id == ID.rightWall){
            changeDirection();
        }
        else if (id == ID.topWall){
            changeDirection();
        }
        else if (id == ID.bottomWall){
            changeDirection();
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

        if(position.getX() == player.getPosition().getX()){
            velX = 0;
        }
        else if(position.getX() < player.getPosition().getX()){
            velX = basicSpeed;
        }else {
            velX = - basicSpeed;
        }


        if(position.getY() == player.getPosition().getY()){
            velY = 0;
        }
        else if(position.getY() < player.getPosition().getY()){
            velY =  basicSpeed;
        }else {
            velY = - basicSpeed;
        }

        // if enemy collides with the barrier then adjust the movement


    }
    public void changeDirection(){

    }

}
