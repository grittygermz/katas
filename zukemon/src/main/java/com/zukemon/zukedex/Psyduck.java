package com.zukemon.zukedex;

import com.zukemon.DamageType;
import org.springframework.stereotype.Component;

@Component
public class Psyduck implements Zukemon {
    @Override
    public int getAttackerType() {
        return 54;
    }

    @Override
    public int getBaseDamage() {
        return 127;
    }

    @Override
    public DamageType getDamageType() {
        return DamageType.WATER;
    }

    @Override
    public int getCriticalHitPercentage() {
        return 20;
    }
}
