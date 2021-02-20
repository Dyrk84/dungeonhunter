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
    private CharacterTypes type;

    public Goblin(int hp) {
        getMonster(hp);
    }

    public Goblin() {
        getMonster();
    }

    @Override
    public void getMonster(int hp) {
        this.type = CharacterTypes.GOBLIN;
        this.hp = hp;
        this.maxDamage = 6;
        this.numOfDices = 1;
         startValues();
    }

    @Override
    public void getMonster() {
        this.type = CharacterTypes.GOBLIN;
        this.hp = Dice.rollDice(4,3);
        this.maxDamage = 6;
        this.numOfDices = 1;
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
        System.out.println("The " + type.charType + " have " + hp + " HP and can do " +
                numOfDices + "-" + numOfDices * maxDamage +
                " damage.");
    }

    @Override
    public int getMonsterDamage() {
        return Dice.rollDice(maxDamage, numOfDices);
    }
}
