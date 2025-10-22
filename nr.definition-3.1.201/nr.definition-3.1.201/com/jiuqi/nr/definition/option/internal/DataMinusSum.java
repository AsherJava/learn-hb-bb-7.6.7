/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.internal;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.impl.group.NodeGatherGroup;
import com.jiuqi.nr.definition.option.impl.page.GatherPage;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DataMinusSum
implements TaskOptionDefine {
    @Override
    public String getKey() {
        return "DATA_MINUS_SUM";
    }

    @Override
    public String getTitle() {
        return "\u542f\u7528\u5dee\u989d\u6c47\u603b";
    }

    @Override
    public String getDefaultValue() {
        return "1";
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
        return 4.0;
    }

    @Override
    public String getPageTitle() {
        return new GatherPage().getTitle();
    }

    @Override
    public String getGroupTitle() {
        return new NodeGatherGroup().getTitle();
    }
}

