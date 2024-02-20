package com.cklxl.boot.easyexcel.annotations;

import java.lang.annotation.*;

import com.cklxl.boot.easyexcel.handler.ExcelDynamicSelect;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelSelected {

    String[] source();

    Class<? extends ExcelDynamicSelect>[] sourceClass();

}
