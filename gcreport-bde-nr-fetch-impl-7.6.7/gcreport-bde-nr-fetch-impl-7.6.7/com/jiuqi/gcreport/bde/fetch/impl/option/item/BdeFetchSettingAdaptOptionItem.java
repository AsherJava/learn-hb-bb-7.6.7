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

public class BdeFetchSettingAdaptOptionItem
implements ISystemOptionItem {
    private static final String TITLE = "\u542f\u7528\u9002\u5e94\u6761\u4ef6";

    public String getId() {
        return "BDE_ADAPT_SWITCH";
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
            private static final long serialVersionUID = 1L;

            public String getTitle() {
                return "\u662f";
            }

            public String getValue() {
                return "1";
            }
        });
        values.add(new ISystemOptionalValue(){
            private static final long serialVersionUID = 1L;

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

