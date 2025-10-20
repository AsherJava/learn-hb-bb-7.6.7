/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.nr.vo;

import java.util.ArrayList;
import java.util.List;

public class FormTreeVo {
    private String id;
    private String title;
    private String code;
    private String serialNumber;
    private List<FormTreeVo> children = new ArrayList<FormTreeVo>();

    public FormTreeVo() {
    }

    public FormTreeVo(String id, String title, String code, String serialNumber, List<FormTreeVo> children) {
        this.id = id;
        this.title = title;
        this.code = code;
        this.serialNumber = serialNumber;
        this.children = children;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<FormTreeVo> getChildren() {
        return this.children;
    }

    public void setChildren(List<FormTreeVo> children) {
        this.children = children;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}

