package com.zukemon.refactor.fight;

import com.zukemon.refactor.fightmodes.FightMode;
import com.zukemon.refactor.fightmodes.FightModeType;
import com.zukemon.refactor.log.ConsoleArenaDisplay;
import com.zukemon.refactor.log.FileHistoryLogger;
import com.zukemon.refactor.zukemons.Zukemon;
import com.zukemon.refactor.zukemons.ZukemonFactory;

public class Fight {

    private FightMode fightMode;

    private final String historyFileName;
    private final ZukemonFactory zukemonFactory;
    private final FightModeFactory fightModeFactory;

    public Fight(String historyFileName, ZukemonFactory zukemonFactory, FightModeFactory fightModeFactory) {
        this.historyFileName = historyFileName;
        this.zukemonFactory = zukemonFactory;
        this.fightModeFactory = fightModeFactory;
    }

    public Fight(ZukemonFactory zukemonFactory) {
        this("history.txt", zukemonFactory, new FightModeFactory());
    }


    /**
     * Blastoise #9 Water Damage 258
     * Mew #151 Psychic Damage 150 (10% chance of critical hit)
     * Wartortle #8 Water Damage 300
     * Mudkip #258 Water Damage 234
     * Pikachu #25 Electric Damage 135
     * Psyduck #54 Water Damage 127 (20% chance of critical hit)
     * Krookodile #553 Dark No Damage -> It is the team lead, so he can call his team members to arms. Add the
     * damage of all other Zukemons
     * <p>
     * Critical hits make double damage
     *
     * @param fightModeType the type of fightMode
     */
    public Zukemon fight(FightModeType fightModeType) {

        FightMode fightMode = fightModeFactory.createFight(fightModeType, zukemonFactory);
        this.fightMode = fightMode;
        this.addObservers(fightMode);

        return fightMode.fight();
    }

    public int getHighScore() {
        return fightMode.getHighScore();
    }

    void addObservers(FightMode fightMode) {
        new ConsoleArenaDisplay(fightMode);
        new FileHistoryLogger(historyFileName, fightMode);
    }



    /*
    S - SRP - only 1 reason to change
    O - OCP - open for extension closed for modification
    L - Liskov substituition
    I - interface segregation
    D - DI
     */

}
