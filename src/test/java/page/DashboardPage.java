package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private final SelenideElement buy = $(byText("Купить"));
    private final SelenideElement credit = $(byText("Купить в кредит"));
    private final SelenideElement paymentByCard = $(byText("Оплата по карте"));
    private final SelenideElement creditAccordingToCard = $(byText("Кредит по данным карты"));

    public DashboardPage payByCard (){
        buy.click();
        paymentByCard.shouldBe(Condition.visible);
        return new DashboardPage();
    }

    public DashboardPage payByCreditCard (){
        credit.click();
        creditAccordingToCard.shouldBe(Condition.visible);
        return new DashboardPage();
    }

}
