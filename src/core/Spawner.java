package core;

import Objects.*;
import levels.Loader;
import levels.Obstacle;

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
        HUD.setPlayer(handler.findPlayer());
    }

    public void spawnBasicEnemy(int x, int y){
        handler.addObject(new BasicEnemy(x, y, handler));
        Loader.enemyCount++;
    }

    public void spawnSingleEnemy(int x, int y){
        handler.addObject(new SingleFireEnemy(x,y,handler));
        Loader.enemyCount++;
    }

    public void spawnObstacle(int x, int y){
        handler.addObject(new Obstacle(x, y, handler));
    }


    public void spawnBossEnemy(int x, int y){
        handler.addObject(new BossEnemy(x,y,handler));
        Loader.enemyCount++;
    }


}
