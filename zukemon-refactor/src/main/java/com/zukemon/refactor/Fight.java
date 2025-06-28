package com.zukemon.refactor;

import com.zukemon.refactor.zukemons.Zukemon;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Fight {

    ZukemonFactory zukemonFactory = new ZukemonFactory();

    //private final ArenaDisplay arenaDisplay = new ArenaDisplay();
    private int highScore = 0;

    /**
     * Blastoise #9 Water Damage 258
     * Mew #151 Psychic Damage 150 (10% chance of critical hit)
     * Wartortle #8 Water Damage 300
     * Mudkip #258 Water Damage 234
     * Pikachu #25 Electric Damage 135
     * Psyduck #54 Water Damage 127 (20% chance of critical hit)
     * Krookodile #553 Dark No Damage -> It is the team lead, so he can call his team members to arms. Add the
     * damage of all other Zukemons
     * <p>
     * Critical hits make double damage
     *
     * @param fightMode the type of fightMode
     */
    public Zukemon fight(FightMode fightMode) {

        return switch (fightMode) {
            case NORMAL -> executeNormalFight();
            case DEFEND -> executeDefendMode();
            case ROYAL_RUMBLE -> executeRoyalRumble();
        };
    }

    private Zukemon executeRoyalRumble() {
        List<Zukemon> fighters = IntStream.range(0, 5)
                .mapToObj(i -> zukemonFactory.createRandomZukemon())
                .collect(Collectors.toList());

        while (fighters.size() > 1) {
            ChosenFighters chosenFighters = getRandomFighterAndDefender(fighters);

            performAttackSequence(chosenFighters.attacker(), chosenFighters.defender());
            if (chosenFighters.defender().isDead()) {
                HistoryLogger.logRoyalRumbleLoseMessage(chosenFighters.defender());
                fighters.remove(chosenFighters.defender());
            }
        }
        return fighters.getFirst();
    }

    private Zukemon executeDefendMode() {
        Zukemon attacker = zukemonFactory.createRandomZukemon();
        Zukemon defender = zukemonFactory.createRandomZukemon();
        int initialLifePoints = defender.getLifePoints();
        // The defender gets super much life points
        defender.increaseLifePointsBy(5000);
        int numberOfSurvivedRounds = 0;
        while (true) {
            performAttackSequence(attacker, defender);
            if (defender.isDead()) {
                HistoryLogger.logRoundsSurvivedMessage(defender, numberOfSurvivedRounds);
                return attacker;
            }

            //heal 10% of initial lifepoints
            defender.increaseLifePointsBy(initialLifePoints / 100 * 10);
            numberOfSurvivedRounds++;
        }
    }

    private Zukemon executeNormalFight() {
        Zukemon attacker = zukemonFactory.createRandomZukemon();
        Zukemon defender = zukemonFactory.createRandomZukemon();
        while (true) {
            performAttackSequence(attacker, defender);
            if (defender.isDead()) {
                HistoryLogger.logDeadMessage(defender);
                return attacker;
            }

            performAttackSequence(defender, attacker);
            if (attacker.isDead()) {
                HistoryLogger.logDeadMessage(attacker);
                return defender;
            }
        }
    }

    private static ChosenFighters getRandomFighterAndDefender(List<Zukemon> fighters) {
        Zukemon attacker = fighters.get(new Random().nextInt(fighters.size()));
        Zukemon defender = fighters.get(new Random().nextInt(fighters.size()));
        while (attacker == defender) {
            defender = fighters.get(new Random().nextInt(fighters.size()));
        }
        return new ChosenFighters(attacker, defender);
    }

    private void performAttackSequence(Zukemon attacker, Zukemon defender) {
        int attackerDamage = attacker.hit();
        defender.reduceLifePointsBy(attackerDamage);

        ArenaDisplay.showDamage(attacker, attackerDamage);
        updateHighScore(attackerDamage, attacker);

        HistoryLogger.logDamage(attacker, attackerDamage, defender);
    }


    private void updateHighScore(int attackerDamage, Zukemon attacker) {
        if (attackerDamage > highScore) {
            highScore = attackerDamage;
            ArenaDisplay.showHighScore(attacker, highScore);
        }
    }

    private record ChosenFighters(Zukemon attacker, Zukemon defender) {
    }

    public int getHighScore() {
        return highScore;
    }

}
