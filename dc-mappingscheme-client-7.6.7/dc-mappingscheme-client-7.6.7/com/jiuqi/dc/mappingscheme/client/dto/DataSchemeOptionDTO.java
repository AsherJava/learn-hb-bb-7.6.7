/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.dto;

import com.jiuqi.dc.mappingscheme.client.option.DataSchemeOptionValue;
import java.io.Serializable;

public class DataSchemeOptionDTO
implements Serializable {
    private static final long serialVersionUID = 4774091969866351446L;
    private String code;
    private String title;
    private DataSchemeOptionValue optionValue;

    public DataSchemeOptionDTO(String code, String title) {
        this.code = code;
        this.title = title;
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

    public DataSchemeOptionValue getOptionValue() {
        return this.optionValue;
    }

    public void setOptionValue(DataSchemeOptionValue optionValue) {
        this.optionValue = optionValue;
    }
}

