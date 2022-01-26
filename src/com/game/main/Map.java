package com.game.main;

import java.awt.Color;
import java.awt.Graphics2D;

public class Map extends GameObject {
    private int sizex;
    private int sizey;
    private int[][][] mapData;
    private int mapID;
    public static final int SQUARELENGTH = 50;

    public Map(int x, int y, int sizex, int sizey, ObjectType type) {
        super(x, y, type);
        setMapSize(sizex, sizey);
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
    public void render(Graphics2D g) {
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

    public void setMapSize(int sizex, int sizey){
        this.sizex = sizex;
        this.sizey = sizey;
        mapData = new int[sizex][sizey][2];
    }

    public int getSizeX(){
        return sizex;
    }

    public int getSizeY(){
        return sizey;
    }

    public void setMapData(Coord coord, int unitID, int terrainID){
        mapData[coord.x][coord.y][0] = unitID;
        mapData[coord.x][coord.y][1] = terrainID;
    }

    public int[] getMapData(Coord coord){
        return mapData[coord.x][coord.y];
    }

    public void setTerrainData(Coord coord, int terrainID){
        mapData[coord.x][coord.y][0] = terrainID;
    }

    public void setUnitData(Coord coord, int unitID){
        mapData[coord.x][coord.y][0] = unitID;
    }

    public int getTerrainID(Coord coord){
        return mapData[coord.x][coord.y][1];
    }

    public int getUnitID(Coord coord){
        return mapData[coord.x][coord.y][0];
    }
}
