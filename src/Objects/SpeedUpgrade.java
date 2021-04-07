package Objects;

import core.GameObject;
import core.ID;
import core.ObjectHandler;
import javafx.scene.image.Image;

public class SpeedUpgrade extends GameObject {
    Image speedImg = new Image("resources/man.png");//just for an example not the actual image of upgrade
    public SpeedUpgrade(int posX, int posY, ObjectHandler handler){
        super(posX, posY, handler);
        this.setId(ID.SpeedUpgrade);
        this.setImage(speedImg);
        width = (int) speedImg.getWidth();
        height = (int) speedImg.getHeight();
    }
    @Override
    public void tick() {

    }

    @Override
    public void collisionCode(ID id, GameObject gameObj) {

    }
}
