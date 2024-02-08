package com.cklxl.filter;

import com.cklxl.filter.enums.FldOperation;
import com.cklxl.filter.enums.FldType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Filter {

    /**
     * 是否为and条件
     */
    private Boolean isAnd;

    /**
     * 条件类型
     */
    private FldType fldType;

    /**
     * 字段名
     */
    private String fldName;

    /**
     * 字段条件
     */
    private FldOperation fldOperation;

    /**
     * 字段值
     */
    private String fldValue;

    /**
     * 分组子条件
     */
    private List<Filter> children;

}
