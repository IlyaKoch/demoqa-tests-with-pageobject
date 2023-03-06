package tests;

import Utils.GetTableContent;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import pages.RegistrationPage;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static org.assertj.core.api.Assertions.assertThat;
import static tests.TestData.*;

public class RegistrationWithExecuteJavaScriptTests extends TestBase {

    RegistrationPage registrationPage = new RegistrationPage();

    public static Map<String, String> expectedData = new LinkedHashMap<>() {{
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

        LinkedHashMap<String, String> actualData = GetTableContent.getTableContentWithExecuteScript();
        assertThat(actualData).isEqualTo(expectedData);
        System.out.println("Expected data:\n" + StringUtils.join(expectedData));
        }
}