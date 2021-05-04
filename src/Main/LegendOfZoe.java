package Main;

import Actives.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class of the program.
 */
public class LegendOfZoe {
    public static final int NB_LEVELS = 6;
    public static final int ZOE_MAX_HP = 5;
    public static final int ZOE_DAMAGE = 1;

    private static Level[] levels = new Level[NB_LEVELS];
    private static int currentLevel = 0;
    private static Player zoe = new Player();
    private static int nbHexaforce = 0;
    private static String inputs = "";
    private static String validInputs = "wasdcxoq";

    // Getters
    public static Level getLevel() { return levels[currentLevel]; }
    public static int getCurrentLevel() { return currentLevel; }
    public static Player getZoe() {
        return zoe;
    }
    public static int getNbHexaforce() {
        return nbHexaforce;
    }
    public static String getInputs() {return inputs;}

    // Setters
    public static void setNbHexaforce(int nbHexaforce) {
        LegendOfZoe.nbHexaforce = nbHexaforce;
    }
    public static void setInputs(String inputs) { LegendOfZoe.inputs = inputs; }

    // Scanner for user's inputs
    private static Scanner scanner = new Scanner(System.in);

    // Methods

    /**
     * Main function of the program. Runs the game and controls the gameplay.
     */
    public static void main(String[] args) {
        // Initialises the game
        init();

        // While Zoe is alive
        while (zoe.getAlive()) {
            // Displays the game on the terminal
            showGame();

            // If user entered more than 1 input at a time, perform all actions before scanning more inputs
            while (inputs.equals("")) { scanInputs(); }

            // Player's turn
            zoe.play();
            // If player has collected the level's Hexaforce piece, Zoe can exit
            if (LegendOfZoe.getNbHexaforce() == LegendOfZoe.getCurrentLevel() + 1) {
                zoe.exit();
            }

            // Monsters' turn
            // Each monster play
            for (Monster m : getLevel().getMonsters()) {
                m.play();
            }
        }
        // If Zoe dies, display loss message
        Messages.afficherDefaite();
    }

    /**
     * Function that reads the input(s) entered by the user.
     * Checks if the input corresponds to a command in the game.
     */
    public static void scanInputs() {
        boolean valid = false;

        while (!valid) {
            inputs = scanner.nextLine();
            valid = true;
            // For all inputs
            for (int i = 0; i < inputs.length(); i++) {
                // Check if input entered is a command in the game
                if (validInputs.indexOf(inputs.charAt(i)) == -1 ) {
                    valid = false;
                }
            }
        }
    }

    /**
     * Function that displays the game on the terminal (i.e.: level, monsters and player).
     */
    public static void showGame() {
        // Display the hearts and Hexaforce pieces
        displayInterface();

        // Display the map of current level
        String[] map = levels[currentLevel].display();

        // Display dead monsters
        ArrayList<Monster> monsters = levels[currentLevel].getMonsters();
        for (Monster m : monsters) {
            // If dead, display monster with 'X'
            if (!m.getAlive()) {
                replaceCharAt(map, m.getSprite(), m.getPos());
            }
        }

        // Display alive monsters
        for (Monster m : monsters) {
            // If alive, display monster with '@'
            if (m.getAlive()) {
                replaceCharAt(map, m.getSprite(), m.getPos());
            }
        }

        // Display player
        replaceCharAt(map, zoe.getSprite(), zoe.getPos());

        String finalMap = "";

        // Adds each line to final map
        for (String line : map) {
            finalMap += line + "\n";
        }
        // Prints final map on terminal
        System.out.println(finalMap);
    }

    /**
     * Function that replaces a character at a specific index.
     * @param map map of the game, made of many characters
     * @param sprite specific symbol to add
     * @param pos location where to add the symbol
     */
    public static void replaceCharAt(String[] map, String sprite, Position pos) {
        map[pos.getY()] = map[pos.getY()].substring(0, pos.getX()) + sprite + map[pos.getY()].substring(pos.getX() + 1);
    }

    /**
     * Function that displays the interface of hearts and Hexaforce pieces.
     */
    public static void displayInterface() {
        String UI = "Zoe: ";
        int hpCount = zoe.getHp();
        int hexaCount = nbHexaforce;

        // Display the hearts according to player's HP
        for (int i = 0; i < ZOE_MAX_HP; i++) {
            if (hpCount > 0) {
                hpCount--;
                UI += "♥ ";
            } else {
                UI += "_ ";
            }
        }

        UI += "| ";

        // Display the Hexaforce pieces collected, according to number of levels
        for (int j = 0; j < NB_LEVELS; j++) {
            if (hexaCount > 0) {
                hexaCount--;
                UI += "△ ";
            } else {
                UI += "_ ";
            }
        }
        System.out.println(UI);
    }

    /**
     * Function that initialises the game.
     */
    public static void init () {
        // Generate each level of the game
        for (int l = 0; l < NB_LEVELS; l++) { levels[l] = new Level(); }
        // Set the player's initial position
        zoe.setPos(levels[0].getZoePos());
        // Display the introduction message
        Messages.afficherIntro();
    }

    /**
     * Function called when the player has completed the current level and enters the next one
     */
    public static void nextLevel() {
        // Display message when entering new level
        System.out.println("Vous entrez dans le niveau " + (++currentLevel + 1));
        // If player has completed the game
        if (currentLevel == 6) {
            Messages.afficherVictoire();
        } else {
            // If not, set the player's initial position
            zoe.setPos(levels[currentLevel].getZoePos());
        }
    }
}