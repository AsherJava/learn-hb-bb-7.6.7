/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.nr.context.infc.impl.NRContext
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.reminder.plan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.user.User;
import com.jiuqi.nr.context.infc.impl.NRContext;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanDO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanDwDO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanFormDO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanTimeDO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanToDO;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public class CbPlanDTO
extends NRContext {
    public static final int PRE_EXECUTION = 1;
    public static final int EXECUTION = 0;
    protected int mode;
    protected String planId;
    protected String title;
    protected String source;
    public static final int MINUTE = 1;
    public static final int HOUR = 2;
    public static final int DAY = 3;
    public static final int WEEK = 4;
    public static final int MONTH = 5;
    public static final int YEAR = 6;
    public static final int ONCE = 0;
    public static final int CRON = 9;
    @JsonIgnore
    protected List<CbPlanTimeDO> times;
    protected boolean enabled;
    public static final int ROLE_PARTICIPANT = 1;
    public static final int ROLE_ALL = 2;
    public static final int ROLE_OPTIONAL = 8;
    protected int userType;
    protected Set<String> sendChannels = new LinkedHashSet<String>();
    protected String execUser;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="GMT+8")
    protected Date effectiveStartTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="GMT+8")
    protected Date effectiveEndTime;
    public static final int KIND_DIRECT = 1;
    public static final int KIND_JOB = 2;
    protected int kind;
    protected String content;
    protected String taskId;
    protected String formSchemeId;
    protected String execUnit;
    protected String execPeriod;
    public static final int DIRECT_SUB = 1;
    public static final int ALL = 2;
    public static final int OPTIONAL = 8;
    protected int unitScop;
    public static final int FORM_ALL = 1;
    public static final int FORM_OPTIONAL = 2;
    protected int formScop;
    protected String createUser;
    protected String updateUser;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="GMT+8")
    protected Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone="GMT+8")
    protected Date updateTime;
    protected List<String> unitIds;
    protected List<String> userIds;
    protected List<String> roleIds;
    protected List<String> ccUserIds;
    protected List<String> ccRoleIds;
    protected List<String> formIds;
    protected WorkFlowType workFlowType;
    protected PeriodWrapper currPeriod;
    protected Date[] currPeriodDate;
    protected String dw;
    protected String dataTime;
    protected Map<String, IEntityRow> unitMap;
    protected Map<String, FormGroupDefine> groupMap;
    protected Map<String, List<String>> group2FormMap;
    protected Map<String, FormDefine> formMap;
    protected Map<String, String> careMap;
    protected final Map<String, User> userMap = new HashMap<String, User>();
    protected String dwDimName;
    protected Map<String, String> dimsNameMap;
    protected Map<String, Set<String>> canCache;
    protected String logId;

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getLogId() {
        return this.logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public Map<String, User> getUserMap() {
        return this.userMap;
    }

    public Map<String, IEntityRow> getUnitMap() {
        return this.unitMap;
    }

    public void setUnitMap(Map<String, IEntityRow> unitMap) {
        this.unitMap = unitMap;
    }

    public String getDw() {
        return this.dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public Map<String, FormGroupDefine> getGroupMap() {
        return this.groupMap;
    }

    public void setGroupMap(Map<String, FormGroupDefine> groupMap) {
        this.groupMap = groupMap;
    }

    public Map<String, FormDefine> getFormMap() {
        return this.formMap;
    }

    public void setFormMap(Map<String, FormDefine> formMap) {
        this.formMap = formMap;
    }

    public Map<String, String> getCareMap() {
        return this.careMap;
    }

    public void setCareMap(Map<String, String> careMap) {
        this.careMap = careMap;
    }

    public Map<String, String> getDimsNameMap() {
        return this.dimsNameMap;
    }

    public void setDimsNameMap(Map<String, String> dimsNameMap) {
        this.dimsNameMap = dimsNameMap;
    }

    public Map<String, Set<String>> getCanCache() {
        return this.canCache;
    }

    public void setCanCache(Map<String, Set<String>> canCache) {
        this.canCache = canCache;
    }

    public String getExecPeriod() {
        return this.execPeriod;
    }

    public void setExecPeriod(String execPeriod) {
        this.execPeriod = execPeriod;
    }

    public CbPlanDO toCbPlanDO() {
        CbPlanDO cbPlanDO = new CbPlanDO();
        cbPlanDO.setId(this.planId);
        cbPlanDO.setTitle(this.title);
        cbPlanDO.setSource(this.source);
        cbPlanDO.setEnabled(this.enabled ? 1 : 0);
        cbPlanDO.setUserType(this.userType);
        StringBuilder channel = new StringBuilder();
        for (String channelItem : this.sendChannels) {
            channel.append(channelItem);
            channel.append(";");
        }
        if (channel.length() > 0) {
            channel.setLength(channel.length() - 1);
        }
        cbPlanDO.setSendChannel(channel.toString());
        cbPlanDO.setExecUser(this.execUser);
        cbPlanDO.setEffectiveStartTime(Timestamp.from(this.effectiveStartTime.toInstant()));
        cbPlanDO.setEffectiveEndTime(Timestamp.from(this.effectiveEndTime.toInstant()));
        cbPlanDO.setKind(this.kind);
        cbPlanDO.setContent(this.content);
        cbPlanDO.setTaskId(this.taskId);
        cbPlanDO.setFormSchemeId(this.formSchemeId);
        cbPlanDO.setWorkFlowType(this.workFlowType.getValue());
        cbPlanDO.setExecUnit(this.execUnit);
        cbPlanDO.setExecPeriod(this.execPeriod);
        cbPlanDO.setUnitScop(this.unitScop);
        cbPlanDO.setFormScop(this.formScop);
        cbPlanDO.setCreateUser(this.createUser);
        cbPlanDO.setUpdateUser(this.updateUser);
        cbPlanDO.setCreateTime(Timestamp.from(this.createTime.toInstant()));
        cbPlanDO.setUpdateTime(Timestamp.from(this.updateTime.toInstant()));
        cbPlanDO.setDw(this.dw);
        return cbPlanDO;
    }

    public void fromCbPlanDO(CbPlanDO cbPlanDO) {
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
        this.execUnit = cbPlanDO.getExecUnit();
        this.execPeriod = cbPlanDO.getExecPeriod();
        this.unitScop = cbPlanDO.getUnitScop();
        this.formScop = cbPlanDO.getFormScop();
        this.createUser = cbPlanDO.getCreateUser();
        this.updateUser = cbPlanDO.getUpdateUser();
        this.createTime = cbPlanDO.getCreateTime();
        this.updateTime = cbPlanDO.getUpdateTime();
        this.workFlowType = WorkFlowType.fromType((int)cbPlanDO.getWorkFlowType());
        this.source = cbPlanDO.getSource();
        this.dw = cbPlanDO.getDw();
    }

    public List<CbPlanDwDO> toDwList() {
        if (this.unitIds == null) {
            return Collections.emptyList();
        }
        ArrayList<CbPlanDwDO> list = new ArrayList<CbPlanDwDO>();
        for (String unitId : this.unitIds) {
            CbPlanDwDO unitDo = new CbPlanDwDO();
            unitDo.setPlanId(this.planId);
            unitDo.setUnitId(unitId);
            list.add(unitDo);
        }
        return list;
    }

    public void fromDwList(List<CbPlanDwDO> dwDOList) {
        if (CollectionUtils.isEmpty(dwDOList)) {
            return;
        }
        this.unitIds = new ArrayList<String>();
        for (CbPlanDwDO cbPlanDwDO : dwDOList) {
            this.unitIds.add(cbPlanDwDO.getUnitId());
        }
    }

    public List<CbPlanToDO> toToList() {
        CbPlanToDO cbPlanToDO;
        ArrayList<CbPlanToDO> list = new ArrayList<CbPlanToDO>();
        if (this.userIds != null) {
            for (String user : this.userIds) {
                cbPlanToDO = new CbPlanToDO();
                cbPlanToDO.setToId(user);
                cbPlanToDO.setPlanId(this.planId);
                cbPlanToDO.setToIdType(2);
                cbPlanToDO.setToType(1);
                list.add(cbPlanToDO);
            }
        }
        if (this.roleIds != null) {
            for (String roleId : this.roleIds) {
                cbPlanToDO = new CbPlanToDO();
                cbPlanToDO.setToId(roleId);
                cbPlanToDO.setPlanId(this.planId);
                cbPlanToDO.setToIdType(1);
                cbPlanToDO.setToType(1);
                list.add(cbPlanToDO);
            }
        }
        if (this.ccUserIds != null) {
            for (String user : this.ccUserIds) {
                cbPlanToDO = new CbPlanToDO();
                cbPlanToDO.setToId(user);
                cbPlanToDO.setPlanId(this.planId);
                cbPlanToDO.setToIdType(2);
                cbPlanToDO.setToType(2);
                list.add(cbPlanToDO);
            }
        }
        if (this.ccRoleIds != null) {
            for (String roleId : this.ccRoleIds) {
                cbPlanToDO = new CbPlanToDO();
                cbPlanToDO.setToId(roleId);
                cbPlanToDO.setPlanId(this.planId);
                cbPlanToDO.setToIdType(1);
                cbPlanToDO.setToType(2);
                list.add(cbPlanToDO);
            }
        }
        return list;
    }

    public void fromToList(List<CbPlanToDO> toList) {
        if (CollectionUtils.isEmpty(toList)) {
            return;
        }
        this.userIds = new ArrayList<String>();
        this.roleIds = new ArrayList<String>();
        this.ccRoleIds = new ArrayList<String>();
        this.ccUserIds = new ArrayList<String>();
        for (CbPlanToDO cbPlanToDO : toList) {
            if (cbPlanToDO.getToType() == 1) {
                if (cbPlanToDO.getToIdType() == 2) {
                    this.userIds.add(cbPlanToDO.getToId());
                    continue;
                }
                if (cbPlanToDO.getToIdType() != 1) continue;
                this.roleIds.add(cbPlanToDO.getToId());
                continue;
            }
            if (cbPlanToDO.getToType() != 2) continue;
            if (cbPlanToDO.getToIdType() == 2) {
                this.ccUserIds.add(cbPlanToDO.getToId());
                continue;
            }
            if (cbPlanToDO.getToIdType() != 1) continue;
            this.ccRoleIds.add(cbPlanToDO.getToId());
        }
    }

    public List<CbPlanFormDO> toFormList() {
        if (this.formIds != null) {
            ArrayList<CbPlanFormDO> list = new ArrayList<CbPlanFormDO>();
            for (String formId : this.formIds) {
                CbPlanFormDO formDO = new CbPlanFormDO();
                formDO.setFormId(formId);
                formDO.setPlanId(this.planId);
                int type = this.workFlowType == WorkFlowType.FORM ? 1 : 2;
                formDO.setFormType(type);
                list.add(formDO);
            }
            return list;
        }
        return Collections.emptyList();
    }

    public void fromFormList(List<CbPlanFormDO> formList) {
        if (!CollectionUtils.isEmpty(formList)) {
            this.formIds = new ArrayList<String>();
            for (CbPlanFormDO formDO : formList) {
                this.formIds.add(formDO.getFormId());
            }
        }
    }

    public String getPlanId() {
        return this.planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getUserType() {
        return this.userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public Set<String> getSendChannels() {
        return this.sendChannels;
    }

    public void setSendChannels(Set<String> sendChannels) {
        this.sendChannels = sendChannels;
    }

    public String getExecUser() {
        return this.execUser;
    }

    public void setExecUser(String execUser) {
        this.execUser = execUser;
    }

    public Date getEffectiveStartTime() {
        return this.effectiveStartTime;
    }

    public void setEffectiveStartTime(Date effectiveStartTime) {
        this.effectiveStartTime = effectiveStartTime;
    }

    public Date getEffectiveEndTime() {
        return this.effectiveEndTime;
    }

    public void setEffectiveEndTime(Date effectiveEndTime) {
        this.effectiveEndTime = effectiveEndTime;
    }

    public int getKind() {
        return this.kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public String getExecUnit() {
        return this.execUnit;
    }

    public void setExecUnit(String execUnit) {
        this.execUnit = execUnit;
    }

    public int getUnitScop() {
        return this.unitScop;
    }

    public void setUnitScop(int unitScop) {
        this.unitScop = unitScop;
    }

    public int getFormScop() {
        return this.formScop;
    }

    public void setFormScop(int formScop) {
        this.formScop = formScop;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<String> getUnitIds() {
        return this.unitIds;
    }

    public void setUnitIds(List<String> unitIds) {
        this.unitIds = unitIds;
    }

    public List<String> getUserIds() {
        return this.userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public List<String> getRoleIds() {
        return this.roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public List<String> getCcUserIds() {
        return this.ccUserIds;
    }

    public void setCcUserIds(List<String> ccUserIds) {
        this.ccUserIds = ccUserIds;
    }

    public List<String> getCcRoleIds() {
        return this.ccRoleIds;
    }

    public void setCcRoleIds(List<String> ccRoleIds) {
        this.ccRoleIds = ccRoleIds;
    }

    public List<String> getFormIds() {
        return this.formIds;
    }

    public void setFormIds(List<String> formIds) {
        this.formIds = formIds;
    }

    public WorkFlowType getWorkFlowType() {
        return this.workFlowType;
    }

    public void setWorkFlowType(WorkFlowType workFlowType) {
        this.workFlowType = workFlowType;
    }

    public void setCurrPeriod(PeriodWrapper currPeriod) {
        this.currPeriod = currPeriod;
    }

    public PeriodWrapper getCurrPeriod() {
        return this.currPeriod;
    }

    public Date[] getCurrPeriodDate() {
        return this.currPeriodDate;
    }

    public void setCurrPeriodDate(Date[] currPeriodDate) {
        this.currPeriodDate = currPeriodDate;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDwDimName() {
        return this.dwDimName;
    }

    public void setDwDimName(String dwDimName) {
        this.dwDimName = dwDimName;
    }

    public List<CbPlanTimeDO> getTimes() {
        if (this.times == null) {
            return null;
        }
        for (CbPlanTimeDO time : this.times) {
            time.setPlanId(this.planId);
        }
        return this.times;
    }

    public void setTimes(List<CbPlanTimeDO> times) {
        this.times = times;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public Map<String, List<String>> getGroup2FormMap() {
        return this.group2FormMap;
    }

    public void setGroup2FormMap(Map<String, List<String>> group2FormMap) {
        this.group2FormMap = group2FormMap;
    }
}

