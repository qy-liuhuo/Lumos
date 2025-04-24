package io.github.qylh.lumos.entity;

import io.github.qylh.lumos.utils.serializer.JsonSerializer;
import lombok.Data;

import java.util.List;

@Data
public class DBInfo {

    private String dbName;

    private String dbType;

    private String dbVersion;

    private List<TableInfo>  tables;

    @Override
    public String toString() {
        return JsonSerializer.serialize(this);
    }
}
