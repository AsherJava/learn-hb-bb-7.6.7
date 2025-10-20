/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.definition.internal.controller.event.FormSchemeUpdateEvent
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.gcreport.formulaschemeconfig.listener;

import com.jiuqi.gcreport.formulaschemeconfig.service.impl.FormulaSchemeConfigEntityIdRepairService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.internal.controller.event.FormSchemeUpdateEvent;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=0x7FFFFFFF)
public class FormulaSchemeConfigFormSchemeUpdateListener
implements ApplicationListener<FormSchemeUpdateEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FormulaSchemeConfigFormSchemeUpdateListener.class);

    @Override
    public void onApplicationEvent(FormSchemeUpdateEvent event) {
        try {
            LOGGER.info("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u76d1\u542c\u4efb\u52a1\u4fee\u6539\u4e8b\u4ef6\uff0c\u5e76\u5f00\u59cb\u53e3\u5f84\u53c2\u6570\u4fee\u590d......");
            ((FormulaSchemeConfigEntityIdRepairService)ApplicationContextRegister.getBean(FormulaSchemeConfigEntityIdRepairService.class)).doRepair(NpContextHolder.getContext().getUserName(), event);
            LOGGER.info("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u76d1\u542c\u4efb\u52a1\u4fee\u6539\u4e8b\u4ef6\uff0c\u5e76\u5b8c\u6210\u53e3\u5f84\u53c2\u6570\u4fee\u590d......");
        }
        catch (Exception e) {
            LOGGER.info("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u76d1\u542c\u4efb\u52a1\u4fee\u6539\u4e8b\u4ef6\uff0c\u4fee\u590d\u53e3\u5f84\u53c2\u6570\u51fa\u73b0\u9519\u8bef{}", (Object)e.getMessage(), (Object)e);
        }
    }
}

