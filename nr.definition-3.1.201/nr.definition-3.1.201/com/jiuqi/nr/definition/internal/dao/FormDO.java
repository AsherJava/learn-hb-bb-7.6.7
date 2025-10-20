/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormDefineImpl;

class FormDO {
    private String key;
    private String code;
    private String title;
    private int type;

    FormDO() {
    }

    public static FormDefine toFormDefine(FormDO formDO, String formSchemeKey) {
        RunTimeFormDefineImpl formDefine = new RunTimeFormDefineImpl();
        formDefine.setKey(formDO.getKey());
        formDefine.setFormCode(formDO.getCode());
        formDefine.setTitle(formDO.getTitle());
        formDefine.setFormType(FormType.forValue(formDO.getType()));
        formDefine.setFormScheme(formSchemeKey);
        return formDefine;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

