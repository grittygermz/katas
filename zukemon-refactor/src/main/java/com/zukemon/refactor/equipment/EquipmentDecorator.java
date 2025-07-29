package com.zukemon.refactor.equipment;

import com.zukemon.refactor.zukemons.Zukemon;

public abstract class EquipmentDecorator extends Zukemon {
    protected EquipmentDecorator() {
        super(0);
    }

    public abstract int hit();

    public abstract String equipped();
}
