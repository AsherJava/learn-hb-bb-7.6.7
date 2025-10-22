/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.adapter.impl.basedata;

import com.jiuqi.nr.entity.model.ITreeStruct;

public class BaseDataTreeStruct
implements ITreeStruct {
    private String levelCode;
    private boolean fixedSize;
    private Integer structType;
    private Integer codeSize;

    @Override
    public String getLevelCode() {
        return this.levelCode;
    }

    @Override
    public boolean isFixedSize() {
        return this.fixedSize;
    }

    @Override
    public Integer getStructType() {
        return this.structType;
    }

    @Override
    public Integer getCodeSize() {
        return this.codeSize;
    }

    public void setCodeSize(Integer codeSize) {
        this.codeSize = codeSize;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public void setFixedSize(boolean fixedSize) {
        this.fixedSize = fixedSize;
    }

    public void setStructType(Integer structType) {
        this.structType = structType;
    }
}

