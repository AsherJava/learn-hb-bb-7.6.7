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

import com.jiuqi.gcreport.common.systemoption.GcClbrNormalOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionalItemValue;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GcClbrOptionDeclare
extends GcClbrNormalOptionDeclare {
    public String getId() {
        return "GCREPORT_CLBR";
    }

    public String getTitle() {
        return "\u534f\u540c\u76f8\u5173\u914d\u7f6e";
    }

    private ISystemOptionItem getClbrOldSplitTitleOption() {
        SystemOptionItem systemOptionItem = new SystemOptionItem();
        systemOptionItem.setId("GCREPORT_CLBR_OLD_OPTIONTITLE");
        systemOptionItem.setTitle("\u65e7\u534f\u540c\u9009\u9879");
        systemOptionItem.setDefaultValue("");
        systemOptionItem.setEditMode(SystemOptionConst.EditMode.GROUP);
        return systemOptionItem;
    }

    private ISystemOptionItem getClbrRigIdOption() {
        SystemOptionItem systemOptionItem = new SystemOptionItem();
        systemOptionItem.setId("GCREPORT_CLBR_RIGID");
        systemOptionItem.setTitle("\u6d41\u7a0b\u521a\u6027\u4e0d\u5141\u8bb8\u9009\u62e9\u591a\u6761\u6570\u636e\u8fdb\u884c\u534f\u540c\u751f\u5355");
        systemOptionItem.setDefaultValue("0");
        systemOptionItem.setEditMode(SystemOptionConst.EditMode.RADIO_BUTTON);
        systemOptionItem.setOptionalValues(this.listClbrRadioButtonOptionalValues());
        return systemOptionItem;
    }

    private ISystemOptionItem getClbrNewSplitTitleOption() {
        SystemOptionItem systemOptionItem = new SystemOptionItem();
        systemOptionItem.setId("GCREPORT_CLBR_NEW_OPTIONTITLE");
        systemOptionItem.setTitle("\u6807\u51c6\u534f\u540c\u9009\u9879");
        systemOptionItem.setDefaultValue("");
        systemOptionItem.setEditMode(SystemOptionConst.EditMode.GROUP);
        return systemOptionItem;
    }

    private ISystemOptionItem getClbrThirdEnableOption() {
        SystemOptionItem systemOptionItem = new SystemOptionItem();
        systemOptionItem.setId("GCREPORT_CLBR_THIRD_ENABLE");
        systemOptionItem.setTitle("\u542f\u7528\u534f\u540c\u4e09\u65b9");
        systemOptionItem.setDefaultValue("0");
        systemOptionItem.setEditMode(SystemOptionConst.EditMode.RADIO_BUTTON);
        systemOptionItem.setOptionalValues(this.listClbrRadioButtonOptionalValues());
        return systemOptionItem;
    }

    private List<ISystemOptionalValue> listClbrRadioButtonOptionalValues() {
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

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(this.getClbrOldSplitTitleOption());
        optionItems.add(this.getClbrRigIdOption());
        optionItems.add(this.getClbrNewSplitTitleOption());
        optionItems.add(this.getClbrThirdEnableOption());
        return optionItems;
    }

    @Override
    public int getOrdinal() {
        return 1;
    }
}

