package database;

import database.jdbc.DatabaseConnection;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CRUDUtils {
    private static final String INSERT_ANIMAL = "INSERT INTO public.animal (id, \"name\", age, \"type\", sex, place) VALUES(?, ?, ?, ?, ?, ?);\n";
    private static final String GET_COUNT_ANIMAL_ROW = "SELECT count(*) from animal";
    private static final String UPDATE_ANIMAL_BY_ID = "UPDATE public.animal SET \"name\"=?, age=?, \"type\"=?, sex=?, place=? WHERE id=?;\n";
    private static final String SELECT_ANIMAL_BY_NAME = "SELECT * FROM public.animal WHERE \"name\"=?;\n";
    private static final String DELETE_ANIMAL_BY_ID = "DELETE FROM public.animal WHERE id=?;\n";


    private static final String INSERT_WORKMAN = "INSERT INTO public.workman (id, \"name\", age, \"position\") VALUES(1, null, 23, 1);\n";
    private static final String INSERT_PLACES = "INSERT INTO public.places (id, \"row\", place_num, \"name\") VALUES(?, ?, ?, ?);\n";
    private static final String GET_COUNT_PLACES_ROW = "SELECT count(*) from places";


    public static List<Animal> getAnimalData(String query) {
        List<Animal> animals = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                int type = resultSet.getInt("type");
                int sex = resultSet.getInt("sex");
                int place = resultSet.getInt("place");

                animals.add(new Animal(id, name, age, type, sex, place));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return animals;
    }

    @SneakyThrows
    public static void insertAnimalData(Animal animal) {
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ANIMAL);
        preparedStatement.setInt(1, animal.getId());
        preparedStatement.setString(2, animal.getName());
        preparedStatement.setInt(3, animal.getAge());
        preparedStatement.setInt(4, animal.getType());
        preparedStatement.setInt(5, animal.getSex());
        preparedStatement.setInt(6, animal.getPlace());
        preparedStatement.executeUpdate();
    }

    public static boolean insertWorkmanData() {
        try {
            Connection connection = DatabaseConnection.createConnection();
            Statement statement = connection.createStatement();

            statement.executeUpdate(INSERT_WORKMAN);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean insertPlacesData(Places place){
        try {
            Connection connection = DatabaseConnection.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PLACES);
            preparedStatement.setInt(1, place.getId());
            preparedStatement.setInt(2, place.getRow());
            preparedStatement.setInt(3, place.getPlace_num());
            preparedStatement.setString(4, place.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static int getCountRowByTable(String tableName) throws SQLException {
        Connection connection = DatabaseConnection.createConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT count (*) from " + tableName);
        resultSet.next();
        int count = resultSet.getInt("count(*)");
        System.out.printf("В таблице public.%s ровно %s записей%n", tableName, count);
        return count;
    }

    public static int getZooCountRow() throws SQLException {
        Connection connection = DatabaseConnection.createConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT count (*) from zoo");
        resultSet.next();
        int count = resultSet.getInt("count(*)");
        System.out.println(resultSet.getInt("count(*)"));
        return count;
    }

    @SneakyThrows
    public static int getAnimalCountRow() {
        Connection connection = DatabaseConnection.createConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_COUNT_ANIMAL_ROW);
        resultSet.next();
        return resultSet.getInt("count(*)");
    }

    public static int getRowCountByTable(String tableName) throws SQLException {
        Connection connection = DatabaseConnection.createConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT count (*) from " + tableName);
        resultSet.next();
        int count = resultSet.getInt("count(*)");
        System.out.printf("Table public.%s has exact %s rows%n", tableName, count);
        return count;
    }

    public static int getPlacesCountRow() throws SQLException {
        Connection connection = DatabaseConnection.createConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT count (*) from places");
        resultSet.next();
        int count = resultSet.getInt("count(*)");
        System.out.println(resultSet.getInt("count(*)"));
        return count;
    }

    public static List<String> getZooNameData() {
        String name;
        List<String> zoo = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id,  \"name\" from public.zoo");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                name = resultSet.getString("name");
                System.out.println(name);
                zoo.add(name);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return zoo;
    }

    @SneakyThrows
    public static int getAnimalId(String nameAnimal) {
        int id = -1;
        String name;
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id,  \"name\" from public.animal");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next() && id < 0) {
            name = resultSet.getString("name");
            if (name.equals(nameAnimal)) id = resultSet.getInt("id");
        }
        return id;
    }


    @SneakyThrows
    public static Animal updateAnimalDataByName(Animal animal, String nameAnimal) {
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ANIMAL_BY_ID);
        preparedStatement.setString(1, animal.getName());
        preparedStatement.setInt(2, animal.getAge());
        preparedStatement.setInt(3, animal.getType());
        preparedStatement.setInt(4, animal.getSex());
        preparedStatement.setInt(5, animal.getPlace());
        preparedStatement.setInt(6, getAnimalId(nameAnimal));
        preparedStatement.executeUpdate();
        return animal;
    }

    @SneakyThrows
    public static Animal updateAnimalDataById(Animal animal, int idAnimal) {
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ANIMAL_BY_ID);
        preparedStatement.setString(1, animal.getName());
        preparedStatement.setInt(2, animal.getAge());
        preparedStatement.setInt(3, animal.getType());
        preparedStatement.setInt(4, animal.getSex());
        preparedStatement.setInt(5, animal.getPlace());
        preparedStatement.setInt(6, idAnimal);
        preparedStatement.executeUpdate();
        return animal;
    }

    @SneakyThrows
    public static void deleteAnimalDataByName(String nameAnimal) {
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ANIMAL_BY_ID);
        preparedStatement.setInt(1, getAnimalId(nameAnimal));
        preparedStatement.executeUpdate();
    }
}
