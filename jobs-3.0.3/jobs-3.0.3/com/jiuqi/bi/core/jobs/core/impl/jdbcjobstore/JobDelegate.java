/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.quartz.JobPersistenceException
 *  org.quartz.TriggerKey
 *  org.quartz.impl.jdbcjobstore.StdJDBCDelegate
 *  org.quartz.spi.OperableTrigger
 */
package com.jiuqi.bi.core.jobs.core.impl.jdbcjobstore;

import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobFactoryManager;
import com.jiuqi.bi.core.jobs.bean.JobInstanceBean;
import com.jiuqi.bi.core.jobs.combination.CombinationExtendStageConfig;
import com.jiuqi.bi.core.jobs.combination.CombinationJobTools;
import com.jiuqi.bi.core.jobs.core.impl.jdbcjobstore.CompatibleInputStream;
import com.jiuqi.bi.core.jobs.extension.JobDispatchControlManager;
import com.jiuqi.bi.core.jobs.manager.ConfigManager;
import com.jiuqi.bi.core.jobs.manager.JobOperationManager;
import com.jiuqi.bi.core.jobs.manager.JobStorageManager;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.core.jobs.realtime.core.RealTimeJobFactory;
import com.jiuqi.bi.core.jobs.simpleschedule.SimpleJobAnnotationHolder;
import com.jiuqi.bi.util.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.quartz.JobPersistenceException;
import org.quartz.TriggerKey;
import org.quartz.impl.jdbcjobstore.StdJDBCDelegate;
import org.quartz.spi.OperableTrigger;

