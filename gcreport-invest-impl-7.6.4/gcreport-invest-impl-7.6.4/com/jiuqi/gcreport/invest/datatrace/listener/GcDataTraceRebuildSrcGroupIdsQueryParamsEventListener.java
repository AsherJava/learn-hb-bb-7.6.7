/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.billcore.offsetcheck.service.GcBillTracerService
 *  com.jiuqi.gcreport.datatrace.event.GcDataTraceRebuildSrcGroupIdsQueryParamsEvent
 *  com.jiuqi.gcreport.datatrace.event.GcDataTraceRebuildSrcGroupIdsQueryParamsEvent$QueryParamsInfo
 */
package com.jiuqi.gcreport.invest.datatrace.listener;

import com.jiuqi.gcreport.billcore.offsetcheck.service.GcBillTracerService;
import com.jiuqi.gcreport.datatrace.event.GcDataTraceRebuildSrcGroupIdsQueryParamsEvent;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class GcDataTraceRebuildSrcGroupIdsQueryParamsEventListener
implements ApplicationListener<GcDataTraceRebuildSrcGroupIdsQueryParamsEvent> {
    @Autowired
    GcBillTracerService gcBillTracerService;

    @Override
    public void onApplicationEvent(GcDataTraceRebuildSrcGroupIdsQueryParamsEvent event) {
        GcDataTraceRebuildSrcGroupIdsQueryParamsEvent.QueryParamsInfo queryParamsInfo = event.getQueryParamsInfo();
        List associatedDataSrcGroupIds = this.gcBillTracerService.getAssociatedDataSrcGroupIds(queryParamsInfo.getCondi());
        event.getQueryParamsInfo().getSrcGroupIds().addAll(associatedDataSrcGroupIds);
    }
}

