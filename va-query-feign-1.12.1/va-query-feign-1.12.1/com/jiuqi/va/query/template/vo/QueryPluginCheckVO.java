/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.template.vo;

import com.jiuqi.va.query.template.vo.QueryCheckItemVO;
import java.util.ArrayList;
import java.util.List;

public class QueryPluginCheckVO {
    private String name;
    private List<QueryCheckItemVO> result = new ArrayList<QueryCheckItemVO>();

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QueryCheckItemVO> getResult() {
        return this.result;
    }

    public void setResult(List<QueryCheckItemVO> result) {
        this.result = result;
    }

    public QueryPluginCheckVO(String name) {
        this.name = name;
    }

    public QueryPluginCheckVO() {
    }

    public String toString() {
        return "QueryPluginCheckVO{name='" + this.name + '\'' + ", result=" + this.result + '}';
    }
}

