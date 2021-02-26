package hu.dungeonhunter.model;

import hu.dungeonhunter.utils.Colors;

public enum CharacterTypes {
    CHAMPION(Colors.ANSI_BLUE + "Champion" + Colors.ANSI_RESET),
    GOBLIN(Colors.ANSI_GREEN + "Goblin" + Colors.ANSI_RESET),
    GOBLIN_KING(Colors.ANSI_RED + "Goblin king" + Colors.ANSI_RESET);

    public final String charType;

    CharacterTypes(String charType) {
        this.charType = charType;
    }
}
