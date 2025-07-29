package com.zukemon.refactor.equipment;

import com.zukemon.refactor.zukemons.Zukemon;

public class Sword extends EquipmentDecorator {

    private final Zukemon zukemon;
    private final int DAMAGE = 20;

    public Sword(Zukemon zukemon) {
        this.zukemon = zukemon;
    }

    @Override
    public int hit() {
        return DAMAGE + zukemon.hit();
    }

    @Override
    public String equipped() {
        return "sword, %s".formatted(zukemon.equipped());
    }
}
