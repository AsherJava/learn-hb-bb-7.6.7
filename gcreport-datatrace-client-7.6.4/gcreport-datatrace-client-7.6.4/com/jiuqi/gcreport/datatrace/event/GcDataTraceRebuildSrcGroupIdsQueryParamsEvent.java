/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.datatrace.event;

import com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi;
import java.util.List;
import org.springframework.context.ApplicationEvent;

public class GcDataTraceRebuildSrcGroupIdsQueryParamsEvent
extends ApplicationEvent {
    private QueryParamsInfo queryParamsInfo;

    public GcDataTraceRebuildSrcGroupIdsQueryParamsEvent(Object source, QueryParamsInfo queryParamsInfo) {
        super(source);
        this.queryParamsInfo = queryParamsInfo;
    }

    public QueryParamsInfo getQueryParamsInfo() {
        return this.queryParamsInfo;
    }

    public void setQueryParamsInfo(QueryParamsInfo queryParamsInfo) {
        this.queryParamsInfo = queryParamsInfo;
    }

    public static class QueryParamsInfo {
        private List<String> srcGroupIds;
        private GcDataTraceCondi condi;

        public QueryParamsInfo(GcDataTraceCondi condi, List<String> srcGroupIds) {
            this.condi = condi;
            this.srcGroupIds = srcGroupIds;
        }

        public List<String> getSrcGroupIds() {
            return this.srcGroupIds;
        }

        public void setSrcGroupIds(List<String> srcGroupIds) {
            this.srcGroupIds = srcGroupIds;
        }

        public GcDataTraceCondi getCondi() {
            return this.condi;
        }

        public void setCondi(GcDataTraceCondi condi) {
            this.condi = condi;
        }
    }
}

