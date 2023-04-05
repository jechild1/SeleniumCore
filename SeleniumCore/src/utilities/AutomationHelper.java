package utilities;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import CoreConfig.CoreConfig;


/**
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
		// New line
		Reporter.log("", true);
	}

	public static void setTextField(WebElement element, String text) {
		element.clear();
		
		while(!element.getAttribute(("value")).equals("")) {
			element.sendKeys(Keys.CONTROL + "A");
			element.sendKeys(Keys.DELETE);
		}
		
		element.sendKeys(text);
		

	}

	public static String getText(WebElement element) {
		String text = null;
		text = element.getText();

		// Sometimes the getText() method doesn't pull back a value. When this happens,
		// lets try the .value property of the object.
		if(text.equals("")) {
			text = element.getAttribute("value");
		}
		return text;
		
	}
	
	public static void waitSeconds(int timeInSeconds) {
		//Convert to milliseconds
		timeInSeconds = timeInSeconds * 1000;
		
		try {
			Thread.sleep(timeInSeconds);
			
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
