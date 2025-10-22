/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.configuration.facade;

import com.jiuqi.nr.configuration.common.OptionItem;
import com.jiuqi.nr.configuration.common.SystemOptionEditMode;
import com.jiuqi.nr.configuration.common.SystemOptionType;
import java.util.List;

@Deprecated
public interface SystemOptionBase {
    public String getKey();

    public String getGroup();

    public String getTitle();

    public Object getDefaultValue();

    public SystemOptionType getOptionType();

    public SystemOptionEditMode getOptionEditMode();

    public List<OptionItem> getOptionItems();

    public String getRegex();

    default public int getOrder() {
        return 0;
    }
}

