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

import java.util.Map;
import java.util.Objects;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;

@AllArgsConstructor
public class SeletedSheetWriteHandler implements SheetWriteHandler {

    private final Map<Integer, ExcelSelectedResolve> selectedMap;

    /**
     * Called before create the sheet.
     */
    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    /**
     * Called after the sheet is created.
     */
    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        // 这里可以对cell进行任何操作
        Sheet sheet = writeSheetHolder.getSheet();
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        DataValidationHelper helper = sheet.getDataValidationHelper();
        this.selectedMap.forEach((k, v) -> {
            if (Objects.isNull(v.getSource())) {
                return;
            }
            // 设置下拉列表的行： 首行，末行，首列，末列
            CellRangeAddressList rangeList = new CellRangeAddressList(v.getFirstRow(), v.getLastRow(), k, k);
            DataValidationConstraint constraint = null;
            // 如果下拉值总数大于25，则使用一个新sheet存储，避免生成的导入模板下拉值获取不到
            if (v.getSource().length > 10) {
                // 定义sheet的名称
                // 1.创建一个隐藏的sheet 名称为 hidden + k
                String sheetName = "hidden" + k;
                Sheet hiddenSheet = workbook.createSheet(sheetName);
                for (int i = 0, length = v.getSource().length; i < length; i++) {
                    // 开始的行数i，列数k 默认为0
                    hiddenSheet.createRow(i).createCell(0).setCellValue(v.getSource()[i]);
                }
                String excelLine = getExcelLine(0);
                // hidden!$H:$1:$H$50 sheet为hidden的 H1列开始H50行数据获取下拉数组
                String refers = sheetName + "!$" + excelLine + "$1:$" + excelLine + "$" + v.getSource().length;
                // 创建可被其他单元格引用的名称
                Name name = workbook.createName();
                // 设置名称的名字
                name.setNameName(sheetName);
                // 设置公式
                name.setRefersToFormula(refers);
                // 设置存储下拉列值得sheet为隐藏
                int hiddenIndex = workbook.getSheetIndex(sheetName);
                if (!workbook.isSheetHidden(hiddenIndex)) {
                    workbook.setSheetHidden(hiddenIndex, true);
                }
                // 将刚才设置的sheet引用到你的下拉列表中
                constraint = helper.createFormulaListConstraint(sheetName);
            }
            else {
                // 设置下拉列表的值
                constraint = helper.createExplicitListConstraint(v.getSource());
            }
            // 设置引用约束
            DataValidation validation = helper.createValidation(constraint, rangeList);
            // 阻止输入非下拉选项的值
            if (validation instanceof HSSFDataValidation) {
                validation.setSuppressDropDownArrow(false);
            }
            else {
                validation.setSuppressDropDownArrow(true);
                validation.setShowErrorBox(true);
            }
            validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            validation.createErrorBox("提示", "请输入下拉选项中的内容");
            // 添加下拉框约束值
            sheet.addValidationData(validation);
        });
    }

    /**
     * 返回excel列标A-Z-AA-ZZ.
     * @param num 列数
     * @return java.lang.String
     */
    private String getExcelLine(int num) {
        String line = "";
        int first = num / 26;
        int second = num % 26;
        if (first > 0) {
            line = (char) ('A' + first - 1) + "";
        }
        line += (char) ('A' + second) + "";
        return line;
    }

}
