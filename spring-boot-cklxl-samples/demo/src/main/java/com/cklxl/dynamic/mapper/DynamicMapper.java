package com.cklxl.dynamic.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cklxl.db.util.TableInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 自定义接口字段表 Mapper 接口
 * </p>
 *
 * @author kun.chen
 * @date 2023-7-18 09:32:38
 */
public interface DynamicMapper {

    /**
     * 新增
     * @param tableInfo
     * @return
     */
    int save(@Param("t") TableInfo tableInfo);

    /**
     * 新增批量
     * @param tableInfo
     * @return
     */
    int saveBatch(@Param("t") TableInfo tableInfo);

    /**
     * 更新
     * @param customSql
     * @return
     */
    int update(@Param("customSql") String customSql);

    /**
     * 删除
     * @param customSql
     * @return
     */
    int delete(@Param("customSql") String customSql);

    /**
     * 查询
     * @param customSql
     * @return
     */
    @Select("${customSql}")
    @ResultType(List.class)
    List<Map<String, Object>> list(@Param("customSql") String customSql);

    /**
     * 分页查询
     * @param customSql
     * @return
     */
    @Select("${customSql}")
    @ResultType(List.class)
    Page<Map<String, Object>> page(@Param("page") Page<Map> page, @Param("customSql") String customSql);

}
