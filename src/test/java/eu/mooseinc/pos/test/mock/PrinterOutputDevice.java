package eu.mooseinc.pos.test.mock;

import eu.mooseinc.pos.exception.OutputDeviceException;
import eu.mooseinc.pos.output.Displayable;
import eu.mooseinc.pos.output.OutputDevice;

/**
 * Created by Jakub on 2015-07-23.
 * Printer mock
 */
public class PrinterOutputDevice implements OutputDevice {
    private int successfulMessagesCount = 0;

    @Override
    public void print(Displayable... messages) throws OutputDeviceException {
        if (messages == null) {
            throw new OutputDeviceException("No messages to print");
        }
        System.out.println("%%%%%% PRINTING STARTED: %%%%%%%");
        for (Displayable message : messages) {
            if (message.getAmount() == null) {
                System.out.println(message.getTile());
            } else {
                System.out.println(message.getTile() + " - " + message.getAmount());
            }
        }
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        successfulMessagesCount += messages.length;
    }

    @Override
    public int getSuccessfullyPrintMessagesCount() {
        return successfulMessagesCount;
    }
}
