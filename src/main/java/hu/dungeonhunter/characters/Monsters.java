package hu.dungeonhunter.characters;
import hu.dungeonhunter.tools.Dice;
import hu.dungeonhunter.tools.Fight;
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

    @Setter
    @Getter
    private String type;

//    public Monsters() {
//        initMonster(Dice.rollDice(4,3));
//    }

    public Monsters(int hp) {
        initMonster(hp);
    }

    private void initMonster(int hp) {
        this.hp = hp;
        this.maxDamage = 6;
        this.numOfDices = 1;
        // startValues();
    }

    public Monsters(String type){
        if (type.equals("goblin king"))
        goblinKing(type);
        if (type.equals("goblin"))
            goblin(type);
    }

    private void goblin(String type){
        this.type = "goblin";
        this.hp = Dice.rollDice(4,3);
        this.maxDamage = 6;
        this.numOfDices = 1;
        startValues();
    }

    private void goblinKing(String type){
        this.type = "goblin king";
        this.hp = Dice.rollDice(6,3) + 6;
        this.maxDamage = 6;
        this.numOfDices = 2;
        startValues();
    }

    public Monsters(int hp, String type){
        goblinKingTest(hp,type);
    }

    private void goblinKingTest(int hp, String type){
        this.hp = hp;
        this.type = "goblin king";
        this.maxDamage = 6;
        this.numOfDices = 2;
        startValues();
    }

    public void enemyVictory(){
       if (hp <= 0){
           System.out.println("You killed the monster!");
           defeat = true;
       }
    }

    private void startValues() {
        System.out.println("The " + type + " have " + hp + " HP and can do " +
                numOfDices + "-" + numOfDices * maxDamage +
                " damage.");
    }

    public int monsterDamage() {
        return Dice.rollDice(maxDamage, numOfDices);
    }
}
