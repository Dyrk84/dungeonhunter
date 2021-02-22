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

    @Getter
    private MonsterFactory monsterFactory = new MonsterFactory();

    @Setter
    @Getter
    private MonstersInterface monster;

    @Setter
    @Getter
    private int monsterCounter;

    @Setter
    @Getter
    private int randomEnemy;

    @Getter
    @Setter
    int finalChampionInitiation;

    @Getter
    @Setter
    int finalMonsterInitiation;

    @Setter
    @Getter
    int antiLoop = 0;

    public Fight() {
        setMonsterCounter(Dice.rollDice(6, 2));
        monsterIncomingOrWin();
    }

    public void goblinKingDamage() {
        for (int i = 0; i < 70; i++) System.out.print("*");
        System.out.println("*");
        System.out.println("The goblin king steps out from the darkness and throws you with a big rock!");
        champion.setHp(champion.getHp() - Dice.rollDice(6, 1));
        System.out.println("You have " + champion.getHp() + " hp");
        champion.enemyVictory();
    }

    public void runningAway() {
        if (monster.getType().equals(CharacterTypes.GOBLIN_KING)) {
            System.out.println("You can't run away from the " + monster.getType().charType + " !");
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

    public void monsterIncomingOrWin() {
        if (monsterCounter != 0) {
            System.out.println(monsterCounter + " monsters are in the Dungeon!");
            System.out.print("Something is coming! ");
            randomEnemy = Dice.rollDice(monsterCounter, 1);
            monsterCaller();
        } else
            textOfWin();
    }

    public MonstersInterface monsterCaller() {
        if (randomEnemy == 1) {
            monster = monsterFactory.getMonster(CharacterTypes.GOBLIN_KING);
            goblinKingDamage();
        } else {
            System.out.println("A goblin steps out from the darkness!");
            monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
            for (int i = 0; i < 70; i++) System.out.print("*");
            System.out.println("*");
        }
        return monster;
    }

    public void attackInitiating() {
        antiLoop();
        attackInitiationCalculationPrint();
        championAttackInitiationCalculation();
        monsterAttackInitiationCalculation();
        if (finalMonsterInitiation > finalChampionInitiation) {
            monsterIsTheFirstAttackerPrint();
            monsterAttack();
            champion.enemyVictory();
            if (!champion.isDefeat()) {
                championAttack();
                if (monster.getType().equals(CharacterTypes.GOBLIN_KING) && monster.getHp() <= 0) {
                    monster.isDefeat();
                    textOfWin();
                } else if (monster.isDefeat()){
                    goblinDefeated();
                }
            }
        } else if (finalMonsterInitiation < finalChampionInitiation) {
            championIsTheFirstAttackerPrint();
            championAttack();
            if (monster.getType().equals(CharacterTypes.GOBLIN_KING) && monster.getHp() <= 0) {
                monster.isDefeat();
                textOfWin();
            } else if (!monster.isDefeat()) {
                monsterAttack();
                champion.enemyVictory();
            } else {
                goblinDefeated();
            }
        }else{
            System.out.println("The values are the same! New initiation calculation!");
            attackInitiating();
        }
    }

    public void monsterAttackInitiationCalculation() {
        System.out.print(monster.getType().charType + " initiation: " + monster.getMonsterInitiative() + " + ");
        int monsterInitRoll = Dice.rollDice(10, 1);
        System.out.println("Final " + monster.getType().charType + " initiation: " + (monster.getMonsterInitiative() + monsterInitRoll));
        finalMonsterInitiation = monster.getMonsterInitiative() + monsterInitRoll;
    }

    public void championAttackInitiationCalculation() {
        System.out.print("Champion initiation: " + champion.getInitiative() + " + ");
        int champInitRoll = Dice.rollDice(10, 1);
        System.out.println("Final Champion initiation: " + (champion.getInitiative() + champInitRoll));
        finalChampionInitiation = champion.getInitiative() + champInitRoll;
    }

    public void antiLoop() {
        antiLoop++;
        if (antiLoop == 500){
            System.out.println("Endless Loop! Exit");
            System.exit(0);
        }
    }

    private void textOfWin() {
        System.out.println("The Dungeon is clear! You killed " + monsterFactory.getKilledMonsterCounter() +
                " monster (not counting the many mothers and children), you win!");
    }

    public void goblinDefeated() {
        antiLoop = 0;
        monsterFactory.setKilledMonsterCounter(monsterFactory.getKilledMonsterCounter() +1);
        monsterCounter--;
        System.out.println("You found a healing potion!");
        if (champion.getHealingPotionCounter() < 5) {
            champion.setHealingPotionCounter(champion.getHealingPotionCounter() + 1);
        } else {
            System.out.println("You can't have more than 5 healing potions!");
        }
        for (int i = 0; i < 70; i++) System.out.print("*");
        System.out.println("*");
        monsterIncomingOrWin();
    }

    private void championIsTheFirstAttackerPrint() {
        System.out.println("Champion is the first attacker! ");
        for (int i = 0; i < 26; i++) System.out.print("*");
        System.out.print(" Damage Calculate: ");
        for (int i = 0; i < 25; i++) System.out.print("*");
        System.out.println("*");
    }

    private void monsterIsTheFirstAttackerPrint() {
        System.out.println(monster.getType().charType + " is the first attacker!");
        for (int i = 0; i < 26; i++) System.out.print("*");
        System.out.print(" Damage Calculate: ");
        for (int i = 0; i < 25; i++) System.out.print("*");
        System.out.println("*");
    }

    private void attackInitiationCalculationPrint() {
        for (int i = 0; i < 19; i++) System.out.print("*");
        System.out.print(" Attack initiation calculation: ");
        for (int i = 0; i < 19; i++) System.out.print("*");
        System.out.println("*");
    }

    public void monsterAttack() {
        System.out.print(monster.getType().charType + " attack: ");
        champion.setHp(champion.getHp() - monster.getMonsterDamage());
        System.out.println("Champion have now " + champion.getHp() + " hit points");
    }

    public void championAttack() {
        System.out.print("Champion attack: ");
        monster.setHp(monster.getHp() - champion.championDamage());
        System.out.println("The " + monster.getType().charType + " have now " + monster.getHp() + " hit points");
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