/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.option.core.OptionEditMode
 *  com.jiuqi.nr.definition.option.core.OptionItem
 *  com.jiuqi.nr.definition.option.impl.group.DataEntryGroup
 *  com.jiuqi.nr.definition.option.spi.TaskOptionDefine
 */
package com.jiuqi.nr.dataentry.taskOption;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.impl.group.DataEntryGroup;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class EnumAttributeShowWay
implements TaskOptionDefine {
    public String getKey() {
        return "ENUM_ATTRIBUTE_SHOW_WAY";
    }

    public String getTitle() {
        return "\u201c\u679a\u4e3e\u5c5e\u6027\u663e\u793a\u5728\u201d\u4ec5\u679a\u4e3e\u9996\u6b21\u5f55\u5165\u65f6\u751f\u6548";
    }

    public String getDefaultValue() {
        return "1";
    }

    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_SWITCH;
    }

    public List<OptionItem> getOptionItems() {
        return null;
    }

    public String getRegex() {
        return null;
    }

    public double getOrder() {
        return 51.0;
    }

    public String getPageTitle() {
        return new DataEntryGroup().getPageTitle();
    }

    public String getGroupTitle() {
        return new DataEntryGroup().getTitle();
    }
}

