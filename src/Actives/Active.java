package Actives;

import Blocks.Block;
import Main.*;

public abstract class Active {
    private boolean alive;
    private String name;
    private String sprite;
    private Position pos;
    private int hp, maxHp, damage;

    // Constructor 1
    public Active(String sprite, int maxHp, int damage, int x, int y) {
        this.alive = true;
        this.sprite = sprite;
        this.hp = maxHp;
        this.maxHp = maxHp;
        this.damage = damage;
        this.pos = new Position(x,y);
    }
    // Constructor 2
    public Active(String sprite, int maxHp, int damage) {
        this.alive = true;
        this.sprite = sprite;
        this.hp = maxHp;
        this.maxHp = maxHp;
        this.damage = damage;
        this.pos = new Position(0,0);
    }

    // Getters
    public boolean getAlive() {
        return this.alive;
    }
    public String getName() {
        return this.name;
    }
    public String getSprite() {
        return this.sprite;
    }
    public Position getPos() {
        return this.pos;
    }
    public int getHp() {
        return this.hp;
    }
    public int getMaxHp() {
        return this.maxHp;
    }
    public int getDamage() {
        return this.damage;
    }

    // Setters
    public void setAlive(boolean state) {
        this.alive = state;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setSprite(String sprite) {
        this.sprite = sprite;
    }
    public void setPos(Position pos) {
        this.pos = pos;
    }

    public void setHp(int hp) {
        this.hp = hp;
        if (this.hp > this.maxHp) {
            this.hp = this.maxHp;
        }
    }
    public void setRelativeHp(int diff) {
        this.setHp(this.getHp() + diff);
        if (this.getHp() <= 0) {
            this.die();
        } if (this.getHp() > this.getMaxHp()) {
            this.setHp(this.getMaxHp());
        }
    }

    // Methods
    /**
     * Allows the active to perform an action during its turn.
     */
    public abstract void play ();

    /**
     * Function called when the active's HP reaches 0.
     */
    public abstract void die();

    /**
     * Function that allows the active to move.
     * @param dx x-displacement
     * @param dy y-displacement
     */
    public void move (int dx, int dy) {
        // Initial position
        Position pos = this.pos;
        // Check if desired displacement is valid
        Position validPos = checkValidMove(pos, dx, dy);
        // Change position of character once desired displacement has been validated
        pos.setX(pos.getX() + validPos.getX());
        pos.setY(pos.getY() - validPos.getY());
    }

    /**
     * Function that checks if the desired displacement is valid.
     * @param pos initial position of the character
     * @param dx x-displacement
     * @param dy y-displacement
     * @return valid (dx, dy) displacement
     */
    public static Position checkValidMove(Position pos, int dx, int dy) {

        // Check if desired displacement would move character out of map
        if (pos.getX() + dx < 0 || pos.getX() + dx >= LevelGenerator.LARGEUR) { dx = 0; }
        if (pos.getY() - dy < 0 || pos.getY() - dy >= LevelGenerator.HAUTEUR) { dy = 0; }

        // Check if desired displacement would move character on inaccessible blocks
        Block[][] map = LegendOfZoe.getLevel().getBlocks();

        if (map[pos.getY() - dy][pos.getX() + dx] == null) {
            return new Position(dx, dy);
        } else if (map[pos.getY()][pos.getX() + dx] == null) {
            return new Position(dx, 0);
        } else if (map[pos.getY() - dy][pos.getX()] == null) {
            return new Position(0, dy);
        } else {
            return new Position(0,0);
        }
    }

    /**
     * Function that allows an active to attack another one
     * @param target the active to attack
     */
    public void attack(Active target) {
        // Reduce the target HP
        target.setRelativeHp(-this.getDamage());
    }
}
