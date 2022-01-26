package com.game.main;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

public class Knight extends Units {

    public Knight(int x, int y, ObjectType type, int player) {
        super(x, y, type, player);
        initialize(90, 90, 25, 100, 5, 5, 1, "Knight", "K", UnitType.KNIGHT);
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
        g.drawString("K", x + 11, y + 30);

    }

}
