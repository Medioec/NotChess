package com.game.main;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Button extends GameObject {

    protected String Label = "";
    protected Color bgColor, fontColor;
    protected Font font;
    protected int labelx, labely;


    public Button(ObjectType type){
        super(type);
        x = 0;
        y = 0;
        width = 0;
        height = 0;
    }
    public Button(int x, int y, int width, int height, ObjectType type) {
        super(x, y, width, height, type);
        Game.objectManager.addObject(this);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(bgColor);
        g.fillRect(x, y, width, height);
        g.setColor(fontColor);
        g.setFont(font);
        g.drawString(Label, labelx, labely);
    }

    @Override
    public void tick() {
    }

    public void setLabel(String Label, String font, int fontSize, Color fontColor) {
        this.Label = Label;
        this.font = new Font(font, Font.BOLD, fontSize);
        this.fontColor = fontColor;
    }

    public void setBGColor(Color color) {
        this.bgColor = color;
    }

    public void setLabelPos(int labelx, int labely) {
        this.labelx = labelx;
        this.labely = labely;
    }

}
