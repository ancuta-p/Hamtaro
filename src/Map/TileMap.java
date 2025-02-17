package Map;

import com.company.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class TileMap {
    // position
    private int x;
    private int y;

    // bounds
    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;

    // map
    private int[][] map;
    private int tileSize;
    private int numRows;
    private int numCols;
    private int width;
    private int height;

    // tileset
    private BufferedImage tileset;
    private int numTilesAcross;
    private Tile[][] tiles;

    // drawing
    private int rowOffset;
    private int colOffset;
    private int numRowsToDraw;
    private int numColsToDraw;

    public TileMap(int tileSize) {
        this.tileSize = tileSize;
        numRowsToDraw = GamePanel.HEIGHT / tileSize+2;
        numColsToDraw = GamePanel.WIDTH / tileSize +2;
    }

    public void loadTiles(String s) {

        try {

            tileset = ImageIO.read(new File(s));
            numTilesAcross = tileset.getWidth() / tileSize;
            tiles = new Tile[2][numTilesAcross];

            BufferedImage subimage;
            for(int col = 0; col < numTilesAcross; col++) {
                subimage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
                tiles[0][col] = new Tile(subimage, false);
                subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
                tiles[1][col] = new Tile(subimage, true); //blocked
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String s) {

        try {
            BufferedReader br=new BufferedReader(new FileReader(s));

            numCols = Integer.parseInt(br.readLine());
            numRows = Integer.parseInt(br.readLine());
            map = new int[numRows][numCols];
            width = numCols * tileSize;
            height = numRows * tileSize;

            xmin = GamePanel.WIDTH - width;
            xmax = 0;
            ymin = GamePanel.HEIGHT - height;
            ymax = 0;

            String delims = "\\s+";
            for(int row = 0; row < numRows; row++) {
                String line = br.readLine();
                String[] tokens = line.split(delims);
                for(int col = 0; col < numCols; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    public int getTileSize() { return tileSize; }
    public int getx() { return x; }
    public int gety() { return y; }

    public int getNumRows() { return numRows; }
    public int getNumCols() { return numCols; }
    public boolean isBlocked(int row, int col) {
        int rc = map[row][col];
        int r = rc / numTilesAcross;
        int c = rc % numTilesAcross;
        return tiles[r][c].isBlocked();
    }

    public void setTile(int row, int col, int index) {
        map[row][col] = index;
    }

    public void setPosition(int x, int y)
    {
        this.x += x - this.x;
        this.y += y - this.y;

        fixBounds();

        colOffset =-this.x / tileSize;
        rowOffset = -this.y / tileSize;
    }

    public void fixBounds() {
        if(x < xmin) x = xmin;
        if(y < ymin) y = ymin;
        if(x > xmax) x = xmax;
        if(y > ymax) y = ymax;
    }

    public void update() { }

    public void draw(Graphics2D g) {

        for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {

            if(row >= numRows) break;

            for(int col = colOffset; col < colOffset + numColsToDraw; col++) {

                if(col >= numCols) break;
                if(map[row][col] == 0) continue;

                int rc = map[row][col];
                int r = rc / numTilesAcross;
                int c = rc % numTilesAcross;

                g.drawImage(tiles[r][c].getImage(), x + col * tileSize, y + row * tileSize, null);

            }
    }

        }


    }

