package com.zukemon.refactor;

import com.zukemon.refactor.zukemons.Zukemon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class HistoryLogger {


    static File historyFile = new File("history.txt");

    static {
        try {
            new File("history.txt").createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void logDeadMessage(Zukemon killedZukemon) {
        String deadMessage = "Zukemon '" + killedZukemon.getClass().getSimpleName() + "' is dead looser";
        try {
            Files.write(historyFile.toPath(), deadMessage.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void logRoundsSurvivedMessage(Zukemon killedZukemon, int numberOfSurvivedRounds) {
        String deadMessage = "Zukemon '" + killedZukemon.getClass().getSimpleName() + "' has survived " + numberOfSurvivedRounds + " rounds.\r\n";
        try {
            Files.write(historyFile.toPath(), deadMessage.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void logRoyalRumbleLoseMessage(Zukemon killedZukemon) {
        String deadMessage = "Zukemon '" + killedZukemon.getClass().getSimpleName() + "' is out of the royal rumble.\r\n";
        try {
            Files.write(historyFile.toPath(), deadMessage.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    static void logDamage(Zukemon attacker, int attackerDamage, Zukemon defender) {
        String historyRecord = "Zukemon '" + attacker.getClass().getSimpleName() + "' made " + attackerDamage + " damage at '" + defender.getClass().getSimpleName() + "'\r\n";
        try {
            Files.write(historyFile.toPath(), historyRecord.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
