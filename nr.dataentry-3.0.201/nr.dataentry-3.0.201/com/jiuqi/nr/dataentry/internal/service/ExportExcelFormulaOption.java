/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.option.core.OptionEditMode
 *  com.jiuqi.nr.definition.option.core.OptionItem
 *  com.jiuqi.nr.definition.option.impl.page.OtherPage
 *  com.jiuqi.nr.definition.option.spi.TaskOptionDefine
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.impl.page.OtherPage;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ExportExcelFormulaOption
implements TaskOptionDefine {
    public static final String EXCELFORMULA_CONDITION = "EXCELFORMULA_CONDITION_123";

    public String getKey() {
        return EXCELFORMULA_CONDITION;
    }

    public String getTitle() {
        return "\u5bfc\u51faexcel\u65f6\u5c06\u8fd0\u7b97\u516c\u5f0f\u8f6c\u6362\u4e3aexcel\u516c\u5f0f";
    }

    public String getDefaultValue() {
        return "0";
    }

    public OptionEditMode getOptionEditMode() {
        return OptionEditMode.OPTION_RADIO_BUTTON;
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
        return new OtherPage().getTitle();
    }

    public String getGroupTitle() {
        return null;
    }
}

