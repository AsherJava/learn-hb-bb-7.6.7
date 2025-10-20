/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.intf.impl;

import com.jiuqi.dc.base.common.intf.IAssDimTableDefine;
import com.jiuqi.dc.base.common.intf.IMoudle;

public class AssDimTableDefine
implements IAssDimTableDefine {
    private IMoudle moudle;
    private String code;
    private String name;
    private String type;
    private int order = Integer.MIN_VALUE;

    public AssDimTableDefine() {
    }

    public AssDimTableDefine(IMoudle moudle, String code, String name, String type) {
        this.moudle = moudle;
        this.code = code;
        this.name = name;
        this.type = type;
    }

    public AssDimTableDefine(IMoudle moudle, String code, String name, String type, int order) {
        this.moudle = moudle;
        this.code = code;
        this.name = name;
        this.type = type;
        this.order = order;
    }

    @Override
    public IMoudle getMoudle() {
        return this.moudle;
    }

    public void setMoudle(IMoudle moudle) {
        this.moudle = moudle;
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
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}

