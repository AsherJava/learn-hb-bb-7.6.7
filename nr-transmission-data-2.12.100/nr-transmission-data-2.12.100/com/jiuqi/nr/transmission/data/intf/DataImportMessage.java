/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.intf;

import java.io.Serializable;

public class DataImportMessage
implements Serializable {
    public static final String UN_KNOW_FORM_KEY = "00000000-0000-0000-0000-000000000000";
    public static final String UN_KNOW_CODE = "00000000";
    public static final String ALL_UNI_CODE = "-";
    public static final String ALL_UNI_TITLE = "\u6240\u6709\u5355\u4f4d";
    private String title;
    private String code;
    private String key;
    private String message;

    public DataImportMessage() {
    }

    public DataImportMessage(String title, String code, String message) {
        this.title = title;
        this.code = code;
        this.message = message;
    }

    public DataImportMessage(String title, String code, String key, String message) {
        this.title = title;
        this.code = code;
        this.key = key;
        this.message = message;
    }

    public DataImportMessage(String title, String code) {
        this.title = title;
        this.code = code;
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

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

