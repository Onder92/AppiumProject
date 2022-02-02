import com.google.common.collect.ImmutableMap;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class StepImplementation extends BaseTest {

    Wait wait = new FluentWait(appiumDriver);

    Logger logger= LogManager.getLogger(StepImplementation.class);

    @Step("<time> saniye bekle")
    public void waitForseconds(int time) throws InterruptedException {
        Thread.sleep(time * 1000);
        logger.info("Bekleme yapildi");
    }

    @Step("id <> li elemente tıkla")
    public void clickByid(String id) {
        appiumDriver.findElement(By.id(id)).click();
        logger.info("Id'li elemente tiklandi");
    }

    @Step("xpath <> li elemente tıkla")
    public void clickByXpath(String xpath){ appiumDriver.findElement(By.xpath(xpath)).click();
    logger.info("Xpath'li elemente tiklandi");}

    @Step("id <id> li elementi bul ve <text> değerini yaz")
    public void sendKeysByid(String id, String text) {
        appiumDriver.findElement(By.id(id)).sendKeys(text);
        logger.info("Text kontrolu yapildi");
    }

    @Step("Android klavyeyi kapat")
    public void closeKeyboardonAndroid() {
        appiumDriver.hideKeyboard();
        logger.info("Klavye kapatildi");
    }

    @Step("Sayfayı sola kaydır")
    public void swipeLeft() {
        final int ANIMATION_TIME = 200; // ms

        final int PRESS_TIME = 200; // ms

        int edgeBorder = 10; // better avoid edges
        PointOption pointOptionStart, pointOptionEnd;
        // init screen variables
        Dimension dims = appiumDriver.manage().window().getSize();
        // init start point = center of screen
        pointOptionStart = PointOption.point(dims.width / 2, dims.height / 2);
        pointOptionEnd = PointOption.point(edgeBorder, dims.height / 2);
        new TouchAction(appiumDriver)
                .press(pointOptionStart)
                // a bit more reliable when we add small wait
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                .moveTo(pointOptionEnd)
                .release().perform();
        logger.info("Sayfa sola kaydirildi");
    }

    @Step("Sayfayı kaydir <x> <y> <endX> <endY> <duration>")
    public void scrollDown(int startX, int startY, int endX, int endY, int msDuration) {
        TouchAction touchAction = new TouchAction(appiumDriver);
        touchAction.press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(msDuration)))
                .moveTo(PointOption.point(endX, endY))
                .release();
        touchAction.perform();
        logger.info("Sayfa asagi kaydirildi");
    }

    @Step("id <id> li elementi bul ve <text> alanını kontrol et")
    public void textAreacontrol(String id, String text) {
        Assert.assertTrue("Element text değerini içermiyor", appiumDriver.findElement(By.id(id)).getText().contains(text));
        System.out.println("Sayfa baglantisi dogru");
        logger.info("Id'li sayfa baglantisi kontrol edildi");
    }

    @Step("xpath <xpath> li elementi bul ve <text> alanını kontrol et")
    public void textControlwithXpath(String xpath, String text) {
        Assert.assertTrue("Element text değerini içermiyor", appiumDriver.findElement(By.xpath(xpath)).getText().contains(text));
        System.out.println("Sayfa baglantisi dogru");
        logger.info("Xpath'li sayfa baglantisi kontrol edildi");
    }

    @Step("Android klavyeden arama tuşuna bas")
    public void clickSearchbuttonOnadnroidKeyboard() {
        ((JavascriptExecutor) appiumDriver).executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));
        logger.info("Android klavyede arama tusuna basildi");
    }

    @Step("Elementler <xpath> arasından rasgele bir tanesini seç ve tıkla")
    public void clickRandomelement(String xpath) {
        Random random = new Random();
        List<MobileElement> products = appiumDriver.findElements(By.xpath(xpath));
        int index = random.nextInt(products.size());
        products.get(index).click();
        logger.info("Rastgele element secildi");
    }

    @Step("Sayfayı doğrulamak için elementi <id> kontrol et")
    public boolean isElementVisible(String id) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Step("List içerisinde indexe tıkla <list>")
    public void clicktoIndex(String xpath) {
        List<MobileElement> products = appiumDriver.findElements(By.xpath(xpath));
        products.get(1).click();
        logger.info("Liste icerisinde indexe tiklandi");

    }
}