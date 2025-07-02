package com.zukemon.refactor.fight;

import com.zukemon.refactor.fightmodes.*;
import com.zukemon.refactor.log.ArenaDisplay;
import com.zukemon.refactor.log.HistoryLogger;
import com.zukemon.refactor.zukemons.Zukemon;
import com.zukemon.refactor.zukemons.ZukemonFactory;

public class Fight {

    private final ZukemonFactory zukemonFactory;
    private AbstractFightMode abstractFightMode;


    // since these 2 are no longer static they need to be passed in from the client.. makes testing easy but makes the class hard to use?
    // should i wire this as a bean and let spring inject it? but does it make testing more difficult? but it is also possible to mock a bean?? to be verified
    // as discussed.. having them passed in makes them easily swappable in the future?
    private final ArenaDisplay arenaDisplay;
    private final HistoryLogger historyLogger;

    public Fight(ZukemonFactory zukemonFactory, ArenaDisplay arenaDisplay, HistoryLogger historyLogger) {
        this.zukemonFactory = zukemonFactory;
        this.arenaDisplay = arenaDisplay;
        this.historyLogger = historyLogger;
    }

    //private final ArenaDisplay arenaDisplay = new ArenaDisplay();
    //private int highScore = 0;

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

        //the abstract class that the concrete class that it extends from now looks super complex with so many fields? is this bad?
        abstractFightMode = switch (fightModeType) {
            case NORMAL -> new NormalFightMode(zukemonFactory, arenaDisplay, historyLogger);
            case DEFEND -> new DefendFightMode(zukemonFactory, arenaDisplay, historyLogger);
            case ROYAL_RUMBLE -> new RoyalRumbleFightMode(zukemonFactory, arenaDisplay, historyLogger);
        };

        return abstractFightMode.fight();
    }

    public int getHighScore() {
        return abstractFightMode.getHighScore();
    }

    /*
    S - SRP - only 1 reason to change
    O - OCP - open for extension closed for modification
    L - Liskov substituition
    I - interface segregation
    D - DI
     */

}
