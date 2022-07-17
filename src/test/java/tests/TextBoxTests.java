package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TextBoxTests {

    @BeforeAll
    static void beforeAll(){
        Configuration.startMaximized = true;
    }

    @Test
    void fillFormTest(){
        open("https://demoqa.com/text-box");
        $("#userName").setValue("Alex");
        $("#userEmail").setValue("Alex@mail.ru");
        $("#currentAddress").setValue("Alex123");
        $("#permanentAddress").setValue("Alexey234");
        $("#submit").click();
        $("#output #name").shouldHave(text("Alex"));
        $("#output #email").shouldHave(text("Alex@mail.ru"));
        $("#output #currentAddress").shouldHave(text("Alex123"));
        $("#output #permanentAddress").shouldHave(text("Alexey234"));
    }
}