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
    private int accuracy;

    @Setter
    @Getter
    private int defense;

    @Setter
    @Getter
    private int hp;

    @Getter
    private int maxDamage;

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
    private static final CharacterTypes type = CharacterTypes.CHAMPION;

    public Champion() {
        this.hp = START_HP;
        this.initiative = 10;
        this.accuracy = 20;
        this.defense = 80;
        this.maxDamage = 6;
        this.numOfDices = 1;
        championDebut();
    }

    public void enemyVictory() {
        if (hp <= 0) {
            System.out.println("You are soooo dead! Game Over!");
            defeat = true;
        }
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

    public int initiationCalculation() {
        System.out.print("Champion initiation: " + getInitiative() + " + ");
        int champInitRoll = Dice.rollDice(10, 1);
        System.out.println("Final Champion initiation: " + (getInitiative() + champInitRoll));
        return getInitiative() + champInitRoll;
    }

    public int accuracyCalculation() {
        System.out.print("Champion accuracy: " + getAccuracy() + " + ");
        int AccuracyRoll = Dice.rollDice(100, 1);
        System.out.println("Final Champion accuracy: " + (getAccuracy() + AccuracyRoll) + " ");
        return getAccuracy() + AccuracyRoll;
    }
}
