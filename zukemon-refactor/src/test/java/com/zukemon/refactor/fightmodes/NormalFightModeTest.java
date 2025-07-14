package com.zukemon.refactor.fightmodes;

import com.zukemon.refactor.zukemons.Blastoise;
import com.zukemon.refactor.zukemons.Pikachu;
import com.zukemon.refactor.zukemons.Zukemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NormalFightModeTest {

    NormalFightMode normalFightMode;

    @BeforeEach
    void setUp() {
        normalFightMode = new NormalFightMode(null);
        //normalFightMode.fightObserverList = List.of(new ConsoleArenaDisplay());
    }

    @Test
    void shouldFight() {
        Zukemon winner = normalFightMode.fight(new Blastoise(), new Pikachu());
        assertThat(winner).isInstanceOf(Blastoise.class);
    }
}