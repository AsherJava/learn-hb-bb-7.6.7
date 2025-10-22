/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 */
package com.jiuqi.nr.system.options.adaptation.declare;

import com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare;
import com.jiuqi.nr.system.options.adaptation.option.AbstractInputOption;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CalculationDeclare
extends BaseNormalOptionDeclare {
    public static final String CALCULATE_FORMULA_COUNT = "CALCULATE_FORMULA_COUNT";
    public static final String ID = "nr-calculation-group";

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "\u8fd0\u7b97";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(new AbstractInputOption(){

            public String getId() {
                return CalculationDeclare.CALCULATE_FORMULA_COUNT;
            }

            public String getTitle() {
                return "\u8fd0\u7b97\u516c\u5f0f\u6267\u884c\u6b21\u6570";
            }

            @Override
            public String getDefaultValue() {
                return "";
            }
        });
        return optionItems;
    }

    @Override
    public int getOrdinal() {
        return 3;
    }
}

