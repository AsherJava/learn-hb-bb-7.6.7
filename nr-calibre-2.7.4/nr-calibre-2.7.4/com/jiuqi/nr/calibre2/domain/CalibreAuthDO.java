/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.domain;

import java.util.Map;

public class CalibreAuthDO {
    private String id;
    private String authAccept;
    private String name;
    private String calibreDefine;
    private String calibreItem;
    private Map<String, String> auth;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthAccept() {
        return this.authAccept;
    }

    public void setAuthAccept(String authAccept) {
        this.authAccept = authAccept;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalibreDefine() {
        return this.calibreDefine;
    }

    public void setCalibreDefine(String calibreDefine) {
        this.calibreDefine = calibreDefine;
    }

    public String getCalibreItem() {
        return this.calibreItem;
    }

    public void setCalibreItem(String calibreItem) {
        this.calibreItem = calibreItem;
    }

    public Map<String, String> getAuth() {
        return this.auth;
    }

    public void setAuth(Map<String, String> auth) {
        this.auth = auth;
    }
}

