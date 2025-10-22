/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.option.core.OptionEditMode
 *  com.jiuqi.nr.definition.option.core.OptionItem
 *  com.jiuqi.nr.definition.option.spi.TaskOptionDefine
 */
package com.jiuqi.nr.finalaccountsaudit.enumcheck.internal.controller;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.internal.controller.EnumCheckGroup;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class EnumCheckBeforUploadTaskOption
implements TaskOptionDefine {
    public static final String ENUMCHECK_BEFORE_UPLOAD = "ENUMCHECK_BEFORE_UPLOAD";

    public String getKey() {
        return ENUMCHECK_BEFORE_UPLOAD;
    }

    public String getTitle() {
        return "\u4e0a\u62a5\u524d\u679a\u4e3e\u5b57\u5178\u68c0\u67e5";
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

    public String getPageTitle() {
        return new EnumCheckGroup().getPageTitle();
    }

    public String getGroupTitle() {
        return new EnumCheckGroup().getTitle();
    }
}

