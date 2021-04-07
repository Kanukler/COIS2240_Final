package Objects;

import core.GameObject;
import core.ID;
import core.ObjectHandler;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class EnemyBullet extends GameObject {
    private GameObject firer;
    GameObject player;
    Image bulletImage = new Image("resources/man.png");
    public EnemyBullet(ObjectHandler handler, double velX, double velY, GameObject firer){
        this.firer = firer;//to get the positions of the enemy that fired the bullet

        this.handler = handler;

        this.position = firer.getPosition();
        this.setId(ID.EnemyBullet);

        this.setImage(bulletImage);
        width = (int) bulletImage.getWidth();
        height = (int) bulletImage.getHeight();

        this.velX = velX;
        this.velY = velY;

    }

    @Override
    public void tick() {
        position = new Point2D(position.getX() + velX, position.getY() + velY);
    }

    @Override
    public void collisionCode(ID id, GameObject gameObj) {

    }

}
