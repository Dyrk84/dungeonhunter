package hu.dungeonhunter.characters.monsters;

import hu.dungeonhunter.model.CharacterTypes;
import jdk.nashorn.internal.objects.annotations.Setter;

public interface MonstersInterface {
    void getMonster();
    int getHp();
    void setHp(int hp);
    CharacterTypes getType();
    int Damage();
    int getDamage();
    boolean enemyVictory();
    void setInitiation(int initiation);
    int getInitiation();
    int initiationCalculation();
    void setMaxDamage(int maxDamage);
    int getMaxDamage();
    void setAccuracy(int accuracy);
    int getAccuracy();
    int accuracyCalculation();
    int getAccuracyRoll();
    void setAccuracyRoll(int accuracyRoll);
    void setDefense(int defense);
    int getDefense();
    int getNumOfDices();
}
