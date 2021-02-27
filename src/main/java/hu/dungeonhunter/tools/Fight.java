package hu.dungeonhunter.tools;

import hu.dungeonhunter.characters.champion.Champion;
import hu.dungeonhunter.characters.monsters.MonsterFactory;
import hu.dungeonhunter.characters.monsters.MonstersInterface;
import hu.dungeonhunter.model.CharacterTypes;
import hu.dungeonhunter.utils.Colors;
import hu.dungeonhunter.utils.TextSeparator;
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

    @Setter
    @Getter
    int championFinalInitiation;

    @Setter
    @Getter
    int monsterFinalInitiation;

    @Setter
    @Getter
    int loopCounter;

    @Setter
    int accuracyRoll;

    int finalAccuracy;

    @Setter
    int randomCriticalMissEvent;

    public Fight() {
        setMonsterCounter(Dice.rollDice(6, 2));
        monsterIncoming();
    }

    public void runningAway() {
        if (monster.getType().equals(CharacterTypes.GOBLIN_KING)) {
            System.out.println("You can't run away from the " + monster.getType().charType + " !");
        } else {
            System.out.println("The monster hits you a last time before you can run away: ");
            champion.setHp(champion.getHp() - monster.Damage());
            System.out.println("Champion have now " + Colors.ANSI_RED + champion.getHp()
                    + Colors.ANSI_RESET + " hit points");
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

    public void goblinKingDamage() {
        TextSeparator.format("The goblin king steps out from the darkness and throws you with a big rock!");
        champion.setHp(champion.getHp() - Dice.rollDice(6, 1));
        System.out.println("You have " + champion.getHp() + " hp");
        champion.enemyVictory();
    }

    public void initiationCalculation() {
        if (loopCounter == 100) {
            throw new RuntimeException("Too much same final initiation");
        }
        TextSeparator.format("Initiation Calculation:");
        championFinalInitiation = champion.initiationCalculation();
        monsterFinalInitiation = monster.initiationCalculation();
        battle();
    }

    public void battle() {
        if (monsterFinalInitiation > championFinalInitiation) {
            loopCounter = 0;
            TextSeparator.format(monster.getType().charType + " attacks faster!");
            monsterAccuracyCalculation();
            championAccuracyCalculation();
        } else if (monsterFinalInitiation < championFinalInitiation) {
            loopCounter = 0;
            TextSeparator.format("Champion attacks faster!");
            championAccuracyCalculation();
            monsterAccuracyCalculation();
        } else {
            TextSeparator.format("The values are same! New initiation calculation!");
            loopCounter++;
            initiationCalculation();
        }
        if (monster.getHp() < 1) monsterDefeated();
    }

    public void monsterAccuracyCalculation() {
        if (monster.getHp() > 0) {
            finalAccuracy = monster.accuracyCalculation();
            accuracyRoll = monster.getAccuracyRoll();
            monsterAccuracy();
        }
    }

    public void championAccuracyCalculation() {
        if (champion.getHp() > 0) {
            finalAccuracy = champion.accuracyCalculation();
            accuracyRoll = champion.getAccuracyRoll();
            championAccuracy();
        }
    }

    private void monsterAccuracy() {
        System.out.println("Champion's defense: " + champion.getDefense());
        if (monster.getAccuracyRoll() == 1) monsterCriticalMiss();
        if (monster.getAccuracyRoll() == 100) monsterDeadlyHit();
        else {
            if (finalAccuracy < champion.getDefense() && champion.getHp() > 0 && monster.getHp() > 0) {
                TextSeparator.format(monster.getType().charType + "'s hit miss!");
            }
            if (finalAccuracy > (champion.getDefense() + 50) && champion.getHp() > 0 && monster.getHp() > 0) {
                monsterCriticalHit();
            }
            else if (finalAccuracy > champion.getDefense() && champion.getHp() > 0 && monster.getHp() > 0) {
                monsterAttack();
            }
        }
    }

    private void championAccuracy() {
        System.out.println(monster.getType().charType + "'s defense: " + monster.getDefense());
        if (champion.getAccuracyRoll() == 1) championCriticalMiss();
        if (champion.getAccuracyRoll() == 100) championDeadlyHit();
        else {
            if (finalAccuracy < monster.getDefense() && champion.getHp() > 0 && monster.getHp() > 0) {
                TextSeparator.format("Champion's hit miss!");
            }
            if (finalAccuracy > (monster.getDefense() + 50) && champion.getHp() > 0 && monster.getHp() > 0) {
                championCriticalHit();
            }
            else if (finalAccuracy > monster.getDefense() && champion.getHp() > 0 && monster.getHp() > 0) {
                championAttack();
            }
        }
    }

    public void championCriticalMiss() {
        randomCriticalMissEvent = Dice.rollDice(2, 1);
        championCriticalMissEvent();
    }

    public void monsterCriticalMiss() {
        randomCriticalMissEvent = Dice.rollDice(2, 1);
        monsterCriticalMissEvent();
    }

    public void championCriticalMissEvent() {
        switch (randomCriticalMissEvent) {
            case 1: {
                System.out.println("The hit is so unfortunate the champion injured himself");
                champion.setHp(champion.getHp() - champion.Damage());
                System.out.println("The selfdamage is " + Colors.ANSI_RED + champion.getDamage() + Colors.ANSI_RESET + "!");
                TextSeparator.format("Champion have now " + champion.getHp() + " hit points");
                champion.enemyVictory();
            }
            case 2: {
                System.out.println("The hit was so unfortunate, the champion's weapon is damaged!");
                champion.setMaxDamage(champion.getMaxDamage() - 1);
            }
        }
    }

    public void monsterCriticalMissEvent() {
        switch (randomCriticalMissEvent) {
            case 1: {
                System.out.println("The hit is so unfortunate the " + monster.getType().charType + " injured himself");
                monster.setHp(monster.getHp() - monster.Damage());
                System.out.println("The selfdamage is " + Colors.ANSI_RED + monster.getDamage() + Colors.ANSI_RESET + "!");
                TextSeparator.format(monster.getType().charType + " have now " + monster.getHp() + " hit points");
                monster.enemyVictory();
            }
            case 2: {
                System.out.println("The hit was so unfortunate, the " + monster.getType().charType + "'s weapon is damaged!");
                monster.setMaxDamage(monster.getMaxDamage() - 1);
            }
        }
    }

    public void championDeadlyHit() {
        System.out.println("The Champion delivers a " + Colors.ANSI_RED + " DEADLY ATTACK "+ Colors.ANSI_RESET
                + "with incredible luck.");
        monster.setHp(monster.getHp() - (champion.Damage() * 10));
        System.out.println("The damage is " + Colors.ANSI_RED + champion.getDamage() * 10 + Colors.ANSI_RESET + "!!!");
        TextSeparator.format("The " + monster.getType().charType + " have now "
                + Colors.ANSI_RED + monster.getHp() + Colors.ANSI_RESET + " hit points");
    }

    public void monsterDeadlyHit() {
        System.out.println("The " + monster.getType().charType + " delivers a "+ Colors.ANSI_RED + " DEADLY ATTACK "
                + Colors.ANSI_RESET + " with incredible luck.");
        champion.setHp(champion.getHp() - (monster.Damage() * 10));
        System.out.println("The damage is " + Colors.ANSI_RED + monster.getDamage() * 10 + Colors.ANSI_RESET + "!!!");
        TextSeparator.format("The champion have now "
                + Colors.ANSI_RED + champion.getHp() + Colors.ANSI_RESET + " hit points");
    }

    public void championAttack() {
        if (champion.getHp() > 0) {
            System.out.print("The Champion hit the " + monster.getType().charType + "! ");
            monster.setHp(monster.getHp() - champion.Damage());
            System.out.println("The damage is " + Colors.ANSI_RED + champion.getDamage() + Colors.ANSI_RESET + ".");
            TextSeparator.format("The " + monster.getType().charType + " have now "
                    + Colors.ANSI_RED + monster.getHp() + Colors.ANSI_RESET + " hit points");
            monster.enemyVictory();
        }
    }

    public void monsterAttack() {
        if (monster.getHp() > 0) {
            System.out.print("The " + monster.getType().charType + " hit the Champion! ");
            champion.setHp(champion.getHp() - monster.Damage());
            System.out.println("The damage is " + Colors.ANSI_RED + monster.getDamage() + Colors.ANSI_RESET + ".");
            TextSeparator.format("Champion have now " + Colors.ANSI_RED + champion.getHp() + Colors.ANSI_RESET
                    + " hit points");
            champion.enemyVictory();
        }
    }

    public void championCriticalHit(){
        if (champion.getHp() > 0) {
            System.out.print("The Champion hit the " + monster.getType().charType + " with a " + Colors.ANSI_RED
                    + "CRITICAL " + Colors.ANSI_RESET + "hit! ");
            monster.setHp(monster.getHp() - champion.Damage() * 2);
            System.out.println("The damage is " + Colors.ANSI_RED + champion.getDamage() * 2 + Colors.ANSI_RESET + "!");
            TextSeparator.format("The " + monster.getType().charType + " have now "
                    + Colors.ANSI_RED + monster.getHp() + Colors.ANSI_RESET + " hit points");
            monster.enemyVictory();
        }
    }

    public void monsterCriticalHit(){
        if (monster.getHp() > 0) {
            System.out.print("The " + monster.getType().charType + "hit the Champion with a " + Colors.ANSI_RED
                    + "CRITICAL " + Colors.ANSI_RESET + "hit! ");
            champion.setHp(champion.getHp() - monster.Damage() * 2);
            System.out.println("The damage is " + Colors.ANSI_RED + monster.getDamage() * 2 + Colors.ANSI_RESET + "!");
            TextSeparator.format("The Champion have now "
                    + Colors.ANSI_RED + monster.getHp() + Colors.ANSI_RESET + " hit points");
            champion.enemyVictory();
        }
    }

    public void monsterDefeated() {
        if (!monster.getType().equals(CharacterTypes.GOBLIN_KING)) {
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

    private void textOfWin() {
        System.out.println("The Dungeon is clear! You killed " + monsterFactory.getKilledMonsterCounter() +
                " monster (not counting the many mothers and children), you win!");
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