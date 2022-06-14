package ru.netology.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {
  String planningDate = generateDate(4);

  public String generateDate(int days) {
    return LocalDate.now()
        .plusDays(days)
        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
  }

  @Test
  void shouldDeliveryCardPositive() {
    Configuration.holdBrowserOpen = true;
    open("http://localhost:9999");
    $("input[placeholder=\"Город\"]").setValue("Санкт-Петербург");
    $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
    $("[data-test-id=date] input").setValue(planningDate);
    $("[data-test-id=\"name\"] input\n").setValue("Дмитрий Иванов");
    $("[data-test-id=\"phone\"] input\n").setValue("+79119500000");
    $(".checkbox__text").click();
    $$("button").find(exactText("Забронировать"))
        .click();

    $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
    $("[data-test-id=\"notification\"]").shouldHave(text("Успешно! Встреча успешно забронирована на " + (planningDate)), Duration.ofSeconds(15));
  }

}

