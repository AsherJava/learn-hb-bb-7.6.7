/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.option.core.OptionEditMode
 *  com.jiuqi.nr.definition.option.core.OptionItem
 *  com.jiuqi.nr.definition.option.impl.page.GatherPage
 *  com.jiuqi.nr.definition.option.spi.TaskOptionDefine
 */
package com.jiuqi.nr.data.gather.option;

import com.jiuqi.nr.data.gather.option.NodeCheckGroup;
import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.impl.page.GatherPage;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ToleranceOption
implements TaskOptionDefine {
    public String getKey() {
        return "NODE_CHECK_TOLERANCE";
    }

    public String getTitle() {
        return "\u5141\u8bb8\u8bef\u5dee";
    }

    public String getDefaultValue() {
        return "0";
    }

    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_INPUT;
    }

    public List<OptionItem> getOptionItems() {
        return null;
    }

    public String getRegex() {
        return "^\\d+(\\.\\d+)?$";
    }

    public String getPageTitle() {
        return new GatherPage().getTitle();
    }

    public String getGroupTitle() {
        return new NodeCheckGroup().getTitle();
    }
}

