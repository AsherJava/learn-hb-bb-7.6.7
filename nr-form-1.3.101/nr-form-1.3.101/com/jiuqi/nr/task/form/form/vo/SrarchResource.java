/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 */
package com.jiuqi.nr.task.form.form.vo;

import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.task.form.common.FomeTypeTitle;

public class SrarchResource {
    private String key;
    private String title;
    private String code;
    private String path;
    private String type;

    public SrarchResource() {
    }

    public SrarchResource(DesignFormDefine designFormDefine) {
        this.key = designFormDefine.getKey();
        this.title = designFormDefine.getTitle();
        this.code = designFormDefine.getFormCode();
        this.type = FomeTypeTitle.forTitle(designFormDefine.getFormType().getValue());
    }

    public SrarchResource(DesignFormDefine designFormDefine, String path) {
        this.key = designFormDefine.getKey();
        this.title = designFormDefine.getTitle();
        this.code = designFormDefine.getFormCode();
        this.type = FomeTypeTitle.forTitle(designFormDefine.getFormType().getValue());
        this.path = path;
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

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

