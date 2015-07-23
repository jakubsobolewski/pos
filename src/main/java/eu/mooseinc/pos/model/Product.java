package eu.mooseinc.pos.model;

import eu.mooseinc.pos.output.Displayable;

/**
 * Created by Jakub on 2015-07-23.
 *
 * Product interface - all product should implement this interface
 */
public interface Product extends Displayable {
    /**
     * Returns products price in smallest possible currency <br/> (eg. cents for US dollars - 1$ = 100)
     * <br/>
     * I'm assuming USD currency for purpose of this sample
     *
     * @return products price in USD
     */
    long getPrice();

    /**
     * @return product barcode
     */
    String getBarcode();
}
