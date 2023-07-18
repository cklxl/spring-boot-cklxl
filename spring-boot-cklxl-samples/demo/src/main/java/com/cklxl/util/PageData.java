package com.cklxl.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
public class PageData<T> {

    /**
     * 响应数据
     */
    private List<T> data;

    /**
     * 当前页
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long page;

    /**
     * 每页条数
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long size;

    /**
     * 总页数
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long pages;

    /**
     * 查询总记录数
     */
    private Long count;

    public PageData() {
        this(Collections.emptyList(), 0L, 0L, 0L, 0L);
    }

    @JsonCreator
    public PageData(@JsonProperty("data") List<T> data,
                    @JsonProperty("page") Long page,
                    @JsonProperty("size") Long size,
                    @JsonProperty("pages") Long pages,
                    @JsonProperty("count") Long count) {
        this.data = data;
        this.page = page;
        this.size = size;
        this.pages = pages;
        this.count = count;
    }

    /**
     * 数据转换
     *
     * @param mapper
     * @param <R>
     * @return
     */
    public <R> PageData<R> mapData(Function<T, R> mapper) {
        return new PageData<>(getData().stream().map(mapper).collect(Collectors.toList()),
                getPage(), getSize(), getPages(), getCount());
    }
}
