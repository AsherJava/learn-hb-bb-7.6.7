/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.internal.review;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.impl.group.CheckGroup;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.List;

public class ReviewConditionsOption
implements TaskOptionDefine {
    @Override
    public String getKey() {
        return "REVIEW_CONDITION";
    }

    @Override
    public String getTitle() {
        return "\u4e0a\u62a5\u524d\u5ba1\u6838\u4e2a\u6027\u5316\u65b9\u6848\u9002\u7528\u6761\u4ef6";
    }

    @Override
    public String getDefaultValue() {
        return null;
    }

    @Override
    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_INPUT;
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
        return 46.0;
    }

    @Override
    public String getPageTitle() {
        return new CheckGroup().getPageTitle();
    }

    @Override
    public String getGroupTitle() {
        return new CheckGroup().getTitle();
    }
}

