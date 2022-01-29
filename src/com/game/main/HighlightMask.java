package com.game.main;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Color;

public class HighlightMask extends GameObject{
    public static int[][] mapMaskData;
    public MaskType maskType;
    private static final int MAXMASK = 255;

    public HighlightMask(Coord coords, MaskType maskType) {
        super(coords);
        this.height = 50;
        this.width = 50;
        this.type = ObjectType.GameObject;
        this.maskType = maskType;
        setCoord(coords);
        addToMaskMap(coords, maskType);
        Game.objectManager.addObject(this);
    }

    public static enum MaskType{
        MOVEMASK,
        MOVEABLEMASK,
        ACTIVEMASK,
        ATTACKMASK,
        ATTACKCONFIRMMASK,
        NONE;
    }

    @Override
    public void tick() {        
    }

    @Override
    public void render(Graphics2D g) {
        if(this.visible == true){
            if(maskType == MaskType.MOVEMASK){
                g.setColor(new Color(0f, 0.8f, 1f));
                g.fillRect(x, y, width, height);
                g.setColor(new Color(0f, 0f, 0f, 0.1f));
                g.drawRect(x, y, width - 1, height - 1);
                //g.fillRect(x + 2, y + 2, width - 4, height - 4);
            } else if(maskType == MaskType.MOVEABLEMASK) {
                g.setColor(new Color(1f, 1f, 1f, 0.8f));
                g.fillRect(x, y, width, height);
                g.setColor(new Color(0f, 0f, 0f, 0.1f));
                g.drawRect(x, y, width - 1, height - 1);
                //g.fillRect(x + 2, y + 2, width - 4, height - 4);
            } else if(maskType == MaskType.ACTIVEMASK) {
                g.setColor(new Color(0.8f, 0.8f, 0.2f));
                g.fillRect(x, y, width, height);
                //g.fillRect(x + 2, y + 2, width - 4, height - 4);
            } else if(maskType == MaskType.ATTACKMASK) {
                g.setColor(new Color(0.7f, 0.5f, 0.5f));
                g.fillRect(x, y, width, height);
                g.setColor(new Color(0f, 0f, 0f, 0.1f));
                g.drawRect(x, y, width - 1, height - 1);
                //g.fillRect(x + 2, y + 2, width - 4, height - 4);
            } else if(maskType == MaskType.ATTACKCONFIRMMASK) {
                g.setColor(new Color(1f, 0.7f, 0.3f));
                g.fillRect(x, y, width, height);
                g.setColor(new Color(0f, 0f, 0f, 0.1f));
                g.drawRect(x, y, width - 1, height - 1);
                //g.fillRect(x + 2, y + 2, width - 4, height - 4);
            }
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

    public static int enumMaskType(MaskType maskType){
        int n;
        switch(maskType){
            case MOVEMASK:
            n = 1;
            break;
            case MOVEABLEMASK:
            n = 2;
            break;
            case ATTACKMASK:
            n = 4;
            break;
            case ACTIVEMASK:
            n = 8;
            break;
            case ATTACKCONFIRMMASK:
            n = 16;
            break;
            default:
            n = 0;
            break;
        }
        return n;
    }

    public static MaskType enumMaskNumber(int n){
        MaskType type;
        switch(n){
            case 1:
            type = MaskType.MOVEMASK;
            break;
            case 2:
            type = MaskType.MOVEABLEMASK;
            break;
            case 4:
            type = MaskType.ATTACKMASK;
            break;
            case 8:
            type = MaskType.ACTIVEMASK;
            break;
            case 16:
            type = MaskType.ATTACKCONFIRMMASK;
            break;
            default:
            type = MaskType.NONE;
            break;
        }
        return type;
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

    public static void setMaskMap(Coord coord, MaskType maskType){
        int n = enumMaskType(maskType);
        mapMaskData[coord.x][coord.y] = n;
    }

    public static void setMaskMap(Coord coord, int n){
        mapMaskData[coord.x][coord.y] = n;
    }

    public static void addToMaskMap(Coord coord, MaskType maskType){
        int n = enumMaskType(maskType);
        mapMaskData[coord.x][coord.y] = mapMaskData[coord.x][coord.y] | n;
    }

    public static void removeFromMaskMap(Coord coord, MaskType maskType){
        int n = enumMaskType(maskType);
        n = MAXMASK ^ n;
        mapMaskData[coord.x][coord.y] = mapMaskData[coord.x][coord.y] & n;
    }

    public static boolean checkMaskMap(Coord coord, MaskType maskType){
        int n = enumMaskType(maskType);
        int mask = mapMaskData[coord.x][coord.y] & n;
        if (mask == enumMaskType(maskType)) {
            return true;
        }
        else {
            return false;
        }
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
                if(checkMaskMap(coord, MaskType.MOVEMASK)) removeFromMaskMap(coord, MaskType.MOVEMASK);
                Game.objectManager.removeObject(mask);
                i--;
            }
            if (mask.maskType == MaskType.ACTIVEMASK) {
                Coord coord = mask.getCoord();
                if(checkMaskMap(coord, MaskType.MOVEMASK)) removeFromMaskMap(coord, MaskType.MOVEMASK);
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
                if(checkMaskMap(coord, MaskType.ATTACKMASK)) removeFromMaskMap(coord, MaskType.ATTACKMASK);
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
            setMaskMap(coord, MaskType.NONE);
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
            setMaskMap(newCoord, getMaskMap(coord));
            setMaskMap(coord, MaskType.NONE);
        }
    }

    public static void removeMask(Coord coord){
        HighlightMask mask = getMaskObject(coord);
        while(mask!=null){
            Game.objectManager.removeObject(mask);
            removeMask(coord);
            mask = getMaskObject(coord);
        }
        setMaskMap(coord, MaskType.NONE);
    }
}
