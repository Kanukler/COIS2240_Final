package Objects;
import core.GameObject;
import core.ID;
import core.ObjectHandler;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class BossEnemy extends GameObject {
    private double bossSpeed;
    private int bossSpeedMod = 0;
    private GameObject player;

    private double health = 500;

    private int charge = 300, fireRate = 5;
    private int bulletVel = 5;
    private int timer = 0;

    Image bossImage = new Image("resources/Boss.png");
    Image bossFire1 = new Image("resources/BossF1.png");
    Image bossFire2 = new Image("resources/BossF2.png");
    Image bossFire3 = new Image("resources/BossF3.png");

    private int endChangeDirection = 0;
    private  boolean enemyCollide = false;
    Image BossImage = new Image("resources/Boss.png");//just for an example not the actual image of the boss enemy


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
        endChangeDirection++;

        if(!enemyCollide || endChangeDirection >= 15){
            move();//if enemy is not collided or change direction counter is greater than 15 then move normally
        }
        fire();
        position = new Point2D(position.getX() + velX*bossSpeed, position.getY() + velY*bossSpeed);
        //unit vector directing to the player
    }



    @Override
    public void collisionCode(ID id, GameObject gameObj) {
        if(id == ID.Bullet){
            health = health - 10;
            handler.removeObject(gameObj);//remove the bullet
            if(health <= 0){

                handler.removeObject( this);//Boss is dead remove it from the game
            }
        }
        if(id == ID.Barrier){
            endChangeDirection = 0;
            enemyCollide = true;
            changeDirectionBoss();
            //if the enemy moves into a barrier then change direction
        }
        if(id == ID.Player){

            bossSpeed = 0;
            bossSpeedMod = 0;

        }
        if(id == ID.BasicEnemy){
            endChangeDirection = 0;
            enemyCollide = true;
            changeDirectionBoss();
            //change direction from colliding with the enemy
        }
        if(id == ID.SingleFireEnemy){
            endChangeDirection = 0;
            enemyCollide = true;
            changeDirectionBoss();
            //change direction from colliding with the enemy
        }
        if(id == ID.BossEnemy){
            endChangeDirection = 0;
            enemyCollide = true;
            changeDirectionBoss();
            //change direction from colliding with the enemy
        }

    }




    public void move(){
        double mdX = player.getPosition().getX() - this.getPosition().getX();
        double mdY = player.getPosition().getY() - this.getPosition().getY();
        double mHyp = Math.sqrt(mdX * mdX + mdY * mdY);
        //Use mnx and mny to modify velocity direction.
        double mux = mdX / mHyp;
        double muy = mdY / mHyp;
        velX = mux*bossSpeed;
        velY = muy*bossSpeed;

        if(bossSpeedMod <= 360){
            bossSpeed = 1;
            bossSpeedMod++;
        }
        if(bossSpeedMod > 360 && bossSpeedMod < 420){
            bossSpeed = 0;
            bossSpeedMod++;
        }

        if(bossSpeedMod >= 420){
            bossSpeed = 2;
            bossSpeedMod++;
        }
        if(bossSpeedMod > 540 ){
            bossSpeedMod = 0;


        }


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
    public void changeDirectionBoss(){
        velX = - velX;
        velY = - velY;

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
