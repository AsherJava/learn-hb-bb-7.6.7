/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.querytable.vo;

import com.jiuqi.va.biz.querytable.intf.QueryTableParamItem;
import com.jiuqi.va.biz.querytable.vo.QueryTableColumnVO;
import java.util.List;

public class QueryItemVO {
    private String name;
    private String title;
    private List<QueryTableParamItem> params;
    private List<QueryTableColumnVO> columns;

    public QueryItemVO() {
    }

    public QueryItemVO(String name, String title, List<QueryTableParamItem> params, List<QueryTableColumnVO> columns) {
        this.name = name;
        this.title = title;
        this.params = params;
        this.columns = columns;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<QueryTableParamItem> getParams() {
        return this.params;
    }

    public void setParams(List<QueryTableParamItem> params) {
        this.params = params;
    }

    public List<QueryTableColumnVO> getColumns() {
        return this.columns;
    }

    public void setColumns(List<QueryTableColumnVO> columns) {
        this.columns = columns;
    }
}

