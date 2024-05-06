package example.cashcards;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

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

}
