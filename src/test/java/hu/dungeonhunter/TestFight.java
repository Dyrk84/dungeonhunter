package hu.dungeonhunter;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import hu.dungeonhunter.characters.champion.Champion;
import hu.dungeonhunter.tools.Fight;
import hu.dungeonhunter.model.CharacterTypes;
import hu.dungeonhunter.characters.monsters.MonstersInterface;
import hu.dungeonhunter.characters.monsters.MonsterFactory;

public class TestFight {

    private SoftAssertions softly = new SoftAssertions(); // akkor van értelme amikor több assert van egy metódusban,
    // az összes hibásat kiadja, nem csak az elsőt. softly.assertAll(); //ezzel gyűjti be a hibás asserteket, ha ez nincs, akkor nem dobja fel a hibákat!!!

    private MonsterFactory monsterFactory = new MonsterFactory();
    private Fight fight = new Fight();
    private Champion champion = new Champion();

    //segítő változók
    int toTestMonsterCounter = fight.getMonsterCounter();
    int toTestKilledMonsterCounter = monsterFactory.getKilledMonsterCounter();
    int toTestHealingPotionCounter = champion.getHealingPotionCounter();

    //segítő meghívások
    private void defeatedGoblin() {
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        monster.setHp(0);
        fight.setMonster(monster);
    }

