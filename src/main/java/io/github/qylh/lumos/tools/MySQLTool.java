package io.github.qylh.lumos.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.qylh.lumos.entity.ColumnInfo;
import io.github.qylh.lumos.entity.DBInfo;
import io.github.qylh.lumos.entity.TableInfo;
import io.github.qylh.lumos.utils.serializer.JsonSerializer;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MySQLTool {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private Connection connection;
    private Statement statement;
    private MySQLProperties properties;

    public MySQLTool(MySQLProperties mysqlProperties) {
        this.properties = mysqlProperties;
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

    @Tool(name = "getDBInfo", description = "获取数据库信息, 包括表名、字段名、字段类型等")
    public String getTablesInfo(){
        DBInfo dbInfo = new DBInfo();
        dbInfo.setDbName(this.properties.getDatabase());
        try {
            dbInfo.setDbVersion(connection.getMetaData().getDatabaseProductVersion());
            dbInfo.setDbType(connection.getMetaData().getDatabaseProductName());
            List<TableInfo> tableInfos = new ArrayList<>();
            var Tables = statement.executeQuery("SHOW TABLES");
            while (Tables.next()) {
                TableInfo tableInfo = new TableInfo();
                String tableName = Tables.getString(1);
                tableInfo.setTableName(tableName);
                tableInfo.setColumns(new ArrayList<>());
                tableInfos.add(tableInfo);
            }
            for (TableInfo tableInfo : tableInfos) {
                var Columns = statement.executeQuery(String.format("select * from information_schema.columns where TABLE_NAME = '%s'", tableInfo.getTableName()));
                while (Columns.next()) {
                    ColumnInfo columnInfo = new ColumnInfo();
                    columnInfo.setColumnName(Columns.getString("COLUMN_NAME"));
                    columnInfo.setColumnType(Columns.getString("DATA_TYPE"));
                    columnInfo.setNullable(Columns.getBoolean("IS_NULLABLE"));
                    columnInfo.setColumnComment(Columns.getString("COLUMN_COMMENT"));
                    tableInfo.getColumns().add(columnInfo);
                }
            }
            dbInfo.setTables(tableInfos);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get tables info", e);
        }
        System.out.println(dbInfo);
        return dbInfo.toString();
    }

}
