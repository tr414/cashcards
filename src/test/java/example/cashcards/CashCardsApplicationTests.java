package example.cashcards;

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

}
