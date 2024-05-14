import io.qameta.allure.Description;
import models.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.greaterThan;

public class SimpleAPITests {
    @Description("Post pet")
    @Test
    void postPetTest() {
        Long idPet = 1000L;
        String petName = "doggie";
        String url = "https://petstore.swagger.io/v2/pet";
        Category category = new Category(1000L, "string");
        List<String> photoUrls = List.of("string");
        List<Tag> tags = List.of(new Tag(1000L, "string"));;
        Pet pet = new Pet(idPet, category, petName, photoUrls, tags, Status.available.toString());

        given().
                header("accept", "application/json").
                header("Content-Type", "application/json").
                body(pet).
            when().
                post(url).
            then().
                assertThat().
                statusCode(200).
                header("content-type", "application/json").
                body("name", equalTo("doggie")).
                body("id", equalTo(1000)).
                body("status", equalTo("available"));
    }

    @Description("Update by pet ID")
    @Test
    void updatePetByIdTest() {
        postPetTest();

        Integer idPet = 1000;
        String body = "name=DUFFY";
        String url = "https://petstore.swagger.io/v2/pet/";

        given().
                accept("application/json").
                contentType("application/x-www-form-urlencoded").
                body(body).
            when().
                post(url+idPet).
            then().
                statusCode(200);

        given().
                header("accept", "application/json").
            when().
                get(url + idPet).
            then().
                assertThat().
                statusCode(200).
                header("content-type", "application/json").
                body("name", equalTo("DUFFY"));
    }

    @Description("Put pet")
    @Test
    void putPetTest() {
        Long idPet = 1000L;
        String url = "https://petstore.swagger.io/v2/pet";
        Category category = new Category(1000L, "string");
        List<String> photoUrls = List.of("string");
        List<Tag> tags = List.of(new Tag(1000L, "string"));;
        Pet pet = new Pet(idPet, category, "catty", photoUrls, tags, Status.pending.toString());

        given().
                header("accept", "application/json").
                header("Content-Type", "application/json").
                body(pet).
            when().
                put(url).
            then().
                assertThat().
                statusCode(200).
                header("content-type", "application/json").
                body("name", equalTo("catty")).
                body("status", equalTo("pending"));
    }

    @Description("Get pet by status where is available")
    @Test
    void getPetFindByStatusIsAvailableTest() {
        String url = "https://petstore.swagger.io/v2/pet/findByStatus?status=available";

        given().
                header("accept", "application/json").
            when().
                get(url).
            then().
                assertThat().
                statusCode(200).
                header("content-type", "application/json").
                body("id", everyItem(notNullValue())).
                body("status", everyItem(equalTo("available"))).
                body("size()", greaterThan(2));
    }

    @Description("Get pet by ID")
    @Test
    void getPetFindByIdTest() {
        postPetTest();

        Integer idPet = 1000;
        String url = "https://petstore.swagger.io/v2/pet/";

        given().
                header("accept", "application/json").
            when().
                get(url + idPet).
            then().
                assertThat().
                statusCode(200).
                header("content-type", "application/json").
                body("id", equalTo(idPet)).
                body("status", equalTo("available"));
    }

    @Description("Delete by pet ID")
    @Test
    void deletePetByIdTest() {
        postPetTest();

        Integer idPet = 1000;
        String url = "https://petstore.swagger.io/v2/pet/";

        given().
                accept("application/json").
            when().
                delete(url+idPet).
            then().
                statusCode(200);

        given().
                header("accept", "application/json").
            when().
                get(url + idPet).
            then().
                assertThat().
                statusCode(404).
                header("content-type", "application/json");
    }
}
