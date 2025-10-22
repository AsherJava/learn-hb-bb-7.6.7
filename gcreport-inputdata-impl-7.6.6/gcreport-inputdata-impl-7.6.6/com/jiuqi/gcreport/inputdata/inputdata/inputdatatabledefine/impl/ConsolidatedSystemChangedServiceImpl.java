/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.event.ConsolidatedSystemChangedEvent
 */
package com.jiuqi.gcreport.inputdata.inputdata.inputdatatabledefine.impl;

import com.jiuqi.gcreport.consolidatedsystem.event.ConsolidatedSystemChangedEvent;
import com.jiuqi.gcreport.inputdata.inputdata.inputdatatabledefine.InputDataTableDefineService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class ConsolidatedSystemChangedServiceImpl
implements ApplicationListener<ConsolidatedSystemChangedEvent> {
    private InputDataTableDefineService inputDataTableDefineService;

    public ConsolidatedSystemChangedServiceImpl(InputDataTableDefineService inputDataTableDefineService) {
        this.inputDataTableDefineService = inputDataTableDefineService;
    }

    @Override
    public void onApplicationEvent(ConsolidatedSystemChangedEvent event) {
        this.inputDataTableDefineService.createInputDataTableByDataSchemeKey(event.getConsolidatedSystemChangedInfo().getDataSchemeKey());
    }
}

