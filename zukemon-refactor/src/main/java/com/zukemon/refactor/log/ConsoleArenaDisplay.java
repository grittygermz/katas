package com.zukemon.refactor.log;

import com.zukemon.refactor.zukemons.Zukemon;

public class ConsoleArenaDisplay implements FightObserver {

    @Override
    public void updateDamage(Zukemon attacker, Zukemon defender, int damage) {
        System.out.println(attacker.getClass().getSimpleName() + " made " + damage + " damage");
    }

    @Override
    public void updateHighScore(Zukemon zukemon, int highScore) {
        System.out.println("New highscore from " + zukemon.getClass().getSimpleName() + ": " + highScore);
    }

    @Override
    public void updateGameEnd(String gameEndMessage) {
    }
}
