package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class CreditPage {
    private SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement month = $("[placeholder='08']");
    private SelenideElement year = $("[placeholder='22']");
    private SelenideElement owner = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvc = $("[placeholder='999']");
    private SelenideElement submit = $(byText("Продолжить"));

    private SelenideElement successNotification = $(byText("Операция одобрена Банком."));
    private SelenideElement failureNotification = $(byText("Ошибка! Банк отказал в проведении операции."));

    private SelenideElement cardField = $(byText("Номер карты")).parent().$(".input__sub");
    private SelenideElement monthField = $(byText("Месяц")).parent().$(".input__sub");
    private SelenideElement yearField = $(byText("Год")).parent().$(".input__sub");
    private SelenideElement ownerField = $(byText("Владелец")).parent().$(".input__sub");
    private SelenideElement cvvField = $(byText("CVC/CVV")).parent().$(".input__sub");

    public void sendForm(DataHelper.CardInfo cardInfo) {
        cardNumber.setValue(cardInfo.getCardNumber());
        month.setValue(cardInfo.getMonth());
        year.setValue(cardInfo.getYear());
        owner.setValue(cardInfo.getOwner());
        cvc.setValue(cardInfo.getCvc());
        submit.click();
    }

    public void sendEmptyForm() {
        submit.click();
    }

    public void successNotificationVisible() {
        successNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void failureNotificationVisible() {
        failureNotification.shouldBe(visible);
    }

    public void checkFieldError(SelenideElement field, String expectedErrorText) {
        field.shouldBe(visible);
        field.shouldHave(text(expectedErrorText));
    }

    public void cardFieldError(String expectedErrorText) {
        checkFieldError(cardField, expectedErrorText);
    }

    public void monthFieldError(String expectedErrorText) {
        checkFieldError(monthField, expectedErrorText);
    }

    public void yearFieldError(String expectedErrorText) {
        checkFieldError(yearField, expectedErrorText);
    }

    public void ownerFieldError(String expectedErrorText) {
        checkFieldError(ownerField, expectedErrorText);
    }

    public void cvvFieldError(String expectedErrorText) {
        checkFieldError(cvvField, expectedErrorText);
    }
}
