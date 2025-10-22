/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.response;

import com.jiuqi.nr.tag.management.intf.ITagFacade;
import com.jiuqi.nr.tag.management.response.PageConfig;
import java.util.List;

public class TagManagerResponse {
    private PageConfig pageConfig;
    private List<ITagFacade> rowDatas;

    public PageConfig getPageConfig() {
        return this.pageConfig;
    }

    public void setPageConfig(PageConfig pageConfig) {
        this.pageConfig = pageConfig;
    }

    public List<ITagFacade> getRowDatas() {
        return this.rowDatas;
    }

    public void setRowDatas(List<ITagFacade> rowDatas) {
        this.rowDatas = rowDatas;
    }
}

