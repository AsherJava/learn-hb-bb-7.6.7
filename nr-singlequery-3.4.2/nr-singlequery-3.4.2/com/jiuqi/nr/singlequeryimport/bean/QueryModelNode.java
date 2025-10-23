/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequeryimport.bean;

public class QueryModelNode {
    private String id;
    private String title;
    private String org;
    private Integer custom;
    private Integer disUse;
    private String order;
    private String item;
    private boolean canRead;
    private boolean canWrite;
    private String forewarnCondition;
    private boolean canAuthorize;

    public boolean isCanRead() {
        return this.canRead;
    }

    public void setCanRead(boolean canRead) {
        this.canRead = canRead;
    }

    public boolean isCanWrite() {
        return this.canWrite;
    }

    public void setCanWrite(boolean canWrite) {
        this.canWrite = canWrite;
    }

    public boolean isCanAuthorize() {
        return this.canAuthorize;
    }

    public void setCanAuthorize(boolean canAuthorize) {
        this.canAuthorize = canAuthorize;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDisUse() {
        return this.disUse;
    }

    public void setDisUse(Integer disUse) {
        this.disUse = disUse;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getOrg() {
        return this.org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public Integer getCustom() {
        return this.custom;
    }

    public void setCustom(Integer custom) {
        this.custom = custom;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getItem() {
        return this.item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getForewarnCondition() {
        return this.forewarnCondition;
    }

    public void setForewarnCondition(String forewarnCondition) {
        this.forewarnCondition = forewarnCondition;
    }
}

