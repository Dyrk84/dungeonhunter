package hu.dungeonhunter.tools;

import hu.dungeonhunter.characters.Champion;
import hu.dungeonhunter.characters.Monsters;

public class Fight {
    Champion champion = new Champion();
    Monsters monster = new Monsters();

    public void championAttack() {
        System.out.print("Champion attack: ");
        monster.setMonsterHP(monster.getMonsterHP() - champion.championDamage());
        System.out.println("Monster have now " + monster.getMonsterHP() + " hit points");
    }

    public void monsterAttack() {
        System.out.print("Monster attack: ");
        champion.setChampionHP(champion.getChampionHP() - monster.monsterDamage());
        System.out.println("Champion have now " + champion.getChampionHP() + " hit points");

    }

    public void gameOver() {

        if (champion.getChampionHP() > 0 && monster.getMonsterHP() < 1)
            System.out.println("You killed the monster! You win!");
        if (champion.getChampionHP() < 1 && monster.getMonsterHP() > 0)
            System.out.println("The monster killed you! You lost!");
        if (champion.getChampionHP() < 1 && monster.getMonsterHP() < 1)
            System.out.println("you are both dead");
    }

    public boolean nextTurn() {
        if (champion.getChampionHP() > 0 && monster.getMonsterHP() > 0)
            return true;
        else {
            return false;
        }
    }

    public void start() {
        System.out.println("Your champion have " + champion.getChampionHP() + " HP and can do " +
                champion.getNumOfDices() + "-" + champion.getNumOfDices() * champion.getChampionMaxDamage() +
                " damage.");
        System.out.println("The monster have " + monster.getMonsterHP() + " HP and can do " +
                monster.getNumOfDices() + "-" + monster.getNumOfDices() * monster.getMonsterMaxDamage() +
                " damage.");
    }

}
