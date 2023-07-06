package com.cklxl.boot.easyexcel;

import com.alibaba.excel.EasyExcel;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<List<String>> head = List.of(List.of("姓名"), List.of("年龄"), List.of("性别"));
        List<List<Object>> data = List.of(List.of("张三", 18, "男"), List.of("李四", 19, "女"));
        EasyExcel.write("D:\\test.xlsx")
                .sheet("模板")
                .head(head)
                .doWrite(data);
    }
}
