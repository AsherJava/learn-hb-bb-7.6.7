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

public class BdeFetchSettingOutportZbCodeRuleOptionItem
implements ISystemOptionItem {
    private static final String TITLE = "\u5bfc\u5165\u5bfc\u51fa\u6307\u6807\u4ee3\u7801\u5339\u914d";

    public String getId() {
        return "BDE_FETCH_SETTING_OUTPUT_ZB_CODE_MAPPING_CODE";
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

    public String getDescribe() {
        return "BDE\u53d6\u6570\u8bbe\u7f6e\u5bfc\u5165\u5bfc\u51fa\u6309\u7167\u6307\u6807\u4ee3\u7801\u8fdb\u884c\u5339\u914d\uff08\u9ed8\u8ba4\u65b9\u5f0f\u4e3a\u6309\u7167\u6570\u636e\u65b9\u6848\u4ee3\u7801\u7ed3\u5408\u6307\u6807\u4ee3\u7801\u8fdb\u884c\u5339\u914d\uff09\uff0c\u89e3\u51b3\u8de8\u4efb\u52a1\u4e14\u6307\u6807\u6807\u9898\u6709\u91cd\u590d\u7684\u60c5\u666f\u7684\u53d6\u6570\u8bbe\u7f6e\u5bfc\u5165\u5bfc\u51fa\u95ee\u9898";
    }
}

