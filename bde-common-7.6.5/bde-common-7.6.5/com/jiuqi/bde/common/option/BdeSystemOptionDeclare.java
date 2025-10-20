/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 */
package com.jiuqi.bde.common.option;

import com.jiuqi.bde.common.option.BdeOptionDeclareItemsGather;
import com.jiuqi.bde.common.option.BdeOptionTypeEnum;
import com.jiuqi.bde.common.option.BdeSystemOptionOperator;
import com.jiuqi.bde.common.option.IBdeOptionDeclareItems;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeSystemOptionDeclare
implements ISystemOptionDeclare {
    private static final long serialVersionUID = 6904760149658927192L;
    @Autowired
    private BdeOptionDeclareItemsGather declareItemsGather;
    @Autowired
    private BdeSystemOptionOperator systemOptionOperator;

    public String getId() {
        return BdeOptionTypeEnum.SYSTEM.getCode();
    }

    public String getTitle() {
        return "BDE\u7cfb\u7edf\u914d\u7f6e";
    }

    public String getNameSpace() {
        return "BDE";
    }

    public List<ISystemOptionItem> getOptionItems() {
        List<IBdeOptionDeclareItems> optionDeclareItems = this.declareItemsGather.getOptionItemsDeclareByType(BdeOptionTypeEnum.SYSTEM);
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        for (IBdeOptionDeclareItems optionDeclareItem : optionDeclareItems) {
            optionItems.add((ISystemOptionItem)optionDeclareItem.buildOptionGroup());
            optionItems.addAll(optionDeclareItem.getItems());
        }
        return optionItems;
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.systemOptionOperator;
    }

    public int getOrdinal() {
        return 0x7FFFFFFE;
    }
}

