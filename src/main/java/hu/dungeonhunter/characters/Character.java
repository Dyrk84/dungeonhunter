package hu.dungeonhunter.characters;

import hu.dungeonhunter.model.CharacterTypes;

public interface Character {
    int getHp();
    void setHp(int hp);
    CharacterTypes getType();
    int damage();
    int getDamage();
    boolean isDefeated();
    void setInitiation(int initiation);
    int getInitiation();
    int initiationCalculation();
    void setMaxDamage(int maxDamage);
    int getMaxDamage();
    int getAccuracy();
    int getNumOfDices();
    int accuracyCalculation(int accuracyRoll);
    int getDefense();
}
