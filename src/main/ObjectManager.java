package main;

import object.*;
import object.ObjectType;
import object.ParentObject;

import java.awt.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ObjectManager {
    private final GamePanel gp;
    private int[][] mapObjectsNum;
    private List<ParentObject> objects = new ArrayList<>();
    private int totalCoins = 0;
    private final Queue<Runnable> pendingUpdates = new ConcurrentLinkedQueue<>();

    public ObjectManager(GamePanel gp) {
        this.gp = gp;
        this.mapObjectsNum = new int[gp.getMaxCol()][gp.getMaxRow()];
        loadMap("/levels/level0_obj");
        createObjects();
    }

    public void loadMap(String filePath) {
        try (InputStream is = getClass().getResourceAsStream(filePath);
             Scanner sc = new Scanner(new InputStreamReader(is))) {
            int col = 0, row = 0;
            while (sc.hasNextInt() && row < gp.getMaxRow()) {
                mapObjectsNum[col][row] = sc.nextInt();
                col++;
                if (col == gp.getMaxCol()) {
                    col = 0;
                    row++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createObjects() {
        objects.clear();
        totalCoins = 0;
        for (int row = 0; row < gp.getMaxRow(); row++) {
            for (int col = 0; col < gp.getMaxCol(); col++) {
                int tileNum = mapObjectsNum[col][row];
                if (tileNum == 0) continue;

                ObjectType type = ObjectType.fromId(tileNum);
                ParentObject obj = createObjectByType(type);
                if (obj != null) {
                    obj.setCoord(col * gp.getTileSize(), row * gp.getTileSize());
                    objects.add(obj);
                    if (isCoinType(type)) {
                        totalCoins++;
                    }
                }
            }
        }
    }

    private ParentObject createObjectByType(ObjectType type) {
        return switch (type) {
            case KEY -> new objKey();
            case COIN -> new objCoin();
            case DOOR_CLOSED -> new objDoorClosed();
            case DOOR_OPENED -> new objDoorOpened();
            case HATCH_CLOSED -> new ObjHatchClosed();
            case HATCH_OPENED -> new ObjHatchOpened();
            case COIN_RED -> new ObjCoinRed();
            case COIN_PUR -> new ObjCoinPur();
            default -> null;
        };
    }

    private boolean isCoinType(ObjectType type) {
        return type == ObjectType.COIN || type == ObjectType.COIN_RED || type == ObjectType.COIN_PUR;
    }

    public void reloadObjects() {
        int level = gp.getActualLevel();
        String path = switch (level) {
            case 1 -> "/levels/level1_obj";
            case 2 -> "/levels/level2_obj";
            case 3 -> "/levels/level3_obj";
            case 4 -> "/levels/level4_obj";
            default -> "/levels/youWon_obj";
        };
        loadMap(path);
        createObjects();
    }

    public void draw(Graphics2D g2) {
        for (ParentObject obj : objects) {
            if (obj != null) {
                g2.drawImage(obj.getImage(), obj.getScreenX(), obj.getScreenY(), gp.getTileSize(), gp.getTileSize(), null);
            }
        }
    }

    public void processPendingUpdates() {
        while (!pendingUpdates.isEmpty()) {
            pendingUpdates.poll().run();
        }
        // Check if all coins are collected and open hatch if needed
        if (totalCoins == 0 && gp.getPlayer().getCoins() > 0) {
            gp.getPlayer().setCoins(0);
            addOrUpdateObjectAt(9, 10, ObjectType.HATCH_OPENED);
        }
    }

    public void queueUpdate(Runnable update) {
        pendingUpdates.add(update);
    }

    public int getMapObjectsNum(int col, int row) {
        return mapObjectsNum[col][row];
    }

    public int[][] getWholeMapObjectsNum() {
        return mapObjectsNum;
    }

    public int getTotalCoins() {
        return totalCoins;
    }

    private ParentObject findObjectAt(int col, int row) {
        int x = col * gp.getTileSize();
        int y = row * gp.getTileSize();
        for (ParentObject obj : objects) {
            if (obj.getScreenX() == x && obj.getScreenY() == y) {
                return obj;
            }
        }
        return null;
    }

    public void removeObjectAt(int col, int row) {
        ParentObject obj = findObjectAt(col, row);
        if (obj != null) {
            ObjectType type = ObjectType.fromId(mapObjectsNum[col][row]);
            objects.remove(obj);
            mapObjectsNum[col][row] = 0;
            if (isCoinType(type)) {
                totalCoins--;
            }
        }
    }

    public void replaceObjectAt(int col, int row, ObjectType newType) {
        ParentObject oldObj = findObjectAt(col, row);
        if (oldObj != null) {
            ObjectType oldType = ObjectType.fromId(mapObjectsNum[col][row]);
            ParentObject newObj = createObjectByType(newType);
            if (newObj != null) {
                newObj.setCoord(oldObj.getScreenX(), oldObj.getScreenY());
                int index = objects.indexOf(oldObj);
                objects.set(index, newObj);
                mapObjectsNum[col][row] = newType.getId();
                if (isCoinType(newType) && !isCoinType(oldType)) {
                    totalCoins++;
                } else if (!isCoinType(newType) && isCoinType(oldType)) {
                    totalCoins--;
                }
            } else {
                System.err.println("Failed to create new object for type: " + newType);
            }
        } else {
            System.err.println("No object found at col: " + col + ", row: " + row);
        }
    }

    public void addOrUpdateObjectAt(int col, int row, ObjectType type) {
        ParentObject existing = findObjectAt(col, row);
        if (existing != null) {
            replaceObjectAt(col, row, type);
        } else {
            ParentObject newObj = createObjectByType(type);
            if (newObj != null) {
                newObj.setCoord(col * gp.getTileSize(), row * gp.getTileSize());
                objects.add(newObj);
                mapObjectsNum[col][row] = type.getId();
                if (isCoinType(type)) {
                    totalCoins++;
                }
            } else {
                System.err.println("Failed to create new object for type: " + type);
            }
        }
    }
}