package com.zukemon.refactor.fightmodes;

import com.zukemon.refactor.log.FightObserver;
import com.zukemon.refactor.zukemons.Blastoise;
import com.zukemon.refactor.zukemons.Pikachu;
import com.zukemon.refactor.zukemons.Zukemon;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoyalRumbleFightModeTest {
    @InjectMocks
    RoyalRumbleFightMode royalRumbleFightMode;

    @Mock
    FightObserver fightObserver;

    @Mock
    RoyalRumbleRandomSelector royalRumbleRandomSelector;

    @Test
    void shouldFight() {
        List<Zukemon> zukemons = new ArrayList<>();
        Zukemon defender = new Pikachu();
        Zukemon attacker = new Blastoise();
        zukemons.add(defender);
        zukemons.add(attacker);

        when(royalRumbleRandomSelector.getRandomFighterAndDefender(any(List.class))).thenReturn(new ChosenFighters(attacker, defender));

        Zukemon winner = royalRumbleFightMode.fight(zukemons);
        assertThat(winner).isInstanceOf(Blastoise.class);
    }

    @Test
    void shouldInvokeObservers() {
        royalRumbleFightMode.addObserver(fightObserver);

        List<Zukemon> zukemons = new ArrayList<>();
        Zukemon defender = new Pikachu();
        Zukemon attacker = new Blastoise();
        zukemons.add(defender);
        zukemons.add(attacker);

        when(royalRumbleRandomSelector.getRandomFighterAndDefender(any(List.class))).thenReturn(new ChosenFighters(attacker, defender));

        royalRumbleFightMode.fight(zukemons);

        verify(fightObserver, times(2)).updateDamage(any(Zukemon.class), any(Zukemon.class), anyInt());
        verify(fightObserver, times(1)).updateHighScore(any(Zukemon.class), anyInt());
        verify(fightObserver, times(1)).updateGameEnd(anyString());
    }

}