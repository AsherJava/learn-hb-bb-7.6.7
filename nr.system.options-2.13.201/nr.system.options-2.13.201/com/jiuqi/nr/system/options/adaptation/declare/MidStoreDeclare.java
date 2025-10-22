/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 */
package com.jiuqi.nr.system.options.adaptation.declare;

import com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare;
import com.jiuqi.nr.system.options.adaptation.option.AbstractTrueFalseOption;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MidStoreDeclare
extends BaseNormalOptionDeclare {
    public static final String AUTOCALCULATE_AFTER_MIDSTOREPULL = "AUTOCALCULATE_AFTER_MIDSTOREPULL";
    public static final String ID = "nr-midstore-group";
    public static final String TITLE = "\u4e2d\u95f4\u5e93";

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return TITLE;
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(new AbstractTrueFalseOption(){

            public String getId() {
                return MidStoreDeclare.AUTOCALCULATE_AFTER_MIDSTOREPULL;
            }

            public String getTitle() {
                return "\u4e2d\u95f4\u5e93\u53d6\u6570\u540e\u81ea\u52a8\u8fd0\u7b97";
            }
        });
        return optionItems;
    }
}

