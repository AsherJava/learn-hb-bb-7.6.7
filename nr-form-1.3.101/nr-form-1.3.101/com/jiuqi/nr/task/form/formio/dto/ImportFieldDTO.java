/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.task.form.formio.dto;

import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.task.form.formio.format.FormatDTO;

public class ImportFieldDTO {
    private String title;
    private String code;
    private DataFieldType fieldType;
    private FormatDTO format;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataFieldType getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(DataFieldType fieldType) {
        this.fieldType = fieldType;
    }

    public FormatDTO getFormat() {
        return this.format;
    }

    public void setFormat(FormatDTO format) {
        this.format = format;
    }
}

