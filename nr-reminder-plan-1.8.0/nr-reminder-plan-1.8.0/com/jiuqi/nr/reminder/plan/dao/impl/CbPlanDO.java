/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.message.domain.VaMessageOption$MsgChannel
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.reminder.plan.dao.impl;

import com.jiuqi.va.message.domain.VaMessageOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

public class CbPlanDO
implements RowMapper<CbPlanDO> {
    private String id;
    private String title;
    protected String source;
    private int enabled;
    private int userType;
    private String sendChannel;
    private String execUser;
    private Timestamp effectiveStartTime;
    private Timestamp effectiveEndTime;
    private int kind;
    private String content;
    private String taskId;
    private String formSchemeId;
    private String execUnit;
    private String execPeriod;
    private int unitScop;
    private int formScop;
    private String createUser;
    private String updateUser;
    private Timestamp createTime;
    private Timestamp updateTime;
    private int workFlowType;
    private String dw;
    public static final String NR_CB_PLAN = "NR_CB_PLAN";
    public static final String ID_COL = "P_ID";
    public static final String TITLE_COL = "P_TITLE";
    public static final String SOURCE_COL = "P_SOURCE";
    public static final String ENABLED_COL = "P_ENABLED";
    public static final String USER_TYPE_COL = "P_USER_TYPE";
    public static final String SEND_CHANNEL_COL = "P_SEND_CHANNEL";
    public static final String EXEC_USER_COL = "P_EXEC_USER";
    public static final String EFFECTIVE_START_TIME_COL = "P_EFFECTIVE_START_TIME";
    public static final String EFFECTIVE_END_TIME_COL = "P_EFFECTIVE_END_TIME";
    public static final String KIND_COL = "P_KIND";
    public static final String CONTENT_COL = "P_CONTENT";
    public static final String TASK_ID_COL = "P_TASK_ID";
    public static final String FORMSCHEME_ID_COL = "P_FORMSCHEME_ID";
    public static final String WORKFLOW_TYPE_COL = "P_WORKFLOW_TYPE";
    public static final String EXEC_UNIT_COL = "P_EXEC_UNIT";
    public static final String EXEC_PERIOD_COL = "P_EXEC_PERIOD";
    public static final String UNIT_SCOP_COL = "P_UNIT_SCOP";
    public static final String FORM_SCOP_COL = "P_FORM_SCOP";
    public static final String CREATE_USER_COL = "P_CREATE_USER";
    public static final String UPDATE_USER_COL = "P_UPDATE_USER";
    public static final String CREATE_TIME_COL = "P_CREATE_TIME";
    public static final String UPDATE_TIME_COL = "P_UPDATE_TIME";
    public static final String DW_COL = "P_DW";
    public static final List<String> SELECT_COLUMNS = new ArrayList<String>(){
        {
            this.add(CbPlanDO.ID_COL);
            this.add(CbPlanDO.TITLE_COL);
            this.add(CbPlanDO.SOURCE_COL);
            this.add(CbPlanDO.ENABLED_COL);
            this.add(CbPlanDO.USER_TYPE_COL);
            this.add(CbPlanDO.SEND_CHANNEL_COL);
            this.add(CbPlanDO.EXEC_USER_COL);
            this.add(CbPlanDO.EFFECTIVE_START_TIME_COL);
            this.add(CbPlanDO.EFFECTIVE_END_TIME_COL);
            this.add(CbPlanDO.KIND_COL);
            this.add(CbPlanDO.CONTENT_COL);
            this.add(CbPlanDO.TASK_ID_COL);
            this.add(CbPlanDO.FORMSCHEME_ID_COL);
            this.add(CbPlanDO.WORKFLOW_TYPE_COL);
            this.add(CbPlanDO.EXEC_UNIT_COL);
            this.add(CbPlanDO.EXEC_PERIOD_COL);
            this.add(CbPlanDO.UNIT_SCOP_COL);
            this.add(CbPlanDO.FORM_SCOP_COL);
            this.add(CbPlanDO.CREATE_USER_COL);
            this.add(CbPlanDO.UPDATE_USER_COL);
            this.add(CbPlanDO.CREATE_TIME_COL);
            this.add(CbPlanDO.UPDATE_TIME_COL);
            this.add(CbPlanDO.DW_COL);
        }
    };

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEnabled() {
        return this.enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public int getUserType() {
        return this.userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getSendChannel() {
        return this.sendChannel;
    }

    public void setSendChannel(String sendChannel) {
        this.sendChannel = sendChannel;
    }

    public String getExecUser() {
        return this.execUser;
    }

    public void setExecUser(String execUser) {
        this.execUser = execUser;
    }

    public Timestamp getEffectiveStartTime() {
        return this.effectiveStartTime;
    }

    public void setEffectiveStartTime(Timestamp effectiveStartTime) {
        this.effectiveStartTime = effectiveStartTime;
    }

    public Timestamp getEffectiveEndTime() {
        return this.effectiveEndTime;
    }

    public void setEffectiveEndTime(Timestamp effectiveEndTime) {
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

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public int getWorkFlowType() {
        return this.workFlowType;
    }

    public void setWorkFlowType(int value) {
        this.workFlowType = value;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getExecPeriod() {
        return this.execPeriod;
    }

    public void setExecPeriod(String execPeriod) {
        this.execPeriod = execPeriod;
    }

    public String getDw() {
        return this.dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public CbPlanDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        CbPlanDO cbPlanDO = new CbPlanDO();
        cbPlanDO.setId(rs.getString(ID_COL));
        cbPlanDO.setTitle(rs.getString(TITLE_COL));
        cbPlanDO.setSource(rs.getString(SOURCE_COL));
        cbPlanDO.setEnabled(rs.getInt(ENABLED_COL));
        cbPlanDO.setUserType(rs.getInt(USER_TYPE_COL));
        String channel = rs.getString(SEND_CHANNEL_COL);
        if (StringUtils.hasLength(channel)) {
            StringBuilder sb = new StringBuilder();
            for (String item : channel.split(";")) {
                if ("1".equals(item)) {
                    item = VaMessageOption.MsgChannel.PC.toString();
                    sb.append(item).append(";");
                    continue;
                }
                if ("2".equals(item)) {
                    item = VaMessageOption.MsgChannel.EMAIL.toString();
                    sb.append(item).append(";");
                    continue;
                }
                if ("3".equals(item)) {
                    item = VaMessageOption.MsgChannel.SMS.toString();
                    sb.append(item).append(";");
                    continue;
                }
                sb.append(item).append(";");
            }
            if (sb.length() > 0) {
                sb.setLength(sb.length() - 1);
            }
            cbPlanDO.setSendChannel(sb.toString());
        }
        cbPlanDO.setExecUser(rs.getString(EXEC_USER_COL));
        cbPlanDO.setEffectiveStartTime(rs.getTimestamp(EFFECTIVE_START_TIME_COL));
        cbPlanDO.setEffectiveEndTime(rs.getTimestamp(EFFECTIVE_END_TIME_COL));
        cbPlanDO.setKind(rs.getInt(KIND_COL));
        cbPlanDO.setContent(rs.getString(CONTENT_COL));
        cbPlanDO.setTaskId(rs.getString(TASK_ID_COL));
        cbPlanDO.setFormSchemeId(rs.getString(FORMSCHEME_ID_COL));
        cbPlanDO.setWorkFlowType(rs.getInt(WORKFLOW_TYPE_COL));
        cbPlanDO.setExecUnit(rs.getString(EXEC_UNIT_COL));
        cbPlanDO.setExecPeriod(rs.getString(EXEC_PERIOD_COL));
        cbPlanDO.setUnitScop(rs.getInt(UNIT_SCOP_COL));
        cbPlanDO.setFormScop(rs.getInt(FORM_SCOP_COL));
        cbPlanDO.setCreateUser(rs.getString(CREATE_USER_COL));
        cbPlanDO.setUpdateUser(rs.getString(UPDATE_USER_COL));
        cbPlanDO.setCreateTime(rs.getTimestamp(CREATE_TIME_COL));
        cbPlanDO.setUpdateTime(rs.getTimestamp(UPDATE_TIME_COL));
        cbPlanDO.setDw(rs.getString(DW_COL));
        return cbPlanDO;
    }
}

