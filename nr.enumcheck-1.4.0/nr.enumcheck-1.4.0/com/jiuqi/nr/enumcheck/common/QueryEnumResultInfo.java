/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.enumcheck.common;

import com.jiuqi.nr.enumcheck.common.EnumDataCheckResultInfo;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.ArrayList;
import java.util.List;

public class QueryEnumResultInfo {
    private JtableContext context;
    private String asyncTaskId;
    private String viewType;
    private String groupKey;
    private int itemOffset;
    private int itemPageSize;
    private int groupOffset;
    private EnumDataCheckResultInfo enumDataCheckResultInfo;
    private List<String> orgCodes = new ArrayList<String>();

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
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

    public String getViewType() {
        return this.viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public EnumDataCheckResultInfo getEnumDataCheckResultInfo() {
        return this.enumDataCheckResultInfo;
    }

    public void setEnumDataCheckResultInfo(EnumDataCheckResultInfo enumDataCheckResultInfo) {
        this.enumDataCheckResultInfo = enumDataCheckResultInfo;
    }

    public int getItemOffset() {
        return this.itemOffset;
    }

    public void setItemOffset(int itemOffset) {
        this.itemOffset = itemOffset;
    }

    public int getItemPageSize() {
        return this.itemPageSize;
    }

    public void setItemPageSize(int itemPageSize) {
        this.itemPageSize = itemPageSize;
    }

    public int getGroupOffset() {
        return this.groupOffset;
    }

    public void setGroupOffset(int groupOffset) {
        this.groupOffset = groupOffset;
    }

    public List<String> getOrgCodes() {
        return this.orgCodes;
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }
}

