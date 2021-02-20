package hu.dungeonhunter.characters.monsters;

import hu.dungeonhunter.model.CharacterTypes;

public class MonsterFactory {

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
}
