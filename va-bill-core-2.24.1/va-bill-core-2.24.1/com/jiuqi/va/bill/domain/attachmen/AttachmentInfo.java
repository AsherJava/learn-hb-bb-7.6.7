/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.domain.attachmen;

public class AttachmentInfo {
    private String filename;
    private String quotecode;
    private String codeField;
    private String codeTable;
    private String numFiled;
    private String numTable;
    private boolean success;
    private String message;

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getQuotecode() {
        return this.quotecode;
    }

    public void setQuotecode(String quotecode) {
        this.quotecode = quotecode;
    }

    public String getCodeField() {
        return this.codeField;
    }

    public void setCodeField(String codeField) {
        this.codeField = codeField;
    }

    public String getCodeTable() {
        return this.codeTable;
    }

    public void setCodeTable(String codeTable) {
        this.codeTable = codeTable;
    }

    public String getNumFiled() {
        return this.numFiled;
    }

    public void setNumFiled(String numFiled) {
        this.numFiled = numFiled;
    }

    public String getNumTable() {
        return this.numTable;
    }

    public void setNumTable(String numTable) {
        this.numTable = numTable;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

