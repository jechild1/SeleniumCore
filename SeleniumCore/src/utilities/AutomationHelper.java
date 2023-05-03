package utilities;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import CoreConfig.CoreConfig;

/**
 * This class is the configuration class that is used by projects and is meant
 * to be inherited by them. Code that is at the CORE level is meant to be
 * non-specific to a project and able to be successfully shared with others.
 * 
 * @author jesse.childress
 *
 */
public class AutomationHelper extends CoreConfig {

	/**
	 * Basic @AfterClass method for staging a test
	 */
	public static void finishTest() {

		if (driver != null) {
			if (!driver.toString().contains("(null)")) {
				driver.quit();
			}
		}
	}

	/**
	 * Method to print the current method name.
	 */
	public static void printMethodName() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		// 0 = getStackTrace
		// 1 = this method name
		// 2 = method that calls this one

		StackTraceElement e = stacktrace[2];
		String methodName = e.getMethodName();

		Reporter.log("Method: " + methodName, true);
	}

	/**
	 * Sets a text field with the passed in text.
	 * <p>
	 * Note: This method also clears any text that may be in the existing field. if
	 * the clear() method doesn't work, it enters a while loop and uses Keys at
	 * select all and delete.
	 * 
	 * @param element
	 * @param text
	 */
	public static void setTextField(WebElement element, String text) {
		element.clear();

		while (!element.getAttribute(("value")).equals("")) {
			element.sendKeys(Keys.CONTROL + "A");
			element.sendKeys(Keys.DELETE);
		}

		element.sendKeys(text);

	}

	/**
	 * Returns the text of a given WebElement.
	 * <p>
	 * If the default Selenium getText() method doesn't work, this method will
	 * attempt to get the "value" attribute of the element.
	 * 
	 * @param element
	 * @return String
	 */
	public static String getText(WebElement element) {
		String text = null;
		text = element.getText();

		// Sometimes the getText() method doesn't pull back a value. When this happens,
		// lets try the .value property of the object.
		if (text.equals("")) {
			text = element.getAttribute("value");
		}
		return text;

	}

	/**
	 * Hits the <i>Enter</i> key on the keyboard on the passed in object.
	 * 
	 * @param element
	 */
	public static void hitEnter(WebElement element) {
		element.sendKeys(Keys.ENTER);
	}

	/**
	 * Returns the Page Title.
	 * 
	 * @return String
	 */
	public static String getPageTitle() {
		return driver.getTitle();
	}

	/**
	 * Method to cause the script to pause for a given amount of time, as indicated
	 * by the passed in parameter.
	 * 
	 * @param timeInSeconds
	 */
	public static void waitSeconds(int timeInSeconds) {
		// Convert to milliseconds
		timeInSeconds = timeInSeconds * 1000;

		try {
			Thread.sleep(timeInSeconds);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
