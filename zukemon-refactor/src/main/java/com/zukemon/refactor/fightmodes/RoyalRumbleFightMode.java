package com.zukemon.refactor.fightmodes;

import com.zukemon.refactor.zukemons.Zukemon;
import com.zukemon.refactor.zukemons.ZukemonFactory;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RoyalRumbleFightMode extends FightMode {

    public RoyalRumbleFightMode(ZukemonFactory zukemonFactory) {
        super(zukemonFactory);
    }

    public Zukemon fight() {
        List<Zukemon> fighters = IntStream.range(0, 5)
                .mapToObj(i -> zukemonFactory.createRandomZukemon())
                .collect(Collectors.toList());

        return fight(fighters);
    }

    Zukemon fight(List<Zukemon> fighters) {
        while (fighters.size() > 1) {
            ChosenFighters chosenFighters = getRandomFighterAndDefender(fighters);

            performAttackSequence(chosenFighters.attacker(), chosenFighters.defender());
            if (chosenFighters.defender().isDead()) {
                Zukemon killedZukemon = chosenFighters.defender();
                String deadMessage = "Zukemon '" + killedZukemon.getClass().getSimpleName() + "' is out of the royal rumble.\r\n";

                super.updateObserversWithGameEnd(deadMessage);
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
