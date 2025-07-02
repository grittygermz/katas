package com.zukemon.refactor.fightmodes;

import com.zukemon.refactor.log.ArenaDisplay;
import com.zukemon.refactor.zukemons.ZukemonFactory;
import com.zukemon.refactor.log.HistoryLogger;
import com.zukemon.refactor.zukemons.Zukemon;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RoyalRumbleFightMode extends AbstractFightMode {

    public RoyalRumbleFightMode(ZukemonFactory zukemonFactory, ArenaDisplay arenaDisplay, HistoryLogger historyLogger) {
        super(zukemonFactory, arenaDisplay, historyLogger);
    }

    public Zukemon fight() {
        List<Zukemon> fighters = IntStream.range(0, 5)
                .mapToObj(i -> zukemonFactory.createRandomZukemon())
                .collect(Collectors.toList());

        while (fighters.size() > 1) {
            ChosenFighters chosenFighters = getRandomFighterAndDefender(fighters);

            performAttackSequence(chosenFighters.attacker(), chosenFighters.defender());
            if (chosenFighters.defender().isDead()) {
                historyLogger.logRoyalRumbleLoseMessage(chosenFighters.defender());
                fighters.remove(chosenFighters.defender());
            }
        }
        return fighters.getFirst();
    }

    private static ChosenFighters getRandomFighterAndDefender(List<Zukemon> fighters) {
        Zukemon attacker = fighters.get(new Random().nextInt(fighters.size()));
        Zukemon defender = fighters.get(new Random().nextInt(fighters.size()));
        while (attacker == defender) {
            defender = fighters.get(new Random().nextInt(fighters.size()));
        }
        return new ChosenFighters(attacker, defender);
    }

    private record ChosenFighters(Zukemon attacker, Zukemon defender) {
    }
}
