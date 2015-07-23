package eu.mooseinc.pos.test.mock;

import eu.mooseinc.pos.dao.ProductDao;
import eu.mooseinc.pos.model.Product;

/**
 * Created by Jakub on 2015-07-23.
 * Product DAO mock - finds products with barcode starting with "x"
 */
public class ProductDaoMock implements ProductDao {
    @Override
    public Product findByBarcode(String barcode) {
        if (barcode == null) {
            return null;
        }
        if (barcode.startsWith("x")) {
            return new SimpleProduct(barcode, barcode + " title", 50 * barcode.length());
        }
        return null;
    }
}
