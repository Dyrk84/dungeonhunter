package hu.dungeonhunter.model;

public enum CharacterTypes {
    CHAMPION("Champion"),
    GOBLIN("Goblin"),
    GOBLIN_KING("Goblin king");

    public final String charType;

    CharacterTypes(String charType) {
        this.charType = charType;
    }
}
