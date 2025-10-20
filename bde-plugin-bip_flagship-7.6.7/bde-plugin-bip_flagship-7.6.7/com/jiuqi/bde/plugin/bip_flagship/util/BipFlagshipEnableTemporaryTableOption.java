/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.enums.OptionType
 *  com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeOption
 */
package com.jiuqi.bde.plugin.bip_flagship.util;

import com.jiuqi.dc.base.common.enums.OptionType;
import com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeOption;
import org.springframework.stereotype.Component;

@Component
public class BipFlagshipEnableTemporaryTableOption
implements IDataSchemeOption {
    public static final String ENABLE_TEMPORARY_TABLE = "BipEnableTemporaryTable";
    public static final String DEFAULT_VALUE = "true";

    public String getCode() {
        return ENABLE_TEMPORARY_TABLE;
    }

    public String getTitle() {
        return "\u542f\u7528\u4e34\u65f6\u8868";
    }

    public OptionType getOptionType() {
        return OptionType.BOOLEAN;
    }

    public String defaultValue() {
        return DEFAULT_VALUE;
    }
}

