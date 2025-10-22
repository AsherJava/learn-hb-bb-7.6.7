/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.internal;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.impl.group.NodeGatherGroup;
import com.jiuqi.nr.definition.option.impl.page.GatherPage;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SummaryAfterUpload
implements TaskOptionDefine {
    @Override
    public String getKey() {
        return "SUMMARY_AFTER_UPLOAD";
    }

    @Override
    public String getTitle() {
        return "\u4ec5\u6c47\u603b\u5df2\u4e0a\u62a5\u5355\u4f4d";
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
        ArrayList<OptionItem> optionItems = new ArrayList<OptionItem>();
        optionItems.add(new OptionItem(){

            @Override
            public String getTitle() {
                return "\u662f";
            }

            @Override
            public String getValue() {
                return "1";
            }
        });
        optionItems.add(new OptionItem(){

            @Override
            public String getTitle() {
                return "\u5426";
            }

            @Override
            public String getValue() {
                return "0";
            }
        });
        return optionItems;
    }

    @Override
    public String getRegex() {
        return null;
    }

    @Override
    public double getOrder() {
        return 34.0;
    }

    @Override
    public String getPageTitle() {
        return new GatherPage().getTitle();
    }

    @Override
    public String getGroupTitle() {
        return new NodeGatherGroup().getTitle();
    }
}

