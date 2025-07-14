package com.zukemon.refactor.fightmodes;

import com.zukemon.refactor.zukemons.Blastoise;
import com.zukemon.refactor.zukemons.Pikachu;
import com.zukemon.refactor.zukemons.Zukemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

//@ExtendWith(MockitoExtension.class)
class DefendFightModeTest {

    //@InjectMocks
    DefendFightMode defendFightMode;

    @BeforeEach
    void setUp() {
        defendFightMode = new DefendFightMode(null);
        //defendFightMode.fightObserverList = List.of(new ConsoleArenaDisplay());
    }

    @Test
    void shouldFight() {
        Zukemon winner = defendFightMode.fight(new Blastoise(), new Pikachu());
        assertThat(defendFightMode.numberOfSurvivedRounds).isEqualTo(73);
        assertThat(winner).isInstanceOf(Pikachu.class);
        assertThat(defendFightMode.getHighScore()).isEqualTo(135);
    }


}