package tile;

import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage image;
    private boolean collision = false;

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setCollision(boolean bool) {
        this.collision = bool;
    }

    public boolean getCollision() {
        return collision;
    }
}