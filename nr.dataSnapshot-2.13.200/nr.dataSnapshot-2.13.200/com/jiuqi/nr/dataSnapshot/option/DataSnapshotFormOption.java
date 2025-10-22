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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSnapshotFormOption
implements TaskOptionDefine {
    private final Logger logger = LoggerFactory.getLogger(DataSnapshotFormOption.class);

    public String getKey() {
        return "DATA_SNAPSHOT_FORM";
    }

    public String getTitle() {
        return "\u9009\u62e9\u751f\u6210\u5feb\u7167\u7684\u62a5\u8868";
    }

    public String getDefaultValue() {
        return "0";
    }

    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_CUSTOM;
    }

    public List<OptionItem> getOptionItems() {
        return null;
    }

    public String getRegex() {
        return null;
    }

    public double getOrder() {
        return 42.0;
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

    public String getPluginName() {
        return "dataSnapshotForm";
    }
}

