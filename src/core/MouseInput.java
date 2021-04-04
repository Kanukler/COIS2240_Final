package core;
//https://docs.oracle.com/javase/tutorial/uiswing/events/mouselistener.html#:~:text=Press%20and%20hold%20the%20left,Release%20the%20mouse%20button.

import Objects.Bullet;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;


//https://app.diagrams.net/#G12TFz69PGY1W9G4cpLPflqlCPo24Td7UU
//
public class MouseInput  {
    private ObjectHandler handler;
    private static boolean fire;
    private static Scene scene;

    public MouseInput(){

    }

    public MouseInput(ObjectHandler handler){
        this.handler = handler;

    }

    public void mouseClicked(MouseEvent e){//when mouse is clicked
        int mx = (int) e.getX();
        int my = (int) e.getY();//needs to be changed since it is in AWT

        for(int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);


            if (tempObject.getId() == ID.Player) {
                handler.addObject(new Bullet((int) tempObject.position.getX(), (int) tempObject.position.getY(), handler, mx, my));
            }
        }
    }
    EventHandler<MouseEvent> mouseClickDown = e->{
        if(e.getEventType().equals(MouseEvent.MOUSE_CLICKED)){

            System.out.println("uwu");

            fire = true;
        }

    };

    EventHandler<MouseEvent> mouseClickUp = e->{

        if(e.isPrimaryButtonDown()){
            fire = false;
        }
    };

    public static void setScene(Scene scene) {
        MouseInput.scene = scene;
    }

    public void press(){
        scene.setOnMouseClicked(mouseClickDown);
    }
}
