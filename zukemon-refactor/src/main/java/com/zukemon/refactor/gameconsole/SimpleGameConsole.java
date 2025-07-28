package com.zukemon.refactor.gameconsole;

import com.zukemon.refactor.gameconsole.commands.Command;
import com.zukemon.refactor.gameconsole.commands.FightModeSelectorCommand;
import com.zukemon.refactor.gameconsole.commands.InitiateFightModeCommand;
import com.zukemon.refactor.gameconsole.commands.ZukemonSelectorCommand;
import com.zukemon.refactor.zukemons.Zukemon;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * to add a replay feature by using a decorator?
 */
@Getter
@Setter
public class SimpleGameConsole extends GameConsole {

    public SimpleGameConsole() {
        this(new HashMap<>(), new GameConsoleMemory(new ArrayList<>()));
    }

    public SimpleGameConsole(Map<String, Command> displayNameAndCommand, GameConsoleMemory gameConsoleMemory) {
        super(displayNameAndCommand, gameConsoleMemory);
    }

    public void init() {
        Command<Object, Integer> zukemonSelectorCommand = new ZukemonSelectorCommand(gameConsoleMemory);
        Command<Object, Integer> fightModeSelectorCommand = new FightModeSelectorCommand(gameConsoleMemory);
        Command<Zukemon, Object> initiateFightModeCommand = new InitiateFightModeCommand(gameConsoleMemory);

        this.displayNameAndCommand.put("select zukemon", zukemonSelectorCommand);
        this.displayNameAndCommand.put("select fightmode", fightModeSelectorCommand);
        this.displayNameAndCommand.put("fight!", initiateFightModeCommand);

        System.out.println("===== showing buttons available on game console =====");
        this.viewAvailableButtons();
        System.out.println("==========");
    }
}
