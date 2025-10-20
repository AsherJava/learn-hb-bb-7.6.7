/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.internal;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.impl.group.CalculateGroup;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AutoCalculatAfterClear
implements TaskOptionDefine {
    @Override
    public String getKey() {
        return "AUTOCALCULAT_AFTER_CLEAR";
    }

    @Override
    public String getTitle() {
        return "\u6574\u8868\u6e05\u9664\u540e\u81ea\u52a8\u8fd0\u7b97";
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
        return new CalculateGroup().getPageTitle();
    }

    @Override
    public String getGroupTitle() {
        return new CalculateGroup().getTitle();
    }
}

