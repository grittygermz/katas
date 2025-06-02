package com.zukemon.zukedex;

import com.zukemon.DamageType;
import org.springframework.stereotype.Component;

@Component
public class Pikachu implements Zukemon {
    @Override
    public int getAttackerType() {
        return 25;
    }

    @Override
    public int getBaseDamage() {
        return 135;
    }

    @Override
    public DamageType getDamageType() {
        return DamageType.ELECTRIC;
    }

    @Override
    public int getCriticalHitPercentage() {
        return 0;
    }
}
