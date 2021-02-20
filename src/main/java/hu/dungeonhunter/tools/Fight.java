package hu.dungeonhunter.tools;

import hu.dungeonhunter.characters.champion.Champion;
import hu.dungeonhunter.characters.monsters.MonsterFactory;
import hu.dungeonhunter.characters.monsters.MonstersInterface;
import hu.dungeonhunter.model.CharacterTypes;
import lombok.Getter;
import lombok.Setter;

public class Fight {

    @Setter
    private Champion champion = new Champion();

    private final MonsterFactory monsterFactory = new MonsterFactory();

    private MonstersInterface monster;

    @Setter
    @Getter
    private int monsterCounter;

    @Getter
    private int killedMonsterCounter = 0;

    @Setter
    @Getter
    private int randomEnemy;

    @Getter
    private boolean runningAwayFromGoblinKingTest;

    @Getter
    boolean goblinKingIsDeadTest = true;

    public Fight() {
        setMonsterCounter(Dice.rollDice(6, 2));
        System.out.println(monsterCounter + " monsters are in the Dungeon!");
        monsterIncomingOrWin();
    }

    public Fight(int numOfMonsters) {  //for test
        setMonsterCounter(numOfMonsters);
        System.out.println(monsterCounter + " monsters are in the Dungeon!");
        monsterIncomingOrWin();
    }

    public void goblinKingDamage() {
        System.out.println("The goblin king steps out from the darkness and throws you with a big rock!");
        champion.setHp(champion.getHp() - Dice.rollDice(6, 1));
        System.out.println("You have " + champion.getHp() + " hp");
        champion.enemyVictory();
    }

    public void runningAway() {
        if (monster.getType().equals(CharacterTypes.GOBLIN_KING)) {
            System.out.println("You can't run away from the " + monster.getType().charType + " !");
            runningAwayFromGoblinKingTest = true; //to test
        } else {
            System.out.println("The monster hits you a last time before you can run away: ");
            champion.setHp(champion.getHp() - monster.getMonsterDamage());
            System.out.println("Champion have now " + champion.getHp() + " hit points");
            champion.enemyVictory();
            if (!champion.isDefeat()) {
                monsterCounter--;
                System.out.println("Your escape was successful! You can go further in the cave.");
                monsterIncomingOrWin();
            }
        }
    }

    private void monsterIncomingOrWin() {
        if (monsterCounter != 0) {
            System.out.println("Still left in the cave " + monsterCounter + " !");
            System.out.print("Something is coming! ");
            randomEnemy = Dice.rollDice(monsterCounter, 1);
            monsterCaller();
        } else
            System.out.println("The Dungeon is clear! You killed " + killedMonsterCounter +
                    " monster (not counting the many mothers and children), you win!");
    }

    public MonstersInterface monsterCaller() {
        if (randomEnemy == 1) {
            monster = monsterFactory.getMonster(CharacterTypes.GOBLIN_KING);
            goblinKingDamage();
        } else {
            System.out.println("A goblin steps out from darkness!");
            monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        }
        return monster;
    }

    public void monsterAttack() {
        System.out.print("Monster attack: ");
        champion.setHp(champion.getHp() - monster.getMonsterDamage());
        System.out.println("Champion have now " + champion.getHp() + " hit points");
        champion.enemyVictory();
    }

    public void championAttack() {
        System.out.print("Champion attack: ");
        monster.setHp(monster.getHp() - champion.championDamage());
        System.out.println("The " + monster.getType().charType + " have now " + monster.getHp() + " hit points");
        if (monster.getType().equals(CharacterTypes.GOBLIN_KING) && monster.getHp() <= 0) {
            System.out.println("The goblin king is dead! You win!");
            goblinKingIsDeadTest = true; //toTest
        } else if (!monster.getDefeat()) {
            monsterAttack();
        } else {
            killedMonsterCounter++;
            monsterCounter--;
            System.out.println("You found a healing potion!");
            if (champion.getHealingPotionCounter() < 5) {
                champion.setHealingPotionCounter(champion.getHealingPotionCounter() + 1);
            } else {
                System.out.println("You can't have more than 5 healing potions!");
            }
            monsterIncomingOrWin();
        }
    }

    public boolean nextTurn() {
        if (champion.getHp() > 0 && monster.getHp() > 0 && monsterCounter > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Champion getChampion() {
        return champion;
    }
}