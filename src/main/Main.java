package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Wandering Joe");

        // Create GamePanel
        GamePanel gamePanel = new GamePanel();

        // Create key count panel (1 tile high)
        JPanel keyCountPanel = new JPanel();
        keyCountPanel.setPreferredSize(new Dimension(gamePanel.getScreenWidth(), gamePanel.getTileSize())); // 760x40
        keyCountPanel.setBackground(Color.darkGray);
        JLabel keyCountLabel = new JLabel("Number of keys: 0");
        keyCountLabel.setForeground(Color.white);
        keyCountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        keyCountLabel.setHorizontalAlignment(SwingConstants.LEFT);
        keyCountPanel.add(keyCountLabel);
        keyCountPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding

        // Add panels to window
        window.setLayout(new BorderLayout());
        window.add(keyCountPanel, BorderLayout.NORTH);
        window.add(gamePanel, BorderLayout.CENTER);

        // Pack and center the window
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Start the game thread
        gamePanel.startGameThread();

        // Timer to update key count display
        Timer keyCountTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyCountLabel.setText("Number of keys: " + gamePanel.getPlayerKeys());
            }
        });
        keyCountTimer.start();
    }
}