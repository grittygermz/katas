package com.zukemon.refactor;

import com.zukemon.refactor.zukemons.Zukemon;

public class ArenaDisplay {

    public static void showDamage(Zukemon zukemon, int damage) {
        System.out.println(zukemon.getClass().getSimpleName() + " made " + damage + " damage");
    }

    public static void showHighScore(Zukemon zukemon, int highScore) {
        System.out.println("New highscore from " + zukemon.getClass().getSimpleName() + ": " + highScore);
    }
}
