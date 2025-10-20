/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.internal;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.impl.group.DataEntryGroup;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DataAnnocationOption
implements TaskOptionDefine {
    @Override
    public String getKey() {
        return "DATA_ANNOCATION";
    }

    @Override
    public String getTitle() {
        return "\u542f\u7528\u6570\u636e\u6279\u6ce8";
    }

    @Override
    public String getDefaultValue() {
        return "0";
    }

    @Override
    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_SWITCH;
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
    public double getOrder() {
        return 20.0;
    }

    @Override
    public String getPageTitle() {
        return new DataEntryGroup().getPageTitle();
    }

    @Override
    public String getGroupTitle() {
        return new DataEntryGroup().getTitle();
    }
}

