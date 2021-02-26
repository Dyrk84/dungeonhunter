package hu.dungeonhunter.characters.monsters;

import hu.dungeonhunter.model.CharacterTypes;
import lombok.Getter;
import lombok.Setter;

public class MonsterFactory {

    @Setter
    @Getter
    int killedMonsterCounter = 0;

    public MonstersInterface getMonster(CharacterTypes type) {
        MonstersInterface monster;
        if (type == CharacterTypes.GOBLIN) {
            monster = new Goblin();
        } else {
            monster = new GoblinKing();
        }
        return monster;
    }
}