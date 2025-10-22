/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.option.core.AffectedMode
 *  com.jiuqi.nr.definition.option.core.OptionEditMode
 *  com.jiuqi.nr.definition.option.core.OptionItem
 *  com.jiuqi.nr.definition.option.core.RelationTaskOptionItem
 *  com.jiuqi.nr.definition.option.impl.group.DataEntryGroup
 *  com.jiuqi.nr.definition.option.internal.DataAnnocationOption
 *  com.jiuqi.nr.definition.option.spi.TaskOptionDefine
 */
package com.jiuqi.nr.dataentry.taskOption;

import com.jiuqi.nr.definition.option.core.AffectedMode;
import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.core.RelationTaskOptionItem;
import com.jiuqi.nr.definition.option.impl.group.DataEntryGroup;
import com.jiuqi.nr.definition.option.internal.DataAnnocationOption;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ViewAllFormAnnoOption
implements TaskOptionDefine {
    public String getKey() {
        return "VIEW_ALL_FORM_ANNO_OPTION";
    }

    public String getTitle() {
        return "\u9ed8\u8ba4\u663e\u793a\u6240\u6709\u62a5\u8868\u6279\u6ce8";
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
        return 21.0;
    }

    public String getPageTitle() {
        return new DataEntryGroup().getPageTitle();
    }

    public String getGroupTitle() {
        return new DataEntryGroup().getTitle();
    }

    public RelationTaskOptionItem getRelationTaskOptionItem() {
        RelationTaskOptionItem relationTaskOptionItem = new RelationTaskOptionItem();
        relationTaskOptionItem.setFromKey(new DataAnnocationOption().getKey());
        relationTaskOptionItem.setAffectedMode(AffectedMode.VISIBLE);
        ArrayList<String> list = new ArrayList<String>();
        list.add("1");
        relationTaskOptionItem.setFromValues(list);
        return relationTaskOptionItem;
    }
}

