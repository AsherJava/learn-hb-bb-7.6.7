/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.treebean;

import java.math.BigDecimal;

public class AmountObject {
    private String id;
    private String code;
    private String title;
    private String parent;
    private BigDecimal ratio;
    private Integer baseunit;
    private String orderl;

    public AmountObject() {
    }

    public AmountObject(String id, String code, String title, String parent, BigDecimal ratio, Integer baseunit, String orderl) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.parent = parent;
        this.ratio = ratio;
        this.baseunit = baseunit;
        this.orderl = orderl;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public BigDecimal getRatio() {
        return this.ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public Integer getBaseunit() {
        return this.baseunit;
    }

    public void setBaseunit(Integer baseunit) {
        this.baseunit = baseunit;
    }

    public String getOrderl() {
        return this.orderl;
    }

    public void setOrderl(String orderl) {
        this.orderl = orderl;
    }
}

