package com.zukemon.refactor.fightmodes;

import com.zukemon.refactor.log.ArenaDisplay;
import com.zukemon.refactor.zukemons.ZukemonFactory;
import com.zukemon.refactor.log.HistoryLogger;
import com.zukemon.refactor.zukemons.Zukemon;

public class AbstractFightMode implements FightMode {

    protected ZukemonFactory zukemonFactory;
    private int highScore = 0;
    protected ArenaDisplay arenaDisplay;
    protected HistoryLogger historyLogger;

    public AbstractFightMode(ZukemonFactory zukemonFactory, ArenaDisplay arenaDisplay, HistoryLogger historyLogger) {
        this.zukemonFactory = zukemonFactory;
        this.arenaDisplay = arenaDisplay;
        this.historyLogger = historyLogger;
    }

    protected void performAttackSequence(Zukemon attacker, Zukemon defender) {
        int attackerDamage = attacker.hit();
        defender.reduceLifePointsBy(attackerDamage);

        arenaDisplay.showDamage(attacker, attackerDamage);
        updateHighScore(attackerDamage, attacker);

        historyLogger.logDamage(attacker, attackerDamage, defender);
    }

    private void updateHighScore(int attackerDamage, Zukemon attacker) {
        if (attackerDamage > highScore) {
            highScore = attackerDamage;
            arenaDisplay.showHighScore(attacker, highScore);
        }
    }

    public int getHighScore() {
        return highScore;
    }

    @Override
    public Zukemon fight() {
        return null;
    }
}
