/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.result;

public class CheckFailNodeInfo {
    private String code;
    private String attributeCode;
    private Object value;
    private String message;
    private int checkOption;

    public CheckFailNodeInfo() {
    }

    public CheckFailNodeInfo(String code, String attributeCode, Object value, String message) {
        this.code = code;
        this.attributeCode = attributeCode;
        this.value = value;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAttributeCode() {
        return this.attributeCode;
    }

    public void setAttributeCode(String attributeCode) {
        this.attributeCode = attributeCode;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCheckOption() {
        return this.checkOption;
    }

    public void setCheckOption(int checkOption) {
        this.checkOption = checkOption;
    }
}

