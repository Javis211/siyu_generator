package com.siyu.model;

import lombok.Data;

/**
* 静态模版配置
* */
/**
 * 动态模版配置
 */
@Data
public class MainTemplateConfig {

    /**
     * 是否生成循环
     */
    private boolean loop;

    /**
     * 作者注释
     */
    private String author = "siyu";

    /**
     * 输出信息
     */
    private String outputText = "输出结果";
}

