package hu.dungeonhunter.characters;
import hu.dungeonhunter.tools.Dice;
public class Champion {

    static final int START_HP = 20;
    private int championHP;
    private final int championMaxDamage;
    private int championNumOfDices;
    private boolean championVictory;

    public Champion() {
        this.championHP = START_HP;
        this.championMaxDamage = 6;
        this.championNumOfDices = 1;
        championStartValues();
        this.championVictory = false;
    }

    public boolean isChampionVictory() {
        return championVictory;
    }

    public void setChampionVictory(boolean championVictory) {
        this.championVictory = championVictory;
    }

    void championStartValues() {
        System.out.println("Your champion have " + championHP + " HP and can do " +
                championNumOfDices + "-" + championNumOfDices * championMaxDamage +
                " damage.");
    }

    public int getChampionHP() {
        return championHP;
    }

    public void setChampionHP(int playerHP) {
        this.championHP = playerHP;
    }

    public int getChampionMaxDamage() {
        return championMaxDamage;
    }

    public int championDamage() {
        return Dice.rollDice(championMaxDamage, championNumOfDices);
    }

    public int getChampionNumOfDices() {
        return championNumOfDices;
    }
}
