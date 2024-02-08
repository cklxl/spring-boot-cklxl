package com.cklxl.dynamic.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cklxl.dynamic.mapper.DynamicMapper;
import com.cklxl.dynamic.service.DynamicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 动态执行SQL服务接口实现
 *
 * @author Kun.Chen
 * @date 2020-11-18 16:11:21
 */
@Slf4j
@Service
public class DynamicServiceImpl implements DynamicService {

    @Autowired
    private DynamicMapper dynamicApiMapper;

    // @DS("#db")
    @Override
    public List<Map<String, Object>> list(String db, String customSql) {
        return dynamicApiMapper.list(customSql);
    }

    // @DS("#db")
    @Override
    public Page<Map<String, Object>> page(String db, String customSql, int pageNum, int pageSize) {
        return dynamicApiMapper.page(new Page(pageNum, pageSize), customSql);
    }

}
