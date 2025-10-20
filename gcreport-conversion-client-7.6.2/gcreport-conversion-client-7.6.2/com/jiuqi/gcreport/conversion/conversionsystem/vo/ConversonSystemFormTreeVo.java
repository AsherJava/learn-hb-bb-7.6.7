/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.conversionsystem.vo;

import java.util.ArrayList;
import java.util.List;

public class ConversonSystemFormTreeVo {
    private String id;
    private String title;
    private String code;
    private String serialNumber;
    private List<ConversonSystemFormTreeVo> children = new ArrayList<ConversonSystemFormTreeVo>();

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

    public List<ConversonSystemFormTreeVo> getChildren() {
        return this.children;
    }

    public void setChildren(List<ConversonSystemFormTreeVo> children) {
        this.children = children;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}

