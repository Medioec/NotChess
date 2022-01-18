package com.game.main;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Archer extends Units {

    public Archer(int x, int y, ObjectType type, int player) {
        super(x, y, type, player);
        this.hp = 80;
        this.maxHp = 80;
        this.atk = 20;
        this.hit = 100;
        this.avd = 10;
        this.mvmt = 3;
        this.range = 3;
        this.name = "Archer";
        this.label = "A";
        this.type = Type.ARCHER;
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
        g.drawString("A", x + 11, y + 30);
    }

}
