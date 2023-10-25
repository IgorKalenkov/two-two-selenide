import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CartDeliveryTest {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldDeliveryCard() {
        open("http://localhost:9999");

        $("[data-test-id=city] input").setValue("Майкоп");
        $("[data-test-id=date] input").doubleClick().sendKeys(generateDate(29, "dd.MM.yyyy"));
        $("[data-test-id=name] input").setValue("Каленков Игорь Дмитриевич");
        $("[data-test-id=phone] input").setValue("+79771837066");
        $(withText("Успешно")).shouldBe(Condition.hidden);
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=notification]").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id=notification]").shouldHave(Condition.text("Успешно!\n" +
                "Встреча успешно забронирована на " + generateDate(29, "dd.MM.yyyy"))).shouldBe(Condition.visible);
    }
}
