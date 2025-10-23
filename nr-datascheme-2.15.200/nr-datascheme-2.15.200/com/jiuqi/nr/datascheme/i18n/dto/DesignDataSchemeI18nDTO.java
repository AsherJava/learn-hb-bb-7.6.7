/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotNull
 *  javax.validation.constraints.Size
 */
package com.jiuqi.nr.datascheme.i18n.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DesignDataSchemeI18nDTO
implements Serializable {
    private static final long serialVersionUID = -1293719544575389612L;
    @Size(max=40, message="{key.size}")
    private @Size(max=40, message="{key.size}") String key;
    @NotNull(message="{type.notNull}")
    private @NotNull(message="{type.notNull}") String type;
    @Size(max=200, message="{title.size}")
    private @Size(max=200, message="{title.size}") String title;
    @Size(max=1000, message="{desc.size}")
    private @Size(max=1000, message="{desc.size}") String desc;
    @NotNull(message="{dataSchemeKey.notNull}")
    @Size(max=40, message="{dataSchemeKey.size}")
    private @NotNull(message="{dataSchemeKey.notNull}") @Size(max=40, message="{dataSchemeKey.size}") String dataSchemeKey;
    @NotNull(message="{tableCode.notNull}")
    private @NotNull(message="{tableCode.notNull}") String tableCode;
    @NotNull(message="{fieldCode.notNull}")
    private @NotNull(message="{fieldCode.notNull}") String fieldCode;
    @NotNull(message="{fieldTitle.notNull}")
    private @NotNull(message="{fieldTitle.notNull}") String fieldTitle;
    @NotNull(message="{fieldDesc.notNull}")
    private @NotNull(message="{fieldDesc.notNull}") String fieldDesc;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public String getFieldDesc() {
        return this.fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc;
    }
}

