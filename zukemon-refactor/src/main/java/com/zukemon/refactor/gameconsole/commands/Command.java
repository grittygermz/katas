package com.zukemon.refactor.gameconsole.commands;

/*
invoker
  - the game console
concretecommand
  - select zukemon - choose a zukemon
  - select gamemode - choose a gamemode
  - play gamemode - returns winner
command
  - with method of execute? with a return of generic type
receiver
  - the select game mode function
  - the select zukemon mode function
  - play game function - use the factory to create the FightModeType and then call the fight method?
client - the sweaty kid? certain game console type? - the one that sets the commands on the invoker
 */

public interface Command<T, U> {
    T execute(U input);
    void prompt();
}
