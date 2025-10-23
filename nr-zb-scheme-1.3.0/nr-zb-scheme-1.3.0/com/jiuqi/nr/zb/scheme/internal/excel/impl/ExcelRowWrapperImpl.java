/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.builder.ToStringBuilder
 */
package com.jiuqi.nr.zb.scheme.internal.excel.impl;

import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowWrapper;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.CollectionUtils;

public class ExcelRowWrapperImpl
implements IExcelRowWrapper {
    private Object data;
    private String code;
    private List<String> errors;
    private int rowNum;
    private boolean group;

    public ExcelRowWrapperImpl() {
    }

    public ExcelRowWrapperImpl(Object data) {
        this.data = data;
    }

    public ExcelRowWrapperImpl(Object data, String code) {
        this.data = data;
        this.code = code;
    }

    @Override
    public Object getData() {
        return this.data;
    }

    @Override
    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int getRowNum() {
        return this.rowNum;
    }

    @Override
    public void setRowNum(int row) {
        this.rowNum = row;
    }

    @Override
    public boolean isGroup() {
        return this.group;
    }

    @Override
    public void setGroup(boolean group) {
        this.group = group;
    }

    @Override
    public boolean isValid() {
        return CollectionUtils.isEmpty(this.errors);
    }

    @Override
    public List<String> getErrors() {
        if (this.errors == null) {
            this.errors = new ArrayList<String>();
        }
        return this.errors;
    }

    public String toString() {
        return new ToStringBuilder((Object)this).append("code", (Object)this.code).append("rowNum", this.rowNum).append("group", this.group).append("data", this.data).toString();
    }
}

