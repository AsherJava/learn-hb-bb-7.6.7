/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.todo.bean;

import java.util.Map;

public class Infoview {
    private String name;
    private String type;
    private Map<String, Object> args;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getArgs() {
        return this.args;
    }

    public void setArgs(Map<String, Object> args) {
        this.args = args;
    }
}

