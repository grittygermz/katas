package com.zukemon.refactor.log;

import com.zukemon.refactor.zukemons.Zukemon;

public interface HistoryLogger {
    void logDeadMessage(Zukemon killedZukemon);
    void logRoundsSurvivedMessage(Zukemon killedZukemon, int numberOfSurvivedRounds);
    void logRoyalRumbleLoseMessage(Zukemon killedZukemon);
    void logDamage(Zukemon attacker, int attackerDamage, Zukemon defender);
}
