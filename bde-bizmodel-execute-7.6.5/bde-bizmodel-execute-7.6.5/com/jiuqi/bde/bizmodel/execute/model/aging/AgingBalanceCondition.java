/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.AgingFetchTypeEnum
 *  com.jiuqi.bde.common.constant.AgingPeriodTypeEnum
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.common.base.util.DateUtils
 */
package com.jiuqi.bde.bizmodel.execute.model.aging;

import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.common.constant.AgingFetchTypeEnum;
import com.jiuqi.bde.common.constant.AgingPeriodTypeEnum;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.common.base.util.DateUtils;
import java.util.Date;
import java.util.List;

public class AgingBalanceCondition
extends BalanceCondition {
    private AgingFetchTypeEnum agingFetchType;
    private AgingPeriodTypeEnum agingPeriodType;
    private Integer agingStartPeriod;
    private Integer agingEndPeriod;
    private String agingFetchDate;
    private String ageGroup;

    public AgingBalanceCondition() {
    }

    public AgingBalanceCondition(String taskId, String unitCode, Date startDate, Date endDate, List<Dimension> assTypeList, OrgMappingDTO orgMapping, boolean includeUncharged) {
        super(taskId, unitCode, startDate, endDate, assTypeList, orgMapping, includeUncharged);
        this.agingFetchDate = DateUtils.format((Date)endDate);
    }

    public void initAgingArgs(AgingFetchTypeEnum agingFetchType, AgingPeriodTypeEnum agingPeriodType, int agingStartPeriod, int agingEndPeriod) {
        this.agingFetchType = agingFetchType;
        this.agingPeriodType = agingPeriodType;
        this.agingStartPeriod = agingStartPeriod;
        this.agingEndPeriod = agingEndPeriod;
    }

    public AgingFetchTypeEnum getAgingFetchType() {
        return this.agingFetchType;
    }

    public void setAgingFetchType(String agingFetchType) {
        this.agingFetchType = AgingFetchTypeEnum.fromName((String)agingFetchType);
    }

    public AgingPeriodTypeEnum getAgingPeriodType() {
        return this.agingPeriodType;
    }

    public void setAgingPeriodType(String agingPeriodType) {
        this.agingPeriodType = AgingPeriodTypeEnum.fromCode((String)agingPeriodType);
    }

    public Integer getAgingStartPeriod() {
        return this.agingStartPeriod;
    }

    public void setAgingStartPeriod(Integer agingStartPeriod) {
        this.agingStartPeriod = agingStartPeriod;
    }

    public Integer getAgingEndPeriod() {
        return this.agingEndPeriod;
    }

    public void setAgingEndPeriod(Integer agingEndPeriod) {
        this.agingEndPeriod = agingEndPeriod;
    }

    public String getAgingFetchDate() {
        return this.agingFetchDate;
    }

    public void setAgingFetchDate(String agingFetchDate) {
        this.agingFetchDate = agingFetchDate;
    }

    public void setAgingFetchType(AgingFetchTypeEnum agingFetchType) {
        this.agingFetchType = agingFetchType;
    }

    public void setAgingPeriodType(AgingPeriodTypeEnum agingPeriodType) {
        this.agingPeriodType = agingPeriodType;
    }

    public String getAgeGroup() {
        return this.ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    @Override
    public String getBizCombId() {
        StringBuilder bizCombIdBuilder = new StringBuilder(super.getBizCombId());
        bizCombIdBuilder.append(ModelExecuteUtil.getValByDefault(this.getAgingFetchType().name())).append("|");
        bizCombIdBuilder.append(ModelExecuteUtil.getValByDefault(this.getAgingPeriodType().getCode())).append("|");
        bizCombIdBuilder.append(ModelExecuteUtil.getValByDefault(this.getAgingFetchDate())).append("|");
        bizCombIdBuilder.append(ModelExecuteUtil.getValByDefault(this.getAgingStartPeriod())).append("|");
        bizCombIdBuilder.append(ModelExecuteUtil.getValByDefault(this.getAgingEndPeriod())).append("|");
        bizCombIdBuilder.append(ModelExecuteUtil.getValByDefault(this.getAgeGroup())).append("|");
        bizCombIdBuilder.append(ModelExecuteUtil.getValByDefault(this.getAssTypeList())).append("|");
        return bizCombIdBuilder.toString();
    }
}

