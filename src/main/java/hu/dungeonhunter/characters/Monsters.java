package hu.dungeonhunter.characters;

import hu.dungeonhunter.tools.Dice;

public class Monsters {
    private int monsterHP;
    private final int monsterMaxDamage;
    private int numOfDices;


    public Monsters() {
        this.monsterHP = 20;
        this.monsterMaxDamage = 6;
        this.numOfDices = 1;

    }

    public int getMonsterHP() {
        return monsterHP;
    }

    public void setMonsterHP(int monsterHP) {
        this.monsterHP = monsterHP;
    }

    public int getMonsterMaxDamage() {
        return monsterMaxDamage;
    }

    public int monsterDamage() {
        return Dice.rollDice(monsterMaxDamage, numOfDices);
    }

}
