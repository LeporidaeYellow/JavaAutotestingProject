import database.Animal;
import database.DatabaseUtils;
import database.CRUDUtils;
import database.Places;
import database.jdbc.DatabaseConnection;
import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

class ZooJdbcTests {

    @BeforeAll
    static void init() {
        DatabaseUtils.createData();
    }

    @AfterAll
    static void tearDown() {
        DatabaseConnection.closeConnection();
    }

    /**
     * В таблице public.animal ровно 10 записей
     */
    @Description("Entities equals 10")
    @Test
    void checkRowCountForAnimal() throws SQLException {
        int actualAnimalCountRow = CRUDUtils.getRowCountByTable("animal");
        Assertions.assertEquals(10, actualAnimalCountRow);
    }

    static Stream<Animal> animalProvider() {
        List<Animal> animals = new ArrayList<>();
        for (int id = 1; id <= 10; id++) {
            Animal animal = new Animal();
            animal.setId(id);
            animal.setName("Sharik");
            animal.setAge(10);
            animal.setType(1);
            animal.setSex(1);
            animal.setPlace(1);
            animals.add(animal);
        }
        return animals.stream();
    }

    /**
     * В таблицу public.animal нельзя добавить строку с индексом от 1 до 10 включительно
     */
    @Description("Can not add Entity with exist id")
    @ParameterizedTest
    @MethodSource("animalProvider")
    void insertIndexAnimal(Animal animal) {
        int countBefore = CRUDUtils.getAnimalCountRow();
        Throwable exception = assertThrows(Exception.class, () -> CRUDUtils.insertAnimalData(animal));
        org.assertj.core.api.Assertions.assertThat(exception.getMessage()).contains("PRIMARY KEY ON PUBLIC.ANIMAL(ID)");
        int countAfter = CRUDUtils.getAnimalCountRow();
        assertEquals(countBefore, countAfter);
    }

    /**
     * В таблицу public.workman нельзя добавить строку с name = null
     */
    @Description("Can not add Entity with null name")
    @Test
    void insertNullToWorkman() {
        Assertions.assertFalse(CRUDUtils.insertWorkmanData());
        System.out.println(CRUDUtils.insertWorkmanData());
    }

    /**
     * Если в таблицу public.places добавить еще одну строку, то в ней будет 6 строк
     */
    @Description("If add Entity to table then rows will count equal 6")
    @Test
    void insertPlacesCountRow() throws SQLException {
        Places place = new Places(6, 1, 185, "Загон 1");
        CRUDUtils.getPlacesCountRow();
        System.out.println(CRUDUtils.insertPlacesData(place));
        Assertions.assertEquals(6, CRUDUtils.getPlacesCountRow());
    }

    /**
     * В таблице public.zoo всего три записи с name 'Центральный', 'Северный', 'Западный'
     */
    @Description("Table got three Entities with names: Центральный, Северный, Западный")
    @Test
    void countRowZoo() throws SQLException {
        List<String> expectedNames = Arrays.asList("Центральный", "Северный", "Западный");

        int actualZooCountRow = CRUDUtils.getRowCountByTable("zoo");
        Assertions.assertEquals(3, actualZooCountRow);

        List<String> actualNames = CRUDUtils.getZooNameData();
        assertThat(actualNames, containsInAnyOrder(expectedNames.toArray()));
    }

    /**
     * Обновление полной информации о животном чере поик по имени
     *  проверка что количество животных не изменилось
     */
    @Description("Update animal by name and check count animals in DB table")
    @Test
    void updateAnimal() {
        String preName = "Абрикос";
        Animal animal = new Animal(0, "Помидорка", 1, 1, 1, 1);

        int preId = CRUDUtils.getAnimalId(preName);
        CRUDUtils.updateAnimalDataByName(animal, preName);

        assertEquals(preId, CRUDUtils.getAnimalId("Помидорка"));
        assertEquals(10, CRUDUtils.getAnimalCountRow());
    }

    /**
     * Добавление и удаление животного в таблицу
     */
    @Description("Insert and then delete animal by name and checkout animals by id in DB table")
    @Test
    void deleteAnimal() {
        Animal animal = new Animal(111, "Помидорка", 1, 1, 1, 1);
        CRUDUtils.insertAnimalData(animal);
        assertEquals(111, CRUDUtils.getAnimalId("Помидорка"));

        CRUDUtils.deleteAnimalDataByName("Помидорка");
        assertEquals(-1, CRUDUtils.getAnimalId("Помидорка"));
    }

    /**
     //     * В таблицу public.animal обновляем от индекса 1 до 10 включительно одним и тем же объектом
     //     */
    @Description("Update all Animal data by same object")
    @Test
    void updateIndexAnimal() {
        animalProvider().forEach(animal -> CRUDUtils.updateAnimalDataById(animal, animal.getId()));

        animalProvider().forEach(
                animal -> assertTrue(animal.getName().equals("Sharik"))
        );
    }
}