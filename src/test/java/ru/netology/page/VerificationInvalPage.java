package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationInvalPage {
    private SelenideElement errorField = $("[class=notification__title]");


    public VerificationInvalPage() {
        errorField.shouldBe(visible);
    }
}
