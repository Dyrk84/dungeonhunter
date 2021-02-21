package hu.dungeonhunter.characters.monsters;

import hu.dungeonhunter.model.CharacterTypes;
import jdk.nashorn.internal.objects.annotations.Setter;

public interface MonstersInterface {
    void getMonster();
    void getMonster(int hp);
    int getHp();
    void setHp(int hp);
    CharacterTypes getType();
    int getMonsterDamage();
    boolean isDefeat();
}
