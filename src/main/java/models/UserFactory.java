package models;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class UserFactory {

    public User getRandomUser() {
        Faker faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("pl-PL"), new RandomService());
        String email = fakeValuesService.bothify("????##@gmail.com");

        String password = faker.internet().password();
        Date birthdate = faker.date().birthday();
        boolean receiveOffersConsent = new Random().nextBoolean();
        boolean customerDataPrivacyConsent = new Random().nextBoolean();
        boolean newsletterConsent = new Random().nextBoolean();
        boolean generalConditionsConsent = new Random().nextBoolean();

        return new User.UserBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPassword(password)
                .setBirthdate(birthdate)
                .setReceiveOffersConsent(receiveOffersConsent)
                .setCustomerDataPrivacyConsent(customerDataPrivacyConsent)
                .setNewsletterConsent(newsletterConsent)
                .setGeneralConditionsConsent(generalConditionsConsent)
                .build();
    }

    public User getAlreadyRegisteredUser() {

    }
}
