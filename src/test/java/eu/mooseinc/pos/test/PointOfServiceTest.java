package eu.mooseinc.pos.test;

import eu.mooseinc.pos.PointOfServiceImpl;
import eu.mooseinc.pos.dao.ProductDao;
import eu.mooseinc.pos.exception.OutputDeviceException;
import eu.mooseinc.pos.input.PointOfService;
import eu.mooseinc.pos.model.Product;
import eu.mooseinc.pos.test.mock.LcdOutputDevice;
import eu.mooseinc.pos.test.mock.PrinterOutputDevice;
import eu.mooseinc.pos.test.mock.ProductDaoMock;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Jakub on 2015-07-23.
 * Tests PointOfService behavior
 */
public class PointOfServiceTest {
    private ProductDao productDao = new ProductDaoMock();

    @Test
    public void testProductNotFound() throws OutputDeviceException {
        LcdOutputDevice lcd = new LcdOutputDevice();
        PrinterOutputDevice printer = new PrinterOutputDevice();
        PointOfService pos = new PointOfServiceImpl(lcd, printer, productDao);
        pos.handleInput("cvd");
        assertEquals(lcd.getSuccessfullyPrintMessagesCount(), 1);
        assertEquals(pos.getProductsCount(), 0);
        assertEquals(pos.getTotalAmount(), 0);
    }

    @Test
    public void testInvalidBarcode() throws OutputDeviceException {
        LcdOutputDevice lcd = new LcdOutputDevice();
        PrinterOutputDevice printer = new PrinterOutputDevice();
        PointOfService pos = new PointOfServiceImpl(lcd, printer, productDao);
        pos.handleInput("");
        assertEquals(lcd.getSuccessfullyPrintMessagesCount(), 1);
        assertEquals(pos.getProductsCount(), 0);
        assertEquals(pos.getTotalAmount(), 0);
        pos.handleInput(null);
        assertEquals(lcd.getSuccessfullyPrintMessagesCount(), 2);
        assertEquals(pos.getProductsCount(), 0);
        assertEquals(pos.getTotalAmount(), 0);
    }

    @Test
    public void testOnlyValidProducts() throws OutputDeviceException {
        LcdOutputDevice lcd = new LcdOutputDevice();
        PrinterOutputDevice printer = new PrinterOutputDevice();
        PointOfService pos = new PointOfServiceImpl(lcd, printer, productDao);

        Product p1 = productDao.findByBarcode("xpr1");
        Product p2 = productDao.findByBarcode("xprod2");
        Product p3 = productDao.findByBarcode("xp3");

        assertNotNull(p1);
        assertNotNull(p2);
        assertNotNull(p3);
        assertEquals(p1.getPrice(), 200);
        assertEquals(p2.getPrice(), 300);
        assertEquals(p3.getPrice(), 150);

        pos.handleInput(p1.getBarcode());
        assertEquals(lcd.getSuccessfullyPrintMessagesCount(), 1);
        assertEquals(printer.getSuccessfullyPrintMessagesCount(), 0);
        assertEquals(pos.getProductsCount(), 1);
        assertEquals(pos.getTotalAmount(), p1.getPrice());

        pos.handleInput(p2.getBarcode());
        assertEquals(lcd.getSuccessfullyPrintMessagesCount(), 2);
        assertEquals(printer.getSuccessfullyPrintMessagesCount(), 0);
        assertEquals(pos.getProductsCount(), 2);
        assertEquals(pos.getTotalAmount(), p1.getPrice() + p2.getPrice());

        pos.handleInput(p3.getBarcode());
        assertEquals(lcd.getSuccessfullyPrintMessagesCount(), 3);
        assertEquals(printer.getSuccessfullyPrintMessagesCount(), 0);
        assertEquals(pos.getProductsCount(), 3);
        assertEquals(pos.getTotalAmount(), p1.getPrice() + p2.getPrice()+ p3.getPrice());

        pos.handleInput("exit");
        assertEquals(lcd.getSuccessfullyPrintMessagesCount(), 4);
        assertEquals(printer.getSuccessfullyPrintMessagesCount(), 5);
        assertEquals(pos.getProductsCount(), 0);
        assertEquals(pos.getTotalAmount(), 0);
    }

    @Test
    public void testValidAndInvalidProducts() throws OutputDeviceException {
        LcdOutputDevice lcd = new LcdOutputDevice();
        PrinterOutputDevice printer = new PrinterOutputDevice();
        PointOfService pos = new PointOfServiceImpl(lcd, printer, productDao);

        Product p1 = productDao.findByBarcode("xpr1");
        Product p2 = productDao.findByBarcode("xprod2");
        Product p3 = productDao.findByBarcode("xp3");

        assertNotNull(p1);
        assertNotNull(p2);
        assertNotNull(p3);
        assertEquals(p1.getPrice(), 200);
        assertEquals(p2.getPrice(), 300);
        assertEquals(p3.getPrice(), 150);

        pos.handleInput(p1.getBarcode());
        assertEquals(lcd.getSuccessfullyPrintMessagesCount(), 1);
        assertEquals(printer.getSuccessfullyPrintMessagesCount(), 0);
        assertEquals(pos.getProductsCount(), 1);
        assertEquals(pos.getTotalAmount(), p1.getPrice());

        pos.handleInput(null);
        assertEquals(lcd.getSuccessfullyPrintMessagesCount(), 2);
        assertEquals(printer.getSuccessfullyPrintMessagesCount(), 0);
        assertEquals(pos.getProductsCount(), 1);
        assertEquals(pos.getTotalAmount(), p1.getPrice());

        pos.handleInput(p2.getBarcode());
        assertEquals(lcd.getSuccessfullyPrintMessagesCount(), 3);
        assertEquals(printer.getSuccessfullyPrintMessagesCount(), 0);
        assertEquals(pos.getProductsCount(), 2);
        assertEquals(pos.getTotalAmount(), p1.getPrice() + p2.getPrice());

        pos.handleInput("");
        assertEquals(lcd.getSuccessfullyPrintMessagesCount(), 4);
        assertEquals(printer.getSuccessfullyPrintMessagesCount(), 0);
        assertEquals(pos.getProductsCount(), 2);
        assertEquals(pos.getTotalAmount(), p1.getPrice() + p2.getPrice());

        pos.handleInput(p3.getBarcode());
        assertEquals(lcd.getSuccessfullyPrintMessagesCount(), 5);
        assertEquals(printer.getSuccessfullyPrintMessagesCount(), 0);
        assertEquals(pos.getProductsCount(), 3);
        assertEquals(pos.getTotalAmount(), p1.getPrice() + p2.getPrice()+ p3.getPrice());

        pos.handleInput("ddd");
        assertEquals(lcd.getSuccessfullyPrintMessagesCount(), 6);
        assertEquals(printer.getSuccessfullyPrintMessagesCount(), 0);
        assertEquals(pos.getProductsCount(), 3);
        assertEquals(pos.getTotalAmount(), p1.getPrice() + p2.getPrice()+ p3.getPrice());

        pos.handleInput("exit");
        assertEquals(lcd.getSuccessfullyPrintMessagesCount(), 7);
        assertEquals(printer.getSuccessfullyPrintMessagesCount(), 5);
        assertEquals(pos.getProductsCount(), 0);
        assertEquals(pos.getTotalAmount(), 0);
    }
}
