/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 */
package com.jiuqi.gcreport.bde.fetch.impl.option.item;

import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;

public class BdeFetchSettingStopUseFlagOptionItem
implements ISystemOptionItem {
    private static final String TITLE = "BDE\u53d6\u6570\u8bbe\u7f6e\u5141\u8bb8\u505c\u7528";

    public String getId() {
        return "BDE_STOP_USE_SWITCH";
    }

    public String getTitle() {
        return TITLE;
    }

    public String getDefaultValue() {
        return "0";
    }

    public SystemOptionConst.EditMode getEditMode() {
        return SystemOptionConst.EditMode.RADIO_BUTTON;
    }

    public List<ISystemOptionalValue> getOptionalValues() {
        ArrayList<ISystemOptionalValue> values = new ArrayList<ISystemOptionalValue>();
        values.add(new ISystemOptionalValue(){
            private static final long serialVersionUID = 3981573531029090246L;

            public String getTitle() {
                return "\u662f";
            }

            public String getValue() {
                return "1";
            }
        });
        values.add(new ISystemOptionalValue(){
            private static final long serialVersionUID = -7219825176601554581L;

            public String getTitle() {
                return "\u5426";
            }

            public String getValue() {
                return "0";
            }
        });
        return values;
    }
}

