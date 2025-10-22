/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.config.multCheckTable.entity;

import java.util.List;

public class MultCheckTableData {
    private String s_key;
    private String s_name;
    private int s_order;
    private List<String> s_dw;
    private String taskkey;
    private String formSchemeKey;

    public String getS_key() {
        return this.s_key;
    }

    public void setS_key(String s_key) {
        this.s_key = s_key;
    }

    public String getS_name() {
        return this.s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public int getS_order() {
        return this.s_order;
    }

    public void setS_order(int s_order) {
        this.s_order = s_order;
    }

    public List<String> getS_dw() {
        return this.s_dw;
    }

    public void setS_dw(List<String> s_dw) {
        this.s_dw = s_dw;
    }

    public String getTaskkey() {
        return this.taskkey;
    }

    public void setTaskkey(String taskkey) {
        this.taskkey = taskkey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }
}

