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
public class FormFxShowOption
implements TaskOptionDefine {
    @Override
    public String getKey() {
        return "FORM_FX_SHOW";
    }

    @Override
    public String getTitle() {
        return "\u516c\u5f0f\u663e\u793a\u533a\u663e\u793a\u5185\u5bb9";
    }

    @Override
    public String getDefaultValue() {
        return "1";
    }

    @Override
    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_RADIO_BUTTON;
    }

    @Override
    public List<OptionItem> getOptionItems() {
        ArrayList<OptionItem> optionItems = new ArrayList<OptionItem>();
        optionItems.add(new OptionItem(){

            @Override
            public String getTitle() {
                return "\u663e\u793a\u516c\u5f0f\u6587\u672c";
            }

            @Override
            public String getValue() {
                return "1";
            }
        });
        optionItems.add(new OptionItem(){

            @Override
            public String getTitle() {
                return "\u663e\u793a\u516c\u5f0f\u542b\u4e49";
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
        return 60.0;
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

