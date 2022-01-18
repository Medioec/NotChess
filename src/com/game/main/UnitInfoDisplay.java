package com.game.main;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class UnitInfoDisplay extends GameObject{
    public static String name = "Name: ";
    public static String level = "Level: ";
    public static String hp = "HP: ";
    public static String atk = "ATK: ";
    public static String hit = "HIT: ";
    public static String avd = "AVD: ";
    public static String mvmt = "MVMT: ";
    public static String range = "RNG: ";

    public UnitInfoDisplay(ObjectType type) {
        super(type);
        Game.objectManager.addObject(this);
    }

    @Override
    public void tick() {        
    }

    @Override
    public void render(Graphics g) {
        if(visible){
            g.setColor(Color.BLACK);        
            Font font = new Font("Calibri", Font.BOLD, 18);
            g.setFont(font);
            x = Game.WIDTH / 64 * 51;
            y = Game.HEIGHT / 32 * 6;
            g.drawString(name, x, y);
            x = Game.WIDTH / 64 * 51;
            y = Game.HEIGHT / 32 * 8;
            g.drawString(hp, x, y);
            x = Game.WIDTH / 64 * 51;
            y = Game.HEIGHT / 32 * 10;
            g.drawString(atk, x, y);
            x = Game.WIDTH / 64 * 51;
            y = Game.HEIGHT / 32 * 12;
            g.drawString(hit, x, y);
            x = Game.WIDTH / 64 * 57;
            y = Game.HEIGHT / 32 * 6;
            g.drawString(level, x, y);
            x = Game.WIDTH / 64 * 57;
            y = Game.HEIGHT / 32 * 8;
            g.drawString(avd, x, y);
            x = Game.WIDTH / 64 * 57;
            y = Game.HEIGHT / 32 * 10;
            g.drawString(mvmt, x, y);
            x = Game.WIDTH / 64 * 57;
            y = Game.HEIGHT / 32 * 12;
            g.drawString(range, x, y);
        }
    }
    
    public static void setDisplayStrings(Units unit){
        if(unit != null){
            name = "Name: " + unit.name;
            level = "Level: " + unit.level;
            hp = "HP: " + unit.hp + "/" + unit.maxHp;
            atk = "ATK: " + unit.atk;
            hit = "HIT: " + unit.hit;
            avd = "AVD: " + unit.avd;
            mvmt = "MVMT: " + unit.mvmt;
            range = "RNG: " + unit.range;
        }
    }
}
