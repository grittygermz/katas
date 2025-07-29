package com.zukemon.refactor.gameconsole.commands;

import com.zukemon.refactor.gameconsole.GameConsoleMemory;
import com.zukemon.refactor.gameconsole.receiver.EquipmentSelector;
import com.zukemon.refactor.zukemons.Zukemon;

import java.io.IOException;
import java.util.List;

public class EquipmentSelectorCommand implements Command<Object, String> {

    private final EquipmentSelector equipmentSelector;
    private final GameConsoleMemory gameConsoleMemory;

    public EquipmentSelectorCommand(GameConsoleMemory gameConsoleMemory) throws IOException, ClassNotFoundException {
        this.equipmentSelector = new EquipmentSelector();
        this.gameConsoleMemory = gameConsoleMemory;
    }

    @Override
    public Object execute(String equipment) {
        List<Zukemon> selectedZukemons = gameConsoleMemory.getSelectedZukemons();
        if(selectedZukemons.isEmpty()) {
            throw new RuntimeException("no zukemon to equip");
        }
        Zukemon currentZukemon = selectedZukemons.removeLast();
        Zukemon equippedZukemon = equipmentSelector.equip(currentZukemon, equipment);
        selectedZukemons.add(equippedZukemon);
        return null;
    }

    @Override
    public void prompt() {
        System.out.println("select an equipment for your zukemon");
    }
}
