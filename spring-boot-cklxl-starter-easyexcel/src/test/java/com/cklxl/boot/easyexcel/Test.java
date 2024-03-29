/*
 * Copyright 2022-2030 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cklxl.boot.easyexcel;

import java.util.List;

import com.alibaba.excel.EasyExcel;

public class Test {

    public static void main(String[] args) {
        List<List<String>> head = List.of(List.of("姓名"), List.of("年龄"), List.of("性别"));
        List<List<Object>> data = List.of(List.of("张三", 18, "男"), List.of("李四", 19, "女"));
        EasyExcel.write("D:\\test.xlsx").sheet("模板").head(head).doWrite(data);
    }

}
