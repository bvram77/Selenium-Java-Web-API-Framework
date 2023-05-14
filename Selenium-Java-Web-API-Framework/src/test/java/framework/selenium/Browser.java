package framework.selenium;

public enum Browser {
    IE("internet explorer"),
    Firefox("firefox"),
    FirefoxHeadLess("firefox"),
    Chrome("chrome"),
    ChromeHeadLess("chrome"),
    Edge("MicrosoftEdge"),
    EdgeHeadLess("MicrosoftEdge"),
    NoBrowser("No Browser");
  
	private final String value;

    private Browser(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
