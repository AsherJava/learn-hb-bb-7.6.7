/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$CategoryType
 */
package com.jiuqi.dc.base.impl.option;

import com.jiuqi.dc.base.impl.option.DcSysOptionIdEnum;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DCSysOption
implements ISystemOptionDeclare {
    @Autowired
    private SystemOptionOperator systemOptionOperator;

    public String getId() {
        return "DC_MODULE_OPTION";
    }

    public String getTitle() {
        return "\u4e00\u672c\u8d26\u7cfb\u7edf\u914d\u7f6e";
    }

    public String getNameSpace() {
        return "\u4e00\u672c\u8d26";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        for (DcSysOptionIdEnum value : DcSysOptionIdEnum.values()) {
            optionItems.add((ISystemOptionItem)this.newOptionItem(value.name()));
        }
        return optionItems;
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.systemOptionOperator;
    }

    public SystemOptionConst.CategoryType getCategoryType() {
        return SystemOptionConst.CategoryType.CUSTOM;
    }

    public String getPluginName() {
        return "dc-sysoption-plugin";
    }

    private SystemOptionItem newOptionItem(String id) {
        SystemOptionItem optionItem = new SystemOptionItem();
        optionItem.setId(id);
        return optionItem;
    }
}

