/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.enums.OptionType
 *  com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeOption
 */
package com.jiuqi.bde.plugin.sap.option;

import com.jiuqi.dc.base.common.enums.OptionType;
import com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeOption;
import org.springframework.stereotype.Component;

@Component
public class SapIncludePlDataOption
implements IDataSchemeOption {
    public static final String INCLUDE_PL_DATA = "sapIncludePlData";
    public static final String DEFAULT_VALUE = "false";

    public String getCode() {
        return INCLUDE_PL_DATA;
    }

    public String getTitle() {
        return "\u5305\u542b\u5386\u53f2\u5e74\u5ea6\u635f\u76ca\u6570\u636e";
    }

    public OptionType getOptionType() {
        return OptionType.BOOLEAN;
    }

    public String defaultValue() {
        return DEFAULT_VALUE;
    }
}

