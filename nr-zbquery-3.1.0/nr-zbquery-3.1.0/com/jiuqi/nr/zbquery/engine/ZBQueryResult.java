/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.quickreport.engine.build.hyperlink.HyperlinkEnv
 */
package com.jiuqi.nr.zbquery.engine;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.build.hyperlink.HyperlinkEnv;
import com.jiuqi.nr.zbquery.model.PageInfo;
import java.util.Map;

public class ZBQueryResult {
    private GridData data;
    private PageInfo pageInfo;
    private String[] colNames;
    private HyperlinkEnv hyperlinkEnv;
    private Map<String, String> aliasFullNameMapper;

    public GridData getData() {
        return this.data;
    }

    public void setData(GridData data) {
        this.data = data;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public String[] getColNames() {
        return this.colNames;
    }

    public void setColNames(String[] colNames) {
        this.colNames = colNames;
    }

    public HyperlinkEnv getHyperlinkEnv() {
        return this.hyperlinkEnv;
    }

    public void setHyperlinkEnv(HyperlinkEnv hyperlinkEnv) {
        this.hyperlinkEnv = hyperlinkEnv;
    }

    public Map<String, String> getAliasFullNameMapper() {
        return this.aliasFullNameMapper;
    }

    public void setAliasFullNameMapper(Map<String, String> aliasFullNameMapper) {
        this.aliasFullNameMapper = aliasFullNameMapper;
    }
}

