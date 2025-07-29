package com.zukemon.refactor.gameconsole.receiver;

import com.zukemon.refactor.equipment.EquipmentDecorator;
import com.zukemon.refactor.zukemons.Pikachu;
import com.zukemon.refactor.zukemons.Zukemon;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class EquipmentSelectorTest {

    @Test
    void shouldFindClasses() throws IOException, ClassNotFoundException {
        List<Class<?>> subclasses = new EquipmentSelector().findSubclasses("com.zukemon.refactor.equipment", EquipmentDecorator.class);
        System.out.println(subclasses);
    }

    @Test
    void shouldEquipZukemon() throws IOException, ClassNotFoundException {
        Zukemon pikachu = new Pikachu();
        System.out.println(pikachu.equipped());

        pikachu = new EquipmentSelector().equip(pikachu, "helmet");
        System.out.println(pikachu.equipped());

        pikachu = new EquipmentSelector().equip(pikachu, "helmet");
        System.out.println(pikachu.equipped());
    }

}