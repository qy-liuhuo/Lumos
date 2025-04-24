package io.github.qylh.lumos.entity;

import lombok.Data;

@Data
public class ColumnInfo {
    private String columnName;
    private String columnType;
    private boolean nullable;
    private String columnComment;

}
