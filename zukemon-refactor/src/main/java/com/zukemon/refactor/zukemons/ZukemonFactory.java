package com.zukemon.refactor.zukemons;

import lombok.Data;

import java.util.List;
import java.util.Random;

@Data
public class ZukemonFactory {

    private final List<Integer> zukemonTypes = List.of(151, 8,9,258,25,54,553);

    public Zukemon createZukemon(int type) {
        return switch (type) {
            case 151 -> new Mew();
            case 8 -> new Wartortle();
            case 9 -> new Blastoise();
            case 258 -> new Mudkip();
            case 25 -> new Pikachu();
            case 54 -> new Psyduck();
            case 553 -> new Krookodile();
            default -> throw new IllegalArgumentException("No Zukemon for type " + type);
        };
    }

    public Zukemon createRandomZukemon() {
        //List<Integer> zukemonTypes = List.of(151, 8,9,258,25,54,553);
        Random rand = new Random();
        Integer zukemonType = zukemonTypes.get(rand.nextInt(zukemonTypes.size()));
        return createZukemon(zukemonType);
    }
}
