/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.unionrule.vo;

public class ImportMessageVO {
    private String title;
    private String message;

    public ImportMessageVO setERROR(String title, String message) {
        ImportMessageVO e = new ImportMessageVO();
        e.setTitle(title);
        e.setMessage(message);
        return e;
    }

    public int hashCode() {
        return this.message.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof ImportMessageVO) {
            ImportMessageVO importMessageVO = (ImportMessageVO)obj;
            return this.title.equals(importMessageVO.title) && this.message.equals(importMessageVO.message);
        }
        return super.equals(obj);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String type) {
        this.title = type;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

