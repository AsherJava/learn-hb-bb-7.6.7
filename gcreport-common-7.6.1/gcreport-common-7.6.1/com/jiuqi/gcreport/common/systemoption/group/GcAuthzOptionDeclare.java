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
public class GcAuthzOptionDeclare
extends GcNormalOptionDeclare {
    public String getId() {
        return "gc-option-authz";
    }

    public String getTitle() {
        return "\u6743\u9650\u63a7\u5236";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        SystemOptionItem listedCompanyAuthzOption = this.createListedCompanyAuthOption();
        optionItems.add((ISystemOptionItem)listedCompanyAuthzOption);
        return optionItems;
    }

    private SystemOptionItem createListedCompanyAuthOption() {
        SystemOptionItem systemOptionItem = new SystemOptionItem();
        systemOptionItem.setId("BEGIN_LISTEDCOMPANY_AUTHZ");
        systemOptionItem.setTitle("\u542f\u7528\u4e0a\u5e02\u516c\u53f8\u6743\u9650\u63a7\u5236");
        systemOptionItem.setDefaultValue("0");
        systemOptionItem.setEditMode(SystemOptionConst.EditMode.RADIO_BUTTON);
        systemOptionItem.setOptionalValues(this.getOptionalValues());
        return systemOptionItem;
    }

    private List<ISystemOptionalValue> getOptionalValues() {
        ArrayList<ISystemOptionalValue> values = new ArrayList<ISystemOptionalValue>();
        SystemOptionalItemValue isTrue = new SystemOptionalItemValue();
        isTrue.setTitle("\u662f");
        isTrue.setValue("1");
        SystemOptionalItemValue isFalse = new SystemOptionalItemValue();
        isFalse.setTitle("\u5426");
        isFalse.setValue("0");
        values.add((ISystemOptionalValue)isTrue);
        values.add((ISystemOptionalValue)isFalse);
        return values;
    }

    @Override
    public int getOrdinal() {
        return 1;
    }
}

