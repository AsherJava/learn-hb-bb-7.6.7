/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 */
package com.jiuqi.bde.floatmodel.plugin.controller;

import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.bde.common.intf.FetchTaskContext;

public class VaQueryConfigInfo
extends FetchTaskContext {
    private QueryConfigInfo queryConfigInfo;

    public QueryConfigInfo getQueryConfigInfo() {
        return this.queryConfigInfo;
    }

    public void setQueryConfigInfo(QueryConfigInfo queryConfigInfo) {
        this.queryConfigInfo = queryConfigInfo;
    }

    public String toString() {
        return "VaQueryConfigInfo{queryConfigInfo=" + this.queryConfigInfo + '}';
    }
}

