package com.zukemon.refactor.gameconsole;

import com.zukemon.refactor.zukemons.Zukemon;

import java.util.Scanner;

public class SweatyKid {

    public static void main(String[] args) {
        new SweatyKid().playNonInteractive();
    }

    public void playNonInteractive() {
        GameConsole simpleGameConsole = new SimpleGameConsole();
        simpleGameConsole.init();

        System.out.println("===== <select zukemon> button prompt =====");
        simpleGameConsole.runButtonPrompt("select zukemon");
        simpleGameConsole.runButtonCommand("select zukemon", 151);
        simpleGameConsole.runButtonCommand("select zukemon", 25);
        System.out.println("==========");

        System.out.println("===== <select fightmode> button prompt =====");
        simpleGameConsole.runButtonPrompt("select fightmode");
        simpleGameConsole.runButtonCommand("select fightmode", 0);
        System.out.println("==========");

        System.out.println("===== <equip zukemon> button prompt =====");
        simpleGameConsole.runButtonPrompt("equip zukemon");
        simpleGameConsole.runButtonCommand("equip zukemon", "helmet");
        System.out.println("==========");

        System.out.println("===== <fight!> button prompt =====");
        simpleGameConsole.runButtonPrompt("fight!");
        Zukemon winner = (Zukemon) simpleGameConsole.runButtonCommand("fight!", null);
        System.out.println("==========");

        System.out.printf("the winner is %s", winner.toString());
    }

    public void playInteractive() {
        GameConsole simpleGameConsole = new SimpleGameConsole();
        simpleGameConsole.init();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1-prompt 2-execute");
            int commandChoice = scanner.nextInt();
            System.out.println("1-select zukemon 2-select fightmode 3-fight!");
            int buttonChoice = scanner.nextInt();

            String nameOfbuttonChoice = switch (buttonChoice) {
                case 1 -> "select zukemon";
                case 2 -> "select fightmode";
                case 3 -> "fight!";
                default -> throw new IllegalStateException("Unexpected value: " + buttonChoice);
            };

            if (commandChoice == 1) {
                simpleGameConsole.runButtonPrompt(nameOfbuttonChoice);
            } else {
                switch (buttonChoice) {
                    case 1:
                        System.out.println("choose a zukemonType");
                        int zukemonType = scanner.nextInt();
                       simpleGameConsole.runButtonCommand(nameOfbuttonChoice, zukemonType);
                        break;
                    case 2:
                        System.out.println("choose a fightMode");
                        int fightMode = scanner.nextInt();
                        simpleGameConsole.runButtonCommand("select fightmode", fightMode);
                        break;
                    case 3:
                        Zukemon winner = (Zukemon) simpleGameConsole.runButtonCommand("fight!", null);
                        System.out.printf("the winner is %s\n", winner.toString());
                        break;
                }
            }
        }
    }
}
