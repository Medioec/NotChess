package com.game.main;

import java.awt.Graphics;
import java.util.LinkedList;

import com.game.main.HighlightMask.MaskType;
import com.game.main.Units.Type;

import java.util.ArrayList;

public class ObjectManager {
    // The Game Object Manager. Ticks, renders and adds/removes objects.

    LinkedList<GameObject> list = new LinkedList<GameObject>();
    LinkedList<Units> unitList = new LinkedList<Units>();

    public void tick() {
        for (int i = 0; i < list.size(); i++) {
            GameObject object = list.get(i);

            object.tick();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < list.size(); i++) {
            GameObject object = list.get(i);

            object.render(g);
        }
        for (int i = 0; i < unitList.size(); i++) {
            Units unit = unitList.get(i);

            unit.render(g);
        }
    }

    public void addObject(GameObject object) {
        this.list.add(object);
    }

    public void removeObject(GameObject object) {
        this.list.remove(object);
    }

    public void removeObjectByType(ObjectType objectType){
        for (int i = 0; i < list.size(); i++) {
            GameObject object = list.get(i);
            if (object.type == objectType) {
                removeObject(object);
                i--;
            }
        }
    }

    public GameObject getObjectByID(int id) {
        for (int i = 0; i < list.size(); i++) {
            GameObject object = list.get(i);
            if (object.id == id) {
                return object;
            }
        }
        return null;
    }

    public ArrayList<GameObject> getObjectsByCoord(Coord coord){
        ArrayList<GameObject> tempList = new ArrayList<GameObject>();
        for (int i = 0; i < list.size(); i++) {
            GameObject object = list.get(i);
            if (object.getCoord().x == coord.x && object.getCoord().y == coord.y) {
                tempList.add(object);
            }
        }
        return tempList;
    }

    public void addUnit(Units unit) {
        this.unitList.add(unit);
    }

    public void removeUnit(Units unit) {
        this.unitList.remove(unit);
    }

    public void unitDefeated(Units unit){
        int terrainID = Map.getTerrainID(unit.getCoord());
        Map.setMapData(unit.getCoord(), -1, terrainID);
        this.unitList.remove(unit);
    }

    public Units getUnitByID(int id) {
        for (int i = 0; i < unitList.size(); i++) {
            Units unit = unitList.get(i);
            if (unit.id == id) {
                return unit;
            }
        }
        return null;
    }

    public ArrayList<Units> getMoveableList() {
        ArrayList<Units> tempList = new ArrayList<Units>();
        for (int i = 0; i < unitList.size(); i++) {
            Units unit = unitList.get(i);
            if (unit.canMove == true) {
                tempList.add(unit);
            }
        }
        return tempList;
    }

    public boolean checkMoveableUnits(){
        for (int i = 0; i < unitList.size(); i++) {
            Units unit = unitList.get(i);
            if (unit.canMove == true) {
                return true;
            }
        }
        return false;
    }

    public void setUnitsMoveable(int player) {
        for (int i = 0; i < unitList.size(); i++) {
            Units unit = unitList.get(i);
            if (unit.player == player) {
                unit.canMove = true;
                new HighlightMask(unit.getCoord(), MaskType.MOVEABLEMASK);
                //System.out.println("Unit " + unit.name + " set moveable.");
            }
        }
    }

    public void setUnitsNotMoveable(int player) {
        for (int i = 0; i < unitList.size(); i++) {
            Units unit = unitList.get(i);
            if (unit.player == player && unit.canMove == true) {
                unit.canMove = false;
                HighlightMask.removeMask(unit.getCoord());
                //System.out.println("Set unit "+unit.name+unit.id+" not moveable");
            }
        }
    }

    public void removeGameObjects() {
        for (int i = 0; i < list.size(); i++) {
            GameObject object = list.get(i);
            if (object.type == ObjectType.GameObject) {
                removeObject(object);
                i--;
            }
        }
        unitList.clear();
    }

    public Units getPlayerKing(int player){
        for (int i = 0; i < unitList.size(); i++) {
            Units unit = unitList.get(i);
            if (unit.type == Type.KING && unit.player == player) {
                return unit;
            }
        }
        return null;
    }

    public int getKingCount(){
        int count = 0;
        for (int i = 0; i < unitList.size(); i++) {
            Units unit = unitList.get(i);
            if (unit.type == Type.KING) {
                count++;
            }
        }
        return count;
    }
}
