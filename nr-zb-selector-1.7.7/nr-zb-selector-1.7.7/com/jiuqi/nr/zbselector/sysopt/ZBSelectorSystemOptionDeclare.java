/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 */
package com.jiuqi.nr.zbselector.sysopt;

import com.jiuqi.nr.system.options.adaptation.BaseNormalOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ZBSelectorSystemOptionDeclare
extends BaseNormalOptionDeclare {
    private List<ISystemOptionItem> optionItems = null;

    public String getId() {
        return "ZBSELECTOR_SYSTEM_OPTION";
    }

    public String getTitle() {
        return "\u6307\u6807\u9009\u62e9\u5668";
    }

    public List<ISystemOptionItem> getOptionItems() {
        if (null != this.optionItems) {
            return this.optionItems;
        }
        ArrayList<ISystemOptionItem> list = new ArrayList<ISystemOptionItem>();
        SystemOptionItem filterHiddenZb = new SystemOptionItem();
        filterHiddenZb.setId("ZBSELECTOR_SHOW_HIDDEN_ZB");
        filterHiddenZb.setTitle("\u663e\u793a\u9690\u85cf\u6307\u6807");
        filterHiddenZb.setEditMode(SystemOptionConst.EditMode.TRUE_FALSE);
        filterHiddenZb.setDefaultValue("0");
        list.add((ISystemOptionItem)filterHiddenZb);
        this.optionItems = list;
        return list;
    }
}

