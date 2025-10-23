/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.web.result;

import com.jiuqi.nr.multcheck2.common.CheckRestultState;

public class ResultItemTBVO {
    private String key;
    private String title;
    private CheckRestultState state;
    private int success;
    private int failed;
    private int ignore;
    private String time;
    private boolean alwaysDisplayView = true;

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

    public CheckRestultState getState() {
        return this.state;
    }

    public void setState(CheckRestultState state) {
        this.state = state;
    }

    public int getSuccess() {
        return this.success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailed() {
        return this.failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }

    public int getIgnore() {
        return this.ignore;
    }

    public void setIgnore(int ignore) {
        this.ignore = ignore;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isAlwaysDisplayView() {
        return this.alwaysDisplayView;
    }

    public void setAlwaysDisplayView(boolean alwaysDisplayView) {
        this.alwaysDisplayView = alwaysDisplayView;
    }
}

