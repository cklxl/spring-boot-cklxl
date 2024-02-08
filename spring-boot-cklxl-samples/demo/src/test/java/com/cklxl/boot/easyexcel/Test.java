package com.cklxl.boot.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.cklxl.filter.Filter;
import com.cklxl.filter.enums.FldOperation;
import com.cklxl.filter.enums.FldType;
import com.google.common.base.CaseFormat;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.changetype.map.KeyValueChange;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test {

    public static void main(String[] args) {
        // IntStream.range(0, 100).boxed().forEach(System.out::println);

        // var locales = Stream.of(Locale.getAvailableLocales());
        // var countryToLocales =
        // locales.collect(Collectors.groupingBy(Locale::getCountry));
        // System.out.println(countryToLocales.keySet());
        // System.out.println(countryToLocales.get("CN"));

        var list = List.of(List.of(1, 2), List.of(3, 4), List.of(5, 6, 7));
        var list2 = list.stream().flatMap(v -> v.stream());

        list2.parallel().unordered().limit(3).forEach(System.out::println);

        // System.out.println(list2);
        // list2.forEach(System.out::println);
        // list2.forEachOrdered(System.out::println);
        // Stream.iterate(1.0, p -> p * 2).peek(e -> System.out.println("Fetching " +
        // e)).limit(20).toArray();
        // test();
        // excelImport();
        // compareMap();
        // System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,
        // "CreateTime"));
        // System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,
        // "create_time"));
        // System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,
        // "create_time"));
        // System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,
        // "u9c_code"));
    }

    public static void excelImport() {
        List<List<String>> head = List.of(List.of("姓名"), List.of("年龄"), List.of("性别"));
        List<List<Object>> data = List.of(List.of("张三", 18, "男"), List.of("李四", 19, "女"));
        EasyExcel.write("D:\\test.xlsx").sheet("模板").head(head).doWrite(data);
    }

    /**
     * 比较两个map的差异
     */
    public static void compareMap() {
        var map1 = new HashMap<String, String>();
        map1.put("姓名", "张三");
        map1.put("年龄", "20");
        map1.put("性别", "男");
        var map2 = new HashMap<String, String>();
        // map2.put("姓名", "李四");
        map2.put("姓名", null);
        map2.put("年龄", "21");
        map2.put("性别", "男");
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

    public static void test() {
        List<Filter> filters = getFilters();
        Filter filter3 = new Filter();
        filter3.setIsAnd(Boolean.TRUE);
        filter3.setFldType(FldType.GROUP);
        filter3.setChildren(getFilters());
        filters.add(filter3);

        Filter filter4 = new Filter();
        filter4.setIsAnd(Boolean.TRUE);
        filter4.setFldType(FldType.GROUP);
        filter4.setChildren(getFilters2());
        filters.add(filter4);
        // 将过滤器转换为sql语句中的where条件
        String where = getWhere(filters);
        System.out.println(where);
    }

    private static List<Filter> getFilters() {
        List<Filter> filters = new ArrayList<>();
        Filter filter1 = new Filter();
        filter1.setIsAnd(Boolean.FALSE);
        filter1.setFldType(FldType.SINGLE);
        filter1.setFldName("name");
        filter1.setFldOperation(FldOperation.EQ);
        filter1.setFldValue("张三");
        filters.add(filter1);
        Filter filter2 = new Filter();
        filter2.setIsAnd(Boolean.TRUE);
        filter2.setFldType(FldType.SINGLE);
        filter2.setFldName("gender");
        filter2.setFldOperation(FldOperation.GT);
        filter2.setFldValue("20");
        filters.add(filter2);
        return filters;
    }

    private static List<Filter> getFilters2() {
        List<Filter> filters = new ArrayList<>();
        Filter filter1 = new Filter();
        filter1.setIsAnd(Boolean.FALSE);
        filter1.setFldType(FldType.SINGLE);
        filter1.setFldName("name");
        filter1.setFldOperation(FldOperation.EQ);
        filter1.setFldValue("张三");
        filters.add(filter1);
        Filter filter2 = new Filter();
        filter2.setIsAnd(Boolean.TRUE);
        filter2.setFldType(FldType.SINGLE);
        filter2.setFldName("gender");
        filter2.setFldOperation(FldOperation.GT);
        filter2.setFldValue("20");
        filters.add(filter2);

        Filter filter3 = new Filter();
        filter3.setIsAnd(Boolean.TRUE);
        filter3.setFldType(FldType.GROUP);
        filter3.setChildren(getFilters());
        filters.add(filter3);
        return filters;
    }

    public static String getWhere(List<Filter> filters) {
        StringBuilder where = new StringBuilder();
        for (Filter filter : filters) {
            if (filter.getIsAnd()) {
                where.append(" and ");
            }
            else {
                where.append(" or ");
            }
            if (filter.getFldType() == FldType.SINGLE) {
                where.append(filter.getFldName());
                where.append(" ");
                where.append(filter.getFldOperation().getValue());
                where.append(" ");
                // 需要基于类型转换
                where.append("'").append(filter.getFldValue()).append("'");
                continue;
            }
            where.append("(");
            where.append(getWhere(filter.getChildren()));
            where.append(")");
        }
        if (where.length() > 0) {
            if (where.indexOf(" and ") == 0) {
                where.delete(0, 5);
            }
            else if (where.indexOf(" or ") == 0) {
                where.delete(0, 4);
            }
        }
        // where.insert(0, "where ");
        return where.toString();
    }

}
