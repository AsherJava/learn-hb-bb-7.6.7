/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.facade;

import com.jiuqi.np.definition.facade.FieldSearchItem;
import java.util.List;

public class FileldSearchParam {
    public static final int PAGE_NO = 1;
    public static final int PAGE_SIZE = 15;
    private Integer pageNo = 1;
    private Integer pageSize = 15;
    private String keywords;
    private List<FieldSearchItem> list;

    public Integer getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<FieldSearchItem> getList() {
        return this.list;
    }

    public void setList(List<FieldSearchItem> list) {
        this.list = list;
    }
}

