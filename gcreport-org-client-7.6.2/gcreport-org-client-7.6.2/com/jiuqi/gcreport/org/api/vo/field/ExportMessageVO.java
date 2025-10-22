/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.api.vo.field;

public class ExportMessageVO {
    private String type;
    private String message;
    private int order;

    public ExportMessageVO setWarn(String message, int i) {
        ExportMessageVO e = new ExportMessageVO();
        e.setType("WARN");
        e.setMessage(message);
        e.setOrder(i);
        return e;
    }

    public ExportMessageVO setINFO(String message, int i) {
        ExportMessageVO e = new ExportMessageVO();
        e.setType("INFO");
        e.setMessage(message);
        e.setOrder(i);
        return e;
    }

    public ExportMessageVO setERROR(String message, int i) {
        ExportMessageVO e = new ExportMessageVO();
        e.setType("ERROR");
        e.setMessage(message);
        e.setOrder(i);
        return e;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}

