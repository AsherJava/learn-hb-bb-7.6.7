/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.internal;

import com.jiuqi.nr.definition.option.core.AffectedMode;
import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.core.RelationTaskOptionItem;
import com.jiuqi.nr.definition.option.impl.group.DataEntryGroup;
import com.jiuqi.nr.definition.option.internal.FormLock;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MultiUserFormLock
implements TaskOptionDefine {
    @Override
    public String getKey() {
        return "MULTIUSER_FORM_LOCK";
    }

    @Override
    public String getTitle() {
        return "\u542f\u7528\u591a\u4eba\u9501\u5b9a";
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
        return new DataEntryGroup().getPageTitle();
    }

    @Override
    public String getGroupTitle() {
        return new DataEntryGroup().getTitle();
    }

    @Override
    public RelationTaskOptionItem getRelationTaskOptionItem() {
        RelationTaskOptionItem item = new RelationTaskOptionItem();
        item.setFromKey(new FormLock().getKey());
        item.setAffectedMode(AffectedMode.VISIBLE);
        ArrayList<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        item.setFromValues(list);
        return item;
    }

    @Override
    public double getOrder() {
        return 61.0;
    }
}

