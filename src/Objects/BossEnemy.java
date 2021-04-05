package Objects;

import core.GameObject;
import core.ID;
import core.ObjectHandler;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class BossEnemy extends GameObject {
    private double bossSpeed;
    private GameObject player;
    private double health = 50;


    private int fireRate = 60;
    private int bulletVel = 5;
    private int timer = 0;

    Image BossImage = new Image("resources/Enemy.png");//just for an example not the actual image of the boss enemy

    public BossEnemy(int posX, int posY, ObjectHandler handler){
        super(posX, posY, handler);
        this.setId(ID.BossEnemy);
        this.setImage(BossImage);
        width = (int) BossImage.getWidth();
        height = (int) BossImage.getHeight();
        this.player = handler.findPlayer();
        this.bossSpeed = 1;//I just want a slower enemy
    }

    @Override
    public void tick() {
        move();
        fire();
        position = new Point2D(position.getX() + velX*bossSpeed, position.getY() + velY*bossSpeed);
        //unit vector directing to the player
    }



    @Override
    public void collisionCode(ID id, GameObject gameObj) {
        if(id == ID.Bullet){
            health = health - 10;
            System.out.println("u hit the boss");
            handler.removeObject(gameObj);
            if(health <= 0){
                System.out.println("u killed him :(");
                handler.removeObject( this);
            }
        }
        if(id == ID.Barrier){
            System.out.println("Barrier");//if the enemy moves into a barrier then change direction

        }

    }
    private int ran;
    public void move(){

        if(position.getX() < player.getPosition().getX() - ran){
            velX = 1;
        }
        if(position.getX() > player.getPosition().getX() - ran){
            velX = - 1;
        }

        if(position.getY() < player.getPosition().getY() - ran){
            velY = 1;
        }
        if(position.getY() > player.getPosition().getY() - ran){
            velY = - 1;
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
                ran = (int)(Math.random()*300) + 200;
                double coin = (Math.random());
                //System.out.println(coin);
                //https://stackoverflow.com/questions/8610635/how-do-you-use-math-random-to-generate-random-ints/8610691
                if(coin <= 0.5){
                    ran = -ran;
                }
                handler.addObject(new EnemyBullet(this.handler, bulletVel*mux, bulletVel*muy, this));



            timer = 0;

        }

        //handler.addObject(new Bullet(this.handler, bulletVel*position.getX(), bulletVel*position.getY()));
        //ask Thompson about the math for the vector


    }
}
