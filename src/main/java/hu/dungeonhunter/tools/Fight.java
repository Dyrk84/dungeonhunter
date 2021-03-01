package hu.dungeonhunter.tools;

import hu.dungeonhunter.characters.champion.Champion;
import hu.dungeonhunter.characters.CharacterFactory;
import hu.dungeonhunter.characters.Character;
import hu.dungeonhunter.model.CharacterTypes;
import hu.dungeonhunter.utils.Colors;
import hu.dungeonhunter.utils.TextSeparator;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Fight {

    @Getter
    private final CharacterFactory characterFactory = new CharacterFactory();

    @Setter
    @Getter
    private Champion champion = new Champion();

    @Setter
    @Getter
    private Character monster;

    @Setter
    @Getter
    private List<Character> charactersInBattle = new ArrayList<>();

    @Setter
    @Getter
    private int monsterCounter = 10;

    @Setter
    @Getter
    private int randomEnemy;

    @Setter
    @Getter
    int loopCounter;

    @Setter
    @Getter
    int randomCriticalMissEvent;

    public void enterToTheCave() {
        charactersInBattle.add(champion);
        setMonsterCounter(Dice.rollDice(6, 2));
        monsterIncoming();
    }

    public void runningAway() {
        if (monster.getType().equals(CharacterTypes.GOBLIN_KING)) {
            System.out.println("You can't run away from the " + monster.getType().charType + " !");
        } else {
            System.out.println("The monster hits you a last time before you can run away: ");
            champion.setHp(champion.getHp() - monster.damage());
            System.out.println("Champion have now " + Colors.ANSI_RED + champion.getHp()
                    + Colors.ANSI_RESET + " hit points");
            if (!champion.isDefeated()) {
                monsterCounter--;
                charactersInBattle.remove(monster);
                System.out.println("Your escape was successful! You can go further in the cave.");
                monsterIncoming();
            }
        }
    }

    public void monsterIncoming() {
        System.out.println(monsterCounter + " monsters are in the Dungeon!");
        System.out.print("Something is coming! ");
        randomEnemy = Dice.rollDice(monsterCounter, 1);
        Character monster = monsterCaller();
        charactersInBattle.add(monster);
    }

    public Character monsterCaller() {
        if (randomEnemy == 1) {
            monster = characterFactory.getCharacter(CharacterTypes.GOBLIN_KING);
            goblinKingDamage();
        } else {
            TextSeparator.format("A goblin steps out from the darkness!");
            monster = characterFactory.getCharacter(CharacterTypes.GOBLIN);
        }
        return monster;
    }

    public void goblinKingDamage() {
        TextSeparator.format("The " + Colors.ANSI_RED + CharacterTypes.GOBLIN_KING.charType + Colors.ANSI_RESET
            + " steps out from the darkness and throws you with a big rock!");
        dealNormalDamage(monster, champion, monster.damage());
    }

    public void initiationCalc() {
        if (loopCounter == 100) {
            throw new RuntimeException("Too much same final initiation");
        }
        TextSeparator.format("Initiation Calculation:");

        List<Character> orderedCharacterList = new ArrayList<>();
        List<Integer> initRolls = new ArrayList<>();
        for (int i = 0; i < charactersInBattle.size(); i++) {
            // hozzáadja az i. karakter initiation roll értékét egy listához
            initRolls.add(charactersInBattle.get(i).initiationCalculation());

            // megnézi, hogy az initrollok közül az i. a legnagyobb e
            if (Collections.max(initRolls).equals(initRolls.get(i))) {
                // ha igen, akkor a rendezett karakterlista 0. helyére szúrja be az i. karaktert
                orderedCharacterList.add(0, charactersInBattle.get(i));
            } else {
                // ha nem, akkor kozzáadja a rendezett karakterlista végére az i. karaktert
                orderedCharacterList.add(charactersInBattle.get(i));
            }
        }

        // HashSet megszünteti a duplikációkat, initrolls listát átadva a HashSet-nek meg tudjuk nézni kell e re-rollolni (egyező initrollok)
        if (orderedCharacterList.size() != new HashSet<>(initRolls).size()) {
            loopCounter++;
            TextSeparator.format("The values are same! New initiation calculation!");
            initiationCalc();
        } else {
            setCharactersInBattle(orderedCharacterList);
        }
        loopCounter = 0;
        battle();
    }

    public void battle() {
        for (int i = 0; i < charactersInBattle.size(); i++) {
            if (charactersInBattle.get(i).getHp() > 0) {
                if (i == 0) {
                    TextSeparator.format(charactersInBattle.get(i).getType().charType + " attacks faster!");
                }
                attack(charactersInBattle.get(i), Dice.rollDice(100, 1));
                if (monster.getHp() <= 0 && monster.getType() != CharacterTypes.GOBLIN_KING) {
                    monsterDefeated();
                    monsterIncoming();
                } else if (monster.getHp() < 0 && monster.getType() == CharacterTypes.GOBLIN_KING) {
                    monsterDefeated();
                }
            }
        }
    }

    public void attack(Character attacker, int accuracyRoll) {
        System.out.println(attacker.getType().charType + "'s defense: " + attacker.getDefense());
        // 1 soros if-else: ha az attacker champion, akkor az attacked monster lesz, ha nem, akkor champion lesz az attacked
        Character attacked = attacker.getType() == CharacterTypes.CHAMPION ? monster : champion;
        int rolledAccuracy = attacker.accuracyCalculation(accuracyRoll);
        if (rolledAccuracy == 1) criticalMiss(attacker);
        if (rolledAccuracy == 100) dealDeadlyHit(attacker, attacked);
        else {
            if (rolledAccuracy <= attacked.getDefense() && attacker.getHp() > 0 && attacked.getHp() > 0) {
                TextSeparator.format(attacker.getType().charType + "'s hit miss!");
            }
            if (rolledAccuracy > (attacked.getDefense() + 50) && attacker.getHp() > 0 && attacked.getHp() > 0) {
                dealCriticalHit(attacker, attacked);
            } else if (rolledAccuracy > attacked.getDefense() && attacker.getHp() > 0 && attacked.getHp() > 0) {
                dealNormalDamage(attacker, attacked);
            }
        }
    }

    public void criticalMiss(Character character) {
        randomCriticalMissEvent = Dice.rollDice(2, 1);
        criticalMissEvent(character);
    }

    public void criticalMissEvent(Character character) {
        switch (randomCriticalMissEvent) {
            case 1: {
                System.out.println("The hit is so unfortunate the " + character.getType().charType + " injured himself");
                character.setHp(character.getHp() - character.damage());
                System.out.println("The selfdamage is " + Colors.ANSI_RED + character.getDamage() + Colors.ANSI_RESET + "!");
                TextSeparator.format(character.getType().charType + " have now " + character.getHp() + " hit points");
                character.isDefeated();
                break;
            }
            case 2: {
                System.out.println("The hit was so unfortunate, the " + character.getType().charType + "'s weapon is damaged!");
                character.setMaxDamage(character.getMaxDamage() - 1);
                break;
            }
        }
    }

    public void dealDeadlyHit(Character attacker, Character attacked) {
        System.out.println("The " + attacker.getType().charType + " delivers a " + Colors.ANSI_RED + " DEADLY ATTACK " + Colors.ANSI_RESET
                + "with incredible luck.");
        attacked.setHp(attacked.getHp() - (attacker.damage() * 10));
        System.out.println("The damage is " + Colors.ANSI_RED + attacker.getDamage() * 10 + Colors.ANSI_RESET + "!!!");
        TextSeparator.format("The " + attacked.getType().charType + " have now "
                + Colors.ANSI_RED + attacked.getHp() + Colors.ANSI_RESET + " hit points");
        attacked.isDefeated();
    }

    public void dealNormalDamage(Character attacker, Character attacked) {
        dealNormalDamage(attacker, attacked, attacker.damage());
    }

    public void dealNormalDamage(Character attacker, Character attacked, int damage) {
        System.out.print("The " + attacker.getType().charType + " hit the " + attacked.getType().charType + "! ");
        attacked.setHp(attacked.getHp() - damage);
        System.out.println("The damage is " + Colors.ANSI_RED + damage + Colors.ANSI_RESET + ".");
        TextSeparator.format("The " + attacked.getType().charType + " have now "
            + Colors.ANSI_RED + attacked.getHp() + Colors.ANSI_RESET + " hit points");
        attacked.isDefeated();
    }

    public void dealCriticalHit(Character attacker, Character attacked) {
        System.out.print("The " + attacker.getType().charType + " hit the " + attacked.getType().charType + " with a " + Colors.ANSI_RED
            + "CRITICAL " + Colors.ANSI_RESET + "hit! ");
        attacked.setHp(monster.getHp() - attacker.damage() * 2);
        System.out.println("The damage is " + Colors.ANSI_RED + attacker.getDamage() * 2 + Colors.ANSI_RESET + "!");
        TextSeparator.format("The " + attacked.getType().charType + " have now "
            + Colors.ANSI_RED + attacked.getHp() + Colors.ANSI_RESET + " hit points");
        attacked.isDefeated();
    }

    public void monsterDefeated() {
        if (!monster.getType().equals(CharacterTypes.GOBLIN_KING)) {
            characterFactory.setKilledMonsterCounter(characterFactory.getKilledMonsterCounter() + 1);
            monsterCounter--;
            charactersInBattle.remove(monster);
            System.out.println("You found a healing potion!");
            if (champion.getHealingPotionCounter() < 5) {
                champion.setHealingPotionCounter(champion.getHealingPotionCounter() + 1);
            } else {
                System.out.println("You can't have more than 5 healing potions!");
            }
        } else {
            monsterCounter--;
            textOfWin();
        }
    }

    private void textOfWin() {
        System.out.println("The Dungeon is clear! You killed " + characterFactory.getKilledMonsterCounter() +
                " monster (not counting the many mothers and children), you win!");
    }

    public boolean nextTurn() {
        if (champion.getHp() <= 0 || monster.getHp() <= 0 && monster.getType() == CharacterTypes.GOBLIN_KING )
            return false;
        else
            return true;
    }
}