/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.subject.impl.subject.cache.intf.impl;

import com.jiuqi.common.subject.impl.subject.cache.intf.ICacheDefine;

public class CacheDefine
implements ICacheDefine {
    private static final long serialVersionUID = 7616444089122751899L;
    private String code;
    private String name;
    private Integer order = Integer.MAX_VALUE;

    public CacheDefine() {
    }

    public CacheDefine(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public CacheDefine(String code, String name, Integer order) {
        this.code = code;
        this.name = name;
        this.order = order;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getOrder() {
        return this.order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String toString() {
        return "CacheDefine [code=" + this.code + ", name=" + this.name + ", order=" + this.order + "]";
    }
}

