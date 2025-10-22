/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.vo;

public class ClbrOverViewVO {
    private String relation;
    private String relationTitle;
    private double rate;
    private Integer total;
    private Integer confirmCount;
    private double confirmRate;
    private Integer partConfirmCount;
    private double partConfirmRate;
    private Integer notConfirmCount;
    private double notConfirmRate;
    private Integer rejectCount;
    private double rejectRate;
    private Double confirmAmount;
    private Double notConfirmAmount;
    private boolean hasChildren;

    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getRelationTitle() {
        return this.relationTitle;
    }

    public void setRelationTitle(String relationTitle) {
        this.relationTitle = relationTitle;
    }

    public Double getRate() {
        return this.rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getConfirmCount() {
        return this.confirmCount;
    }

    public void setConfirmCount(Integer confirmCount) {
        this.confirmCount = confirmCount;
    }

    public double getConfirmRate() {
        return this.confirmRate;
    }

    public void setConfirmRate(double confirmRate) {
        this.confirmRate = confirmRate;
    }

    public Integer getPartConfirmCount() {
        return this.partConfirmCount;
    }

    public void setPartConfirmCount(Integer partConfirmCount) {
        this.partConfirmCount = partConfirmCount;
    }

    public double getPartConfirmRate() {
        return this.partConfirmRate;
    }

    public void setPartConfirmRate(double partConfirmRate) {
        this.partConfirmRate = partConfirmRate;
    }

    public Integer getNotConfirmCount() {
        return this.notConfirmCount;
    }

    public void setNotConfirmCount(Integer notConfirmCount) {
        this.notConfirmCount = notConfirmCount;
    }

    public double getNotConfirmRate() {
        return this.notConfirmRate;
    }

    public void setNotConfirmRate(double notConfirmRate) {
        this.notConfirmRate = notConfirmRate;
    }

    public Integer getRejectCount() {
        return this.rejectCount;
    }

    public void setRejectCount(Integer rejectCount) {
        this.rejectCount = rejectCount;
    }

    public double getRejectRate() {
        return this.rejectRate;
    }

    public void setRejectRate(double rejectRate) {
        this.rejectRate = rejectRate;
    }

    public Double getConfirmAmount() {
        return this.confirmAmount;
    }

    public void setConfirmAmount(Double confirmAmount) {
        this.confirmAmount = confirmAmount;
    }

    public Double getNotConfirmAmount() {
        return this.notConfirmAmount;
    }

    public void setNotConfirmAmount(Double notConfirmAmount) {
        this.notConfirmAmount = notConfirmAmount;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public boolean isHasChildren() {
        return this.hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }
}

