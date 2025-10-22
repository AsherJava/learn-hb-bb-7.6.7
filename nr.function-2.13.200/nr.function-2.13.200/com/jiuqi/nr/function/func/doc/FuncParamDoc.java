/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.function.func.doc;

public class FuncParamDoc {
    private String name;
    private String sortName;
    private String type;
    private String desc;

    public FuncParamDoc() {
    }

    public FuncParamDoc(String name, String sortName, String type, String desc) {
        this.name = name;
        this.sortName = sortName;
        this.type = type;
        this.desc = desc;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortName() {
        return this.sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

