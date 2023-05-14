package testscripts.scenario1;

import framework.core.IterationOptions;
import framework.library.DriverScript;
import framework.library.TestCase;
import framework.selenium.Browser;
import org.testng.annotations.Test;


public class Template extends TestCase
{
	@Test(enabled = false)
	public void runTemplate()
	{
		/**** Modify the test parameters as required  **************/
		/** set your test case description */
		testParameters.setCurrentTestDescription("Write your Test Case Description");
		/** set your iteration mode and if only one iteration then use "IterationOptions.RunOneIterationOnly" */
		testParameters.setIterationMode(IterationOptions.RunOneIterationOnly);
		/** if you are planning to decide in between iteration then use "IterationOptions.RunRangeOfIterations" */
		//testParameters.setIterationMode(IterationOptions.RunRangeOfIterations);
		//testParameters.setStartIteration(1);
		//testParameters.setEndIteration(5);
		/** if you are planning to run all iteration then use "IterationOptions.RunAllIterations" */
		//testParameters.setIterationMode(IterationOptions.RunAllIterations);
		/** chooese your browser to run */
		testParameters.setBrowser(Browser.Edge);
		/** if you are using API testing then use "Browser.NoBrowser" */
		//testParameters.setBrowser(Browser.NoBrowser);
		/** initialize Driver script ****/
		driverScript = new DriverScript(testParameters);
		driverScript.driveTestExecution();
	}
	
	@Override
	public void setUp()
	{
		// report.addTestLogSection("Setup");	// Uncomment this line if you implement the setUp() function
	}
	
	@Override
	public void executeTest()
	{
		// Specify the main test logic here
	}
	
	@Override
	public void tearDown()
	{
		// report.addTestLogSection("Teardown");	// Uncomment this line if you implement the tearDown() function
	}
}