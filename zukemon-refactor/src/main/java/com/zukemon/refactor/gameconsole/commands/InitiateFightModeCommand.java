package com.zukemon.refactor.gameconsole.commands;

import com.zukemon.refactor.fight.FightModeFactory;
import com.zukemon.refactor.fightmodes.FightMode;
import com.zukemon.refactor.fightmodes.FightModeType;
import com.zukemon.refactor.gameconsole.GameConsoleMemory;
import com.zukemon.refactor.log.ConsoleArenaDisplay;
import com.zukemon.refactor.log.FileHistoryLogger;
import com.zukemon.refactor.zukemons.Zukemon;

import java.util.List;

public class InitiateFightModeCommand implements Command<Zukemon, Object> {

    private final FightModeFactory fightModeFactory;
    private final String historyFileName;
    private final GameConsoleMemory gameConsoleMemory;

    public InitiateFightModeCommand(GameConsoleMemory gameConsoleMemory) {
        this(new FightModeFactory(), "history.txt", gameConsoleMemory);
    }

    public InitiateFightModeCommand(FightModeFactory fightModeFactory, String historyFileName, GameConsoleMemory gameConsoleMemory) {
        this.fightModeFactory = fightModeFactory;
        this.historyFileName = historyFileName;
        this.gameConsoleMemory = gameConsoleMemory;
    }

    @Override
    public Zukemon execute(Object object) {

        List<Zukemon> contenders = gameConsoleMemory.getSelectedZukemons();
        if(contenders.size() < 2) {
            throw new RuntimeException("should have at least 2 zukemons selected");
        }

        FightModeType selectedFightMode = gameConsoleMemory.getSelectedFightMode();
        if(selectedFightMode == null) {
            throw new RuntimeException("should have selected a fightMode");
        }

        FightMode fightMode = fightModeFactory.createFight(gameConsoleMemory.getSelectedFightMode(), null);
        addObservers(fightMode);

        return fightMode.fight(contenders);
    }

    @Override
    public void prompt() {
        System.out.println("fightmodetype must be selected, minimum of 2 zukemons must have been selected");
    }

    void addObservers(FightMode fightMode) {
        new ConsoleArenaDisplay(fightMode);
        new FileHistoryLogger(historyFileName, fightMode);
    }
}
