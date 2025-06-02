package com.zukemon.zukedex;

import com.zukemon.DamageType;
import org.springframework.stereotype.Component;

@Component
public class Mew implements Zukemon {
    @Override
    public int getAttackerType() {
        return 151;
    }

    @Override
    public int getBaseDamage() {
        return 150;
    }

    @Override
    public DamageType getDamageType() {
        return DamageType.PSYCHIC;
    }

    @Override
    public int getCriticalHitPercentage() {
        return 10;
    }
}
