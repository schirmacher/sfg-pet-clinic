package guru.springframework.sfgpetclinic.webdriver;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumTest {

    @Test
    public void testEditOwner() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "../chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/owners/1/edit");

        Thread.sleep(1000);  // Let the user actually see something!
        WebElement firstName = driver.findElement(By.name("firstName"));
        firstName.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        firstName.sendKeys("John");
        Thread.sleep(1000);  // Let the user actually see something!
        firstName.submit();
        Thread.sleep(1000);  // Let the user actually see something!

        String result = driver.findElement(By.cssSelector("b")).getText();
        assertEquals(result, "John Weston");

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] data = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            outputStream.write(data);
            String filename = "target/screenshot1.png";
            final FileOutputStream fos = new FileOutputStream(filename);
            fos.write(outputStream.toByteArray());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver.quit();
    }
}