package Actives;

import Main.*;

public class Monster extends Active {
    // True : if Zoe is on same tile or on 1 of 8 neighbouring tiles
    // False : if not
    private boolean zoeInRange;
    // Item carried by monster. Dropped at its death
    private Item item;

    // Constructor
    public Monster( int x, int y, String item, int currentLevel) {
        super("@", (int) Math.max(0.6 * currentLevel, 1), (int) Math.max(0.4 * currentLevel, 1), x, y);
        this.item = new Item(item);
        this.setName(generateName());
        int posXDiff = this.getPos().getX() - LegendOfZoe.getZoe().getPos().getX();
        int posYDiff = this.getPos().getY() - LegendOfZoe.getZoe().getPos().getY();
        this.checkZoeInRange(posXDiff, posYDiff);
    }

    /**
     * Function that assigns a name to each monster in the game.
     * @return String name
     */
    public String generateName() {
        String[] listNames = { "Renwil", "Guthcarne", "Isenachet", "Vell-bras", "Lafferdric", "Gar-nas",
                               "Eadbardven", "Thothormax", "Burbethgrimseph", "Ceomit", "Mawerd", "Isenachet",
                               "Vanack", "Blorgor", "Grigrel", "Rogim", "Morthos", "Brulurzgrim", "Gratlúrtz",
                               "Garselbug", "Umor", "Hargtul", "Irok", "Lug", "Grarok", "Gugash", "Tulgor", "Dra",
                               "Va-u", "Getarg", "Grimdrak", "Garneon", "Eadthor", "Crobar", "Torhubeorn" };
        return listNames[(int) Math.floor(Math.random() * listNames.length)];
    }

    // Getter
    public boolean getZoeInRange() {
        return zoeInRange;
    }
    // Setter
    public void setZoeInRange(boolean state) {
        this.zoeInRange = state;
    }

    // Methods
    /**
     * Function that checks if player Zoe is in the range of monster for an attack
     * @param posXDiff difference between monster's x-coordinate and player's x-coordinate
     * @param posYDiff difference between monster's y-coordinate and player's y-coordinate
     */
    public void checkZoeInRange(int posXDiff, int posYDiff) {
        if ((posXDiff <= 1 && posXDiff >= -1) && (posYDiff <= 1 && posYDiff >= -1)) {
            this.setZoeInRange(true);
        }
    }

    /**
     * Implements 'play' method in Active abstract class.
     * According to player's position, allows monster to attack it or move towards it.
     */
    public void play () {
        if (this.getAlive()) {
            Player zoe = LegendOfZoe.getZoe();
            int monsterPosX = this.getPos().getX();
            int monsterPosY = this.getPos().getY();
            int zoePosX = zoe.getPos().getX();
            int zoePosY = zoe.getPos().getY();
            int posXDiff = monsterPosX - zoePosX;
            int posYDiff = monsterPosY - zoePosY;

            // Check if player is in range
            this.checkZoeInRange(posXDiff, posYDiff);

            if (this.getZoeInRange()) { // If player is in range
                this.attack(zoe);
            } else { // Player not in range, so move towards player
                int dx = 0;
                int dy = 0;

                if (posXDiff < 0) { // Case player to right, move right
                    dx = 1;
                } else if (posXDiff > 0) { // Case player to left, move left
                    dx = -1;
                }

                if (posYDiff > 0) { // Case player up, move up
                    dy = 1;
                } else if (posYDiff < 0) { // Case player down, move down
                    dy = -1;
                }

                // Performs the move on monster
                this.move(dx, dy);

                // TODO Make sure this portion of code is useful
                monsterPosX = this.getPos().getX();
                monsterPosY = this.getPos().getY();
                posXDiff = monsterPosX - zoePosX;
                posYDiff = monsterPosY - zoePosY;
                this.checkZoeInRange(posXDiff, posYDiff);
            }
        }
    }

    /**
     * Implements 'die' method in Active abstract class.
     * When monster dies, set monster dead and player loots the item it carried.
     */
    @Override
    public void die() {
        this.setSprite("X");
        this.setAlive(false);
        this.zoeInRange = false;
        System.out.println(this.getName() + " meurt!");
        this.item.loot();
    }

    // Calculate the direction to go to get near the player
    // * BONUS * 5% if it never hits walls
    public Position trajectory () {
        // TODO
        return new Position(0,0);
    }
}
