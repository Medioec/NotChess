package com.game.main;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Soldier extends Units {

    public Soldier(int x, int y, ObjectType type, int player) {
        super(x, y, type, player);
        this.hp = 100;
        this.maxHp = 100;
        this.atk = 20;
        this.hit = 100;
        this.avd = 10;
        this.mvmt = 3;
        this.range = 1;
        this.name = "Soldier";
        this.label = "S";
        this.type = Type.SOLDIER;
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
        g.drawString("S", x + 13, y + 30);

    }

}
