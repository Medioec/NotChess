package com.game.main;

import java.awt.Graphics2D;

public abstract class GameObject {

    public int x, y, width, height, id;
    public int commonx, commony;
    public ObjectType type;
    static int count = 0;
    public boolean visible = true;

    public GameObject(ObjectType type) {
        this.type = type;
        this.id = generateId();
    }

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
        this.id = generateId();
    }

    public GameObject(Coord coords) {
        setCoord(coords);
    }

    public GameObject(int x, int y, ObjectType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.id = generateId();
    }

    public GameObject(int x, int y, int width, int height, ObjectType type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.id = generateId();
    }

    public abstract void tick();

    public abstract void render(Graphics2D g);

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPos(int x , int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ObjectType getType() {
        return type;
    }

    public void setType(ObjectType type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int generateId() {
        return count++;
    }

    public int getId() {
        return id;
    }

    public void setCoord(Coord coords) {
        x = Game.map.x + Map.SQUARELENGTH * coords.x;
        y = Game.map.y + Map.SQUARELENGTH * coords.y;
    }

    public Coord getCoord() {
        Map map = Game.map;
        Coord coord = new Coord((x - map.x) / 50, (y - map.y) / 50);
        return coord;
    }

    public void toggleVisible() {
        visible = visible ^ true;
    }

}
