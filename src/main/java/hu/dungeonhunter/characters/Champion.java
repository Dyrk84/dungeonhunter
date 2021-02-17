package hu.dungeonhunter.characters;
import hu.dungeonhunter.tools.Dice;
import lombok.Getter;
import lombok.Setter;

public class Champion {

    static final int START_HP = 20;

    @Setter
    @Getter
    private int hp;

    @Getter
    private final int maxDamage;

    @Setter
    @Getter
    private int numOfDices;

    @Setter
    @Getter
    private boolean defeat;

    public Champion() {
        this.hp = START_HP;
        this.maxDamage = 6;
        this.numOfDices = 1;
        startValues();
        this.defeat = false;
    }

    public void enemyVictory(){
        if (hp <= 0){
            System.out.println("You are soooo dead! Game Over!");
            defeat = true;
        }
    }

    void startValues() {
        System.out.println("Your champion have " + hp + " HP and can do " +
                numOfDices + "-" + numOfDices * maxDamage +
                " damage.");
    }

    public int championDamage() {
        return Dice.rollDice(maxDamage, numOfDices);
    }

}
