package com.cklxl.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.util.CollectionUtils;

import java.util.Objects;

/**
 * <p>
 * 分页工具类
 * </p>
 *
 * @author kun.chen
 * @date 2021/8/18 11:25
 */
public class PageUtils {
    /**
     * 转换为分页数据
     *
     * @param iPage
     * @param <T>
     * @return
     */
    public static <T> PageData<T> toPageData(IPage<T> iPage) {
        if (Objects.nonNull(iPage) && !CollectionUtils.isEmpty(iPage.getRecords())) {
            return new PageData<>(iPage.getRecords(),
                    iPage.getCurrent(),
                    iPage.getSize(),
                    iPage.getPages(),
                    iPage.getTotal());
        }
        return new PageData<>();
    }
}
