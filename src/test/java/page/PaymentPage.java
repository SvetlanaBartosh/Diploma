package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class PaymentPage {
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

    public void sendForm (DataHelper.CardInfo cardInfo) {
        cardNumber.setValue(cardInfo.getCardNumber());
        month.setValue(cardInfo.getMonth());
        year.setValue(cardInfo.getYear());
        owner.setValue(cardInfo.getOwner());
        cvc.setValue(cardInfo.getCvc());
        submit.click();
    }

    public void sendEmptyForm () {
        submit.click();
    }

    public void successNotificationVisible () {
        successNotification.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void failureNotificationVisible () {
        failureNotification.shouldBe(Condition.visible);
    }

    public void invalidFormatCard () {
        cardField.shouldHave(text("Неверный формат"));
    }

    public void invalidFormatMonth () {
        monthField.shouldHave(text("Неверный формат"));
    }

    public void invalidFormatYear () {
        yearField.shouldHave(text("Неверный формат"));
    }

    public void invalidFormatCvc () {
        cvvField.shouldHave(text("Неверный формат"));
    }

    public void invalidFormatOwner () {
        ownerField.shouldHave(text("Неверный формат"));
    }

    public void expirationDateYear () {
        yearField.shouldHave(text("Истёк срок действия карты"));
    }

    public void invalidExpirationDateMonth () {
        monthField.shouldHave(text("Неверно указан срок действия карты"));
    }

    public void invalidExpirationDateYear () {
        yearField.shouldHave(text("Неверно указан срок действия карты"));
    }

    public void emptyFieldOwner () {
        ownerField.shouldHave(text("Поле обязательно для заполнения"));
    }
}
