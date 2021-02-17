package hu.dungeonhunter.characters;
import hu.dungeonhunter.tools.Dice;

public class Monsters {
    private int hp;
    private final int maxDamage;
    private int numOfDices;
    private boolean lose;

    public Monsters() {
        this.hp = 20;
        this.maxDamage = 6;
        this.numOfDices = 1;
        startValues();
        this.lose = false;
    }

    public boolean isLose() {
        return lose;
    }

    public void enemyVictory(){
       if (hp <= 0){
           System.out.println("You killed the monster!");
           lose = true;
       }
    }

    void startValues() {
        System.out.println("The monster have " + hp + " HP and can do " +
                numOfDices + "-" + numOfDices * maxDamage +
                " damage.");
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public int monsterDamage() {
        return Dice.rollDice(maxDamage, numOfDices);
    }

    public int getNumOfDices() {
        return numOfDices;
    }
}
