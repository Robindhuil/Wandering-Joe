package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Entity {
    protected int speed;
    protected int screenX;
    protected int screenY;
    protected Direction direction = Direction.IDLE;
    protected int spriteCounter = 0;
    protected int spriteNumber = 1;
    protected Rectangle collisionBox;
    protected boolean collisionIsOn = false;
    protected Map<Direction, BufferedImage[]> animations = new HashMap<>();

    public Entity() {}

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpriteCounter() {
        return spriteCounter;
    }

    public void setSpriteCounter(int spriteCounter) {
        this.spriteCounter = spriteCounter;
    }

    public int getSpriteNumber() {
        return spriteNumber;
    }

    public void setSpriteNumber(int spriteNumber) {
        this.spriteNumber = spriteNumber;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public void setCollisionBox(Rectangle collisionBox) {
        this.collisionBox = collisionBox;
    }

    public boolean isCollisionIsOn() {
        return collisionIsOn;
    }

    public void setCollisionIsOn(boolean collisionIsOn) {
        this.collisionIsOn = collisionIsOn;
    }

    public BufferedImage getCurrentImage() {
        BufferedImage[] frames = animations.getOrDefault(direction, animations.get(Direction.IDLE));
        return frames != null ? frames[spriteNumber - 1] : null; // 1-based to 0-based index
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }
}