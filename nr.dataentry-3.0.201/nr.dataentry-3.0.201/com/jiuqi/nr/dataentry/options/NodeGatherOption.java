/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.option.core.OptionEditMode
 *  com.jiuqi.nr.definition.option.core.OptionItem
 *  com.jiuqi.nr.definition.option.impl.group.NodeGatherGroup
 *  com.jiuqi.nr.definition.option.impl.page.GatherPage
 *  com.jiuqi.nr.definition.option.spi.TaskOptionDefine
 */
package com.jiuqi.nr.dataentry.options;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.impl.group.NodeGatherGroup;
import com.jiuqi.nr.definition.option.impl.page.GatherPage;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class NodeGatherOption
implements TaskOptionDefine {
    public static final String GATHER_NO_CONDITION = "GATHER_NO_CONDITION";

    public String getKey() {
        return GATHER_NO_CONDITION;
    }

    public String getTitle() {
        return "\u8282\u70b9\u6c47\u603b\u5305\u542b\u4e0d\u7b26\u5408\u9002\u5e94\u6761\u4ef6\u7684\u8868\u5355";
    }

    public String getDefaultValue() {
        return "0";
    }

    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_SWITCH;
    }

    public List<OptionItem> getOptionItems() {
        ArrayList<OptionItem> optionItems = new ArrayList<OptionItem>();
        optionItems.add(new OptionItem(){

            public String getTitle() {
                return "\u662f";
            }

            public String getValue() {
                return "1";
            }
        });
        optionItems.add(new OptionItem(){

            public String getTitle() {
                return "\u5426";
            }

            public String getValue() {
                return "0";
            }
        });
        return optionItems;
    }

    public String getRegex() {
        return null;
    }

    public String getPageTitle() {
        return new GatherPage().getTitle();
    }

    public String getGroupTitle() {
        return new NodeGatherGroup().getTitle();
    }
}

