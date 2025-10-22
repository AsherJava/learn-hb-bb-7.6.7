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
public class UnitEdit
implements TaskOptionDefine {
    @Override
    public String getKey() {
        return "UNIT_EDIT";
    }

    @Override
    public String getTitle() {
        return "\u5141\u8bb8\u7ba1\u7406\u5355\u4f4d";
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
        return 10.0;
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

