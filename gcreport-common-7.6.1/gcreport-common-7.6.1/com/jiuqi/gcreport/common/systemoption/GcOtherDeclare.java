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
package com.jiuqi.gcreport.common.systemoption;

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
public class GcOtherDeclare
extends GcNormalOptionDeclare {
    public static final String UPDATE_PRCTRMAPPING_OPTION = "UPDATE_PRCTRMAPPING_OPTION";
    public static final String ID = "gc-other-group";

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "\u5176\u4ed6";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(this.getOptionItem());
        return optionItems;
    }

    private ISystemOptionItem getOptionItem() {
        SystemOptionItem systemOptionItem = new SystemOptionItem();
        systemOptionItem.setId(UPDATE_PRCTRMAPPING_OPTION);
        systemOptionItem.setTitle("\u5141\u8bb8\u4fee\u6539\u5229\u6da6\u4e2d\u5fc3\u62a5\u8868\u5355\u4f4d\u6620\u5c04");
        systemOptionItem.setDefaultValue("0");
        systemOptionItem.setEditMode(SystemOptionConst.EditMode.RADIO_BUTTON);
        systemOptionItem.setOptionalValues(this.listOptionValues());
        return systemOptionItem;
    }

    private List<ISystemOptionalValue> listOptionValues() {
        ArrayList<ISystemOptionalValue> optionalValues = new ArrayList<ISystemOptionalValue>();
        SystemOptionalItemValue yesSystemOptionalItemValue = new SystemOptionalItemValue();
        yesSystemOptionalItemValue.setTitle("\u662f");
        yesSystemOptionalItemValue.setValue("1");
        SystemOptionalItemValue noSystemOptionalItemValue = new SystemOptionalItemValue();
        noSystemOptionalItemValue.setTitle("\u5426");
        noSystemOptionalItemValue.setValue("0");
        optionalValues.add((ISystemOptionalValue)yesSystemOptionalItemValue);
        optionalValues.add((ISystemOptionalValue)noSystemOptionalItemValue);
        return optionalValues;
    }

    @Override
    public int getOrdinal() {
        return 10;
    }
}

