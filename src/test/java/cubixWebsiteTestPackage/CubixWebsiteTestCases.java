package cubixWebsiteTestPackage;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import cubixWebsitePages.BaseClass;
import cubixWebsitePages.XpathClass;

public class CubixWebsiteTestCases {

	BaseClass base = new BaseClass();
	XpathClass CWxpath = new XpathClass();

	public WebDriver driver;
	public WebDriverWait wait;
	public ExtentReports extent;
	public ExtentTest test;

	@BeforeTest
	public void startChrome() {

		try {
			base.deleteOldScreenshots(); // Add this line to delete old screenshots
			base.setExtend();
			extent = base.extent;

		} catch (Exception e) {
			System.err.println("Error during setup: " + e.getMessage());
			throw new IllegalStateException("Failed to initialize reporting tools", e);
		}
	}

	@AfterTest
	public void CloseChrome() {

		try {
			base.CloseBrowser();
			base.closeExtentReports();

		} catch (Exception e) {
			System.err.println("Error during teardown: " + e.getMessage());
		}
	}

	@BeforeMethod
	public void setup() {
		driver = base.driver;
		base.gotoUrl(CWxpath.CWurl);
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		base.tearDown(result, driver, extent, test);
	}

	@Test(priority = 1)
	public void verifyHomepage() {

		test = extent.createTest("Assert Homepage");
		base.verifyText(CWxpath.HomePageTitle, base.getTitle());

	}

	@Test(priority = 2)
	public void verifyContactPage() throws InterruptedException {

		test = extent.createTest("Contact Page form submission");
		base.gotoUrl(CWxpath.CWContacturl);
		base.waitUntilDisplayed(CWxpath.CWCookieAcceptBtn);
		base.sendKeys(CWxpath.CWcontactname, CWxpath.CWcontactnamevalue);
		base.sendKeys(CWxpath.CWcontactemail, CWxpath.CWcontactemailvalue);
		base.sendKeys(CWxpath.CWcontactnumber, CWxpath.CWcontactnumbervalue);
		base.sendKeys(CWxpath.CWcontactmessage, CWxpath.CWcontactmessagevalue);

		base.forClick(CWxpath.CWcontactSubmitBtn);

		base.verifyText(base.getText(CWxpath.CWThankyouPage), CWxpath.CWThankyouPageValue);
		base.verifyCurrentUrl(base.getUrl(), CWxpath.CWProjectThankYouUrl);

	}

	@Test(priority = 3)
	public void verifyProjectForm() {

		test = extent.createTest("Got a project form submission");
		base.waitUntilDisplayed(CWxpath.CWCookieAcceptBtn);
		base.forClick(CWxpath.CWproject);
		base.sendKeys(CWxpath.CWprojectname, CWxpath.CWprojectnamevalue);
		base.sendKeys(CWxpath.CWprojectemail, CWxpath.CWprojectemailvalue);
		base.sendKeys(CWxpath.CWprojectnumber, CWxpath.CWprojectnumbervalue);
		base.sendKeys(CWxpath.CWprojectdescription, CWxpath.CWprojectdescriptionvalue);
		base.forClick(CWxpath.CWprojectcheckbox);
		base.forClick(CWxpath.CWprojectSubmitBtn);
		base.verifyText(base.getText(CWxpath.CWThankyouPage), CWxpath.CWThankyouPageValue);
		base.verifyCurrentUrl(base.getUrl(), CWxpath.CWProjectThankYouUrl);

	}
	
	@Test(priority = 4)
	public void searchFunctionality() {
		
		test = extent.createTest("Blog search functionality");
		base.gotoUrl(CWxpath.CWBlogUrl);
		base.waitUntilDisplayed(CWxpath.CWCookieAcceptBtn);
		//base.waitUntilDisplayed(CWxpath.CWBlogSearch);
		base.sendKeys(CWxpath.CWBlogSearch, CWxpath.CWBlogSearchValue);
		
		base.forClick(CWxpath.CWBlogSearchBtn);
		
		//base.waitUntilDisplayed(CWxpath.CWBlogFooter);
		base.scrollToPageEnd();
		base.scrollToElement(CWxpath.CWBlogSearchResult);
		base.verifyText(base.getText(CWxpath.CWBlogSearchResult), CWxpath.CWBlogSearchResultValue);
		
	}

	@Test(priority = 4)
	public void verifyCareerForm() {

		test = extent.createTest("Career form submission");
		// base.waitUntilDisplayed(CWxpath.CWCookieAcceptBtn);
		base.forClick(CWxpath.CWApplyNowCareer);

	}

}
