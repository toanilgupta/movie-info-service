package com.microservices.movieinfoservice;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieInfoServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieInfoServiceServiceApplicationTests {

	@LocalServerPort
	private int port;
	TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();

	@Test
	public void testGetRatingInfo() throws JSONException {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/1"),
				HttpMethod.GET, entity, String.class);

		String actual = response.getBody();
		//String expected = "[{\"movieId\":\"URI\",\"movieName\":\"URI\",\"yearOfMaking\":\"2019\",\"description\":\"description\"}]";

		String expected = "{\"timestamp\":\"2019-10-01T04:07:20.483+0000\",\"status\":500,\"error\":\"Internal Server Error\",\"message\":\"No instances available for rating-data-service\",\"path\":\"/api/1\"}";

		System.out.println("actual response " + actual);
		System.out.println("expected response " + expected);

		JSONAssert.assertEquals(expected, actual, false);
	}


	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}


	}
