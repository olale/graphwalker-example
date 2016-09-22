package com.company.helper;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by krikar on 2015-02-01.
 */
public class Helper {
	private static final Logger log = LoggerFactory.getLogger(Helper.class);

	/**
	 * Random number generator. Will be used to create random data used for
	 * input in test.
	 */
	static private Random random = new Random(System.currentTimeMillis());

	/**
	 * Timeout time in seconds used for waiting for element(s) to show up.
	 */
	final static int timeOut = 10;

	/**
	 * Generates a random number with 1 to max digits.
	 *
	 * @param max
	 *            Maximum length of digits.
	 * @return The random number
	 */
	public static int getRandomInt(int max) {
		return random.nextInt(max) + 1;
	}

	/**
	 * Creates an instance of the Firefox WebDriver.
	 */
	private static class WebDriverHolder {
		private static final WebDriver INSTANCE = new ChromeDriver();
	}

	/**
	 * If not already created, creates the singleton webdriver object.
	 *
	 * @return the singleton webdriver object
	 */
	public static WebDriver getInstance() {
		return WebDriverHolder.INSTANCE;
	}

	/**
	 * Will wait for a specified web element to appear. If not found an
	 * assertion will fail.
	 *
	 * @param by
	 *            The description of the element
	 * @return The matching element if found.
	 */
	public static WebElement WaitForElement(final By by) {
		Wait<WebDriver> stubbornWait = new FluentWait<WebDriver>(getInstance())
				.withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
		getInstance().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		return stubbornWait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(by);
			}
		});
		// for (int second = 0; ; second++) {
		// if (second >= timeOut) {
		// Assert.fail("Timeout occurred while waiting for: " + by.toString());
		// }
		// try {
		// return getInstance().findElement(by);
		// } catch (Exception e1) {
		// log.debug(e1.getMessage());
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e2) {
		// log.debug(e2.getMessage());
		// }
		// }
		// }
	}

	/**
	 * Will wait for a specified web element(s) to appear. If not found an
	 * assertion will fail.
	 *
	 * @param by
	 *            The description of the element
	 * @return A list of matching element(s) if found.
	 */
	public static List<WebElement> WaitForElements(final By by) {
		Wait<WebDriver> stubbornWait = new FluentWait<WebDriver>(getInstance())
				.withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);

		return stubbornWait.until(new Function<WebDriver, List<WebElement>>() {
			public List<WebElement> apply(WebDriver driver) {
				return driver.findElements(by);
			}
		});
		// for (int second = 0;; second++) {
		// if (second >= timeOut) {
		// Assert.fail("timeout");
		// }
		// List<WebElement> elements = null;
		// try {
		// elements = getInstance().findElements(by);
		// return elements;
		// } catch (Exception e) {
		// }
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// }
		// }
	}
}
