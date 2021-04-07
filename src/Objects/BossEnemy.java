package Objects;

import core.GameObject;
import core.ID;
import core.ObjectHandler;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class BossEnemy extends GameObject {
    private final double bossSpeed;
    private GameObject player;
    private double health = 500;

    private int charge = 300, fireRate = 5;
    private int bulletVel = 5;
    private int timer = 0;

    Image bossImage = new Image("resources/Boss.png");
    Image bossFire1 = new Image("resources/BossF1.png");
    Image bossFire2 = new Image("resources/BossF2.png");
    Image bossFire3 = new Image("resources/BossF3.png");

    public BossEnemy(int posX, int posY, ObjectHandler handler){
        super(posX, posY, handler);
        this.setId(ID.BossEnemy);
        this.setImage(bossImage);
        width = (int) bossImage.getWidth();
        height = (int) bossImage.getHeight();
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

    public void move(){

        if(position.getX() < player.getPosition().getX() - ran1){
            velX = 0;
        }
        if(position.getX() > player.getPosition().getX() - ran1){
            velX = - 0;
        }

        if(position.getY() < player.getPosition().getY() - ran1){
            velY = 0;
        }
        if(position.getY() > player.getPosition().getY() - ran1){
            velY = - 0;
        }
        // if enemy collides with the barrier then adjust the movement


    }






    // --- Boss firing mechanics --- //
    private int ran1, ran2;
    private boolean firing = false;
    private double coin = 0;
    public void fire(){
        if(timer == 0) {
            firing = false;
            coin = Math.random();
            this.setImage(bossImage);
        }
        if(!firing) timer ++;

        if (timer == charge - 120) this.setImage(bossFire1);
        else if (timer == charge - 100) this.setImage(bossFire2);
        else if (timer >= charge - 30) {

            velX = 0; velY = 0;
            this.setImage(bossFire3);

        }


        if (timer >= charge || firing){
            if (coin < 0.5)
                firePattern1();
            else
                firePattern2();

            velX = 0; velY = 0;
            firing = true;
            timer = timer - 2;
            this.setImage(bossFire3);

        }

        //handler.addObject(new Bullet(this.handler, bulletVel*position.getX(), bulletVel*position.getY()));
        //ask Thompson about the math for the vector

    }

    private void firePattern1(){
        bulletVel = 5;
        ran1 = (int)(Math.random()*this.getPosition().getX());
        ran2 = (int)(Math.random()*this.getPosition().getY());
        double coin1 = Math.random();
        double coin2 = Math.random();
        //System.out.println(coin);
        //https://stackoverflow.com/questions/8610635/how-do-you-use-math-random-to-generate-random-ints/8610691
        if(coin1 <= 0.5){
            ran1 = -ran1;
        }
        if(coin2 <= 0.5){
            ran2 = -ran2;
        }

        // Calculates the unit vector to allow for consistent bullet velocity.
        // Note that since PlayerInput is static, this code can be copied anywhere it's needed.
        double mdX = ran1;
        double mdY = ran2;
        double mHyp = Math.sqrt(mdX * mdX + mdY * mdY);
        //Use mnx and mny to modify velocity direction.
        double mux = mdX / mHyp;
        double muy = mdY / mHyp;

        if(timer % 2 == 0) {
            handler.addObject(new EnemyBullet(this.handler, bulletVel * mux, bulletVel * muy, this));
        }
    }

    private void firePattern2(){
        bulletVel = 10;
        ran1 = (int)(Math.random()*this.getPosition().getX())/3;
        ran2 = (int)(Math.random()*this.getPosition().getY())/3;
        double coin1 = Math.random();
        double coin2 = Math.random();
        //System.out.println(coin);
        //https://stackoverflow.com/questions/8610635/how-do-you-use-math-random-to-generate-random-ints/8610691
        if(coin1 <= 0.5){
            ran1 = -ran1;
        }
        if(coin2 <= 0.5){
            ran2 = -ran2;
        }

        // Calculates the unit vector to allow for consistent bullet velocity.
        // Note that since PlayerInput is static, this code can be copied anywhere it's needed.
        double mdX = player.getPosition().getX() - this.getPosition().getX() +ran1;
        double mdY = player.getPosition().getY() - this.getPosition().getY() +ran2;
        double mHyp = Math.sqrt(mdX * mdX + mdY * mdY);
        // Use mnx and mny to modify velocity direction.
        double mux = mdX / mHyp;
        double muy = mdY / mHyp;

        if(timer % fireRate == 0) {
            handler.addObject(new EnemyBullet(this.handler, bulletVel * mux, bulletVel * muy, this));
        }
    }

}
