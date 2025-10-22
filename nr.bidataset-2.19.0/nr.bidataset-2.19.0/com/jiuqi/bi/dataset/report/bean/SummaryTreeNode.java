/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryGroup
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.datascheme.api.core.INode
 */
package com.jiuqi.bi.dataset.report.bean;

import com.jiuqi.nr.batch.summary.storage.entity.SummaryGroup;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.datascheme.api.core.INode;

public class SummaryTreeNode
implements INode {
    private static int GROUP_TYPE = 0;
    private static int SCHEME_TYPE = 1;
    private String key;
    private String code;
    private String title;
    private int type;

    public SummaryTreeNode(SummaryGroup summaryGroup) {
        this.key = summaryGroup.getKey();
        this.title = summaryGroup.getTitle();
        this.type = GROUP_TYPE;
    }

    public SummaryTreeNode(SummaryScheme summaryScheme) {
        this.key = summaryScheme.getKey();
        this.code = summaryScheme.getCode();
        this.title = summaryScheme.getTitle();
        this.type = SCHEME_TYPE;
    }

    public String getKey() {
        return this.key;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

