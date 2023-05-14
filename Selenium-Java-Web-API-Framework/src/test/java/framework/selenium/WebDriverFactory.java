package framework.selenium;

import framework.core.FrameworkException;
import framework.core.Settings;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class WebDriverFactory
{		
  private static Properties properties;

  public static void initDriverLocation(Properties properties){
    System.setProperty("firefox.bin.path", properties.getProperty("FirefoxBinaryPath"));
    System.setProperty("webdriver.gecko.driver", properties.getProperty("FirefoxDriverPath"));
    System.setProperty("webdriver.chrome.driver", properties.getProperty("ChromeDriverPath"));
    System.setProperty("webdriver.ie.driver", properties.getProperty("IEDriverPath"));
    System.setProperty("webdriver.edge.driver", properties.getProperty("EdgeDriverPath"));
  }

  public static WebDriver getDriver(Browser browser)  {
    WebDriver driver = null;
    Boolean maximize = true;
    DesiredCapabilities caps = new DesiredCapabilities();
    properties = Settings.getInstance();
    boolean proxyRequired = Boolean.parseBoolean(properties.getProperty("ProxyRequired"));

    initDriverLocation(properties);

    switch (browser) {
      case IE:
        driver = new InternetExplorerDriver(withRemoteEdgeIEModeOption());
        break;
      case Firefox:
        driver = new FirefoxDriver(withFirefoxProfile(caps));
        break;
      case FirefoxHeadLess:
        maximize = false;
        driver = new FirefoxDriver(withHeadLessFirefoxOptions());
        break;
      case Chrome:
        driver = new ChromeDriver(withChromeOptions());
        break;
      case ChromeHeadLess:
        maximize = false;
        driver = new ChromeDriver(withHeadLessChromeOptions());
        break;
      case Edge:
        driver = new EdgeDriver(withEdgeOptions());
        break;
      case EdgeHeadLess:
        driver = new EdgeDriver(withHeadLessEdgeOptions());
        break;
      case NoBrowser:
        return null;
    default:
      throw new FrameworkException("Unhandled browser!");
    }
    if (driver != null && maximize){
      driver.manage().window().maximize();
    }
    return driver;
  }

  public static WebDriver getDriver(Browser browser, String remoteUrl){
    return getDriver(browser, null, null, remoteUrl);
  }

  public static WebDriver getDriver(Browser browser, String browserVersion, Platform platform, String remoteUrl)  {
    properties = Settings.getInstance();
    WebDriver driver = null;
    DesiredCapabilities caps = new DesiredCapabilities();
    boolean proxyRequired = Boolean.parseBoolean(properties.getProperty("ProxyRequired"));
    Boolean maximize = true;
    URL url;
    
    if (browser.equals(Browser.IE)) {
      caps = caps.merge(withRemoteEdgeIEModeOption());
    } else if (browser.equals((Browser.Firefox))) {
      caps = caps.merge(withFirefoxCapabilities(caps));
    } else if (browser.equals(Browser.FirefoxHeadLess)){
      caps = caps.merge(withFirefoxCapabilities(caps));
    } else if (browser.equals(Browser.Chrome)){
      caps = caps.merge(withChromeOptions());
    } else if (browser.equals(Browser.ChromeHeadLess)){
      maximize = false;
      caps = caps.merge(withHeadLessChromeOptions());
    } else if (browser.equals(Browser.Edge)){
      caps = caps.merge(withEdgeOptions());
    } else if (browser.equals(Browser.EdgeHeadLess)){
      caps = caps.merge(withHeadLessEdgeOptions());
    }

    caps.setBrowserName(browser.getValue());
    caps.setJavascriptEnabled(true);

    try    {
      url = new URL(remoteUrl);
    } catch (MalformedURLException e)  {
      e.printStackTrace();
      throw new FrameworkException("The specified remote URL is malformed");
    }

    driver = new RemoteWebDriver(url, caps);

    if (driver != null && maximize){
      driver.manage().window().maximize();
    }
    return driver;
  }

  private static DesiredCapabilities getProxyCapabilities()  {
    Proxy proxy = new Proxy();
    proxy.setProxyType(Proxy.ProxyType.MANUAL);
    properties = Settings.getInstance();
    String proxyUrl = properties.getProperty("ProxyHost") + ":" +  properties.getProperty("ProxyPort");
    proxy.setHttpProxy(proxyUrl);
    proxy.setFtpProxy(proxyUrl);
    proxy.setSslProxy(proxyUrl);
    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    desiredCapabilities.setCapability("proxy", proxy);
    return desiredCapabilities;
  }

  private static InternetExplorerOptions withRemoteEdgeIEModeOption(){
    InternetExplorerOptions options = new InternetExplorerOptions();
    options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
    options.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, UnexpectedAlertBehaviour.IGNORE);
    options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
    options.withEdgeExecutablePath(properties.getProperty("EdgeBrowserPath"));
    options.attachToEdgeChrome();
    return options;
  }

  private static DesiredCapabilities withFirefoxCapabilities(DesiredCapabilities caps){
    FirefoxOptions fOptions = new FirefoxOptions();
    FirefoxProfile fProfile;
    Object obj = caps.getCapability(FirefoxDriver.Capability.PROFILE);
    if (obj != null && obj instanceof FirefoxProfile) {
      fProfile = (FirefoxProfile) obj;
    } else {
      fProfile = new FirefoxProfile();
    }
    fProfile = addFFProfile(fProfile);
    String binPath = System.getProperty("firefox.bin.path");
    if (binPath != null && !binPath.isEmpty()){
      fOptions.setBinary(binPath);
    }
    caps.setCapability(FirefoxDriver.Capability.PROFILE, fProfile);
    caps.setCapability("moz:firefoxOptions", fOptions);
    caps.setCapability("marionette", true);
    fOptions.merge(caps);
    return caps;
  }

  private static FirefoxOptions withFirefoxProfile(DesiredCapabilities caps) {
    FirefoxOptions fOptions = new FirefoxOptions();
    FirefoxProfile fProfile;
    Object obj = caps.getCapability(FirefoxDriver.Capability.PROFILE);
    if (obj != null && obj instanceof FirefoxProfile) {
      fProfile = (FirefoxProfile) obj;
    } else {
      fProfile = new FirefoxProfile();
    }
    fProfile = addFFProfile(fProfile);
    String binPath = System.getProperty("firefox.bin.path");
    if (binPath != null && !binPath.isEmpty()){
      fOptions.setBinary(binPath);
    }
    caps.setCapability(FirefoxDriver.Capability.PROFILE, fProfile);
    caps.setCapability("moz:firefoxOptions", fOptions);
    caps.setCapability("marionette", true);
    fOptions.merge(caps);
    return fOptions;
  }

  private static FirefoxProfile addFFProfile(FirefoxProfile fProfile){
    fProfile = new FirefoxProfile();
    fProfile.setPreference("network.negotiate-auth.delegation-uris", "http://,https://");
    fProfile.setPreference("network.negotiate-auth.trusted-uris", "http://,https://");
    fProfile.setPreference("network.auth.force-generic-ntlm", true);
    return fProfile;
  }

  private static FirefoxOptions withHeadLessFirefoxOptions(){
    FirefoxOptions options = new FirefoxOptions();
    options.addArguments("--remote-allow-origins=*", "--headless", "--disable-gpu", "--window-size=1366,768");
    return options;
  }

  private static ChromeOptions withChromeOptions(){
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--remote-allow-origins=*");
    options.addArguments("--start-maximized");
    options.addArguments("test-type");
    return options;
  }

  private static ChromeOptions withHeadLessChromeOptions(){
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--remote-allow-origins=*", "--headless", "--disable-gpu", "--window-size=1366,768");
    return options;
  }

  private static EdgeOptions withEdgeOptions(){
    EdgeOptions options = new EdgeOptions();
    options.addArguments("--remote-allow-origins=*");
    options.addArguments("--start-maximized");
    options.addArguments("test-type");
    return options;
  }

  private static EdgeOptions withHeadLessEdgeOptions(){
    EdgeOptions options = new EdgeOptions();
    options.addArguments("--remote-allow-origins=*", "--headless", "--disable-gpu", "--window-size=1366,768");
    return options;
  }
}
