package com.zukemon.refactor.zukemons;

import com.zukemon.refactor.CriticalHit;

public class Psyduck extends Zukemon {

    public static final int NUMBER = 54;

    private static final int damage = 127;

    CriticalHit criticalHit = new CriticalHit();

    public Psyduck() {
        super(500);
    }

    @Override
    public int hit() {
        if(criticalHit.isCriticalHit(20)) {
            return 2* damage;
        }
        return damage;
    }
}
