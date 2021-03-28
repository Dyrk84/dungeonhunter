package hu.dungeonhunter.tools;

import hu.dungeonhunter.characters.champion.Champion;
import hu.dungeonhunter.characters.MonsterFactory;
import hu.dungeonhunter.characters.Character;
import hu.dungeonhunter.model.CharacterTypes;
import hu.dungeonhunter.utils.Colors;
import hu.dungeonhunter.utils.TextSeparator;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

public class Fight {

    @Getter
    private final MonsterFactory monsterFactory = new MonsterFactory();

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

    @Getter
    Map<Integer, Character> initRolls;

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
            monster = monsterFactory.getCharacter(CharacterTypes.GOBLIN_KING);
            goblinKingDamage();
        } else {
            TextSeparator.format("A goblin steps out from the darkness!");
            monster = monsterFactory.getCharacter(CharacterTypes.GOBLIN);
        }
        return monster;
    }

    public void goblinKingDamage() {
        TextSeparator.format("The " + Colors.ANSI_RED + CharacterTypes.GOBLIN_KING.charType + Colors.ANSI_RESET
                + " steps out from the darkness and throws you with a big rock!");
        dealNormalDamage(monster, champion, monster.damage());
    }

    public void initiationCalc() {  //TODO erre hogy lehet teszteket írni?
        if (loopCounter == 100) {
            throw new RuntimeException("Too much same final initiation");
        }
        TextSeparator.format("Initiation Calculation:");

        Map<Integer, Character> initRolls = new HashMap<>(); //D: ez a HashMap azért kell, hogy beletöltsük a karaktereket és a karakterek dobott kezdeményező értékeit.
        for (int i = 0; i < charactersInBattle.size(); i++) {
            // hozzáadja az i. karaktert és annak a rollolt értékét egy HashMap-hez (key: rolled int, value: karakter)
            initRolls.put(charactersInBattle.get(i).initiationCalculation(), charactersInBattle.get(i)); //D: itt a trükk annyi, hogy nem lehet két azonos key a HashMap-ben, ezért ha azonos lenne a dobás, az egyik key elveszne.
        }

        // ha nem egyezik a harcoló felek száma a dobott, egyedi initation értékek számával, akkor újrakezdjük az init dobást.
        if (charactersInBattle.size() != initRolls.size()) { //D: ha nem egyezik, akkor ez lefut, de mivel újra kezdi az initiationCalc()-ot, az is le fog futni, és ami ez után jön, az annyiszor le fog futni a program szál végén, ahányszor azonos kezdeményező érték volt.
            loopCounter++;
            TextSeparator.format("The values are same! New initiation calculation!");
            initiationCalc();
        } else {

            // A hashmap kulcsai (karakter) rendezi csökkenő sorrendbe a hashmap értékei (init rolls) alapján és a rendezezz map kulcsaiból listát csinál

            List<Character> orderedCharacterList = initRolls.entrySet().stream() // D: az endtrySet() visszaadja a kulcs értékek rögzített adathalmazát, ebből csinál streamet
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByKey())) //D: a sorted -el (ez egy stream függvény) meghívunk egy logikát, ami alapján rendezünk(a collections-t hívjuk meg, a reverseOrdert használva ami a stream -el használható speciális rendező függvény, ami fordított sorrendbe fogja rakni az értékeket és a zárójelben meghatározzuk, hogy mi alapján fogunk rendezni (a map adathalmazai közül összehasonlít a keyek alapján)
                    .map(Map.Entry::getValue) //D: itt nekünk már csak a karakterek kellenek a dobott értékek alapján sorrendbe rakva(de a dobott értékek már nem kellenek), ennek a sornak ez a célja. A .map-elés (ez is egy stream függvény)az egy eljárás, megfeleltetése értékeket valamilyen logika alapján másmilyen értékeknek. A Map.Entry azt csinálja, hogy vegye a map elemeit, a :: azt jelenti, hogy vegye ki a mögötte jövőt, ami ugye a value lesz, így veszi ki a Map value-kat, amik ugye a karakterek. Röviden: A Map (Map.) adatai (Entry) közül vedd ki a value-kat (::getValue).
                    .collect(Collectors.toList()); //D: ez a collect változtatja át az eddigi stream adatfolyamot listává, hogy lehessen használni listaként a későbbiekben.

            setCharactersInBattle(orderedCharacterList);

            loopCounter = 0;
            battle();
        }
    }

    public void battle() {
        for (int i = 0; i < charactersInBattle.size(); i++) {
            if (charactersInBattle.get(i).getHp() > 0) {  //D: ez az if azért van, hogy halott karakter ne vehessen részt a csatában.
                if (i == 0) { //ez azért van itt, hogy kiírja hogy az első karakter a listában (rendezésnél ő neki lett a legnagyobb kezdeményezése) kezdi a támadást
                    TextSeparator.format(charactersInBattle.get(i).getType().charType + " attacks faster!");
                }
                attack(charactersInBattle.get(i), Dice.rollDice(100, 1));
                if (monster.getHp() <= 0 && monster.getType() != CharacterTypes.GOBLIN_KING) {
                    monsterDefeated();
                } else if (monster.getHp() <= 0 && monster.getType() == CharacterTypes.GOBLIN_KING) {
                    monsterDefeated();
                }
            }
        }
    }

    public void attack(Character attacker, int accuracyRoll) {
        // 1 soros if-else: ha az attacker champion, akkor az attacked monster lesz, ha nem, akkor champion lesz az attacked
        Character attacked = attacker.getType() == CharacterTypes.CHAMPION ? monster : champion;

        int finalAccuracy = attacker.accuracyCalculation(accuracyRoll);
        System.out.println(attacked.getType().charType + "'s defense: " + attacked.getDefense());
        if (accuracyRoll == 1) criticalMiss(attacker);
        if (accuracyRoll == 100) dealDeadlyHit(attacker, attacked);
        else {
            if (finalAccuracy <= attacked.getDefense() && attacker.getHp() > 0 && attacked.getHp() > 0) {
                TextSeparator.format(attacker.getType().charType + "'s hit miss!");
            }
            if (finalAccuracy > (attacked.getDefense() + 50) && attacker.getHp() > 0 && attacked.getHp() > 0) {
                dealCriticalHit(attacker, attacked);
            } else if (finalAccuracy > attacked.getDefense() && attacker.getHp() > 0 && attacked.getHp() > 0) {
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
        attacked.setHp(attacked.getHp() - attacker.damage() * 2);
        System.out.println("The damage is " + Colors.ANSI_RED + attacker.getDamage() * 2 + Colors.ANSI_RESET + "!");
        TextSeparator.format("The " + attacked.getType().charType + " have now "
                + Colors.ANSI_RED + attacked.getHp() + Colors.ANSI_RESET + " hit points");
        attacked.isDefeated();
    }

    public void monsterDefeated() {
        if (!monster.getType().equals(CharacterTypes.GOBLIN_KING)) {
            monsterFactory.setKilledMonsterCounter(monsterFactory.getKilledMonsterCounter() + 1);
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
        System.out.print("The Dungeon is clear! ");
        if (monsterFactory.getKilledMonsterCounter() > 0) System.out.print("You killed " + monsterFactory
                .getKilledMonsterCounter() + " monster");
        if (monsterFactory.getKilledMonsterCounter() > 1) System.out.print("s, ");
        System.out.println("you win!");
    }

    public boolean nextTurn() {
        if (monster.getHp() <= 0 && monster.getType() != CharacterTypes.GOBLIN_KING){
            monsterIncoming();
        }
        if (champion.getHp() <= 0 || monster.getHp() <= 0 && monster.getType() == CharacterTypes.GOBLIN_KING)
            return false;
        else
            return true;
    }
}