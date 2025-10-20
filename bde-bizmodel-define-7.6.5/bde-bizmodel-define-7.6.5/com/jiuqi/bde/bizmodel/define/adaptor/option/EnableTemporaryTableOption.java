/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.enums.OptionType
 *  com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeOption
 */
package com.jiuqi.bde.bizmodel.define.adaptor.option;

import com.jiuqi.dc.base.common.enums.OptionType;
import com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeOption;
import org.springframework.stereotype.Component;

@Component
public class EnableTemporaryTableOption
implements IDataSchemeOption {
    public String getCode() {
        return "enableTemporaryTable";
    }

    public String getTitle() {
        return "\u542f\u7528\u4e34\u65f6\u8868";
    }

    public OptionType getOptionType() {
        return OptionType.BOOLEAN;
    }

    public String defaultValue() {
        return "false";
    }
}

