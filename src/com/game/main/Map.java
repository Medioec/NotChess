package com.game.main;

import java.awt.Color;
import java.awt.Graphics;

public class Map extends GameObject {
    public int sizex;
    public int sizey;
    public static int[][][] mapData;
    public static int mapID;
    public static final int SQUARELENGTH = 50;

    public Map(int x, int y, int sizex, int sizey, ObjectType type) {
        super(x, y, type);
        this.sizex = sizex;
        this.sizey = sizey;
        this.width = sizex * SQUARELENGTH;
        this.height = sizey * SQUARELENGTH;
        mapID = this.id;
        HighlightMask.initializeMaskMap(sizex, sizey);
        Game.objectManager.addObject(this);
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(x, y, width, height);
        g.setColor(Color.black);
        for (int i = 0; i + x < x + width; i += 50) {
            for (int j = 0; j + y < y + height; j += 50) {
                g.drawRect(i + x, j + y, SQUARELENGTH, SQUARELENGTH);
            }
        }

    }

    public Coord convToCoord(int mx, int my) {
        Coord coord = new Coord( (mx - x) / 50, (my - y) / 50 );
        return coord;
    }

    public static void setMapData(Coord coord, int unitID, int terrainID){
        mapData[coord.x][coord.y][0] = unitID;
        mapData[coord.x][coord.y][1] = terrainID;
    }

    public static void setTerrainData(Coord coord, int terrainID){
        mapData[coord.x][coord.y][0] = terrainID;
    }

    public static void setUnitData(Coord coord, int unitID){
        mapData[coord.x][coord.y][0] = unitID;
    }

    public static int getTerrainID(Coord coord){
        return mapData[coord.x][coord.y][1];
    }

    public static int getUnitID(Coord coord){
        return mapData[coord.x][coord.y][0];
    }
}
