/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.examine.web.bean;

import java.util.HashMap;
import java.util.Map;

public class BaseDimVO {
    private String id;
    private String code;
    private String title;
    private Map<String, String> items;

    public BaseDimVO() {
    }

    public BaseDimVO(String id, String code, String title) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.items = new HashMap<String, String>();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Map<String, String> getItems() {
        return this.items;
    }

    public void setItems(Map<String, String> items) {
        this.items = items;
    }
}

