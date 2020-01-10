package com.eis.communication.network;

/**
 * Class that manage all the sent commands.
 * It just let them executing.
 *
 * @param <C> command type
 *
 * @author Edoardo Raimondi, idea by Marco Cognolato, Enrico cestaro
 */
public class CommandExecutor<C extends Command>{

    /**
     * @param command to be performed
     */
    public void execute(C command){ command.execute(); }
}
