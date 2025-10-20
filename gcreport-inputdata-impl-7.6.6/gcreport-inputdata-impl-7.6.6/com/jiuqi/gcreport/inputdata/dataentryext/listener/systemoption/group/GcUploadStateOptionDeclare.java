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
package com.jiuqi.gcreport.inputdata.dataentryext.listener.systemoption.group;

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
public class GcUploadStateOptionDeclare
extends GcNormalOptionDeclare {
    private final String GCUPLOAD_STATE_OPTION_DECLARE = "GCUPLOAD_STATE_OPTION_DECLARE";
    private final String GCUPLOAD_STATE_OPTION = "GCUPLOAD_STATE_OPTION";

    public String getId() {
        return "GCUPLOAD_STATE_OPTION_DECLARE";
    }

    public String getTitle() {
        return "\u7ba1\u7406\u67b6\u6784\u5355\u4f4d\u72b6\u6001\u540c\u6b65";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(this.getOptionItem());
        return optionItems;
    }

    private ISystemOptionItem getOptionItem() {
        SystemOptionItem systemOptionItem = new SystemOptionItem();
        systemOptionItem.setId("GCUPLOAD_STATE_OPTION");
        systemOptionItem.setTitle("\u542f\u7528\u7ba1\u7406\u67b6\u6784\u5c42\u5c42\u6821\u9a8c");
        systemOptionItem.setDefaultValue("0");
        systemOptionItem.setEditMode(SystemOptionConst.EditMode.DROP_DOWN_SINGLE);
        systemOptionItem.setOptionalValues(this.listOptionValues());
        return systemOptionItem;
    }

    private List<ISystemOptionalValue> listOptionValues() {
        ArrayList<ISystemOptionalValue> optionalValues = new ArrayList<ISystemOptionalValue>();
        SystemOptionalItemValue yesSystemOptionalItemValue = new SystemOptionalItemValue();
        yesSystemOptionalItemValue.setTitle("\u5f3a\u63a7");
        yesSystemOptionalItemValue.setValue("1");
        SystemOptionalItemValue noSystemOptionalItemValue = new SystemOptionalItemValue();
        noSystemOptionalItemValue.setTitle("\u4e0d\u542f\u7528");
        noSystemOptionalItemValue.setValue("0");
        optionalValues.add((ISystemOptionalValue)yesSystemOptionalItemValue);
        optionalValues.add((ISystemOptionalValue)noSystemOptionalItemValue);
        return optionalValues;
    }
}

