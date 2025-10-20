/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.enums.OptionType
 */
package com.jiuqi.dc.mappingscheme.impl.define;

import com.jiuqi.dc.base.common.enums.OptionType;

public interface IDataSchemeOption {
    public String getCode();

    public String getTitle();

    public OptionType getOptionType();

    default public String defaultValue() {
        return null;
    }

    default public String source() {
        return null;
    }

    default public String baseDataTable() {
        return null;
    }

    default public String description() {
        return "";
    }
}

