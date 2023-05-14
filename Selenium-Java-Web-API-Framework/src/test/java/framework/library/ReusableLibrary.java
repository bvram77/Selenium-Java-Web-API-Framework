package framework.library;

import framework.core.ExcelDataTable;
import framework.core.FrameworkParameters;
import framework.core.Settings;
import framework.selenium.SeleniumReport;
import org.openqa.selenium.WebDriver;

import java.util.Properties;


public abstract class ReusableLibrary
{
	/**
	 * The {@link ExcelDataTable} object (passed from the test script)
	 */
	protected ExcelDataTable dataTable;
	/**
	 * The {@link SeleniumReport} object (passed from the test script)
	 */
	protected SeleniumReport report;
	/**
	 * The {@link WebDriver} object (passed from the test script)
	 */
	protected WebDriver driver;
	/**
	 * The {@link ScriptHelper} object (required for calling one reusable library from another)
	 */
	protected ScriptHelper scriptHelper;
	
	/**
	 * The {@link Properties} object with settings loaded from the framework properties file
	 */
	protected Properties properties;
	/**
	 * The {@link FrameworkParameters} object
	 */
	protected FrameworkParameters frameworkParameters;
	
	
	/**
	 * Constructor to initialize the {@link ScriptHelper} object and in turn the objects wrapped by it
	 * @param scriptHelper The {@link ScriptHelper} object
	 */
	public ReusableLibrary(ScriptHelper scriptHelper)
	{
		this.scriptHelper = scriptHelper;
		
		this.dataTable = scriptHelper.getDataTable();
		this.report = scriptHelper.getReport();
		this.driver = scriptHelper.getDriver();
		
		properties = Settings.getInstance();
		frameworkParameters = FrameworkParameters.getInstance();
	}
}