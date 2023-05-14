package framework.library;

import framework.core.ExcelDataTable;
import framework.selenium.SeleniumReport;
import org.openqa.selenium.WebDriver;


public class ScriptHelper
{
	private final ExcelDataTable dataTable;
	private final SeleniumReport report;
	private final WebDriver driver;
	
	/**
	 * Constructor to initialize all the objects wrapped by the {@link ScriptHelper} class
	 * @param dataTable The {@link ExcelDataTable} object
	 * @param report The {@link SeleniumReport} object
	 * @param driver The {@link WebDriver} object
	 */
	public ScriptHelper(ExcelDataTable dataTable, SeleniumReport report, WebDriver driver)
	{
		this.dataTable = dataTable;
		this.report = report;
		this.driver = driver;
	}
	
	/**
	 * Function to get the {@link ExcelDataTable} object
	 * @return The {@link ExcelDataTable} object
	 */
	public ExcelDataTable getDataTable()
	{
		return dataTable;
	}
	
	/**
	 * Function to get the {@link SeleniumReport} object
	 * @return The {@link SeleniumReport} object
	 */
	public SeleniumReport getReport()
	{
		return report;
	}
	
	/**
	 * Function to get the {@link WebDriver} object
	 * @return The {@link WebDriver} object
	 */
	public WebDriver getDriver()
	{
		return driver;
	}
}