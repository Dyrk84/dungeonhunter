package hu.dungeonhunter.characters.monsters;

import hu.dungeonhunter.model.CharacterTypes;
import jdk.nashorn.internal.objects.annotations.Setter;

public interface MonstersInterface {
    void getMonster();
    void getMonster(int hp);
    void getMonster(int hp, int initiative, int accuracy, int defense, int numOfDices, int maxDamage);
    int getHp();
    void setHp(int hp);
    CharacterTypes getType();
    int getMonsterDamage();
    boolean isDefeat();
    int getInitiative();
    void setInitiative(int initiative);
    void setFinalInitiation(int finalInitiation);
    int getFinalInitiation();
    void attackInitiationCalculation();
    int getAccuracy();
    void setAccuracy(int accuracy);
    int getDefense();
    void setDefense(int defense);
    int getFinalAccuracy();
    void accuracyCalculation();
    int getNumOfDices();
    void setNumOfDices(int numOfDices);
    int getMaxDamage();
    void setMaxDamage(int maxDamage);
}
