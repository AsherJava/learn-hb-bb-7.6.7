/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
package com.jiuqi.nr.fieldselect.define;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.fieldselect.define.FormDataSerializer;

@JsonSerialize(using=FormDataSerializer.class)
public class FormData {
    private String id;
    private String title;
    private String code;
    private String serialNumber;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setSerialNum(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCode() {
        return this.code;
    }

    public String getSerialNum() {
        return this.serialNumber;
    }
}

