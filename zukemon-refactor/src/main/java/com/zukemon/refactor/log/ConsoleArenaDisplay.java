package com.zukemon.refactor.log;

import com.zukemon.refactor.zukemons.Zukemon;

public class ConsoleArenaDisplay implements ArenaDisplay {

    public void showDamage(Zukemon zukemon, int damage) {
        System.out.println(zukemon.getClass().getSimpleName() + " made " + damage + " damage");
    }

    public void showHighScore(Zukemon zukemon, int highScore) {
        System.out.println("New highscore from " + zukemon.getClass().getSimpleName() + ": " + highScore);
    }
}
