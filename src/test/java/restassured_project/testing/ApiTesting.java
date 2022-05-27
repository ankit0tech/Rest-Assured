package restassured_project.testing;

import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

import org.hamcrest.core.IsEqual;
//import org.hamcrest.core.IsEqual.*;

public class ApiTesting {
	
	ResponseSpecification spec = null;
	
	@BeforeClass
	public void setSpecification() {
		spec = RestAssured.expect();
		spec.contentType(ContentType.JSON);
		spec.statusCode(200);
		spec.statusLine("HTTP/1.1 200 OK");
		
		ExtentReportManager.createReport();
	}
	
	@Test
	public void getListOfUsers() {
		
		ExtentReportManager.test = ExtentReportManager.reports.startTest("getListOfUsers", "Gets the list of users");
		
		try {
			
			RestAssured.baseURI = "https://dummy.restapiexample.com/";
			ExtentReportManager.test.log(LogStatus.INFO, "configured the BASE URL", RestAssured.baseURI);
			
			ExtentReportManager.test.log(LogStatus.INFO, "Make an API call", "api/v1/employees");

			ExtentReportManager.test.log(LogStatus.INFO, "Expected Message --> ", "Successfully! All records has been fetched.");
			
			
			given().when().get("api/v1/employees").then().assertThat().body("message", IsEqual.equalTo("Successfully! All records has been fetched.")); 

		}
		catch(Exception ex) {
			ExtentReportManager.test.log(LogStatus.ERROR, "Exception occured !!!", ex.getMessage());
			ExtentReportManager.test.log(LogStatus.FAIL, "Test case failed !!!", ex.getMessage());
		}

	}
	
	@Test
	public void getOneUser() {
		
		ExtentReportManager.test = ExtentReportManager.reports.startTest("getOneUser", "Get one user");
		try {
			RestAssured.baseURI = "https://dummy.restapiexample.com/";
			ExtentReportManager.test.log(LogStatus.INFO, "configured the BASE URL", RestAssured.baseURI);
			ExtentReportManager.test.log(LogStatus.INFO, "Make an API call", "api/v1/employee/<id>");
			ExtentReportManager.test.log(LogStatus.INFO, "Employee ID ", "1");
			ExtentReportManager.test.log(LogStatus.INFO, "Expected Message --> ", "Successfully! Record has been fetched.");
			
			given().when().get("api/v1/employee/1").then().assertThat().body("message", IsEqual.equalTo("Successfully! Record has been fetched."));
		}
		catch(Exception ex) {
			ExtentReportManager.test.log(LogStatus.ERROR, "Exception occured !!!", ex.getMessage());
			ExtentReportManager.test.log(LogStatus.FAIL, "Test case failed !!!", ex.getMessage());
		}

	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void createUser() {
		
		ExtentReportManager.test = ExtentReportManager.reports.startTest("createUser", "create one user");
		
		try {
			JSONObject params = new JSONObject(); 
			params.put("name", "test101"); 
			params.put("age", "23");
			params.put("salary", "12000");
			ExtentReportManager.test.log(LogStatus.INFO, "JSON object creation done", params.toJSONString());
			
			RestAssured.baseURI = "https://dummy.restapiexample.com/";
			ExtentReportManager.test.log(LogStatus.INFO, "configured the BASE URL", RestAssured.baseURI);
			
			ExtentReportManager.test.log(LogStatus.INFO, "Making POST API call", "api/v1/create");
			
			given().header("Content-Type", "application/json").body(params.toJSONString()).post("api/v1/create")
			.then().assertThat().body("message", IsEqual.equalTo("Successfully! Record has been added."));
		}
		catch (Exception ex) {
			ExtentReportManager.test.log(LogStatus.ERROR, "Exception occured !!!", ex.getMessage());
			ExtentReportManager.test.log(LogStatus.FAIL, "Test case failed !!!", ex.getMessage());
		}

		
	}
	
	
	@Test
	public void deleteUser() {
		ExtentReportManager.test = ExtentReportManager.reports.startTest("deleteUser", "delete one user");
		
		try {
			RestAssured.baseURI = "https://dummy.restapiexample.com/";
			ExtentReportManager.test.log(LogStatus.INFO, "configured the BASE URL", RestAssured.baseURI);
			
			ExtentReportManager.test.log(LogStatus.INFO, "Making POST API call", "api/v1/create");
			ExtentReportManager.test.log(LogStatus.INFO, "Employee ID ", "1");
			ExtentReportManager.test.log(LogStatus.INFO, "Expected Message -> ", "successfully! deleted Records");

			given().when().delete("api/v1/delete/1").then().assertThat().body("message", IsEqual.equalTo("successfully! deleted Records"));
		}
		catch (Exception ex) {
			ExtentReportManager.test.log(LogStatus.ERROR, "Exception occured !!!", ex.getMessage());
			ExtentReportManager.test.log(LogStatus.FAIL, "Test case failed !!!", ex.getMessage());
		}

	}
	
	
	@Test
	public void updateUser() {
		ExtentReportManager.test = ExtentReportManager.reports.startTest("updateUser", "update one user");

		try {
			RestAssured.baseURI = "https://dummy.restapiexample.com/";
			ExtentReportManager.test.log(LogStatus.INFO, "configured the BASE URL", RestAssured.baseURI);
			
			ExtentReportManager.test.log(LogStatus.INFO, "Making POST API call", "api/v1/update/<id>");
			ExtentReportManager.test.log(LogStatus.INFO, "Employee ID ", "1");
			ExtentReportManager.test.log(LogStatus.INFO, "Expected Message -> ", "Successfully! Record has been updated.");

			given().when().put("api/v1/update/1").then().assertThat().body("message", IsEqual.equalTo("Successfully! Record has been updated."));
		}
		catch (Exception ex) {
			ExtentReportManager.test.log(LogStatus.ERROR, "Exception occured !!!", ex.getMessage());
			ExtentReportManager.test.log(LogStatus.FAIL, "Test case failed !!!", ex.getMessage());
		}

	}	
	
	
	@AfterClass
	public void endReport() {
		
		ExtentReportManager.reports.flush();
	}
	
	
}










