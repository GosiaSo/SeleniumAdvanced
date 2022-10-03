package pages.cart;

import models.User;
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

    @FindBy(css = "#invoice-addresses article")
    private WebElement invoiceAddressesSection;

    @FindBy(css = ".js-address-form .clearfix button")
    private WebElement buttonContinue;

    public void clickBillingAddressDiffersFromShippingAddressButton() {
        click(billingAddressDiffersFromShippingAddressButton);
    }

    //TODO ustawić tu ten address usera!!!!!!!!!!!!!!
    public void fillBillingForm(User user) {
        if(!checkIfInvoiceAddressAlreadyExists()){
            clickBillingAddressDiffersFromShippingAddressButton();
            if(!checkIfInvoiceAddressAlreadyExists()) {
                waitToBeVisible(adressInput);
                fillAddress("ul. sezamkowa");
                fillCity("Warszawka");
                selectRandomState();
                fillPostcode("00000");
                submitAddressForm();
            }else{
                click(buttonContinue);
            }
        }
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

    public boolean checkIfInvoiceAddressAlreadyExists(){
        if(isElementPresent(invoiceAddressesSection)){
            click(invoiceAddressesSection);
            return true;
        }
        return false;
    }
}
