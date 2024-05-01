import controllers.PetController;
import controllers.UserController;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import models.Pet;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static testdata.ApiTestData.DEFAULT_USER;
import static testdata.ApiTestData.DEFAULT_PET;


@Feature("ControllerTests")
@Tag("api")
class ControllerApiTests {
    UserController userController = new UserController();
    PetController petController = new PetController();


    @BeforeEach
    @AfterEach
    void clear() {
        userController.deleteUserByName(DEFAULT_USER.getUsername());
        petController.deletePetByName(DEFAULT_PET.getId());
    }

    @Test
    @Tag("smoke")
    @DisplayName("Check add user is returns 200 status ok")
    void checkAddUserTest() {
        Response response = userController.addDefaultUser();
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @Tag("smoke")
    @DisplayName("Check add pet is returns 200 status ok")
    void checkAddPetTest() {
        Response response = petController.addDefaultPet();
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @Tag("extended")
    @DisplayName("Check user added correctly")
    void checkAddUserExtendedTest() {
        Response addUserResponse = userController.addUser(DEFAULT_USER);
        long expectedId = Long.parseLong(addUserResponse.jsonPath().getString("message"));
        assertThat(addUserResponse.statusCode()).isEqualTo(200);

        Response getUserResponse = userController.getUserByName(DEFAULT_USER.getUsername());
        User actualUser = getUserResponse.as(User.class);

        assertThat(getUserResponse.statusCode()).isEqualTo(200);
        assertThat(actualUser.getId()).isEqualTo(expectedId);
        assertThat(actualUser).usingRecursiveComparison().ignoringFields("id").isEqualTo(DEFAULT_USER);
    }

    @Test
    @Tag("extended")
    @DisplayName("Check pet added correctly")
    void checkAddPetExtendedTest() {
        Response addPetResponse = petController.addPet(DEFAULT_PET);
        long expectedId = Long.parseLong(addPetResponse.jsonPath().getString("id"));
        assertThat(addPetResponse.statusCode()).isEqualTo(200);

        Response getPetResponse = petController.getPetById(expectedId);
        Pet actualPet = getPetResponse.as(Pet.class);

        assertThat(addPetResponse.statusCode()).isEqualTo(200);
        assertThat(actualPet.getId()).isEqualTo(expectedId);
        assertThat(actualPet).usingRecursiveComparison().ignoringFields("id").isEqualTo(DEFAULT_PET);
    }

}
