/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotEmpty
 */
package com.jiuqi.nr.summary.model.group;

import java.util.Date;
import javax.validation.constraints.NotEmpty;

public class SummarySolutionGroup {
    private String key;
    @NotEmpty(message="\u7528\u6237\u540d\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotEmpty(message="\u7528\u6237\u540d\u4e0d\u80fd\u4e3a\u7a7a") String title;
    private String parent;
    private String order;
    private Date modifyTime;

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

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}

