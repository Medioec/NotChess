package com.game.main;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

public class Archer extends Units {

    public Archer(int x, int y, ObjectType type, int player) {
        super(x, y, type, player);
        initialize(80, 80, 20, 100, 10, 3, 3, "Archer", "A", UnitType.ARCHER);
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
        g.drawString("A", x + 11, y + 30);
    }

}
