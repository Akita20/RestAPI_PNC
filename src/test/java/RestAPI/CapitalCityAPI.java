package RestAPI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


public class CapitalCityAPI {
	Properties prop=new Properties();
	String baseURI = "https://restcountries.com";
	JsonPath jsonpath=null;
	Response response=null;

	@BeforeClass
	public void init() {
		RestAssured.baseURI=baseURI;
		try {
			FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"/TestData.properties");
			prop.load(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@BeforeMethod
	public void getAbitRest() throws InterruptedException {
		Thread.sleep(1000);
	}

	// Test1: positive test for city name
	@Test
	public void testWithCityName_pstTest() {
//		RestAssured.baseURI=baseURI;
		
		response=given()
				.pathParam("name", prop.getProperty("cityName_pos"))
				.when()
				.get("/v3.1/name/{name}")
				.then()
				.extract().response();
		
		jsonpath=response.jsonPath();
		
		Assert.assertTrue(jsonpath.get("capital").toString().contains(prop.getProperty("cptByCityName_pos")));
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(prop.getProperty("statusCode_pos")));	

	}
	// Test2: negative test for city name
	@Test
	public void testWithCityName_ngtTest() {
		
		response=given()
				.pathParam("name", prop.getProperty("cityName_neg"))
				.when()
				.get("/v3.1/name/{name}")
				.then()
				.extract().response();
		
		jsonpath=response.jsonPath();
		
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(prop.getProperty("statusCode_neg")));	
	}

	// Test3: positive test for city code
	@Test
	public void testWithCityCode_pstTest() {
		response=given()
				.pathParam("code", prop.getProperty("cityCode_pos"))
				.when()
				.get("/v3.1/alpha/{code}")
				.then()
				.extract().response();
		
		jsonpath=response.jsonPath();
		
		Assert.assertTrue(jsonpath.get("capital").toString().contains(prop.getProperty("cptByCityCode_pos")));
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(prop.getProperty("statusCode_pos")));	
		
	}

	// Test4: negative test for city code
	@Test
	public void testWithCityCode_ngtTest() {
		response=given()
				.pathParam("code", prop.getProperty("cityCode_neg"))
				.when()
				.get("/v3.1/name/{code}")
				.then()
				.extract().response();
		
		jsonpath=response.jsonPath();
		
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(prop.getProperty("statusCode_neg")));	
	}
}
