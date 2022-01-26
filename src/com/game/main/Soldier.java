package com.game.main;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

public class Soldier extends Units {

    public Soldier(int x, int y, ObjectType type, int player) {
        super(x, y, type, player);
        initialize(100, 100, 20, 100, 10, 3, 1, "Soldier", "S", UnitType.SOLDIER);
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        Font font = new Font("Calibri", Font.BOLD, 30);
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString("S", x + 13, y + 30);

    }

}
