package com.zukemon.zukedex;

import com.zukemon.DamageType;
import org.springframework.stereotype.Component;

@Component
public class Blastoise implements Zukemon {
    @Override
    public int getAttackerType() {
        return 9;
    }

    @Override
    public int getActualDamage() {
        return this.getBaseDamage();
    }

    @Override
    public int getBaseDamage() {
        return 258;
    }

    @Override
    public DamageType getDamageType() {
        return DamageType.WATER;
    }

    @Override
    public int getCriticalHitPercentage() {
        return 0;
    }
}
