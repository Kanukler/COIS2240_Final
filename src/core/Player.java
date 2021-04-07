package core;

import Menus.Launcher;
import Objects.Bullet;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/** The player, as well as the player's controls.
 * Only reason I can think of to change this would be to adjust player speed
 * */
public class Player extends GameObject {

    private boolean immune = false;//when true the player is immune
    private int immuneCount = 0;//counter for the length the player is immune for
    Image playerImage = new Image("resources/man.png");//booger man image
    PlayerStats user = PlayerStats.getInstance();

    public Player(int posX, int posY, ObjectHandler handler) {
        super(posX, posY, handler);
        this.setId(ID.Player);
        this.setImage(playerImage);
        width = (int) playerImage.getWidth();
        height = (int) playerImage.getHeight();

    }


    public void collisionCode(ID id, GameObject gameObj) {
        if(id == ID.EnemyBullet){
            handler.removeObject(gameObj);//removes enemy bullet


            if(!immune){

                immune = true;//player is immune
                immuneCount = 0;//counter starts for the immunity period
                user.setHealth(user.getHealth() -10);//damage taken from the bullet
            }
        }
        if(id == ID.BossEnemy){
            if(!immune){

                immune = true;//player is immune
                immuneCount = 0;//counter starts for the immunity period

                user.setHealth(user.getHealth() - 20);//damage taken from the bosses melee attack

            }


        }
        if(id == ID.BasicEnemy && !immune){

            user.setHealth(user.getHealth() - 10);//damage taken from the Basic Enemies melee attack

            immune = true;//player is immune
            immuneCount = 0;//counter starts for the immunity period

        }
        if(id == ID.SingleFireEnemy && !immune){

            user.setHealth(user.getHealth() - 10);//damage taken from the Basic Enemies melee attack

            immune = true;//player is immune
            immuneCount = 0;//counter starts for the immunity period

        }
        if(id == ID.Health){
            handler.removeObject(gameObj);//remove the health upgrade
            user.setHealth(user.getHealth() + 20);//heal player by 20
        }

        if(id == ID.FireRateUpgrade){
            handler.removeObject(gameObj);//remove the fire rate upgrade
            user.setFireRate(user.getFireRate() - 2);//reduce fire rate by 2

        }
        if(id == ID.SpeedUpgrade){
            handler.removeObject(gameObj);//remove the player speed upgrade
            user.setSpeedMod(user.getSpeedMod() + 5);//increase speed by 5
        }




    }
    public void playImmunity(){
        //immunity ends
        if(immune){

            immune = false;

        }


    }


    @Override
    public void tick() {
        playerInput();

        position = new Point2D(position.getX() + velX, position.getY() + velY);

        immuneCount = immuneCount + 1;
        if (180 <= immuneCount && immune) {

        playImmunity();
        immuneCount = 0;//reset the immunity counter
        }

        playerInput();//take in player input
        position = new Point2D(position.getX() + velX * user.getSpeedMod(), position.getY() + velY * user.getSpeedMod());

    }









    // --- Below here is all information for player input --- //



    private int timer = 0;

    public void playerInput(){
        PlayerInput e = PlayerInput.getInput();
        e.press();
        // Player Movement
        /* Obsolete
        if(PlayerInput.up) velY = -5;
        else if(!PlayerInput.down) velY = 0;
        if(PlayerInput.left) velX = -5;
        else if(!PlayerInput.right) velX = 0;
        if(PlayerInput.down) velY = 5;
        if(PlayerInput.right) velX = 5;

        if(PlayerInput.up && PlayerInput.down) velY = 0;
        if(PlayerInput.left && PlayerInput.right) velX = 0;
         */

        double mX = 0, mY = 0;
        //mX = 0; mY = 0;

        if(PlayerInput.up) mY -= 1;
        if(PlayerInput.down) mY +=1;
        if(PlayerInput.left) mX -= 1;
        if(PlayerInput.right) mX += 1;

        double Hyp = Math.sqrt(mX * mX + mY * mY);
        if (Hyp == 0) Hyp = 1;
        //Use mnx and mny to modify velocity direction.
        double uX = mX / Hyp;
        double uY = mY / Hyp;

        velX = user.getSpeedMod()*uX; velY = user.getSpeedMod()*uY;



        // --- Player firing
        // Calculates the unit vector to allow for consistent bullet velocity.
        // Note that since PlayerInput is static, this code can be copied anywhere it's needed.
        double mdX = PlayerInput.mouseX - 500;
        double mdY = PlayerInput.mouseY - 300;
        double mHyp = Math.sqrt(mdX * mdX + mdY * mdY);
        //Use mnx and mny to modify velocity direction.
        double mux = mdX / mHyp;
        double muy = mdY / mHyp;

        timer ++;
        if (timer >= user.getFireRate()){
            if (PlayerInput.firing){
            handler.addObject(new Bullet(this.handler, user.getBulletVel()*mux, user.getBulletVel()*muy));
            timer = 0;
            }
        }

        // Exits the game upon registering that the escape key has been pressed.
        // This may be changed later to a pause rather than a hard exit
        if (PlayerInput.exit){
            Launcher launch = new Launcher();
            try {
                launch.mainMenu();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            handler.clearObjects();
        }

    }

    /** Nested class for inputs, most of the properties are static as there's no need to worry
     * about multiple instances of player input, and it's easier to set the scene from Main that way.
     * */
    public static class PlayerInput{
        private static boolean up, down, left, right;
        private static boolean exit;
        private static boolean firing;
        private static Scene scene;
        public static double mouseX, mouseY;

        public static PlayerInput getInput(){
            return new PlayerInput();
        }
        public PlayerInput() {
        }

        public void setScene(Scene scene) {
            PlayerInput.scene = scene;
        }

        // Uses the key event checkers to set directional values to true when keys are pressed,
        // then to false when released
        public void press(){
            scene.setOnKeyPressed(keyDown);
            scene.setOnKeyReleased(keyUp);
            scene.setOnMousePressed(mouseDown);
            scene.setOnMouseDragged(mouseDown);
            scene.setOnMouseReleased(mouseUp);
        }


        // Sets the directional values to true upon key event
        EventHandler<KeyEvent> keyDown = e -> {
            if (e.getCode().equals(KeyCode.W)) {
                up = true;
            }

            if (e.getCode().equals(KeyCode.A)) {
                left = true;
            }

            if (e.getCode().equals(KeyCode.S)) {
                down = true;
            }

            if (e.getCode().equals(KeyCode.D)) {
                right = true;
            }

            if (e.getCode().equals(KeyCode.ESCAPE)){
                exit = true;
            }
        };

        // Sets the directional values to false upon key event
        EventHandler<KeyEvent> keyUp = e -> {
            if (e.getCode().equals(KeyCode.W)) {
                up = false;
            }

            if (e.getCode().equals(KeyCode.A)) {
                left = false;
            }

            if (e.getCode().equals(KeyCode.S)) {
                down = false;
            }

            if (e.getCode().equals(KeyCode.D)) {
                right = false;
            }

            if (e.getCode().equals(KeyCode.ESCAPE)){
                exit = false;
            }

        };

        // Mouse events, implemented the same way as above
        EventHandler<MouseEvent> mouseDown = e ->{
          if (e.getButton() == MouseButton.PRIMARY){
              firing = true;
              mouseX = e.getX();
              mouseY = e.getY();
          }
        };
        EventHandler<MouseEvent> mouseUp = e ->{
            if (e.getButton() == MouseButton.PRIMARY){
                firing = false;
            }
        };

    }



}
