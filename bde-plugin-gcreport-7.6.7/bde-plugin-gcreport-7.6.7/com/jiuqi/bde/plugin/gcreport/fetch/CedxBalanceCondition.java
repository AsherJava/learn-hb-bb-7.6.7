/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.bde.plugin.gcreport.fetch;

import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CedxBalanceCondition
extends BalanceCondition {
    private String taskId;
    private final String fetchDate;
    private String periodScheme;
    private Map<String, String> otherEntity;
    public static final String FN_MD_CURRENCY = "MD_CURRENCY";
    public static final String FN_ORGTYPE = "ORGTYPE";
    public static final String FN_MD_GCORGTYPE = "MD_GCORGTYPE";

    public CedxBalanceCondition(String taskId, String unitCode, Date startDate, Date endDate, List<Dimension> assTypeList, OrgMappingDTO orgMapping, boolean includeUncharged) {
        super(taskId, unitCode, startDate, endDate, assTypeList, orgMapping, Boolean.valueOf(includeUncharged));
        this.fetchDate = DateUtils.format((Date)endDate);
    }

    public void initCedxArgs(String taskId, String periodScheme, Map<String, String> otherEntity) {
        this.taskId = taskId;
        this.periodScheme = periodScheme;
        this.otherEntity = otherEntity == null ? new HashMap() : otherEntity;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFetchDate() {
        return this.fetchDate;
    }

    public String getPeriodScheme() {
        return this.periodScheme;
    }

    public void setPeriodScheme(String periodScheme) {
        this.periodScheme = periodScheme;
    }

    public Map<String, String> getOtherEntity() {
        return this.otherEntity;
    }

    public void setOtherEntity(Map<String, String> otherEntity) {
        this.otherEntity = otherEntity;
    }

    public String getEntityValByKey(String key) {
        if (StringUtils.isEmpty((String)key) || this.otherEntity == null || this.otherEntity.isEmpty()) {
            return "";
        }
        return this.otherEntity.get(key);
    }

    public String toString() {
        return "CedxBalanceCondition [taskId=" + this.taskId + ", fetchDate=" + this.fetchDate + ", periodScheme=" + this.periodScheme + ", otherEntity=" + this.otherEntity + ", toString()=" + super.toString() + "]";
    }
}

