/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.systemoption.GcNormalOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionalItemValue
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 */
package com.jiuqi.gcreport.intermediatelibrary.systemoption;

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
public class DataDockingOptionDeclare
extends GcNormalOptionDeclare {
    public static final String YES = "1";
    public static final String NO = "0";

    public String getId() {
        return "GC_DATADOCKING_SETTING";
    }

    public String getTitle() {
        return "\u62a5\u8868API\u6570\u636e\u63a5\u53e3";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(this.getOptionItem());
        return optionItems;
    }

    private ISystemOptionItem getOptionItem() {
        SystemOptionItem systemOptionItem = new SystemOptionItem();
        systemOptionItem.setId("DATADOCKING_ENABLE_USER_AUTH");
        systemOptionItem.setTitle("\u542f\u7528\u7528\u6237\u6743\u9650");
        systemOptionItem.setDefaultValue(NO);
        systemOptionItem.setEditMode(SystemOptionConst.EditMode.RADIO_BUTTON);
        systemOptionItem.setOptionalValues(this.listOptionValues());
        return systemOptionItem;
    }

    private List<ISystemOptionalValue> listOptionValues() {
        ArrayList<ISystemOptionalValue> optionalValues = new ArrayList<ISystemOptionalValue>();
        SystemOptionalItemValue yesSystemOptionalItemValue = new SystemOptionalItemValue();
        yesSystemOptionalItemValue.setTitle("\u662f");
        yesSystemOptionalItemValue.setValue(YES);
        SystemOptionalItemValue noSystemOptionalItemValue = new SystemOptionalItemValue();
        noSystemOptionalItemValue.setTitle("\u5426");
        noSystemOptionalItemValue.setValue(NO);
        optionalValues.add((ISystemOptionalValue)yesSystemOptionalItemValue);
        optionalValues.add((ISystemOptionalValue)noSystemOptionalItemValue);
        return optionalValues;
    }

    public int getOrdinal() {
        return 1;
    }
}

