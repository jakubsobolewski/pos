package eu.mooseinc.pos.output;

/**
 * Created by Jakub on 2015-07-23.
 * This is simple message to be displayed on output device.
 */
public class SimpleMessage implements Displayable {
    private String message;

    /**
     * @param message message text
     */
    public SimpleMessage(String message) {
        this.message = message;
    }

    @Override
    public String getTile() {
        return message;
    }

    /**
     * @return always null
     */
    @Override
    public String getAmount() {
        return null;
    }
}
