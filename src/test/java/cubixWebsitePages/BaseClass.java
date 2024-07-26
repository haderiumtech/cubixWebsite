package cubixWebsitePages;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public WebDriver driver;
	public WebDriverWait wait;
	public JavascriptExecutor js;
	public ExtentReports extent;
	public ExtentTest test;

	public BaseClass() {

		setupDriver();
	}

	private void setupDriver() {
		try {
			WebDriverManager.chromedriver().setup();
			initializeDriver();
		} catch (Exception e) {
			System.err.println("Failed to download driver automatically: " + e.getMessage());
			// Attempting to use a pre-downloaded driver or specifying a version known to be
			// stable
			WebDriverManager.chromedriver().driverVersion("specific-version").setup();
			initializeDriver();
		}
	}

	private void initializeDriver() {
		ChromeOptions options = new ChromeOptions();
		//options.addArguments("--headless");
		options.addArguments("--disable-notifications");
		// arrugmentt call for allowing web pplication chrome update
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");

		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		js = (JavascriptExecutor) driver;
	}

	public void setExtend() {

		try {
			String reportDirPath = System.getProperty("user.dir") + "/test-output/Reports";
			String reportFileName = "CustomReport.html";
			String reportFilePath = reportDirPath + "/" + reportFileName;

			// Create the directory if it doesn't exist
			File reportDir = new File(reportDirPath);
			if (!reportDir.exists()) {
				reportDir.mkdirs();
			}

			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFilePath);
			sparkReporter.config().setDocumentTitle("Automation Report");
			sparkReporter.config().setReportName("Functional Report");
			sparkReporter.config().setTheme(Theme.STANDARD);

			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);
			// Use environment variable if available
			// extent.setSystemInfo("Hostname", System.getenv("HOSTNAME"));
			extent.setSystemInfo("Tester Name", "Ali Hammad");
			extent.setSystemInfo("Browser", "Chrome");

			// Log the path for debugging purposes
			System.out.println("Report Path:" + reportFilePath);
		} catch (Exception e) {
			System.err.println("Error setting up the Extent Reports: " + e.getMessage());
			throw new RuntimeException("Failed to set up Extent Reports", e);
		}

	}

	public void tearDown(ITestResult result, WebDriver driver, ExtentReports extent, ExtentTest test)
			throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, "Test Case Failed is " + result.getName());
			test.log(Status.FAIL, "Test Case Failed is " + result.getThrowable());
			String screenshotPath = getScreenShot(driver, result.getName());
			test.addScreenCaptureFromPath(screenshotPath);
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, "Test Case Skipped is " + result.getName());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, "Test Case Passed is " + result.getName());
		}
		extent.flush();
	}

	public String getScreenShot(WebDriver driver, String screenShotName) throws IOException {

		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		String destinationPath = System.getProperty("user.dir") + "/Screenshots/" + screenShotName + dateName + ".png";
		File finalDestination = new File(destinationPath);
		try {
			FileUtils.copyFile(source, finalDestination);
		} catch (IOException e) {
			System.err.println("Error taking screenshot: " + e.getMessage());
			throw e;
		}
		return destinationPath;

	}

	public void closeExtentReports() {
		if (extent != null) {
			extent.flush();
		}
	}

	public void testcaseName(String testName) {
		test = extent.createTest(testName);
	}

	public void gotoUrl(String url) {
		driver.get(url);
	}

	public void pageRefresh() {
		driver.navigate().refresh();
	}

	public void navigateback() {
		driver.navigate().back();
	}

	public void navigateforward() {
		driver.navigate().forward();
	}

	public void CloseBrowser() {
		if (driver != null) {
			driver.close();
			driver.quit();
		}
	}

	public String getTitle() {
		String title = driver.getTitle();
		return title;
	}

	public String getUrl() {
		String url = driver.getCurrentUrl();
		return url;
	}

	public WebElement findElement(String locator) {
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
		return element;

	}

	public void forClick(String locator) {
		WebElement element = this.findElement(locator);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator))).click();
		} catch (Exception e) {
			e.printStackTrace();
			js.executeScript("arguments[0].click();", element);
		}
	}

	public String getText(String locator) {
		String text = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator))).getText();
		return text;

	}

	public void sendKeys(String locator, String value) {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator))).sendKeys(value);
	}

	public void attributeContains(String locator, String attribute, String value) {
		WebElement element = this.findElement(locator);
		wait.until(ExpectedConditions.attributeContains(element, attribute, value));
	}

	public void verifySize(String locator, int expected) {
		int size = 0;
		try {
			size = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator))).size();
		} catch (Exception e) {
			e.printStackTrace();
			size = 0;
		}
		Assert.assertEquals(expected, size);
	}

	public String getCssValue(String attribute, String locator) {
		WebElement element = this.findElement(locator);
		String value = element.getCssValue(attribute);
		return value;
	}

	public void pressEnter(String locator) {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator))).sendKeys(Keys.ENTER);

	}

	public void byLinkText(String locator) {
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText(locator))).click();
	}

	public void verifyText(String expected, String actual) {
		Assert.assertEquals(expected, actual);
	}

	public void verifyCurrentUrl(String expected, String actual) {
		Assert.assertEquals(expected, actual);
	}

	public void verifyTrue(Boolean actual) {
		Assert.assertTrue(actual);
	}

	public void verifyFalse(Boolean actual) {
		Assert.assertFalse(actual);
	}

	public void waitUntilUrl(String url) {
		wait.until(ExpectedConditions.urlToBe(url));
	}

	public void waitUrlContains(String value) {
		wait.until(ExpectedConditions.urlContains(value));
	}

	public boolean waitUntilDisplayed(String locator) {
		boolean element;
		try {
			element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator))).isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
			element = false;
		}
		return element;
	}

	public void clear(String locator) {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator))).clear();
	}

	public void switchToIframeAndFindElement(String iframeLocator) {
		WebElement secondIframe = driver.findElement(By.xpath(iframeLocator));
		driver.switchTo().frame(secondIframe);
	}

	public void switchBackToContent() {
		driver.switchTo().defaultContent();
	}

	// Function to select an option from a dropdown by visible text
	public void selectDropdownByVisibleText(String locator, String visibleText) {
		WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		Select dropdown = new Select(dropdownElement);
		dropdown.selectByVisibleText(visibleText);
	}

	// Function to select an option from a dropdown by value
	public void selectDropdownByValue(String locator, String value) {
		WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		Select dropdown = new Select(dropdownElement);
		dropdown.selectByValue(value);
	}

	// Function to select an option from a dropdown by index
	public void selectDropdownByIndex(String locator, int index) {
		WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		Select dropdown = new Select(dropdownElement);
		dropdown.selectByIndex(index);
	}
	
	public void scrollToPageEnd() { 
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}
	
	public void scrollToElement() {
		//WebElement element = driver.findElement(By.xpath(locator));
		//js.executeScript("arguments[0].scrollIntoView(true);", element);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight * 2 / 3);");
	}
	
	public String getTextElement(WebElement elements, String locator) {
		String element;
		elements = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
		element = elements.getText();
		return element;
		
	}

	public void deleteOldScreenshots() {
		String screenshotsDirPath = System.getProperty("user.dir") + "/Screenshots";
		File screenshotsDir = new File(screenshotsDirPath);

		if (screenshotsDir.exists() && screenshotsDir.isDirectory()) {
			File[] files = screenshotsDir.listFiles();
			if (files != null) {
				for (File file : files) {
					try {
						Path filePath = Paths.get(file.getAbsolutePath());
						BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
						Instant fileTime = attr.creationTime().toInstant();
						Instant oneWeekAgo = Instant.now().minus(2, ChronoUnit.DAYS);

						if (fileTime.isBefore(oneWeekAgo)) {
							Files.delete(filePath);
						}
					} catch (IOException e) {
						System.err.println("Error deleting old screenshot: " + e.getMessage());
					}
				}
			}
		}
	}
}
