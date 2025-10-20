/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.link.provider;

import com.jiuqi.nvwa.link.provider.ResourceParamDataType;
import com.jiuqi.nvwa.link.provider.ResourceParamType;

public class ResourceParam {
    private String code;
    private String title;
    private ResourceParamType type;
    private ResourceParamDataType dataType;
    private String desc;

    public ResourceParam() {
    }

    public ResourceParam(String code, String title, ResourceParamDataType dataType, String desc) {
        this(code, title, ResourceParamType.NORMAL, dataType, desc);
    }

    public ResourceParam(String code, String title, ResourceParamType type, ResourceParamDataType dataType, String desc) {
        this.code = code;
        this.title = title;
        this.type = type;
        this.dataType = dataType;
        this.desc = desc;
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

    public ResourceParamType getType() {
        return this.type;
    }

    public void setType(ResourceParamType type) {
        this.type = type;
    }

    public ResourceParamDataType getDataType() {
        return this.dataType;
    }

    public void setDataType(ResourceParamDataType dataType) {
        this.dataType = dataType;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

