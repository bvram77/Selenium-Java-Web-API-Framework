package testscripts.library;

import framework.core.Status;
import framework.library.ReusableLibrary;
import framework.library.ScriptHelper;
import framework.selenium.WebDriverUtil;
import org.openqa.selenium.By;

public class KeyWordLibrary extends ReusableLibrary{
	
	WebDriverUtil browser = new WebDriverUtil(driver);
	
	public KeyWordLibrary(ScriptHelper scriptHelper) {
		super(scriptHelper);
		
	}
	
	public void click(By by) {
		driver.findElement(by).click();
		report.updateTestLog("[Click]", "Cick on object ..[" + by.toString() + "]", Status.DONE);
	}
	
	public void set(By by, String value) {
		driver.findElement(by).sendKeys(value);
		report.updateTestLog("[Set]", "Cick on object value..[" + value + "]", Status.DONE);
	}

	

}
