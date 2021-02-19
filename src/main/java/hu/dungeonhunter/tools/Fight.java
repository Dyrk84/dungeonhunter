package hu.dungeonhunter.tools;

import hu.dungeonhunter.characters.Champion;
import hu.dungeonhunter.characters.Monsters;
import lombok.Getter;
import lombok.Setter;

public class Fight {

    @Setter
    private Champion champion = new Champion();

    @Setter
    private Monsters monster = monsterCaller();

    @Setter
    @Getter
    private int monsterCounter;

    @Getter
    private int killedMonsterCounter = 0;

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

    public void runningAway() {
        System.out.println("The monster hits you a last one before you can run away: ");
        champion.setHp(champion.getHp() - monster.monsterDamage());
        System.out.println("Champion have now " + champion.getHp() + " hit points");
        champion.enemyVictory();
        if (!champion.isDefeat()) {
            monsterCounter--;
            System.out.println("Your escape was successful! You can go further in the cave.");
            monsterIncomingOrWin();
        }

    }

    private void monsterIncomingOrWin() {
        if (monsterCounter != 0) {
            System.out.println("Still left in the cave " + monsterCounter + " !");
            monsterCaller();
        } else
            System.out.println("The Dungeon is clear! You killed " + killedMonsterCounter +
                    " monster (not counting the many mothers and children), you win!");
    }

    public Monsters monsterCaller() {
        if (monsterCounter != 0) {
            System.out.println("A monster steps out from darkness!");
            monster = new Monsters();
        }
        return monster;
    }

    public void monsterAttack() {
        System.out.print("Monster attack: ");
        champion.setHp(champion.getHp() - monster.monsterDamage());
        System.out.println("Champion have now " + champion.getHp() + " hit points");
        champion.enemyVictory();
    }

    public void championAttack() {
        System.out.print("Champion attack: ");
        monster.setHp(monster.getHp() - champion.championDamage());
        System.out.println("Monster have now " + monster.getHp() + " hit points");
        monster.enemyVictory();
        if (!monster.isDefeat()) {
            monsterAttack();
        } else {
            killedMonsterCounter++;
            monsterCounter--;
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
}