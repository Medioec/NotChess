package com.game.main;

import java.awt.Color;
import java.awt.Graphics2D;

public class GameHelp extends GameObject{
    public String help1 = "Defeat the enemy King";
    public String help2 = LogicManager.EFFECTIVE_DAMAGE_MODIFIER + "x Damage:";
    public String help3 = "Archer -> Soldier";
    public String help4 = "Soldier -> Knight";
    public String help5 = "Knight -> Archer";

    public GameHelp(ObjectType type) {
        super(type);
        Game.objectManager.addObject(this);
    }

    @Override
    public void tick() {        
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.BLACK);
        x = commonx + Game.WIDTH / 64 * 51;
        y = commony + Game.HEIGHT / 32 * 16;
        g.drawString(help1, x, y);
        x = commonx + Game.WIDTH / 64 * 51;
        y = commony + Game.HEIGHT / 32 * 17;
        g.drawString(help2, x, y);
        x = commonx + Game.WIDTH / 64 * 51;
        y = commony + Game.HEIGHT / 32 * 18;
        g.drawString(help3, x, y);
        x = commonx + Game.WIDTH / 64 * 51;
        y = commony + Game.HEIGHT / 32 * 19;
        g.drawString(help4, x, y);
        x = commonx + Game.WIDTH / 64 * 51;
        y = commony + Game.HEIGHT / 32 * 20;
        g.drawString(help5, x, y);
    }
    
}
