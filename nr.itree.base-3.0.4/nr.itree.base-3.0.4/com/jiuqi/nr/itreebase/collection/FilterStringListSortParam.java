/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.collection;

import com.jiuqi.nr.itreebase.collection.IFilterStringListSortParam;
import java.util.List;

public class FilterStringListSortParam
implements IFilterStringListSortParam {
    private String operate;
    private String beforeNode;
    private String afterNode;
    private List<String> range;

    @Override
    public String getOperate() {
        return this.operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    @Override
    public String getBeforeNode() {
        return this.beforeNode;
    }

    public void setBeforeNode(String beforeNode) {
        this.beforeNode = beforeNode;
    }

    @Override
    public String getAfterNode() {
        return this.afterNode;
    }

    public void setAfterNode(String afterNode) {
        this.afterNode = afterNode;
    }

    @Override
    public List<String> getRange() {
        return this.range;
    }

    public void setRange(List<String> range) {
        this.range = range;
    }
}

