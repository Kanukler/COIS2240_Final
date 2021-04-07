package core;

import Objects.BasicEnemy;
import Objects.BossEnemy;
import Objects.Bullet;

import java.util.Timer;
import java.util.TimerTask;

public class LevelLoader {


    public LevelLoader(Spawner player, Map map, BasicEnemy enemy, Spawner enemySpawner, BossEnemy bossEnemy, Bullet bullet){

    }

    public void loadLevel(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {


            }
        },0, 3000);
    }


}
