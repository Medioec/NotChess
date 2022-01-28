package com.game.main;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

public class King extends Units {

    public King(int x, int y, ObjectType type, int player) {
        super(x, y, type, player);
        initialize(100, 100, 25, 110, 15, 3, 1, "King", "KG", UnitType.KING);
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        Font font = new Font("Calibri", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("KG", x + 6, y + 28);

    }

}
