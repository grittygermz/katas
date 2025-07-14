package com.zukemon.refactor.fight;

import com.zukemon.refactor.fightmodes.*;
import com.zukemon.refactor.zukemons.ZukemonFactory;

public class FightModeFactory {
    public FightMode createFight(FightModeType fightModeType, ZukemonFactory zukemonFactory) {
        return switch (fightModeType) {
            case NORMAL -> new NormalFightMode(zukemonFactory);
            case DEFEND -> new DefendFightMode(zukemonFactory);
            case ROYAL_RUMBLE -> new RoyalRumbleFightMode(zukemonFactory);
        };
    }
}
