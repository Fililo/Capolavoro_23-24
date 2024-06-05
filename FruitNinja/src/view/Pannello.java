package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import controller.FruitController;
import model.ElementoGioco;

public class Pannello extends JPanel {
    private FruitController fruitController;
    private ImageIcon immagineVite;

    public Pannello(FruitController fruitController) {
        this.fruitController = fruitController;
        loadImage("../images/vite.png");
    }

    private void loadImage(String imagePath) {
        // Carica l'immagine
        immagineVite = new ImageIcon(getClass().getResource(imagePath));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Disegna le vite solo se l'immagine è stata caricata correttamente
        if (immagineVite != null) {
            int startX = 20;
            int startY = 20;
            int heartSize = 20;
            for (int i = 0; i < fruitController.getNumeroVite(); i++) {
                g.drawImage(immagineVite.getImage(), startX + i * (heartSize + 5), startY, heartSize, heartSize, null);
            }
        }

        // Disegna il punteggio in alto a destra
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Punteggio: " + fruitController.getPunteggio(), getWidth() - 120, 20);

        // Disegna gli elementi del gioco
        for (ElementoGioco elemento : fruitController.getElementiGioco()) {
            elemento.disegna(g);
        }
    }
}
