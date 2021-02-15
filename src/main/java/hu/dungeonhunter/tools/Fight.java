package hu.dungeonhunter.tools;

import hu.dungeonhunter.characters.Champion;
import hu.dungeonhunter.characters.Monsters;

public class Fight {
    Champion champion = new Champion();
    Monsters monster = new Monsters();

    public void championAttack(){
        System.out.print("Champion attack: ");
        monster.setMonsterHP(monster.getMonsterHP() - champion.championDamage());
        System.out.println("Monster have now " + monster.getMonsterHP() + " hit points");
    }

    public void monsterAttack(){
        System.out.print("Monster attack: ");
        champion.setChampionHP(champion.getChampionHP() - monster.monsterDamage());
        System.out.println("Champion have now " + champion.getChampionHP() + " hit points");

    }

}
