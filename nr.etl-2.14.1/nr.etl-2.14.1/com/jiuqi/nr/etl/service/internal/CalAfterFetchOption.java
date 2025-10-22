/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.option.core.OptionEditMode
 *  com.jiuqi.nr.definition.option.core.OptionItem
 *  com.jiuqi.nr.definition.option.impl.group.CalculateGroup
 *  com.jiuqi.nr.definition.option.spi.TaskOptionDefine
 */
package com.jiuqi.nr.etl.service.internal;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.impl.group.CalculateGroup;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CalAfterFetchOption
implements TaskOptionDefine {
    public String getKey() {
        return "CAL_AFTER_FETCH";
    }

    public String getTitle() {
        return "ETL\u53d6\u6570\u540e\u81ea\u52a8\u8fd0\u7b97";
    }

    public String getDefaultValue() {
        return "0";
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
        return 40.0;
    }

    public String getPageTitle() {
        return new CalculateGroup().getPageTitle();
    }

    public String getGroupTitle() {
        return new CalculateGroup().getTitle();
    }
}

