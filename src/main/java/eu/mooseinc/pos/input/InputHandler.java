package eu.mooseinc.pos.input;

import eu.mooseinc.pos.exception.OutputDeviceException;

/**
 * Created by Jakub on 2015-07-23.
 * Interface for classes handling data from input devices
 */
public interface InputHandler {
    /**
     * Handles input text sent by input device
     *
     * @param inputText input text sent by input device
     * @throws eu.mooseinc.pos.exception.OutputDeviceException when output error occurred
     */
    void handleInput(String inputText) throws OutputDeviceException;
}
