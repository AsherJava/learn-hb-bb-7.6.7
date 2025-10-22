/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  nr.single.data.bean.CheckParam
 */
package com.jiuqi.nr.finalaccountsaudit.entitytreecheck.bean;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.io.Serializable;
import nr.single.data.bean.CheckParam;

public class QueryEntityTreeCheckResultInfo
implements Serializable {
    private static final long serialVersionUID = 5408193567334046430L;
    private JtableContext context;
    private String asyncTaskId;
    private String itemKey = "";
    private int itemOffset;
    private CheckParam checkParam;
    private int pagerCount = 20;

    public int getPagerCount() {
        return this.pagerCount;
    }

    public void setPagerCount(int pagerCount) {
        this.pagerCount = pagerCount;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getAsyncTaskId() {
        return this.asyncTaskId;
    }

    public void setAsyncTaskId(String asyncTaskId) {
        this.asyncTaskId = asyncTaskId;
    }

    public String getItemKey() {
        return this.itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public int getItemOffset() {
        return this.itemOffset;
    }

    public void setItemOffset(int itemOffset) {
        this.itemOffset = itemOffset;
    }

    public CheckParam getCheckParam() {
        return this.checkParam;
    }

    public void setCheckParam(CheckParam checkParam) {
        this.checkParam = checkParam;
    }
}

