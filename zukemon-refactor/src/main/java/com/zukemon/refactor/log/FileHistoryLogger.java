package com.zukemon.refactor.log;

import com.zukemon.refactor.zukemons.Zukemon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/*
avoid static methods, fields and blocks because it makes testing tricky?
 */
public class FileHistoryLogger implements HistoryLogger {

    private final File historyFile;

    public FileHistoryLogger(String historyFileName) {
        try {
            historyFile = new File(historyFileName);
            historyFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //static File historyFile = new File("history.txt");
    //
    //static {
    //    try {
    //        new File("history.txt").createNewFile();
    //    } catch (IOException e) {
    //        throw new RuntimeException(e);
    //    }
    //}

    public void logDeadMessage(Zukemon killedZukemon) {
        String deadMessage = "Zukemon '" + killedZukemon.getClass().getSimpleName() + "' is dead looser";
        try {
            Files.write(historyFile.toPath(), deadMessage.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void logRoundsSurvivedMessage(Zukemon killedZukemon, int numberOfSurvivedRounds) {
        String deadMessage = "Zukemon '" + killedZukemon.getClass().getSimpleName() + "' has survived " + numberOfSurvivedRounds + " rounds.\r\n";
        try {
            Files.write(historyFile.toPath(), deadMessage.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void logRoyalRumbleLoseMessage(Zukemon killedZukemon) {
        String deadMessage = "Zukemon '" + killedZukemon.getClass().getSimpleName() + "' is out of the royal rumble.\r\n";
        try {
            Files.write(historyFile.toPath(), deadMessage.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void logDamage(Zukemon attacker, int attackerDamage, Zukemon defender) {
        String historyRecord = "Zukemon '" + attacker.getClass().getSimpleName() + "' made " + attackerDamage + " damage at '" + defender.getClass().getSimpleName() + "'\r\n";
        try {
            Files.write(historyFile.toPath(), historyRecord.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
