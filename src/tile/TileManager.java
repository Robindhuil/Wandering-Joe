package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class TileManager {
    private final GamePanel gp;
    private final Tile[] tile;
    private final int[][] mapTilesNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        this.tile = new Tile[10];
        this.mapTilesNum = new int[gp.getMaxCol()][gp.getMaxRow()];
        loadTileImages();
        loadMap("/levels/level0");
    }

    private void loadTileImages() {
        try {
            tile[0] = new Tile();
            tile[0].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png")));

            tile[1] = new Tile();
            tile[1].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/brick_wall.png")));
            tile[1].setCollision(true);

            tile[2] = new Tile();
            tile[2].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/woodFloor.png")));

            tile[3] = new Tile();
            tile[3].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/dirt.png")));

            tile[4] = new Tile();
            tile[4].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/bush.png")));
            tile[4].setCollision(true);

            tile[5] = new Tile();
            tile[5].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/flower.png")));

            tile[6] = new Tile();
            tile[6].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/water.png")));
            tile[6].setCollision(true);

            tile[7] = new Tile();
            tile[7].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/treePot.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try (InputStream is = getClass().getResourceAsStream(filePath);
             Scanner sc = new Scanner(new InputStreamReader(is))) {
            int col = 0, row = 0;
            while (sc.hasNextInt() && row < gp.getMaxRow()) {
                mapTilesNum[col][row] = sc.nextInt();
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

    public void reloadTiles() {
        int level = gp.getActualLevel();
        String path = switch (level) {
            case 1 -> "/levels/level1";
            case 2 -> "/levels/level2";
            case 3 -> "/levels/level3";
            case 4 -> "/levels/level4";
            default -> "/levels/youWon";
        };
        loadMap(path);
    }

    public void draw(Graphics2D g2) {
        for (int row = 0; row < gp.getMaxRow(); row++) {
            for (int col = 0; col < gp.getMaxCol(); col++) {
                int x = gp.getTileSize() * col;
                int y = gp.getTileSize() * row;
                int tileNum = mapTilesNum[col][row];
                g2.drawImage(tile[tileNum].getImage(), x, y, gp.getTileSize(), gp.getTileSize(), null);
            }
        }
    }

    public int getMapTilesNum(int col, int row) {
        return mapTilesNum[col][row];
    }

    public Tile[] getTile() {
        return tile;
    }
}