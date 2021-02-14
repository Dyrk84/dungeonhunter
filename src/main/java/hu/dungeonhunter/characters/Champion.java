package hu.dungeonhunter.characters;

import hu.dungeonhunter.tools.Dice;

public class Champion {
    private int playerHP;
    private final int playerMaxDamage;

    public Champion(){
        this.playerHP = 20;
        this.playerMaxDamage = 6;
    }

    public int getPlayerHP(){
        return playerHP;
    }

    public void setPlayerHP(int playerHP) {
        this.playerHP = playerHP;
    }

    public int getPlayerMaxDamage() {
        return playerMaxDamage;
    }

    public int playerDamage() {
        return Dice.rollDice(playerMaxDamage, 1);
    }


}
