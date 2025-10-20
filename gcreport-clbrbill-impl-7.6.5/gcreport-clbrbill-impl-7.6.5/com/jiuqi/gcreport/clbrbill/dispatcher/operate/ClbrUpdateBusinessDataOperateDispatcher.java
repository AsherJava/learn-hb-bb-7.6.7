/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.gcreport.clbrbill.dispatcher.operate;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.clbrbill.dispatcher.operate.ClbrUpdateBusinessDataOperateHandler;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClbrUpdateBusinessDataOperateDispatcher {
    @Autowired
    private List<ClbrUpdateBusinessDataOperateHandler> clbrUpdateBusinessDataOperateHandlerList;

    public ClbrUpdateBusinessDataOperateHandler dispatch(String operateType) {
        for (ClbrUpdateBusinessDataOperateHandler handler : this.clbrUpdateBusinessDataOperateHandlerList) {
            if (!operateType.equals(handler.getOperateType())) continue;
            return handler;
        }
        throw new BusinessRuntimeException("\u64cd\u4f5c\u7c7b\u578b\u4e3a\uff1a" + operateType + "\u7684\u5904\u7406\u5668\u672a\u627e\u5230");
    }
}

