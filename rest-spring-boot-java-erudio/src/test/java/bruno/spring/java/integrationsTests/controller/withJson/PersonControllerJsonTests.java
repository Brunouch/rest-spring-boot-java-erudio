package bruno.spring.java.integrationsTests.controller.withJson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static io.restassured.RestAssured.given;

import bruno.spring.java.configs.TestsConfigs;
import bruno.spring.java.integrationsTests.testsContainers.AbstractIntegrationTest;
import bruno.spring.java.integrationsTests.vo.PersonVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerJsonTests extends AbstractIntegrationTest {
    
    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static PersonVO person;

    @BeforeAll
    public static void setup(){
        
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        person = new PersonVO();
        
    }

    @Test
    @Order(1)
    public void TestCreate() throws JsonMappingException, JsonProcessingException{
        mockPerson();

        specification = new RequestSpecBuilder()
        .addHeader(TestsConfigs.HEADER_PARAM_ORIGIN, TestsConfigs.ORIGIN_ERUDIO)
        .setBasePath("/api/person/v1").setPort(TestsConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();
        var content = given().spec(specification)
        .contentType(TestsConfigs.CONTENT_TYPE_JSON)
        .body(person).when().post().then()
        .statusCode(200).extract().body().asString();

        PersonVO createdPerson = objectMapper.readValue(content, PersonVO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());

        assertTrue(createdPerson.getId() > 0);
        assertEquals("Richard", createdPerson.getFirstName());
        assertEquals("Stallman", createdPerson.getLastName());
        assertEquals("New York City, New York, US", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());

    }

    @Test
    @Order(2)
    public void TestCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException{
        mockPerson();

        specification = new RequestSpecBuilder()
        .addHeader(TestsConfigs.HEADER_PARAM_ORIGIN, TestsConfigs.ORIGIN_SEMERU)
        .setBasePath("/api/person/v1").setPort(TestsConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();
        var content = given().spec(specification)
        .contentType(TestsConfigs.CONTENT_TYPE_JSON)
        .body(person).when().post().then()
        .statusCode(403).extract().body().asString();

        assertNotNull(content);
        assertEquals("Invalid CORS request", content);

    }

    @Test
    @Order(3)
    public void TestFindById() throws JsonMappingException, JsonProcessingException{
        mockPerson();

        specification = new RequestSpecBuilder()
        .addHeader(TestsConfigs.HEADER_PARAM_ORIGIN, TestsConfigs.ORIGIN_ERUDIO)
        .setBasePath("/api/person/v1").setPort(TestsConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();
        var content = given().spec(specification)
        .contentType(TestsConfigs.CONTENT_TYPE_JSON)
        .pathParam("id",person.getId() ).when().get("{id}").then()
        .statusCode(200).extract().body().asString();

        PersonVO persistendPerson = objectMapper.readValue(content, PersonVO.class);
        person = persistendPerson;

        assertNotNull(persistendPerson.getId());
        assertNotNull(persistendPerson.getFirstName());
        assertNotNull(persistendPerson.getLastName());
        assertNotNull(persistendPerson.getAddress());
        assertNotNull(persistendPerson.getGender());

        assertTrue(persistendPerson.getId() > 0);
        assertEquals("Richard", persistendPerson.getFirstName());
        assertEquals("Stallman", persistendPerson.getLastName());
        assertEquals("New York City, New York, US", persistendPerson.getAddress());
        assertEquals("Male", persistendPerson.getGender());
    }

    @Test
    @Order(4)
    public void TestFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException{
        mockPerson();

        specification = new RequestSpecBuilder()
        .addHeader(TestsConfigs.HEADER_PARAM_ORIGIN, TestsConfigs.ORIGIN_SEMERU)
        .setBasePath("/api/person/v1").setPort(TestsConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();
        var content = given().spec(specification)
        .contentType(TestsConfigs.CONTENT_TYPE_JSON)
        .pathParam("id",person.getId() ).when().get("{id}").then()
        .statusCode(403).extract().body().asString();

        assertNotNull(content);
        assertEquals("Invalid CORS request", content);
    }


    private void mockPerson() {
        person.setFirstName("Richard");
        person.setLastName("Stallman");
        person.setAddress("New York City, New York, US");
        person.setGender("Male");
    }

    
}
