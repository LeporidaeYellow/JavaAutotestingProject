import io.qameta.allure.Description;
import io.restassured.path.json.JsonPath;
import models.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;

public class ExtendedAPITest {

    void postUserSerializationTest(User user) {
        String endpoint = "https://petstore.swagger.io/v2/user/";
        given().
                header("accept", "application/json").
                header("Content-Type", "application/json").
                body(user).
            when().
                post(endpoint).
            then().
                assertThat().
                statusCode(200);
    }

    void postPetSerializationTest(Pet pet) {
        String url = "https://petstore.swagger.io/v2/pet";
        given().
                header("accept", "application/json").
                header("Content-Type", "application/json").
                body(pet).
            when().
                post(url).
            then().
                assertThat().
                statusCode(200).
                header("content-type", "application/json");
    }

    @Description("DeserializationTest: get user")
    @Test
    void getUserDeserializationTest() {
        String username = "user_1";
        String endpoint = "https://petstore.swagger.io/v2/user/" + username;

        User expextedUser = new User(1000L, username, "firstName", "lastName", "email@gmail.com", "qwerty", "12345678", 0L);
        postUserSerializationTest(expextedUser);

        User actualUser = given().when().get(endpoint).as(User.class);
        assertThat(actualUser).usingRecursiveComparison().ignoringFields("id").isEqualTo(expextedUser);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actualUser.getFirstName()).isEqualTo(expextedUser.getFirstName());
        softly.assertThat(actualUser.getLastName()).isEqualTo(expextedUser.getLastName());
        softly.assertThat(actualUser.getUsername()).isEqualTo(expextedUser.getUsername());
        softly.assertThat(actualUser.getUserStatus()).isEqualTo(expextedUser.getUserStatus());
        softly.assertThat(actualUser.getEmail()).isEqualTo(expextedUser.getEmail());
        softly.assertThat(actualUser.getPassword()).isEqualTo(expextedUser.getPassword());
        softly.assertAll();
    }

    @Description("JsonPathTest: get pet")
    @Test
    void getPetJsonPathTest() {
        Long idPet = 1000L;
        String url = "https://petstore.swagger.io/v2/pet/";

        Category category = new Category(1000L, "string");
        List<String> photoUrls = List.of("string");
        List<Tag> tags = List.of(new Tag(1000L, "string"));;
        Pet expectPet = new Pet(idPet, category, "catty", photoUrls, tags, Status.pending.toString());
        postPetSerializationTest(expectPet);

        String jsonResponse =
        given().
                header("accept", "application/json").
                header("Content-Type", "application/json").
                body(expectPet).
            when().
                get(url + idPet).
            asString();

        JsonPath jsonPath = from(jsonResponse);

        SoftAssertions softAssert = new SoftAssertions();
        softAssert.assertThat(jsonPath.getLong("id")).isEqualTo(idPet);
        softAssert.assertThat(jsonPath.getString("name")).isEqualTo(expectPet.getName());
        softAssert.assertThat(jsonPath.getString("status")).isEqualTo(expectPet.getStatus());
    }

    @Description("JsonPathTest: get pets by status")
    @Test
    void getPetFindByStatusJsonPathTest() {
        String endpoint = "https://petstore.swagger.io/v2/pet/findByStatus";
        JsonPath jsonPath = given().
                header("accept", "application/json").
                queryParam("status", "available").
                when().
                get(endpoint).
                then()
                .extract()
                .jsonPath();

        List<Long> ids = jsonPath.getList("id", Long.class);
        List<String> names = jsonPath.getList("name", String.class);
        List<Pet> filteredIds = jsonPath.getList("findAll { it.id > 1000 }", Pet.class);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(filteredIds.get(0).getId()).isInstanceOf(Long.class);
        softly.assertThat(ids).allMatch(id -> id > 0L);
        softly.assertAll();
    }

    @Description("Json Schema Validation: user")
    @Test
    void jsonSchemaTest() {
        String username = "user_1";
        String endpoint = "https://petstore.swagger.io/v2/user/" + username;

        User expextedUser = new User(1000L, username, "firstName", "lastName", "email@gmail.com", "qwerty", "12345678", 0L);
        postUserSerializationTest(expextedUser);

        given().
            when().
                get(endpoint).
            then().
                assertThat().
                body(matchesJsonSchemaInClasspath("jsonSchema/userSchema.json"));
    }
}
