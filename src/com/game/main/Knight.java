package com.game.main;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Knight extends Units {

    public Knight(int x, int y, ObjectType type, int player) {
        super(x, y, type, player);
        this.hp = 90;
        this.maxHp = 90;
        this.atk = 25;
        this.hit = 100;
        this.avd = 5;
        this.mvmt = 5;
        this.range = 1;
        this.name = "Knight";
        this.label = "K";
        this.type = Type.KNIGHT;
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
        g.drawString("K", x + 11, y + 30);

    }

}
