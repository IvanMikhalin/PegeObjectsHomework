package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

class TransferTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferFromFirstToSecond() {
        var loginPage = new LoginPage();
        var authInfo = getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance = dashboardPage.checkBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.checkBalance(secondCardInfo);
        var amount = generateValidAmount(firstCardBalance);
        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        dashboardPage = transferPage.makeValidTransfer(amount, firstCardInfo);
        assertEquals(firstCardBalance - amount, dashboardPage.checkBalance(firstCardInfo));
        assertEquals(secondCardBalance + amount, dashboardPage.checkBalance(secondCardInfo));
    }

    @Test
    void shouldTransferFromSecondToFirst() {
        var loginPage = new LoginPage();
        var authInfo = getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance = dashboardPage.checkBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.checkBalance(secondCardInfo);
        var amount = generateValidAmount(secondCardBalance);
        var moneyTransferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
        dashboardPage = moneyTransferPage.makeValidTransfer(amount, secondCardInfo);
        assertEquals(secondCardBalance - amount, dashboardPage.checkBalance(secondCardInfo));
        assertEquals(firstCardBalance + amount, dashboardPage.checkBalance(firstCardInfo));
    }

    @Test
    void shouldShowErrorIfAmountOfChargeOverBalanceFirstToSecond() {
        var loginPage = new LoginPage();
        var authInfo = getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance = dashboardPage.checkBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.checkBalance(secondCardInfo);
        var amount = generateInvalidAmount(firstCardBalance);
        var moneyTransferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        dashboardPage = moneyTransferPage.makeValidTransfer(amount, firstCardInfo);
        TransferPage.searchErrorMessage("Ошибка!");
        assertEquals(firstCardBalance - amount, dashboardPage.checkBalance(firstCardInfo));
        assertEquals(secondCardBalance + amount, dashboardPage.checkBalance(secondCardInfo));
    }

    @Test
    void shouldShowErrorIfAmountOfChargeOverBalanceSecondToFirst() {
        var loginPage = new LoginPage();
        var authInfo = getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance = dashboardPage.checkBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.checkBalance(secondCardInfo);
        var amount = generateInvalidAmount(secondCardBalance);
        var moneyTransferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
        dashboardPage = moneyTransferPage.makeValidTransfer(amount, secondCardInfo);
        TransferPage.searchErrorMessage(
                "Ошибка!");
        assertEquals(secondCardBalance, dashboardPage.checkBalance(secondCardInfo));
        assertEquals(firstCardBalance, dashboardPage.checkBalance(firstCardInfo));
    }
}
