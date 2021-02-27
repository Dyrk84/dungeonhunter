package hu.dungeonhunter;

import hu.dungeonhunter.characters.monsters.MonsterFactory;
import hu.dungeonhunter.characters.monsters.MonstersInterface;
import hu.dungeonhunter.model.CharacterTypes;
import hu.dungeonhunter.tools.Fight;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestMonster {

    private MonsterFactory monsterFactory = new MonsterFactory();
    private Fight fight = new Fight();

    @Test
    public void attackInitiationCalculationGoblinTest(){
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        monster.setInitiation(10);
        fight.setMonster(monster);

        int monsterInitiation = monster.initiationCalculation();

        assertThat(monsterInitiation).isBetween(11,20);
    }

    @Test
    public void attackInitiationCalculationGoblinKingTest(){
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        monster.setInitiation(10);
        fight.setMonster(monster);

        int monsterInitiation = monster.initiationCalculation();

        assertThat(monsterInitiation).isBetween(11,20);
    }


    @Test
    public void accuracyCalculationGoblinTest(){
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        monster.setAccuracy(10);
        fight.setMonster(monster);

        int monsterAccuracy = monster.accuracyCalculation();

        assertThat(monsterAccuracy).isBetween(11,110);
    }

    @Test
    public void accuracyCalculationGoblinKingTest(){
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        monster.setAccuracy(10);
        fight.setMonster(monster);

        int monsterAccuracy = monster.accuracyCalculation();

        assertThat(monsterAccuracy).isBetween(11,110);
    }

}
