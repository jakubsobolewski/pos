package eu.mooseinc.pos.output;

import eu.mooseinc.pos.exception.OutputDeviceException;

/**
 * Created by Jakub on 2015-07-23.
 * Interface of output device capable of printing Displayable messages
 */
public interface OutputDevice {
    /**
     * Prints collection of messages
     *
     * @param messages messages to print
     * @throws eu.mooseinc.pos.exception.OutputDeviceException when printing error occurred
     */
    void print(Displayable... messages) throws OutputDeviceException;

    /**
     * For testing purposes only
     *
     * @return count of messages that had been printed successfully
     */
    int getSuccessfullyPrintMessagesCount();
}
