package tests;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import pages.RegistrationPage;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tests.TestData.*;

public class RegistrationWithElementsCollectionTest extends TestBase {

    RegistrationPage registrationPage = new RegistrationPage();

    Map<String, String> expectedData = new HashMap<>() {{
        put("Student Name", firstName + " " + lastName);
        put("Student Email", email);
        put("Gender", gender);
        put("Mobile", mobile);
        put("Date of Birth", dateOfBirth);
        put("Subjects", subjects);
        put("Hobbies", hobbies);
        put("Picture", pictureName);
        put("Address", address);
        put("State and City", state + " " + city);
    }};

    @Test
    void successfulRegistrationTest() {
        registrationPage.openPage();
        registrationPage.typeFirstName(firstName);
        registrationPage.typeLastName(lastName);
        registrationPage.typeEmail(email);
        $("#genterWrapper").$(byText("Male")).click();
        registrationPage.typeNumber(mobile);
        registrationPage.calendar.setDate("31", "July", "1997");
        $("#subjectsInput").setValue(subjects).pressEnter();
        $("#hobbiesWrapper").$(byText("Sports")).click();
        $("#hobbiesWrapper").$(byText("Music")).click();
        $("#uploadPicture").uploadFromClasspath("img/myPhoto.img");
        registrationPage.typeAddress(address);
        $("#state").click();
        $("#stateCity-wrapper").$(byText(state)).click();
        $("#city").click();
        $("#stateCity-wrapper").$(byText(city)).click();
        $("#submit").click();
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));

        ElementsCollection lines = $$(".table-responsive tbody tr").snapshot();
        for (SelenideElement line : lines) {
            String key = line.$("td").text(); //Student name
            String expectedValue = expectedData.get(key);
            String actualValue = line.$("td", 1).text();
            assertEquals(expectedValue, actualValue);
        }
    }

    @Test
    void successfulRegistrationWithSoftAssertTest() {
        registrationPage.openPage();
        registrationPage.typeFirstName(firstName);
        registrationPage.typeLastName(lastName);
        registrationPage.typeEmail(email);
        $("#genterWrapper").$(byText("Male")).click();
        registrationPage.typeNumber(mobile);
        registrationPage.calendar.setDate("31", "July", "1997");
        $("#subjectsInput").setValue(subjects).pressEnter();
        $("#hobbiesWrapper").$(byText("Sports")).click();
        $("#hobbiesWrapper").$(byText("Music")).click();
        $("#uploadPicture").uploadFromClasspath("img/myPhoto.img");
        registrationPage.typeAddress(address);
        $("#state").click();
        $("#stateCity-wrapper").$(byText(state)).click();
        $("#city").click();
        $("#stateCity-wrapper").$(byText(city)).click();
        $("#submit").click();
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));

        ElementsCollection lines = $$(".table-responsive tbody tr").snapshot();
        SoftAssertions sa = new SoftAssertions();
        for (SelenideElement line : lines) {
            String key = line.$("td").text(); //Student name
            String expectedValue = expectedData.get(key);
            String actualValue = line.$("td", 1).text();
            sa.assertThat(actualValue)
                    .as(format("Resilt in line %s was %s, but expected %s", key, actualValue, expectedValue))
                    .isEqualTo(expectedValue);
        }
        sa.assertAll();
    }
}