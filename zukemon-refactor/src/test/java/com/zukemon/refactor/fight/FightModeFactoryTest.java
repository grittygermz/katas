package com.zukemon.refactor.fight;

import com.zukemon.refactor.fightmodes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FightModeFactoryTest {

    FightModeFactory fightModeFactory;

    @BeforeEach
    void setUp() {
        fightModeFactory = new FightModeFactory();
    }

    @Test
    void shouldCreateDefendFight() {
        FightMode fight = fightModeFactory.createFight(FightModeType.DEFEND, null);
        assertThat(fight).isInstanceOf(DefendFightMode.class);
    }

    @Test
    void shouldCreateNormalFight() {
        FightMode fight = fightModeFactory.createFight(FightModeType.NORMAL, null);
        assertThat(fight).isInstanceOf(NormalFightMode.class);
    }

    @Test
    void shouldCreateRoyalRumbleFight() {
        FightMode fight = fightModeFactory.createFight(FightModeType.ROYAL_RUMBLE, null);
        assertThat(fight).isInstanceOf(RoyalRumbleFightMode.class);
    }

}