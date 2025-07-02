package com.zukemon.refactor.fightmodes;

import com.zukemon.refactor.zukemons.ZukemonFactory;
import com.zukemon.refactor.log.ArenaDisplay;
import com.zukemon.refactor.log.HistoryLogger;
import com.zukemon.refactor.zukemons.Zukemon;

public class NormalFightMode extends AbstractFightMode {

    public NormalFightMode(ZukemonFactory zukemonFactory, ArenaDisplay arenaDisplay, HistoryLogger historyLogger) {
        super(zukemonFactory, arenaDisplay, historyLogger);
    }

    public Zukemon fight() {
        Zukemon attacker = zukemonFactory.createRandomZukemon();
        Zukemon defender = zukemonFactory.createRandomZukemon();
        while (true) {
            performAttackSequence(attacker, defender);
            if (defender.isDead()) {
                historyLogger.logDeadMessage(defender);
                return attacker;
            }

            performAttackSequence(defender, attacker);
            if (attacker.isDead()) {
                historyLogger.logDeadMessage(attacker);
                return defender;
            }
        }
    }
}
