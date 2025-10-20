/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 */
package com.jiuqi.gcreport.calculate.dto;

import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class GcCalcArgmentsDTO
extends GcTaskBaseArguments
implements Serializable {
    private String sn;
    private List<String> ruleIds;
    private String adjtypeId;
    private List<String> recordIds;
    private AtomicBoolean preCalcFlag = new AtomicBoolean(false);
    private AtomicBoolean disabledCopyInitOffset = new AtomicBoolean(false);
    private transient Map<String, Object> extendInfo = new HashMap<String, Object>();

    public List<String> getRuleIds() {
        return this.ruleIds;
    }

    public void setRuleIds(List<String> ruleIds) {
        this.ruleIds = ruleIds;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getAdjtypeId() {
        return this.adjtypeId;
    }

    public void setAdjtypeId(String adjtypeId) {
        this.adjtypeId = adjtypeId;
    }

    public List<String> getRecordIds() {
        return this.recordIds;
    }

    public void setRecordIds(List<String> recordIds) {
        this.recordIds = recordIds;
    }

    public AtomicBoolean getPreCalcFlag() {
        return this.preCalcFlag;
    }

    public void setPreCalcFlag(AtomicBoolean preCalcFlag) {
        this.preCalcFlag = preCalcFlag;
    }

    public AtomicBoolean getDisabledCopyInitOffset() {
        return this.disabledCopyInitOffset;
    }

    public void setDisabledCopyInitOffset(AtomicBoolean disabledCopyInitOffset) {
        this.disabledCopyInitOffset = disabledCopyInitOffset;
    }

    public Map<String, Object> getExtendInfo() {
        return this.extendInfo;
    }

    public void setExtendInfo(Map<String, Object> extendInfo) {
        this.extendInfo = extendInfo;
    }
}

