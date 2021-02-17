package hu.dungeonhunter.characters;
import hu.dungeonhunter.tools.Dice;
import lombok.Getter;
import lombok.Setter;

public class Monsters {

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

    public Monsters() {
        this.hp = Dice.rollDice(4,3);
        this.maxDamage = 6;
        this.numOfDices = 1;
        startValues();
        this.defeat = false;
    }

    public void enemyVictory(){
       if (hp <= 0){
           System.out.println("You killed the monster!");
           defeat = true;
       }
    }

    void startValues() {
        System.out.println("The monster have " + hp + " HP and can do " +
                numOfDices + "-" + numOfDices * maxDamage +
                " damage.");
    }

    public int monsterDamage() {
        return Dice.rollDice(maxDamage, numOfDices);
    }
}
