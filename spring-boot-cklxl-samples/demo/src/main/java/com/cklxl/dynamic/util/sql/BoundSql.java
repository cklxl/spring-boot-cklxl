package com.cklxl.dynamic.util.sql;

import com.cklxl.util.parse.ParameterMapping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 解析后sql
 * </p>
 *
 * @author Kun.Chen
 * @date 2020/11/14 16:49
 */
public class BoundSql implements Serializable {

    /**
     * 解析过后的sql
     */
    private String sqlText;

    /**
     * 解析后的参数列表
     */
    private List<ParameterMapping> parameterMappingList = new ArrayList<>();

    public BoundSql(String sqlText, List<ParameterMapping> parameterMappingList) {
        this.sqlText = sqlText;
        this.parameterMappingList = parameterMappingList;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }
}
