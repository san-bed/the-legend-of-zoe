package Main;

public class Item {
    private String contents;

    public Item(String contents) {
        this.contents = contents;
    }

    // Getter
    public String getContents() {
        return contents;
    }

    /**
     * Function that allows the player to consume a certain item.
     */
    public void loot() {
        switch(this.getContents()) {
            case "coeur":
                LegendOfZoe.getZoe().setRelativeHp(1);
                System.out.println("Vous trouvez un coeur!");
                break;
            case "potionvie":
                LegendOfZoe.getZoe().setHp(LegendOfZoe.ZOE_MAX_HP);
                System.out.println("Vous trouvez une potion de vie!");
                break;
            case "hexaforce":
                LegendOfZoe.setNbHexaforce(LegendOfZoe.getNbHexaforce() + 1);
                System.out.println("Vous trouvez un morceau d'Hexaforce!");
                break;
        }
    }
}
