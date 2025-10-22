/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.internal.update;

import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.np.definition.internal.impl.FormatProperties;

@DBAnno.DBTable(dbTable="sys_fielddefine")
public class UpdateFd {
    @DBAnno.DBField(dbField="fd_type", tranWith="transFieldType", dbType=Integer.class, appType=FieldType.class, notUpdate=true)
    private FieldType type;
    @DBAnno.DBField(dbField="fd_show_format", notUpdate=true)
    private String showFormat;
    @DBAnno.DBField(dbField="fd_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="fd_fraction_digits", notUpdate=true)
    private int fractionDigits;
    @DBAnno.DBField(dbField="fd_format_properties", tranWith="transFormatProperties", dbType=String.class, appType=FormatProperties.class)
    private FormatProperties formatProperties;

    public FieldType getType() {
        return this.type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public String getShowFormat() {
        return this.showFormat;
    }

    public void setShowFormat(String showFormat) {
        this.showFormat = showFormat;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public FormatProperties getFormatProperties() {
        return this.formatProperties;
    }

    public void setFormatProperties(FormatProperties formatProperties) {
        this.formatProperties = formatProperties;
    }

    public int getFractionDigits() {
        return this.fractionDigits;
    }

    public void setFractionDigits(int fractionDigits) {
        this.fractionDigits = fractionDigits;
    }
}

