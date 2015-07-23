package eu.mooseinc.pos.util;

import java.util.ResourceBundle;

/**
 * Created by Jakub on 2015-07-23.
 * Util methods
 */
public class PosUtils {
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    /**
     * Reads message from message bundle
     * @param key message key
     * @return localized message
     */
    public static String getMessage(String key) {
        return resourceBundle.getString(key);
    }

    /**
     * Converts amount in human readable form. For instance 125 will be transformed to "1.25 USD"
     * assuming that currency is USD
     * @param amount amount to convert
     * @return amount as String
     */
    public static String toHumanReadableAmount(long amount) {
        String value = ""+amount;
        if (amount < 100) {
            return value;
        }
        return value.substring(0, value.length()-2) + "."+value.substring(value.length()-2)+" "+
                getMessage("currency");
    }
}
