/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.utils.excel.core;

public interface ExcelEntity {
    public String getNo();

    public void setNo(String var1);

    public String getExpression();

    public void setExpression(String var1);

    public String getDescription();

    public void setDescription(String var1);

    public String getType();

    public void setType(String var1);

    public String getAuditType();

    public void setAuditType(String var1);

    public String getAdjustIndicator();

    public void setAdjustIndicator(String var1);

    public ExcelEntity clone();
}

