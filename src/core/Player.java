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

    private double speedMod;

    Image playerImage = new Image("resources/man.png");

    public Player(int posX, int posY, ObjectHandler handler) {
        super(posX, posY, handler);
        this.setId(ID.Player);
        this.setImage(playerImage);
        width = (int) playerImage.getWidth();
        height = (int) playerImage.getHeight();
        this.speedMod = 5;
    }

    @Override
    public void collisionCode(ID id, GameObject object) {

    }

    @Override
    public void tick() {
        playerInput();
        position = new Point2D(position.getX() + velX, position.getY() + velY);
    }

    public void setSpeedMod(double speedMod) {
        this.speedMod = speedMod;
    }










    // --- Below here is all information for player input --- //

    private int fireRate = 20;
    private int bulletVel = 15;
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

        velX = speedMod*uX; velY = speedMod*uY;



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

    // --- Getters and setters --- //
    public void setFireRate(int fireRate) {
        this.fireRate = fireRate;
    }

    public void setBulletVel(int bulletVel) {
        this.bulletVel = bulletVel;
    }

}
