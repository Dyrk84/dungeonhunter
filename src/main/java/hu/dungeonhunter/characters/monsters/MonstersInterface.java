package hu.dungeonhunter.characters.monsters;

import hu.dungeonhunter.model.CharacterTypes;
import jdk.nashorn.internal.objects.annotations.Setter;

public interface MonstersInterface {
    void getMonster();
    int getHp();
    void setHp(int hp);
    CharacterTypes getType();
    int getMonsterDamage();
    boolean isDefeat();
    void setInitiation(int initiation);
    int getInitiation();
    int initiationCalculation();
    int getMaxDamage();
    void setAccuracy(int accuracy);
    int getAccuracy();
    void setDefense(int defense);
    int getDefense();
    int accuracyCalculation();
    int getNumOfDices();
}
