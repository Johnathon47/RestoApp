package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private final static int defaultPortPsql = 5432;
    private final String host = System.getenv("DATABASE_HOST");
    private final String user = System.getenv("DATABASE_USER");
    private final String loginPassword = System.getenv("LOGIN_PASSWORD_DATABASE");
    private final String nameDatabase = System.getenv("DATABASE_NAME");
    private final String jdbcUrl;

    public DataSource() {
        jdbcUrl = "jdbc:postgresql://"+host+":"+defaultPortPsql+"/"+nameDatabase;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(jdbcUrl, user, loginPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}