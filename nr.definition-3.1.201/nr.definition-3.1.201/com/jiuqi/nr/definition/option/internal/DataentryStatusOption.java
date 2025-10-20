/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.internal;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.impl.group.DisplayGroup;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DataentryStatusOption
implements TaskOptionDefine {
    @Override
    public String getKey() {
        return "DATAENTRY_STATUS";
    }

    @Override
    public String getTitle() {
        return "\u5f55\u6570\u72b6\u6001\u663e\u793a";
    }

    @Override
    public String getDefaultValue() {
        return "[]";
    }

    @Override
    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_CHECK_BOX;
    }

    @Override
    public List<OptionItem> getOptionItems() {
        ArrayList<OptionItem> optionItems = new ArrayList<OptionItem>();
        optionItems.add(new OptionItem(){

            @Override
            public String getValue() {
                return "0";
            }

            @Override
            public String getTitle() {
                return "\u65f6\u671f";
            }
        });
        optionItems.add(new OptionItem(){

            @Override
            public String getValue() {
                return "1";
            }

            @Override
            public String getTitle() {
                return "\u5355\u4f4d";
            }
        });
        optionItems.add(new OptionItem(){

            @Override
            public String getValue() {
                return "2";
            }

            @Override
            public String getTitle() {
                return "\u62a5\u8868";
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
        return 50.0;
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

