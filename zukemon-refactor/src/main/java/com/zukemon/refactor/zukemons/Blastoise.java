package com.zukemon.refactor.zukemons;

public class Blastoise extends Zukemon {

    public static final int NUMBER = 9;

    public Blastoise() {
        super(600);
    }

    @Override
    public int hit() {
        return 258;
    }
}
