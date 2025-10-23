/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 */
package com.jiuqi.nr.print.web.vo;

import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;

public class SearchResultVO {
    private String key;
    private String title;
    private String path;
    private String code;

    public SearchResultVO() {
    }

    public SearchResultVO(String key, String title, String code) {
        this.key = key;
        this.title = title;
        this.code = code;
        this.path = title;
    }

    public SearchResultVO(DesignFormDefine formDefine, String path) {
        this.key = formDefine.getKey();
        this.title = formDefine.getTitle();
        this.code = formDefine.getFormCode();
        this.path = path;
    }

    public SearchResultVO(DesignFormGroupDefine formGroupDefine, String path) {
        this.key = formGroupDefine.getKey();
        this.title = formGroupDefine.getTitle();
        this.code = formGroupDefine.getCode();
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

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

