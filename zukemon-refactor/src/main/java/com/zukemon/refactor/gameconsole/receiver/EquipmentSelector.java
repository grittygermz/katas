package com.zukemon.refactor.gameconsole.receiver;

import com.zukemon.refactor.equipment.EquipmentDecorator;
import com.zukemon.refactor.zukemons.Zukemon;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class EquipmentSelector {

    private List<Class<?>> equipments;

    public EquipmentSelector() throws IOException, ClassNotFoundException {
        equipments = findSubclasses("com.zukemon.refactor.equipment", EquipmentDecorator.class);
    }

    public Zukemon equip(Zukemon zukemon, String equipment) {
        Optional<EquipmentDecorator> first = equipments.stream().filter(e -> e.getSimpleName().equalsIgnoreCase(equipment)).map(aClass -> {
            try {
                return (EquipmentDecorator) aClass.getDeclaredConstructor(Zukemon.class).newInstance(zukemon);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }).findFirst();
        if(first.isEmpty()) {
            throw new RuntimeException("equipment: %s doesnt exists".formatted(equipment));
        }
        return first.get();
    }

    /*
    to reproduce spring like behaviour
     */
    List<Class<?>> findSubclasses(String packageName, Class<?> baseClass) throws ClassNotFoundException {
        List<Class<?>> subclasses = new ArrayList<>();
        String path = packageName.replace('.', '/');

        //TODO why running in tests vs running in main will give different absolute paths?
        File directory = new File("target/classes/" + path);
        // for main
        //File directory = new File("zukemon-refactor/target/classes/" + path);
        System.out.println(directory.getAbsolutePath());

        for (File file : directory.listFiles()) {
            if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().replace(".class", "");
                Class<?> cls = Class.forName(className);
                if (baseClass.isAssignableFrom(cls) && !cls.equals(baseClass)) {
                    subclasses.add(cls);
                }
            }
        }
        return subclasses;
    }
}
