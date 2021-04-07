package Objects;

import core.GameObject;
import core.ID;
import core.ObjectHandler;
import javafx.scene.image.Image;

public class Health extends GameObject{


    private double health = 10.0;
    Image HealthImg = new Image("resources/man.png");//just for an example not the actual image of the basic enemy
    private Object Bullet;
    private Object BasicEnemy;

    public Health(int posX, int posY, ObjectHandler handler) {
        super(posX, posY, handler);
        this.setId(ID.Health);
        this.setImage(HealthImg);
        width = (int) HealthImg.getWidth();
        height = (int) HealthImg.getHeight();


    }

    @Override
    public void tick() {

    }

    @Override
    public void collisionCode(ID id, GameObject gameObj) {

    }
}
