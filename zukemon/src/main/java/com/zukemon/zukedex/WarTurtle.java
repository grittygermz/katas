package com.zukemon.zukedex;

import com.zukemon.DamageType;
import org.springframework.stereotype.Component;

@Component
public class WarTurtle implements Zukemon {
    @Override
    public int getAttackerType() {
        return 8;
    }

    @Override
    public int getBaseDamage() {
        return 300;
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
