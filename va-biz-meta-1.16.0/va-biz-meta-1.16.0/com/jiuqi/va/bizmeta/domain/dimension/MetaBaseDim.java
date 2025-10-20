/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.va.bizmeta.domain.dimension;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class MetaBaseDim {
    private String name;
    private String title;
    private String moduleName;

    public MetaBaseDim() {
    }

    public MetaBaseDim(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public MetaBaseDim(String name, String title, String moduleName) {
        this.name = name;
        this.title = title;
        this.moduleName = moduleName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}

