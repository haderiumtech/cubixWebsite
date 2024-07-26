package cubixWebsitePages;

public class XpathClass {

	public final String CWurl = "https://www.cubix.co/";
	public final String HomePageTitle = "Software Development Company In USA | App Development | Cubix";

	// contact us page
	public final String CWContacturl = "https://www.cubix.co/contact/";
	public final String CWcontactname = "//input[@id='name']";
	public final String CWcontactemail = "//input[@id='email']";
	public final String CWcontactnumber = "//input[@id='number']";
	public final String CWcontactmessage = "//textarea[@id='message']";
	public final String CWcontactSubmitBtn = "//button/p[contains(text(),'Send Message')]";
	public final String CWcontactnamevalue = "Test";
	public final String CWcontactemailvalue = "test@yopmail.com";
	public final String CWcontactnumbervalue = "1234567890";
	public final String CWcontactmessagevalue = "Test email, please ignore!";

	// Got A Project? Pane
	public final String CWproject = "//button/span[contains(text(),'Get in touch')]";
	public final String CWprojectname = "//input[@id='name']";
	public final String CWprojectemail = "//input[@id='email']";
	public final String CWprojectnumber = "//input[@id='number']";
	public final String CWprojectdescription = "//textarea[@id='description']";
	public final String CWprojectcheckbox = "//input[@id='check']";
	public final String CWprojectSubmitBtn = "//button[contains(text(),'Send Message')]";
	public final String CWProjectThankYouUrl = "https://www.cubix.co/thank-you/";
	public final String CWprojectnamevalue = "Test";
	public final String CWprojectemailvalue = "test@yopmail.com";
	public final String CWprojectnumbervalue = "1234567890";
	public final String CWprojectdescriptionvalue = "Test email, please ignore!";
	public final String CWCookieAcceptBtn = "//button/span[contains(text(),'Accept All')]";
	public final String CWThankyouPage = "//h2[contains(text(),'Thank you.')]";
	public final String CWThankyouPageValue = "Thank you.";

	// career page xpaths
	public final String CWCareerPage = "https://www.cubix.co/careers/";
	public final String CWApplyNowCareer = "//button/span[contains(text(),'Apply Now')]";
	public final String CWCareername = "//input[@id='name']";
	public final String CWCareerEmail = "//input[@id='email']";
	public final String CWCareerNumber = "//input[@id='number']";
	public final String CWCareerDescription = "//textarea[@id='message']";
	public final String CWCareerResume = "//input[@id='resume']";
	public final String CWCareerSend = "//button[contains(text(),'Send Message')]";
	public final String CWTestFilePath = System.getProperty("user.dir") + "/src/test/resources/TestDocument.pdf";

	// Blog page
	public final String CWBlogUrl = "https://www.cubix.co/blog/";
	public final String CWBlogSearch = "//input[@id='default-search']";
	public final String CWBlogSearchBtn = "//button[contains(text(),'Search')]";
	public final String CWBlogSearchValue = "How Generative AI Can Help Build a Sustainable Future?";
	public final String CWBlogSearchResult = "//*[@id='recent-blogs']//article/h3[contains(text(),'How Generative AI Can Help Build a Sustainable Future?')]";
	public final String CWBlogSearchResultValue = "How Generative AI Can Help Build a Sustainable Future?";


	// Blog post page
	public final String CWBlogPostUrl = "https://www.cubix.co/blog/the-future-of-generative-ai/";
	public final String CWPostTitle = "The Future of Generative AI | Cubix Blogs";


}
