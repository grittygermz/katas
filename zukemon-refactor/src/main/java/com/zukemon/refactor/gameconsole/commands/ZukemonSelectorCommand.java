package com.zukemon.refactor.gameconsole.commands;

import com.zukemon.refactor.gameconsole.GameConsoleMemory;
import com.zukemon.refactor.gameconsole.receiver.ZukemonSelector;
import com.zukemon.refactor.zukemons.Zukemon;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ZukemonSelectorCommand implements Command<Object, Integer> {

    private final ZukemonSelector zukemonSelector = new ZukemonSelector();
    private final GameConsoleMemory gameConsoleMemory;

    @Override
    public Object execute(Integer zukemonType) {
        Zukemon zukemon = zukemonSelector.createZukemon(zukemonType);
        gameConsoleMemory.getSelectedZukemons().add(zukemon);
        return null;
    }

    @Override
    public void prompt() {
        zukemonSelector.getAvailableZukemons();
    }
}
