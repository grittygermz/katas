package com.zukemon.fight;

import com.zukemon.InvalidAttackerTypeException;
import com.zukemon.TeamManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Blastoise #9 Water Damage 258
 * Mew #151 Psychic Damage 150 (10% chance of critical hit)
 * Wartortle #8 Water Damage 300
 * Mudkip #258 Water Damage 234
 * Pikachu #25 Electric Damage 135
 * Psyduck #54 Water Damage 127 (20% chance of critical hit)
 * Krookodile #553 Dark No Damage -> It is the team lead, so he can call his team members to arms. Add the
 * damage of all other Zukemons
 *
 * Critical damage means the damage will be doubled
 **/
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class FightTest {

    @Autowired
    Fight fight;

    @Autowired
    TeamManager teamManager;

    @BeforeEach
    void setUp() {
        teamManager.getTeamMembers().clear();
    }


    @RepeatedTest(value = 10, name = "Run {currentRepetition} of {totalRepetitions}")
    public void shouldGiveMewDamage() {
        int damage = fight.hit(151);
        assertThat(damage).isIn(150, 300);
    }

    @RepeatedTest(value = 10, name = "Run {currentRepetition} of {totalRepetitions}")
    public void shouldGivePsyduckDamage() {
        int damage = fight.hit(54);
        assertThat(damage).isIn(127, 254);
    }

    @ParameterizedTest
    @CsvSource({
            "9, 258",
            "8, 300",
            "258, 234",
            "25, 135"
    })
    void shouldGiveNonCriticalDamage(int attackerType, int expectedDamage) {
        int damage = fight.hit(attackerType);
        assertThat(damage).isEqualTo(expectedDamage);
    }

    @Test
    public void shouldGiveZeroKrookodileDamage() {
        int damage = fight.hit(553);
        assertThat(damage).isEqualTo(0);
    }

    @Test
    public void shouldGiveKrookodileDamage() {
        fight.hit(8);
        fight.hit(8);
        fight.hit(258);
        fight.hit(553);

        int damage = fight.hit(553);
        assertThat(damage).isEqualTo(834);
    }

    @Test
    public void shouldThrowInvalidAttackerTypeException() {
        int attackerType = 0;
        assertThatThrownBy(() -> fight.hit(attackerType))
                .isInstanceOf(InvalidAttackerTypeException.class)
                .hasMessageContaining("attackerType of %d does not exists".formatted(attackerType));
    }


}
