package com.game.main;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public abstract class Units extends GameObject {

    public int hp, atk, hit, avd, mvmt, range, level;
    public int maxHp;
    public int player;
    public boolean canMove = false;
    public String name;
    public String label;
    public Type type;


    public enum Type{
        SOLDIER,
        KNIGHT,
        ARCHER,
        MEDIC,
        KING
    };

    public Units(int squarex, int squarey, ObjectType type, int player) {
        super(type);
        this.player = player;
        this.height = 40;
        this.width = 40;
        Coord coord = new Coord(squarex, squarey);
        setCoord(coord);
        this.level = 1;
        Game.objectManager.addUnit(this);
    }

    @Override
    public abstract void tick();

    @Override
    public void render(Graphics g){
        if (player == 1) {
            g.setColor(Color.BLUE);
        } else {
            g.setColor(Color.RED);
        }
        g.fillOval(x, y, width, height);
        Font font = new Font("Calibri", Font.BOLD, 30);
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(label, x + 13, y + 30);
        if(canMove){
            g.setColor(Color.WHITE);

        }
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

}
