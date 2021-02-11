import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestRestAssured {

	static String CLOTHING_SELECTED = "swimmers";
//	static String CLOTHING_SELECTED ="wo0llen";

	@BeforeClass
	/*
	 * public static void beforeAll() { System.out.println("BEFOREALL");
	 * CLOTHING_SELECTED ="swimmers"; }
	 */

	/*
	 * Method will get the current temperature from openweathermap and test the
	 * dress selection
	 */
	@Test
	public void testValidClothing() {

		float current_temp = 0;
		RequestSpecification request = null;
		Response response = null;
		try {
			RestAssured.baseURI = "https://api.openweathermap.org/data/2.5/";
			RestAssured.port = 443;
			request = RestAssured.given();

			response = request.queryParam("q", "Delhi").queryParam("units", "imperial")
					.queryParam("appid", "75170b60658128b2e289b9351ff3ae56").when().get("/weather").then().extract()
					.response();

		} catch (Exception ex) {

			ex.printStackTrace();
		}

		String jsonString = response.asString();
		System.out.println(jsonString);

		Object obj = response.getBody().jsonPath().get("main");

		if (obj != null) {

			HashMap<String, Object> hmap = (HashMap<String, Object>) response.getBody().jsonPath().get("main");

			System.out.println("jsonpath" + hmap.get("temp"));

			try {

				current_temp = Float.valueOf(hmap.get("temp").toString());

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		System.out.println("Current Clothing  Selected     " + CLOTHING_SELECTED);
		System.out.println("Current Temperature     " + current_temp);

		if (current_temp > 0 && current_temp < 20) {

			Assert.assertTrue(CLOTHING_SELECTED.contentEquals("woollen"));
		} else if (current_temp > 0 && current_temp > 20) {

			Assert.assertTrue(CLOTHING_SELECTED.contentEquals("swimmers"));

		} else {

			Assert.assertTrue(false);
		}

		Assert.assertEquals(true, true);
	}
}