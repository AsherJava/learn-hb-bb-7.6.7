/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.option.core.OptionEditMode
 *  com.jiuqi.nr.definition.option.core.OptionItem
 *  com.jiuqi.nr.definition.option.impl.page.OtherPage
 *  com.jiuqi.nr.definition.option.spi.TaskOptionDefine
 */
package com.jiuqi.nr.io.service.impl;

import com.jiuqi.nr.definition.option.core.OptionEditMode;
import com.jiuqi.nr.definition.option.core.OptionItem;
import com.jiuqi.nr.definition.option.impl.page.OtherPage;
import com.jiuqi.nr.definition.option.spi.TaskOptionDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AllowImportIllegalDataOption
implements TaskOptionDefine {
    public static final String ILLEGALDATAIMPORT = "IllegalDataImport_2132";

    public String getKey() {
        return ILLEGALDATAIMPORT;
    }

    public String getTitle() {
        return "\u5141\u8bb8\u975e\u6cd5\u6570\u636e\u5165\u5e93";
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

