package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.DbUtilsData;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDbInterection {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldValidLogin() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verify = verificationPage.verify(DbUtilsData.getVerificationCode());
        assertEquals("Личный кабинет", DashboardPage.getHeading());
    }

    @Test
    void shouldIncorrectPasswordEntryThreeTimes() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getIncorrectPass();
        loginPage.invalidLogin(authInfo);
        loginPage.cleanFields();
        loginPage.invalidLogin(authInfo);
        loginPage.cleanFields();
        loginPage.invalidLogin(authInfo);
        val status = DbUtilsData.getStatusDb("vasya");
        assertEquals("blocked", status);
    }

    @AfterAll
    static void close() {
        DbUtilsData.cleanDatabase();
    }
}