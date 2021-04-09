package Menus;

import core.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import levels.Loader;

public class Launcher {

    private static ObjectHandler handler;
    private static GameLoop loop;
    public static Scene scene;

    public Launcher(){

    }

    public void game() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Game.fxml"));
        scene.setRoot(root);

        Player.PlayerInput.getInput().setScene(scene);
    }

    public void score() throws  Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../Menus/HighScore.fxml"));
        scene.setRoot(root);
    }

    public void mainMenu() throws  Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../Menus/MainMenu.fxml"));
        scene.setRoot(root);
    }

    public void gameEnd() throws Exception{
        loop.stop();

        handler.clearObjects();
        PlayerStats.reset();
        Loader.enemyCount = 0;

        mainMenu();
    }

    public static void setHandler(ObjectHandler handler) {
        Launcher.handler = handler;
    }

    public static void setLoop(GameLoop loop) {
        Launcher.loop = loop;
    }
}
