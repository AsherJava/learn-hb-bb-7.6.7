/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.option.core.OptionEditMode
 *  com.jiuqi.nr.definition.option.core.OptionItem
 *  com.jiuqi.nr.definition.option.impl.group.CheckGroup
 *  com.jiuqi.nr.definition.option.spi.TaskOptionDefine
 */
package com.jiuqi.nr.data.logic.internal.option;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.impl.group.CheckGroup;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CheckAllowErrorOption
implements TaskOptionDefine {
    public static final String KEY = "@nr/CHECK_ALLOW_ERROR";
    public static final String TITLE = "\u5ba1\u6838\u5141\u8bb8\u901a\u8fc7\u7684\u8bef\u5dee\u503c";

    public String getKey() {
        return KEY;
    }

    public String getTitle() {
        return TITLE;
    }

    public String getDefaultValue() {
        return "0.00";
    }

    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_INPUT;
    }

    public List<OptionItem> getOptionItems() {
        return Collections.emptyList();
    }

    public String getRegex() {
        return "^(?!0\\d)(?!\\.)\\d+\\.\\d{2}$";
    }

    public String getRegexMsg() {
        return "\u5ba1\u6838\u5141\u8bb8\u901a\u8fc7\u7684\u8bef\u5dee\u503c\uff1a\u8bf7\u8f93\u5165\u975e\u8d1f\u5e26\u4e24\u4f4d\u5c0f\u6570\u7684\u6570\u503c";
    }

    public String getPlaceholder() {
        return "\u8bf7\u8f93\u5165\u975e\u8d1f\u5e26\u4e24\u4f4d\u5c0f\u6570\u7684\u6570\u503c";
    }

    public double getOrder() {
        return 21.0;
    }

    public String getPageTitle() {
        return new CheckGroup().getPageTitle();
    }

    public String getGroupTitle() {
        return new CheckGroup().getTitle();
    }
}

