package controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import view.Pannello;
import model.Bomba;
import model.ElementoGioco;
import model.Frutto;

public class FruitController {
    // Dichiarazione delle variabili di istanza
    private Pannello pannello;
    private JFrame frame;
    private ArrayList<ElementoGioco> elementiGioco = new ArrayList<>();
    private static final float VELOCITA_INIZIALE_FRUTTO = 4.0f; // Velocità iniziale del frutto
    private static final int INTERVALLO_MOVIMENTO = 40; // Intervallo di tempo per il movimento degli elementi
    private Timer timer; // Timer per il movimento degli elementi
    private int punteggio = 0; // Punteggio del giocatore
    private static final int MAX_ELEMENTI_SCHERMO = 7; // Numero massimo di elementi visualizzabili contemporaneamente
    private int vite = 3; // Numero di vite del giocatore
    private ElementoGioco ultimoElementoTagliato; // Ultimo elemento tagliato
    private int offsetVerticale = 110; // Spazio verticale tra gli elementi generati
    private float velocitaCorrenteFrutto = VELOCITA_INIZIALE_FRUTTO; // Velocità corrente del frutto
    private static final int INTERVALLO_AGGIORNAMENTO_VELOCITA = 2500; // Intervallo di tempo per l'aumento della velocità
    private static final float AUMENTO_VELOCITA = 0.15f; // Aumento della velocità ad ogni intervallo

    // Costruttore
    public FruitController() {
        pannello = new Pannello(this);
        // Aggiunge un listener per il movimento del mouse
        pannello.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                rimuoviElementoGioco(e.getX(), e.getY()); // Rimuove l'elemento al passaggio del mouse
            }
        });
    }

    // Avvia il gioco
    public void start() {
        aggiungiPannello(); // Aggiunge il pannello al frame
        aggiungiElementoGioco(); // Aggiunge gli elementi di gioco
        avviaTimerMovimento(); // Avvia il timer per il movimento degli elementi
        avviaTimerAggiornamentoVelocita(); // Avvia il timer per l'aggiornamento della velocità
        pannello.repaint(); // Ridisegna il pannello
    }
    
    // Aggiunge il pannello al frame
    public void aggiungiPannello() {
        frame = new JFrame("Fruit Ninja");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        pannello.setBackground(Color.black);
        frame.add(pannello);
        frame.setSize(900, 650);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    // Aggiunge gli elementi di gioco
    private void aggiungiElementoGioco() {
        Random random = new Random();
        // Calcola il numero di elementi da aggiungere
        int numElementiDaAggiungere = random.nextInt(MAX_ELEMENTI_SCHERMO - elementiGioco.size()) + 1;
        for (int i = 0; i < numElementiDaAggiungere; i++) {
            // Esci se abbiamo già raggiunto il limite di elementi
            if (elementiGioco.size() >= MAX_ELEMENTI_SCHERMO) {
                break;
            }
            // Genera casualmente una bomba o un frutto
            int probabilitaBomba = random.nextInt(4);
            int x = random.nextInt(pannello.getWidth() - ElementoGioco.RAGGIO);
            int y = random.nextInt(pannello.getHeight() - ElementoGioco.RAGGIO);
            if (probabilitaBomba == 0 && getNumeroBombe() < MAX_ELEMENTI_SCHERMO - 1) {
                elementiGioco.add(new Bomba(x, y));
            } else {
                // Imposta la posizione del nuovo elemento in base all'ultimo elemento tagliato
                if (ultimoElementoTagliato != null) {
                    y = Math.min(y, ultimoElementoTagliato.getY() - offsetVerticale);
                }
                elementiGioco.add(new Frutto(x, y));
            }
        }
    }

    // Avvia il timer per l'aggiornamento della velocità
    private void avviaTimerAggiornamentoVelocita() {
        Timer timerAggiornamentoVelocita = new Timer(INTERVALLO_AGGIORNAMENTO_VELOCITA, e -> {
            velocitaCorrenteFrutto += AUMENTO_VELOCITA;
        });
        timerAggiornamentoVelocita.start();
    }

    // Avvia il timer per il movimento degli elementi
    private void avviaTimerMovimento() {
        timer = new Timer(INTERVALLO_MOVIMENTO, e -> {
            for (int i = 0; i < elementiGioco.size(); i++) {
                ElementoGioco elemento = elementiGioco.get(i);
                float nuovaY = elemento.getY() + velocitaCorrenteFrutto;
                // Rimuovi l'elemento se raggiunge il fondo dello schermo
                if (nuovaY > pannello.getHeight()) {
                    if (elemento instanceof Frutto) {
                        vite--;
                        if (vite == 0) {
                            gameOver();
                            return;
                        }
                    }
                    elementiGioco.remove(elemento);
                    aggiungiElementoGioco();
                    pannello.repaint();
                    continue;
                }
                elemento.setY((int)nuovaY);
            }
            pannello.repaint();
        });
        timer.start();
    }

    // Mostra il messaggio di game over e chiude l'applicazione
    private void gameOver() {
        JOptionPane.showMessageDialog(frame, "Game Over\nYour Score: " + punteggio);
        System.exit(0);
    }

    // Rimuove l'elemento di gioco in base alla posizione del mouse
    private void rimuoviElementoGioco(int mouseX, int mouseY) {
        for (ElementoGioco elemento : new ArrayList<>(elementiGioco)) {
            if (elemento.contienePunto(mouseX, mouseY)) {
                if (elemento instanceof Frutto) {
                    punteggio++;
                    // Aumenta le vite ogni 15 punti
                    if (punteggio % 15 == 0) {
                        vite = Math.min(3, vite + 1);
                    }
                } else if (elemento instanceof Bomba) {
                    vite--;
                }
                elementiGioco.remove(elemento);
                ultimoElementoTagliato = elemento;
                aggiungiElementoGioco();
                pannello.repaint();
                break;
            }
        }
        // Termina il gioco se le vite sono esaurite
        if (vite <= 0) {
            gameOver();
        }
    }

    // Restituisce il punteggio del giocatore
    public int getPunteggio() {
        return punteggio;
    }

    // Restituisce la lista degli elementi di gioco
    public ArrayList<ElementoGioco> getElementiGioco() {
        return elementiGioco;
    }

    // Restituisce il numero di bombe presenti
    private int getNumeroBombe() {
        int numeroBombe = 0;
        for (ElementoGioco elemento : elementiGioco) {
            if (elemento instanceof Bomba) {
                numeroBombe++;
            }
        }
        return numeroBombe;
    }

    // Restituisce il numero di vite rimanenti
    public int getNumeroVite() {
        return vite;
    }
}