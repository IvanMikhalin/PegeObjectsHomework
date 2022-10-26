package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class TransferPage {

    private final SelenideElement amountInput = $("[data-test-id='amount'] input");
    private final SelenideElement fromInput = $("[data-test-id='from'] input");
    private final SelenideElement submitRecharge = $("[data-test-id='action-transfer']");
    private final SelenideElement rechargeHead = $(byText("Пополнение карты"));
    private static final SelenideElement errorMessage = $("[data-test-id='error-message']");


    public TransferPage() {
        rechargeHead.should(visible);
    }

    public DashboardPage makeValidTransfer(double amount, DataHelper.CardInfo cardInfo) {
        rechargeCard (amount, cardInfo);
        return new DashboardPage();
    }

    public void rechargeCard(double value, DataHelper.CardInfo cardInfo) {
        amountInput.setValue(String.valueOf(value));
        fromInput.setValue(cardInfo.getCardNumber());
        submitRecharge.click();
    }

    public static void searchErrorMessage(String expectedText) {
        errorMessage.shouldHave(exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }
}
