import configurations.TestConfig;
import controllers.CartController;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import models.Item;
import models.Items;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static testdata.ApiTestData.POLO_ITEM;
import static testdata.ApiTestData.SNEAKER_ITEM;

@Story("Auth API")
class AuthApiTests {
    CartController controller = new CartController();
    TestConfig config = new TestConfig();


    @Test
    @Description("Get token")
    void tokenTest() {
        assertThat(controller.getGuestToken().equals(config.getBasicAuth()));
    }

    @Test
    @Description("Add items in cart")
    void addItemTest() {
        int qtyBefore = controller.getBag().jsonPath().get("data.itemCount");
        assertThat(qtyBefore).isEqualTo(0);

        controller.addItems(POLO_ITEM);
        controller.addItems(SNEAKER_ITEM);
        int qtyAfter = controller.getBag().jsonPath().get("data.itemCount");

        assertThat(qtyAfter).isGreaterThan(qtyBefore);
    }

    @Test
    @Description("Edit (patch) items in cart")
    void editItemTest() {
        int qtyBefore = controller.getBag().jsonPath().get("data.itemCount");
        assertThat(qtyBefore).isEqualTo(0);

        controller.addItems(POLO_ITEM);
        String itemId = controller.getBag().jsonPath().get("data.items[0].itemId");
        Item updatedItem = POLO_ITEM.getItems().get(0);
        updatedItem.setItemId(itemId);
        updatedItem.setQuantity(2);
        Items updatedItems = Items.builder()
                .items(List.of(updatedItem))
                .build();

        controller.editItems(updatedItems);
        int qtyAfter = controller.getBag().jsonPath().get("data.itemCount");
        assertThat(qtyAfter).isEqualTo(2);
    }
}
