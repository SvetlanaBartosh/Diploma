package data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class CardInfo {
        String cardNumber;
        String month;
        String year;
        String owner;
        String cvc;
    }

    public static String getApprovedCardNumber() {
        return ("4444 4444 4444 4441");
    }

    public static String getDeclinedCardNumber() {
        return ("4444 4444 4444 4442");
    }

    public static CardInfo get15CardNumber() {
        return new CardInfo("4444 4444 4444 444", "01", "25", "Ivan Ivanov", "456");
    }

    public static CardInfo getRandomCardNumber() {
        return new CardInfo("1234 5678 9012 3456", "01", "25", "Ivan Ivanov", "456");
    }

    public static CardInfo getEmptyCardNumber() {
        return new CardInfo("", "01", "25", "Ivan Ivanov", "456");
    }

    public static String getValidMonth() {
        return ("01");
    }

    public static CardInfo getOneNumberOfMonth() {
        return new CardInfo("4444 4444 4444 4441", "1", "25", "Ivan Ivanov", "456");
    }

    public static CardInfo getWrongMonth() {
        return new CardInfo("4444 4444 4444 4441", "00", "25", "Ivan Ivanov", "456");
    }

    public static CardInfo getNonexistentMonth() {
        return new CardInfo("4444 4444 4444 4441", "13", "25", "Ivan Ivanov", "456");
    }

    public static CardInfo getEmptyMonth() {
        return new CardInfo("4444 4444 4444 4441", "", "25", "Ivan Ivanov", "456");
    }

    public static String getValidYear() {
        return ("25");
    }

    public static CardInfo getWrongYear() {
        return new CardInfo("4444 4444 4444 4441", "01", "50", "Ivan Ivanov", "456");
    }

    public static CardInfo getExpiredYear() {
        return new CardInfo("4444 4444 4444 4441", "01", "23", "Ivan Ivanov", "456");
    }

    public static CardInfo getEmptyYear() {
        return new CardInfo("4444 4444 4444 4441", "01", "", "Ivan Ivanov", "456");
    }

    public static String getValidOwner() {
        Faker FAKER = new Faker(new Locale("en"));
        return FAKER.name().fullName();
    }

    public static CardInfo getOneWordOwner() {
        return new CardInfo("4444 4444 4444 4441", "01", "25", "Ivan", "456");
    }

    public static CardInfo getOneLetterOwner() {
        return new CardInfo("4444 4444 4444 4441", "01", "25", "I", "456");
    }

    public static CardInfo getCyrillicNameOwner() {
        return new CardInfo("4444 4444 4444 4441", "01", "25", "Иван", "456");
    }

    public static CardInfo getNumberNameOwner() {
        return new CardInfo("4444 4444 4444 4441", "01", "25", "123456", "456");
    }

    public static CardInfo getSymbolNameOwner() {
        return new CardInfo("4444 4444 4444 4441", "01", "25", "*&%#@", "456");
    }

    public static CardInfo getLowerCaseNameOwner() {
        return new CardInfo("4444 4444 4444 4441", "01", "25", "ivan ivanov", "456");
    }

    public static CardInfo getEmptyNameOwner() {
        return new CardInfo("4444 4444 4444 4441", "01", "25", "", "456");
    }

    public static String getValidCvc() {
        return ("456");
    }

    public static CardInfo getTwoNumbersCvc() {
        return new CardInfo("4444 4444 4444 4441", "01", "25", "Ivan Ivanov", "45");
    }

    public static CardInfo getOneNumberCvc() {
        return new CardInfo("4444 4444 4444 4441", "01", "25", "Ivan Ivanov", "4");
    }

    public static CardInfo getEmptyCvc() {
        return new CardInfo("4444 4444 4444 4441", "01", "25", "Ivan Ivanov", "");
    }
}