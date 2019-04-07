package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.*;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.PageFactory;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MobilePage extends  Page{
    public MobilePage(WebDriver driver) {
        super(driver);
}

    public void open()  {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.tinkoff.ru/mobile-operator/tariffs/");

    }

    public  boolean pageLoaded(String titlePage){
        wait.until(d -> d.getTitle().contains(titlePage));
        return true;
    }

// методы для клика полей

    public void clickName(){
        wait.until(d ->{
            driver.findElement(By.xpath("//span[contains(text(),'Фамилия')]/../../../div/input[last()]")).click();
            return true;
        });
    }

    public void clickPhone(){
        wait.until(d ->{
            driver.findElement(By.xpath("//span[contains(text(),'телефон')]/../../../div/input[last()]")).click();
            return true;
        });
    }

    public void clickEmail(){
        wait.until(d ->{
            driver.findElement(By.xpath("//span[contains(text(),'почта')]/../../../div/input[last()]")).click();
            return true;
        });

    }

    public void clickCountry(){
        wait.until(d ->{
            driver.findElement(By.xpath("//span[@class='ui-select__value']")).click(); // гражданство
            driver.findElement(By.xpath("//span[contains(text(),'Не имею гражданства РФ')]")).click();
            driver.findElement(By.xpath("//input[@name='temp_non_resident_nationality']")).click();
            driver.findElement(By.cssSelector("[name='temp_non_resident_nationality']")).click();
            return true;
        });

    }

// проверка ошибок на пустые поля

    public void errorEmptyName(){
        assertEquals("Укажите ваше ФИО",
                driver.findElement(By.xpath("//*[contains(@class, 'fio-field')]//*[contains(@class, 'error-message')]"))
                        .getText());
    }

    public void errorEmptyPhone(){
        assertEquals("Необходимо указать номер телефона",
                driver.findElement(By.xpath("//*[contains(@class, 'ui-form__row ui-form__row_tel')]//*/div[contains(text(), 'указать номер')]"))
                        .getText());
    }

    public void errorEmptyCountry(){
        assertEquals("Поле обязательное",
                driver.findElement(By
                        .xpath("//*[contains(@class, 'ui-form__row ui-form__row_dropdownSuggest ui-form__row_default-error-view-visible')]" +
                                "//*[contains(@class, 'ui-form-field-error-message ui-form-field-error-message_ui-form')]"))
                        .getText());
    }

    public void errorEmptyCondition(){
        assertEquals("Для продолжения нужно согласие с условиями",
                driver.findElement(By.
                        xpath("//label[@for='agreement']/..//../../div[contains(text(),'Для продолжения')]"))
                        .getText());
    }

    // проверка ошибок на некорректные вводимые значения

    public void errorEnglishName(){
        assertEquals("Допустимо использовать только буквы русского алфавита и дефис",
               driver.findElement(By.cssSelector("div.ui-form__row_dropdownFIO .ui-form-field-error-message")).getText());
    }
    public void errorShortName(){
        assertEquals("Недостаточно информации. Введите фамилию, имя и отчество через пробел (Например: Иванов Иван Алексеевич)",
               driver.findElement(By.cssSelector("div.ui-form__row_dropdownFIO .ui-form-field-error-message")).getText());
    }

    public void errorShortPhone(){
        assertEquals("Номер телефона должен состоять из 10 цифр, начиная с кода оператора",
                driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Контактный телефон'])[1]/following::div[2]"))
                        .getText());
    }

    public void errorCodePhone(){
        assertEquals("Код оператора должен начинаться с цифры 9",
                driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Контактный телефон'])[1]/following::div[2]"))
                        .getText());

    }

    public void errorIncorrectCountry(){
        assertEquals("Выберите страну из выпадающего списка",
                driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Укажите страну'])[1]/following::div[3]"))
                        .getText());
    }

    public void errorIncorrectEmail(){
        assertEquals("Введите корректный адрес эл. почты",
                driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Электронная почта'])[1]/following::div[3]"))
                        .getText());

    }

        // методы для заполнения полей
    public void textInputName(String nameValue){
        wait.until(d ->{
            driver.findElement(By.xpath("//span[contains(text(),'Фамилия')]/../../../div/input[last()]")).sendKeys(nameValue);
            return true;
        });
        return ;
    }

    public void textInputPhone( String phoneValue){
        wait.until(d ->{
            driver.findElement(By.xpath("//span[contains(text(),'телефон')]/../../../div/input[last()]")).sendKeys(phoneValue);
            return true;
        });
        return ;
    }

    public void textInputEmail( String emailValue){
        wait.until(d ->{
            driver.findElement(By.xpath("//span[contains(text(),'почта')]/../../../div/input[last()]")).sendKeys(emailValue);
            return true;
        });
        return ;
    }

    public void textInputCountry( String countryValue){
        wait.until(d ->{
            driver.findElement(By.xpath("//span[@class='ui-select__value']")).click(); // гражданство
            driver.findElement(By.xpath("//span[contains(text(),'Не имею гражданства РФ')]")).click();
            driver.findElement(By.xpath("//input[@name='temp_non_resident_nationality']")).click();
            driver.findElement(By.cssSelector("[name='temp_non_resident_nationality']")).sendKeys(countryValue);
            return true;
        });
        return ;
    }

    // Методы для удаления значений из полей

    public void deleteValueName(){
        driver.findElement(By.cssSelector("input[name='fio']")).sendKeys(Keys.CONTROL, "a" , Keys.DELETE);
    }

    public void deleteValuePhone(){
        driver.findElement(By.name("phone_mobile")).sendKeys(Keys.CONTROL, "a" , Keys.DELETE);
    }

    //Нажатие на кнопки
    public void buttonOrderClick(){
        driver.findElement(By.xpath("//div[contains(text(),'Заказать')]/../../../button")).click();
    }

    public void buttonRequestClick(){
        driver.findElement(By.xpath("//div[contains(text(),'Оставить заявку')]/../../../button")).click();
    }

    public void  buttonOrderShipping(){
        wait.until(d ->{
            d.findElement(By
                    .xpath("//div[@class='UIAppointmentForm__button_xyTKE UIAppointmentForm__buttonPrimary_P00g7']//button[@type='button']"))
                    .isEnabled();
            return true;
        });
    }
    // метод для копирвоания

    public void CopyValueName(String value){
        StringSelection selection = new StringSelection(value);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        driver.findElement(By.cssSelector("input[name='fio']")).sendKeys(Keys.CONTROL, "v");
    }

    // кнопка нет, изменить
    public void changeCity(){
        driver.findElement(By. xpath("//span[contains(text(),'Нет, изменить') and @class='MvnoRegionConfirmation__option_v9PfP MvnoRegionConfirmation__optionRejection_1NrnL']")).click();
        return;
    }

    // метод для поиска города

    public boolean findCity(String city){
        boolean townCheck = false;
        List<WebElement> result = driver.findElements(By.xpath("//div[@class='MobileOperatorRegionsPopup__listParts_16aoL']/div/div"));

        System.out.println(result.size());
        for (int i=0; i<result.size(); i++)
        {
            String listitem = result.get(i).getAttribute("innerHTML");
            System.out.println(listitem);
            if(listitem.contains(city))
            {
                result.get(i).click();
                townCheck = true;
                break;
            }
        }
        return  townCheck;
    }

    // методы для работы с селекторами Интернет и Звонки

    public boolean selectInterntet(String value){
        wait.until(d -> {d.findElement(By
                .xpath("//span[contains(text(), 'Интернет')]/ancestor::div[@data-qa-file='UIDropdownField']"))
                .click();
            return this;
        });
        boolean selectChek = false;
        List<WebElement> result = driver.findElements
                (By.xpath("//div[1]/span[contains(text(), 'Интернет')]" +
                        "/ancestor::div[@data-qa-file='UIDropdownField']/div[@data-qa-file='UIDropdownList']/div"));

        System.out.println(result.size());
        for (int i=0; i<result.size(); i++)
        {
            String listitem = result.get(i).getAttribute("innerHTML");
            System.out.println(listitem);
            if(listitem.contains(value))
            {
                result.get(i).click();
                selectChek = true;
                break;
            }
        }
        return  selectChek;
    }

    public boolean selectPhoneCalls(String value){
        wait.until(d -> {d.findElement(By
                .xpath("//span[contains(text(), 'Звонки')]/ancestor::div[@data-qa-file='UIDropdownField']"))
                .click();
            boolean selectChek = false;
            List<WebElement> result = driver.findElements
                    (By.xpath("//div[1]/span[contains(text(), 'Звонки')]" +
                            "/ancestor::div[@data-qa-file='UIDropdownField']/div[@data-qa-file='UIDropdownList']/div"));

            System.out.println(result.size());
            for (int i=0; i<result.size(); i++)
            {
                String listitem = result.get(i).getAttribute("innerHTML");
                System.out.println(listitem);
                if(listitem.contains(value))
                {
                    result.get(i).click();
                    selectChek = true;
                    break;
                }
            }
            return  selectChek;

        });

       return true;
    }

    // чек боксы

    public MobilePage chekBoxConditions(boolean value){

        WebElement chekbox= driver.findElement(By.xpath("//label[@for='agreement']/..//../div[@tabindex]"));

        if(!(chekbox.getAttribute("innerHTML").contains("checked"))== value)
        {
            chekbox.click();
        }
        return this;
    }

    public MobilePage chekBoxModem(boolean value){

        WebElement chekbox= driver.findElement(By.xpath("//label[contains(text(),'Режим модема')]/../div"));

        if(!(chekbox.getAttribute("innerHTML").contains("checked"))== value)
        {
            chekbox.click();
        }
        return this;
    }

    public MobilePage chekBoxSMS(boolean value){

        WebElement chekbox= driver.findElement(By.xpath("//label[contains(text(),'Безлимитные СМС')]/../div"));

        if(!(chekbox.getAttribute("innerHTML").contains("checked"))== value)
        {
            chekbox.click();
        }
        return this;
    }

    public MobilePage chekBoxMessenger(boolean value){

        WebElement chekbox= driver.findElement(By.xpath("//label[contains(text(),'Мессенджеры')]/../div"));

        if(!(chekbox.getAttribute("innerHTML").contains("checked"))== value)
        {
            chekbox.click();
        }
        return this;
    }

    public MobilePage chekBoxSocialNetwork(boolean value){

        WebElement chekbox= driver.findElement(By.xpath("//label[contains(text(),'Социальные сети')]/../div"));

        if(!(chekbox.getAttribute("innerHTML").contains("checked"))== value)
        {
            chekbox.click();
        }
        return this;
    }

    public MobilePage chekBoxMusic(boolean value){
        WebElement chekbox= driver.findElement(By
                .xpath("//label[contains(text(),'Музыка')]/../div"));
        if(!(chekbox.getAttribute("innerHTML").contains("checked"))== value)
        {
            chekbox.click();
        }
        return this;
    }

    public MobilePage chekBoxVideo(boolean value){
        WebElement chekbox= driver.findElement(By
                .xpath("//label[contains(text(),'Видео')]/../div"));
        if(!(chekbox.getAttribute("innerHTML").contains("checked"))== value)
        {
            chekbox.click();
        }
        return this;
    }

    /// что делать с селктом со страной

    public void SelectCountry(String country){
        wait.until(d ->{
            driver.findElement(By
                    .xpath("//span[contains(text(),'Фамилия')]/../../../div/input[last()]).sendKeys(value)")).click();
            return true;
        });
        return ;
    }
    // title
    public void getTitleMobile(String titleMobile){
        wait.until(d -> {
            boolean tinkoffTitle = false;
            for (String title : driver.getWindowHandles()) {
                driver.switchTo().window(title);
                System.out.println(d.getTitle());
                tinkoffTitle = d.getTitle().equals(titleMobile);
            }
            return tinkoffTitle;
        });
    }

    public void refresh(){
        wait.until(d -> {
            d.navigate().refresh();
        return this;
        }
        );
    }

    // сравнение текста, сумм
    public void equalsDefolt(String value){
        wait.until(d ->{
            assertNotEquals(value,driver.findElement(By
                    .xpath("//h3[contains(text(),'Общая цена')]"))
                    .getText().replaceAll("[^0-9]", ""));
            return true;
        });
    }

    public void equalsMaxSum(String value){
        wait.until(d ->{
            assertEquals(value,driver.findElement(By
                    .xpath("//h3[contains(text(),'Общая цена')]"))
                    .getText().replaceAll("[^0-9]", ""));
            return  true;
        });
    }

    public void equalsCity(String cityName){
       wait.until(d -> {assertEquals(cityName,
               d.findElement(By.xpath("//div[@class='MvnoRegionConfirmation__title_DOqnW']")).getText());
           return true; });
    }



}


