package model;

import java.awt.Graphics;

public abstract class ElementoGioco {
    protected int x;
    protected int y;
    public static final int RAGGIO = 25;

    public ElementoGioco(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void disegna(Graphics g);

    public abstract boolean contienePunto(int mouseX, int mouseY);

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }
}
