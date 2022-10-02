package pages.cart;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.PageBase;

import java.util.List;

public class BillingAddressPage extends PageBase {

    public BillingAddressPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "a[data-link-action='different-invoice-address']")
    private WebElement billingAddressDiffersFromShippingAddressButton;

    @FindBy(css = "input[name='address1']")
    private WebElement adressInput;

    @FindBy(css = "input[name='city']")
    private WebElement cityInput;

    @FindBy(css = "select[name='id_state']")
    private WebElement stateDropdown;

    @FindBy(css = "select[name='id_state'] option")
    private List<WebElement> stateOptions;

    @FindBy(css = "input[name='postcode']")
    private WebElement postcodeInput;

    @FindBy(css = "button[name='confirm-addresses']")
    private WebElement submitButton;

    @FindBy(css = "button[name='confirmDeliveryOption']")
    private WebElement confirmDeliveryOptionButton;

    @FindBy(css = "#payment-option-1")
    private WebElement payByCheckOption;

    @FindBy(id = "conditions_to_approve[terms-and-conditions]")
    private WebElement consentTermsAndConditions;

    @FindBy(css = "#payment-confirmation button")
    private WebElement placeOrderSubmitButton;

//    @FindBy(css = ".js-address-form")
//    private WebElement invoiceAddressesSection;


    public void clickBillingAddressDiffersFromShippingAddressButton() {
        click(billingAddressDiffersFromShippingAddressButton);
    }

    public void fillBillingForm() {
        clickBillingAddressDiffersFromShippingAddressButton();
        waitToBeVisible(adressInput);
//        waitToBeVisible(invoiceAddressesSection);
        fillAddress("ul. sezamkowa");
        fillCity("Warszawka");
        selectRandomState();
        fillPostcode("00000");
        submitAddressForm();
    }

    public void fillAddress(String address) {
        sendKeys(adressInput, address);
    }

    public void fillCity(String city) {
        sendKeys(cityInput, city);
    }

    public void selectRandomState() {
        click(stateDropdown);
        click(getRandomElement(stateOptions));
    }

    public void fillPostcode(String postcode) {
        sendKeys(postcodeInput, postcode);
    }

    public void submitAddressForm() {
        click(submitButton);
    }

    public void confirmDeliveryOption() {
        click(confirmDeliveryOptionButton);
    }

    public void selectPayByCheckOption() {
        click(payByCheckOption);
    }

    public void acceptTermsAndConditions() {
        click(consentTermsAndConditions);
    }

    public void placeOrder() {
        click(placeOrderSubmitButton);
    }
}
