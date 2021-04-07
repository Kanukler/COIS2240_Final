package core;


import Menus.Launcher;

public final class PlayerStats {

    private static final PlayerStats INSTANCE  = new PlayerStats();

    private int speedMod = 1;
    private int maxSpeedMod  = 15;
    private int health = 100;
    private int maxHealth = 100;
    private int damage = 10;
    private int fireRate = 20;
    private int bulletVel = 15;


    private void PlayerStats(){

    }
    public static PlayerStats getInstance(){
        return INSTANCE;
    }

    public int getSpeedMod() {
        return speedMod;
    }

    public void setSpeedMod(int speedMod) {
        this.speedMod = speedMod;
        if(speedMod >= maxSpeedMod){
            this.speedMod = maxSpeedMod;
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        if(health >= maxHealth){
            this.health = maxHealth;
        }
        if(health <= 0){
            Launcher end = new Launcher();
            try {
                end.mainMenu();//when player dies go back to main menu
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }



    public int getFireRate() {
        return fireRate;
    }

    public void setFireRate(int fireRate) {

        this.fireRate = fireRate;
        if(fireRate <= 1){//fire rate cannot equal zero
            this.fireRate = 1;
        }
    }

    public int getBulletVel() {
        return bulletVel;
    }

    public void setBulletVel(int bulletVel) {
        this.bulletVel = bulletVel;
    }




}
