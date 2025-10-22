/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.workingpaper.vo;

public class WorkingPaperQueryWayItemVO {
    private String id;
    private Long recver;
    private String title;
    private String qmsType;
    private String dxsType;
    private String creator;
    private Double floatOrder;
    private Integer workType;

    public WorkingPaperQueryWayItemVO() {
    }

    public WorkingPaperQueryWayItemVO(String id, Long recver, String title, String qmsType, String dxsType, String creator, Double floatOrder, Integer workType) {
        this.id = id;
        this.recver = recver;
        this.title = title;
        this.qmsType = qmsType;
        this.dxsType = dxsType;
        this.creator = creator;
        this.floatOrder = floatOrder;
        this.workType = workType;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getRecver() {
        return this.recver;
    }

    public void setRecver(Long recver) {
        this.recver = recver;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQmsType() {
        return this.qmsType;
    }

    public void setQmsType(String qmsType) {
        this.qmsType = qmsType;
    }

    public String getDxsType() {
        return this.dxsType;
    }

    public void setDxsType(String dxsType) {
        this.dxsType = dxsType;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Double getFloatOrder() {
        return this.floatOrder;
    }

    public void setFloatOrder(Double floatOrder) {
        this.floatOrder = floatOrder;
    }

    public Integer getWorkType() {
        return this.workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }
}

