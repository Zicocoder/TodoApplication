package se.lexicon.dao;
import se.lexicon.model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class PeopleImpl implements People{

    private final Connection connection;

    public PeopleImpl(Connection connection) {
        this.connection = connection;

    }


    @Override
    public Person create(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("person cant be null");

        }
        String sql = "INSERT INTO person (first_name, last_name) VALUES (?, ?)";

        try (
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {

            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        person.setPersonId((resultSet.getInt(1) ));

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return person;
    }



    @Override
    public Person findById(int id) {
        String sql = "SELECT * FROM person WHERE person_id = ?";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ) {
            preparedStatement.setInt(1, id);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
                    ) {
                if (resultSet.next()) {
                    return new Person(
                            resultSet.getInt("person_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<Person> findAll() {
        java.util.List<Person> people = new java.util.ArrayList<>();

        String sql = "SELECT * FROM person";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                Person person = new Person(
                        resultSet.getInt("person_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name")
                );
                people.add(person);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    @Override
    public Collection<Person> findByName(String name) {
        java.util.List<Person> people = new java.util.ArrayList<>();

        String sql = "SELECT * FROM person where first_name LIKE ? OR last_name LIKE ?";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            String pattern = "%" + name + "%";
                preparedStatement.setString(1, pattern);
                preparedStatement.setString(2, pattern);

               try (ResultSet resultSet = preparedStatement.executeQuery()) {


            while (resultSet.next()) {
                people.add(new Person(
                        resultSet.getInt("person_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name")
                ));

            }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    @Override
    public Person update(Person person) {
        String sql = "UPDATE person SET first_name = ?, last_name = ? WHERE person_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setInt(3, person.getPersonId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
            System.out.println("No person updated (check person_id).");
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM person WHERE person_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {

            preparedStatement.setInt(1, id);

            int affectedRows = preparedStatement.executeUpdate();

           return affectedRows > 0;

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    }

