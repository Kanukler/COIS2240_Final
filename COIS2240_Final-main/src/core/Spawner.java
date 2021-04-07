package core;

import Objects.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

import java.util.Random;

/** A placeholder spawner class. At the moment, does basically nothing */
public  class Spawner extends GameObject{

    public Canvas gameCanvas;
    public Pane pane;
    private ObjectHandler handler;
    private Camera camera;
    private Random r = new Random();
    private int timer = 0;
    private int spawnerCount;
    GraphicsContext graphicsContext;

    public Spawner(ObjectHandler handler, Camera camera){
        this.handler = handler;
        this.camera = camera;
        this.graphicsContext = handler.context;
    }
    public Spawner(){}

    @Override public void tick(){
    renderLevelOne();
    timer++;

    }
    public void renderLevelOne(){
        Map map = new Map(handler);
        map.mapLevel1();
    }


    public void playerSpawner(int x, int y) {
        handler.addObject(new Player(x, y, handler));
        camera.findTarget();
    }

    public void basicEnemySpawner(int x, int y){
        handler.addObject(new BasicEnemy(x, y, handler));
    }

    public void singleFireEnemySpawner(int x, int y)
    {
        handler.addObject(new SingleFireEnemy(x, y, handler));
    }

    public void bossEnemySpawner(int x, int y){
        handler.addObject(new BossEnemy(x, y, handler));
    }

    public void spawnLevelWalls(){
        handler.addObject(new Walls(0,0, 1920,5, ID.topWall, handler));//Top border.
        handler.addObject(new Walls(0,1915, 1920,10, ID.bottomWall, handler));//Bottom border.
        handler.addObject(new Walls(1915,0, 10,1920, ID.rightWall, handler));//Right border.
        handler.addObject(new Walls(-100,0, 105,1920, ID.leftWall,handler));//Left border.
    }



    @Override
    public void collisionCode(ID id, GameObject gameObject) { }


}
