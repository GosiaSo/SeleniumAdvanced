package pages.account.orderhistory.orderhistorytable;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import java.util.List;

public class OrderHistoryRow {

    public OrderHistoryRow(WebElement item) {
        PageFactory.initElements(new DefaultElementLocatorFactory(item), this);
    }

    @FindBy(css = ".table tbody tr th")
    private WebElement orderReference;

    @FindBy(css = "td:nth-child(1)")
    private WebElement date;

    @FindBy(css = "td:nth-child(2)")
    private WebElement totalPrice;

    @FindBy(css = "td:nth-child(3)")
    private WebElement payment;

    @FindBy(css = "td:nth-child(4)")
    private WebElement status;

    @FindBy(css = "td.order-actions a[data-link-action='view-order-details']")
    private WebElement detailsButton;

    @FindBy(css = ".table tbody tr th")
    private List<WebElement> orderRefereneces;

    public String getOrderReference() {
        return orderReference.getText().trim();
    }

//    public String getOrderReference(Map<Integer, OrderHistoryRow> orders){
//        Integer key = orders.entrySet().iterator().next().getKey();
//        for (int i = 0; i < orderRefereneces.size(); i++) {
//            return orderRefereneces.get(key).getText().trim();
//        }
//        return StringUtils.EMPTY;
//    }

    public String getDate() {
        return date.getText().trim();
    }

    public double getTotalPrice() {
        String text = totalPrice.getText().trim();
        String substring = text.substring(text.indexOf("$") + 1).trim();
        return Double.parseDouble(substring);
    }

    public String getPayment() {
        return payment.getText().trim();
    }

    public String getStatus() {
        return status.getText().trim();
    }

    public WebElement getDetailsButton() {
        return detailsButton;
    }
}
