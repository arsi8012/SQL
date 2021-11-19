package ru.netology.test;

import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.DbUtilsPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDbInterection {
    DbUtilsPage db = new DbUtilsPage();
    private SelenideElement loginField;
    private SelenideElement passwordField;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldValidLogin() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verify = verificationPage.verify(DbUtilsPage.getVerificationCode());
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
        val status = DbUtilsPage.getStatusDb();
        assertEquals("blocked", status);
    }

    @AfterAll
    static void close() {
        DbUtilsPage.cleanCards();
    }

    @AfterAll
    static void close1() {
        DbUtilsPage.cleanAuthCodes();
    }

    @AfterAll
    static void close2() {
        DbUtilsPage.cleanUsers();
    }
}
