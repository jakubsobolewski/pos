package eu.mooseinc.pos;

import eu.mooseinc.pos.dao.ProductDao;
import eu.mooseinc.pos.exception.OutputDeviceException;
import eu.mooseinc.pos.input.PointOfService;
import eu.mooseinc.pos.model.Product;
import eu.mooseinc.pos.output.Displayable;
import eu.mooseinc.pos.output.OutputDevice;
import eu.mooseinc.pos.output.SimpleMessage;
import eu.mooseinc.pos.output.SubTotalMessage;
import eu.mooseinc.pos.util.PosUtils;

import java.util.ArrayList;

/**
 * Created by Jakub on 2015-07-23.
 * Single Point Of Service with LCD display and receipt printer
 */
public class PointOfServiceImpl implements PointOfService {
    private ArrayList<Displayable> storedMessages = new ArrayList<>();
    private long total = 0;

    /**
     * In standard environment dependency injection should be used (Guice, Spring, other)
     * In this sample I will use constructor to pass output devices and DAO to this POS
     */
    private OutputDevice lcdDisplay;
    private OutputDevice printer;
    private ProductDao productDao;

    /**
     * Creates new POS instance and attaches output devices
     *
     * @param lcdDisplay LCD
     * @param printer    Receipt printer
     * @param productDao DAO object fetching products from database
     */
    public PointOfServiceImpl(OutputDevice lcdDisplay, OutputDevice printer, ProductDao productDao) {
        this.lcdDisplay = lcdDisplay;
        this.printer = printer;
        this.productDao = productDao;
    }

    @Override
    public void handleInput(String inputText) throws OutputDeviceException {
        if (inputText == null || inputText.isEmpty()) {
            displayInvalidBarcodeMessage();
        } else if ("exit".equals(inputText)) {
            finalizeOrder();
        } else {
            Product product = productDao.findByBarcode(inputText);
            if (product == null) {
                displayProductNotFoundMessage();
            } else {
                addProduct(product);
            }
        }
    }

    /**
     * Adds product to current product list and prints it on LCD
     *
     * @param product product to add
     * @throws eu.mooseinc.pos.exception.OutputDeviceException when output error occurred
     */
    private void addProduct(Product product) throws OutputDeviceException {
        lcdDisplay.print(product);
        storedMessages.add(product);
        total += product.getPrice();
    }

    /**
     * Displays error message on LCD - product not found
     * @throws eu.mooseinc.pos.exception.OutputDeviceException when output error occurred
     */
    private void displayProductNotFoundMessage() throws OutputDeviceException {
        lcdDisplay.print(new SimpleMessage(PosUtils.getMessage("err_product_not_found")));
    }

    /**
     * Displays error message on LCD - invalid barcode
     * @throws eu.mooseinc.pos.exception.OutputDeviceException when output error occurred
     */
    private void displayInvalidBarcodeMessage() throws OutputDeviceException {
        lcdDisplay.print(new SimpleMessage(PosUtils.getMessage("err_invalid_barcode")));
    }

    /**
     * Prints receipt, displays subtotal on LCD and clears current product list - POS is ready for another order
     * @throws eu.mooseinc.pos.exception.OutputDeviceException when output error occurred
     */
    private void finalizeOrder() throws OutputDeviceException {
        SubTotalMessage subTotalMessage = new SubTotalMessage(total);
        SimpleMessage dashMessage = new SimpleMessage("-------------------------");
        lcdDisplay.print(subTotalMessage);
        Displayable[] messages = storedMessages.toArray(new Displayable[storedMessages.size() + 2]);
        messages[messages.length-2] = dashMessage;
        messages[messages.length-1] = subTotalMessage;
        printer.print(messages);
        total = 0;
        storedMessages.clear();
    }

    @Override
    public long getTotalAmount() {
        return total;
    }

    @Override
    public int getProductsCount() {
        return storedMessages.size();
    }
}