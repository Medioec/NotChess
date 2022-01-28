package com.game.main;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

public class Medic extends Units {

    public Medic(int squarex, int squarey, ObjectType type, int player) {
        super(squarex, squarey, type, player);
        initialize(70, 70, 10, 100, 10, 3, 1, "Medic", "M", UnitType.MEDIC);
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        g.drawString("M", x + 7, y + 30);

    }

}
