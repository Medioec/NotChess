package com.game.main;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Color;

public class HighlightMask extends GameObject{
    public static int[][] mapMaskData;
    public MaskType maskType;

    public HighlightMask(Coord coords, MaskType maskType) {
        super(coords);
        this.height = 50;
        this.width = 50;
        this.type = ObjectType.GameObject;
        this.maskType = maskType;
        setCoord(coords);
        int n = 0;
        switch(maskType){
            case MOVEMASK:
            n = 1;
            break;
            case ACTIVEMASK:
            n = 2;
            break;
            case ATTACKMASK:
            n = 3;
            break;
            default:
            n = 0;
            break;
        }
        setMaskMap(coords, n);
        Game.objectManager.addObject(this);
    }

    public enum MaskType{
        MOVEMASK,
        MOVEABLEMASK,
        ACTIVEMASK,
        ATTACKMASK,
        ATTACKCONFIRMMASK;
    }

    @Override
    public void tick() {        
    }

    @Override
    public void render(Graphics2D g) {
        if(maskType == MaskType.MOVEMASK){
            g.setColor(new Color(0f, 0.8f, 1f));
            g.fillRect(x + 1, y + 1, width - 1, height - 1);
            //g.fillRect(x + 2, y + 2, width - 4, height - 4);
        } else if(maskType == MaskType.MOVEABLEMASK) {
            g.setColor(new Color(1f, 1f, 1f, 0.8f));
            g.fillRect(x + 1, y + 1, width - 1, height - 1);
            //g.fillRect(x + 2, y + 2, width - 4, height - 4);
        } else if(maskType == MaskType.ACTIVEMASK) {
            g.setColor(new Color(1f, 1f, 0f, 0.8f));
            g.fillRect(x + 1, y + 1, width - 1, height - 1);
            //g.fillRect(x + 2, y + 2, width - 4, height - 4);
        } else if(maskType == MaskType.ATTACKMASK) {
            g.setColor(new Color(1f, 0f, 0f));
            g.fillRect(x + 1, y + 1, width - 1, height - 1);
            //g.fillRect(x + 2, y + 2, width - 4, height - 4);
        } else if(maskType == MaskType.ATTACKCONFIRMMASK) {
            g.setColor(new Color(1f, 0f, 0f));
            g.fillRect(x + 1, y + 1, width - 1, height - 1);
            //g.fillRect(x + 2, y + 2, width - 4, height - 4);
        }
    }

    public static void initializeMaskMap(int x, int y){
        mapMaskData = new int[x][y];
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                mapMaskData[i][j] = 0;
            }
        }
    }

    public int enumMaskType(){
        int n;
        switch(maskType){
            case MOVEMASK:
            n = 1;
            break;
            case MOVEABLEMASK:
            n = 2;
            break;
            case ATTACKMASK:
            n = 3;
            break;
            default:
            n = 0;
            break;
        }
        return n;
    }

    public static HighlightMask getMaskObject(Coord coord){
        HighlightMask mask = null;
        for (int i = 0; i < Game.objectManager.list.size(); i++) {
            try {
                mask = (HighlightMask) Game.objectManager.list.get(i);
            } catch (ClassCastException e){
                continue;
            }
            if(coord.x == mask.getCoord().x && coord.y == mask.getCoord().y){
                return mask;
            }
        }

        return null;
    }

    public static int getMaskMap(Coord coord){
        return mapMaskData[coord.x][coord.y];
    }

    public static void setMaskMap(Coord coord, int n){
        mapMaskData[coord.x][coord.y] = n;
    }

    public static void resetMoveMask(){
        for (int i = 0; i < Game.objectManager.list.size(); i++) {
            HighlightMask mask;
            try {
                mask = (HighlightMask) Game.objectManager.list.get(i);
            } catch (ClassCastException e){
                continue;
            }
            if (mask.maskType == MaskType.MOVEMASK) {
                Coord coord = mask.getCoord();
                setMaskMap(coord, 0);
                Game.objectManager.removeObject(mask);
                i--;
            }
        }
    }

    public static void resetAttackMask(){
        for (int i = 0; i < Game.objectManager.list.size(); i++) {
            HighlightMask mask;
            try {
                mask = (HighlightMask) Game.objectManager.list.get(i);
            } catch (ClassCastException e){
                continue;
            }
            
            if (mask.maskType == MaskType.ATTACKMASK) {
                Coord coord = mask.getCoord();
                setMaskMap(coord, 0);
                Game.objectManager.removeObject(mask);
                i--;
            }
        }
    }

    public static void resetAttackConfirmMask(){
        for (int i = 0; i < Game.objectManager.list.size(); i++) {
            HighlightMask mask;
            try {
                mask = (HighlightMask) Game.objectManager.list.get(i);
            } catch (ClassCastException e){
                continue;
            }
            
            if (mask.maskType == MaskType.ATTACKCONFIRMMASK) {
                Game.objectManager.removeObject(mask);
                i--;
            }
        }
    }

    public static void resetAllMask(){
        for (int i = 0; i < Game.objectManager.list.size(); i++) {
            HighlightMask mask;
            try {
                mask = (HighlightMask) Game.objectManager.list.get(i);
            } catch (ClassCastException e){
                continue;
            }
            Coord coord = mask.getCoord();
            setMaskMap(coord, 0);
            Game.objectManager.removeObject(mask);
            i--;
        }
    }

    public static void shiftMoveableMask(Coord coord, Coord newCoord){
        ArrayList<GameObject> tempList = Game.objectManager.getObjectsByCoord(coord);
        HighlightMask mask = null;
        for(GameObject object:tempList){
            try {
                mask = (HighlightMask) object;
            } catch (ClassCastException e){
                continue;
            }
            if(mask.maskType == MaskType.ACTIVEMASK){
                break;
            }
        }
        if(mask!=null){
            mask.setCoord(newCoord);
            setMaskMap(coord, 0);
        }
    }

    public static void removeMask(Coord coord){
        HighlightMask mask = getMaskObject(coord);
        if(mask!=null){
            Game.objectManager.removeObject(mask);
            setMaskMap(coord, 0);
            removeMask(coord);
        }
    }
}
