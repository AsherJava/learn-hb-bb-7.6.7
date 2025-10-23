/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.datascheme.bean;

import com.jiuqi.nr.query.datascheme.bean.FieldRel;
import java.util.List;

public class TableRelInfo {
    private String desTableKey;
    private String srcTableKey;
    private String dataSchemeKey;
    private List<FieldRel> fieldRels;
    private String desTableFormatTitle;

    public String getDesTableKey() {
        return this.desTableKey;
    }

    public void setDesTableKey(String desTableKey) {
        this.desTableKey = desTableKey;
    }

    public String getSrcTableKey() {
        return this.srcTableKey;
    }

    public void setSrcTableKey(String srcTableKey) {
        this.srcTableKey = srcTableKey;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public List<FieldRel> getFieldRels() {
        return this.fieldRels;
    }

    public void setFieldRels(List<FieldRel> fieldRels) {
        this.fieldRels = fieldRels;
    }

    public String getDesTableFormatTitle() {
        return this.desTableFormatTitle;
    }

    public void setDesTableFormatTitle(String desTableFormatTitle) {
        this.desTableFormatTitle = desTableFormatTitle;
    }
}

