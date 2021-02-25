package hu.dungeonhunter.characters.monsters;

import hu.dungeonhunter.model.CharacterTypes;
import hu.dungeonhunter.tools.Dice;
import lombok.Getter;
import lombok.Setter;

public class Goblin implements MonstersInterface {

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

    @Setter
    @Getter
    private int maxDamage;

    @Setter
    @Getter
    private int numOfDices;

    @Getter
    int finalInitiation;

    @Getter
    private final CharacterTypes type = CharacterTypes.GOBLIN;

    public Goblin(int hp) {
        getMonster(hp);
    }

    public Goblin() {
        getMonster();
    }

    public Goblin(int hp, int initiative, int accuracy, int defense, int numOfDices, int maxDamage){
        getMonster(hp, initiative, accuracy, defense, numOfDices, maxDamage);
    }

    @Override
    public void getMonster(int hp) {
        this.hp = hp;
        startValues();
    }

    @Override
    public void getMonster(int hp, int initiative, int accuracy, int defense, int numOfDices, int maxDamage) {
        this.hp = hp;
        this.initiative = initiative;
        this.accuracy = accuracy;
        this.defense = defense;
        this.maxDamage = maxDamage;
        this.numOfDices = numOfDices;
    }

    @Override
    public void getMonster() {
        this.hp = Dice.rollDice(4, 3);
        startValues();
    }

    @Override
    public boolean isDefeat() {
        if (hp <= 0) {
            System.out.println("You killed the monster!");
            return true;
        }
        return false;
    }

    private void startValues() {
        this.initiative = 5;
        this.accuracy = 10;
        this.defense = 50;
        this.maxDamage = 6;
        this.numOfDices = 1;
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
        System.out.print("Goblin initiation: " + getInitiative() + " + ");
        int monsterInitRoll = Dice.rollDice(10, 1);
        System.out.println("Final Goblin initiation: " + (getInitiative() + monsterInitRoll));
        finalInitiation = getInitiative() + monsterInitRoll;
    }

    public void accuracyCalculation() {
        System.out.print("Goblin accuracy calculation: " + getAccuracy() + " + ");
        int AccuracyRoll = Dice.rollDice(100, 1);
        System.out.print("Final Goblin accuracy: " + (getAccuracy() + AccuracyRoll) + " ");
        finalAccuracy = getAccuracy() + AccuracyRoll;
    }
}