public class JobDelegate
extends StdJDBCDelegate {
    private JobOperationManager jobOperationManager = new JobOperationManager();
    private JobStorageManager jobStorageManager = new JobStorageManager();
    private static final String SELECT_NEXT_TRIGGER_TO_ACQUIRE = "SELECT TRIGGER_NAME, TRIGGER_GROUP, NEXT_FIRE_TIME, PRIORITY, JOB_NAME FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_STATE = ? AND NEXT_FIRE_TIME <= ? AND (MISFIRE_INSTR = -1 OR (MISFIRE_INSTR != -1 AND NEXT_FIRE_TIME >= ?)) ORDER BY NEXT_FIRE_TIME ASC, PRIORITY DESC";
    private static final String SELECT_FIRED_TRIGGERS = "SELECT COUNT(*) FROM {0}FIRED_TRIGGERS WHERE SCHED_NAME = ? AND JOB_GROUP = ? ";
    private static final String SELECT_TRIGGER_STATE_FROM_STATE = "SELECT COUNT(*) FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ? AND TRIGGER_STATE = ?";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<TriggerKey> selectTriggerToAcquire(Connection conn, long noLaterThan, long noEarlierThan, int maxCount) throws SQLException {
        LinkedList<TriggerKey> linkedList;
        PreparedStatement ps = null;
        ResultSet rs = null;
        LinkedList<TriggerKey> nextTriggers = new LinkedList<TriggerKey>();
        try {
            ps = conn.prepareStatement(this.rtp(SELECT_NEXT_TRIGGER_TO_ACQUIRE));
            if (maxCount < 1) {
                maxCount = 1;
            }
            ps.setMaxRows(maxCount);
            ps.setFetchSize(maxCount);
            ps.setString(1, "WAITING");
            ps.setBigDecimal(2, new BigDecimal(String.valueOf(noLaterThan)));
            ps.setBigDecimal(3, new BigDecimal(String.valueOf(noEarlierThan)));
            rs = ps.executeQuery();
            ConfigManager configManager = ConfigManager.getInstance();
            List<String> unexecutableJobGuid = configManager.getUnexecutableJobGuid();
            String jobDispatchType = configManager.getJobDispatchType();
            String jobMatchType = configManager.getJobMatchType();
            JobDispatchControlManager jobDispatchControlManager = new JobDispatchControlManager();
            jobDispatchControlManager.load(jobMatchType, jobDispatchType);
            HashMap<String, Integer> jobExcuteCountMap = new HashMap<String, Integer>();
            block4: while (rs.next() && nextTriggers.size() <= maxCount) {
                String triggerName = rs.getString("TRIGGER_NAME");
                String triggerGroup = rs.getString("TRIGGER_GROUP");
                String jobName = rs.getString("JOB_NAME");
                String checkGroup = triggerGroup;
                if (triggerGroup.startsWith("combination_job")) {
                    try {
                        int stage;
                        int i = triggerGroup.lastIndexOf("_");
                        String insId = triggerGroup.substring(i + 1);
                        JobInstanceBean jobInstanceBean = this.jobOperationManager.getInstanceById(insId);
                        if (jobInstanceBean != null && (stage = jobInstanceBean.getStage()) > 0) {
                            JobModel job = this.jobStorageManager.getJob(jobInstanceBean.getJobId());
                            List<CombinationExtendStageConfig> stageConfigs = CombinationJobTools.getStageConfigs(job.getExtendedConfig());
                            CombinationExtendStageConfig combinationExtendStageConfig = stageConfigs.get(stage - 1);
                            checkGroup = combinationExtendStageConfig.getType();
                        }
                    }
                    catch (Exception e) {
                        this.logger.error(e.getMessage(), e);
                    }
                }
                if (triggerGroup.startsWith("com.jiuqi.bi.jobs.simpleschedule") && triggerName.startsWith("ANNO_") && !SimpleJobAnnotationHolder.getInstance().exist(triggerName)) continue;
                if (checkGroup.startsWith("<sub>")) {
                    checkGroup = checkGroup.substring("<sub>".length());
                }
                for (String un : unexecutableJobGuid) {
                    if (!jobName.equals(un)) continue;
                    continue block4;
                }
                if (!triggerGroup.startsWith("com.jiuqi.bi.jobs.simpleschedule")) {
                    if ("BY_TAG".equals(jobDispatchType)) {
                        ArrayList<String> tags = new ArrayList<String>();
                        if (checkGroup.startsWith("com.jiuqi.bi.jobs.realtime")) {
                            boolean taskTypeAllowed;
                            checkGroup = checkGroup.substring("com.jiuqi.bi.jobs.realtime".length() + 1);
                            checkGroup = checkGroup.replace("." + triggerName, "");
                            String[] vales = RealTimeJobFactory.getInstance().getRealTimeJobTags(checkGroup);
                            if (vales == null) continue;
                            if (vales.length == 0) {
                                tags.add(checkGroup);
                            } else {
                                tags.addAll(Arrays.asList(vales));
                            }
                            if (!(taskTypeAllowed = jobDispatchControlManager.isAllowed(tags)) || !this.jobOperationManager.allowRealtimeRunning(checkGroup, jobExcuteCountMap.getOrDefault(checkGroup, 0), this.rtp(SELECT_FIRED_TRIGGERS), this.schedName)) {
                                continue;
                            }
                        } else {
                            checkGroup = checkGroup.contains(triggerName) ? checkGroup.replace(triggerName, "") : checkGroup.replace("." + jobName, "");
                            JobFactory jobFactory = JobFactoryManager.getInstance().getJobFactory(checkGroup);
                            if (jobFactory == null) continue;
                            String[] vales = jobFactory.getTags();
                            if (vales.length == 0) {
                                tags.add(checkGroup);
                            } else {
                                tags.addAll(Arrays.asList(vales));
                            }
                            boolean taskTypeAllowed = jobDispatchControlManager.isAllowed(tags);
                            if (!taskTypeAllowed) {
                                continue;
                            }
                        }
                    } else {
                        JobFactory jobFactory;
                        if (!jobDispatchControlManager.isAllowed(checkGroup)) continue;
                        if (checkGroup.startsWith("com.jiuqi.bi.jobs.realtime")) {
                            checkGroup = checkGroup.substring("com.jiuqi.bi.jobs.realtime".length() + 1);
                            if (!this.jobOperationManager.allowRealtimeRunning(checkGroup = checkGroup.replace("." + triggerName, ""), jobExcuteCountMap.getOrDefault(checkGroup, 0), this.rtp(SELECT_FIRED_TRIGGERS), this.schedName)) {
                                continue;
                            }
                        } else if (checkGroup.contains(triggerName) && null == (jobFactory = JobFactoryManager.getInstance().getJobFactory(checkGroup.replace(triggerName, "")))) continue;
                    }
                }
                nextTriggers.add(TriggerKey.triggerKey((String)triggerName, (String)triggerGroup));
                jobExcuteCountMap.put(checkGroup, jobExcuteCountMap.getOrDefault(checkGroup, 0) + 1);
            }
            linkedList = nextTriggers;
        }
        catch (Throwable throwable) {
            JobDelegate.closeResultSet(rs);
            JobDelegate.closeStatement((Statement)ps);
            throw throwable;
        }
        JobDelegate.closeResultSet((ResultSet)rs);
        JobDelegate.closeStatement((Statement)ps);
        return linkedList;
    }

    public OperableTrigger selectTrigger(Connection conn, TriggerKey triggerKey) throws SQLException, ClassNotFoundException, IOException, JobPersistenceException {
        OperableTrigger trigger = super.selectTrigger(conn, triggerKey);
        if (StringUtils.isEmpty((String)trigger.getCalendarName())) {
            trigger.setCalendarName(null);
        }
        return trigger;
    }

    protected Object getObjectFromBlob(ResultSet rs, String colName) throws ClassNotFoundException, IOException, SQLException {
        Object obj = null;
        byte[] bytes = rs.getBytes(colName);
        if (null != bytes && bytes.length > 0) {
            ByteArrayInputStream bytesInputStream = new ByteArrayInputStream(bytes);
            try (CompatibleInputStream in = new CompatibleInputStream(bytesInputStream);){
                obj = in.readObject();
            }
        }
        return obj;
    }

    protected Object getJobDataFromBlob(ResultSet rs, String colName) throws ClassNotFoundException, IOException, SQLException {
        if (this.canUseProperties()) {
            byte[] bytes = rs.getBytes(colName);
            if (null != bytes && bytes.length > 0) {
                ByteArrayInputStream bytesInputStream = new ByteArrayInputStream(bytes);
                return bytesInputStream;
            }
            return null;
        }
        return this.getObjectFromBlob(rs, colName);
    }

    protected void setBoolean(PreparedStatement ps, int index, boolean val) throws SQLException {
        ps.setString(index, val ? "1" : "0");
    }

    protected boolean getBoolean(ResultSet rs, String columnName) throws SQLException {
        boolean result = false;
        String value = rs.getString(columnName);
        if ("1".equals(value)) {
            result = true;
        }
        return result;
    }

    protected boolean getBoolean(ResultSet rs, int columnIndex) throws SQLException {
        boolean result = false;
        String value = rs.getString(columnIndex);
        if ("1".equals(value)) {
            result = true;
        }
        return result;
    }

    protected void setBytes(PreparedStatement ps, int index, ByteArrayOutputStream baos) throws SQLException {
        ps.setBytes(index, baos == null ? null : baos.toByteArray());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int selectTriggerStateFromOtherState(Connection conn, TriggerKey triggerKey, String oldState) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp(SELECT_TRIGGER_STATE_FROM_STATE));
            ps.setString(1, triggerKey.getName());
            ps.setString(2, triggerKey.getGroup());
            ps.setString(3, oldState);
            int count = 0;
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            n = count;
        }
        catch (Throwable throwable) {
            JobDelegate.closeStatement(ps);
            throw throwable;
        }
        JobDelegate.closeStatement((Statement)ps);
        return n;
    }
}

