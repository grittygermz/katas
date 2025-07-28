package com.zukemon.refactor.fightmodes;

import com.zukemon.refactor.zukemons.Zukemon;
import com.zukemon.refactor.zukemons.ZukemonFactory;

import java.util.List;

public class DefendFightMode extends FightMode {

    int numberOfSurvivedRounds = 0;

    public DefendFightMode(ZukemonFactory zukemonFactory) {
        super(zukemonFactory);
    }

    public Zukemon fight() {
        Zukemon attacker = zukemonFactory.createRandomZukemon();
        Zukemon defender = zukemonFactory.createRandomZukemon();
        return fight(defender, attacker);
    }

    @Override
    public Zukemon fight(List<Zukemon> zukemons) {
        return this.fight(zukemons.get(0), zukemons.get(1));
    }

    public Zukemon fight(Zukemon defender, Zukemon attacker) {
        int initialLifePoints = defender.getLifePoints();
        // The defender gets super much life points
        defender.increaseLifePointsBy(5000);
        while (true) {
            performAttackSequence(attacker, defender);
            if (defender.isDead()) {
                String deadMessage = "Zukemon '" + defender.getClass().getSimpleName() + "' has survived " + numberOfSurvivedRounds + " rounds.\r\n";
                super.updateObserversWithGameEnd(deadMessage);
                return attacker;
            }

            //heal 10% of initial lifepoints
            defender.increaseLifePointsBy(initialLifePoints / 100 * 10);
            numberOfSurvivedRounds++;
        }
    }
}
