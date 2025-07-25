package com.zukemon.refactor.fightmodes;

import com.zukemon.refactor.log.FightObserver;
import com.zukemon.refactor.zukemons.Blastoise;
import com.zukemon.refactor.zukemons.Pikachu;
import com.zukemon.refactor.zukemons.Zukemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NormalFightModeTest {

    NormalFightMode normalFightMode;

    @Mock
    FightObserver fightObserver;

    @BeforeEach
    void setUp() {
        normalFightMode = new NormalFightMode(null);
    }

    @Test
    void shouldFight() {
        Zukemon winner = normalFightMode.fight(new Blastoise(), new Pikachu());
        assertThat(winner).isInstanceOf(Blastoise.class);
    }

    @Test
    void shouldInvokeObservers() {
        normalFightMode.addObserver(fightObserver);

        normalFightMode.fight(new Blastoise(), new Pikachu());

        verify(fightObserver, times(3)).updateDamage(any(Zukemon.class), any(Zukemon.class), anyInt());
        verify(fightObserver, times(1)).updateHighScore(any(Zukemon.class), anyInt());
        verify(fightObserver, times(1)).updateGameEnd(anyString());
    }

}