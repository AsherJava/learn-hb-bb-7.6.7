/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.subject.impl.subject.expimp.intf.impl;

import com.jiuqi.common.subject.impl.subject.data.DataType;
import com.jiuqi.common.subject.impl.subject.expimp.intf.ISubjectExpImpFieldDefine;

public class SubjectExpImpFieldDefine
implements ISubjectExpImpFieldDefine {
    private String code;
    private String name;
    private DataType dataType;
    private boolean required;
    private Integer width;
    private String remark;
    private String example;

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public DataType getDataType() {
        return this.dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    @Override
    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public Integer getWidth() {
        return this.width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Override
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String getExample() {
        return this.example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}

