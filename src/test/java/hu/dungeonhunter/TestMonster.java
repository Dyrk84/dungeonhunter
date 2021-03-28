package hu.dungeonhunter;

import hu.dungeonhunter.characters.MonsterFactory;
import hu.dungeonhunter.characters.Character;
import hu.dungeonhunter.model.CharacterTypes;
import hu.dungeonhunter.tools.Fight;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestMonster {

    private MonsterFactory monsterFactory = new MonsterFactory();
    private Fight fight = new Fight();

    @Test
    public void attackInitiationCalculationGoblinTest(){
        Character monster = monsterFactory.getCharacter(CharacterTypes.GOBLIN);
        monster.setInitiation(10);
        fight.setMonster(monster);

        int monsterInitiation = monster.initiationCalculation();

        assertThat(monsterInitiation).isBetween(11,20);
    }

    @Test
    public void attackInitiationCalculationGoblinKingTest(){
        Character monster = monsterFactory.getCharacter(CharacterTypes.GOBLIN);
        monster.setInitiation(10);
        fight.setMonster(monster);

        int monsterInitiation = monster.initiationCalculation();

        assertThat(monsterInitiation).isBetween(11,20);
    }


//    @Test
//    public void accuracyCalculationGoblinTest(){
//        Character monster = characterFactory.getCharacter(CharacterTypes.GOBLIN);
//        monster.setAccuracy(10);
//        fight.setMonster(monster);
//
//        int monsterAccuracy = monster.accuracyCalculation();
//
//        assertThat(monsterAccuracy).isBetween(11,110);
//    }
//
//    @Test
//    public void accuracyCalculationGoblinKingTest(){
//        Character monster = characterFactory.getCharacter(CharacterTypes.GOBLIN);
//        monster.setAccuracy(10);
//        fight.setMonster(monster);
//
//        int monsterAccuracy = monster.accuracyCalculation();
//
//        assertThat(monsterAccuracy).isBetween(11,110);
//    }

}
