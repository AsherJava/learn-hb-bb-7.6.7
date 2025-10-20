/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 */
package com.jiuqi.nr.definition.internal.update;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.np.definition.internal.impl.FormatProperties;

@DBAnno.DBTable(dbTable="NR_PARAM_DATALINK")
public class UpdateDl {
    @DBAnno.DBField(dbField="dl_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="dl_show_format")
    private String showFormat;
    @DBAnno.DBField(dbField="dl_format_properties", tranWith="transFormatProperties", dbType=String.class, appType=FormatProperties.class)
    private FormatProperties formatProperties;
    private int fractionDigits;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getShowFormat() {
        return this.showFormat;
    }

    public void setShowFormat(String showFormat) {
        this.showFormat = showFormat;
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

