/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionalItemValue
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 */
package com.jiuqi.gcreport.common.systemoption.group;

import com.jiuqi.gcreport.common.systemoption.GcNormalOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionalItemValue;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GcConversionOptionDeclare
extends GcNormalOptionDeclare {
    public static final String ALLOW_VALUE = "1";
    public static final String NOT_ALLOW_VALUE = "0";

    public String getId() {
        return "ALLOW_UPLOADSTATE_CONVERSION";
    }

    public String getTitle() {
        return "\u5916\u5e01\u6298\u7b97";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add((ISystemOptionItem)this.createSystemOptionItem());
        return optionItems;
    }

    private SystemOptionItem createSystemOptionItem() {
        SystemOptionItem systemOptionItem = new SystemOptionItem();
        systemOptionItem.setId("ALLOW_UPLOADSTATE_CONVERSION");
        systemOptionItem.setTitle("\u5141\u8bb8\u5355\u4f4d\u9001\u5ba1\u3001\u4e0a\u62a5\u540e\u6267\u884c\u5916\u5e01\u6298\u7b97");
        systemOptionItem.setDefaultValue(NOT_ALLOW_VALUE);
        systemOptionItem.setEditMode(SystemOptionConst.EditMode.RADIO_BUTTON);
        systemOptionItem.setOptionalValues(this.getOptionalValues());
        return systemOptionItem;
    }

    private List<ISystemOptionalValue> getOptionalValues() {
        ArrayList<ISystemOptionalValue> values = new ArrayList<ISystemOptionalValue>();
        SystemOptionalItemValue isTrue = new SystemOptionalItemValue();
        isTrue.setTitle("\u662f");
        isTrue.setValue(ALLOW_VALUE);
        SystemOptionalItemValue isFalse = new SystemOptionalItemValue();
        isFalse.setTitle("\u5426");
        isFalse.setValue(NOT_ALLOW_VALUE);
        values.add((ISystemOptionalValue)isTrue);
        values.add((ISystemOptionalValue)isFalse);
        return values;
    }
}

