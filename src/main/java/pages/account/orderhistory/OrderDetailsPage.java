package pages.account.orderhistory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.PageBase;

public class OrderDetailsPage extends PageBase {

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailsPage.class);

    public OrderDetailsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#order-history table tbody td:nth-child(1)")
    private WebElement date;

    @FindBy(css = "#order-products tfoot tr td:nth-child(2)")
    private WebElement totalPrice;

    @FindBy(css = "#order-history table tbody td:nth-child(2)")
    private WebElement paymentStatus;

    @FindBy(css = "#delivery-address address")
    private WebElement deliveryAddress;

    @FindBy(css = "#invoice-address address")
    private WebElement invoiceAddress;

    @FindBy(css = "#order-infos .row div")
    private WebElement orderReference;


    public String getDate() {
        return date.getText().trim();
    }

    public double getTotalPrice() {
        String text = totalPrice.getText().trim();
       return getPriceFromText(text);
    }

    public String getPaymentStatus(){
        return paymentStatus.getText().trim();
    }

    public String getDeliveryAddress(){
        return deliveryAddress.getText().trim();
    }

    public String getInvoiceAddress(){
        return invoiceAddress.getText().trim();
    }

    public String getOrderReference(){
        String text = orderReference.getText().trim();
        return text.substring(text.indexOf("Reference") + 9, text.indexOf("-")-1).trim();
    }
}

