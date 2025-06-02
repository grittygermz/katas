package com.zukemon.zukedex;

import com.zukemon.DamageType;
import com.zukemon.fight.CriticalHit;

public interface Zukemon {
    int getAttackerType();
    int getBaseDamage();
    DamageType getDamageType();
    int getCriticalHitPercentage();

    default int getActualDamage() {
        CriticalHit criticalHit = new CriticalHit();
        return criticalHit.isCriticalHit(getCriticalHitPercentage()) ? getBaseDamage() * 2 : getBaseDamage();
    }

}
