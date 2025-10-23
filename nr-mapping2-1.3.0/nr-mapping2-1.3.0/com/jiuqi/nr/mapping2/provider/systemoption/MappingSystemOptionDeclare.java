/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 */
package com.jiuqi.nr.mapping2.provider.systemoption;

import com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class MappingSystemOptionDeclare
extends BaseNormalOptionDeclare {
    private List<ISystemOptionItem> optionItems = null;

    public String getId() {
        return "MAPPING_SYSTEM_OPTION";
    }

    public String getTitle() {
        return "\u6620\u5c04\u65b9\u6848";
    }

    public List<ISystemOptionItem> getOptionItems() {
        if (null != this.optionItems) {
            return this.optionItems;
        }
        ArrayList<ISystemOptionItem> list = new ArrayList<ISystemOptionItem>();
        SystemOptionItem filterHiddenZb = new SystemOptionItem();
        filterHiddenZb.setId("MAPPING_SHOW_JQR_CONFIG");
        filterHiddenZb.setTitle("\u662f\u5426\u5c55\u793aJQR\u6620\u5c04\u914d\u7f6e");
        filterHiddenZb.setEditMode(SystemOptionConst.EditMode.TRUE_FALSE);
        filterHiddenZb.setDefaultValue("0");
        list.add((ISystemOptionItem)filterHiddenZb);
        this.optionItems = list;
        return list;
    }
}

