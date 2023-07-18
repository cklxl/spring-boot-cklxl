package com.cklxl.demo.web;

import com.cklxl.db.util.TableInfo;
import com.cklxl.dynamic.mapper.DynamicMapper;
import com.cklxl.dynamic.service.DynamicService;
import com.cklxl.util.PageData;
import com.cklxl.util.PageUtils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

@RestController
@AllArgsConstructor
public class DemoWeb {

    private DynamicService dynamicService;

    private DynamicMapper dynamicMapper;

    @GetMapping("/test")
    public PageData<Map<String, Object>> test() {
        var page = dynamicService.page("", "select * from demo", 1, 10);
        return PageUtils.toPageData(page);
    }

    @GetMapping("/add")
    public void add() {
        dynamicMapper.save(TableInfo.of("demo",
                Map.of("code", new Date().getTime() + "",
                        "name", "张三" + new Random().nextInt(1000000),
                        "age", new Random().nextInt(100),
                        "birthday", new Date(),
                        "create_time", new Date(),
                        "sync_time", new Date(),
                        "amount", new BigDecimal("12.12")
                )));
    }

    @GetMapping("/addbatch")
    public void addBatch() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(Map.of("code", new Date().getTime() + "",
                    "name", "张三" + i,
                    "age", new Random().nextInt(100),
                    "birthday", new Date(),
                    "create_time", new Date(),
                    "sync_time", new Date(),
                    "amount", new BigDecimal("12.12")
            ));
        }
        dynamicMapper.saveBatch(TableInfo.ofList("demo", list));
    }
}
