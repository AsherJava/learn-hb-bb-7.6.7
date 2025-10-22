/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.nr.bpm.instance.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class GridDataItem {
    private String key;
    private String code;
    private String title;
    private int state;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date startTime;
    private boolean checked;

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public static enum StartState {
        SUCCESS(1),
        FAIL(2),
        PART_SUCESS(3),
        STOP(4);

        private int state;

        private StartState(int state) {
            this.state = state;
        }

        public int getState() {
            return this.state;
        }
    }
}

