package com.game.main;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class King extends Units {

    public King(int x, int y, ObjectType type, int player) {
        super(x, y, type, player);
        this.hp = 100;
        this.maxHp = 100;
        this.atk = 25;
        this.hit = 110;
        this.avd = 15;
        this.mvmt = 3;
        this.range = 1;
        this.name = "King";
        this.label = "KG";
        this.type = Type.KING;
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
        Font font = new Font("Calibri", Font.BOLD, 25);
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString("KG", x + 6, y + 28);

    }

}
