package com.cklxl.util.parse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParameterMapping {

    private String content;

    public ParameterMapping(String content) {
        this.content = content;
    }
}
