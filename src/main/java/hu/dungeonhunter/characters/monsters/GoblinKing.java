package hu.dungeonhunter.characters.monsters;

import hu.dungeonhunter.model.CharacterTypes;
import hu.dungeonhunter.tools.Dice;
import lombok.Getter;
import lombok.Setter;

public class GoblinKing implements MonstersInterface {

    @Setter
    @Getter
    private int finalAccuracy;

    @Setter
    @Getter
    private int initiative;

    @Setter
    @Getter
    private int accuracy;

    @Setter
    @Getter
    private int defense;

    @Setter
    @Getter
    private int hp;

    @Getter
    private int maxDamage;

    @Setter
    @Getter
    private int numOfDices;

    @Getter
    @Setter
    int finalInitiation;

    @Getter
    private final CharacterTypes type = CharacterTypes.GOBLIN_KING;

    public GoblinKing(int hp, int initiative, int accuracy, int defense){
        getMonster(hp, initiative, accuracy, defense);
    }

    public GoblinKing(int hp) {
        getMonster(hp);
    }

    public GoblinKing() {
        getMonster();
    }

    @Override
    public void getMonster(int hp) {
        this.hp = hp;
        startValues();
    }

    @Override
    public void getMonster(int hp, int initiative, int accuracy, int defense) {
        this.hp = hp;
        this.initiative = initiative;
        this.accuracy = accuracy;
        this.defense = defense;
        this.maxDamage = 6;
        this.numOfDices = 1;
        System.out.println("The " + type.charType + " have " + hp + " HP and can do " +
                numOfDices + "-" + numOfDices * maxDamage +
                " damage.");
    }

    @Override
    public void getMonster() {
        this.hp = Dice.rollDice(6, 3) + 6;
        startValues();
    }

    @Override
    public boolean isDefeat() {
        if (hp <= 0) {
            System.out.println("The goblin king is dead!");
            return true;
        }
        return false;
    }

    private void startValues() {
        this.initiative = 7;
        this.accuracy = 15;
        this.defense = 70;
        this.maxDamage = 6;
        this.numOfDices = 2;
        System.out.println("The " + type.charType + " have " + hp + " HP and can do " +
                numOfDices + "-" + numOfDices * maxDamage +
                " damage.");
    }

    @Override
    public int getMonsterDamage() {
        return Dice.rollDice(maxDamage, numOfDices);
    }

    @Override
    public void attackInitiationCalculation() {
        System.out.print("Goblin king initiation: " + getInitiative() + " + ");
        int monsterInitRoll = Dice.rollDice(10, 1);
        System.out.println("Final Goblin king initiation: " + (getInitiative() + monsterInitRoll));
        finalInitiation = getInitiative() + monsterInitRoll;
    }

    public void accuracyCalculation() {
        System.out.print("Goblin king accuracy calculation: " + getAccuracy() + " + ");
        int AccuracyRoll = Dice.rollDice(100, 1);
        System.out.print("Final Goblin king accuracy: " + (getAccuracy() + AccuracyRoll) + " ");
        finalAccuracy = getAccuracy() + AccuracyRoll;
    }
}
