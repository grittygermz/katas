package com.zukemon.refactor.gameconsole;

import com.zukemon.refactor.fightmodes.FightModeType;
import com.zukemon.refactor.zukemons.Zukemon;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
public class GameConsoleMemory {
    private final List<Zukemon> selectedZukemons;
    private FightModeType selectedFightMode;
}
