/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.dc.base.common.intf.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.dc.base.common.intf.IEntity;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class Entity
implements IEntity {
    private static final long serialVersionUID = 5969842703567162837L;
    private String code;
    private String name;
    private String type;

    public Entity() {
    }

    public Entity(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Entity(String code, String name, String type) {
        this.code = code;
        this.name = name;
        this.type = type;
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
}

