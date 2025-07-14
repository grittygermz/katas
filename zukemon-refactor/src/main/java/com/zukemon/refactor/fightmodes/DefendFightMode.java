package com.zukemon.refactor.fightmodes;

import com.zukemon.refactor.zukemons.ZukemonFactory;
import com.zukemon.refactor.log.ArenaDisplay;
import com.zukemon.refactor.log.HistoryLogger;
import com.zukemon.refactor.zukemons.Zukemon;

public class DefendFightMode extends FightMode {

    public DefendFightMode(ZukemonFactory zukemonFactory, ArenaDisplay arenaDisplay, HistoryLogger historyLogger) {
        super(zukemonFactory, arenaDisplay, historyLogger);
    }

    public Zukemon fight() {
        Zukemon attacker = zukemonFactory.createRandomZukemon();
        Zukemon defender = zukemonFactory.createRandomZukemon();
        return fight(defender, attacker);
    }

    Zukemon fight(Zukemon defender, Zukemon attacker) {
        int initialLifePoints = defender.getLifePoints();
        // The defender gets super much life points
        defender.increaseLifePointsBy(5000);
        int numberOfSurvivedRounds = 0;
        while (true) {
            performAttackSequence(attacker, defender);
            if (defender.isDead()) {
                historyLogger.logRoundsSurvivedMessage(defender, numberOfSurvivedRounds);
                return attacker;
            }

            //heal 10% of initial lifepoints
            defender.increaseLifePointsBy(initialLifePoints / 100 * 10);
            numberOfSurvivedRounds++;
        }
    }
}
