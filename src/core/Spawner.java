package core;

import Objects.BasicEnemy;
import Objects.BossEnemy;
import Objects.Health;
import Objects.SingleFireEnemy;

/** A placeholder spawner class. At the moment, does basically nothing */
public class Spawner {
    private ObjectHandler handler;
    private Camera camera;

    public Spawner(ObjectHandler handler, Camera camera){
        this.handler = handler;
        this.camera = camera;
    }

    /** It is essential that camera.findTarget is ran whenever the player is added
     * */
    public void spawnPlayer(int x, int y){
        handler.addObject(new Player(x, y, handler));
        camera.findTarget();
    }

    public void spawnBasicEnemy(int x, int y){
        handler.addObject(new BasicEnemy(x, y, handler));

    }

    public void spawnSingleEnemy(int x, int y){
        handler.addObject(new SingleFireEnemy(x,y,handler));
    }

    public void spawnHealth(int x, int y){
        handler.addObject(new Health(x, y, handler));
    }


    public void spawnBossEnemy(int x, int y){
        handler.addObject(new BossEnemy(x,y,handler));
    }




    public void tick(){
    }



}
