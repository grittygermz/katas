package com.zukemon.refactor.zukemons;

public abstract class Zukemon {
    private int lifePoints = 0;

    protected Zukemon(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public abstract int hit();

    public void reduceLifePointsBy(int lifePointsToReduce) {
        this.lifePoints = Math.max(0, lifePoints - lifePointsToReduce);
    }

    public void increaseLifePointsBy(int lifePointsToIncrease) {
        this.lifePoints += lifePointsToIncrease;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public boolean isDead() {
        return lifePoints == 0;
    }
}
