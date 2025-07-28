package com.zukemon.refactor.gameconsole.receiver;

import com.zukemon.refactor.fightmodes.FightModeType;

public class FightModeSelector {

    public void listFightModes() {
        for (int i = 0; i < FightModeType.values().length; i++) {
            FightModeType value = FightModeType.values()[i];
            System.out.printf("%d: %s\n", i, value.toString());
        }
    }

    public FightModeType getFightMode(int option) {
        if(option > FightModeType.values().length - 1) {
            throw new IllegalArgumentException("%d is not a supported fightmode option".formatted(option));
        }
        return FightModeType.values()[option];
    }
}
