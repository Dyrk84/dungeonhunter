package hu.dungeonhunter.tools;

import hu.dungeonhunter.characters.Champion;
import hu.dungeonhunter.characters.Monsters;

public class Fight {
    Champion champion = new Champion();
    Monsters monster = new Monsters();

    public void damagePlayer(){
        champion.setPlayerHP(champion.getPlayerHP() - monster.monsterDamage());
    }

    public void damageMonster(){
        monster.setMonsterHP(monster.getMonsterHP() - champion.playerDamage());
    }
}
