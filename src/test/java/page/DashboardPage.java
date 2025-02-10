package page;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    public void goToPaymentPage() {
        $(byText("Купить")).click();
    }

    public void goToCreditPage() {
        $(byText("Купить в кредит")).click();
    }
}
