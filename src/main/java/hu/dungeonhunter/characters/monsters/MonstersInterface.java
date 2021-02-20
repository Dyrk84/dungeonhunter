package hu.dungeonhunter.characters.monsters;

import hu.dungeonhunter.model.CharacterTypes;

public interface MonstersInterface {
    void getMonster();
    void getMonster(int hp);
    int getHp();
    void setHp(int hp);
    CharacterTypes getType();
    int getMonsterDamage();
    boolean isDefeat();
}
