package com.cklxl.boot.easyexcel;

import com.alibaba.excel.EasyExcel;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.changetype.map.KeyValueChange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Map.Entry;
import static java.util.Map.of;

public class Test {
    public static void main(String[] args) {
        compareMap();
    }

    public static void excelImport() {
        List<List<String>> head = List.of(List.of("姓名"), List.of("年龄"), List.of("性别"));
        List<List<Object>> data = List.of(List.of("张三", 18, "男"), List.of("李四", 19, "女"));
        EasyExcel.write("D:\\test.xlsx")
                .sheet("模板")
                .head(head)
                .doWrite(data);
    }

    /**
     * 比较两个map的差异
     */
    public static void compareMap() {
        var map1 = new HashMap<String, String>();
        map1.put("姓名", "张三");
        map1.put("年龄", "20");
        map1.put( "性别", "男");
        var map2 = new HashMap<String, String>();
//        map2.put("姓名", "李四");
        map2.put("姓名", null);
        map2.put("年龄", "21");
        map2.put( "性别", "男");
        Javers javers = JaversBuilder.javers().build();
        var diff = javers.compare(map1, map2);
        diff.getChanges().forEach(change -> {
            KeyValueChange valueChange = (KeyValueChange) change;
            if (valueChange.getPropertyName().equals("map")) {
                valueChange.getEntryChanges().forEach(entryValueChange -> {
                    System.out.println(format(entryValueChange.toString()));
                });
            }
        });
    }

    public static String format(String str) {
        // 将字符串”· entry ['姓名' : '张三'] -> ['姓名' : '李四']“ 解析为 ”姓名: '张三' -> '李四'“
        String[] split = str.split("->");
        String[] split1 = split[0].split("'");
        String[] split2 = split[1].split("'");
        return split1[1] + ": " + "'" + split1[3] + "'" + " -> " + "'" + split2[3] + "'";
    }
}
