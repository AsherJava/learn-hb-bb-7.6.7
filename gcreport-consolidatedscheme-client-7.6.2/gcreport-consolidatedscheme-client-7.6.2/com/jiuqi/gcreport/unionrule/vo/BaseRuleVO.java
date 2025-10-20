/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotEmpty
 *  javax.validation.constraints.Size
 */
package com.jiuqi.gcreport.unionrule.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class BaseRuleVO {
    private String id;
    @NotEmpty(message="\u8bf7\u586b\u5199\u540d\u79f0")
    @Size(max=30, message="\u89c4\u5219\u540d\u79f0\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc730\u4e2a\u5b57\u7b26")
    private @NotEmpty(message="\u8bf7\u586b\u5199\u540d\u79f0") @Size(max=30, message="\u89c4\u5219\u540d\u79f0\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc730\u4e2a\u5b57\u7b26") String title;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

