package com.zukemon.refactor.fightmodes;

import com.zukemon.refactor.zukemons.Zukemon;

import java.util.List;
import java.util.Random;

/*
had to extract this to a separate class in order to mock it for RoyalRumbleFightModeTest. is it ok?
 */
public class RoyalRumbleRandomSelector {
    ChosenFighters getRandomFighterAndDefender(List<Zukemon> fighters) {
        Zukemon attacker = fighters.get(new Random().nextInt(fighters.size()));
        Zukemon defender = fighters.get(new Random().nextInt(fighters.size()));
        while (attacker == defender) {
            defender = fighters.get(new Random().nextInt(fighters.size()));
        }
        return new ChosenFighters(attacker, defender);
    }
}
