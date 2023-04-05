package CoreConfig;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.testng.ITestContext;
import org.testng.Reporter;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Class containing methods to set up browser types and configurations
 * 
 * @author jesse.childress
 *
 */
public abstract class CoreConfig {

	// Project URL
	private static String BASE_URL = ""; // Usually, project specific and will override this in lower level project.

	protected static WebDriver driver;

	/**
	 * Abstract Config Constructor
	 */
	public CoreConfig() {

	}

	/**
	 * Abstract Config Constructor accepts the address from the page inheriting this
	 * abstract config.
	 * 
	 * @param project_URL
	 */
	public CoreConfig(String project_URL) {
		BASE_URL = project_URL;
	}

	/**
	 * Loads the given page in a new browser instance
	 */
	public void loadPage() {
		driver = this.setDriver();
		launchAndConfigureBrowser(driver);
	}

	private void launchAndConfigureBrowser(WebDriver driver) {
		// Softer Asserter can go here
		// Timeout can go here
		setBrowserProperties(driver);
		loadPage(driver);
	}

	/**
	 * Loads a browser with the base URL as outlined in the
	 * 
	 * @param driver
	 */
	private static void loadPage(WebDriver driver) {
		driver.get(BASE_URL);
	}

	/**
	 * Sets properties in the browser, such as maximizing the screen size.
	 * 
	 * @param driver
	 */
	private static void setBrowserProperties(WebDriver driver) {
		driver.manage().window().maximize();
	}

	/**
	 * This method instantiates a WebDriver for use in the application.
	 * 
	 * @return WebDriver
	 */
	private WebDriver setDriver() {

		// Get the user-specified browser from the test suite xml file. If none listed,
		// we will go with the default
		String selectedBrowser = getSelectedBrowser();

		// Default browser in string if not user selected from xml test suite file.
		selectedBrowser = selectedBrowser != null ? selectedBrowser : "Chrome";

		// Switch statement to go through each browser and set properties, if need be.
		// See archived core classes for options, if need be.
		switch (selectedBrowser.toLowerCase()) {
		case "ie":
			WebDriverManager.iedriver().arch32().setup();

			// Ignoring Zoom Settings
			InternetExplorerOptions options = new InternetExplorerOptions();
			options.ignoreZoomSettings();
			driver = new InternetExplorerDriver(options);
			break;

		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;

		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;

		case "firefox":
		default:
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		return driver;
	}

	/**
	 * Gets the selected browser from the XML text file, e.g. FireFox, Chrome, Edge,
	 * etc. It uses it for picking a browser to test with.
	 * 
	 * @return String
	 */
	protected String getSelectedBrowser() {

		ITestContext testContext = Reporter.getCurrentTestResult().getTestContext();

		HashMap<String, String> parameters = new HashMap<String, String>(
				testContext.getCurrentXmlTest().getAllParameters());
		return parameters.get("selectedBrowser");

	}

}
