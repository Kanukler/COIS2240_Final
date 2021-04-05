package core;


public class PlayerStats extends GameObject {

    PlayerStats p = new PlayerStats();

    private double speedMod;
    private double health = 100.0;
    private double damage = 10.0;
    private boolean immune = false;


    private PlayerStats(){
    }

    public PlayerStats getStats(){
        return p;
    }
    @Override
    public void tick() {

    }

    @Override
    public void collisionCode(ID id, GameObject gameObj) {

    }
}
