package testscripts.library;

import framework.library.DriverScript;
import framework.library.ReusableLibrary;
import framework.library.ScriptHelper;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import javax.net.ssl.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import static io.restassured.RestAssured.given;

public class APILibrary extends ReusableLibrary
{
	/**
	 * Constructor to initialize the functional library
	 * @param scriptHelper The {@link ScriptHelper} object passed from the {@link DriverScript}
	 */
	public APILibrary(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	public String getResponse(String hostUrl, String requestUrl){
		RestAssured.baseURI = hostUrl;
		Response response = given().config(RestAssuredConfig.newConfig().sslConfig(new SSLConfig()
				            .relaxedHTTPSValidation())).when().get(requestUrl).then().extract().response();
		return response.getBody().prettyPrint();
	}
	
	public int postRequest(String hostUrl, String requestUrl, String strJSON){
		RestAssured.baseURI = hostUrl;
		Response response = given().config(RestAssuredConfig.newConfig().sslConfig(new SSLConfig()
						    .relaxedHTTPSValidation())).contentType(ContentType.JSON)
				            .accept(ContentType.JSON).body(strJSON).when().post(requestUrl);
		return response.getStatusCode();
	}

	public String readJSON(String fileName) throws Exception {
		return readFileAsString(fileName);
	}

	public String readFileAsString(String file)throws Exception{
		return new String(Files.readAllBytes(Paths.get(file)));
	}

	public void addX509Certificate(){
		TrustManager[] certs = new TrustManager[] {
				new X509TrustManager()	{
					@Override
					public X509Certificate[] getAcceptedIssuers() {	return null;}
					@Override
					public void checkServerTrusted(X509Certificate[] chain, String authType)
							throws CertificateException { }
					@Override
					public void checkClientTrusted(X509Certificate[] chain, String authType)
							throws CertificateException	{ }
				}
		};
		SSLContext ctx = null;
		try	{
			ctx = SSLContext.getInstance("TLS");
			ctx.init(null, certs, new SecureRandom());
		}
		catch (java.security.GeneralSecurityException ex) {	}
		if (ctx != null)	{
			HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
		}
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier()	{
			public boolean verify(String hostname, SSLSession session)
			{
				return true;
			}
		});
	}

}