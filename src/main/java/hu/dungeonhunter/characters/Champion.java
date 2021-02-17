package hu.dungeonhunter.characters;
import hu.dungeonhunter.tools.Dice;
public class Champion {

    static final int START_HP = 20;
    private int hp;
    private final int maxDamage;
    private int numOfDices;
    private boolean lose;

    public Champion() {
        this.hp = START_HP;
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
            System.out.println("You are soooo dead! Game Over!");
            lose = true;
        }
    }

    void startValues() {
        System.out.println("Your champion have " + hp + " HP and can do " +
                numOfDices + "-" + numOfDices * maxDamage +
                " damage.");
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int playerHP) {
        this.hp = playerHP;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public int championDamage() {
        return Dice.rollDice(maxDamage, numOfDices);
    }

    public int getNumOfDices() {
        return numOfDices;
    }
}
