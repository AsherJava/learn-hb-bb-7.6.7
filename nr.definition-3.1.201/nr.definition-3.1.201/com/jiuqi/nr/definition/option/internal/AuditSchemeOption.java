/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.internal;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.impl.group.AuditSchemeGroup;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AuditSchemeOption
implements TaskOptionDefine {
    public static final String KEY = "AUDIT_SCHEME";
    public static final String CONDITION = "AUDIT_SCHEME_CONDITION";
    public static final String CONDITION_TITLE = "\u9002\u7528\u6761\u4ef6";
    public static final String AUDIT_TYPE = "AUDIT_TYPE";

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getDefaultValue() {
        return null;
    }

    @Override
    public OptionEditMode getOptionEditMode() {
        return null;
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
        return new AuditSchemeGroup().getPageTitle();
    }

    @Override
    public String getGroupTitle() {
        return new AuditSchemeGroup().getTitle();
    }
}

