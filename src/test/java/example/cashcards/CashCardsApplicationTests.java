package example.cashcards;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import  org.assertj.core.api.Assert;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CashCardsApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	void shouldReturnACashCardWhenDataIsSaved() {
		// save the response from a get request made to the URI "/cashcards/99".
		// The response type is expected to be a String.class
		ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/99", String.class);

		// ensure that the request is successful and does not result in an error
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void shouldReturnACashCardWithCorrectId() {
		// save the response from a get request made to the URI "/cashcards/99".
		// The response type is expected to be a String.class
		ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/99", String.class);

		// ensure that the request is successful and does not result in an error
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		// read the response body as a JSON
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Number id = documentContext.read("$.id");

		// ensure that the id of the object that is returned is what we expect it to be (99 in this case)
		assertThat(id).isEqualTo(99);
	}

	@Test
	void shouldNotReturnACashCardWithUnknownId() {
		ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/1000", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();
		// the below test fails when we run it. What is the difference between isBlank and isEmpty?
		// assertThat(response.getBody()).isEmpty();
	}

	@Test
	void shouldCreateCashCard() {
		CashCard newCashCard = new CashCard(null, 250.0);
		// create a post request to the cashcards endpoint with the newCashCard provided. The response body will not
		// contain anything, hence the final argument is Void.class
		ResponseEntity<Void> response = restTemplate.postForEntity("/cashcards", newCashCard, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfNewCashCard = response.getHeaders().getLocation();
		ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewCashCard, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		// check to verify that the card that is returned by the Get request to the uri provided in the
		// POST request response is valid and has the same amount as what was provided in the test
		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number id = documentContext.read("$.id");
		Double amount = documentContext.read("$.amount");

		assertThat(id).isNotNull();
		assertThat(amount).isEqualTo(250.0);
	}

	@Test
	void shouldReturnAllCashCardsWhenListIsRequested() {
		ResponseEntity<String> response = restTemplate.getForEntity("/cashcards", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		int cashCardCount = documentContext.read("$.length()");
		assertThat(cashCardCount).isEqualTo(3);

		JSONArray ids = documentContext.read("$..id");
		//assertThat(ids).containsExactlyInAnyOrder(99, 100, 101);
	}

}
