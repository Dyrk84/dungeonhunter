package hu.dungeonhunter.characters.monsters;

import hu.dungeonhunter.model.CharacterTypes;
import hu.dungeonhunter.tools.Dice;
import lombok.Getter;
import lombok.Setter;

public class GoblinKing implements MonstersInterface {
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
    private final CharacterTypes type = CharacterTypes.GOBLIN_KING;

    public GoblinKing(int hp) {
        getMonster(hp);
    }

    public GoblinKing() {
        getMonster();
    }

    @Override
    public void getMonster(int hp) {
        this.hp = hp;
        startValues();
    }

    @Override
    public void getMonster() {
        this.hp = Dice.rollDice(6, 3) + 6;
        startValues();
    }

    @Override
    public boolean isDefeat() {
        if (hp <= 0) {
            System.out.println("The goblin king is dead!");
            return true;
        }
        return false;
    }

    private void startValues() {
        this.maxDamage = 6;
        this.numOfDices = 2;
        this.initiative = 7;
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
        System.out.print("Goblin king initiation: " + getInitiative() + " + ");
        int monsterInitRoll = Dice.rollDice(10, 1);
        System.out.println("Final Goblin king initiation: " + (getInitiative() + monsterInitRoll));
        finalMonsterInitiation = getInitiative() + monsterInitRoll;
    }
}
