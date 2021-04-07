package Objects;

import core.GameObject;
import core.ID;
import core.ObjectHandler;

public class Walls extends GameObject {


    public Walls(int posX, int posY, int height, int width, ID id, ObjectHandler handler) {
        super(posX, posY, height, width, id,handler);
    }
    @Override
    public void tick() { }

    @Override
    public void collisionCode(ID id, GameObject gameObject) {

    }



}
