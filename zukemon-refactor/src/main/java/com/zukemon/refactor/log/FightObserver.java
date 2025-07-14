package com.zukemon.refactor.log;

import com.zukemon.refactor.zukemons.Zukemon;

public interface FightObserver {
    void updateDamage(Zukemon attacker, Zukemon defender, int damage);
    void updateHighScore(Zukemon zukemon, int highScore);
    void updateGameEnd(String gameEndMessage);
}
