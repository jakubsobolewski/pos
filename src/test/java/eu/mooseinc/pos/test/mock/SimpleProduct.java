package eu.mooseinc.pos.test.mock;

import eu.mooseinc.pos.model.Product;
import eu.mooseinc.pos.util.PosUtils;

/**
 * Created by Jakub on 2015-07-23.
 * Simple product mock - this should be some kind of entity (Hibernate, JPA, JOOQ, any other)
 */
public class SimpleProduct implements Product {
    private String barcode;
    private String title;
    private long price;

    /**
     * Creates new product with name and price
     * @param barcode product barcode
     * @param title product title
     * @param price product price
     */
    public SimpleProduct(String barcode, String title, long price) {
        this.barcode = barcode;
        this.title = title;
        this.price = price;
    }

    @Override
    public long getPrice() {
        return price;
    }

    @Override
    public String getTile() {
        return title;
    }

    @Override
    public String getAmount() {
        return PosUtils.toHumanReadableAmount(price);
    }

    @Override
    public String getBarcode() {
        return barcode;
    }
}
