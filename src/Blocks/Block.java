package Blocks;

public abstract class Block {
    private String sprite; // representation of the block on the map
    private boolean solid; // if the block is accessible or not for the player

    // Constructor
    public Block(String sprite, boolean solid) {
        this.sprite = sprite;
        this.solid = solid;
    }

    // Getter
    public String getSprite() {
        return this.sprite;
    }

    // Setter
    public void setSprite(String sprite) {
        this.sprite = sprite;
    }
}
