package pages.account;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.PageBase;

public class AccountPage extends PageBase {

    private static final Logger logger = LoggerFactory.getLogger(AccountPage.class);

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "history-link")
    private WebElement orderHistory;

    public void goToOrderHistoryPage() {
        logger.info("Going to order history page");
        click(orderHistory);
    }
}
