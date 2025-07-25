package com.zukemon.refactor.fightmodes;

import com.zukemon.refactor.zukemons.Zukemon;
import com.zukemon.refactor.zukemons.ZukemonFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RoyalRumbleFightMode extends FightMode {

    RoyalRumbleRandomSelector royalRumbleRandomSelector;

    public RoyalRumbleFightMode(ZukemonFactory zukemonFactory) {
        this(zukemonFactory, new RoyalRumbleRandomSelector());
    }

    public RoyalRumbleFightMode(ZukemonFactory zukemonFactory, RoyalRumbleRandomSelector royalRumbleRandomSelector) {
        super(zukemonFactory);
        this.royalRumbleRandomSelector = royalRumbleRandomSelector;
    }

    public Zukemon fight() {
        List<Zukemon> fighters = IntStream.range(0, 5)
                .mapToObj(i -> zukemonFactory.createRandomZukemon())
                .collect(Collectors.toList());

        return fight(fighters);
    }

    Zukemon fight(List<Zukemon> fighters) {
        while (fighters.size() > 1) {
            ChosenFighters chosenFighters = royalRumbleRandomSelector.getRandomFighterAndDefender(fighters);

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
}
