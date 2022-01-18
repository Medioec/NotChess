package com.game.main;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Medic extends Units {

    public Medic(int squarex, int squarey, ObjectType type, int player) {
        super(squarex, squarey, type, player);
        this.hp = 70;
        this.maxHp = 70;
        this.atk = 10;
        this.hit = 100;
        this.avd = 10;
        this.mvmt = 3;
        this.range = 1;
        this.name = "Medic";
        this.label = "M";
        this.type = Type.MEDIC;
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        if (player == 1) {
            g.setColor(Color.BLUE);
        } else {
            g.setColor(Color.RED);
        }
        g.fillOval(x, y, width, height);
        Font font = new Font("Calibri", Font.BOLD, 30);
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString("M", x + 8, y + 30);

    }

}
