package hu.dungeonhunter.characters;

import hu.dungeonhunter.characters.champion.Champion;
import hu.dungeonhunter.characters.monsters.Goblin;
import hu.dungeonhunter.characters.monsters.GoblinKing;
import hu.dungeonhunter.model.CharacterTypes;
import lombok.Getter;
import lombok.Setter;

public class MonsterFactory {

    @Setter
    @Getter
    int killedMonsterCounter = 0;

    public Character getCharacter(CharacterTypes type) {
        Character character;
        if (type == CharacterTypes.CHAMPION) {
            throw new RuntimeException("champion is not a monster type!");
        } else if (type == CharacterTypes.GOBLIN_KING) {
            character = new GoblinKing();
        } else {
            character = new Goblin();
        }
        return character;
    }
}