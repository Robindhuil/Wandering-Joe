package entity;

import main.GamePanel;
import main.KeyManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    private GamePanel gp;
    private KeyManager keyM;
    private int keys = 0;
    private int coins = 0;

    public Player(GamePanel gp, KeyManager keyM) {
        this.gp = gp;
        this.keyM = keyM;
        // Centrovanie hráča
        this.screenX = gp.getScreenWidth() / 2 - gp.getTileSize() / 2;
        this.screenY = gp.getScreenHeight() / 2 - gp.getTileSize() / 2;
        setDefaultValues();
        loadPlayerImages();
        setCollisionBox(new Rectangle(18, 18, 12, 22));
    }

    private void setDefaultValues() {
        speed = 2;
        direction = Direction.IDLE;
    }

    private void loadPlayerImages() {
        try {
            animations.put(Direction.IDLE, new BufferedImage[]{
                    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_idle1.png"))),
                    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_idle2.png")))
            });
            animations.put(Direction.UP, new BufferedImage[]{
                    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_go_up1.png"))),
                    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_go_up2.png")))
            });
            animations.put(Direction.DOWN, new BufferedImage[]{
                    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_go_down1.png"))),
                    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_go_down2.png")))
            });
            animations.put(Direction.LEFT, new BufferedImage[]{
                    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_go_left1.png"))),
                    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_go_left2.png")))
            });
            animations.put(Direction.RIGHT, new BufferedImage[]{
                    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_go_right1.png"))),
                    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_go_right2.png")))
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // Smer na základe kláves
        if (keyM.isUpPressed()) {
            direction = Direction.UP;
        } else if (keyM.isDownPressed()) {
            direction = Direction.DOWN;
        } else if (keyM.isLeftPressed()) {
            direction = Direction.LEFT;
        } else if (keyM.isRightPressed()) {
            direction = Direction.RIGHT;
        } else {
            direction = Direction.IDLE;
        }

        collisionIsOn = false;
        gp.getCollisionCheck().checkTile(this);

        // Pohyb ak nie je kolízia
        if (!collisionIsOn && direction != Direction.IDLE) {
            switch (direction) {
                case UP -> screenY -= speed;
                case DOWN -> screenY += speed;
                case LEFT -> screenX -= speed;
                case RIGHT -> screenX += speed;
            }
        }

        // Animácia
        spriteCounter++;
        if (spriteCounter > 15) {
            spriteNumber = (spriteNumber == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = getCurrentImage();
        if (image != null) {
            g2.drawImage(image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
        }
    }

    public int getKeys() {
        return keys;
    }

    public void setKeys(int keys) {
        this.keys = keys;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}