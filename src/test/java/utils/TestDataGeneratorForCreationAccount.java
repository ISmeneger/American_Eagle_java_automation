package utils;

import com.github.javafaker.Faker;

public final class TestDataGeneratorForCreationAccount {

    private static final Faker FAKER = new Faker();

    private TestDataGeneratorForCreationAccount() {
    }

    public static String generateEmail() {
        return FAKER.internet().emailAddress();
    }

    public static String generatePassword() {
        return FAKER.internet().password(8, 25, true, true);
    }

    public static String generateFirstName() {
        return FAKER.name().firstName();
    }

    public static String generateLastName() {
        return FAKER.name().lastName();
    }
}