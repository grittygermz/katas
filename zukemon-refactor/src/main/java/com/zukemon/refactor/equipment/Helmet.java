package com.zukemon.refactor.equipment;

import com.zukemon.refactor.zukemons.Zukemon;

public class Helmet extends EquipmentDecorator {

    private final Zukemon zukemon;
    private final int DAMAGE = 5;

    public Helmet(Zukemon zukemon) {
        this.zukemon = zukemon;
    }

    @Override
    public int hit() {
        return DAMAGE + zukemon.hit();
    }

    @Override
    public String equipped() {
        return "helmet, %s".formatted(zukemon.equipped());
    }
}
