package com.zukemon.refactor.gameconsole.commands;

import com.zukemon.refactor.fightmodes.FightModeType;
import com.zukemon.refactor.gameconsole.GameConsoleMemory;
import com.zukemon.refactor.gameconsole.receiver.FightModeSelector;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FightModeSelectorCommand implements Command<Object, Integer> {

    private final FightModeSelector fightModeSelector = new FightModeSelector();
    private final GameConsoleMemory gameConsoleMemory;

    @Override
    public Object execute(Integer option) {
        FightModeType fightMode = fightModeSelector.getFightMode(option);
        gameConsoleMemory.setSelectedFightMode(fightMode);
        return null;
    }

    @Override
    public void prompt() {
        fightModeSelector.listFightModes();
    }
}
