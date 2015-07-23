package eu.mooseinc.pos.test.mock;

import eu.mooseinc.pos.exception.OutputDeviceException;
import eu.mooseinc.pos.output.Displayable;
import eu.mooseinc.pos.output.OutputDevice;

/**
 * Created by Jakub on 2015-07-23.
 * Mock LCD panel
 */
public class LcdOutputDevice implements OutputDevice {
    private int successfulCount = 0;
    @Override
    public void print(Displayable... messages) throws OutputDeviceException{
        if (messages == null || messages.length != 1) {
            throw new OutputDeviceException("LCD can't handle empty messages or more than one message at a time");
        }
        processMessage(messages[0]);
    }

    @Override
    public int getSuccessfullyPrintMessagesCount() {
        return successfulCount;
    }

    private void processMessage(Displayable message) {
        System.out.print("************* LCD display: ");
        if (message.getAmount() == null) {
            System.out.println(message.getTile());
        }
        else {
            System.out.println(message.getTile()+" - "+message.getAmount());
        }
        successfulCount++;
    }
}
