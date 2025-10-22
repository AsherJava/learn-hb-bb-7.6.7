/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.shiro.util.Assert
 */
package com.jiuqi.nr.entity.engine.var;

import java.io.Serializable;
import org.apache.shiro.util.Assert;

public class PageCondition
implements Serializable {
    private Integer pageSize;
    private Integer pageIndex;

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        Assert.isTrue((pageSize > 0 ? 1 : 0) != 0, (String)"\u9875\u5927\u5c0f\u9700\u8981\u5927\u4e8e0.");
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }
}

