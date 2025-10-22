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
public class GcMultilevelOptionDeclare
extends GcNormalOptionDeclare {
    public static final String ID = "GC_MULTILEVEL_OPTION_DECLARE";
    public static final String ALLOW_VALUE = "1";
    public static final String NOT_ALLOW_VALUE = "0";

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "\u591a\u7ea7\u90e8\u7f72";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(this.getParamUpdateOptionItem());
        return optionItems;
    }

    private ISystemOptionItem getParamUpdateOptionItem() {
        SystemOptionItem systemOptionItem = new SystemOptionItem();
        systemOptionItem.setId("MULTILEVEL_IMPORT_MULTILANGUAGE");
        systemOptionItem.setTitle("\u53c2\u6570\u66f4\u65b0\u662f\u5426\u66f4\u65b0\u591a\u8bed\u8a00");
        systemOptionItem.setDefaultValue(NOT_ALLOW_VALUE);
        systemOptionItem.setEditMode(SystemOptionConst.EditMode.RADIO_BUTTON);
        systemOptionItem.setOptionalValues(this.listParamUpdateOptionValues());
        return systemOptionItem;
    }

    private List<ISystemOptionalValue> listParamUpdateOptionValues() {
        ArrayList<ISystemOptionalValue> optionalValues = new ArrayList<ISystemOptionalValue>();
        SystemOptionalItemValue yesSystemOptionalItemValue = new SystemOptionalItemValue();
        yesSystemOptionalItemValue.setTitle("\u662f");
        yesSystemOptionalItemValue.setValue(ALLOW_VALUE);
        SystemOptionalItemValue noSystemOptionalItemValue = new SystemOptionalItemValue();
        noSystemOptionalItemValue.setTitle("\u5426");
        noSystemOptionalItemValue.setValue(NOT_ALLOW_VALUE);
        optionalValues.add((ISystemOptionalValue)yesSystemOptionalItemValue);
        optionalValues.add((ISystemOptionalValue)noSystemOptionalItemValue);
        return optionalValues;
    }
}

