package hu.dungeonhunter.characters;

import hu.dungeonhunter.tools.Dice;

public class Champion {
    private int championHP;
    private final int playerMaxDamage;
    private int numOfDices;

    public Champion() {
        this.championHP = 20;
        this.playerMaxDamage = 6;
        this.numOfDices = 1;
    }

    public int getChampionHP() {
        return championHP;
    }

    public void setChampionHP(int playerHP) {
        this.championHP = playerHP;
    }

    public int getChampionMaxDamage() {
        return playerMaxDamage;
    }

    public int championDamage() {
        return Dice.rollDice(playerMaxDamage, numOfDices);
    }

    public int getNumOfDices() {
        return numOfDices;
    }
}
