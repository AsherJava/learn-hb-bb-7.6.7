/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.enums.OptionType
 *  com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeOption
 */
package com.jiuqi.bde.plugin.cloud_acca.option;

import com.jiuqi.dc.base.common.enums.OptionType;
import com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeOption;
import org.springframework.stereotype.Component;

@Component
public class BdeCloudAccaSsoAppIdOption
implements IDataSchemeOption {
    public static final String EGAS_SSO_APP_ID_OPTION = "egasSsOAppidOption";
    public static final String DEFAULT_VALUE = "EGAS";

    public String getCode() {
        return EGAS_SSO_APP_ID_OPTION;
    }

    public String getTitle() {
        return "\u4e91\u6838\u7b97\u7cfb\u7edf\u5355\u70b9APP ID \u6807\u8bc6";
    }

    public OptionType getOptionType() {
        return OptionType.STRING;
    }

    public String defaultValue() {
        return DEFAULT_VALUE;
    }
}

