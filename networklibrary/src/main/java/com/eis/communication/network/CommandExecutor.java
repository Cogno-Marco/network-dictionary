package com.eis.communication.network;

/**
 * Class that manage all the sent commands.
 * It just lets them executing.
 *
 * @link https://refactoring.guru/design-patterns/command
 *
 * @author Edoardo Raimondi, idea by Marco Cognolato, Enrico cestaro
 */
public class CommandExecutor{

    /**
     * @param command to be performed
     */
    public void execute(Command command){ command.execute(); }
}
