package me.skylvs.vfsbot.vfs;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ApplicationChecker {

    @PostConstruct
    public void setupDrivers() {
        WebDriverManager.firefoxdriver().setup();
    }

    public String getApplicationStatus(String[] referenceNumberParts, String birthDate) {
        val options = new FirefoxOptions();
        options.setBinary(new FirefoxBinary());
        options.setHeadless(true);

        val driver = new FirefoxDriver(options);

        driver.get("https://www.vfsvisaservicesrussia.com/poland-Russia-tracking_new/trackingParam.aspx?P=ri7FHohe3VirNKmyLaRu36t9/pEItw3gfYXFtDFlxVY=");

        driver.findElement(By.id("ctl00_CPH_txtR2Part1")).sendKeys(referenceNumberParts[0]);
        driver.findElement(By.id("ctl00_CPH_txtR2Part2")).sendKeys(referenceNumberParts[1]);
        driver.findElement(By.id("ctl00_CPH_txtR2Part3")).sendKeys(referenceNumberParts[2]);
        driver.findElement(By.id("ctl00_CPH_txtR2Part4")).sendKeys(referenceNumberParts[3]);

        driver.findElement(By.id("ctl00_CPH_txtDOB_txtDate")).sendKeys(birthDate);

        driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);

        driver.findElement(By.id("ctl00_CPH_btnDOB")).click();

        val result = driver.findElement(By.className("fnstatus")).getText();

        driver.quit();

        return result;
    }

}
