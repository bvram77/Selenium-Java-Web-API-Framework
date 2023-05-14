package testscripts.scenario2;

import framework.core.IterationOptions;
import framework.core.Status;
import framework.library.DriverScript;
import framework.library.TestCase;
import framework.selenium.Browser;
import framework.selenium.WebDriverUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import testscripts.library.APILibrary;
import testscripts.library.FunctionalLibrary;


public class API_TC5 extends TestCase
{
	private APILibrary API_LIBRARY;
	
	@Test
	public void runAPI_TC5()
	{
		testParameters.setCurrentTestDescription("Test for book flight tickets and verify booking");
		testParameters.setIterationMode(IterationOptions.RunOneIterationOnly);
		testParameters.setBrowser(Browser.NoBrowser);
		driverScript = new DriverScript(testParameters);
		driverScript.driveTestExecution();
	}
	
	@Override
	public void setUp()
	{
		API_LIBRARY = new APILibrary(scriptHelper);
		report.addTestLogSection("Setup");
		report.updateTestLog("Invoke Application",
				"Invoke the application under test @ " + properties.getProperty("APIHostURL"),
				            Status.DONE);
	}
	
	@Override
	public void executeTest()
	{
		String response = API_LIBRARY.getResponse("https://demo.guru99.com",
				"V4/sinkministatement.php?CUSTOMER_ID=68195&PASSWORD=1234!&Account_No=1");
		report.updateTestLog("API RESPONSE", response, Status.DONE);
	}

	
	@Override
	public void tearDown()
	{
		report.addTestLogSection("Teardown");
	}
}