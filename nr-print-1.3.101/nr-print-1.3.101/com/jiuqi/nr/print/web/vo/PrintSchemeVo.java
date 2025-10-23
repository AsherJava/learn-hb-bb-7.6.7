/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 */
package com.jiuqi.nr.print.web.vo;

import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import org.springframework.beans.BeanUtils;

public class PrintSchemeVo {
    private String key;
    private String title;
    private String desc;
    private String order;
    private String taskKey;
    private String formSchemeKey;

    public static PrintSchemeVo toPrintSchemeVo(DesignPrintTemplateSchemeDefine printSchemeDefine) {
        PrintSchemeVo vo = new PrintSchemeVo();
        BeanUtils.copyProperties(printSchemeDefine, vo);
        return vo;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

