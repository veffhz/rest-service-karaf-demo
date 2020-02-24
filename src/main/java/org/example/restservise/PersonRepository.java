package org.example.restservise;

import org.example.restservise.dbconnector.MSConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonRepository {

    public static List<Person> getPersons() {
        List<Person> persons = new ArrayList<>();

        Connection connection = connect();

        PreparedStatement statement;

        String sql = "SELECT [id], [name] FROM [test].[dbo].[persons]";

        try {
            statement = connection.prepareStatement(sql);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");

                    persons.add(new Person(id, name));
                }
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        MSConnector.connectionClose();

        return persons;
    }

    public static Person getPerson(int id) {
        Connection connection = connect();

        PreparedStatement statement;

        String sql = "SELECT [name] FROM [test].[dbo].[persons] WHERE [id] = ?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    return new Person(id, name);
                }
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        MSConnector.connectionClose();

        return null;
    }

    public static Person addPerson(String name) {
        Connection connection = connect();

        PreparedStatement statement;

        String sql = "INSERT INTO [test].[dbo].[persons] (name) VALUES (?)";

        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                       int id = rs.getInt(1);
                       return new Person(id, name);
                    }
                }
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        MSConnector.connectionClose();
        return null;
    }

    public static Person updatePerson(Person person) {
        Connection connection = connect();

        PreparedStatement statement;

        String sql = "UPDATE [test].[dbo].[persons] SET [name] = ? WHERE id = ?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, person.getName());
            statement.setInt(2, person.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                return person;
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        MSConnector.connectionClose();
        return null;
    }

    public static boolean deletePersonById(int id) {
        Connection connection = connect();

        PreparedStatement statement;

        String sql = "DELETE FROM [test].[dbo].[persons] WHERE id = ?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                return true;
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        MSConnector.connectionClose();
        return false;
    }

    private static Connection connect() {
        String dockerIp = "172.17.0.1";
        //return MSConnector.getConnectionOpen("127.0.0.1", "test", "sa", "30TFN@M!$r97!");
        return MSConnector.getOpenConnection(dockerIp, "test", "sa", "30TFN@M!$r97!");
    }

    public static void main(String[] args) {
        runTest();
    }

    private static void runTest() {
        Person nariman = PersonRepository.addPerson("nariman");
        System.out.println(nariman);

        Person person = PersonRepository.getPerson(nariman.getId());
        System.out.println(person);

        PersonRepository.updatePerson(new Person(nariman.getId(), "oleg"));
        List<Person> persons = PersonRepository.getPersons();

        for (Person p : persons) {
            System.out.println(p);
        }

        PersonRepository.deletePersonById(nariman.getId());
    }

}
