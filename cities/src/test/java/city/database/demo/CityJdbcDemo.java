package city.database.demo;

import city.data.CityFrLbk;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.jdbc.PgConnection;
import utils.DataException;

import java.sql.*;
import java.text.MessageFormat;
import java.util.stream.Stream;

public class CityJdbcDemo {

    Connection connection;

    @BeforeEach
    void initConnection() throws SQLException {
        String user = "city";
        String password = "password";
        String host = "localhost";
        int port = 5432;
        String dbname = "dbcity";
        String url = MessageFormat.format(
                "jdbc:postgresql://{0}:{1,number,#####}/{2}",
                host,
                port,
                dbname
        );
        // System.out.println(url);
        connection = DriverManager.getConnection(url, user, password);
    }

    @Test
    void demoQuery() throws SQLException {
        // NB: Java 17: multi lines String
        String sql = """
                SELECT 
                    nom_standard, 
                    code_postal, 
                    code_insee, 
                    population
                FROM city
                WHERE dep_code = '30'
                ORDER BY population DESC
                """;
        System.out.println("[Debug] connection = " + connection);
        try (
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
        ) {
            System.out.println("[Debug] statement = " + resultSet);
            System.out.println("[Debug] result set = " + resultSet);
            System.out.println();
            while (resultSet.next()) {
                String name = resultSet.getString("nom_standard");
                int population = resultSet.getInt("population");
                System.out.println(MessageFormat.format(
                        "name = {0}, population = {1}",
                        name,
                        population
                ));
                var city = CityFrLbk.builder()
                        .name(name)
                        .population(population)
                        .build();
                System.out.println("city: " + city);
                System.out.println();
            }
        } // close: statement + resultset
    }

    @Test
    void demoQueryWithParameter() throws SQLException {
        // NB: Java 17: multi lines String
        String sql = """
                SELECT 
                    nom_standard, 
                    code_postal, 
                    code_insee, 
                    population
                FROM city
                WHERE dep_code = ?
                ORDER BY population DESC
                """;
        System.out.println("[Debug] connection = " + connection);
        try (
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            Stream.of("30", "84", "65")
                    .forEach(departmentNumber -> {
                        System.out.println(" * Department: " + departmentNumber);
                        try {
                            statement.setString(1, departmentNumber);
                            try (
                                    ResultSet resultSet = statement.executeQuery()
                            ) {
                                while (resultSet.next()) {
                                    String name = resultSet.getString("nom_standard");
                                    int population = resultSet.getInt("population");
                                    var city = CityFrLbk.builder()
                                            .name(name)
                                            .population(population)
                                            .build();
                                    System.out.println("\t- " + city);
                                } // end while
                            } // close resultset
                        } catch (SQLException e) {
                            throw new DataException("Error while executing SQL query", e);
                        }
                        System.out.println();
                    });
        } // close: statement
    }


    @AfterEach
    void closeConnection() throws SQLException {
        connection.close();
    }
}
