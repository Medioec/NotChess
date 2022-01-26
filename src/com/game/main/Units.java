package com.game.main;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

public abstract class Units extends GameObject {

    private int hp, atk, hit, avd, mvmt, range, level;
    private int maxHp;
    private int player;
    private boolean canMove;
    private String name;
    private String label;
    private UnitType type;


    public enum UnitType{
        SOLDIER,
        KNIGHT,
        ARCHER,
        MEDIC,
        KING
    };

    public Units(int squarex, int squarey, ObjectType type, int player) {
        super(type);
        canMove = false;
        level = 1;
        this.player = player;
        this.height = 40;
        this.width = 40;
        Coord coord = new Coord(squarex, squarey);
        setCoord(coord);
        Game.objectManager.addUnit(this);
    }

    @Override
    public abstract void tick();

    @Override
    public void render(Graphics2D g){
        if (player == 1) {
            g.setColor(new Color(0f, 0f, 0.9f));
        } else {
            g.setColor(new Color(0.7f, 0f, 0f));
        }
        g.fillOval(x, y, width, height);
    }

    public void setCoord(Coord coord) {
        int unitmid = height / 2;
        int squaremid = Map.SQUARELENGTH / 2;
        Map map = Game.map;
        x = map.x + squaremid - unitmid + Map.SQUARELENGTH * coord.x;
        y = map.y + squaremid - unitmid + Map.SQUARELENGTH * coord.y;
    }

    public Coord getCoord() {
        Map map = Game.map;
        Coord coord = new Coord((x - map.x) / 50, (y - map.y) / 50);
        return coord;
    }

    public void initialize(int hp, int maxHp, int atk, int hit, int avd, int mvmt, int range, String name, String label, UnitType type ){
        this.hp = hp;
        this.maxHp = maxHp;
        this.atk = atk;
        this.hit = hit;
        this.avd = avd;
        this.mvmt = mvmt;
        this.range = range;
        this.name = name;
        this.label = label;
        this.type = type;
    }

    public int getPlayer(){
        return player;
    }

    public String getName(){
        return name;
    }

    public int getHp(){
        return hp;
    }

    public void setHp(int hp){
        this.hp = hp;
    }

    public int getAtk(){
        return atk;
    }
    
    public int getHit(){
        return hit;
    }

    public int getAvd(){
        return avd;
    }

    public int getMvmt(){
        return mvmt;
    }

    public int getRange(){
        return range;
    }

    public int getLevel(){
        return level;
    }

    public void increaseUnitLevel(){
        level++;
        maxHp += 5;
        atk += 5;
        hit += 5;
        avd += 5;
    }

    public int getMaxHp(){
        return maxHp;
    }

    public boolean getMoveable(){
        return canMove;
    }

    public UnitType getUnitType(){
        return type;
    }

    public void setMoveable(boolean bool){
        canMove = bool;
    }
}
