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
public class BipFlagShipTenantOption
implements IDataSchemeOption {
    public static final String TENANT_CODE = "TENANT_CODE";

    public String getCode() {
        return TENANT_CODE;
    }

    public String getTitle() {
        return "\u79df\u6237";
    }

    public OptionType getOptionType() {
        return OptionType.TEXT;
    }
}

