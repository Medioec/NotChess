package com.game.main;

import java.awt.Graphics2D;
import java.util.LinkedList;

import com.game.main.HighlightMask.MaskType;
import com.game.main.Units.UnitType;

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

    public void render(Graphics2D g) {
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
        int terrainID = Game.map.getTerrainID(unit.getCoord());
        Game.map.setMapData(unit.getCoord(), -1, terrainID);
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
            if (unit.getMoveable() == true) {
                tempList.add(unit);
            }
        }
        return tempList;
    }

    public boolean checkMoveableUnits(){
        for (int i = 0; i < unitList.size(); i++) {
            Units unit = unitList.get(i);
            if (unit.getMoveable() == true) {
                return true;
            }
        }
        return false;
    }

    public void setUnitsMoveable(int player) {
        for (int i = 0; i < unitList.size(); i++) {
            Units unit = unitList.get(i);
            if (unit.getPlayer() == player) {
                unit.setMoveable(true);;
                new HighlightMask(unit.getCoord(), MaskType.MOVEABLEMASK);
            }
        }
    }

    public void setUnitsNotMoveable(int player) {
        for (int i = 0; i < unitList.size(); i++) {
            Units unit = unitList.get(i);
            if (unit.getPlayer() == player && unit.getMoveable() == true) {
                unit.setMoveable(false);
                HighlightMask.removeMask(unit.getCoord());
            }
        }
    }

    public void setAllUnitsNotMoveable(){
        for (int i = 0; i < unitList.size(); i++) {
            Units unit = unitList.get(i);
            unit.setMoveable(false);
            HighlightMask.removeMask(unit.getCoord());
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
            if (unit.getUnitType() == UnitType.KING && unit.getPlayer() == player) {
                return unit;
            }
        }
        return null;
    }

    public int getKingCount(){
        int count = 0;
        for (int i = 0; i < unitList.size(); i++) {
            Units unit = unitList.get(i);
            if (unit.getUnitType() == UnitType.KING) {
                count++;
            }
        }
        return count;
    }
}
