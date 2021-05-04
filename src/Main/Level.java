package Main;

import Blocks.*;
import Actives.*;
import java.util.ArrayList;

public class Level {
    private static int generatedLevel = 0;
    private Paire paire; // contains 2D boolean table representing the walls and the objects on the map
    private Block[][] blocks; // 2D table of the blocks on the map
    private ArrayList<Monster> monsters = new ArrayList<>();
    private Position zoePos;

    // Constructor
    public Level () {
        paire = LevelGenerator.generateLevel(++generatedLevel);
        this.blocks = new Block[LevelGenerator.HAUTEUR][LevelGenerator.LARGEUR];
        placeWalls((boolean[][]) paire.getKey());
        placeObjects((String[]) paire.getValue());
    }

    // Getters
    public Block[][] getBlocks() { return blocks; }
    public ArrayList<Monster> getMonsters() { return monsters; }
    public Position getZoePos() {
        return zoePos;
    }

    // Methods
    /**
     * Function that places a block 'wall' on the map.
     * @param walls 2D boolean table (true = wall at location and false = no wall)
     */
    private void placeWalls (boolean[][] walls) {
        for (int y = 0; y < walls.length; y++) {
            for (int x = 0; x < walls[y].length; x++) {
                if (walls[y][x]) {
                    blocks[y][x] = new Wall();
                }
            }
        }
    }

    /**
     * Function that places the objects on the map.
     * @param objects description of the objects in the level
     */
    private void placeObjects (String[] objects) {
        // For each object
        for (String s : objects) {
            String[] info = s.split(":");
            int x;
            int y;
            switch (info[0]) {
                case ("tresor") :
                    // Get location of the object
                    x = Integer.valueOf(info[2]);
                    y = Integer.valueOf(info[3]);
                    // Add treasure at that location
                    blocks[y][x] = new Chest(info[1]);
                    break;
                case ("monstre") :
                    // Get location of the object
                    x = Integer.valueOf(info[2]);
                    y = Integer.valueOf(info[3]);
                    // Add monster at that location
                    monsters.add(new Monster(x, y, info[1],generatedLevel));
                    break;
                case ("sortie") :
                    // Get location of the object
                    x = Integer.valueOf(info[1]);
                    y = Integer.valueOf(info[2]);
                    // Add exit at that location
                    blocks[y][x] = new Exit();
                    break;
                case ("zoe") :
                    // Get initial position of the player
                    x = Integer.valueOf(info[1]);
                    y = Integer.valueOf(info[2]);
                    // Set the initial position of the player
                    zoePos = new Position(x, y);
                    break;
            }
        }
    }

    /**
     * Function that converts the map of blocks for a set level to string
     * @return Array of string representing the map
     */
    public String[] display() {
        String[] result = new String[LevelGenerator.HAUTEUR];
        int itt = 0;
        // For each block
        for (Block[] line : blocks) {
            String lineString = "";
            for (Block b : line) {
                // If tile empty
                if (b == null) {
                    lineString += " ";
                } else {
                    // Display block's sprite
                    lineString += b.getSprite();
                }
            }
            result[itt++] = lineString;
        }
        return result;
    }
}
