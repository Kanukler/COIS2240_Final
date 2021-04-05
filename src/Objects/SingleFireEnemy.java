package Objects;

import Objects.EnemyBullet;
import core.GameObject;
import core.ID;
import core.ObjectHandler;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class SingleFireEnemy extends GameObject {
    private double singleSpeed;
    private GameObject player;
    private double health = 30;

    private int fireRate = 60;
    private int bulletVel = 5;
    private int timer = 0;


    Image singleImage = new Image("resources/Enemy2.png");//just for an example not the actual image of the single fire enemy

    public SingleFireEnemy(int posX, int posY, ObjectHandler handler) {
        super(posX, posY, handler);
        this.setId(ID.SingleFireEnemy);
        this.setImage(singleImage);
        width = (int) singleImage.getWidth();
        height = (int) singleImage.getHeight();
        this.player = handler.findPlayer();
        this.singleSpeed = 0.5;
    }

    @Override
    public void tick() {

        move();
        position = new Point2D(position.getX() + velX*singleSpeed, position.getY() + velY*singleSpeed);//unit vector directing to the player
        //unit vector directing to the player


        fire();
    }

    @Override
    public void collisionCode(ID id, GameObject gameObj) {
        if(id == ID.Bullet){
            System.out.println("plz don't hurt the enemy :(");
            handler.removeObject(gameObj);
            health = health - 10;
            if(health <= 0){
                System.out.println("u killed him :(");
                handler.removeObject( this);
            }


        }
        if(id == ID.Barrier){
            System.out.println("Barrier");//if the enemy moves into a barrier then change direction

        }



    }

    public void move(){

        if(position.getX() == player.getPosition().getX()){
            velX = 0;
        }
        else if(position.getX() < player.getPosition().getX()){
            velX = singleSpeed;
        }else {
            velX = - singleSpeed;
        }


        if(position.getY() == player.getPosition().getY()){
            velY = 0;
        }
        else if(position.getY() < player.getPosition().getY()){
            velY =  singleSpeed;
        }else {
            velY = - singleSpeed;
        }
        // if enemy collides with the barrier then adjust the movement


    }
    public void fire(){
        // --- Player firing
        // Calculates the unit vector to allow for consistent bullet velocity.
        // Note that since PlayerInput is static, this code can be copied anywhere it's needed.
        double mdX = player.getPosition().getX() - this.getPosition().getX();
        double mdY = player.getPosition().getY() - this.getPosition().getY();
        double mHyp = Math.sqrt(mdX * mdX + mdY * mdY);
        //Use mnx and mny to modify velocity direction.
        double mux = mdX / mHyp;
        double muy = mdY / mHyp;

        timer ++;
        if (timer >= fireRate){

                handler.addObject(new EnemyBullet(this.handler, bulletVel*mux, bulletVel*muy, this));
                timer = 0;

        }

        //handler.addObject(new Bullet(this.handler, bulletVel*position.getX(), bulletVel*position.getY()));
        //ask Thompson about the math for the vector


    }
}
