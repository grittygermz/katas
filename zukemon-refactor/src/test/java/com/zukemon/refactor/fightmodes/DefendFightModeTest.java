package com.zukemon.refactor.fightmodes;

import com.zukemon.refactor.log.FightObserver;
import com.zukemon.refactor.zukemons.Blastoise;
import com.zukemon.refactor.zukemons.Pikachu;
import com.zukemon.refactor.zukemons.Zukemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DefendFightModeTest {

    DefendFightMode defendFightMode;

    @Mock
    FightObserver observer;

    @BeforeEach
    void setUp() {
        defendFightMode = new DefendFightMode(null);
    }

    @Test
    void shouldFight() {
        Zukemon winner = defendFightMode.fight(new Blastoise(), new Pikachu());

        assertThat(defendFightMode.numberOfSurvivedRounds).isEqualTo(73);
        assertThat(winner).isInstanceOf(Pikachu.class);
        assertThat(defendFightMode.getHighScore()).isEqualTo(135);
    }

    @Test
    @DisplayName("given zukemon with 0 life points when fighting" +
            "zukemon starts with 5000 life points at the start of the fight")
    void fight() {
        Zukemon winner = defendFightMode.fight(createZeroLifePointsZukemon(), new Pikachu());

        assertThat(defendFightMode.numberOfSurvivedRounds).isEqualTo(37);
        assertThat(winner).isInstanceOf(Pikachu.class);
        assertThat(defendFightMode.getHighScore()).isEqualTo(135);
    }

    @Test
    void shouldInvokeObservers() {
        defendFightMode.addObserver(observer);

        defendFightMode.fight(createZeroLifePointsZukemon(), new Pikachu());

        verify(observer, times(38)).updateDamage(any(Zukemon.class), any(Zukemon.class), anyInt());
        verify(observer, times(1)).updateHighScore(any(Zukemon.class), anyInt());
        verify(observer, times(1)).updateGameEnd(anyString());
    }

    private Zukemon createZeroLifePointsZukemon() {
        return new Zukemon(0) {
            @Override
            public int hit() {
                return 0;
            }
        };
    }
}