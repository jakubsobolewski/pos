package eu.mooseinc.pos.output;

/**
 * Created by Jakub on 2015-07-23.
 * Interface for objects that can be displayed in output devices
 */
public interface Displayable {
    /**
     * @return title to be displayed on device
     */
    String getTile();

    /**
     * @return amount to be displayed on device in human readable form. Null value is allowed for
     * simple messages like information or errors.
     */
    String getAmount();
}
