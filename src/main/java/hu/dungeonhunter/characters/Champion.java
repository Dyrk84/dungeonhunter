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
    private int maxDamage;

    @Setter
    @Getter
    private int numOfDices;

    @Setter
    @Getter
    private boolean defeat;

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
        System.out.println("Your champion have " + hp + " HP and can do " +
                numOfDices + "-" + numOfDices * maxDamage +
                " damage.");
    }

    public int championDamage() {
        return Dice.rollDice(maxDamage, numOfDices);
    }

}
