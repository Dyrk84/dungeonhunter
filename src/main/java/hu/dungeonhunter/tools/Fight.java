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
        if (monster.getMonsterHP() > 0)
            monsterAttack();
        else {
            gameOver();
            victory();
        }
    }

    public void monsterAttack() {
        System.out.print("Monster attack: ");
        champion.setChampionHP(champion.getChampionHP() - monster.monsterDamage());
        System.out.println("Champion have now " + champion.getChampionHP() + " hit points");
        gameOver();
        victory();
    }

    public void gameOver() {
        if (champion.getChampionHP() > 0 && monster.getMonsterHP() < 1) {
            System.out.println("You killed the monster! You win!");
            champion.setChampionVictory(true);
        }
        if (champion.getChampionHP() < 1 && monster.getMonsterHP() > 0) {
            System.out.println("The monster killed you! You lost!");
            monster.setMonsterVictory(true);
        }
        if (champion.getChampionHP() < 1 && monster.getMonsterHP() < 1)
            System.out.println("you are both dead");
    }

    public void victory() {
    }

    public boolean nextTurn() {
        if (champion.getChampionHP() > 0 && monster.getMonsterHP() > 0)
            return true;
        else {
            return false;
        }
    }
}