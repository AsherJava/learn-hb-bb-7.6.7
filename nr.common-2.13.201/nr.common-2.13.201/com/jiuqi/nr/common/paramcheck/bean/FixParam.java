/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.paramcheck.bean;

public class FixParam {
    private String bean;
    private Object obj;

    public FixParam() {
    }

    public FixParam(String bean, Object obj) {
        this.bean = bean;
        this.obj = obj;
    }

    public String getBean() {
        return this.bean;
    }

    public void setBean(String bean) {
        this.bean = bean;
    }

    public Object getObj() {
        return this.obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}

