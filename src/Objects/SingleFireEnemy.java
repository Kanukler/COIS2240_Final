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

    private int fireRate = 180;
    private int bulletVel = 5;
    private int timer = 0;

    private int ran;

    Image singleImage = new Image("resources/Enemy2.png");
    Image firingImage = new Image("resources/Enemy2F.png");

    private  boolean enemyCollide = false;
    private int endChangeDirection = 0;


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
        endChangeDirection++;

        if(!enemyCollide || endChangeDirection >= 15){
            move();//if enemy is not collided or change direction counter is greater than 15 then move normally
        }
        position = new Point2D(position.getX() + velX*singleSpeed, position.getY() + velY*singleSpeed);
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
            endChangeDirection = 0;
            enemyCollide = true;
            changeDirectionSingle();
            //if the enemy moves into a barrier then change direction
        }
        if(id == ID.SingleFireEnemy){
            endChangeDirection = 0;
            enemyCollide = true;
            changeDirectionSingle();
            //change direction from colliding with the enemy
        }
        if(id == ID.BasicEnemy){
            endChangeDirection = 0;
            enemyCollide = true;
            changeDirectionSingle();
            //change direction from colliding with the enemy
        }
        if(id == ID.Player){
            endChangeDirection = 0;
            enemyCollide = true;
            changeDirectionSingle();

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
        if (timer == fireRate - 40) this.setImage(firingImage);

        if (timer >= fireRate){
            ran = (int)(Math.random()*300) + 200;
            double coin = (Math.random());
            //System.out.println(coin);
            //https://stackoverflow.com/questions/8610635/how-do-you-use-math-random-to-generate-random-ints/8610691
            if(coin <= 0.5){
                ran = -ran;
            }
            handler.addObject(new EnemyBullet(this.handler, bulletVel*mux, bulletVel*muy, this));

                handler.addObject(new EnemyBullet(this.handler, bulletVel*mux, bulletVel*muy, this));
                timer = 0;
                this.setImage(singleImage);
        }



        }

    public void changeDirectionSingle(){
        velX = - velX;
        velY = - velY;
    }
}
