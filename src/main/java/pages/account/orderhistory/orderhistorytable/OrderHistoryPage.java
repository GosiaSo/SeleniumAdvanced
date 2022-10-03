package pages.account.orderhistory.orderhistorytable;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.PageBase;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryPage extends PageBase {

    private static final Logger logger = LoggerFactory.getLogger(OrderHistoryPage.class);

    public OrderHistoryPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#_desktop_logo")
    private WebElement homeLogoButton;

    @FindBy(css = ".table tbody tr")
    private List<WebElement> allRowsOrderHistory;

    public List<OrderHistoryRow> getAllOrderHistoryRows() {
        List<OrderHistoryRow> allOrderHistoryRows = new ArrayList<>();

        for (WebElement item : allRowsOrderHistory) {
            allOrderHistoryRows.add(new OrderHistoryRow(item));
        }
        return allOrderHistoryRows;
    }

    public void goToOrderHistoryDetailPage(OrderHistoryRow order) {
        logger.info("Going to order detail page. Order reference: " + order.getOrderReference());
        click(order.getDetailsButton());
    }
}
