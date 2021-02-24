package hu.dungeonhunter.characters.champion;

import hu.dungeonhunter.model.CharacterTypes;
import hu.dungeonhunter.tools.Dice;
import hu.dungeonhunter.tools.TextSeparator;
import lombok.Getter;
import lombok.Setter;

public class Champion {

    static final int START_HP = 20;

    @Setter
    @Getter
    private int finalAccuracy;

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

    @Setter
    @Getter
    private boolean defeat;

    @Setter
    @Getter
    private int healingPotionCounter = 1;

    @Setter
    @Getter
    private int initiative;

    @Getter
    @Setter
    int finalChampionInitiation;

    @Getter
    private final CharacterTypes type = CharacterTypes.CHAMPION;

    public Champion() {
        startValues(START_HP);
    }

    public Champion(int startHp) {
        startValues(startHp);
    }

    public Champion(int startHP, int initiative, int accuracy, int defense) {
        startValues(startHP, initiative, accuracy, defense);
    }

    public void enemyVictory() {
        if (hp <= 0) {
            System.out.println("You are soooo dead! Game Over!");
            defeat = true;
        }
    }

    private void startValues(int startHP, int initiative, int accuracy, int defense){
        maxDamage = 6;
        numOfDices = 1;
        championDebut();
    }

    private void startValues(int startHp) {
        this.hp = startHp;
        this.initiative = 10;
        this.accuracy = 20;
        this.defense = 80;
        this.maxDamage = 6;
        this.numOfDices = 1;
        championDebut();
    }

    public void championDebut() {
        System.out.println("Your champion have " + hp + " HP and can do " +
                numOfDices + "-" + numOfDices * maxDamage +
                " damage.");
        TextSeparator.format("");
    }

    public int championDamage() {
        return Dice.rollDice(maxDamage, numOfDices);
    }

    public void drinkAHealingPotion() {
        if (getHealingPotionCounter() <= 0) {
            System.out.println("You have no more healing potions!");
        } else {
            System.out.println("You drink a healing potion.");
            int healingAmount = Dice.rollDice(4, 2);
            setHp(getHp() + healingAmount);
            System.out.println("You healed " + healingAmount + "hp. You have now " + getHp() + "hp.");
            if (getHp() > 40) {
                System.out.println("Your Hp cannot be more than 40!");
                setHp(40);
            }
            setHealingPotionCounter(getHealingPotionCounter() - 1);
        }
    }

    public void championAttackInitiationCalculation() {
        System.out.print("Champion initiation calculation: " + getInitiative() + " + ");
        int champInitRoll = Dice.rollDice(10, 1);
        System.out.println("Final Champion initiation: " + (getInitiative() + champInitRoll));
        finalChampionInitiation = getInitiative() + champInitRoll;
    }

    public void accuracyCalculation() {
        System.out.print("Champion accuracy calculation: " + getAccuracy() + " + ");
        int AccuracyRoll = Dice.rollDice(100, 1);
        System.out.print("Final Champion accuracy: " + (getAccuracy() + AccuracyRoll) + " ");
        finalAccuracy = getAccuracy() + AccuracyRoll;
    }
}
