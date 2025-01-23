package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;


import org.junit.jupiter.api.*;
import page.DashboardPage;
import page.PaymentPage;


import static com.codeborne.selenide.Selenide.open;
import static data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TourPurchaseTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }

    @Test
    @DisplayName("1. Отправка формы покупки картой со статусом Approved")
    void shouldSuccessfulBuyingTour() {
        var cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendForm(cardInfo);
        form.successNotificationVisible();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }


    @Test
    @DisplayName("2. Отправка формы покупки в кредит картой со статусом Approved")
    void shouldSuccessfulCreditTour() {
        var cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendForm(cardInfo);
        form.successNotificationVisible();
        assertEquals("APPROVED", SQLHelper.getCreditStatus());
    }

    @Test
    @DisplayName("3. Попытка покупки тура картой со статусом Declined")
    void shouldDeclinePurchaseOfTheTour() {
        var cardInfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendForm(cardInfo);
        form.failureNotificationVisible(); //не должен успешно отправлять заявку в банк
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("4. Попытка покупки тура в кредит картой со статусом Declined")
    void shouldDeclineCreditPurchaseOfTheTour() {
        var cardInfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendForm(cardInfo);
        form.failureNotificationVisible(); //не должен успешно отправлять заявку в банк
        assertEquals("DECLINED", SQLHelper.getCreditStatus());
    }

    @Test
    @DisplayName("5. Попытка отправки пустой формы “Купить")
    void sendEmptyForm() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendEmptyForm();
        form.invalidFormatCard();
        form.invalidFormatMonth();
        form.invalidFormatYear();
        form.emptyFieldOwner();
        form.invalidFormatCvc();
    }

    @Test
    @DisplayName("6. Попытка отправки пустой формы “Купить в кредит")
    void sendEmptyCreditForm() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendEmptyForm();
        form.invalidFormatCard();
        form.invalidFormatMonth();
        form.invalidFormatYear();
        form.emptyFieldOwner();
        form.invalidFormatCvc();
    }

    @Test
    @DisplayName("7. Отправка заявки с некорректными данными в поле Номер карты")
    void get15CardNumber() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.get15CardNumber());
        form.invalidFormatCard();
    }

    @Test
    @DisplayName("8. Отправка заявки с рандомным номером карты")
    void getRandomCardNumber() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getRandomCardNumber());
        form.invalidFormatCard(); //должен подсвечивать поле и выдавать сообщение "Неверный формат"
        form.failureNotificationVisible();
    }

    @Test
    @DisplayName("9. Отправка заявки с пустым полем Номер карты")
    void getEmptyCardNumber() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getEmptyCardNumber());
        form.invalidFormatCard();
    }

    @Test
    @DisplayName("10. Отправка заявки с заполненным 1 цифрой полем Месяц")
    void getOneNumberOfMonth() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getOneNumberOfMonth());
        form.invalidFormatMonth();
    }

    @Test
    @DisplayName("11. Отправка заявки с некорректными данными в поле Месяц")
    void getWrongMonth() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getWrongMonth());
        form.invalidExpirationDateMonth();
    }

    @Test
    @DisplayName("12. Отправка заявки с некорректными некорректным сроком действия карты в поле Месяц")
    void getNonexistentMonth() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getNonexistentMonth());
        form.invalidExpirationDateMonth();
    }

    @Test
    @DisplayName("13. Отправка заявки с пустым полем Месяц")
    void getEmptyMonth() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getEmptyMonth());
        form.invalidFormatMonth();
    }

    @Test
    @DisplayName("14. Отправка заявки с истекшим сроком действия карты в поле Год")
    void getExpiredYear() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getExpiredYear());
        form.expirationDateYear();
    }

    @Test
    @DisplayName("15. Отправка заявки с некорректными данными в поле Год")
    void getWrongYear() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getWrongYear());
        form.invalidExpirationDateYear();
    }

    @Test
    @DisplayName("16. Отправка заявки с пустым полем Год")
    void getEmptyYear() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getEmptyYear());
        form.invalidFormatYear();
    }

    @Test
    @DisplayName("17. Отправка заявки с одним словом в поле Владелец")
    void getOneWordOwner() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getOneWordOwner());
        form.invalidFormatOwner(); // не должен отправлять заявку, под полем владец должно быть сообщение Неверный формат
    }

    @Test
    @DisplayName("18. Отправка заявки с некорректными данными в поле Владелец")
    void getOneLetterOwner() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getOneLetterOwner());
        form.invalidFormatOwner(); // не должен отправлять заявку, под полем владец должно быть сообщение Неверный формат
    }

    @Test
    @DisplayName("19. Отправка заявки с данными на кириллице в поле Владелец")
    void getCyrillicNameOwner() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getCyrillicNameOwner());
        form.invalidFormatOwner(); // не должен отправлять заявку, под полем владец должно быть сообщение Неверный формат
    }

    @Test
    @DisplayName("20. Отправка заявки с цифрами в поле Владелец")
    void getNumberNameOwner() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getNumberNameOwner());
        form.invalidFormatOwner(); // не должен отправлять заявку, под полем владец должно быть сообщение Неверный формат
    }

    @Test
    @DisplayName("21. Отправка заявки со спецсимволами в поле Владелец")
    void getSymbolNameOwner() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getSymbolNameOwner());
        form.invalidFormatOwner(); // не должен отправлять заявку, под полем владец должно быть сообщение Неверный формат
    }

    @Test
    @DisplayName("22. Отправка заявки с данными в нижнем регистре в поле Владелец")
    void getLowerCaseNameOwner() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getLowerCaseNameOwner());
        form.invalidFormatOwner(); // не должен отправлять заявку, под полем владец должно быть сообщение Неверный формат
    }

    @Test
    @DisplayName("23. Отправка заявки с пустым полем Владелец")
    void getEmptyNameOwner() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getEmptyNameOwner());
        form.emptyFieldOwner();
    }

    @Test
    @DisplayName("24. Отправка заявки с 1 цифрой в поле CVC/CVV")
    void getOneNumberCvc() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getOneNumberCvc());
        form.invalidFormatCvc();
    }

    @Test
    @DisplayName("25. Отправка заявки с 2 цифрами в поле CVC/CVV")
    void getTwoNumbersCvc() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getTwoNumbersCvc());
        form.invalidFormatCvc();
    }

    @Test
    @DisplayName("26. Отправка заявки с пустым полем CVC/CVV")
    void getEmptyCvc() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getEmptyCvc());
        form.invalidFormatCvc();
    }

    @Test
    @DisplayName("27. Отправка заявки покупки тура в кредит с некорректными данными в поле Номер карты")
    void get15CardNumberCredit() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.get15CardNumber());
        form.invalidFormatCard();
    }

    @Test
    @DisplayName("28. Отправка заявки покупки тура в кредит с рандомным номером карты")
    void getRandomCardNumberCredit() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getRandomCardNumber());
        form.invalidFormatCard(); //должен подсвечивать поле и выдавать сообщение "Неверный формат", заявка не должна отправляться
        form.failureNotificationVisible();
    }

    @Test
    @DisplayName("29. Отправка заявки покупки тура в кредит с пустым полем Номер карты")
    void getEmptyCardNumberCredit() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getEmptyCardNumber());
        form.invalidFormatCard();
    }

    @Test
    @DisplayName("30. Отправка заявки покупки тура в кредит с заполненным 1 цифрой полем Месяц")
    void getOneNumberOfMonthCredit() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getOneNumberOfMonth());
        form.invalidFormatMonth();
    }

    @Test
    @DisplayName("31. Отправка заявки покупки тура в кредит с некорректными данными в поле Месяц")
    void getWrongMonthCredit() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getWrongMonth());
        form.invalidExpirationDateMonth();
    }

    @Test
    @DisplayName("32. Отправка заявки покупки тура в кредит с некорректными некорректным сроком действия карты в поле Месяц")
    void getNonexistentMonthCredit() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getNonexistentMonth());
        form.invalidExpirationDateMonth();
    }

    @Test
    @DisplayName("33. Отправка заявки покупки тура в кредит с пустым полем Месяц")
    void getEmptyMonthCredit() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getEmptyMonth());
        form.invalidFormatMonth();
    }

    @Test
    @DisplayName("34. Отправка заявки покупки тура в кредит с истекшим сроком действия карты в поле Год")
    void getExpiredYearCredit() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getExpiredYear());
        form.expirationDateYear();
    }

    @Test
    @DisplayName("35. Отправка заявки покупки тура в кредит с некорректными данными в поле Год")
    void getWrongYearCredit() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getWrongYear());
        form.invalidExpirationDateYear();
    }

    @Test
    @DisplayName("36. Отправка заявки покупки тура в кредит с пустым полем Год")
    void getEmptyYearCredit() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getEmptyYear());
        form.invalidFormatYear();
    }

    @Test
    @DisplayName("37. Отправка заявки покупки тура в кредит с одним словом в поле Владелец")
    void getOneWordOwnerCredit() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getOneWordOwner());
        form.invalidFormatOwner(); // не должен отправлять заявку, под полем владец должно быть сообщение Неверный формат
    }

    @Test
    @DisplayName("38. Отправка заявки покупки тура в кредит с некорректными данными в поле Владелец")
    void getOneLetterOwnerCredit() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getOneLetterOwner());
        form.invalidFormatOwner(); // не должен отправлять заявку, под полем владец должно быть сообщение Неверный формат
    }

    @Test
    @DisplayName("39. Отправка заявки покупки тура в кредит с данными на кириллице в поле Владелец")
    void getCyrillicNameOwnerCredit() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getCyrillicNameOwner());
        form.invalidFormatOwner(); // не должен отправлять заявку, под полем владец должно быть сообщение Неверный формат
    }

    @Test
    @DisplayName("40. Отправка заявки покупки тура в кредит с цифрами в поле Владелец")
    void getNumberNameOwnerCredit() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getNumberNameOwner());
        form.invalidFormatOwner(); // не должен отправлять заявку, под полем владец должно быть сообщение Неверный формат
    }

    @Test
    @DisplayName("41. Отправка заявки покупки тура в кредит со спецсимволами в поле Владелец")
    void getSymbolNameOwnerCredit() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getSymbolNameOwner());
        form.invalidFormatOwner(); // не должен отправлять заявку, под полем владец должно быть сообщение Неверный формат
    }

    @Test
    @DisplayName("42. Отправка заявки покупки тура в кредит с данными в нижнем регистре в поле Владелец")
    void getLowerCaseNameOwnerCredit() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getLowerCaseNameOwner());
        form.invalidFormatOwner(); // не должен отправлять заявку, под полем владец должно быть сообщение Неверный формат
    }

    @Test
    @DisplayName("43. Отправка заявки покупки тура в кредит с пустым полем Владелец")
    void getEmptyNameOwnerCredit() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getEmptyNameOwner());
        form.emptyFieldOwner();
    }

    @Test
    @DisplayName("44. Отправка заявки покупки тура в кредит с 1 цифрой в поле CVC/CVV")
    void getOneNumberCvcCredit() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getOneNumberCvc());
        form.invalidFormatCvc();
    }

    @Test
    @DisplayName("45. Отправка заявки покупки тура в кредит с 2 цифрами в поле CVC/CVV")
    void getTwoNumbersCvcCredit() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getTwoNumbersCvc());
        form.invalidFormatCvc();
    }

    @Test
    @DisplayName("46. Отправка заявки покупки тура в кредит с пустым полем CVC/CVV")
    void getEmptyCvcCredit() {
        var dashboardPage = new DashboardPage();
        dashboardPage.payByCreditCard();
        var form = new PaymentPage();
        form.sendForm(DataHelper.getEmptyCvc());
        form.invalidFormatCvc();
    }
}