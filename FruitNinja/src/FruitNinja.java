import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.*;

public class FruitNinja extends JFrame {
    public FruitNinja() {
        setTitle("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        JPanel panel = new JPanel(new BorderLayout());
        JButton startButton = new JButton("Start Game");

        // Aggiunge un'azione al pulsante Start Game
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aspetta 1 secondo prima di avviare il gioco
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        dispose(); // Chiudi il menu principale
                        startGame(); // Avvia il gioco
                    }
                }).start();
            }
        });

        panel.add(startButton, BorderLayout.CENTER);
        add(panel);

        setLocationRelativeTo(null); // Centra la finestra
        setVisible(true);
    }

    private void startGame() {
        FruitController game = new FruitController();
        game.start(); // Avvia il gioco
    }

    public static void main(String[] args) {
        new FruitNinja();
    }
}
