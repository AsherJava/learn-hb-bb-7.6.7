/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.internal;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.impl.page.EFDCPage;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class EfdcGetValueModify
implements TaskOptionDefine {
    @Override
    public String getKey() {
        return "EFDC_GET_VALUE_MODIFY_TASK";
    }

    @Override
    public String getTitle() {
        return "\u8d26\u8868\u53d6\u503c\u5355\u5143\u683c\u5141\u8bb8\u4fee\u6539";
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
        return 36.0;
    }

    @Override
    public String getPageTitle() {
        return new EFDCPage().getTitle();
    }

    @Override
    public String getGroupTitle() {
        return null;
    }
}

