package com.zukemon.refactor;

import com.zukemon.refactor.fight.Fight;
import com.zukemon.refactor.fightmodes.FightModeType;
import com.zukemon.refactor.zukemons.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FightIntegrationTest {

    @Mock
    private ZukemonFactory zukemonFactory;

    @InjectMocks
    private Fight fight;

    @BeforeEach
    void setUp() {
        fight = new Fight(zukemonFactory);
    }

    @Test
    public void wartortleVsBlastoiseNormalMode() {
        Mockito.when(zukemonFactory.createRandomZukemon()).thenReturn(new Wartortle()).thenReturn(new Blastoise());

        Zukemon winner = fight.fight(FightModeType.NORMAL);

        assertThat(winner).isInstanceOf(Wartortle.class);
        assertThat(fight.getHighScore()).isEqualTo(300);
    }

    @Test
    public void blastoiseVsWartortleNormalMode() {
        Wartortle wartortle = new Wartortle();
        //to ensure that defender will win
        wartortle.increaseLifePointsBy(100000);
        Mockito.when(zukemonFactory.createRandomZukemon()).thenReturn(new Blastoise()).thenReturn(wartortle);

        Zukemon winner = fight.fight(FightModeType.NORMAL);

        assertThat(winner).isInstanceOf(Wartortle.class);
    }

    @Test
    public void wartortleVsBlastoiseDefendMode() {
        Mockito.when(zukemonFactory.createRandomZukemon()).thenReturn(new Wartortle()).thenReturn(new Blastoise());

        Zukemon winner = fight.fight(FightModeType.DEFEND);

        assertThat(winner).isInstanceOf(Wartortle.class);
    }

    @Test
    public void royalRumbleMode() {
        // just to make sure he wins
        Zukemon krookodile = new Krookodile();
        krookodile.increaseLifePointsBy(10000);
        Mockito.when(zukemonFactory.createRandomZukemon()).thenReturn(new Wartortle()).thenReturn(new Blastoise()).thenReturn(krookodile).thenReturn(new Mew()).thenReturn(new Pikachu());

        Zukemon winner = fight.fight(FightModeType.ROYAL_RUMBLE);

        assertThat(winner).isEqualTo(krookodile);
    }

}
