package com.zukemon.fight;

import java.util.Random;

public class CriticalHit {
    public boolean isCriticalHit(int percentage) {
        double random = new Random().nextDouble();
        return random < ((double)percentage / 100);
    }
}
