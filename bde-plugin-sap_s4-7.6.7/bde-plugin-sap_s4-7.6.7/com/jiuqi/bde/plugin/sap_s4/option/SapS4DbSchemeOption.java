/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.enums.OptionType
 *  com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeOption
 */
package com.jiuqi.bde.plugin.sap_s4.option;

import com.jiuqi.dc.base.common.enums.OptionType;
import com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeOption;
import org.springframework.stereotype.Component;

@Component
public class SapS4DbSchemeOption
implements IDataSchemeOption {
    public static final String OPTION_CODE = "sapS4DbScheme";

    public String getCode() {
        return OPTION_CODE;
    }

    public String getTitle() {
        return "\u6570\u636e\u5e93\u6a21\u5f0f\u540d";
    }

    public OptionType getOptionType() {
        return OptionType.STRING;
    }

    public String defaultValue() {
        return "";
    }
}

