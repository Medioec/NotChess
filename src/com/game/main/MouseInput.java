package com.game.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

    public void mousePressed(MouseEvent e) {

        LogicManager logicManager = Game.logicManager;

        logicManager.processMouseClick(e);

    }

    public static boolean mouseOver(int mx, int my, GameObject object){
        if (object.x < mx && mx < object.x + object.width && object.y < my && my < object.y + object.height){
            return true;
        } else {
            return false;
        }
    }
}
