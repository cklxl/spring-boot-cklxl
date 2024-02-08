package com.cklxl.dynamic.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * 动态执行SQL服务接口
 *
 * @author Kun.Chen
 * @date 2020-11-18 16:11:21
 */
public interface DynamicService {

    /**
     * 不分页接口
     * @param db
     * @param customSql
     * @return
     */
    List<Map<String, Object>> list(String db, String customSql);

    /**
     * 分页接口
     * @param db
     * @param customSql
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<Map<String, Object>> page(String db, String customSql, int pageNum, int pageSize);

}
