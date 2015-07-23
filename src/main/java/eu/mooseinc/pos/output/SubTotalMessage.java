package eu.mooseinc.pos.output;

import eu.mooseinc.pos.util.PosUtils;

/**
 * Created by Jakub on 2015-07-23.
 * Subtotal amount message
 */
public class SubTotalMessage implements Displayable {
    private long amount;

    /**
     * @param amount subtotal amount
     */
    public SubTotalMessage(long amount) {
        this.amount = amount;
    }

    @Override
    public String getTile() {
        return PosUtils.getMessage("subtotal");
    }

    @Override
    public String getAmount() {
        return PosUtils.toHumanReadableAmount(amount);
    }
}
