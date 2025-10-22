/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$CategoryType
 */
package com.jiuqi.nr.dataentry.options;

import com.jiuqi.nr.dataentry.options.PierceManageOperator;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PierceManageDeclare
implements ISystemOptionDeclare {
    @Autowired
    private PierceManageOperator pierceManageOperator;

    public String getId() {
        return "pierce-manage-declar";
    }

    public String getTitle() {
        return "\u7a7f\u900f\u63d2\u4ef6\u7ba1\u7406";
    }

    public String getNameSpace() {
        return "\u62a5\u8868";
    }

    public SystemOptionConst.CategoryType getCategoryType() {
        return SystemOptionConst.CategoryType.CUSTOM;
    }

    public boolean disableReset() {
        return true;
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        SystemOptionItem item = new SystemOptionItem();
        item.setId("tableData");
        item.setTitle("\u7a7f\u900f\u63d2\u4ef6");
        optionItems.add((ISystemOptionItem)item);
        return optionItems;
    }

    public boolean disableImportExport() {
        return true;
    }

    public String getPluginName() {
        return "pierceManagePlugin";
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.pierceManageOperator;
    }
}

