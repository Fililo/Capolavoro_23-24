package model;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Bomba extends ElementoGioco {
    private Image immagineBomba;

    public Bomba(int x, int y) {
        super(x, y);
        loadImage("../images/bomba.png");
    }

    private void loadImage(String path) {
        ImageIcon ii = new ImageIcon(getClass().getResource(path));
        immagineBomba = ii.getImage();
    }

    @Override
    public void disegna(Graphics g) {
        if (immagineBomba != null) {
            g.drawImage(immagineBomba, x - RAGGIO, y - RAGGIO, null);
        } else {
            System.err.println("Immagine del frutto non caricata correttamente.");
        }
    }

    @Override
    public boolean contienePunto(int mouseX, int mouseY) {
        return mouseX >= x - RAGGIO && mouseX <= x + RAGGIO &&
               mouseY >= y - RAGGIO && mouseY <= y + RAGGIO;
    }
}
