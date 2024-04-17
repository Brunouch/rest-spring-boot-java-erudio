package bruno.spring.java.integrationsTests.swagger;

import static io.restassured.RestAssured.given;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import bruno.spring.java.configs.TestsConfigs;
import bruno.spring.java.integrationsTests.testsContainers.AbstractIntegrationTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTests extends AbstractIntegrationTest {

	@Test
	public void shouldDisplaySwaggerUiPage() {
		var content = 
		given().basePath("/swagger-ui/index.html")
		.port(TestsConfigs.SERVER_PORT)
		.when().get().then().statusCode(200)
		.extract().body().asString();

		assertTrue(content.contains("Swagger UI"));
	}

}
