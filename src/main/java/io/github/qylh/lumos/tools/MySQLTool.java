package io.github.qylh.lumos.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class MySQLTool {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private Connection connection;
    private Statement statement;

    public MySQLTool(MySQLProperties mysqlProperties) {
        String DB_URL = "jdbc:mysql://" + mysqlProperties.getHost() + ":" + mysqlProperties.getPort() + "/" + mysqlProperties.getDatabase();
        String USER = mysqlProperties.getUsername();
        String PASS = mysqlProperties.getPassword();
        try {
            Class.forName(DRIVER);
            // Establish a connection to the database
            this.connection = DriverManager.getConnection(DB_URL, USER, PASS);
            this.statement = this.connection.createStatement();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to MySQL database", e);
        }
    }

    @Tool(name = "getDBInfo", description = "获取数据库相关信息")
    public String getDBInfo(){
        System.out.println("getDBInfo");
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("Database URL: ").append(connection.getMetaData().getURL()).append("\n");
            sb.append("Database User: ").append(connection.getMetaData().getUserName()).append("\n");
            sb.append("Database Product Name: ").append(connection.getMetaData().getDatabaseProductName()).append("\n");
            sb.append("Database Product Version: ").append(connection.getMetaData().getDatabaseProductVersion()).append("\n");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get database info", e);
        }
        return sb.toString();
    }

    @Tool(name = "getTablesInfo", description = "获取数据库中所有表信息")
    public String getTablesInfo(){
        System.out.println("getTablesInfo");
        StringBuilder sb = new StringBuilder();
        try {
            var resultSet = statement.executeQuery("SHOW TABLES");
            while (resultSet.next()) {
                sb.append(resultSet.getString(1)).append("\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get tables info", e);
        }
        return sb.toString();
    }

}
