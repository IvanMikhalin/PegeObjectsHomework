package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByText;
import com.google.common.primitives.UnsignedInteger;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private final SelenideElement amountInput = $("[data-test-id='amount'] input");
    private final SelenideElement fromInput = $("[data-test-id='from'] input");
    private final SelenideElement submitRecharge = $("[data-test-id='action-transfer']");
    private final SelenideElement rechargeHead = $(byText("Пополнение карты"));
    private static final SelenideElement errorMessage = $("[data-test-id='error-notification']");


    public TransferPage() {
        rechargeHead.should(visible);
    }

    public DashboardPage makeValidTransfer(int amount, DataHelper.CardInfo cardInfo) {
        rechargeCard(amount, cardInfo);
        return new DashboardPage();
    }

    public void rechargeCard(int value, DataHelper.CardInfo cardInfo) {
        amountInput.setValue(String.valueOf(value));
        fromInput.setValue(cardInfo.getCardNumber());
        submitRecharge.click();
    }

    public static void searchErrorMessage(String expectedText) {
  //      errorMessage.withText(withText(), Duration.ofSeconds(15)).shouldBe(visible);
        $(withText(expectedText)).shouldBe(visible);
    }
}
