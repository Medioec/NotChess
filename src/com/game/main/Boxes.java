package com.game.main;

import java.awt.Graphics2D;
import java.awt.Color;

public class Boxes extends GameObject {

    protected Color bgColor;

    public enum Fill {
        Fill,
        Nofill,
    };

    public enum Edges {
        Default,
        Rounded
    };

    protected Fill fill;
    protected Edges edges;
    protected int arcWidth;
    protected int arcHeight;

    public Boxes(int x, int y, int width, int height, ObjectType type) {
        super(x, y, width, height, type);
        Game.objectManager.addObject(this);
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(bgColor);
        if (this.fill == Fill.Fill) {
            if (this.edges == Edges.Default) {
                g.fillRect(x, y, width, height);
            } else {
                g.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
            }
        } else {
            if (this.edges == Edges.Default) {
                g.drawRect(x, y, width, height);
            } else {
                g.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
            }
        }

    }

    public void setBGColor(Color color) {
        this.bgColor = color;
    }

    public void setFill(Fill fill) {
        this.fill = fill;
    }

    public void setEdges(Edges edges) {
        this.edges = edges;
    }

    public void setEdges(Edges edges, int arcWidth, int arcHeight) {
        this.edges = edges;
        this.arcHeight = arcHeight;
        this.arcWidth = arcWidth;
    }
}
