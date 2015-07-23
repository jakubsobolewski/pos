package eu.mooseinc.pos.dao;

import eu.mooseinc.pos.model.Product;

/**
 * Created by Jakub on 2015-07-23.
 * DAO object fetching products from database
 */
public interface ProductDao {
    /**
     * Searches database for product with specified barcode
     *
     * @param barcode barcode to look for
     * @return found product or null if product doesn't exist in database
     */
    Product findByBarcode(String barcode);
}
