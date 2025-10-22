/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare
 *  com.jiuqi.nr.system.options.adaptation.option.AbstractTrueFalseOption
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 */
package com.jiuqi.nr.definition.option.systemoption;

import com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare;
import com.jiuqi.nr.system.options.adaptation.option.AbstractTrueFalseOption;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PrivateOption
extends BaseNormalOptionDeclare {
    public static final String PRIVATE_FORMULA_NAME = "\u5206\u7ea7\u516c\u5f0f";
    public static final String PRIVATE_FORMULA_CODE = "PRIVATE_FORMULA";
    public static final String PRIVATE_FORMULA_VALUE = "PRIVATE_FORMULA_VALUE";

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> systemOptionItems = new ArrayList<ISystemOptionItem>();
        systemOptionItems.add((ISystemOptionItem)new AbstractTrueFalseOption(){

            public String getId() {
                return PrivateOption.PRIVATE_FORMULA_VALUE;
            }

            public String getTitle() {
                return PrivateOption.PRIVATE_FORMULA_NAME;
            }

            public String getDefaultValue() {
                return "0";
            }
        });
        return systemOptionItems;
    }

    public String getId() {
        return PRIVATE_FORMULA_CODE;
    }

    public String getTitle() {
        return PRIVATE_FORMULA_NAME;
    }
}

