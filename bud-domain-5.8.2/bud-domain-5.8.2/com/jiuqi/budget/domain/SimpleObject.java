/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.budget.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.va.mapper.domain.TenantDO;
import org.springframework.util.StringUtils;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class SimpleObject
extends TenantDO {
    private String code;
    private String name;
    private String title;
    private String id;
    private String value;

    public SimpleObject() {
    }

    public SimpleObject(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        if (!StringUtils.hasLength(this.title)) {
            return this.name;
        }
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

