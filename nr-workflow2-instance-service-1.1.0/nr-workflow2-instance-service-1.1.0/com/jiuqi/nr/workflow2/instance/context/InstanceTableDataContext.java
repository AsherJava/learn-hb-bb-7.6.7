/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.instance.context;

import com.jiuqi.nr.workflow2.instance.context.InstanceBaseContext;
import com.jiuqi.nr.workflow2.instance.entity.PageInfo;
import com.jiuqi.nr.workflow2.instance.entity.TableDataFilterInfo;

public class InstanceTableDataContext
extends InstanceBaseContext {
    private PageInfo pageInfo;
    private TableDataFilterInfo filterInfo;

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public TableDataFilterInfo getFilterInfo() {
        return this.filterInfo;
    }

    public void setFilterInfo(TableDataFilterInfo filterInfo) {
        this.filterInfo = filterInfo;
    }
}

