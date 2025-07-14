package com.zukemon.refactor.log;

import com.zukemon.refactor.zukemons.Zukemon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class FileHistoryLogger implements FightObserver {

    private final File historyFile;

    public FileHistoryLogger(String historyFileName) {
        try {
            historyFile = new File(historyFileName);
            historyFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateDamage(Zukemon attacker, Zukemon defender, int damage) {
        String historyRecord = "Zukemon '" + attacker.getClass().getSimpleName() + "' made " + damage + " damage at '" + defender.getClass().getSimpleName() + "'\r\n";
        writeMessage(historyRecord);
    }

    @Override
    public void updateGameEnd(String gameEndMessage) {
        writeMessage(gameEndMessage);
    }

    private void writeMessage(String message) {
        try {
            Files.write(historyFile.toPath(), message.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void updateHighScore(Zukemon zukemon, int highScore) {
    }

}
