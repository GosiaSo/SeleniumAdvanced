package models;

import java.util.List;
import java.util.Random;

public class User {

    private SocialTitle socialTitle;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String birthdate;
    private boolean receiveOffersConsent;
    private boolean customerDataPrivacyConsent;
    private boolean newsletterConsent;
    private boolean generalConditionsConsent;
    private String address;
    private String city;
    private String postalCode;
    private String deliveryAddress;

    public SocialTitle getSocialTitle() {
        return socialTitle;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public boolean isReceiveOffersConsent() {
        return receiveOffersConsent;
    }

    public boolean isCustomerDataPrivacyConsent() {
        return customerDataPrivacyConsent;
    }

    public boolean isNewsletterConsent() {
        return newsletterConsent;
    }

    public boolean isGeneralConditionsConsent() {
        return generalConditionsConsent;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public User(SocialTitle socialTitle, String firstName, String lastName, String email, String password, String birthdate, boolean receiveOffersConsent, boolean customerDataPrivacyConsent, boolean newsletterConsent, boolean generalConditionsConsent, String address, String city, String postalCode, String deliveryAddress) {
        this.socialTitle = socialTitle;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.receiveOffersConsent = receiveOffersConsent;
        this.customerDataPrivacyConsent = customerDataPrivacyConsent;
        this.newsletterConsent = newsletterConsent;
        this.generalConditionsConsent = generalConditionsConsent;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.deliveryAddress = deliveryAddress;
    }

    @Override
    public String toString() {
        return "User{" +
                "socialTitle=" + socialTitle +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", receiveOffersConsent=" + receiveOffersConsent +
                ", customerDataPrivacyConsent=" + customerDataPrivacyConsent +
                ", newsletterConsent=" + newsletterConsent +
                ", generalConditionsConsent=" + generalConditionsConsent +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                '}';
    }

    enum SocialTitle {
        MR, MRS;

        private static final List<SocialTitle> VALUES =
                List.of(values());
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        public static SocialTitle randomSocialTitle() {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }
    }

    public static class UserBuilder {
        private SocialTitle socialTitle;
        private String firstName;
        private String lastName;

        private String email;
        private String password;
        private String birthdate;
        private boolean receiveOffersConsent;
        private boolean customerDataPrivacyConsent;
        private boolean newsletterConsent;
        private boolean generalConditionsConsent;
        private String address;
        private String city;
        private String postalCode;
        private String deliveryAddress;

        public UserBuilder setSocialTitle(SocialTitle socialTitle) {
            this.socialTitle = socialTitle;
            return this;
        }

        public UserBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setBirthdate(String birthdate) {
            this.birthdate = birthdate;
            return this;
        }

        public UserBuilder setReceiveOffersConsent(boolean receiveOffersConsent) {
            this.receiveOffersConsent = receiveOffersConsent;
            return this;
        }

        public UserBuilder setCustomerDataPrivacyConsent(boolean customerDataPrivacyConsent) {
            this.customerDataPrivacyConsent = customerDataPrivacyConsent;
            return this;
        }

        public UserBuilder setNewsletterConsent(boolean newsletterConsent) {
            this.newsletterConsent = newsletterConsent;
            return this;
        }

        public UserBuilder setGeneralConditionsConsent(boolean generalConditionsConsent) {
            this.generalConditionsConsent = generalConditionsConsent;
            return this;
        }

        public UserBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public UserBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public UserBuilder setPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public UserBuilder setDeliveryAddress(String deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
            return this;
        }

        public User build() {
            return new User(socialTitle, firstName, lastName, email, password, birthdate, receiveOffersConsent, customerDataPrivacyConsent, newsletterConsent, generalConditionsConsent, address, city, postalCode, deliveryAddress);
        }
    }
}

