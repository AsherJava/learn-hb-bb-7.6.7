/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.batch.summary.storage.entity.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow;
import com.jiuqi.nr.batch.summary.storage.entity.impl.CustomCalibreValue;
import com.jiuqi.nr.batch.summary.storage.entity.serializer.CustomCalibreRowSerializer;
import com.jiuqi.util.StringUtils;

@JsonSerialize(using=CustomCalibreRowSerializer.class)
public class CustomCalibreRowDefine
implements CustomCalibreRow {
    private String key;
    private String code;
    private String title;
    private String scheme;
    private String parentCode;
    private String ordinal;
    private CustomCalibreValue value;

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    @Override
    public CustomCalibreValue getValue() {
        return this.value;
    }

    public void setValue(CustomCalibreValue value) {
        this.value = value;
    }

    @Override
    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    @Override
    public String getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }

    @Override
    @JsonIgnore
    public boolean isValidRow() {
        return StringUtils.isNotEmpty((String)this.getCode()) && StringUtils.isNotEmpty((String)this.getTitle());
    }
}

