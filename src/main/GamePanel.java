package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    private final int originalTileSize = 8;
    private final int scale = 5;
    private final int tileSize = scale * originalTileSize;
    private final int maxCol = 19;
    private final int maxRow = 19;
    private final int screenWidth = tileSize * maxCol; // 760
    private final int screenHeight = tileSize * maxRow; // 760
    private int actualLevel = 0;
    private Thread gameThread;
    private final TileManager tileManager = new TileManager(this);
    private final KeyManager keyM = new KeyManager();
    private final CollisionCheck collisionCheck = new CollisionCheck(this);
    private final ObjectManager objectManager = new ObjectManager(this);
    private final int fps = 60;
    private final Player player = new Player(this, keyM);
    private long lastTime = System.nanoTime();

    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.black);
        setDoubleBuffered(true);
        addKeyListener(keyM);
        setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000.0 / fps;
        double delta = 0;
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                objectManager.processPendingUpdates(); // Process updates after rendering
                delta--;
                drawCount++;
            }

            if (timer >= 1_000_000_000) {
                timer = 0;
                drawCount = 0;
            }
        }
    }

    public void update() {
        player.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileManager.draw(g2);
        objectManager.draw(g2);
        player.draw(g2);
        g2.dispose();
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getMaxCol() {
        return maxCol;
    }

    public int getMaxRow() {
        return maxRow;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public CollisionCheck getCollisionCheck() {
        return collisionCheck;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public Player getPlayer() {
        return player;
    }

    public int getActualLevel() {
        return actualLevel;
    }

    public void setActualLevel(int actualLevel) {
        this.actualLevel = actualLevel;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    // New method to get the player's key count
    public int getPlayerKeys() {
        return player.getKeys();
    }
}