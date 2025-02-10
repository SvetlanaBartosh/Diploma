package data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

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
    private static final Faker FAKER = new Faker();

    public static String getValidMonth() {
        return String.format("%02d", ThreadLocalRandom.current().nextInt(1, 13));
    }

    public static String getValidYear() {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear() % 100;
        int randomYear = currentYear + ThreadLocalRandom.current().nextInt(1, 6);
        return String.format("%02d", randomYear);
    }

    public static String getValidOwner() {
        Faker FAKER = new Faker(new Locale("en"));
        return FAKER.name().fullName();
    }

    public static String getValidCvc() {
        return String.format("%03d", FAKER.number().numberBetween(100, 1000));
    }

    public static String getApprovedCardNumber() {
        return ("4444 4444 4444 4441");
    }

    public static String getDeclinedCardNumber() {
        return ("4444 4444 4444 4442");
    }

    public static CardInfo get15CardNumber() {
        return new CardInfo("4444 4444 4444 444", getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getRandomCardNumber() {
        return new CardInfo("1234 5678 9012 3456", getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getEmptyCardNumber() {
        return new CardInfo("", getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getOneNumberOfMonth() {
        return new CardInfo(getApprovedCardNumber(), "1", getValidYear(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getWrongMonth() {
        return new CardInfo(getApprovedCardNumber(), "00", getValidYear(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getNonexistentMonth() {
        return new CardInfo(getApprovedCardNumber(), "13", getValidYear(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getEmptyMonth() {
        return new CardInfo(getApprovedCardNumber(), "", getValidYear(), getValidOwner(), getValidCvc());
    }

    public static CardInfo getWrongYear() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "50", getValidOwner(), getValidCvc());
    }

    public static CardInfo getExpiredYear() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "23", getValidOwner(), getValidCvc());
    }

    public static CardInfo getEmptyYear() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "", getValidOwner(), getValidCvc());
    }

    public static CardInfo getOneWordOwner() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "Ivan", getValidCvc());
    }

    public static CardInfo getOneLetterOwner() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "I", getValidCvc());
    }

    public static CardInfo getCyrillicNameOwner() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "Иван", getValidCvc());
    }

    public static CardInfo getNumberNameOwner() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "123456", getValidCvc());
    }

    public static CardInfo getSymbolNameOwner() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "*&%#@", getValidCvc());
    }

    public static CardInfo getLowerCaseNameOwner() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "ivan ivanov", getValidCvc());
    }

    public static CardInfo getEmptyNameOwner() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "", getValidCvc());
    }

    public static CardInfo getTwoNumbersCvc() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidOwner(), "45");
    }

    public static CardInfo getOneNumberCvc() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidOwner(), "4");
    }

    public static CardInfo getEmptyCvc() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidOwner(), "");
    }
}