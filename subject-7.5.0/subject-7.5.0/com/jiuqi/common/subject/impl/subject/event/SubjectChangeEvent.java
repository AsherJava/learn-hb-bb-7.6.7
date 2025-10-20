/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.subject.impl.subject.event;

import java.util.UUID;

public class SubjectChangeEvent {
    private String code;
    private String name;
    private String parentid;
    private Integer orient;
    private String generalType;
    private String asstype;
    private UUID id;
    private Integer stopFlag;
    private Boolean stopChildItem;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentid() {
        return this.parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public Integer getOrient() {
        return this.orient;
    }

    public void setOrient(Integer orient) {
        this.orient = orient;
    }

    public String getGeneralType() {
        return this.generalType;
    }

    public void setGeneralType(String generalType) {
        this.generalType = generalType;
    }

    public String getAsstype() {
        return this.asstype;
    }

    public void setAsstype(String asstype) {
        this.asstype = asstype;
    }

    public Integer getStopFlag() {
        return this.stopFlag;
    }

    public void setStopFlag(Integer stopFlag) {
        this.stopFlag = stopFlag;
    }

    public Boolean getStopChildItem() {
        return this.stopChildItem;
    }

    public void setStopChildItem(Boolean stopChildItem) {
        this.stopChildItem = stopChildItem;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}

