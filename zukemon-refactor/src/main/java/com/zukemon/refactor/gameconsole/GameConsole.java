package com.zukemon.refactor.gameconsole;

import com.zukemon.refactor.gameconsole.commands.Command;

import java.util.Map;

public abstract class GameConsole {
    protected final Map<String, Command> displayNameAndCommand;
    protected final GameConsoleMemory gameConsoleMemory;

    public GameConsole(Map<String, Command> displayNameAndCommand, GameConsoleMemory gameConsoleMemory) {
        this.displayNameAndCommand = displayNameAndCommand;
        this.gameConsoleMemory = gameConsoleMemory;
    }

    abstract void init();

    public Object runButtonCommand(String buttonName, Object input) {
        return displayNameAndCommand.get(buttonName).execute(input);
    }

    public void runButtonPrompt(String buttonName) {
        displayNameAndCommand.get(buttonName).prompt();
    }

    public void viewAvailableButtons() {
        for (String buttonDisplayName : displayNameAndCommand.keySet()) {
            System.out.println(buttonDisplayName);
        }
    }
}
