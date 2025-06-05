package com.zukemon.fight;

import com.zukemon.ZukemonFactory;
import org.springframework.stereotype.Component;

@Component
public class Fight {

    private final ZukemonFactory zukemonFactory;

    public Fight(ZukemonFactory zukemonFactory) {
        this.zukemonFactory = zukemonFactory;
    }

    //CriticalHit criticalHit = new CriticalHit();

    /**
     * Blastoise #9 Water Damage 258
     * Mew #151 Psychic Damage 150 (10% chance of critical hit)
     * Wartortle #8 Water Damage 300
     * Mudkip #258 Water Damage 234
     * Pikachu #25 Electric Damage 135
     * Psyduck #54 Water Damage 127 (20% chance of critical hit)
     * Krookodile #553 Dark No Damage -> It is the team lead, so he can call his team members to arms. Add the
     * damage of all other Zukemons
     *
     * Critical damage means the damage will be doubled
     *
     * @param attackerType
     */
    public int hit(int attackerType) {
        return zukemonFactory.getZukemon(attackerType).getActualDamage();
    }
}
