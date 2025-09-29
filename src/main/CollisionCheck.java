package main;

import entity.Direction;
import entity.Entity;
import object.ObjectType;

import java.util.ArrayList;
import java.util.List;

public class CollisionCheck {
    private final GamePanel gp;

    public CollisionCheck(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeft = entity.getScreenX() + entity.getCollisionBox().x;
        int entityTop = entity.getScreenY() + entity.getCollisionBox().y;
        int entityRight = entityLeft + entity.getCollisionBox().width;
        int entityBottom = entityTop + entity.getCollisionBox().height;

        int leftCol = entityLeft / gp.getTileSize();
        int rightCol = entityRight / gp.getTileSize();
        int topRow = entityTop / gp.getTileSize();
        int bottomRow = entityBottom / gp.getTileSize();

        int tile1, tile2;
        List<int[]> objectsToInteract = new ArrayList<>(); // Store [objectId, col, row] for objects to process

        switch (entity.getDirection()) {
            case UP -> {
                topRow = (entityTop - entity.getSpeed()) / gp.getTileSize();
                tile1 = gp.getTileManager().getMapTilesNum(leftCol, topRow);
                tile2 = gp.getTileManager().getMapTilesNum(rightCol, topRow);
                int obj1 = gp.getObjectManager().getMapObjectsNum(leftCol, topRow);
                if (ObjectType.fromId(obj1) != ObjectType.NONE) {
                    objectsToInteract.add(new int[]{obj1, leftCol, topRow});
                }
                int obj2 = gp.getObjectManager().getMapObjectsNum(rightCol, topRow);
                if (ObjectType.fromId(obj2) != ObjectType.NONE && (leftCol != rightCol || topRow != topRow)) {
                    objectsToInteract.add(new int[]{obj2, rightCol, topRow});
                }
            }
            case DOWN -> {
                bottomRow = (entityBottom + entity.getSpeed()) / gp.getTileSize();
                tile1 = gp.getTileManager().getMapTilesNum(leftCol, bottomRow);
                tile2 = gp.getTileManager().getMapTilesNum(rightCol, bottomRow);
                int obj1 = gp.getObjectManager().getMapObjectsNum(leftCol, bottomRow);
                if (ObjectType.fromId(obj1) != ObjectType.NONE) {
                    objectsToInteract.add(new int[]{obj1, leftCol, bottomRow});
                }
                int obj2 = gp.getObjectManager().getMapObjectsNum(rightCol, bottomRow);
                if (ObjectType.fromId(obj2) != ObjectType.NONE && (leftCol != rightCol || bottomRow != bottomRow)) {
                    objectsToInteract.add(new int[]{obj2, rightCol, bottomRow});
                }
            }
            case LEFT -> {
                leftCol = (entityLeft - entity.getSpeed()) / gp.getTileSize();
                tile1 = gp.getTileManager().getMapTilesNum(leftCol, topRow);
                tile2 = gp.getTileManager().getMapTilesNum(leftCol, bottomRow);
                int obj1 = gp.getObjectManager().getMapObjectsNum(leftCol, topRow);
                if (ObjectType.fromId(obj1) != ObjectType.NONE) {
                    objectsToInteract.add(new int[]{obj1, leftCol, topRow});
                }
                int obj2 = gp.getObjectManager().getMapObjectsNum(leftCol, bottomRow);
                if (ObjectType.fromId(obj2) != ObjectType.NONE && (leftCol != leftCol || topRow != bottomRow)) {
                    objectsToInteract.add(new int[]{obj2, leftCol, bottomRow});
                }
            }
            case RIGHT -> {
                rightCol = (entityRight + entity.getSpeed()) / gp.getTileSize();
                tile1 = gp.getTileManager().getMapTilesNum(rightCol, topRow);
                tile2 = gp.getTileManager().getMapTilesNum(rightCol, bottomRow);
                int obj1 = gp.getObjectManager().getMapObjectsNum(rightCol, topRow);
                if (ObjectType.fromId(obj1) != ObjectType.NONE) {
                    objectsToInteract.add(new int[]{obj1, rightCol, topRow});
                }
                int obj2 = gp.getObjectManager().getMapObjectsNum(rightCol, bottomRow);
                if (ObjectType.fromId(obj2) != ObjectType.NONE && (rightCol != rightCol || topRow != bottomRow)) {
                    objectsToInteract.add(new int[]{obj2, rightCol, bottomRow});
                }
            }
            default -> { return; }
        }

        // Process object interactions
        if (!objectsToInteract.isEmpty()) {
            interactObjects(objectsToInteract, entity);
        }

        // Check tile collisions
        if (gp.getTileManager().getTile()[tile1].getCollision() || gp.getTileManager().getTile()[tile2].getCollision()) {
            entity.setCollisionIsOn(true);
        }

        // Additional check for DOOR_CLOSED to block movement
        for (int[] objData : objectsToInteract) {
            ObjectType type = ObjectType.fromId(objData[0]);
            if (type == ObjectType.DOOR_CLOSED && gp.getPlayer().getKeys() <= 0) {
                entity.setCollisionIsOn(true); // Block movement if no keys
            }
        }
    }

    private void interactObjects(List<int[]> objectsToInteract, Entity entity) {
        gp.getObjectManager().queueUpdate(() -> {
            int keysToAdd = 0;
            int coinsToAdd = 0;
            List<int[]> doorsToOpen = new ArrayList<>();
            boolean hatchOpened = false;

            // Process all objects in the list
            for (int[] objData : objectsToInteract) {
                int objectId = objData[0];
                int col = objData[1];
                int row = objData[2];
                ObjectType type = ObjectType.fromId(objectId);

                switch (type) {
                    case KEY -> {
                        keysToAdd++;
                        gp.getObjectManager().removeObjectAt(col, row);
                    }
                    case COIN, COIN_RED, COIN_PUR -> {
                        coinsToAdd++;
                        gp.getObjectManager().removeObjectAt(col, row);
                    }
                    case DOOR_CLOSED -> {
                        if (gp.getPlayer().getKeys() > 0) {
                            doorsToOpen.add(new int[]{col, row});
                        }
                    }
                    case HATCH_OPENED -> {
                        hatchOpened = true;
                    }
                    default -> {}
                }
            }

            // Apply accumulated changes
            if (keysToAdd > 0) {
                gp.getPlayer().setKeys(gp.getPlayer().getKeys() + keysToAdd);
            }
            if (coinsToAdd > 0) {
                gp.getPlayer().setCoins(gp.getPlayer().getCoins() + coinsToAdd);
            }
            for (int[] door : doorsToOpen) {
                if (gp.getPlayer().getKeys() > 0) { // Re-check key availability
                    gp.getPlayer().setKeys(gp.getPlayer().getKeys() - 1);
                    gp.getObjectManager().replaceObjectAt(door[0], door[1], ObjectType.DOOR_OPENED);
                }
            }
            if (hatchOpened) {
                gp.setActualLevel(gp.getActualLevel() + 1);
                gp.getObjectManager().reloadObjects();
                gp.getTileManager().reloadTiles();
                gp.getPlayer().setScreenX(9 * gp.getTileSize());
                gp.getPlayer().setScreenY(10 * gp.getTileSize());
            }
        });
    }
}