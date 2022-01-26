package com.game.main;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

public class UnitInfoDisplay extends GameObject{
    public static String name;
    public static String level;
    public static String hp;
    public static String atk;
    public static String hit;
    public static String avd;
    public static String mvmt;
    public static String range;

    public UnitInfoDisplay(ObjectType type) {
        super(type);
        name = "Name: ";
        level = "Level: ";
        hp = "HP: ";
        atk = "ATK: ";
        hit = "HIT: ";
        avd = "AVD: ";
        mvmt = "MVMT: ";
        range = "RNG: ";
        Game.objectManager.addObject(this);
    }

    @Override
    public void tick() {        
    }

    @Override
    public void render(Graphics2D g) {
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

    public void initialize(){
        name = "Name: ";
        level = "Level: ";
        hp = "HP: ";
        atk = "ATK: ";
        hit = "HIT: ";
        avd = "AVD: ";
        mvmt = "MVMT: ";
        range = "RNG: ";
    }
    
    public void setDisplayStrings(Units unit){
        if(unit != null){
            name = "Name: " + unit.getName();
            level = "Level: " + unit.getLevel();
            hp = "HP: " + unit.getHp() + "/" + unit.getMaxHp();
            atk = "ATK: " + unit.getAtk();
            hit = "HIT: " + unit.getHit();
            avd = "AVD: " + unit.getAvd();
            mvmt = "MVMT: " + unit.getMvmt();
            range = "RNG: " + unit.getRange();
        }
    }
}
