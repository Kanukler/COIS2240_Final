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

    private double speedModX;
    private double speedModY;
    private double health = 100.0;
    private double damage = 10.0;
    private boolean immune = false;
    Image playerImage = new Image("resources/man.png");

    public Player(int posX, int posY, ObjectHandler handler) {
        super(posX, posY, handler);
        this.setId(ID.Player);
        this.setImage(playerImage);
        width = (int) playerImage.getWidth();
        height = (int) playerImage.getHeight();
        speedModX = 1;
        speedModY = 1;
    }

    @Override
    public void collisionCode(ID id, GameObject gameObj) {
        if(id == ID.EnemyBullet){

            health = health - damage;
            System.out.println(health);
            handler.removeObject(gameObj);//removes enemy bullet
        }
        if(id == ID.BasicEnemy && !immune){


            health = health - damage;
            System.out.println(health);
            immune = true;

        }
        if(id == ID.Health){
            handler.removeObject(gameObj);
            health = health + 20;
            if(health > 100){
                health = 100;
            }
            System.out.println(health);
        }
        if(health <= 0){


            System.exit(0);
            //exits program
            //https://www.delftstack.com/howto/java/java-end-program/#:~:text=To%20end%20a%20Java%20program,program%20that%20we%20are%20running.
        }

        playImmunity();



        if(id == ID.topWall){
            if(velY < 0) speedModY = 0;
            else if (velY > 0)speedModY = 1;
        }
        if(id == ID.bottomWall)
        {
            if(velY > 0)speedModY = 0;
            else if(velY < 0) speedModY = 1;
        }
        if (id == ID.leftWall){
            if(velX < 0)speedModX = 0;
            else if (velX > 0)speedModX = 1;
        }
        if (id == ID.rightWall)
        {
            if(velX > 0) speedModX = 0;
            else if (velX < 0) speedModX = 1;
        }

    }
    public void playImmunity(){

        if(immune){

            immune = false;

        }

    }

    @Override
    public void tick() {
        playerInput();
        position = new Point2D(position.getX() + velX*speedModX, position.getY() + velY*speedModY);
    }
    // --- Below here is all information for player input --- //

    private int fireRate = 50;
    private int bulletVel = 15;
    private int timer = 0;

    public void playerInput(){
        PlayerInput e = PlayerInput.newInput();
        e.press();

        // Player Movement
        if(PlayerInput.up) velY = -5;
        else if(!PlayerInput.down) velY = 0;
        if(PlayerInput.left) velX = -5;
        else if(!PlayerInput.right) velX = 0;
        if(PlayerInput.down) velY = 5;
        if(PlayerInput.right) velX = 5;

        if(PlayerInput.up && PlayerInput.down) velY = 0;
        if(PlayerInput.left && PlayerInput.right) velX = 0;

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
        if (timer >= fireRate){
            if (PlayerInput.firing){
                handler.addObject(new Bullet(this.handler, bulletVel*mux, bulletVel*muy));
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

        public static PlayerInput newInput(){
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

    // --- Getters and setters --- //
    public void setFireRate(int fireRate) {
        this.fireRate = fireRate;
    }

    public void setBulletVel(int bulletVel) {
        this.bulletVel = bulletVel;
    }

    public double getSpeedModX() {
        return speedModX;
    }

    public void setSpeedModX(double speedModX) {
        this.speedModX = speedModX;
    }

    public double getSpeedModY() {
        return speedModY;
    }

    public void setSpeedModY(double speedModY) {
        this.speedModY = speedModY;
    }

}
