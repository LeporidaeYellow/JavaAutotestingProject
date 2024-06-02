package database.hibernate;

import database.hibernate.models.Animal;
import database.hibernate.models.Places;
import database.hibernate.models.Workman;
import database.hibernate.models.Zoo;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DBHibernateService {

    private static final String INSERT_ANIMAL = "INSERT INTO animal (id, \"name\", age, \"type\", sex, place) VALUES (%s, '%s', %s, %s, %s, %s)";
    private static final String UPDATE_ANIMAL = "UPDATE animal SET \"name\"='%s', age=%s, \"type\"=%s, sex=%s, place=%s WHERE id=%s";
    private static final String DELETE_ANIMAL = "DELETE FROM animal WHERE id=%s";
    private static final String INSERT_WORKMAN = "INSERT INTO workman (id, \"name\", age, \"position\") VALUES (%s, %s, %s, %s)";
    private static final String INSERT_PLACES = "INSERT INTO places (id, \"row\", place_num, \"name\") VALUES (%s, %s, %s, '%s')";

    @SneakyThrows
    public Optional<Animal> getAnimalByName(String name) {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        List<Animal> listAnimal = session.createNativeQuery("SELECT * FROM animal", Animal.class).getResultList();
        Optional<Animal> res = listAnimal.stream().filter(animal -> animal.getName().equals(name)).findAny();
        return res;
    }

    @SneakyThrows
    public List<Animal> getAnimals() {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        List<Animal> listAnimal = session.createNativeQuery("SELECT * FROM animal", Animal.class).getResultList();
        return listAnimal;
    }

    public List<Workman> getWorkmans() {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        List<Workman> workmanList = session.createNativeQuery("SELECT * FROM workman", Workman.class).getResultList();
        return workmanList;
    }

    public int getCountRowAnimal() {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        int count = session.createNativeQuery("SELECT * from animal ", Animal.class).getResultList().size();
        System.out.printf("Table public.animal has exact %s rows%n", count);
        return count;
    }

    public  int getCountRowZoo() {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        int count = session.createNativeQuery("SELECT * from zoo ", Zoo.class).getResultList().size();
        System.out.println(count);
        return count;
    }

    public List<String> getZooNameData() {
        List<String> names = new ArrayList<>();
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        List<Zoo> zooList = session.createNativeQuery("SELECT * from zoo", Zoo.class).getResultList();
        for (Zoo zoo: zooList) {
            names.add(zoo.getName());
        }
        return names;
    }

    public int getCountRowPlaces() {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        int count = session.createNativeQuery("SELECT * from places", Places.class).getResultList().size();
        System.out.println(count);
        return count;
    }

    public void insertWorkman(Workman workman) {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createNativeQuery(String.format(INSERT_WORKMAN, workman.getId(), workman.getName(), workman.getAge(), workman.getPosition())).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void insertAnimal(Animal animal) {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createNativeQuery(String.format(INSERT_ANIMAL, animal.getId(), animal.getName(), animal.getAge(), animal.getType(), animal.getSex(), animal.getPlace())).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void insertPlaces(Places places) {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createNativeQuery(String.format(INSERT_PLACES, places.getId(), places.getRow(), places.getPlace_num(), places.getName())).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void updateAnimalById(Animal animal) {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createNativeQuery(String.format(UPDATE_ANIMAL, animal.getName(), animal.getAge(), animal.getType(), animal.getSex(), animal.getPlace(), animal.getId())).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void deleteAnimal(Animal animal) {
        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createNativeQuery(String.format(DELETE_ANIMAL, animal.getId())).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
