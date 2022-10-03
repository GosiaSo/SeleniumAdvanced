package pages.signin;

import models.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.PageBase;

public class LoginPage extends PageBase {
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".no-account a")
    private WebElement noAccountButton;

    @FindBy(css = "input[name='email']")
    private WebElement emailInput;

    @FindBy(css = "input[name='password']")
    private WebElement passwordInput;

    @FindBy(css = "#submit-login")
    private WebElement signInButton;

    @FindBy(css = "#submit-login")
    private WebElement submitButton;

    public void loginAlreadyCreatedUser(User user) {
        sendKeys(emailInput, user.getEmail());
        sendKeys(passwordInput, user.getPassword());
        logger.info("Logging in as a goha@sii.pl");
        click(submitButton);
    }

    public void goToCreateNewAccountPage() {
        logger.info("Going to sign in page");
        click(noAccountButton);
    }
}
