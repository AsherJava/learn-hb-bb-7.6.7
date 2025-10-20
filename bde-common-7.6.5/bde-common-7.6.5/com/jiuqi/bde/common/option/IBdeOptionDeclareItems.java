/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 */
package com.jiuqi.bde.common.option;

import com.jiuqi.bde.common.option.BdeOptionTypeEnum;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.List;

public interface IBdeOptionDeclareItems {
    public BdeOptionTypeEnum getOptionType();

    public String getId();

    public String getTitle();

    public List<ISystemOptionItem> getItems();

    public int getOrder();

    default public SystemOptionItem buildOptionGroup() {
        SystemOptionItem optionGroup = new SystemOptionItem();
        optionGroup.setId(this.getId());
        optionGroup.setTitle(this.getTitle());
        optionGroup.setEditMode(SystemOptionConst.EditMode.GROUP);
        return optionGroup;
    }
}

