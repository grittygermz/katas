package com.zukemon.refactor.fight;

import com.zukemon.refactor.fightmodes.*;
import com.zukemon.refactor.log.ArenaDisplay;
import com.zukemon.refactor.log.ConsoleArenaDisplay;
import com.zukemon.refactor.log.FileHistoryLogger;
import com.zukemon.refactor.log.HistoryLogger;
import com.zukemon.refactor.zukemons.Zukemon;
import com.zukemon.refactor.zukemons.ZukemonFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Fight {

    private FightMode abstractFightMode;

    private final ZukemonFactory zukemonFactory;
    private final ArenaDisplay arenaDisplay;
    private final HistoryLogger historyLogger;

    public Fight() {
        this(new ZukemonFactory(), new ConsoleArenaDisplay(), new FileHistoryLogger("history.txt"));
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
