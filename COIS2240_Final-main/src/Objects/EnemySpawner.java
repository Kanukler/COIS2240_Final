package Objects;

import core.GameObject;
import core.ID;
import core.ObjectHandler;
import javafx.scene.image.Image;

public class EnemySpawner extends GameObject {
    private ObjectHandler handler;
    private Image spawnerImage = new Image("resources/openspawn.png");

    public EnemySpawner (int x, int y, ObjectHandler handler){
        this.handler = handler;
        this.setId(ID.EnemySpawner);
        this.setImage(spawnerImage);
    }






    @Override
    public void tick() { }
    @Override
    public void collisionCode(ID id, GameObject gameObject)
    {
        if(id == ID.EnemySpawner){
            handler.removeObjectType(ID.EnemySpawner);
        }

    }
}
