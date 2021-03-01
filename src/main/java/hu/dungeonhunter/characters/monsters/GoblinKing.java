package hu.dungeonhunter.characters.monsters;

import hu.dungeonhunter.characters.Character;
import hu.dungeonhunter.model.CharacterTypes;
import hu.dungeonhunter.tools.Dice;
import lombok.Getter;
import lombok.Setter;

public class GoblinKing implements Character {

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

    private static final CharacterTypes type = CharacterTypes.GOBLIN_KING;

    public GoblinKing() {
        this.hp = Dice.rollDice(6, 3) + 6;
        startValues();
    }

    private void startValues() {
        this.initiation = 7;
        this.accuracy = 15;
        this.defense = 70;
        this.maxDamage = 6;
        this.numOfDices = 2;
        System.out.println("The " + type.charType + " have " + hp + " HP and can do " +
                numOfDices + "-" + numOfDices * maxDamage +
                " damage.");
    }

    @Override
    public boolean isDefeated() {
        if (hp <= 0) {
            System.out.println("The goblin king is dead!");
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
        System.out.print("Goblin king initiation: " + getInitiation() + " + ");
        int monsterInitRoll = Dice.rollDice(10, 1);
        System.out.println("Final Goblin king initiation: " + (getInitiation() + monsterInitRoll));
        return getInitiation() + monsterInitRoll;
    }

    @Override
    public int accuracyCalculation(int accuracyRoll) {
        System.out.print("Goblin king accuracy: " + getAccuracy() + " + ");
        System.out.println("Final Goblin king accuracy: " + (getAccuracy() + accuracyRoll) + " ");
        return getAccuracy() + accuracyRoll;
    }
}
