package test;

import org.junit.Test;
import pages.GooglePage;
import pages.MobilePage;
import pages.PageResultSearch;


public class EmptyFieldError extends BaseRunner {

    @Test
    public void testEmptyFieldError() {

        MobilePage mobilePage = app.tinkoffMobile;
        mobilePage.open();

        mobilePage.clickName();
        mobilePage.clickCountry();
        mobilePage.clickPhone();
        mobilePage.clickEmail();
        mobilePage.chekBoxConditions(false);

        mobilePage.buttonRequestClick();

        mobilePage.errorEmptyName();
        mobilePage.errorEmptyPhone();
        mobilePage.errorEmptyCountry();
        mobilePage.errorEmptyCondition();

    }

    @Test
    public void testNonValidValue() {

        MobilePage mobilePage = app.tinkoffMobile;
        mobilePage.open();

        mobilePage.textInputName("Марлен");
        mobilePage.clickEmail();
        mobilePage.errorShortName();
        mobilePage.deleteValueName();
        mobilePage.CopyValueName("Selenium IDE");
        mobilePage.clickEmail();
        mobilePage.errorEnglishName();

        mobilePage.textInputCountry("China");
        mobilePage.clickEmail();
        mobilePage.errorIncorrectCountry();

        mobilePage.textInputEmail("@yandex.ru");
        mobilePage.clickName();
        mobilePage.errorIncorrectEmail();

        mobilePage.textInputPhone("(777) 777-77-77");
        mobilePage.clickEmail();
        mobilePage.errorCodePhone();
        mobilePage.deleteValuePhone();
        mobilePage.textInputPhone("(999) 999-99-0");
        mobilePage.clickEmail();
        mobilePage.errorShortPhone();

    }

    @Test
    public void testSwithcTabs() throws InterruptedException {

        GooglePage googlePage = app.googlePage;
        googlePage.open();
        // поменяла строку поиска, т.к.
        googlePage.searchResultField("tinkoff mobile тарифы");

        PageResultSearch pageResultSearch = app.googleSearchResult;
        pageResultSearch.foundLink("https://www.tinkoff.ru/mobile-operator/tariffs/");


       MobilePage tinkoffMobile = app.tinkoffMobile;
       tinkoffMobile.refresh();
       tinkoffMobile.switchToWindow("Тарифы Тинькофф Мобайла");
       tinkoffMobile.switchbetweenWindows(0);
       tinkoffMobile.closeCurrentTab();
       tinkoffMobile.switchToWindow("Тарифы Тинькофф Мобайла");
       tinkoffMobile.getCurrentUrl("https://www.tinkoff.ru/mobile-operator/tariffs/");
    }

    @Test
    public void changeRegion() {
        MobilePage mobilePage = app.tinkoffMobile;
        mobilePage.open();

        mobilePage.changeCity("Москва");

        mobilePage.refresh();
        mobilePage.equalsCity("Москва и Московская область");
        mobilePage.equalsDefolt("296");

        mobilePage.selectPhoneCalls("Безлимитные минуты");
        mobilePage.selectInterntet("Безлимитный интернет");
        mobilePage.chekBoxModem(true);
        mobilePage.chekBoxSMS(true);
        mobilePage.equalsMaxSum("2546");
    }

    @Test
    public void inactiveButton (){

        MobilePage mobilePage = app.tinkoffMobile;
        mobilePage.open();

        mobilePage.changeCity("Москва");

        mobilePage.selectPhoneCalls("0 минут");
        mobilePage.selectInterntet("0 ГБ");
        mobilePage.chekBoxMessenger(false);
        mobilePage.chekBoxSMS(false);
        mobilePage.chekBoxMusic(false);
        mobilePage.chekBoxSocialNetwork(false);
        mobilePage.chekBoxVideo(false);

        mobilePage.textInputName("Марлен Иванович");
        mobilePage.textInputEmail("my@yandex.ru");
        mobilePage.textInputPhone("(999) 999-99-09");

        mobilePage.buttonOrderClick();
        mobilePage.buttonOrderShipping();
    }
}
