package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ParentObject {
    private BufferedImage image;
    private String name;
    private int screenX;
    private int screenY;
    private boolean collision;

    public void draw(Graphics2D g2, GamePanel gp) {
        g2.drawImage(image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public boolean getCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public void setCoord(int x, int y) {
        this.screenX = x;
        this.screenY = y;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }
}