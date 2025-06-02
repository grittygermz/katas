package com.zukemon.zukedex;

import com.zukemon.DamageType;
import org.springframework.stereotype.Component;

@Component
public class Mudkip implements Zukemon {
    @Override
    public int getAttackerType() {
        return 258;
    }

    @Override
    public int getBaseDamage() {
        return 234;
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
