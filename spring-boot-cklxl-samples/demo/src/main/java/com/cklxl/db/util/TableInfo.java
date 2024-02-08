package com.cklxl.db.util;

import com.google.common.base.CaseFormat;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.JdbcType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TableInfo {

    private String tableName;

    private List<String> columns;

    private List<JdbcType> types;

    private List<Object> values;

    private List<List<Object>> batchValues;

    public static TableInfo of(String tableName, Map<String, Object> data) {
        var t = new TableInfo();
        t.setTableName(tableName);
        t.setColumns(new ArrayList<>());
        t.setValues(new ArrayList<>());
        data.keySet().forEach(k -> {
            // 驼峰文转下划线
            t.getColumns().add(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, k));
            // 获取值
            t.getValues().add(data.get(k));
        });
        return t;
    }

    public static TableInfo ofList(String tableName, List<Map<String, Object>> data) {
        var t = new TableInfo();
        t.setTableName(tableName);
        t.setColumns(new ArrayList<>());
        t.setBatchValues(new ArrayList<>());
        data.get(0).keySet().forEach(k -> {
            // 驼峰文转下划线
            t.getColumns().add(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, k));
        });
        data.forEach(d -> {
            var row = new ArrayList<>();
            t.getColumns().forEach(k -> {
                // 获取值
                row.add(d.get(k));
            });
            t.getBatchValues().add(row);
        });
        return t;
    }

}
