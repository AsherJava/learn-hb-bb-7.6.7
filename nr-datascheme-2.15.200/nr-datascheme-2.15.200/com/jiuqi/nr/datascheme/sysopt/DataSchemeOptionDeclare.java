/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare
 *  com.jiuqi.nr.system.options.adaptation.option.AbstractTrueFalseOption
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 */
package com.jiuqi.nr.datascheme.sysopt;

import com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare;
import com.jiuqi.nr.system.options.adaptation.option.AbstractTrueFalseOption;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DataSchemeOptionDeclare
extends BaseNormalOptionDeclare {
    public static final String DS_ID = "data-scheme-sys-opt";
    public static final String DS_USER_DELETION_DISABLED = "userDeletionDisabled";

    public String getId() {
        return DS_ID;
    }

    public String getTitle() {
        return "\u6570\u636e\u65b9\u6848";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add((ISystemOptionItem)new AbstractTrueFalseOption(){

            public String getId() {
                return DataSchemeOptionDeclare.DS_USER_DELETION_DISABLED;
            }

            public String getTitle() {
                return "\u666e\u901a\u7528\u6237\u7981\u6b62\u5220\u9664\u6570\u636e\u8868\u3001\u6570\u636e\u6307\u6807";
            }

            public String getDefaultValue() {
                return "0";
            }
        });
        return optionItems;
    }
}

