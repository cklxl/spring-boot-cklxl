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

package com.cklxl.boot.easyexcel.handler;

import java.lang.reflect.InvocationTargetException;

import com.cklxl.boot.easyexcel.annotations.ExcelSelected;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@NoArgsConstructor
public class ExcelSelectedResolve {

    /**
     * 下拉内容
     */
    private String[] source;

    /**
     * 设置下拉框的起始行，默认为第二行
     */
    private Integer firstRow;

    /**
     * 设置下拉框的结束行，默认为最后一行
     */
    private Integer lastRow;

    public ExcelSelectedResolve(String[] source) {
        this.source = source;
        this.firstRow = 1;
        this.lastRow = 10000;
    }

    public String[] resolveSelectedSource(ExcelSelected excelSelected) {
        if (excelSelected == null) {
            return null;
        }

        // 获取固定下拉框的内容
        String[] source = excelSelected.source();
        if (source.length > 0) {
            return source;
        }

        // 获取动态下拉框的内容
        Class<? extends ExcelDynamicSelect>[] classes = excelSelected.sourceClass();
        if (classes.length > 0) {
            try {
                ExcelDynamicSelect excelDynamicSelect = classes[0].getDeclaredConstructor().newInstance();
                String[] dynamicSelectSource = excelDynamicSelect.getSource();
                if (dynamicSelectSource != null && dynamicSelectSource.length > 0) {
                    return dynamicSelectSource;
                }
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
                log.error("解析动态下拉框数据异常", ex);
            }
        }
        return null;
    }

}
