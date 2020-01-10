package com.eis.communication.network;

/**
 * This interface has to be implemented by any classes
 * that want to be a command
 *
 * Created following command design pattern
 * @link https://refactoring.guru/design-patterns/command
 *
 * @author Edoardo Raimondi, idea by Marco Cognolato, Enrico Cestaro
 */
public interface CommandExecuter {

    /**
     * Execute the specific class command
     */
    void execute();
}
