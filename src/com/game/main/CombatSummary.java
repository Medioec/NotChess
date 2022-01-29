package com.game.main;

import java.awt.Graphics2D;

import com.game.main.Units.UnitType;

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
    public void render(Graphics2D g) {
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
        if(activeUnit.getUnitType() == UnitType.ARCHER && targetUnit.getUnitType() == UnitType.SOLDIER || 
            activeUnit.getUnitType() == UnitType.KNIGHT && targetUnit.getUnitType() == UnitType.ARCHER ||
            activeUnit.getUnitType() == UnitType.SOLDIER && targetUnit.getUnitType() == UnitType.KNIGHT){
            modifier1 = LogicManager.EFFECTIVE_DAMAGE_MODIFIER;
            info = "Dealing " + LogicManager.EFFECTIVE_DAMAGE_MODIFIER + "x DMG";
        }
        if(targetUnit.getUnitType() == UnitType.ARCHER && activeUnit.getUnitType() == UnitType.SOLDIER || 
            targetUnit.getUnitType() == UnitType.KNIGHT && activeUnit.getUnitType() == UnitType.ARCHER ||
            targetUnit.getUnitType() == UnitType.SOLDIER && activeUnit.getUnitType() == UnitType.KNIGHT){
            modifier2 = LogicManager.EFFECTIVE_DAMAGE_MODIFIER;
            info = "Taking " + LogicManager.EFFECTIVE_DAMAGE_MODIFIER + "x DMG";
        }
        int damageDone = (int)( (double) (activeUnit.getAtk() + LogicManager.INITIATE_ATK_BONUS) * modifier1);
        int damageReceived = (int)( (double)targetUnit.getAtk() * modifier2);
        if(activeUnit.getPlayer() != targetUnit.getPlayer()){
            if(canCounter){
                int final1 = activeUnit.getHp() - damageReceived;
                int final2 = targetUnit.getHp() - damageDone;
                if(final1 < 0) final1 = 0;
                if(final2 < 0) final2 = 0;
                name1 = activeUnit.getName() + " attacks " + targetUnit.getName();
                hp1 = "HP: " + activeUnit.getHp() + "->" + final1;
                atk1 = "ATK: " + damageDone;
                hit1 = "HIT%: " + (activeUnit.getHit() - targetUnit.getAvd());
                hp2 = "HP: " + targetUnit.getHp() + "->" + final2;
                atk2 = "ATK: " + damageReceived;
                hit2 = "HIT%: " + (targetUnit.getHit() - activeUnit.getAvd());
            }
            else {
                info = "";
                int final2 = targetUnit.getHp() - damageDone;
                if(final2 < 0) final2 = 0;
                name1 = activeUnit.getName() + " attacks " + targetUnit.getName();
                hp1 = "HP: " + activeUnit.getHp();
                atk1 = "ATK: " + damageDone;
                hit1 = "HIT%: " + (activeUnit.getHit() - targetUnit.getAvd());
                hp2 = "HP: " + targetUnit.getHp() + "->" + final2;
                atk2 = "ATK: " + "---";
                hit2 = "HIT%: " + "---";
            }
        }
        else if(activeUnit.getUnitType() == UnitType.MEDIC) {
            int final2 = targetUnit.getHp() + (activeUnit.getAtk() + LogicManager.INITIATE_ATK_BONUS) * 2;
            if(final2 > targetUnit.getMaxHp()) final2 = targetUnit.getMaxHp();
            name1 = activeUnit.getName() + " heals " + targetUnit.getName();
            hp1 = "HP: " + activeUnit.getHp();
            atk1 = "Heal: " + (activeUnit.getAtk() + LogicManager.INITIATE_ATK_BONUS) * 2;
            hit1 = "HIT%: ---";
            hp2 = "HP: " + targetUnit.getHp() + "->" + final2;
            atk2 = "ATK: ---";
            hit2 = "HIT%: ---";
        }
        mode = displayMode.PRE;
    }

    public static void setPostCombatData(Units activeUnit, Units targetUnit, String result1, String result2){
        name1 = activeUnit.getName();
        name2 = targetUnit.getName();
        if(result1 == "Miss"){
            hp1 = String.valueOf(activeUnit.getHp());
        }
        if(result2 == "Miss"){
            hp2 = String.valueOf(targetUnit.getHp());
        }
        CombatSummary.result1 = result1;
        CombatSummary.result2 = result2;
        mode = displayMode.POST;
        info = "";
    }
}
