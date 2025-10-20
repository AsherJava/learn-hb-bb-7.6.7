/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 */
package com.jiuqi.gcreport.common.systemoption.group;

import com.jiuqi.gcreport.common.systemoption.GcNormalOptionDeclare;
import com.jiuqi.gcreport.common.systemoption.saveoperator.OrgFieldAutoCalcOperator;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcOrgFieldAutoCalcOptionDeclare
extends GcNormalOptionDeclare {
    @Autowired
    protected OrgFieldAutoCalcOperator systemOptionOperator;

    public String getId() {
        return "gc_option_org_fieldCalc";
    }

    public String getTitle() {
        return "\u5408\u5e76\u5355\u4f4d\u7ba1\u7406";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add((ISystemOptionItem)this.createOptionItem());
        optionItems.add((ISystemOptionItem)this.createDisableOptionItem());
        return optionItems;
    }

    @Override
    public int getOrdinal() {
        return 1;
    }

    @Override
    public ISystemOptionOperator getSystemOptionOperator() {
        return this.systemOptionOperator;
    }

    private SystemOptionItem createOptionItem() {
        SystemOptionItem systemOptionItem = new SystemOptionItem();
        systemOptionItem.setId("NOT_AUTO_CALC_FIELD");
        systemOptionItem.setTitle("\u65e0\u9700\u8ba1\u7b97\u7684\u7c7b\u578b\uff0c\u793a\u4f8b\uff1aMD_ORG_XX;MD_ORG_XXX");
        systemOptionItem.setDefaultValue("");
        systemOptionItem.setEditMode(SystemOptionConst.EditMode.TEXT_AREA);
        return systemOptionItem;
    }

    private SystemOptionItem createDisableOptionItem() {
        SystemOptionItem systemOptionItem = new SystemOptionItem();
        systemOptionItem.setId("DISABLE_GCREPORT_ORG");
        systemOptionItem.setTitle("\u7981\u7528\u5408\u5e76\u5355\u4f4d\u7ba1\u7406\uff08\u4e13\u4e1a\u7248\u52ff\u52fe\u9009\uff0c\u4fdd\u5b58\u540e\u4e00\u5206\u949f\u540e\u751f\u6548\uff09");
        systemOptionItem.setDefaultValue("0");
        systemOptionItem.setEditMode(SystemOptionConst.EditMode.TRUE_FALSE);
        return systemOptionItem;
    }
}

