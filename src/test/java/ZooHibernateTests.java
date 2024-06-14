import database.DatabaseUtils;
import database.hibernate.DBHibernateService;
import database.hibernate.models.Animal;
import database.hibernate.models.Places;
import database.hibernate.models.Workman;
import io.qameta.allure.Description;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

class ZooHibernateTests {
    DBHibernateService dbHibernateService = new DBHibernateService();

    @BeforeAll
    static void init() {
        DatabaseUtils.createData();
    }

    /**
     * В таблице public.animal ровно 10 записей
     */
    @Description("Entities equals 10")
    @Test
    void countRowAnimal() {
        Assertions.assertEquals(10, dbHibernateService.getCountRowAnimal());
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
    @ParameterizedTest
    @MethodSource("animalProvider")
    void insertIndexAnimal(Animal animal) {
        assertThrows(PersistenceException.class, () -> dbHibernateService.insertAnimal(animal));
    }

    /**
     * В таблицу public.workman нельзя добавить строку с name = null
     */
    @Description("Can not add Entity with null name")
    @Test
    void insertNullToWorkman() {
        Workman workman = new Workman();
        workman.setId(88);
        workman.setName(null);
        workman.setAge(12);
        workman.setPosition(1);
        assertThrows(PersistenceException.class, () -> dbHibernateService.insertWorkman(workman));
    }

    /**
     * Если в таблицу public.places добавить еще одну строку, то в ней будет 6 строк
     */
    @Description("If add Entity to table then rows will count equal 6")
    @Test
    void insertPlacesCountRow() {
        int sizeBefore = dbHibernateService.getCountRowPlaces();
        Places places = new Places();
        places.setId(6);
        places.setRow(1);
        places.setPlace_num(185);
        places.setName("Загон 1");
        dbHibernateService.insertPlaces(places);
        Assertions.assertEquals(sizeBefore + 1, dbHibernateService.getCountRowPlaces());
    }

    /**
     * В таблице public.zoo всего три записи с name 'Центральный', 'Северный', 'Западный'
     */
    @Description("Table got threee Entities with names: Центральный, Северный, Западный")
    @Test
    void countRowZoo() {
        List<String> expectedNames = Arrays.asList("Центральный", "Северный", "Западный");

        int actualZooCountRow = dbHibernateService.getCountRowZoo();
        Assertions.assertEquals(3, actualZooCountRow);

        List<String> actualNames = dbHibernateService.getZooNameData();
        assertThat(actualNames, containsInAnyOrder(expectedNames.toArray()));
    }

    /**
     * Обновление полной информации о животном чере поик по имени
     *  проверка что количество животных не изменилось
     */
    @Description("Update animal by name and check count animals in DB table")
    @Test
    void updateAnimal() {
        Animal animalOne = new Animal();
        animalOne.setId(111);
        animalOne.setName("Помидорка");
        animalOne.setAge(1);
        animalOne.setSex(1);
        animalOne.setType(1);
        animalOne.setPlace(1);

        dbHibernateService.insertAnimal(animalOne);
        assertEquals(111, dbHibernateService.getAnimalByName("Помидорка").get().getId());
        assertEquals( 11, dbHibernateService.getCountRowAnimal());

        Animal animalTwo = dbHibernateService.getAnimalByName("Помидорка").get();
        animalTwo.setName("Ромашка");
        animalTwo.setAge(23);
        animalOne.setAge(2);
        animalOne.setSex(2);
        animalOne.setType(2);
        animalOne.setPlace(2);

        dbHibernateService.updateAnimalById(animalTwo);
        assertEquals(111, dbHibernateService.getAnimalByName("Ромашка").get().getId());
        assertEquals( 11, dbHibernateService.getCountRowAnimal());
    }

    /**
     * Добавление и удаление животного в таблицу
     */
    @Description("Insert and then delete animal by name and checkout animals by id in DB table")
    @Test
    void deleteAnimal() {
        Animal animal = new Animal();
        animal.setId(111);
        animal.setName("Помидорка");
        animal.setAge(1);
        animal.setSex(1);
        animal.setType(1);
        animal.setPlace(1);

        dbHibernateService.insertAnimal(animal);
        assertEquals(111, dbHibernateService.getAnimalByName("Помидорка").get().getId());
        assertEquals( 11, dbHibernateService.getCountRowAnimal());

        dbHibernateService.deleteAnimal(animal);
        assertTrue(dbHibernateService.getAnimalByName("Помидорка").isEmpty());
        assertEquals( 10, dbHibernateService.getCountRowAnimal());
    }

    /**
     * В таблицу public.animal обновляем от индекса 1 до 10 включительно одним и тем же объектом
     */
    @Description("Update all Animal data by same object")
    @Test
    void updateIndexAnimal() {
        animalProvider().forEach(animal -> dbHibernateService.updateAnimalById(animal));

        dbHibernateService.getAnimals().stream().forEach(
                animal -> assertTrue(animal.getName().equals("Sharik"))
        );
    }
}
