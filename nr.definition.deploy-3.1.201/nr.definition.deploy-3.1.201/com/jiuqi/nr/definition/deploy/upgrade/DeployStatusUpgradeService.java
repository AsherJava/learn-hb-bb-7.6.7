/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.definition.common.ParamDeployEnum$DeployStatus
 *  com.jiuqi.nr.definition.common.ParamDeployEnum$ParamStatus
 */
package com.jiuqi.nr.definition.deploy.upgrade;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.definition.common.ParamDeployEnum;
import com.jiuqi.nr.definition.deploy.entity.ParamDeployStatusDO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.BeanUtils;

public class DeployStatusUpgradeService
implements CustomClassExecutor {
    private static final String SQL_QUERY_TASK_STATUS = "SELECT TPP_TASKKEY, TPP_PUBLISHSTATUS, TPP_USERNAME, TPP_USERKEY, TPP_UPDATETIME, TPP_COMMENT, TPP_DDL_STATUS_BIT FROM NR_TASK_PLANPUBLISH ORDER BY TPP_UPDATETIME";
    private static final String SQL_QUERY_FORM_SCHEME = "SELECT FC_KEY, FC_TASK_KEY FROM NR_PARAM_FORMSCHEME";
    private static final String SQL_INSERT_DEPLOY_STATUS = "INSERT INTO NR_PARAM_DEPLOY_STATUS (SCHEME_KEY, PARAM_STATUS, DEPLOY_STATUS, DEPLOY_DETAIL, DEPLOY_TIME, LAST_DEPLOY_TIME, USER_KEY, USER_NAME, DDL_STATUS) VALUES (?,?,?,?,?,?,?,?,?)";

    public void execute(DataSource dataSource) throws Exception {
        Throwable throwable;
        ArrayList<TaskStatus> taskStatuses = new ArrayList<TaskStatus>();
        Connection connection = dataSource.getConnection();
        Object object = null;
        try (PreparedStatement statement222 = connection.prepareStatement(SQL_QUERY_TASK_STATUS);){
            connection.setAutoCommit(true);
            ResultSet throwable4 = statement222.executeQuery();
            while (throwable4.next()) {
                TaskStatus taskStatus = new TaskStatus();
                taskStatus.taskKey = throwable4.getString(1);
                taskStatus.publishStatus = throwable4.getString(2);
                taskStatus.username = throwable4.getString(3);
                taskStatus.userKey = throwable4.getString(4);
                taskStatus.updateTime = throwable4.getTimestamp(5);
                taskStatus.comment = throwable4.getString(6);
                taskStatus.ddlStatusBit = throwable4.getInt(7);
                taskStatuses.add(taskStatus);
            }
        }
        catch (Throwable statement222) {
            object = statement222;
            throw statement222;
        }
        finally {
            if (connection != null) {
                if (object != null) {
                    try {
                        connection.close();
                    }
                    catch (Throwable statement222) {
                        ((Throwable)object).addSuppressed(statement222);
                    }
                } else {
                    connection.close();
                }
            }
        }
        HashMap<String, ParamDeployStatusDO> deployStatusMap = new HashMap<String, ParamDeployStatusDO>();
        for (TaskStatus taskStatus : taskStatuses) {
            ParamDeployStatusDO status = (ParamDeployStatusDO)deployStatusMap.get(taskStatus.taskKey);
            if (null == status) {
                status = new ParamDeployStatusDO();
                status.setParamStatus(ParamDeployEnum.ParamStatus.DEFAULT);
                switch (taskStatus.publishStatus) {
                    case "publish_success": {
                        status.setDeployStatus(ParamDeployEnum.DeployStatus.SUCCESS);
                        break;
                    }
                    case "publish_fail": {
                        status.setDeployStatus(ParamDeployEnum.DeployStatus.FAIL);
                        break;
                    }
                    case "publish_warring": {
                        status.setDeployStatus(ParamDeployEnum.DeployStatus.WARNING);
                        break;
                    }
                    default: {
                        status.setDeployStatus(ParamDeployEnum.DeployStatus.SUCCESS);
                    }
                }
                status.setDeployDetail(taskStatus.comment);
                status.setUserKey(taskStatus.userKey);
                status.setUserName(taskStatus.username);
                status.setDeployTime(taskStatus.updateTime);
                status.setLastDeployTime(taskStatus.updateTime);
                deployStatusMap.put(taskStatus.taskKey, status);
                continue;
            }
            if (null != status.getLastDeployTime()) continue;
            status.setLastDeployTime(taskStatus.updateTime);
        }
        ArrayList<ParamDeployStatusDO> deployStatus = new ArrayList<ParamDeployStatusDO>();
        HashMap<String, String> formSchemes = new HashMap<String, String>();
        Throwable throwable2 = null;
        try (Connection connection2 = dataSource.getConnection();){
            throwable = null;
            try (PreparedStatement statement3 = connection2.prepareStatement(SQL_QUERY_FORM_SCHEME);){
                connection2.setAutoCommit(true);
                ResultSet resultSet2 = statement3.executeQuery();
                while (resultSet2.next()) {
                    formSchemes.put(resultSet2.getString(1), resultSet2.getString(2));
                }
            }
            catch (Throwable throwable3) {
                throwable = throwable3;
                throw throwable3;
            }
        }
        catch (Throwable statement3) {
            Throwable throwable4 = statement3;
            throw statement3;
        }
        for (Map.Entry entry : formSchemes.entrySet()) {
            ParamDeployStatusDO status = (ParamDeployStatusDO)deployStatusMap.get(entry.getValue());
            if (null == status) {
                status = new ParamDeployStatusDO();
                status.setParamStatus(ParamDeployEnum.ParamStatus.DEFAULT);
                status.setDeployStatus(ParamDeployEnum.DeployStatus.SUCCESS);
            } else {
                status = DeployStatusUpgradeService.copy(status);
            }
            status.setSchemeKey((String)entry.getKey());
            deployStatus.add(status);
        }
        Throwable throwable5 = null;
        try (Connection connection2 = dataSource.getConnection();){
            throwable = null;
            try (PreparedStatement statement = connection2.prepareStatement(SQL_INSERT_DEPLOY_STATUS);){
                connection2.setAutoCommit(false);
                for (ParamDeployStatusDO status : deployStatus) {
                    statement.setString(1, status.getSchemeKey());
                    statement.setInt(2, status.getParamStatus().getValue());
                    statement.setInt(3, status.getDeployStatus().getValue());
                    statement.setString(4, status.getDeployDetail());
                    Date time = status.getDeployTime();
                    statement.setTimestamp(5, Timestamp.from(null == time ? Instant.now() : time.toInstant()));
                    time = status.getLastDeployTime();
                    statement.setTimestamp(6, Timestamp.from(null == time ? Instant.now() : time.toInstant()));
                    statement.setString(7, status.getUserKey());
                    statement.setString(8, status.getUserName());
                    statement.setInt(9, status.getDdlStatus());
                    statement.addBatch();
                }
                statement.executeBatch();
                connection2.commit();
                connection2.setAutoCommit(true);
            }
            catch (Throwable throwable6) {
                throwable = throwable6;
                throw throwable6;
            }
        }
        catch (Throwable throwable7) {
            Throwable throwable8 = throwable7;
            throw throwable7;
        }
    }

    private static ParamDeployStatusDO copy(ParamDeployStatusDO old) {
        ParamDeployStatusDO copy = new ParamDeployStatusDO();
        BeanUtils.copyProperties(old, copy);
        return copy;
    }

    public static class TaskStatus {
        String taskKey;
        String publishStatus;
        String username;
        String userKey;
        Date updateTime;
        String comment;
        int ddlStatusBit;
    }
}

