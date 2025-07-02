package com.zukemon.refactor.log;

import com.zukemon.refactor.zukemons.Zukemon;

public interface ArenaDisplay {
    void showDamage(Zukemon zukemon, int damage);
    void showHighScore(Zukemon zukemon, int highScore);
}
