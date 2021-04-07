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
    private int endChangeDirection = 0;
    private  boolean enemyCollide = false;
    Image BossImage = new Image("resources/Boss.png");//just for an example not the actual image of the boss enemy

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
    private int bossSpeedMod = 0;

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




    }
    public void changeDirectionBoss(){
        velX = - velX;
        velY = - velY;
    }
}
