package Objects;

import core.GameObject;
import core.ID;
import core.ObjectHandler;
import javafx.scene.image.Image;

public class FireRateUpgrade extends GameObject {

    Image fireRateImg = new Image("resources/man.png");//just for an example not the actual image of upgrade
    public FireRateUpgrade(int posX, int posY, ObjectHandler handler){
        super(posX, posY, handler);
        this.setId(ID.FireRateUpgrade);
        this.setImage(fireRateImg);
        width = (int) fireRateImg.getWidth();
        height = (int) fireRateImg.getHeight();
    }
    @Override
    public void tick() {

    }

    @Override
    public void collisionCode(ID id, GameObject gameObj) {

    }
}
