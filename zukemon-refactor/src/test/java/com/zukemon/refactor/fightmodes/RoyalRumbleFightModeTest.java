package com.zukemon.refactor.fightmodes;

import com.zukemon.refactor.zukemons.Blastoise;
import com.zukemon.refactor.zukemons.Pikachu;
import com.zukemon.refactor.zukemons.Zukemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RoyalRumbleFightModeTest {
    RoyalRumbleFightMode royalRumbleFightMode;

    @BeforeEach
    void setUp() {
        royalRumbleFightMode = new RoyalRumbleFightMode(null);
        //royalRumbleFightMode.fightObserverList = List.of(new ConsoleArenaDisplay());
    }

    @Test
    void shouldFight() {
        List<Zukemon> zukemons = new ArrayList<>();
        zukemons.add(new Blastoise());
        zukemons.add(new Pikachu());

        Zukemon winner = royalRumbleFightMode.fight(zukemons);
        assertThat(winner).isInstanceOf(Blastoise.class);
    }

}