package core;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Map {

    private ObjectHandler handler;
    GraphicsContext graphicsContext;
    private Image levelOneMapImage = new Image("resources/Level1.png");
    //private Image levelTwoMapImage = new Image("resources/Level2.png");
   // private  Image levelThreeMapImage = new Image("resources/Level3.png");
   // private Image bossLevelImage = new Image("resources/bossLevel.png");
    public Map(ObjectHandler handler)
    {
        this.handler = handler;
        this.graphicsContext = handler.context;
    }

    public void mapLevel1()//This renders the map.
    {
        graphicsContext.save();
        graphicsContext.drawImage(levelOneMapImage, 0, 0);
        graphicsContext.restore();
    }
    public void mapLevel2()//This renders the map.
    {
       /* graphicsContext.save();
        graphicsContext.drawImage(levelTwoMapImage, 0, 0);
        graphicsContext.restore();*/
    }

    public void mapLevel3(){}
    public void mapBossLevel(){ }


    public void tick(int level){
        if(level == 1){
            mapLevel1();
        }
        else if (level == 2)
        {
            mapLevel2();
        }


    }
}

