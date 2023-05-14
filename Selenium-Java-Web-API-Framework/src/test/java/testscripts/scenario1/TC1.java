package testscripts.scenario1;

import framework.core.IterationOptions;
import framework.core.Status;
import framework.library.DriverScript;
import framework.library.TestCase;
import framework.selenium.Browser;
import testscripts.library.FunctionalLibrary;
import org.testng.annotations.Test;

public class TC1 extends TestCase
{
	private FunctionalLibrary functionalLibrary;
	
	@Test()
	public void runTC1()
	{
		testParameters.setCurrentTestDescription("Test for login with valid user credentials");
		testParameters.setIterationMode(IterationOptions.RunOneIterationOnly);
		testParameters.setBrowser(Browser.Chrome);
		driverScript = new DriverScript(testParameters);
		driverScript.driveTestExecution();
	}
	
	@Override
	public void setUp()
	{
		functionalLibrary = new FunctionalLibrary(scriptHelper);
		report.addTestLogSection("Setup");
		driver.get(properties.getProperty("ApplicationUrl"));
		report.updateTestLog("Invoke Application", "Invoke the application under test @ " +
								properties.getProperty("ApplicationUrl"), Status.DONE);
	}
	
	@Override
	public void executeTest()
	{
		functionalLibrary.login();
		functionalLibrary.verifyLoginValidUser();
		functionalLibrary.logout();
	}
	
	@Override
	public void tearDown()
	{
		// Nothing to do
	}
}