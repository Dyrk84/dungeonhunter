package hu.dungeonhunter.characters;
import hu.dungeonhunter.tools.Dice;
import lombok.Getter;
import lombok.Setter;

public class Monsters {

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

    public Monsters() {
        initMonster(Dice.rollDice(4,3));
    }

    public Monsters(int hp) {
        initMonster(hp);
    }

    private void initMonster(int hp) {
        this.hp = hp;
        this.maxDamage = 6;
        this.numOfDices = 1;
        startValues();
    }

    public void enemyVictory(){
       if (hp <= 0){
           System.out.println("You killed the monster!");
           defeat = true;
       }
    }

    private void startValues() {
        System.out.println("The monster have " + hp + " HP and can do " +
                numOfDices + "-" + numOfDices * maxDamage +
                " damage.");
    }

    public int monsterDamage() {
        return Dice.rollDice(maxDamage, numOfDices);
    }
}
