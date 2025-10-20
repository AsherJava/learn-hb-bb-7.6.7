/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItemValue
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.vo.ResultObject
 */
package com.jiuqi.bde.common.option;

import com.jiuqi.bde.common.option.BdeOptionDeclareItemsGather;
import com.jiuqi.bde.common.option.IBdeSystemOptionItemHandler;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItemValue;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator;
import com.jiuqi.nvwa.systemoption.vo.ResultObject;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeSystemOptionOperator
extends SystemOptionOperator {
    private static final long serialVersionUID = 5844289219400508538L;
    @Autowired
    private BdeOptionDeclareItemsGather optionDeclareItemsGather;

    public ResultObject save(List<ISystemOptionItemValue> itemValues) {
        ResultObject resultObject = super.save(itemValues);
        for (IBdeSystemOptionItemHandler optionItemHandler : this.optionDeclareItemsGather.listOptionItemHandler()) {
            for (ISystemOptionItemValue itemValue : itemValues) {
                optionItemHandler.doSaveHandle(itemValue);
            }
        }
        return resultObject;
    }
}

