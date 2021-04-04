package core;

import javafx.scene.image.Image;

public class TemporaryEnemy extends GameObject{

    private double speedModX;


    Image enemyImage = new Image("resources/man.png");

    public TemporaryEnemy(int posX, int posY, ObjectHandler handler){
        super(posX, posY, handler);
        this.setId(ID.Enemy);
        this.setImage(enemyImage);
        width = (int) enemyImage.getWidth();
        height = (int) enemyImage.getHeight();
        this.speedModX = 1;

    }

    @Override
    public void tick() {

    }

    @Override
    public void collisionCode(ID id) {

    }


}
