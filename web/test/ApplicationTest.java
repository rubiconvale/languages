import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.Result;
import play.twirl.api.Content;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class ApplicationTest {

	private final String baseUri = "http://localhost:3333"; // base HTTP server uri
	private final int maxResponseTime = 5000; // expected response time

	@Test
	public void simpleCheck() {
		int a = 1 + 1;
		assertThat(a).isEqualTo(2);
	}

	@Test
	public void renderTemplate() {
		Content html = views.html.index.render("Your new application is ready.");
		assertThat(contentType(html)).isEqualTo("text/html");
		assertThat(contentAsString(html)).contains("Your new application is ready.");
	}

	@Test
	public void languagesNotNull() {
		running(fakeApplication(), new Runnable() {

			@Override
			public void run() {
				Result result = route(fakeRequest(GET, "/v1/languages"));

				assertThat(result).isNotNull();
			}
		});
	}

	@Test
	public void languageDialectsNotNull() {
		running(fakeApplication(), new Runnable() {

			@Override
			public void run() {
				Result result = route(fakeRequest(GET, "/v1/languages/de"));

				assertThat(result).isNotNull();
			}
		});
	}

	@Test
	public void alphaLanguages() {
		running(testServer(3333), new Runnable() {

			@Override
			public void run() {
				WSResponse response = WS.url(baseUri + "/v1/languages").get().get(maxResponseTime);

				assertAlphaLanguages(response, expectedAlphaLanguages());
			}
		});
	}

	private void assertAlphaLanguages(WSResponse response, Map<String, String> expectedAlphaLanguages) {
		assertLanguages(response, 200, 15, expectedAlphaLanguages);
	}

	@Test
	public void stableLanguages() {
		running(testServer(3333, fakeApplication(new HashMap<String, Object>() {{
			put("pics.env", "stable");
		}})), new Runnable() {

			@Override
			public void run() {
				WSResponse response = WS.url(baseUri + "/v1/languages").get().get(maxResponseTime);

				assertNonAlphaLanguages(response, expectedNonAlphaLanguages());
			}
		});
	}

	private void assertNonAlphaLanguages(WSResponse response, Map<String, String> expectedNonAlphaLanguages) {
		assertLanguages(response, 200, 12, expectedNonAlphaLanguages);
	}

	private void assertLanguages(WSResponse response, int httpStatusCode, int numberOfLanguages,
								 Map<String, String> expectedNonAlphaLanguages) {
		assertThat(response).isNotNull();
		assertThat(response.getStatus()).isEqualTo(httpStatusCode);

		JsonNode jsonResponse = response.asJson();

		assertThat(jsonResponse.findValues("id").size()).isEqualTo(numberOfLanguages);

		Iterator<JsonNode> nodes = jsonResponse.iterator();
		while (nodes.hasNext()) {
			JsonNode jsonNode = nodes.next();
			assertThat(expectedNonAlphaLanguages.keySet().contains(jsonNode.findValue("id").asText()));
			assertThat(expectedNonAlphaLanguages.values().contains(jsonNode.findValue("name").asText()));
		}
	}

	private Map<String, String> expectedAlphaLanguages() {
		return new HashMap<String, String>() {{

			putAll(expectedNonAlphaLanguages());
			put("da", "Dansk");
			put("hu", "Magyar");
			put("cs", "Čeština");

		}};
	}

	private Map<String, String> expectedNonAlphaLanguages() {
		return new HashMap<String, String>() {{

			put("de", "Deutsch");
			put("zh", "中文");
			put("no", "Norsk");
			put("fi", "Suomi");
			put("pl", "Polski");
			put("pt", "Português");
			put("sv", "Svenska");
			put("fr", "Français");
			put("en", "English");
			put("es", "Español");
			put("nl", "Nederlands");
			put("it", "Italiano");

		}};
	}
}
