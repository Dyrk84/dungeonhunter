package hu.dungeonhunter;

import hu.dungeonhunter.characters.champion.Champion;
import hu.dungeonhunter.tools.Dice;
import hu.dungeonhunter.tools.Fight;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestChampion {
    private Fight fight = new Fight();
    private Champion champion = new Champion();

    @Test
    public void attackInitiationCalculationChampionTest(){
        champion.setInitiation(10);
        fight.setChampion(champion);

        int championInitiation = champion.initiationCalculation();

        assertThat(championInitiation).isBetween(11,20);
    }

    @Test
    public void accuracyCalculationChampionTest(){
        champion.setAccuracy(10);
        fight.setChampion(champion);

        int championAccuracy = champion.accuracyCalculation(Dice.rollDice(1, 1));

        assertThat(championAccuracy).isEqualTo(11);
    }
}
