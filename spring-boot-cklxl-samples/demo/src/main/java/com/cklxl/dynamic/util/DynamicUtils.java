package com.cklxl.dynamic.util;

import com.cklxl.dynamic.util.sql.BoundSql;
import com.cklxl.util.parse.GenericTokenParser;
import com.cklxl.util.parse.ParameterMapping;
import com.cklxl.util.parse.ParameterMappingTokenHandler;

import java.util.List;

/**
 * <p>
 * 动态解析工具类
 * </p>
 *
 * @author Kun.Chen
 * @date 2020/11/14 17:09
 */
public class DynamicUtils {

    /**
     * 完成对#{}的解析工作：1.将#{}使用placeholder进行代替，2.解析出#{}里面的值进行存储
     * @param sql
     * @return
     */
    public static BoundSql getBoundSql(String sql, String placeholder) {
        // 标记处理类：配置标记解析器来完成对占位符的解析处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler(placeholder);
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        // 解析出来的sql
        String parseSql = genericTokenParser.parse(sql);
        // #{}里面解析出来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(parseSql, parameterMappings);
        return boundSql;
    }

}
