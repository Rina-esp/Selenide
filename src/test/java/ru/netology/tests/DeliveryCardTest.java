package ru.netology.tests;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {
  LocalDate date = LocalDate.now();
  LocalDate deliveryDate = date.plusDays(3);


  @Test
  void shouldDeliveryCardPositive() {
    Configuration.holdBrowserOpen = true;
    open("http://localhost:9999");
    $("input[placeholder=\"Город\"]").setValue("Санкт-Петербург");
    $("[data-test-id=date] input").doubleClick();
    $("[data-test-id=date] input").setValue(String.valueOf(deliveryDate));
    $("[data-test-id=\"name\"] input\n").setValue("Дмитрий Иванов");
    $("[data-test-id=\"phone\"] input\n").setValue("+79119500000");
    $(byText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).click();
    $$("button").find(exactText("Забронировать"))
        .click();

    $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
    $("[data-test-id=\"notification\"]").shouldHave(text("Успешно! Встреча успешно забронирована на 16.06.2022"), Duration.ofSeconds(15));
  }

  @Test
  void shouldDeliveryCardDateLess3DaysN() {
    Configuration.holdBrowserOpen = true;
    open("http://localhost:9999");
    $("input[placeholder=\"Город\"]").setValue("Санкт-Петербург");
    $("[data-test-id=date] input").doubleClick();
    $("[data-test-id=date] input").sendKeys("14.06.2022");
    $("[data-test-id=\"name\"] input\n").setValue("Дмитрий Иванов");
    $("[data-test-id=\"phone\"] input\n").setValue("+79119500000");
    $(byText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).click();
    $$("button").find(exactText("Забронировать"))
        .click();

    $("[data-test-id=\"date\"] .input_invalid .input__sub\n").shouldHave(text("Заказ на выбранную дату невозможен"));
  }

}

