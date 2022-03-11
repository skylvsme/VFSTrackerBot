package me.skylvs.vfsbot.vfs;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationChecker {

    @Value("${selenium.remote.host}")
    private String seleniumHost;

    @Value("${selenium.remote.port}")
    private String seleniumPort;

    @PostConstruct
    public void setupDrivers() {
        WebDriverManager.chromedriver().setup();
    }

    @SneakyThrows
    public ApplicationCheckEvent getApplicationStatus(String[] referenceNumberParts, String birthDate) {
        val seleniumURL = String.format("http://%s:%s", seleniumHost, seleniumPort);
        log.info("Using Selenium connect to {}", seleniumURL);

        val options = new ChromeOptions();

        val driver = new RemoteWebDriver(new URL(seleniumURL), options);

        driver.get("https://www.vfsvisaservicesrussia.com/poland-Russia-tracking_new/trackingParam.aspx?P=ri7FHohe3VirNKmyLaRu36t9/pEItw3gfYXFtDFlxVY=");

        driver.findElement(By.id("ctl00_CPH_txtR2Part1")).sendKeys(referenceNumberParts[0]);
        driver.findElement(By.id("ctl00_CPH_txtR2Part2")).sendKeys(referenceNumberParts[1]);
        driver.findElement(By.id("ctl00_CPH_txtR2Part3")).sendKeys(referenceNumberParts[2]);
        driver.findElement(By.id("ctl00_CPH_txtR2Part4")).sendKeys(referenceNumberParts[3]);

        driver.findElement(By.id("ctl00_CPH_txtDOB_txtDate")).sendKeys(birthDate);

        driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);

        driver.findElement(By.id("ctl00_CPH_btnDOB")).click();

        try {
            val result = driver.findElement(By.className("fnstatus")).getText();
            return new ApplicationCheckEvent(result, false);
        } catch (NoSuchElementException e) {
            val result = driver.findElement(By.id("ctl00_CPH_divError")).getText();
            return new ApplicationCheckEvent(result, true);
        } finally {
            driver.quit();
        }
    }

}
