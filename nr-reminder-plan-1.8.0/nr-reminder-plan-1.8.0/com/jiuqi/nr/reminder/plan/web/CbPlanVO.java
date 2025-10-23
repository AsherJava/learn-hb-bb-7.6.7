/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.reminder.plan.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.reminder.plan.CbPlanDTO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanDO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanFormDO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanTimeDO;
import com.jiuqi.nr.reminder.plan.web.CbPlanTimeVO;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class CbPlanVO
extends CbPlanDTO {
    private int planExeCount;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp preExeTime;
    private String nextExeTime;
    private List<String> ccInfos = new ArrayList<String>();
    protected int periodType;
    protected LinkedHashSet<CbPlanTimeVO> timeVos;
    private String unitName;

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public CbPlanVO() {
    }

    public CbPlanVO(CbPlanDO cbPlanDO) {
        this.planId = cbPlanDO.getId();
        this.title = cbPlanDO.getTitle();
        this.enabled = cbPlanDO.getEnabled() == 1;
        this.userType = cbPlanDO.getUserType();
        this.sendChannels.addAll(Arrays.asList(cbPlanDO.getSendChannel().split(";")));
        this.execUser = cbPlanDO.getExecUser();
        this.effectiveStartTime = cbPlanDO.getEffectiveStartTime();
        this.effectiveEndTime = cbPlanDO.getEffectiveEndTime();
        this.kind = cbPlanDO.getKind();
        this.content = cbPlanDO.getContent();
        this.taskId = cbPlanDO.getTaskId();
        this.formSchemeId = cbPlanDO.getFormSchemeId();
        this.workFlowType = WorkFlowType.fromType((int)cbPlanDO.getWorkFlowType());
        this.execUnit = cbPlanDO.getExecUnit();
        this.unitScop = cbPlanDO.getUnitScop();
        this.formScop = cbPlanDO.getFormScop();
        this.createUser = cbPlanDO.getCreateUser();
        this.updateUser = cbPlanDO.getUpdateUser();
        this.createTime = cbPlanDO.getCreateTime();
        this.updateTime = cbPlanDO.getUpdateTime();
        this.source = cbPlanDO.getSource();
    }

    public int getPlanExeCount() {
        return this.planExeCount;
    }

    public void setPlanExeCount(int planExeCount) {
        this.planExeCount = planExeCount;
    }

    public Timestamp getPreExeTime() {
        return this.preExeTime;
    }

    public void setPreExeTime(Timestamp preExeTime) {
        this.preExeTime = preExeTime;
    }

    public String getNextExeTime() {
        return this.nextExeTime;
    }

    public void setNextExeTime(String nextExeTime) {
        this.nextExeTime = nextExeTime;
    }

    public List<String> getCcInfos() {
        return this.ccInfos;
    }

    public void setCcInfos(List<String> ccInfos) {
        this.ccInfos = ccInfos;
    }

    public LinkedHashSet<CbPlanTimeVO> getTimeVos() {
        return this.timeVos;
    }

    public void setTimeVos(LinkedHashSet<CbPlanTimeVO> timeVos) {
        this.timeVos = timeVos;
    }

    public void toTimeList() {
        if (CollectionUtils.isEmpty(this.timeVos)) {
            CbPlanTimeDO cbPlanTimeDO = new CbPlanTimeDO();
            cbPlanTimeDO.setPlanId(this.planId);
            cbPlanTimeDO.setId(OrderGenerator.newOrder());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(this.getEffectiveStartTime());
            cbPlanTimeDO.setPeriodType(0);
            cbPlanTimeDO.setTime(new Timestamp(calendar.getTimeInMillis()));
            this.setTimes(Collections.singletonList(cbPlanTimeDO));
            return;
        }
        LinkedHashSet<CbPlanTimeVO> timeVos = this.timeVos;
        ArrayList<CbPlanTimeDO> times = new ArrayList<CbPlanTimeDO>();
        for (CbPlanTimeVO timeVo : timeVos) {
            int periodValue;
            CbPlanTimeDO timeDO = new CbPlanTimeDO();
            timeDO.setPlanId(this.getPlanId());
            timeDO.setId(OrderGenerator.newOrder());
            timeDO.setPeriodType(this.getPeriodType());
            timeDO.setPeriodValue(timeVo.getPeriodValue());
            Calendar calendar = Calendar.getInstance();
            if (this.getPeriodType() == 0) {
                calendar.setTime(this.getEffectiveStartTime());
                timeDO.setTime(new Timestamp(calendar.getTimeInMillis()));
                times.add(timeDO);
                break;
            }
            if (this.getPeriodType() == 2) {
                calendar.setTime(this.getEffectiveStartTime());
                timeDO.setTime(new Timestamp(calendar.getTimeInMillis()));
                times.add(timeDO);
                break;
            }
            if (this.getPeriodType() == 3) {
                calendar.setTime(this.getEffectiveStartTime());
                timeDO.setTime(new Timestamp(calendar.getTimeInMillis()));
                times.add(timeDO);
                break;
            }
            if (this.getPeriodType() == 4) {
                calendar.setTime(timeVo.getTime());
                timeDO.setTime(new Timestamp(calendar.getTimeInMillis()));
            } else if (this.getPeriodType() == 5) {
                calendar.setTime(timeVo.getTime());
                periodValue = timeVo.getPeriodValue();
                calendar.set(5, periodValue);
                timeDO.setTime(new Timestamp(calendar.getTimeInMillis()));
            } else {
                if (this.getPeriodType() != 6) continue;
                calendar.setTime(timeVo.getTime());
                periodValue = timeVo.getPeriodValue() - 1;
                calendar.set(2, periodValue);
                int periodValue2 = timeVo.getPeriodValue2();
                calendar.set(5, periodValue2);
                timeDO.setTime(new Timestamp(calendar.getTimeInMillis()));
            }
            times.add(timeDO);
        }
        this.setTimes(times);
    }

    public void fromTimeList(List<CbPlanTimeDO> cbPlanTimeDOS) {
        this.times = cbPlanTimeDOS;
        this.timeVos = new LinkedHashSet();
        for (CbPlanTimeDO time : this.times) {
            CbPlanTimeVO timeVO = new CbPlanTimeVO();
            timeVO.setPeriodValue(time.getPeriodValue());
            timeVO.setTime(time.getTime());
            Calendar instance = Calendar.getInstance();
            instance.setTime(time.getTime());
            this.periodType = time.getPeriodType();
            if (this.getPeriodType() == 6) {
                timeVO.setPeriodValue2(instance.get(5));
            }
            this.timeVos.add(timeVO);
        }
    }

    @Override
    public void fromFormList(List<CbPlanFormDO> formList) {
        if (!CollectionUtils.isEmpty(formList)) {
            this.formIds = new ArrayList();
            for (CbPlanFormDO formDO : formList) {
                this.formIds.add(formDO.getFormId());
            }
        }
    }

    public int getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }
}

