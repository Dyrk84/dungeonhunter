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
        monsterIncoming();
    }

    public void goblinKingDamage() {
        TextSeparator.format("The goblin king steps out from the darkness and throws you with a big rock!");
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
                monsterIncoming();
            }
        }
    }

    public void monsterIncoming() {
            System.out.println(monsterCounter + " monsters are in the Dungeon!");
            System.out.print("Something is coming! ");
            randomEnemy = Dice.rollDice(monsterCounter, 1);
            monsterCaller();
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

    public void battle() {
        TextSeparator.format("Initiation Calculation:");
        champion.attackInitiationCalculation();
        monster.attackInitiationCalculation();
        if (monster.getFinalInitiation() > champion.getFinalInitiation()) {
            TextSeparator.format(monster.getType().charType + " attacks faster!");
            monsterAccuracy();
            championAccuracy();
        } else if (monster.getFinalInitiation() < champion.getFinalInitiation()) {
            TextSeparator.format("Champion attacks faster!");
            championAccuracy();
            monsterAccuracy();
        } else {
            System.out.println("The values are same! New initiation calculation!");
            battle();
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
            monsterIncoming();
        } else {
            textOfWin();
        }
    }

    public void monsterAccuracy() {
        if (monster.getHp() > 0) {
            monster.accuracyCalculation();
            System.out.println("Champion's defense: " + champion.getDefense());
            if (monster.getFinalAccuracy() > champion.getDefense()){
                monsterAttack();
            } else TextSeparator.format(monster.getType().charType + "'s hit miss!");
        }
    }

    public void championAccuracy() {
        if (champion.getHp() > 0) {
            champion.accuracyCalculation();
            System.out.println(monster.getType().charType + "'s defense: " + monster.getDefense());
            if (champion.getFinalAccuracy() > monster.getDefense()) {
                championAttack();
            } else TextSeparator.format("Champion's hit miss!");
        }
    }

    public void monsterAttack() {
        if (monster.getHp() > 0) {
            System.out.print("The " +monster.getType().charType + " hit you: ");
            champion.setHp(champion.getHp() - monster.getMonsterDamage());
            TextSeparator.format("Champion have now " + champion.getHp() + " hit points");
            champion.enemyVictory();
        }
    }

    public void championAttack() {
        if (champion.getHp() > 0) {
            System.out.print("The Champion hit the " + monster.getType().charType + "! ");
            monster.setHp(monster.getHp() - champion.championDamage());
            TextSeparator.format("The " + monster.getType().charType + " have now " + monster.getHp() + " hit points");
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
    } //TODO kérdés Gergőnek: ez miért így van, miért nem @getter -el?
}