package com.zukemon.refactor.fight;

import com.zukemon.refactor.fightmodes.FightMode;
import com.zukemon.refactor.fightmodes.FightModeType;
import com.zukemon.refactor.zukemons.Blastoise;
import com.zukemon.refactor.zukemons.Zukemon;
import com.zukemon.refactor.zukemons.ZukemonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FightTest {

    @Mock
    ZukemonFactory zukemonFactory;

    //cant mock a string
    //@Mock
    //String historyFileName;

    @Mock
    FightModeFactory fightModeFactory;

    @Mock
    FightMode fightMode;

    @InjectMocks
    Fight fight;

    @BeforeEach
    void setUp() {
        fight = new Fight("history.txt", zukemonFactory, fightModeFactory);
    }

    @Test
    void shouldFight() {
        Zukemon blastoise = new Blastoise();

        when(fightModeFactory.createFight(any(FightModeType.class), eq(zukemonFactory)))
                .thenReturn(fightMode);
        //when(zukemonFactory.createRandomZukemon()).thenReturn(blastoise, new Wartortle());
        doNothing().when(fightMode).addObserver(any());
        when(fightMode.fight()).thenReturn(blastoise);

        Zukemon winner = fight.fight(FightModeType.NORMAL);

        verify(fightMode, times(2)).addObserver(any());
        verify(fightMode).fight();
        assertThat(winner).isEqualTo(blastoise);
    }

    @Test
    void shouldGetHighScore() {
        when(fightMode.getHighScore()).thenReturn(10);

        assertThat(fight.getHighScore()).isEqualTo(10);
    }

}