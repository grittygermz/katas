package com.zukemon.refactor.zukemons;


import java.util.List;

public class Krookodile extends Zukemon {

    public static final int NUMBER = 553;

    List<Zukemon> teamMembers = List.of(new Blastoise(), new Mew(), new Wartortle(), new Mudkip(), new Pikachu(), new Psyduck());

    public Krookodile() {
        super(900);
    }

    @Override
    public int hit() {
        int damage = 0;
        for (Zukemon zukemon: teamMembers) {
            damage += zukemon.hit();
        }
        return damage;
    }
}
