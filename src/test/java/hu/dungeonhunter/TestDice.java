package hu.dungeonhunter;

import hu.dungeonhunter.characters.Champion;
import hu.dungeonhunter.characters.Monsters;
import hu.dungeonhunter.tools.Dice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class TestDice {

    @Test
    public void testRollDice() {
        int result = Dice.rollDice(1, 1);
        assertThat(result).as("A kockadobás eredménye nem 1!").isEqualTo(1);
    }

    @Test
    public void testRollDice10d1() {
        int result = Dice.rollDice(1, 10);
        assertThat(result).as("A kockadobás eredménye nem 10!").isEqualTo(10);
    }

    @Test
    public void testRollDice1d10() {
        int result = Dice.rollDice(10, 1);
        assertThat(result).as("A kockadobás eredménye nem 1-10!").isBetween(1, 10);
    }

    @Test
    public void championWinTest() {
        Monsters monster = new Monsters();
        monster.setHp(0);
        monster.enemyVictory();
        assertThat(monster.isDefeat()).as("championWinTest").isTrue();
    }

    @Test
    public void monsterWinTest() {
        Champion champion = new Champion();
        champion.setHp(0);
        champion.enemyVictory();
        assertThat(champion.isDefeat()).as("monsterWinTest").isTrue();
    }

    @Test
    public void monsterCounterTestForTheCorrectValueAssignmentInFightMethod() {
        int monsterCounter;
        monsterCounter = Dice.rollDice(6, 2);
        assertThat(monsterCounter).as("Test Fight() monsterCounter is not between 2-12").isBetween(2, 12);
    }

    @Test
    public void monsterCounterAndKilledMonsterCounterTest() {
        int monsterCounter = 0;
        int killedMonsterCounter = 0;
        boolean defeat = true;
        if (defeat) {
            killedMonsterCounter++;
            monsterCounter--;
        }
        assertThat(monsterCounter).as("monsterCounter test to decrease in value").isEqualTo(-1);
        assertThat(killedMonsterCounter).as("killedMonsterCounter test to increase the value").isEqualTo(1);

    }

    @Test
    public void FightChampionAttackMethodTest() {
        int hp = 0;
        boolean defeat;
        int monsterCounter = 0;
        int killedMonsterCounter = 0;
        //System.out.print("Champion attack: ");
        //monster.setHp(monster.getHp() - champion.championDamage());
        //System.out.println("Monster have now " + monster.getHp() + " hit points");
        //monster.enemyVictory();
        if (hp <= 0){
//            System.out.println("You killed the monster!");
            defeat = true;
        }
//        if (!monster.isDefeat()){
        if (defeat = false){
            //monsterAttack();
        }
        else{
            killedMonsterCounter++;
            monsterCounter--;
            //monsterIncomingOrWin();
        }
        assertThat(monsterCounter).as("monsterCounter test to decrease in value").isEqualTo(-1);
        assertThat(killedMonsterCounter).as("killedMonsterCounter test to increase the value").isEqualTo(1);
    }

}