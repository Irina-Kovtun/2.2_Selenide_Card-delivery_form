package netology.ru.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static java.time.format.DateTimeFormatter.ofPattern;

public class CallBackTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTestHappyPath() {
        $("[data-test-id='city'] input").setValue("Москва");
        SelenideElement dateField = $("[placeholder='Дата встречи']");
        dateField.click();
        dateField.sendKeys(Keys.CONTROL, "a");
        dateField.sendKeys(Keys.BACK_SPACE);
        String dateOfMeeting = LocalDate.now().plusDays(3).format(ofPattern("dd.MM.yyyy"));
        dateField.setValue(dateOfMeeting);
        $("[data-test-id='name'] input").setValue("Василий");
        $("[data-test-id='phone'] input").setValue("+12345678999");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=notification] .notification__content").shouldBe(visible, ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + dateOfMeeting));
    }
}
