package testdata;

import models.*;

import java.util.List;

public class ApiTestData {
    private static Category category = new Category(1000L, "string1");
    private static List<String> photoUrls = List.of("string1");
    private static List<Tag> tags = List.of(new Tag(1000L, "string1"));;

    public static final Pet DEFAULT_PET = new Pet(1000L, category, "doggie", photoUrls, tags, Status.pending.toString());
    public static final Pet DEFAULT_PET_BUILDER = Pet.builder()
            .id(1000L)
            .category(category)
            .name("doggie")
            .photoUrls(photoUrls)
            .tags(tags)
            .status(Status.pending.toString())
            .build();

    public static final User DEFAULT_USER = new User(1000L, "user_1", "firstName", "lastName", "email@gmail.com", "qwerty", "12345678", 0L);
    public static final User DEFAULT_USER_BUILDER = User.builder()
            .id(1000L)
            .username("user_1")
            .firstName("firstName")
            .lastName("lastName")
            .email("email@gmail.com")
            .password("qwerty")
            .phone("12345678")
            .userStatus(0L)
            .build();

    public static final Items POLO_ITEM = Items.builder()
            .items(List.of(new Item("0041024928", 1, null)))
            .build();

    public static final Items SNEAKER_ITEM = Items.builder()
            .items(List.of(new Item("0038889846", 1, null)))
            .build();
}
