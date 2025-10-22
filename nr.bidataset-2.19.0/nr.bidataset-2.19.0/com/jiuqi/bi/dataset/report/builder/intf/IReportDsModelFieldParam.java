/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.report.builder.intf;

public interface IReportDsModelFieldParam {
    public String getKey();

    public String getCode();

    public String getTitle();

    public String getTableName();

    public String getExpression();

    public boolean isFmdmField();

    public String getDim();

    public String getTaskKey();
}

