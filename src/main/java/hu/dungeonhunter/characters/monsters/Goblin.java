package hu.dungeonhunter.characters.monsters;
import hu.dungeonhunter.model.CharacterTypes;
import hu.dungeonhunter.tools.Dice;
import lombok.Getter;
import lombok.Setter;

public class Goblin implements MonstersInterface {

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
    private int initiative;

    @Getter
    @Setter
    int finalMonsterInitiation;

    @Getter
    private final CharacterTypes type = CharacterTypes.GOBLIN;

    public Goblin(int hp) {
        getMonster(hp);
    }

    public Goblin() {
        getMonster();
    }

    @Override
    public void getMonster(int hp) {
        this.hp = hp;
         startValues();
    }

    @Override
    public void getMonster() {
        this.hp = Dice.rollDice(4,3);
        startValues();
    }

    @Override
    public boolean isDefeat(){
       if (hp <= 0){
           System.out.println("You killed the monster!");
           return true;
       }
       return false;
    }

    private void startValues() {
        this.maxDamage = 6;
        this.numOfDices = 1;
        this.initiative = 5;
        System.out.println("The " + type.charType + " have " + hp + " HP and can do " +
                numOfDices + "-" + numOfDices * maxDamage +
                " damage.");
    }

    @Override
    public int getMonsterDamage() {
        return Dice.rollDice(maxDamage, numOfDices);
    }

    @Override
    public void monsterAttackInitiationCalculation() {
        System.out.print("Goblin initiation: " + getInitiative() + " + ");
        int monsterInitRoll = Dice.rollDice(10, 1);
        System.out.println("Final Goblin initiation: " + (getInitiative() + monsterInitRoll));
        finalMonsterInitiation = getInitiative() + monsterInitRoll;

    }
}
