package com.eis.communication.network;

import com.eis.communication.network.Commands.Command;

/**
 * Class that manage all the sent commands.
 * It just lets them executing.
 *
 * @link https://refactoring.guru/design-patterns/command
 * @param <C> command type
 *
 * @author Edoardo Raimondi, idea by Marco Cognolato, Enrico cestaro
 */
public class CommandExecutor <C extends Command>{

    /**
     * @param command to be performed
     */
    public void execute(C command){ command.execute(); }
}
