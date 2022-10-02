package models;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;

import static models.User.SocialTitle.MRS;
import static models.User.SocialTitle.randomSocialTitle;

public class UserFactory {

    public User getRandomUser() {
        Faker faker = new Faker();


        User.SocialTitle socialTitle = randomSocialTitle();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("pl-PL"), new RandomService());
        String email = fakeValuesService.bothify("????##@gmail.com");

        String password = faker.internet().password();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String birthdate = sdf.format(faker.date().birthday(18, 101));
        boolean receiveOffersConsent = new Random().nextBoolean();
        boolean customerDataPrivacyConsent = new Random().nextBoolean();
        boolean newsletterConsent = new Random().nextBoolean();
        boolean generalConditionsConsent = new Random().nextBoolean();

        return new User.UserBuilder()
                .setSocialTitle(socialTitle)
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

        String name = "Gosia";
        String surname = "So";
        String email = "email@email.com";
        String password = "hasło-masło";
        String birthdate = "04/10/2000";

        return new User.UserBuilder()
                .setSocialTitle(MRS)
                .setFirstName(name)
                .setLastName(surname)
                .setEmail(email)
                .setPassword(password)
                .setBirthdate(birthdate)
                .setReceiveOffersConsent(true)
                .setCustomerDataPrivacyConsent(false)
                .setNewsletterConsent(false)
                .setGeneralConditionsConsent(true)
                .build();
    }
}
