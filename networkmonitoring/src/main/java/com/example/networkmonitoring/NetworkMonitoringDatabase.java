package com.example.networkmonitoring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;

public class NetworkMonitoringDatabase {

    private String jdbcUrl;
    private String username;
    private String password;

    public NetworkMonitoringDatabase(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS network_monitoring (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "parameter_name VARCHAR(255) NOT NULL," +
                "parameter_value DOUBLE NOT NULL," +
                "timestamp TIMESTAMP NOT NULL" +
                ")";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(createTableQuery)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle table creation failure
        }
    }

    public void saveParameters(Map<String, Double> parameters) {
        String insertQuery = "INSERT INTO network_monitoring (parameter_name, parameter_value, timestamp) " +
                "VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            LocalDateTime timestamp = LocalDateTime.now();

            for (Map.Entry<String, Double> entry : parameters.entrySet()) {
                statement.setString(1, entry.getKey());
                statement.setDouble(2, entry.getValue());
                statement.setObject(3, timestamp);
                statement.addBatch();
            }

            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle parameter saving failure
        }
    }
}