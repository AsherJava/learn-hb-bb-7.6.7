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
public class NonLeafNodeReadOnlyOption
implements TaskOptionDefine {
    @Override
    public String getKey() {
        return "NON_LEAF_NODE_READONLY";
    }

    @Override
    public String getTitle() {
        return "\u975e\u53f6\u5b50\u5355\u4f4d\u7981\u6b62\u5f55\u5165\u6570\u636e";
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
        return 50.0;
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

