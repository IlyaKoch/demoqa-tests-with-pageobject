package tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import pages.RegistrationPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class RegistrationWithJavaFakerTest extends TestBase {

    RegistrationPage registrationPage = new RegistrationPage();
    Faker faker = new Faker();
    String firstname = faker.name().firstName(),
            lastname = faker.name().lastName(),
            phoneNumber = faker.phoneNumber().subscriberNumber(10),
            userEmail = faker.internet().emailAddress(),
            address = faker.lebowski().quote();

    @Test
    void formTest() {
        registrationPage.openPage();
        registrationPage.typeFirstName(firstname);
        registrationPage.typeLastName(lastname);
        registrationPage.typeEmail(userEmail);
        $("#genterWrapper").$(byText("Male")).click();
        registrationPage.typeNumber(phoneNumber);
        registrationPage.calendar.setDate("31", "July", "1997");
        $("#subjectsInput").setValue("English").pressEnter();
        $("#hobbiesWrapper").$(byText("Sports")).click();
        $("#hobbiesWrapper").$(byText("Music")).click();
        $("#uploadPicture").uploadFromClasspath("img/myPhoto.img");
        registrationPage.typeAddress(address);
        $("#state").click();
        $("#stateCity-wrapper").$(byText("NCR")).click();
        $("#city").click();
        $("#stateCity-wrapper").$(byText("Noida")).click();
        $("#submit").click();
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        $(".table-responsive").shouldHave(text(firstname),
                text(lastname),
                text(userEmail),
                text("Male"),
                text(phoneNumber),
                text("31 July,1997"),
                text("English"),
                text("Sports"),
                text("Music"),
                text("myPhoto.img"),
                text(address),
                text("NCR"),
                text("Noida"));
    }
}