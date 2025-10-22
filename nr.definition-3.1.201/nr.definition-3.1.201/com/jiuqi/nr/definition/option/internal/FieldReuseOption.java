/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.internal;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.impl.page.OtherPage;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FieldReuseOption
implements TaskOptionDefine {
    @Override
    public String getKey() {
        return "FIELD_REUSE_OPTION";
    }

    @Override
    public String getTitle() {
        return "\u62a5\u8868\u590d\u5236\u56fa\u5b9a\u8868\u76f8\u540c\u4ee3\u7801\u6307\u6807\u590d\u7528";
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
    public String getPageTitle() {
        return new OtherPage().getTitle();
    }

    @Override
    public String getGroupTitle() {
        return null;
    }
}

