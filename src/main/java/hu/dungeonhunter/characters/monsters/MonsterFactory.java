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

    public MonstersInterface getMonster(CharacterTypes type, int hp) {
        if (type == CharacterTypes.GOBLIN) {
            return new Goblin(hp);
        } else {
            return new GoblinKing(hp);
        }
    }

    public MonstersInterface getMonster(CharacterTypes type, int hp, int initiative, int accuracy, int defense) {
        if (type == CharacterTypes.GOBLIN) {
            return new Goblin(hp, initiative, accuracy, defense);
        } else {
            return new GoblinKing(hp, initiative, accuracy, defense);
        }
    }
}