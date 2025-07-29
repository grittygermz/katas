package com.zukemon.refactor.equipment;

import com.zukemon.refactor.zukemons.Pikachu;
import com.zukemon.refactor.zukemons.Zukemon;
import org.junit.jupiter.api.Test;

class EquipmentTest {

    @Test
    void shouldHaveBonusDamage() {
        Zukemon pikachu = new Pikachu();
        System.out.println(pikachu.hit());

        pikachu = new Sword(pikachu);
        pikachu = new Helmet(pikachu);

        System.out.println(pikachu.equipped());
        System.out.println(pikachu.hit());
    }

}