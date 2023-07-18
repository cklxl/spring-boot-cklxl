package com.cklxl.util.parse;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ParameterMappingTokenHandler implements TokenHandler {
    /**
     * 占位符
     */
    private String placeholder = "?";

    @Getter
    @Setter
    private List<ParameterMapping> parameterMappings = new ArrayList<>();

    public ParameterMappingTokenHandler() {
    }

    public ParameterMappingTokenHandler(String placeholder) {
        this.placeholder = placeholder;
    }

    /**
     * @param content 是参数名称 #{id} #{username}
     * @return
     */
    public String handleToken(String content) {
        parameterMappings.add(buildParameterMapping(content));
        return placeholder;
    }

    private ParameterMapping buildParameterMapping(String content) {
        ParameterMapping parameterMapping = new ParameterMapping(content);
        return parameterMapping;
    }

}
