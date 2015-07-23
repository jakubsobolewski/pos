package eu.mooseinc.pos.exception;

/**
 * Created by Jakub on 2015-07-23.
 * Exception thrown when output device is not responding or printing message fails
 */
public class OutputDeviceException extends Exception {
    public OutputDeviceException(String message) {
        super(message);
    }
}
