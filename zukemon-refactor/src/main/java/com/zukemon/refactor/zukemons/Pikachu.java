package com.zukemon.refactor.zukemons;

public class Pikachu extends Zukemon {
    public static final int NUMBER = 25;

    public Pikachu() {
        super(400);
    }

    @Override
    public int hit() {
        return 135;
    }
}
