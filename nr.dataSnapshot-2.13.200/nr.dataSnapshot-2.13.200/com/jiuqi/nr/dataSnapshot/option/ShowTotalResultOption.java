/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.option.core.AffectedMode
 *  com.jiuqi.nr.definition.option.core.OptionEditMode
 *  com.jiuqi.nr.definition.option.core.OptionItem
 *  com.jiuqi.nr.definition.option.core.RelationTaskOptionItem
 *  com.jiuqi.nr.definition.option.impl.group.DataEntryGroup
 *  com.jiuqi.nr.definition.option.spi.TaskOptionDefine
 */
package com.jiuqi.nr.dataSnapshot.option;

import com.jiuqi.nr.dataSnapshot.option.DataSnapshotOption;
import com.jiuqi.nr.definition.option.core.AffectedMode;
import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.core.RelationTaskOptionItem;
import com.jiuqi.nr.definition.option.impl.group.DataEntryGroup;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ShowTotalResultOption
implements TaskOptionDefine {
    public String getKey() {
        return "DATASNAPSHOT_SHOWTOTAL";
    }

    public String getTitle() {
        return "\u663e\u793a\u5feb\u7167\u5bf9\u6bd4\u6c47\u603b\u7ed3\u679c";
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
        return 41.0;
    }

    public String getPageTitle() {
        return new DataEntryGroup().getPageTitle();
    }

    public String getGroupTitle() {
        return new DataEntryGroup().getTitle();
    }

    public RelationTaskOptionItem getRelationTaskOptionItem() {
        RelationTaskOptionItem relationTaskOptionItem = new RelationTaskOptionItem();
        relationTaskOptionItem.setFromKey(new DataSnapshotOption().getKey());
        relationTaskOptionItem.setAffectedMode(AffectedMode.VISIBLE);
        ArrayList<String> list = new ArrayList<String>();
        list.add("1");
        relationTaskOptionItem.setFromValues(list);
        return relationTaskOptionItem;
    }
}

