package com.zukemon.refactor;

import com.zukemon.refactor.zukemons.*;

import java.util.List;
import java.util.Random;

public class ZukemonFactory {

    public Zukemon createZukemon(int type) {
        switch (type) {
            case 151:
                return new Mew();
            case 8:
                return new Wartortle();
            case 9:
                return new Blastoise();
            case 258:
                return new Mudkip();
            case 25:
                return new Pikachu();
            case 54:
                return new Psyduck();
            case 553:
                return new Krookodile();
        }
        throw new IllegalArgumentException("No Zukemon for type " + type);
    }

    public Zukemon createRandomZukemon() {
        List<Integer> zukemonTypes = List.of(151, 8,9,258,25,54,553);
        Random rand = new Random();
        Integer zukemonType = zukemonTypes.get(rand.nextInt(zukemonTypes.size()));
        return createZukemon(zukemonType);
    }

}
