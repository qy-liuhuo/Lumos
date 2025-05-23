package io.github.qylh.lumos.tools;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "lumos.db.mysql")
@Data
public class MySQLProperties {

    private String host;

    private int port;

    private String username;

    private String password;

    private String database;

}