    private void defeatedGoblinKing() {
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN_KING);
        monster.setHp(0);
        fight.setMonster(monster);
    }

    private void defeatedChampion() {
        Champion champion = new Champion();
        champion.setHp(0);
        fight.setChampion(champion);
    }

    private void lowHpGoblin() {
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        monster.setHp(1);
        fight.setMonster(monster);
    }

    private void lowHpGoblinKing() {
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN_KING);
        monster.setHp(1);
        fight.setMonster(monster);
    }

    private void lowHpChampion() {
        Champion champion = new Champion();
        champion.setHp(1);
        fight.setChampion(champion);
    }

    private void highHpGoblin() {
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        monster.setHp(1000);
        fight.setMonster(monster);
    }

    private void highHpChampion() {
        Champion lowHpChampion = new Champion();
        champion.setHp(1000);
        fight.setChampion(lowHpChampion);
    }
    //segítő assertek

    private void ifAssertThatIsChampionStillAlive() {
        softly.assertThat(fight.getChampion().getHp()).as("Champion still alive").isGreaterThan(0);
        softly.assertThat(fight.nextTurn()).as("There should be no next turn.").isTrue();
    }

    private void ifAssertThatIsChampionDefeated() {
        softly.assertThat(fight.getChampion().getHp()).as("Champion defeated").isLessThan(1);
        softly.assertThat(fight.nextTurn()).as("There should be no next turn.").isFalse();
    }

    private void ifAssertThatIsMonsterStillAlive() {
        softly.assertThat(fight.getMonster().getHp()).as("Goblin or Goblin king still alive after damage").isGreaterThan(0);
    }

    private void ifAssertThatIsMonsterDefeated() {
        softly.assertThat(fight.getMonster().getHp()).as("Goblin or Goblin King defeated").isLessThan(1);
    }

    @Test
    public void goblinKingDamageTest() {
        lowHpChampion();

        fight.goblinKingDamage();

        ifAssertThatIsChampionDefeated();
        softly.assertAll();
    }

    @Test
    public void runningAwayFromGoblinKingTest() {
        lowHpGoblinKing();
        lowHpChampion();

        fight.runningAway();

        ifAssertThatIsChampionStillAlive();
        softly.assertAll();
    }

    @Test
    public void runningAwayWithGoblinHitChampStillAliveTest() {
        highHpChampion();
        lowHpGoblin();

        fight.runningAway();

        ifAssertThatIsChampionStillAlive();
        softly.assertThat(fight.getMonsterCounter()).isEqualTo(toTestMonsterCounter - 1);
        softly.assertAll();
    }

    @Test
    public void runningAwayWithGoblinHitChampDefeatedTest() {
        lowHpChampion();
        lowHpGoblin();


        fight.runningAway();

        ifAssertThatIsChampionDefeated();
        softly.assertThat(fight.getMonsterCounter()).isEqualTo(toTestMonsterCounter);
        softly.assertAll();
    }

    @Test
    public void monsterIncomingRandomEnemyTest() {
        fight.setMonsterCounter(10);

        fight.monsterIncoming();

        assertThat(fight.getRandomEnemy()).isBetween(1, 10);
    }

    @Test
    public void monsterCallerGoblinKingTest() {
        fight.setRandomEnemy(1);
        highHpGoblin();

        fight.monsterCaller();

        assertThat(fight.getMonster().getNumOfDices()).as("goblinKing have 2 numOfDices").isEqualTo(2);
    }

    @Test
    public void monsterCallerGoblinTest() {
        fight.setRandomEnemy(10);
        highHpGoblin();

        fight.monsterCaller();

        assertThat(fight.getMonster().getNumOfDices()).as("goblin have 1 numOfDices").isEqualTo(1);
    }

    @Test
    public void battleMonsterWithHighInitiationAndHighAccuracyTest() {
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        monster.setInitiative(1000);
        monster.setAccuracy(1000);
        fight.setMonster(monster);
        lowHpChampion();

        fight.battle();

        ifAssertThatIsChampionDefeated();
        ifAssertThatIsMonsterStillAlive();
        softly.assertThat(fight.getMonsterFactory().getKilledMonsterCounter()).isEqualTo(toTestKilledMonsterCounter);
        softly.assertThat(fight.getMonsterCounter()).isEqualTo(toTestMonsterCounter);
        softly.assertThat(fight.getChampion().getHealingPotionCounter()).isEqualTo(toTestHealingPotionCounter);
        softly.assertThat(fight.nextTurn()).isFalse();
        softly.assertAll();
    }

    @Test
    public void battleMonsterWithLowHpHighInitiationAndChampWithHighDefenseAndAccuracyTest() {
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        monster.setHp(1);
        monster.setInitiative(1000);
        fight.setMonster(monster);
        Champion champion = new Champion();
        champion.setHp(1 + fight.getMonster().getMaxDamage());
        champion.setAccuracy(1000);
        champion.setDefense(1000);
        fight.setChampion(champion);

        fight.battle();

        ifAssertThatIsChampionStillAlive();
        softly.assertThat(fight.getMonsterFactory().getKilledMonsterCounter()).isEqualTo(toTestKilledMonsterCounter + 1);
        softly.assertThat(fight.getMonsterCounter()).isEqualTo(toTestMonsterCounter - 1);
        softly.assertThat(fight.getChampion().getHealingPotionCounter()).isEqualTo(toTestHealingPotionCounter + 1);
        ifAssertThatIsMonsterStillAlive(); //this is the new monster
        softly.assertThat(fight.nextTurn()).isTrue();
        softly.assertAll();
    }

    @Test
    public void battleMonsterWithHighInitiationAndHighDefenseAndChampWithHighDefenseTest() {
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        monster.setInitiative(1000);
        monster.setDefense(1000);
        fight.setMonster(monster);

        Champion champion = new Champion();
        champion.setHp(1);
        champion.setDefense(1000);
        fight.setChampion(champion);

        fight.battle();

        ifAssertThatIsChampionStillAlive();
        ifAssertThatIsMonsterStillAlive();
        softly.assertThat(fight.getMonsterFactory().getKilledMonsterCounter()).isEqualTo(toTestKilledMonsterCounter);
        softly.assertThat(fight.getMonsterCounter()).isEqualTo(toTestMonsterCounter);
        softly.assertThat(fight.getChampion().getHealingPotionCounter()).isEqualTo(toTestHealingPotionCounter);
        softly.assertThat(fight.nextTurn()).isTrue();
        softly.assertAll();
    }

    @Test
    public void battleChampionWithHighInitiationAndHighAccuracyTest() {
        lowHpGoblin();

        Champion champion = new Champion();
        champion.setHp(1 + fight.getMonster().getMaxDamage());
        champion.setInitiative(1000);
        champion.setAccuracy(1000);
        fight.setChampion(champion);

        fight.battle();

        ifAssertThatIsChampionStillAlive();
        softly.assertThat(fight.getMonsterFactory().getKilledMonsterCounter()).isEqualTo(toTestKilledMonsterCounter + 1);
        softly.assertThat(fight.getMonsterCounter()).isEqualTo(toTestMonsterCounter - 1);
        softly.assertThat(fight.getChampion().getHealingPotionCounter()).isEqualTo(toTestHealingPotionCounter + 1);
        ifAssertThatIsChampionStillAlive(); //this is the new goblin
        softly.assertAll();
    }

    @Test
    public void battleChampionWithHighInitiationAndMonsterWithHighAccuracyAndDefenseTest() {
        Champion champion = new Champion();
        champion.setHp(1);
        champion.setInitiative(1000);
        fight.setChampion(champion);

        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        monster.setAccuracy(1000);
        monster.setDefense(1000);
        fight.setMonster(monster);

        fight.battle();

        ifAssertThatIsChampionDefeated();
        ifAssertThatIsMonsterStillAlive();
        softly.assertThat(fight.getMonsterFactory().getKilledMonsterCounter()).isEqualTo(toTestKilledMonsterCounter);
        softly.assertThat(fight.getMonsterCounter()).isEqualTo(toTestMonsterCounter);
        softly.assertThat(fight.getChampion().getHealingPotionCounter()).isEqualTo(toTestHealingPotionCounter);
        softly.assertThat(fight.nextTurn()).isFalse();
        softly.assertAll();
    }

    @Test
    public void battleChampionWithHighInitiationAndHighDefenseAndMonsterWithHighDefenseTest() {
        Champion champion = new Champion();
        champion.setHp(1);
        champion.setInitiative(1000);
        champion.setDefense(1000);
        fight.setChampion(champion);

        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        monster.setDefense(1000);
        fight.setMonster(monster);

        fight.battle();

        ifAssertThatIsChampionStillAlive();
        ifAssertThatIsMonsterStillAlive();
        softly.assertThat(fight.getMonsterFactory().getKilledMonsterCounter()).isEqualTo(toTestKilledMonsterCounter);
        softly.assertThat(fight.getMonsterCounter()).isEqualTo(toTestMonsterCounter);
        softly.assertThat(fight.getChampion().getHealingPotionCounter()).isEqualTo(toTestHealingPotionCounter);
        softly.assertThat(fight.nextTurn()).isTrue();
        softly.assertAll();
    }

    @Test
    public void monsterDefeatedWithDefeatedGoblinTest() {
        defeatedGoblin();

        fight.monsterDefeated();

        softly.assertThat(fight.getMonsterFactory().getKilledMonsterCounter()).isEqualTo(toTestKilledMonsterCounter + 1);
        softly.assertThat(fight.getMonsterCounter()).isEqualTo(toTestMonsterCounter - 1);
        softly.assertThat(fight.getChampion().getHealingPotionCounter()).isEqualTo(toTestHealingPotionCounter + 1);
        ifAssertThatIsChampionStillAlive(); //this is the new goblin
        softly.assertThat(fight.nextTurn()).isTrue();
        softly.assertAll();
    }

    @Test
    public void monsterDefeatedWithDefeatedGoblinAndHealingPotionOnMaxTest() {
        defeatedGoblin();
        champion.setHealingPotionCounter(5);
        int toTestHealingPotionCounter = champion.getHealingPotionCounter();

        fight.monsterDefeated();

        softly.assertThat(fight.getMonsterFactory().getKilledMonsterCounter()).isEqualTo(toTestKilledMonsterCounter + 1);
        softly.assertThat(fight.getMonsterCounter()).isEqualTo(toTestMonsterCounter - 1);
        softly.assertThat(champion.getHealingPotionCounter()).isEqualTo(toTestHealingPotionCounter);
        ifAssertThatIsChampionStillAlive(); //this is the new goblin
        softly.assertThat(fight.nextTurn()).isTrue();
        softly.assertAll();
    }

    @Test
    public void monsterDefeatedWithDefeatedGoblinKingTest() {
        defeatedGoblinKing();

        fight.monsterDefeated();

        softly.assertThat(fight.getMonsterFactory().getKilledMonsterCounter()).isEqualTo(toTestKilledMonsterCounter);
        softly.assertThat(fight.getMonsterCounter()).isEqualTo(toTestMonsterCounter);
        softly.assertThat(fight.getChampion().getHealingPotionCounter()).isEqualTo(toTestHealingPotionCounter);
        ifAssertThatIsMonsterDefeated(); //dont call new monster
        softly.assertThat(fight.nextTurn()).isFalse();
        softly.assertAll();
    }

    @Test
    public void monsterAccuracyAliveMonsterWithHighAccuracyTest() {
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        monster.setAccuracy(1000);
        fight.setMonster(monster);
        lowHpChampion();

        fight.monsterAccuracy();

        ifAssertThatIsChampionDefeated();
        softly.assertAll();
    }

    @Test
    public void monsterAccuracyAliveMonsterAndChampWithHighDefenseTest() {
        lowHpGoblin();
        Champion champion = new Champion();
        champion.setHp(1);
        champion.setDefense(1000);
        fight.setChampion(champion);

        fight.monsterAccuracy();

        ifAssertThatIsChampionStillAlive();
        softly.assertAll();
    }

    @Test
    public void monsterAccuracyGoblinDefeated() {
        defeatedGoblin();

        fight.monsterAccuracy();

        assertThat(fight.nextTurn()).isFalse();
    }

    @Test
    public void championAccuracyAliveChampionWithHighAccuracyTest() {
        Champion champion = new Champion();
        champion.setHp(1);
        champion.setAccuracy(1000);
        fight.setChampion(champion);

        lowHpGoblin();

        fight.championAccuracy();

        ifAssertThatIsMonsterDefeated();
        softly.assertAll();
    }

    @Test
    public void championAccuracyAliveChampionAndMonsterWithHighDefenseTest() {
        lowHpChampion();
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        monster.setDefense(1000);
        fight.setMonster(monster);

        fight.championAccuracy();

        ifAssertThatIsMonsterStillAlive();
        softly.assertAll();
    }

    @Test
    public void championAccuracyGoblinDefeated() {
        defeatedChampion();

        fight.championAccuracy();

        assertThat(fight.nextTurn()).isFalse();
    }

    @Test
    public void championAttackAliveChampionTest() {
        lowHpChampion();
        lowHpGoblin();

        fight.championAttack();

        ifAssertThatIsMonsterDefeated();
        softly.assertThat(fight.nextTurn()).as("There should be no next turn.").isFalse();
        softly.assertAll();
    }

    @Test
    public void championAttackDefeatedChampionTest() {
        defeatedChampion();
        lowHpGoblin();

        fight.championAttack();

        ifAssertThatIsMonsterStillAlive();
        softly.assertAll();
    }

    @Test
    public void monsterAttackAliveChampionTest() {
        lowHpGoblin();
        lowHpChampion();

        fight.monsterAttack();

        ifAssertThatIsChampionDefeated();
        softly.assertAll();
    }

    @Test
    public void monsterAttackDefeatedMonsterTest() {
        defeatedGoblin();
        lowHpChampion();

        fight.monsterAttack();

        softly.assertThat(fight.getChampion().getHp()).as("Champion still alive").isGreaterThan(0);
        softly.assertThat(fight.nextTurn()).as("There should be no next turn.").isFalse();
        softly.assertAll();
    }

    @Test
    public void attackInitiationCalculationChampionTest(){
        Champion champion = new Champion();
        champion.setInitiative(10);
        fight.setChampion(champion);

        int championInitiation = champion.initiationCalculation();

        assertThat(championInitiation).isBetween(11,20);
    }

    @Test
    public void attackInitiationCalculationGoblinTest(){
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        monster.setInitiative(10);
        fight.setMonster(monster);

        int monsterInitiation = monster.initiationCalculation();

        assertThat(monsterInitiation).isBetween(11,20);
    }

    @Test
    public void attackInitiationCalculationGoblinKingTest(){
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        monster.setInitiative(10);
        fight.setMonster(monster);

        int monsterInitiation = monster.initiationCalculation();

        assertThat(monsterInitiation).isBetween(11,20);
    }

    @Test
    public void accuracyCalculationChampionTest(){
        Champion champion = new Champion();
        champion.setAccuracy(10);
        fight.setChampion(champion);

        int championAccuracy = champion.accuracyCalculation();

        assertThat(championAccuracy).isBetween(11,110);

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