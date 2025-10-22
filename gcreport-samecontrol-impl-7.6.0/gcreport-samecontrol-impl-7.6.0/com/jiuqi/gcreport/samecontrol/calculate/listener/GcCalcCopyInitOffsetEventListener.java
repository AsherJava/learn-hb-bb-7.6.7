/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.event.GcCalcExecuteInitOffsetItemEvent
 */
package com.jiuqi.gcreport.samecontrol.calculate.listener;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.event.GcCalcExecuteInitOffsetItemEvent;
import com.jiuqi.gcreport.samecontrol.calculate.service.GcCalcInitOffSetItemCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=-2147483648)
public class GcCalcCopyInitOffsetEventListener
implements ApplicationListener<GcCalcExecuteInitOffsetItemEvent> {
    @Autowired
    private GcCalcInitOffSetItemCopyService initOffSetItemCopyService;

    @Override
    public void onApplicationEvent(GcCalcExecuteInitOffsetItemEvent event) {
        GcCalcEnvContext env = event.getEnv();
        this.initOffSetItemCopyService.executeInitToAdjustOffSetItem(env);
    }
}

