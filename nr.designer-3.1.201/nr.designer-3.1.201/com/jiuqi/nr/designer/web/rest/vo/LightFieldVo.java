/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.jiuqi.np.definition.facade.FieldDefine;

public class LightFieldVo {
    private String key;
    private String title;
    private String code;

    public LightFieldVo() {
    }

    public LightFieldVo(FieldDefine fieldDefine) {
        this.key = fieldDefine.getKey();
        this.title = fieldDefine.getTitle();
        this.code = fieldDefine.getCode();
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean equals(Object obj) {
        if (obj instanceof LightFieldVo) {
            String key1 = ((LightFieldVo)obj).getKey();
            if (key1 == null || this.key == null) {
                return false;
            }
            return key1.equals(this.key);
        }
        return super.equals(obj);
    }
}

