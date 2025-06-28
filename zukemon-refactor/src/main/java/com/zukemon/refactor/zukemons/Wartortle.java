package com.zukemon.refactor.zukemons;

public class Wartortle extends Zukemon {
    public static final int NUMBER = 8;

    public Wartortle() {
        super(400);
    }

    @Override
    public int hit() {
        return 300;
    }
}
