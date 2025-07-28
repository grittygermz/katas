package com.zukemon.refactor.gameconsole.receiver;

import com.zukemon.refactor.zukemons.Zukemon;
import com.zukemon.refactor.zukemons.ZukemonFactory;

public class ZukemonSelector {

    private final ZukemonFactory zukemonFactory;

    public ZukemonSelector() {
        this(new ZukemonFactory());
    }

    public ZukemonSelector(ZukemonFactory zukemonFactory) {
        this.zukemonFactory = zukemonFactory;
    }

    public void getAvailableZukemons() {
        System.out.println("available zukemons:");
        System.out.println(zukemonFactory.getZukemonTypes());
    }

    public Zukemon createZukemon(int zukemonType) {
        return zukemonFactory.createZukemon(zukemonType);
    }
}
