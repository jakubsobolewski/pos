package eu.mooseinc.pos.input;

/**
 * Created by Jakub on 2015-07-23.
 * POS interface
 */
public interface PointOfService extends InputHandler {
    /**
     * @return total amount of current products list
     */
    long getTotalAmount();

    /**
     * @return number of products in current products list
     */
    int getProductsCount();
}
