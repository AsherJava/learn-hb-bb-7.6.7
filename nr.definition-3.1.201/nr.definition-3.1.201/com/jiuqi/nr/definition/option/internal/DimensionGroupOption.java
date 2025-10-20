/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.internal;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.impl.group.UnitTreeGroup;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DimensionGroupOption
implements TaskOptionDefine {
    public static final String OPTION_KEY = "DIMENSION_GROUP";

    @Override
    public String getKey() {
        return OPTION_KEY;
    }

    @Override
    public String getTitle() {
        return "\u5206\u7ec4";
    }

    @Override
    public String getDefaultValue() {
        return null;
    }

    @Override
    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_DROP_DOWN;
    }

    @Override
    public List<OptionItem> getOptionItems() {
        return null;
    }

    @Override
    public String getRegex() {
        return null;
    }

    @Override
    public String getPageTitle() {
        return new UnitTreeGroup().getPageTitle();
    }

    @Override
    public String getGroupTitle() {
        return new UnitTreeGroup().getTitle();
    }
}

