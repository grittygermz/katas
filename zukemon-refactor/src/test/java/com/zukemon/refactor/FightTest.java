package com.zukemon.refactor;

import com.zukemon.refactor.fight.Fight;
import com.zukemon.refactor.fightmodes.FightModeType;
import com.zukemon.refactor.log.ArenaDisplay;
import com.zukemon.refactor.log.HistoryLogger;
import com.zukemon.refactor.zukemons.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FightTest {

    @Mock
    private ZukemonFactory factory;

    @Mock
    private ArenaDisplay arenaDisplay;

    @Mock
    private HistoryLogger historyLogger;

    //works without injectmock here??
    @InjectMocks
    private Fight fight;

    // is this still needed if i use injectmock annotation?
    //@BeforeEach
    //public void setUp() {
    //    fight = new Fight(factory, arenaDisplay, historyLogger);
    //    //fight.zukemonFactory = factory;
    //}

    @Test
    public void wartortleVsBlastoiseNormalMode() {
        Mockito.when(factory.createRandomZukemon()).thenReturn(new Wartortle()).thenReturn(new Blastoise());

        Zukemon winner = fight.fight(FightModeType.NORMAL);

        assertThat(winner).isInstanceOf(Wartortle.class);
        assertThat(fight.getHighScore()).isEqualTo(300);
    }

    @Test
    public void blastoiseVsWartortleNormalMode() {
        Wartortle wartortle = new Wartortle();
        //to ensure that defender will win
        wartortle.increaseLifePointsBy(100000);
        Mockito.when(factory.createRandomZukemon()).thenReturn(new Blastoise()).thenReturn(wartortle);

        Zukemon winner = fight.fight(FightModeType.NORMAL);

        assertThat(winner).isInstanceOf(Wartortle.class);
    }

    @Test
    public void wartortleVsBlastoiseDefendMode() {
        Mockito.when(factory.createRandomZukemon()).thenReturn(new Wartortle()).thenReturn(new Blastoise());

        Zukemon winner = fight.fight(FightModeType.DEFEND);

        assertThat(winner).isInstanceOf(Wartortle.class);
    }

    @Test
    public void royalRumbleMode() {
        // just to make sure he wins
        Zukemon krookodile = new Krookodile();
        krookodile.increaseLifePointsBy(10000);
        Mockito.when(factory.createRandomZukemon()).thenReturn(new Wartortle()).thenReturn(new Blastoise()).thenReturn(krookodile).thenReturn(new Mew()).thenReturn(new Pikachu());

        Zukemon winner = fight.fight(FightModeType.ROYAL_RUMBLE);

        assertThat(winner).isEqualTo(krookodile);
    }

}
