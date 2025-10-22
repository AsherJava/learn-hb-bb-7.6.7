/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.np.core.context.NpContext
 */
package com.jiuqi.gcreport.calculate.env.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.np.core.context.NpContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class CalcContextExpandVariableCenter {
    private List<GcOffSetVchrItemDTO> preCalcOffSetItems = Collections.synchronizedList(new ArrayList());
    private NpContext npContext;
    private GcOrgCenterService orgCenterService;
    private static ThreadLocal<Double> phsValueLocal = new ThreadLocal();
    private AtomicBoolean existsDisposedLedgers = new AtomicBoolean(false);
    private Map<String, ConcurrentLinkedQueue<String>> preGenerateRuleId2MRecidsQueue = new ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>();

    public NpContext getNpContext() {
        return this.npContext;
    }

    public void setNpContext(NpContext npContext) {
        this.npContext = npContext;
    }

    public GcOrgCenterService getOrgCenterService() {
        return this.orgCenterService;
    }

    public void setOrgCenterService(GcOrgCenterService orgCenterService) {
        this.orgCenterService = orgCenterService;
    }

    public Double getPhsValue() {
        return phsValueLocal.get();
    }

    public void setPhsValue(Double phsValue) {
        phsValueLocal.set(phsValue);
    }

    public List<GcOffSetVchrItemDTO> getPreCalcOffSetItems() {
        return this.preCalcOffSetItems;
    }

    public Map<String, ConcurrentLinkedQueue<String>> getPreGenerateRuleId2MRecidsQueue() {
        return this.preGenerateRuleId2MRecidsQueue;
    }

    public void setPreGenerateRuleId2MRecidsQueue(Map<String, ConcurrentLinkedQueue<String>> preGenerateRuleId2MRecidsQueue) {
        this.preGenerateRuleId2MRecidsQueue = preGenerateRuleId2MRecidsQueue;
    }

    public void preGenerateRuleId2MRecids(GcCalcEnvContext env, String ruleId, long preCount) {
        Map<String, ConcurrentLinkedQueue<String>> preGenerateRuleId2MRecidsQueueMap = env.getCalcContextExpandVariableCenter().getPreGenerateRuleId2MRecidsQueue();
        if (preGenerateRuleId2MRecidsQueueMap.get(ruleId) == null) {
            this.getPreGenerateRuleId2MRecidsQueue().put(ruleId, new ConcurrentLinkedQueue());
        }
        int i = 0;
        while ((long)i < preCount) {
            String preMRecid = UUIDOrderUtils.newUUIDStr();
            this.getPreGenerateRuleId2MRecidsQueue().get(ruleId).offer(preMRecid);
            ++i;
        }
    }

    public String getPreGernerateOffsetItemMRecid(String ruleId) {
        if (StringUtils.isEmpty((String)ruleId)) {
            return UUIDOrderUtils.newUUIDStr();
        }
        ConcurrentLinkedQueue<String> preMRecidsDeque = this.getPreGenerateRuleId2MRecidsQueue().get(ruleId);
        if (!CollectionUtils.isEmpty(preMRecidsDeque)) {
            String id = preMRecidsDeque.poll();
            return StringUtils.isEmpty((String)id) ? UUIDOrderUtils.newUUIDStr() : id;
        }
        return UUIDOrderUtils.newUUIDStr();
    }

    public AtomicBoolean getExistsDisposedLedgers() {
        return this.existsDisposedLedgers;
    }
}

