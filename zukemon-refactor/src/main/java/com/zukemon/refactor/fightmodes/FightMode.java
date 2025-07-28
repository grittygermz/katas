package com.zukemon.refactor.fightmodes;

import com.zukemon.refactor.log.FightObserver;
import com.zukemon.refactor.zukemons.Zukemon;
import com.zukemon.refactor.zukemons.ZukemonFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public abstract class FightMode {

    protected final ZukemonFactory zukemonFactory;
    private int highScore = 0;

    List<FightObserver> fightObserverList = new ArrayList<>();

    protected void performAttackSequence(Zukemon attacker, Zukemon defender) {
        int attackerDamage = attacker.hit();
        defender.reduceLifePointsBy(attackerDamage);

        updateObserversWithDamage(attacker, defender, attackerDamage);
        updateHighScore(attackerDamage, attacker);

    }

    private void updateHighScore(int attackerDamage, Zukemon attacker) {
        if (attackerDamage > highScore) {
            highScore = attackerDamage;
            updateObserversWithHighScore(attacker, highScore);
        }
    }

    public int getHighScore() {
        return highScore;
    }

    public void addObserver(FightObserver observer) {
        this.fightObserverList.add(observer);
    }

    private void updateObserversWithDamage(Zukemon attacker, Zukemon defender, int damage) {
        this.fightObserverList.forEach(o -> o.updateDamage(attacker, defender, damage));
    }

    private void updateObserversWithHighScore(Zukemon attacker, int highScore) {
        this.fightObserverList.forEach(o -> o.updateHighScore(attacker, highScore));
    }

    protected void updateObserversWithGameEnd(String gameEndMessage) {
        this.fightObserverList.forEach(o -> o.updateGameEnd(gameEndMessage));
    }

    public abstract Zukemon fight();

    public abstract Zukemon fight(List<Zukemon> zukemons);
}
