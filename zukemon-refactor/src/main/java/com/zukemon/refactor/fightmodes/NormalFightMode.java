package com.zukemon.refactor.fightmodes;

import com.zukemon.refactor.zukemons.Zukemon;
import com.zukemon.refactor.zukemons.ZukemonFactory;

public class NormalFightMode extends FightMode {

    public NormalFightMode(ZukemonFactory zukemonFactory) {
        super(zukemonFactory);
    }

    public Zukemon fight() {
        Zukemon attacker = zukemonFactory.createRandomZukemon();
        Zukemon defender = zukemonFactory.createRandomZukemon();
        return fight(attacker, defender);
    }

    Zukemon fight(Zukemon attacker, Zukemon defender) {

        Zukemon deadZukemon;
        while (true) {
            deadZukemon = attack(defender, attacker);
            if (deadZukemon != null) return deadZukemon;

            deadZukemon = attack(attacker, defender);
            if (deadZukemon != null) return deadZukemon;
        }
    }

    private Zukemon attack(Zukemon attacker, Zukemon defender) {
        performAttackSequence(defender, attacker);
        if (attacker.isDead()) {
            String deadMessage = "Zukemon '" + attacker.getClass().getSimpleName() + "' is dead looser";
            super.updateObserversWithGameEnd(deadMessage);
            return defender;
        }
        return null;
    }
}
