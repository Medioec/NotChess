package com.game.main;

import java.awt.Graphics;

import com.game.main.Units.Type;

import java.awt.Color;
import java.awt.Font;

public class CombatSummary extends GameObject{
    public static String name1 = "";
    public static String hp1 = "";
    public static String atk1 = "";
    public static String hit1 = "";
    public static String name2 = "";
    public static String hp2 = "";
    public static String atk2 = "";
    public static String hit2 = "";
    public static String result1 = "";
    public static String result2 = "";
    public static displayMode mode = displayMode.PRE;
    public static String info = "";

    public enum displayMode{
        PRE,
        POST
    };

    public CombatSummary(ObjectType type) {
        super(type);
        this.visible = false;
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
            if (mode == displayMode.PRE) {
                g.setFont(font);
                x = Game.WIDTH / 64 * 52;
                y = Game.HEIGHT / 32 * 6;
                g.drawString(name1, x, y);
                x = Game.WIDTH / 64 * 51;
                y = Game.HEIGHT / 32 * 8;
                g.drawString(hp1, x, y);
                x = Game.WIDTH / 64 * 51;
                y = Game.HEIGHT / 32 * 10;
                g.drawString(atk1, x, y);
                x = Game.WIDTH / 64 * 51;
                y = Game.HEIGHT / 32 * 12;
                g.drawString(hit1, x, y);
                x = Game.WIDTH / 64 * 57;
                y = Game.HEIGHT / 32 * 8;
                g.drawString(hp2, x, y);
                x = Game.WIDTH / 64 * 57;
                y = Game.HEIGHT / 32 * 10;
                g.drawString(atk2, x, y);
                x = Game.WIDTH / 64 * 57;
                y = Game.HEIGHT / 32 * 12;
                g.drawString(hit2, x, y);
                x = Game.WIDTH / 64 * 53;
                y = Game.HEIGHT / 32 * 13;
                g.setColor(Color.RED);
                g.drawString(info, x, y);
            } else {
                g.setFont(font);
                x = Game.WIDTH / 64 * 51;
                y = Game.HEIGHT / 32 * 6;
                g.drawString(name1, x, y);
                x = Game.WIDTH / 64 * 51;
                y = Game.HEIGHT / 32 * 8;
                g.drawString(hp1, x, y);
                x = Game.WIDTH / 64 * 51;
                y = Game.HEIGHT / 32 * 10;
                g.drawString(result1, x, y);
                x = Game.WIDTH / 64 * 57;
                y = Game.HEIGHT / 32 * 6;
                g.drawString(name2, x, y);
                x = Game.WIDTH / 64 * 57;
                y = Game.HEIGHT / 32 * 8;
                g.drawString(hp2, x, y);
                x = Game.WIDTH / 64 * 57;
                y = Game.HEIGHT / 32 * 10;
                g.drawString(result2, x, y);
            }
        }
    }
    
    public static void setPreCombatData(Units activeUnit, Units targetUnit, boolean canCounter){
        double modifier1 = 1;
        double modifier2 = 1;
        info = "";
        if(activeUnit.type == Type.ARCHER && targetUnit.type == Type.SOLDIER || 
            activeUnit.type == Type.KNIGHT && targetUnit.type == Type.ARCHER ||
            activeUnit.type == Type.SOLDIER && targetUnit.type == Type.KNIGHT){
            modifier1 = LogicManager.EFFECTIVE_DAMAGE_MODIFIER;
            info = "Dealing " + LogicManager.EFFECTIVE_DAMAGE_MODIFIER + "x DMG";
        }
        if(targetUnit.type == Type.ARCHER && activeUnit.type == Type.SOLDIER || 
            targetUnit.type == Type.KNIGHT && activeUnit.type == Type.ARCHER ||
            targetUnit.type == Type.SOLDIER && activeUnit.type == Type.KNIGHT){
            modifier2 = LogicManager.EFFECTIVE_DAMAGE_MODIFIER;
            info = "Taking " + LogicManager.EFFECTIVE_DAMAGE_MODIFIER + "x DMG";
        }
        int damageDone = (int)( (double) (activeUnit.atk + LogicManager.INITIATE_ATK_BONUS) * modifier1);
        int damageReceived = (int)( (double)targetUnit.atk * modifier2);
        if(activeUnit.player != targetUnit.player){
            if(canCounter){
                int final1 = activeUnit.hp - damageReceived;
                int final2 = targetUnit.hp - damageDone;
                if(final1 < 0) final1 = 0;
                if(final2 < 0) final2 = 0;
                name1 = activeUnit.name + " attacks " + targetUnit.name;
                hp1 = "HP: " + activeUnit.hp + "->" + final1;
                atk1 = "ATK: " + damageDone;
                hit1 = "HIT%: " + (activeUnit.hit - targetUnit.avd);
                hp2 = "HP: " + targetUnit.hp + "->" + final2;
                atk2 = "ATK: " + damageReceived;
                hit2 = "HIT%: " + (targetUnit.hit - activeUnit.avd);
            }
            else {
                if (info == "Taking 1.3x DMG") info = "";
                int final2 = targetUnit.hp - damageDone;
                if(final2 < 0) final2 = 0;
                name1 = activeUnit.name + " attacks " + targetUnit.name;
                hp1 = "HP: " + activeUnit.hp;
                atk1 = "ATK: " + damageDone;
                hit1 = "HIT%: " + (activeUnit.hit - targetUnit.avd);
                hp2 = "HP: " + targetUnit.hp + "->" + final2;
                atk2 = "ATK: " + "---";
                hit2 = "HIT%: " + "---";
            }
        }
        else if(activeUnit.type == Type.MEDIC) {
            int final2 = targetUnit.hp + (activeUnit.atk + LogicManager.INITIATE_ATK_BONUS) * 2;
            if(final2 > targetUnit.maxHp) final2 = targetUnit.maxHp;
            name1 = activeUnit.name + " heals " + targetUnit.name;
            hp1 = "HP: " + activeUnit.hp;
            atk1 = "Heal: " + (activeUnit.atk + LogicManager.INITIATE_ATK_BONUS) * 2;
            hit1 = "HIT%: ---";
            hp2 = "HP: " + targetUnit.hp + "->" + final2;
            atk2 = "ATK: ---";
            hit2 = "HIT%: ---";
        }
        mode = displayMode.PRE;
    }

    public static void setPostCombatData(Units activeUnit, Units targetUnit, String result1, String result2){
        name1 = activeUnit.name;
        name2 = targetUnit.name;
        if(result1 == "Miss"){
            hp1 = String.valueOf(activeUnit.hp);
        }
        if(result2 == "Miss"){
            hp2 = String.valueOf(targetUnit.hp);
        }
        CombatSummary.result1 = result1;
        CombatSummary.result2 = result2;
        mode = displayMode.POST;
        info = "";
    }
}
