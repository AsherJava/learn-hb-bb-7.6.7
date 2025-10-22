/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.format.NegativeStyle
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 */
package com.jiuqi.nr.datacrud.impl.format.dto;

import com.jiuqi.np.definition.internal.format.NegativeStyle;
import com.jiuqi.np.definition.internal.impl.FormatProperties;

public class FormatPropertiesDTO {
    private FormatProperties formatProperties;
    private NegativeStyle negativeStyle;

    public FormatProperties getFormatProperties() {
        return this.formatProperties;
    }

    public void setFormatProperties(FormatProperties formatProperties) {
        this.formatProperties = formatProperties;
    }

    public NegativeStyle getNegativeStyle() {
        return this.negativeStyle;
    }

    public void setNegativeStyle(NegativeStyle negativeStyle) {
        this.negativeStyle = negativeStyle;
    }
}

