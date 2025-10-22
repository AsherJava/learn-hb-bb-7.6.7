/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.bean;

import com.jiuqi.nr.single.core.syntax.bean.BaseCellDataType;

public class StringDataType
extends BaseCellDataType {
    private String sourceCode;
    private String destCode;

    public String getSourceCode() {
        return this.sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getDestCode() {
        return this.destCode;
    }

    public void setDestCode(String destCode) {
        this.destCode = destCode;
    }
}

