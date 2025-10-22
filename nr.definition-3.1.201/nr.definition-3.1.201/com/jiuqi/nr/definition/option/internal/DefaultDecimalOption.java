/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.internal;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.impl.group.DisplayGroup;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DefaultDecimalOption
implements TaskOptionDefine {
    @Override
    public String getKey() {
        return "DEFAULT_DECIMAL";
    }

    @Override
    public String getTitle() {
        return "\u91d1\u989d\u8f6c\u6362\u9ed8\u8ba4\u4fdd\u7559\u5c0f\u6570\u4f4d";
    }

    @Override
    public String getDefaultValue() {
        return "";
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
        return 30.0;
    }

    @Override
    public String getPageTitle() {
        return new DisplayGroup().getPageTitle();
    }

    @Override
    public String getGroupTitle() {
        return new DisplayGroup().getTitle();
    }
}

