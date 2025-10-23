/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.excel;

import java.util.List;

public interface IExcelRowWrapper {
    public String getCode();

    public void setCode(String var1);

    public Object getData();

    public void setData(Object var1);

    public int getRowNum();

    public void setRowNum(int var1);

    public boolean isGroup();

    public void setGroup(boolean var1);

    public boolean isValid();

    public List<String> getErrors();

    default public void addErrors(List<String> errors) {
        this.getErrors().addAll(errors);
    }

    default public void addError(String error) {
        this.getErrors().add(error);
    }
}

