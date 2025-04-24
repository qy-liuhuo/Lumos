package io.github.qylh.lumos.entity;

import lombok.Data;

import java.util.List;

@Data
public class TableInfo {

    String tableName;

    List<ColumnInfo> columns;

}
