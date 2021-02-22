package hu.dungeonhunter.characters.champion;
import hu.dungeonhunter.model.CharacterTypes;
import hu.dungeonhunter.tools.Dice;
import lombok.Getter;
import lombok.Setter;

public class Champion {

    static final int START_HP = 20;

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
    private final CharacterTypes type = CharacterTypes.CHAMPION;

    public Champion() {
        startValues(START_HP);
    }

    public Champion(int startHp) {
        startValues(startHp);
    }

    public void enemyVictory(){
        if (hp <= 0){
            System.out.println("You are soooo dead! Game Over!");
            defeat = true;
        }
    }

    private void startValues(int startHp) {
        this.hp = startHp;
        this.maxDamage = 6;
        this.numOfDices = 1;
        this.initiative = 10;
        championDebut();
    }

    public void championDebut() {
        System.out.println("Your champion have " + hp + " HP and can do " +
                numOfDices + "-" + numOfDices * maxDamage +
                " damage.");
        for (int i = 0; i < 70; i++) System.out.print("*");
        System.out.println("*");
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
}
