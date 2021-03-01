package hu.dungeonhunter.characters.monsters;

import hu.dungeonhunter.characters.Character;
import hu.dungeonhunter.model.CharacterTypes;
import hu.dungeonhunter.tools.Dice;
import lombok.Getter;
import lombok.Setter;

public class Goblin implements Character {

    @Setter
    @Getter
    private int initiation;

    @Setter
    @Getter
    private int accuracy;

    @Getter
    private int damage;

    @Setter
    @Getter
    private int defense;

    @Setter
    @Getter
    private int hp;

    @Setter
    @Getter
    private int maxDamage;

    @Setter
    @Getter
    private int numOfDices;

    private static final CharacterTypes type = CharacterTypes.GOBLIN;

    public Goblin() {
        this.hp = Dice.rollDice(4, 3);
        startValues();
    }

    private void startValues() {
        this.initiation = 5;
        this.accuracy = 10;
        this.defense = 50;
        this.maxDamage = 6;
        this.numOfDices = 1;
        System.out.println("The " + type.charType + " have " + hp + " HP and can do " +
                numOfDices + "-" + numOfDices * maxDamage +
                " damage.");
    }

    @Override
    public boolean isDefeated() {
        if (hp <= 0) {
            System.out.println("You killed the monster!");
            return true;
        }
        return false;
    }

    @Override
    public CharacterTypes getType() {
        return type;
    }

    @Override
    public int damage() {
        damage = Dice.rollDice(maxDamage, numOfDices);
        return damage;
    }

    @Override
    public int initiationCalculation() {
        System.out.print("Goblin initiation: " + this.getInitiation() + " + ");
        int monsterInitRoll = Dice.rollDice(10, 1);
        System.out.println("Final Goblin initiation: " + (this.getInitiation() + monsterInitRoll));
        return this.getInitiation() + monsterInitRoll;
    }

    @Override
    public int accuracyCalculation(int accuracyRoll) {
        System.out.print("Goblin accuracy: " + getAccuracy() + " + ");
        System.out.println("Final Goblin accuracy: " + (getAccuracy() + accuracyRoll) + " ");
        return getAccuracy() + accuracyRoll;
    }
}
