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

    public Fight() {
        setMonsterCounter(Dice.rollDice(6, 2));
        monsterIncomingOrWin();
    }

    public void goblinKingDamage() {
        TextSeparator.format("");
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
            TextSeparator.format("A goblin steps out from the darkness!");
            monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        }
        return monster;
    }

    public void attackInitiating() {
        TextSeparator.format("Initiation Calculation:");
        champion.championAttackInitiationCalculation();
        monster.monsterAttackInitiationCalculation();
        if (monster.getFinalMonsterInitiation() > champion.getFinalChampionInitiation()) {
            monsterIsTheFirstAttackerPrint();
            monsterAttack();
            championAttack();
        } else if (monster.getFinalMonsterInitiation() < champion.getFinalChampionInitiation()) {
            championIsTheFirstAttackerPrint();
            championAttack();
            monsterAttack();
        } else {
            System.out.println("The values are same! New initiation calculation!");
            attackInitiating();
        }
        if (monster.getHp() < 1) monsterDefeated();
    }

    private void textOfWin() {
        System.out.println("The Dungeon is clear! You killed " + monsterFactory.getKilledMonsterCounter() +
                " monster (not counting the many mothers and children), you win!");
    }

    public void monsterDefeated() {
        if (!monster.getType().equals(CharacterTypes.GOBLIN_KING) && monster.getHp() <= 0) {
            monsterFactory.setKilledMonsterCounter(monsterFactory.getKilledMonsterCounter() + 1);
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
        } else {
            textOfWin();
        }
    }

    private void championIsTheFirstAttackerPrint() {
        System.out.println("Champion is the first attacker! ");
        TextSeparator.format ("Damage calculation:");
    }

    private void monsterIsTheFirstAttackerPrint() {
        System.out.println(monster.getType().charType + " is the first attacker!");
        TextSeparator.format("Damage calculation:");
    }

    public void monsterAttack() {
        if (monster.getHp() > 0) {
            System.out.print(monster.getType().charType + " attack: ");
            champion.setHp(champion.getHp() - monster.getMonsterDamage());
            System.out.println("Champion have now " + champion.getHp() + " hit points");
            champion.enemyVictory();
        }
    }

    public void championAttack() {
        if (champion.getHp() > 0) {
            System.out.print("Champion attack: ");
            monster.setHp(monster.getHp() - champion.championDamage());
            System.out.println("The " + monster.getType().charType + " have now " + monster.getHp() + " hit points");
        }
    }

    public boolean nextTurn() {
        if (champion.getHp() > 0 && monster.getHp() > 0 && monsterCounter > 0)
            return true;
        else
            return false;
    }

    public Champion getChampion() {
        return champion;
    }
}