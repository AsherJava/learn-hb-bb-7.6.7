/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DBException
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.bi.database.paging.OrderField
 *  com.jiuqi.bi.database.paging.UnsupportPagingException
 *  com.jiuqi.bi.util.Html
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.core.jobs.dao;

import com.jiuqi.bi.core.jobs.JobFactoryManager;
import com.jiuqi.bi.core.jobs.base.BaseJobFactoryManager;
import com.jiuqi.bi.core.jobs.bean.JobInstanceBean;
import com.jiuqi.bi.core.jobs.monitor.JobGroupByInfo;
import com.jiuqi.bi.core.jobs.monitor.JobType;
import com.jiuqi.bi.core.jobs.monitor.State;
import com.jiuqi.bi.core.jobs.realtime.core.RealTimeJobFactory;
import com.jiuqi.bi.database.DBException;
import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.bi.database.paging.OrderField;
import com.jiuqi.bi.database.paging.UnsupportPagingException;
import com.jiuqi.bi.util.Html;
import com.jiuqi.bi.util.StringUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobInstancesDAO {
    private static final Logger logger = LoggerFactory.getLogger(JobInstancesDAO.class);
    public static final String TABLE_NAME = "BI_JOBS_INSTANCES";
    public static final String FIELD_INSTANCEID = "BJI_INSTANCEID";
    private static final String FIELD_PARENT_INSTANCEID = "BJI_PARENT_INSTID";
    private static final String FIELD_ROOT_INSTANCEID = "BJI_ROOT_INSTID";
    private static final String FIELD_LEVEL = "BJI_LEVEL";
    private static final String FIELD_PROGRESS = "BJI_PROGRESS";
    private static final String FIELD_PROMPT = "BJI_PROMPT";
    private static final String FIELD_STATE = "BJI_STATE";
    private static final String FIELD_JOBTYPE = "BJI_JOBTYPE";
    private static final String FIELD_USERGUID = "BJI_USERGUID";
    private static final String FIELD_USERNAME = "BJI_USERNAME";
    private static final String FIELD_STARTTIME = "BJI_STARTTIME";
    private static final String FIELD_ENDTIME = "BJI_ENDTIME";
    private static final String FIELD_JOBID = "BJI_JOBGUID";
    private static final String FIELD_RESULT = "BJI_RESULT";
    private static final String FIELD_RESULTMESSAGE = "BJI_RESULTMSG";
    private static final String FIELD_NODE = "BJI_NODE";
    private static final String FIELD_INSTITLE = "BJI_INSTITLE";
    private static final String FIELD_QUARTZ_INSTANCE = "BJI_QUARTZ_INSTANCE";
    private static final String FIELD_CATEGORY_ID = "BJI_CATEGORY_ID";
    private static final String FIELD_CATEGORY_TITLE = "BJI_CATEGORY_TITLE";
    private static final String FIELD_BACKSTAGE = "BJI_BACKSTAGE";
    private static final String FIELD_EXECUTE_STAGE = "BJI_EXECUTE_STAGE";
    private static final String FIELD_QUERY_FIELD1 = "BJI_QUERY_FIELD1";
    private static final String FIELD_QUERY_FIELD2 = "BJI_QUERY_FIELD2";
    private static final String FIELD_PUBLISH_NODE = "BJI_PUBLISH_NODE";
    private static final String FIELD_EXEC_START_TIME = "BJI_EXEC_START_TIME";
    private static final String SQL_SELECT_BASE = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES ";
    private static final String SQL_INSERT_INSTANCE = "INSERT INTO BI_JOBS_INSTANCES(BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_INSTANCE_TITLE = "UPDATE BI_JOBS_INSTANCES SET BJI_INSTITLE = ? WHERE BJI_INSTANCEID = ?";
    private static final String SQL_UPDATE_INSTANCE_STAGE = "UPDATE BI_JOBS_INSTANCES SET BJI_EXECUTE_STAGE = ? WHERE BJI_INSTANCEID = ?";
    private static final String SQL_UPDATE_INSTANCE_PROGRESS_AND_PROMPT = "UPDATE BI_JOBS_INSTANCES SET BJI_PROGRESS = ?, BJI_PROMPT = ? WHERE BJI_INSTANCEID = ?";
    private static final String SQL_UPDATE_INSTANCE_PROGRESS = "UPDATE BI_JOBS_INSTANCES SET BJI_PROGRESS = ? WHERE BJI_INSTANCEID = ?";
    private static final String SQL_UPDATE_INSTANCE_BACKSTAGE = "UPDATE BI_JOBS_INSTANCES SET BJI_BACKSTAGE = ? WHERE BJI_INSTANCEID = ?";
    private static final String SQL_UPDATE_INSTANCE_STATE = "UPDATE BI_JOBS_INSTANCES SET BJI_STATE = ?, BJI_RESULT = ?, BJI_RESULTMSG = ?, BJI_ENDTIME = ? WHERE BJI_INSTANCEID = ?";
    private static final String SQL_UPDATE_INSTANCE_STATE_AND_NODE = "UPDATE BI_JOBS_INSTANCES SET BJI_STATE = ?, BJI_NODE = ?, BJI_QUARTZ_INSTANCE = ? WHERE BJI_INSTANCEID = ?";
    private static final String SQL_UPDATE_INSTANCE_STATE_AND_NODE_AND_EXEC_TIME = "UPDATE BI_JOBS_INSTANCES SET BJI_STATE = ?, BJI_NODE = ?, BJI_QUARTZ_INSTANCE = ?, BJI_EXEC_START_TIME = ? WHERE BJI_INSTANCEID = ?";
    private static final String SQL_SET_UNFINISHED_INSTANCE_TO_TERMINATED_BASE = "UPDATE BI_JOBS_INSTANCES SET BJI_STATE = " + State.FINISHED.getValue() + ", " + "BJI_RESULT" + " = " + 3 + ", " + "BJI_ENDTIME" + " = ? , " + "BJI_RESULTMSG" + " = '\u7cfb\u7edf\u5f02\u5e38\u7ec8\u6b62' WHERE 1=1 ";
    private static final String SQL_SET_UNFINISHED_INSTANCE_TO_TERMINATED = SQL_SET_UNFINISHED_INSTANCE_TO_TERMINATED_BASE + " AND ( " + "BJI_STATE" + " < " + State.FINISHED.getValue() + " OR " + "BJI_STATE" + " > " + State.FINISHED.getValue() + " )";
    private static final String SQL_SET_UNFINISHED_INSTANCE_TO_TERMINATED_BY_NODE = SQL_SET_UNFINISHED_INSTANCE_TO_TERMINATED_BASE + " AND ( " + "BJI_STATE" + " < " + State.FINISHED.getValue() + " OR " + "BJI_STATE" + " > " + State.FINISHED.getValue() + " ) AND " + "BJI_NODE" + " = ?";
    private static final String SQL_SET_UNFINISHED_INSTANCE_TO_TERMINATED_BY_IDS = SQL_SET_UNFINISHED_INSTANCE_TO_TERMINATED_BASE + " AND " + "BJI_INSTANCEID" + " IN (?INSTANCE_IDS) ";
    private static final String SQL_GET_UNFINISHED_INSTANCES = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES  WHERE  ( BJI_STATE < " + State.FINISHED.getValue() + " OR " + "BJI_STATE" + " > " + State.FINISHED.getValue() + " ) AND " + "BJI_NODE" + " = ?";
    private static final String SQL_GET_UNFINISHED_INSTANCES_ALL_NODE = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES  WHERE ( BJI_STATE < " + State.FINISHED.getValue() + " OR " + "BJI_STATE" + " > " + State.FINISHED.getValue() + " )";
    private static final String SQL_CANCEL_INSTANCE = "UPDATE BI_JOBS_INSTANCES SET BJI_STATE = " + State.CANCELING.getValue() + " WHERE " + "BJI_INSTANCEID" + " = ? AND " + "BJI_STATE" + " in (" + State.RUNNING.getValue() + ", " + State.WAITING.getValue() + ")";
    private static final String SQL_SELECT_JOIN_BY_FOLDER = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_CATEGORY_TITLE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME, BJ_FOLDER, BJ_JOBCATEGORY FROM BI_JOBS_INSTANCES LEFT JOIN BI_JOBS ON BJI_JOBGUID = BJ_JOBGUID";
    private static final String SQL_CLUSTER_ORDERBY_STARTTIME = " ORDER BY BJI_STARTTIME DESC";
    private static final String SQL_QUERY_INSTANCE = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES WHERE BJI_JOBGUID = ? AND BJI_PARENT_INSTID IS NULL";
    private static final String SQL_QUERY_INSTANCE_WITH_ORDER_BY = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES WHERE BJI_JOBGUID = ? AND BJI_PARENT_INSTID IS NULL ORDER BY BJI_STARTTIME DESC";
    private static final String SQL_SELECT_SIMPLE_BASE = "SELECT BJI_INSTANCEID, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_STARTTIME, BJI_RESULT FROM BI_JOBS_INSTANCES ";
    private static final String SQL_QUERY_INSTANCE_BY_GUID = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES WHERE BJI_INSTANCEID = ?";
    private static final String SQL_QUERY_INSTANCE_SIMPLE_BY_GUID = "SELECT BJI_INSTANCEID, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_STARTTIME, BJI_RESULT FROM BI_JOBS_INSTANCES WHERE BJI_INSTANCEID = ?";
    private static final String SQL_QUERY_INSTANCE_BY_GUIDS = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES WHERE BJI_INSTANCEID IN ( ";
    private static final String SQL_QUERY_INSTANCE_SIMPLE_BY_GUIDS = "SELECT BJI_INSTANCEID, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_STARTTIME, BJI_RESULT FROM BI_JOBS_INSTANCES WHERE BJI_INSTANCEID IN ( ";
    private static final String SQL_QUERY_SUBINSTANCE = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES  WHERE BJI_PARENT_INSTID = ?";
    private static final String SQL_QUERY_SUBINSTANCE_FINISHED = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES  WHERE BJI_PARENT_INSTID = ? AND BJI_STATE = " + State.FINISHED.getValue();
    private static final String SQL_QUERY_SUBINSTANCE_UNFINISHED = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES  WHERE BJI_PARENT_INSTID = ? AND ( BJI_STATE < " + State.FINISHED.getValue() + " OR " + "BJI_STATE" + " > " + State.FINISHED.getValue() + " ) ";
    private static final String SQL_QUERY_INSTANCE_COUNT_BY_CONDITION = "SELECT COUNT(*)  FROM BI_JOBS_INSTANCES T1  LEFT JOIN BI_JOBS T2  ON T1.BJI_JOBGUID = T2.BJ_JOBGUID  WHERE T1.BJI_PARENT_INSTID IS NULL ";
    private static final String SQL_QUERY_PARENTINSTANCE = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES  WHERE BJI_INSTANCEID = ?";
    private static final String SQL_QUERY_INSTANCES_BY_CONDITION = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME,  T2.BJ_JOBCATEGORY BJ_JOBCATEGORY , (CASE BJI_STATE WHEN 0 THEN 1 WHEN -1 THEN 1 WHEN -2 THEN 1 ELSE 0 END) AS STATE_ORDER,  (CASE BJI_STATE WHEN 0 THEN BJI_STARTTIME WHEN -1 THEN BJI_STARTTIME WHEN -2 THEN BJI_STARTTIME ELSE BJI_ENDTIME END) AS STATE_ENDTIME FROM BI_JOBS_INSTANCES  T1  LEFT JOIN BI_JOBS T2  ON T1.BJI_JOBGUID = T2.BJ_JOBGUID  WHERE T1.BJI_PARENT_INSTID IS NULL ";
    private static final String SQL_QUERY_REALTIME_INSTANCES_BY_CONDITION = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME,  (CASE BJI_STATE WHEN 0 THEN 1 WHEN -1 THEN 1 WHEN -2 THEN 1 ELSE 0 END) AS STATE_ORDER,  (CASE BJI_STATE WHEN 0 THEN BJI_STARTTIME WHEN -1 THEN BJI_STARTTIME WHEN -2 THEN BJI_STARTTIME ELSE BJI_ENDTIME END) AS STATE_ENDTIME FROM BI_JOBS_INSTANCES  T1  WHERE T1.BJI_PARENT_INSTID IS NULL ";
    private static long UNFINISH_ENDTIME = 9999999999999L;
    private static final String SQL_QUERY_INSTANCES_BY_INSTANCE_IDS = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES  WHERE BJI_INSTANCEID IN ( ?IDS ) ";
    private static final String SQL_QUERY_ALL_SUBINSTANCE = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES  WHERE BJI_ROOT_INSTID = ? ";
    private static final String SQL_QUERY_SUBINSTANCE_BY_ROOT = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES  WHERE BJI_ROOT_INSTID IN ( ?IDS ) ";
    private static final String SQL_QUERY_SUBINSTANCE_BY_PARENT = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES  WHERE BJI_PARENT_INSTID IN ( ?IDS ) ";
    private static final String SQL_DELETE_BY_GUID = " DELETE FROM BI_JOBS_INSTANCES WHERE BJI_INSTANCEID IN ( ?IDS ) ";
    private static final String SQL_QUERY_EXPIRE_INSTANCE_COUNT = " SELECT COUNT(*) FROM BI_JOBS_INSTANCES WHERE BJI_JOBTYPE = ?  AND BJI_ENDTIME < ? AND BJI_ENDTIME > 0  AND ?USERCONDITION ";
    private static final String SQL_QUERY_EXPIRE_INSTANCE = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES  WHERE BJI_JOBTYPE = ?  AND BJI_ENDTIME < ? AND BJI_ENDTIME > 0  AND BJI_USERGUID = ?  ORDER BY BJI_STARTTIME";
    private static final String SQL_QUERY_EXPIRE_INSTANCE_NO_USER = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES  WHERE BJI_JOBTYPE = ?  AND BJI_ENDTIME < ? AND BJI_ENDTIME > 0  ORDER BY BJI_STARTTIME";
    private static final String SQL_QUERY_EXPIRE_SCHEDULE_INSTANCE_COUNT = " SELECT COUNT(*) FROM BI_JOBS_INSTANCES WHERE BJI_JOBTYPE = ?  AND BJI_ENDTIME < ? AND BJI_ENDTIME > 0  AND BJI_USERGUID = ?  AND BJI_CATEGORY_ID IN ( ";
    private static final String SQL_QUERY_EXPIRE_SCHEDULE_INSTANCE_COUNT_NO_USER = " SELECT COUNT(*) FROM BI_JOBS_INSTANCES WHERE BJI_JOBTYPE = ?  AND BJI_ENDTIME < ? AND BJI_ENDTIME > 0  AND BJI_CATEGORY_ID IN ( ";
    private static final String SQL_QUERY_EXPIRE_SCHEDULE_INSTANCE = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES  WHERE BJI_JOBTYPE = ?  AND BJI_ENDTIME < ? AND BJI_ENDTIME > 0  AND BJI_USERGUID = ?  AND BJI_CATEGORY_ID IN (?CATEGORIES)  AND BJI_JOBGUID IN (SELECT BJ_JOBGUID FROM BI_JOBS GROUP BY BJ_JOBGUID)  ORDER BY BJI_STARTTIME";
    private static final String SQL_QUERY_EXPIRE_SCHEDULE_INSTANCE_NO_USER = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES  WHERE BJI_JOBTYPE = ?  AND BJI_ENDTIME < ? AND BJI_ENDTIME > 0  AND BJI_CATEGORY_ID IN (?CATEGORIES)  AND BJI_JOBGUID IN (SELECT BJ_JOBGUID FROM BI_JOBS GROUP BY BJ_JOBGUID)  ORDER BY BJI_STARTTIME";
    private static final String SQL_QUERY_EXPIRE_SIMPLE_INSTANCE_COUNT = " SELECT COUNT(*) FROM BI_JOBS_INSTANCES WHERE BJI_ENDTIME < ? AND BJI_ENDTIME > 0  AND BJI_USERGUID = ?  AND BJI_JOBGUID IN (SELECT BSJ_GUID FROM BI_SIMPLE_JOBS GROUP BY BSJ_GUID) ";
    private static final String SQL_QUERY_EXPIRE_SIMPLE_INSTANCE_COUNT_NO_USER = " SELECT COUNT(*) FROM BI_JOBS_INSTANCES WHERE BJI_ENDTIME < ? AND BJI_ENDTIME > 0  AND BJI_JOBGUID IN (SELECT BSJ_GUID FROM BI_SIMPLE_JOBS GROUP BY BSJ_GUID) ";
    private static final String SQL_QUERY_EXPIRE_SIMPLE_INSTANCE = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES  WHERE BJI_ENDTIME < ? AND BJI_ENDTIME > 0  AND BJI_USERGUID = ?  AND BJI_JOBGUID IN (SELECT BSJ_GUID FROM BI_SIMPLE_JOBS GROUP BY BSJ_GUID)  ORDER BY BJI_STARTTIME";
    private static final String SQL_QUERY_EXPIRE_SIMPLE_INSTANCE_NO_USER = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES  WHERE BJI_ENDTIME < ? AND BJI_ENDTIME > 0  AND BJI_JOBGUID IN (SELECT BSJ_GUID FROM BI_SIMPLE_JOBS GROUP BY BSJ_GUID)  ORDER BY BJI_STARTTIME";
    private static final String SQL_QUERY_REALTIME_INSTANCE_COUNT_BY_CONDITION = "SELECT COUNT(*)  FROM BI_JOBS_INSTANCES T1  LEFT JOIN BI_JOBS T2  ON T1.BJI_JOBGUID = T2.BJ_JOBGUID  WHERE T1.BJI_PARENT_INSTID IS NULL ";
    private static final String SQL_QUERY_INSTANCE_COUNT_GROUP_BY = "SELECT COUNT(*), BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_NODE, BJI_PUBLISH_NODE, BJI_STATE FROM BI_JOBS_INSTANCES T1  WHERE (BJI_CATEGORY_ID IN ( ?CATEGORY ) )  GROUP BY BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_NODE, BJI_PUBLISH_NODE, BJI_STATE HAVING BJI_STATE <= 1";
    private static final String SQL_QUERY_LAST_SUCCESS_INSTANCE = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES ".replace("BI_JOBS_INSTANCES", "") + " (  SELECT *,ROW_NUMBER() OVER (PARTITION BY " + "BJI_NODE" + ", " + "BJI_CATEGORY_ID" + " ORDER BY " + "BJI_ENDTIME" + " DESC) as rn  FROM BI_JOBS_INSTANCES WHERE " + "BJI_STATE" + " = ? AND " + "BJI_RESULT" + " >= " + 100 + " AND " + "BJI_PUBLISH_NODE" + " IS NOT NULL AND " + "BJI_JOBTYPE" + " = " + JobType.REALTIME_JOB.getValue() + " AND  (" + "BJI_CATEGORY_ID" + " IN ( ?CATEGORY ) )  ) t WHERE t.rn = 1";
    private static final String SQL_QUERY_LAST_PUBLISH_INSTANCE = "SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES ".replace("BI_JOBS_INSTANCES", "") + " (  SELECT *,ROW_NUMBER() OVER (PARTITION BY " + "BJI_PUBLISH_NODE" + ", " + "BJI_CATEGORY_ID" + " ORDER BY " + "BJI_EXEC_START_TIME" + " DESC) as rn  FROM BI_JOBS_INSTANCES WHERE " + "BJI_STATE" + " >= ? AND " + "BJI_PUBLISH_NODE" + " IS NOT NULL AND " + "BJI_JOBTYPE" + " = " + JobType.REALTIME_JOB.getValue() + " AND  (" + "BJI_CATEGORY_ID" + " IN ( ?CATEGORY ) )  ) t WHERE t.rn = 1";

    public static final void insertInstance(Connection conn, JobInstanceBean bean) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_INSTANCE);){
            stmt.setString(1, bean.getInstanceId());
            stmt.setString(2, bean.getParentInstanceId());
            stmt.setString(3, bean.getRootInstanceId());
            stmt.setInt(4, bean.getLevel());
            stmt.setInt(5, bean.getProgress());
            stmt.setString(6, bean.getPrompt());
            stmt.setInt(7, bean.getState());
            stmt.setInt(8, bean.getJobType());
            stmt.setString(9, bean.getUserguid());
            stmt.setString(10, bean.getUsername());
            stmt.setLong(11, bean.getStarttime());
            stmt.setLong(12, bean.getEndtime());
            stmt.setString(13, bean.getJobId());
            stmt.setInt(14, bean.getResult());
            stmt.setString(15, bean.getResultMessage());
            stmt.setString(16, bean.getNode());
            stmt.setString(17, bean.getInstanceName());
            stmt.setString(18, bean.getQuartzInstance());
            stmt.setString(19, bean.getCategoryId());
            stmt.setString(20, bean.getCategoryTitle());
            stmt.setInt(21, bean.isBackstage() ? 1 : 0);
            stmt.setInt(22, bean.getStage());
            stmt.setString(23, bean.getQueryField1());
            stmt.setString(24, bean.getQueryField2());
            stmt.setString(25, bean.getPublishNode());
            stmt.setLong(26, bean.getExecStartTime());
            stmt.executeUpdate();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final void updateInstanceTitle(Connection conn, String instanceId, String title) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_INSTANCE_TITLE);){
            stmt.setString(1, title);
            stmt.setString(2, instanceId);
            stmt.executeUpdate();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void updateInstanceExecuteStage(Connection conn, String instanceId, int stage) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_INSTANCE_STAGE);){
            stmt.setInt(1, stage);
            stmt.setString(2, instanceId);
            stmt.executeUpdate();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final void updateInstanceProgress(Connection conn, String instanceId, int progress, String prompt) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_INSTANCE_PROGRESS_AND_PROMPT);){
            stmt.setInt(1, progress);
            stmt.setString(2, prompt);
            stmt.setString(3, instanceId);
            stmt.executeUpdate();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final void updateInstanceProgress(Connection conn, String instanceId, int progress) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_INSTANCE_PROGRESS);){
            stmt.setInt(1, progress);
            stmt.setString(2, instanceId);
            stmt.executeUpdate();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final void updateInstanceBackstage(Connection conn, String instanceId, boolean backstage) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_INSTANCE_BACKSTAGE);){
            stmt.setInt(1, backstage ? 1 : 0);
            stmt.setString(2, instanceId);
            stmt.executeUpdate();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final void updateInstanceState(Connection conn, String instanceId, int state, int result, String resultMessage, long endtime) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_INSTANCE_STATE);){
            stmt.setInt(1, state);
            stmt.setInt(2, result);
            stmt.setString(3, resultMessage);
            stmt.setLong(4, endtime);
            stmt.setString(5, instanceId);
            stmt.executeUpdate();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final void updateInstanceStateAndNodeName(Connection conn, String instanceId, int state, String nodeName, String quartzInstance) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_INSTANCE_STATE_AND_NODE);){
            stmt.setInt(1, state);
            stmt.setString(2, nodeName);
            stmt.setString(3, quartzInstance);
            stmt.setString(4, instanceId);
            stmt.executeUpdate();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final void updateInstanceStateAndNodeNameAndExecTime(Connection conn, String instanceId, int state, String nodeName, String quartzInstance, long execTime) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_INSTANCE_STATE_AND_NODE_AND_EXEC_TIME);){
            stmt.setInt(1, state);
            stmt.setString(2, nodeName);
            stmt.setString(3, quartzInstance);
            stmt.setLong(4, execTime);
            stmt.setString(5, instanceId);
            stmt.executeUpdate();
        }
    }

    public static final void setUnfinishedInstanceToTerminated(Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_SET_UNFINISHED_INSTANCE_TO_TERMINATED);){
            stmt.setLong(1, System.currentTimeMillis());
            stmt.executeUpdate();
        }
    }

    public static final void setUnfinishedInstanceToTerminated(Connection conn, String nodeName) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_SET_UNFINISHED_INSTANCE_TO_TERMINATED_BY_NODE);){
            stmt.setLong(1, System.currentTimeMillis());
            stmt.setString(2, nodeName);
            stmt.executeUpdate();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Deprecated
    public static final void setUnfinishedInstanceToTerminatedByIds(Connection conn, List<String> instanceIds) throws SQLException {
        StringBuilder idBuilder = new StringBuilder();
        for (int i = 0; i < instanceIds.size(); ++i) {
            String id = instanceIds.get(i);
            if (i > 0) {
                idBuilder.append(",");
            }
            idBuilder.append("'").append(id).append("'");
        }
        String sql = SQL_SET_UNFINISHED_INSTANCE_TO_TERMINATED_BY_IDS.replace("?INSTANCE_IDS", idBuilder.toString());
        try (PreparedStatement stmt = conn.prepareStatement(sql);){
            stmt.executeUpdate();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static List<JobInstanceBean> getUnfinishedInstances(Connection conn, String nodeName) throws SQLException {
        ArrayList<JobInstanceBean> instanceBeans = new ArrayList<JobInstanceBean>();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_GET_UNFINISHED_INSTANCES);){
            stmt.setString(1, nodeName);
            try (ResultSet rs = stmt.executeQuery();){
                if (rs.next()) {
                    instanceBeans.add(JobInstancesDAO.fillBean(rs));
                }
            }
        }
        return instanceBeans;
    }

    public static List<JobInstanceBean> getUnfinishedInstances(Connection conn) throws SQLException {
        ArrayList<JobInstanceBean> instanceBeans = new ArrayList<JobInstanceBean>();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_GET_UNFINISHED_INSTANCES_ALL_NODE);
             ResultSet rs = stmt.executeQuery();){
            while (rs.next()) {
                instanceBeans.add(JobInstancesDAO.fillBean(rs));
            }
        }
        return instanceBeans;
    }

    public static int getNumsBeforeTime(Connection conn, long starttime, State state) throws SQLException {
        String sql = "SELECT COUNT(1) FROM BI_JOBS_INSTANCES WHERE BJI_STATE = ? AND BJI_STARTTIME < ? ";
        try (PreparedStatement stmt = conn.prepareStatement(sql);){
            int n;
            block16: {
                ResultSet rs;
                block14: {
                    int n2;
                    block15: {
                        stmt.setInt(1, state.getValue());
                        stmt.setLong(2, starttime);
                        rs = stmt.executeQuery();
                        try {
                            if (!rs.next()) break block14;
                            n2 = rs.getInt(1);
                            if (rs == null) break block15;
                        }
                        catch (Throwable throwable) {
                            if (rs != null) {
                                try {
                                    rs.close();
                                }
                                catch (Throwable throwable2) {
                                    throwable.addSuppressed(throwable2);
                                }
                            }
                            throw throwable;
                        }
                        rs.close();
                    }
                    return n2;
                }
                n = 0;
                if (rs == null) break block16;
                rs.close();
            }
            return n;
        }
    }

    public static final void cancelInstance(Connection conn, String instanceId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_CANCEL_INSTANCE);){
            stmt.setString(1, instanceId);
            stmt.executeUpdate();
        }
    }

    public static final JobInstanceBean queryLastInstance(Connection conn, String jobId, String category) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_QUERY_INSTANCE_WITH_ORDER_BY);){
            JobInstanceBean jobInstanceBean;
            block16: {
                ResultSet rs;
                block14: {
                    JobInstanceBean jobInstanceBean2;
                    block15: {
                        stmt.setString(1, jobId);
                        rs = stmt.executeQuery();
                        try {
                            if (!rs.next()) break block14;
                            jobInstanceBean2 = JobInstancesDAO.fillBean(rs);
                            if (rs == null) break block15;
                        }
                        catch (Throwable throwable) {
                            if (rs != null) {
                                try {
                                    rs.close();
                                }
                                catch (Throwable throwable2) {
                                    throwable.addSuppressed(throwable2);
                                }
                            }
                            throw throwable;
                        }
                        rs.close();
                    }
                    return jobInstanceBean2;
                }
                jobInstanceBean = null;
                if (rs == null) break block16;
                rs.close();
            }
            return jobInstanceBean;
        }
    }

    public static final JobInstanceBean queryInstanceByGuid(Connection conn, String instanceId) throws SQLException {
        return JobInstancesDAO.queryInstanceByGuid(conn, instanceId, false);
    }

    public static final JobInstanceBean queryInstanceByGuid(Connection conn, String instanceId, boolean simple) throws SQLException {
        String sql = simple ? SQL_QUERY_INSTANCE_SIMPLE_BY_GUID : SQL_QUERY_INSTANCE_BY_GUID;
        try (PreparedStatement stmt = conn.prepareStatement(sql);){
            JobInstanceBean jobInstanceBean;
            block16: {
                ResultSet rs;
                block14: {
                    JobInstanceBean jobInstanceBean2;
                    block15: {
                        stmt.setString(1, instanceId);
                        rs = stmt.executeQuery();
                        try {
                            if (!rs.next()) break block14;
                            jobInstanceBean2 = JobInstancesDAO.fillBean(rs, simple);
                            if (rs == null) break block15;
                        }
                        catch (Throwable throwable) {
                            if (rs != null) {
                                try {
                                    rs.close();
                                }
                                catch (Throwable throwable2) {
                                    throwable.addSuppressed(throwable2);
                                }
                            }
                            throw throwable;
                        }
                        rs.close();
                    }
                    return jobInstanceBean2;
                }
                jobInstanceBean = null;
                if (rs == null) break block16;
                rs.close();
            }
            return jobInstanceBean;
        }
    }

    public static Map<String, JobInstanceBean> queryInstanceByGuid(Connection conn, List<String> instanceGuids, boolean simple) throws SQLException {
        String sql = simple ? SQL_QUERY_INSTANCE_SIMPLE_BY_GUIDS : SQL_QUERY_INSTANCE_BY_GUIDS;
        StringBuilder stringBuilder = new StringBuilder(sql);
        for (int i = 0; i < instanceGuids.size(); ++i) {
            stringBuilder.append(" ?,");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1).append(" ) ");
        HashMap<String, JobInstanceBean> map = new HashMap<String, JobInstanceBean>();
        try (PreparedStatement stmt = conn.prepareStatement(stringBuilder.toString());){
            for (int i = 0; i < instanceGuids.size(); ++i) {
                stmt.setString(i + 1, instanceGuids.get(i));
            }
            try (ResultSet rs = stmt.executeQuery();){
                while (rs.next()) {
                    JobInstanceBean bean = JobInstancesDAO.fillBean(rs, simple);
                    map.put(bean.getInstanceId(), bean);
                }
            }
        }
        return map;
    }

    public static final int getAllInstanceByFolderCount(Connection conn, String folderId, String filter) throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append("BJ_FOLDER").append(" = ? AND ").append(FIELD_PARENT_INSTANCEID).append(" IS NULL");
        if (filter != null && filter.length() > 0) {
            sb.append(" AND (").append(filter).append(")");
        }
        String sql = "SELECT COUNT(1) FROM (SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_CATEGORY_TITLE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME, BJ_FOLDER, BJ_JOBCATEGORY FROM BI_JOBS_INSTANCES LEFT JOIN BI_JOBS ON BJI_JOBGUID = BJ_JOBGUID WHERE " + sb.toString() + ") T1";
        try (PreparedStatement stmt = conn.prepareStatement(sql);){
            int n;
            block17: {
                ResultSet rs;
                block15: {
                    int n2;
                    block16: {
                        stmt.setString(1, folderId);
                        rs = stmt.executeQuery();
                        try {
                            if (!rs.next()) break block15;
                            n2 = rs.getInt(1);
                            if (rs == null) break block16;
                        }
                        catch (Throwable throwable) {
                            if (rs != null) {
                                try {
                                    rs.close();
                                }
                                catch (Throwable throwable2) {
                                    throwable.addSuppressed(throwable2);
                                }
                            }
                            throw throwable;
                        }
                        rs.close();
                    }
                    return n2;
                }
                n = -1;
                if (rs == null) break block17;
                rs.close();
            }
            return n;
        }
    }

    /*
     * Enabled aggressive exception aggregation
     */
    public static List<JobInstanceBean> queryAllInstanceByFolder(Connection conn, String folderId, int start, int end, String filter) throws SQLException {
        IDatabase db = DatabaseManager.getInstance().findDatabaseByConnection(conn);
        try {
            IPagingSQLBuilder pageBuilder = db.createPagingSQLBuilder();
            pageBuilder.setRawSQL(SQL_SELECT_JOIN_BY_FOLDER);
            OrderField of = new OrderField(FIELD_STARTTIME);
            of.setOrderMode("DESC");
            pageBuilder.getOrderFields().add(of);
            pageBuilder.setInnerTableAlias("T1 ");
            StringBuilder sb = new StringBuilder();
            sb.append("BJ_FOLDER").append(" = ? AND ").append(FIELD_PARENT_INSTANCEID).append(" IS NULL");
            if (filter != null && !filter.isEmpty()) {
                sb.append(" AND (").append(filter).append(")");
            }
            pageBuilder.setFilter(sb.toString());
            try {
                String buildSQL = pageBuilder.buildSQL(start, end);
                logger.debug("\u6b63\u5728\u6267\u884c\u6309\u6587\u4ef6\u5939\u83b7\u53d6\u6267\u884c\u5b9e\u4f8bSQL\uff1a", (Object)buildSQL);
                try (PreparedStatement stmt = conn.prepareStatement(buildSQL);){
                    ArrayList<JobInstanceBean> arrayList;
                    block18: {
                        stmt.setString(1, folderId);
                        ResultSet rs = stmt.executeQuery();
                        try {
                            ArrayList<JobInstanceBean> list = new ArrayList<JobInstanceBean>();
                            while (rs.next()) {
                                JobInstanceBean bean = JobInstancesDAO.fillBean(rs);
                                bean.setCategoryId(rs.getString("BJ_JOBCATEGORY"));
                                list.add(bean);
                            }
                            arrayList = list;
                            if (rs == null) break block18;
                        }
                        catch (Throwable throwable) {
                            if (rs != null) {
                                try {
                                    rs.close();
                                }
                                catch (Throwable throwable2) {
                                    throwable.addSuppressed(throwable2);
                                }
                            }
                            throw throwable;
                        }
                        rs.close();
                    }
                    return arrayList;
                }
            }
            catch (DBException e) {
                SQLException sqle = new SQLException(e.getMessage());
                sqle.setStackTrace(e.getStackTrace());
                throw sqle;
            }
        }
        catch (UnsupportPagingException e) {
            SQLException sqle = new SQLException(e.getMessage());
            sqle.setStackTrace(e.getStackTrace());
            throw sqle;
        }
    }

    /*
     * Enabled aggressive exception aggregation
     */
    public static final List<JobInstanceBean> queryAllInstance(Connection conn, String jobId, String category, int start, int end) throws SQLException {
        IDatabase db = DatabaseManager.getInstance().findDatabaseByConnection(conn);
        try {
            IPagingSQLBuilder pageBuilder = db.createPagingSQLBuilder();
            pageBuilder.setRawSQL(SQL_SELECT_BASE);
            OrderField of = new OrderField(FIELD_STARTTIME);
            of.setOrderMode("DESC");
            pageBuilder.getOrderFields().add(of);
            pageBuilder.setInnerTableAlias("T1 ");
            pageBuilder.setFilter("BJI_JOBGUID = ? AND BJI_PARENT_INSTID IS NULL");
            try {
                String buildSQL = pageBuilder.buildSQL(start, end);
                try (PreparedStatement stmt = conn.prepareStatement(buildSQL);){
                    ArrayList<JobInstanceBean> arrayList;
                    block33: {
                        stmt.setString(1, jobId);
                        ResultSet rs = stmt.executeQuery();
                        try {
                            ArrayList<JobInstanceBean> list = new ArrayList<JobInstanceBean>();
                            while (rs.next()) {
                                JobInstanceBean bean = JobInstancesDAO.fillBean(rs);
                                list.add(bean);
                            }
                            arrayList = list;
                            if (rs == null) break block33;
                        }
                        catch (Throwable list) {
                            if (rs != null) {
                                try {
                                    rs.close();
                                }
                                catch (Throwable throwable) {
                                    list.addSuppressed(throwable);
                                }
                            }
                            throw list;
                        }
                        rs.close();
                    }
                    return arrayList;
                }
            }
            catch (DBException e) {
                SQLException sqle = new SQLException(e.getMessage());
                sqle.setStackTrace(e.getStackTrace());
                throw sqle;
            }
        }
        catch (UnsupportPagingException e) {
            logger.warn("\u8be5\u6570\u636e\u5e93\u4e0d\u652f\u6301\u5206\u9875\uff0c\u5c06\u4f1a\u4ee5\u5168\u91cf\u67e5\u8be2\u7684\u65b9\u5f0f\u8fdb\u884c\u67e5\u8be2\uff0c\u6570\u636e\u91cf\u5927\u65f6\u5c06\u4f1a\u5f71\u54cd\u6027\u80fd", e);
            try (PreparedStatement stmt = conn.prepareStatement(SQL_QUERY_INSTANCE_WITH_ORDER_BY);){
                ArrayList<JobInstanceBean> arrayList;
                block35: {
                    ArrayList<JobInstanceBean> list;
                    stmt.setString(1, jobId);
                    ResultSet rs = stmt.executeQuery();
                    try {
                        list = new ArrayList<JobInstanceBean>();
                        int count = 0;
                        while (rs.next()) {
                            if (++count > start && count <= end) {
                                JobInstanceBean bean = JobInstancesDAO.fillBean(rs);
                                list.add(bean);
                                continue;
                            }
                            if (count <= end) continue;
                            arrayList = list;
                            if (rs == null) break block34;
                        }
                    }
                    catch (Throwable throwable) {
                        if (rs != null) {
                            try {
                                rs.close();
                            }
                            catch (Throwable throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                        }
                        throw throwable;
                    }
                    {
                        block34: {
                            rs.close();
                        }
                        return arrayList;
                    }
                    arrayList = list;
                    if (rs == null) break block35;
                    rs.close();
                }
                return arrayList;
            }
        }
    }

    public static final int getAllInstanceCount(Connection conn, String jobId, String category) throws SQLException {
        String sql = "SELECT COUNT(1) FROM (SELECT BJI_INSTANCEID, BJI_PARENT_INSTID, BJI_ROOT_INSTID, BJI_LEVEL, BJI_PROGRESS, BJI_PROMPT, BJI_STATE, BJI_JOBTYPE, BJI_USERGUID, BJI_USERNAME, BJI_STARTTIME, BJI_ENDTIME, BJI_JOBGUID, BJI_RESULT, BJI_RESULTMSG, BJI_NODE, BJI_INSTITLE, BJI_QUARTZ_INSTANCE, BJI_CATEGORY_ID, BJI_CATEGORY_TITLE, BJI_BACKSTAGE, BJI_EXECUTE_STAGE, BJI_QUERY_FIELD1, BJI_QUERY_FIELD2, BJI_PUBLISH_NODE, BJI_EXEC_START_TIME FROM BI_JOBS_INSTANCES WHERE BJI_JOBGUID = ? AND BJI_PARENT_INSTID IS NULL) T1";
        try (PreparedStatement stmt = conn.prepareStatement(sql);){
            int n;
            block12: {
                stmt.setString(1, jobId);
                ResultSet rs = stmt.executeQuery();
                try {
                    rs.next();
                    n = rs.getInt(1);
                    if (rs == null) break block12;
                }
                catch (Throwable throwable) {
                    if (rs != null) {
                        try {
                            rs.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                rs.close();
            }
            return n;
        }
    }

    private static final JobInstanceBean fillBean(ResultSet rs) throws SQLException {
        return JobInstancesDAO.fillBean(rs, false);
    }

    private static final JobInstanceBean fillBean(ResultSet rs, boolean simple) throws SQLException {
        JobInstanceBean bean = new JobInstanceBean();
        bean.setInstanceId(rs.getString(FIELD_INSTANCEID));
        bean.setState(rs.getInt(FIELD_STATE));
        bean.setProgress(rs.getInt(FIELD_PROGRESS));
        bean.setPrompt(rs.getString(FIELD_PROMPT));
        bean.setResult(rs.getInt(FIELD_RESULT));
        bean.setStarttime(rs.getLong(FIELD_STARTTIME));
        if (simple) {
            return bean;
        }
        bean.setParentInstanceId(rs.getString(FIELD_PARENT_INSTANCEID));
        bean.setRootInstanceId(rs.getString(FIELD_ROOT_INSTANCEID));
        bean.setLevel(rs.getInt(FIELD_LEVEL));
        bean.setJobType(rs.getInt(FIELD_JOBTYPE));
        bean.setUserguid(rs.getString(FIELD_USERGUID));
        bean.setUsername(rs.getString(FIELD_USERNAME));
        long aLong = rs.getLong(FIELD_ENDTIME);
        if (UNFINISH_ENDTIME == aLong) {
            aLong = -1L;
        }
        bean.setEndtime(aLong);
        bean.setJobId(rs.getString(FIELD_JOBID));
        bean.setResultMessage(rs.getString(FIELD_RESULTMESSAGE));
        bean.setNode(rs.getString(FIELD_NODE));
        bean.setInstanceName(rs.getString(FIELD_INSTITLE));
        bean.setQuartzInstance(rs.getString(FIELD_QUARTZ_INSTANCE));
        bean.setCategoryId(rs.getString(FIELD_CATEGORY_ID));
        bean.setCategoryTitle(rs.getString(FIELD_CATEGORY_TITLE));
        bean.setBackstage(rs.getInt(FIELD_BACKSTAGE) == 1);
        bean.setStage(rs.getInt(FIELD_EXECUTE_STAGE));
        bean.setQueryField1(rs.getString(FIELD_QUERY_FIELD1));
        bean.setQueryField2(rs.getString(FIELD_QUERY_FIELD2));
        bean.setPublishNode(rs.getString(FIELD_PUBLISH_NODE));
        bean.setExecStartTime(rs.getLong(FIELD_EXEC_START_TIME));
        bean.setGroupId(RealTimeJobFactory.getRealTimeJobGroupByCategoryId(bean.getCategoryId()));
        return bean;
    }

    public static final int getSubInstanceCount(Connection conn, String instanceId) throws SQLException {
        String sql = "SELECT COUNT(1) FROM (" + SQL_QUERY_SUBINSTANCE_UNFINISHED + " UNION ALL " + SQL_QUERY_SUBINSTANCE_FINISHED + ") T1";
        try (PreparedStatement stmt = conn.prepareStatement(sql);){
            int n;
            block12: {
                stmt.setString(1, instanceId);
                stmt.setString(2, instanceId);
                ResultSet rs = stmt.executeQuery();
                try {
                    rs.next();
                    n = rs.getInt(1);
                    if (rs == null) break block12;
                }
                catch (Throwable throwable) {
                    if (rs != null) {
                        try {
                            rs.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                rs.close();
            }
            return n;
        }
    }

    public static final List<JobInstanceBean> getSubInstance(Connection conn, String instanceId) throws SQLException {
        JobInstanceBean bean;
        ResultSet rs;
        ArrayList<JobInstanceBean> list = new ArrayList<JobInstanceBean>();
        String sql = SQL_QUERY_SUBINSTANCE_UNFINISHED + SQL_CLUSTER_ORDERBY_STARTTIME;
        try (PreparedStatement stmt = conn.prepareStatement(sql);){
            stmt.setString(1, instanceId);
            rs = stmt.executeQuery();
            try {
                while (rs.next()) {
                    bean = JobInstancesDAO.fillBean(rs);
                    list.add(bean);
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
            }
        }
        sql = SQL_QUERY_SUBINSTANCE_FINISHED + SQL_CLUSTER_ORDERBY_STARTTIME;
        stmt = conn.prepareStatement(sql);
        try {
            stmt.setString(1, instanceId);
            rs = stmt.executeQuery();
            try {
                while (rs.next()) {
                    bean = JobInstancesDAO.fillBean(rs);
                    list.add(bean);
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
            }
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return list;
    }

    public static int queryInstanceCount(Connection conn, String jobTitle, List<String> categorys, List<State> states, List<String> execNodes, String execUserTitle, String execUserGuid, Long startTime, Long endTime, String queryField1, String queryField2) throws SQLException {
        return JobInstancesDAO.queryInstanceCount(conn, jobTitle, categorys, states, execNodes, execUserTitle, execUserGuid, startTime, endTime, queryField1, queryField2, null);
    }

    public static int queryInstanceCount(Connection conn, String jobTitle, List<String> categorys, List<State> states, List<String> execNodes, String execUserTitle, String execUserGuid, Long startTime, Long endTime, String queryField1, String queryField2, String filter) throws SQLException {
        int count = 0;
        ArrayList<Object> objects = new ArrayList<Object>();
        String sqlConditionString = JobInstancesDAO.generateQueryInstanceCondition(false, jobTitle, categorys, states, execNodes, execUserTitle, execUserGuid, startTime, endTime, queryField1, queryField2, filter, objects);
        try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*)  FROM BI_JOBS_INSTANCES T1  LEFT JOIN BI_JOBS T2  ON T1.BJI_JOBGUID = T2.BJ_JOBGUID  WHERE T1.BJI_PARENT_INSTID IS NULL " + sqlConditionString);){
            JobInstancesDAO.setParams(objects, ps);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        }
        return count;
    }

    public static final JobInstanceBean getInstance(Connection conn, String instanceId) throws SQLException {
        JobInstanceBean bean = new JobInstanceBean();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_QUERY_PARENTINSTANCE);){
            stmt.setString(1, instanceId);
            try (ResultSet rs = stmt.executeQuery();){
                if (rs.next()) {
                    bean = JobInstancesDAO.fillBean(rs);
                }
            }
        }
        return bean;
    }

    public static List<JobInstanceBean> queryInstances(Connection conn, String jobTitle, List<String> categorys, List<State> states, List<String> execNodes, String execUserTitle, String execUserGuid, Long startTime, Long endTime, int start, int end, String queryField1, String queryField2) throws SQLException {
        return JobInstancesDAO.queryInstances(conn, jobTitle, categorys, states, execNodes, execUserTitle, execUserGuid, startTime, endTime, start, end, queryField1, queryField2, null);
    }

    public static List<JobInstanceBean> queryInstances(Connection conn, String jobTitle, List<String> categorys, List<State> states, List<String> execNodes, String execUserTitle, String execUserGuid, Long startTime, Long endTime, int start, int end, String queryField1, String queryField2, String filter) throws SQLException {
        ArrayList<JobInstanceBean> instances = new ArrayList<JobInstanceBean>();
        int selectCount = end - start;
        ArrayList<Object> objects = new ArrayList<Object>();
        String sqlConditionString = JobInstancesDAO.generateQueryInstanceCondition(false, jobTitle, categorys, states, execNodes, execUserTitle, execUserGuid, startTime, endTime, queryField1, queryField2, filter, objects);
        String sql = SQL_QUERY_INSTANCES_BY_CONDITION + sqlConditionString;
        IDatabase db = DatabaseManager.getInstance().findDatabaseByConnection(conn);
        try {
            IPagingSQLBuilder pageBuilder = db.createPagingSQLBuilder();
            OrderField orderField1 = new OrderField("STATE_ORDER");
            orderField1.setOrderMode("DESC");
            pageBuilder.getOrderFields().add(orderField1);
            OrderField orderField2 = new OrderField("STATE_ENDTIME");
            orderField2.setOrderMode("DESC");
            pageBuilder.getOrderFields().add(orderField2);
            pageBuilder.setRawSQL(sql);
            try {
                String buildSQL = pageBuilder.buildSQL(start, end);
                try (PreparedStatement stmt = conn.prepareStatement(buildSQL);){
                    JobInstancesDAO.setParams(objects, stmt);
                    try (ResultSet rs = stmt.executeQuery();){
                        while (rs.next()) {
                            JobInstanceBean bean = JobInstancesDAO.fillBean(rs);
                            if (StringUtils.isEmpty((String)bean.getCategoryId())) {
                                bean.setCategoryId(rs.getString("BJ_JOBCATEGORY"));
                            }
                            instances.add(bean);
                        }
                    }
                }
            }
            catch (DBException e) {
                SQLException sqle = new SQLException(e.getMessage());
                sqle.setStackTrace(e.getStackTrace());
                throw sqle;
            }
        }
        catch (UnsupportPagingException e) {
            logger.warn("\u8be5\u6570\u636e\u5e93\u4e0d\u652f\u6301\u5206\u9875\uff0c\u5c06\u4f1a\u4ee5\u5168\u91cf\u67e5\u8be2\u7684\u65b9\u5f0f\u8fdb\u884c\u67e5\u8be2\uff0c\u6570\u636e\u91cf\u5927\u65f6\u5c06\u4f1a\u5f71\u54cd\u6027\u80fd", e);
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery();){
                int count = 0;
                while (rs.next()) {
                    if (++count > start && count <= end) {
                        JobInstanceBean bean = JobInstancesDAO.fillBean(rs);
                        instances.add(bean);
                        continue;
                    }
                    if (count <= end) continue;
                }
            }
        }
        if (instances.size() == selectCount) {
            return instances;
        }
        ArrayList<JobInstanceBean> resultInstances = new ArrayList<JobInstanceBean>();
        for (int i = 0; i < instances.size() && i < selectCount; ++i) {
            resultInstances.add((JobInstanceBean)instances.get(i));
        }
        return resultInstances;
    }

    public static List<JobInstanceBean> queryInstancesByInstanceIds(Connection conn, List<String> instanceIds) throws SQLException {
        ArrayList<JobInstanceBean> instances = new ArrayList<JobInstanceBean>();
        if (instanceIds == null || instanceIds.isEmpty()) {
            return instances;
        }
        StringBuilder idBuilder = new StringBuilder();
        for (int i = 0; i < instanceIds.size(); ++i) {
            String id = instanceIds.get(i);
            id = Html.cleanName((String)id, (char[])new char[0]);
            if (i > 0) {
                idBuilder.append(",");
            }
            idBuilder.append("'").append(id).append("'");
        }
        String sql = String.format(SQL_QUERY_INSTANCES_BY_INSTANCE_IDS.replace("?IDS", "%s"), idBuilder.toString());
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery();){
            while (rs.next()) {
                instances.add(JobInstancesDAO.fillBean(rs));
            }
        }
        return instances;
    }

    private static String generateQueryInstanceCondition(boolean realtime, String jobTitle, List<String> categorys, List<State> states, List<String> execNodes, String execUserTitle, String execUserGuid, Long startTime, Long endTime, String queryField1, String queryField2, String filter, List<Object> params) {
        String tempSql;
        StringBuilder sqlBuilder = new StringBuilder();
        if (StringUtils.isNotEmpty((String)jobTitle)) {
            jobTitle = Html.encodeQueryString((String)jobTitle);
            sqlBuilder.append(" AND UPPER(T1.BJI_INSTITLE) LIKE ? ");
            params.add("%" + jobTitle.toUpperCase() + "%");
        }
        if (categorys != null && !categorys.isEmpty()) {
            StringBuilder categoryBuilder = new StringBuilder();
            for (int i = 0; i < categorys.size(); ++i) {
                if (i > 0) {
                    categoryBuilder.append(",");
                }
                String s = categorys.get(i);
                s = Html.cleanName((String)s, (char[])new char[0]);
                categoryBuilder.append("?");
            }
            if (realtime) {
                tempSql = " AND (BJI_CATEGORY_ID IN ( ?CATEGORY ) ) ";
                sqlBuilder.append(String.format(tempSql.replace("?CATEGORY", "%s"), categoryBuilder));
                params.addAll(categorys);
            } else {
                tempSql = " AND ( T2.BJ_JOBCATEGORY IN ( ?CATEGORY ) OR BJI_CATEGORY_ID IN ( ?CATEGORY ) ) ";
                sqlBuilder.append(String.format(tempSql.replace("?CATEGORY", "%s"), categoryBuilder, categoryBuilder));
                params.addAll(categorys);
                params.addAll(categorys);
            }
        }
        if (states != null && !states.isEmpty()) {
            StringBuilder stateBuilder = new StringBuilder();
            for (int i = 0; i < states.size(); ++i) {
                if (i > 0) {
                    stateBuilder.append(",");
                }
                stateBuilder.append("?");
                params.add(states.get(i).getValue());
            }
            tempSql = " AND T1.BJI_STATE IN ( ?STATE ) ";
            sqlBuilder.append(String.format(tempSql.replace("?STATE", "%s"), stateBuilder.toString()));
        }
        if (execNodes != null && !execNodes.isEmpty()) {
            StringBuilder execNodeBuilder = new StringBuilder();
            for (int i = 0; i < execNodes.size(); ++i) {
                if (i > 0) {
                    execNodeBuilder.append(",");
                }
                execNodeBuilder.append("?");
                params.add(execNodes.get(i));
            }
            String tempSql2 = " AND T1.BJI_NODE IN ( ?EXECNODE ) ";
            sqlBuilder.append(String.format(tempSql2.replace("?EXECNODE", "%s"), execNodeBuilder.toString()));
        }
        if (StringUtils.isNotEmpty((String)execUserTitle)) {
            execUserTitle = Html.encodeQueryString((String)execUserTitle);
            sqlBuilder.append(" AND UPPER(T1.BJI_USERNAME) LIKE ?");
            params.add("%" + execUserTitle.toUpperCase() + "%");
        }
        if (StringUtils.isNotEmpty((String)execUserGuid)) {
            execUserGuid = Html.encodeQueryString((String)execUserGuid);
            sqlBuilder.append(" AND T1.BJI_USERGUID = ?");
            params.add(execUserGuid);
        }
        if (startTime != null && endTime != null) {
            sqlBuilder.append(" AND ( T1.BJI_STARTTIME < ? AND T1.BJI_ENDTIME > ? ) ");
            params.add(endTime);
            params.add(startTime);
        } else if (startTime != null) {
            sqlBuilder.append(" AND T1.BJI_STARTTIME > ?");
            params.add(startTime);
        } else if (endTime != null) {
            sqlBuilder.append(" AND T1.BJI_ENDTIME < ?");
            params.add(endTime);
        }
        if (StringUtils.isNotEmpty((String)queryField1)) {
            sqlBuilder.append(" AND T1.BJI_QUERY_FIELD1 = ?");
            params.add(queryField1);
        }
        if (StringUtils.isNotEmpty((String)queryField2)) {
            sqlBuilder.append(" AND T1.BJI_QUERY_FIELD2 = ?");
            params.add(queryField2);
        }
        if (filter != null && !filter.isEmpty()) {
            sqlBuilder.append(" AND (").append(filter).append(")");
        }
        return sqlBuilder.toString();
    }

    private static void setParams(List<Object> objects, PreparedStatement ps) throws SQLException {
        for (int i = 0; i < objects.size(); ++i) {
            Object o = objects.get(i);
            if (o instanceof String) {
                ps.setString(i + 1, (String)o);
                continue;
            }
            if (o instanceof Long) {
                ps.setLong(i + 1, (Long)o);
                continue;
            }
            if (!(o instanceof Integer)) continue;
            ps.setInt(i + 1, (Integer)o);
        }
    }

    public static List<JobInstanceBean> getAllSubInstanceByRootInstanceId(Connection conn, String rootInstanceId) throws SQLException {
        ArrayList<String> ids = new ArrayList<String>();
        ids.add(rootInstanceId);
        return JobInstancesDAO.getAllSubInstanceListByRootInstanceIds(conn, ids);
    }

    public static List<JobInstanceBean> getAllSubInstanceListByRootInstanceIds(Connection conn, List<String> rootInstanceId) throws SQLException {
        ArrayList<JobInstanceBean> instanceBeans = new ArrayList<JobInstanceBean>();
        Map<String, List<JobInstanceBean>> resultMap = JobInstancesDAO.getAllSubInstanceByRootInstanceId(conn, rootInstanceId);
        for (Map.Entry<String, List<JobInstanceBean>> entry : resultMap.entrySet()) {
            instanceBeans.addAll((Collection<JobInstanceBean>)entry.getValue());
        }
        return instanceBeans;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Map<String, List<JobInstanceBean>> getAllSubInstanceByRootInstanceId(Connection conn, List<String> rootInstanceId) throws SQLException {
        HashMap<String, List<JobInstanceBean>> result = new HashMap<String, List<JobInstanceBean>>();
        if (rootInstanceId == null || rootInstanceId.isEmpty()) {
            return result;
        }
        StringBuilder idBuilder = new StringBuilder();
        for (int i = 0; i < rootInstanceId.size(); ++i) {
            String id = rootInstanceId.get(i);
            id = Html.cleanName((String)id, (char[])new char[0]);
            if (i > 0) {
                idBuilder.append(",");
            }
            idBuilder.append("'").append(id).append("'");
            result.put(id, new ArrayList());
        }
        String sql = String.format(SQL_QUERY_SUBINSTANCE_BY_ROOT.replace("?IDS", "%s"), idBuilder.toString());
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery();){
            while (rs.next()) {
                JobInstanceBean bean = JobInstancesDAO.fillBean(rs);
                ((List)result.get(bean.getRootInstanceId())).add(bean);
            }
        }
        return result;
    }

    public static List<JobInstanceBean> getAllSubInstanceListByParentInstanceId(Connection conn, List<String> parentInstanceId) throws SQLException {
        ArrayList<JobInstanceBean> instanceBeans = new ArrayList<JobInstanceBean>();
        Map<String, List<JobInstanceBean>> resultMap = JobInstancesDAO.getAllSubInstanceByParentInstanceId(conn, parentInstanceId);
        for (Map.Entry<String, List<JobInstanceBean>> entry : resultMap.entrySet()) {
            instanceBeans.addAll((Collection<JobInstanceBean>)entry.getValue());
        }
        return instanceBeans;
    }

    public static Map<String, List<JobInstanceBean>> getAllSubInstanceByParentInstanceId(Connection conn, List<String> parentInstanceId) throws SQLException {
        HashMap<String, List<JobInstanceBean>> result = new HashMap<String, List<JobInstanceBean>>();
        if (parentInstanceId == null || parentInstanceId.size() == 0) {
            return result;
        }
        StringBuilder idBuilder = new StringBuilder();
        for (int i = 0; i < parentInstanceId.size(); ++i) {
            String id = parentInstanceId.get(i);
            id = Html.cleanName((String)id, (char[])new char[0]);
            if (i > 0) {
                idBuilder.append(",");
            }
            idBuilder.append("'").append(id).append("'");
            result.put(id, new ArrayList());
        }
        String sql = String.format(SQL_QUERY_SUBINSTANCE_BY_PARENT.replace("?IDS", "%s"), idBuilder.toString());
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery();){
            while (rs.next()) {
                JobInstanceBean bean = JobInstancesDAO.fillBean(rs);
                ((List)result.get(bean.getParentInstanceId())).add(bean);
            }
        }
        return result;
    }

    public static void delete(Connection conn, List<String> instanceIds) throws SQLException {
        if (instanceIds == null || instanceIds.isEmpty()) {
            return;
        }
        StringBuilder idBuilder = new StringBuilder();
        for (int i = 0; i < instanceIds.size(); ++i) {
            String id = instanceIds.get(i);
            id = Html.cleanName((String)id, (char[])new char[0]);
            if (i > 0) {
                idBuilder.append(",");
            }
            idBuilder.append("'").append(id).append("'");
        }
        String sql = String.format(SQL_DELETE_BY_GUID.replace("?IDS", "%s"), idBuilder.toString());
        try (PreparedStatement ps = conn.prepareStatement(sql);){
            ps.execute();
        }
    }

    public static int getExpireRealtimeInstanceCount(Connection conn, String userGuid, long expireTimePoint) throws SQLException {
        int count = 0;
        String sql = StringUtils.isNotEmpty((String)userGuid) ? SQL_QUERY_EXPIRE_INSTANCE_COUNT.replace("?USERCONDITION", "BJI_USERGUID = ? ") : SQL_QUERY_EXPIRE_INSTANCE_COUNT.replace("?USERCONDITION", " 1=1 ");
        try (PreparedStatement ps = conn.prepareStatement(sql);){
            ps.setInt(1, JobType.REALTIME_JOB.getValue());
            ps.setLong(2, expireTimePoint);
            if (StringUtils.isNotEmpty((String)userGuid)) {
                ps.setString(3, userGuid);
            }
            try (ResultSet rs = ps.executeQuery();){
                while (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        }
        return count;
    }

    public static List<JobInstanceBean> getExpireRealtimeInstance(Connection conn, int topN, String userGuid, long expireTimePoint) throws SQLException {
        ArrayList<JobInstanceBean> instances = new ArrayList<JobInstanceBean>();
        String sql = StringUtils.isNotEmpty((String)userGuid) ? SQL_QUERY_EXPIRE_INSTANCE : SQL_QUERY_EXPIRE_INSTANCE_NO_USER;
        try (PreparedStatement ps = conn.prepareStatement(sql, 1003, 1007);){
            ps.setInt(1, JobType.REALTIME_JOB.getValue());
            ps.setLong(2, expireTimePoint);
            if (StringUtils.isNotEmpty((String)userGuid)) {
                ps.setString(3, userGuid);
            }
            try (ResultSet rs = ps.executeQuery();){
                while (rs.next()) {
                    if (instances.size() >= topN) {
                        break;
                    }
                    instances.add(JobInstancesDAO.fillBean(rs));
                }
            }
        }
        return instances;
    }

    public static int getExpireScheduleInstanceCount(Connection conn, JobType type, String userGuid, long expireTimePoint) throws SQLException {
        int count = 0;
        String sql = StringUtils.isNotEmpty((String)userGuid) ? SQL_QUERY_EXPIRE_SCHEDULE_INSTANCE_COUNT : SQL_QUERY_EXPIRE_SCHEDULE_INSTANCE_COUNT_NO_USER;
        List<String> categoryIds = JobFactoryManager.getInstance().getCategoryIds();
        if (categoryIds == null || categoryIds.isEmpty()) {
            return count;
        }
        StringBuilder sqlBuilder = new StringBuilder(sql);
        StringBuilder idBuilder = new StringBuilder();
        categoryIds.forEach(id -> idBuilder.append("'").append((String)id).append("',"));
        sqlBuilder.append(idBuilder.substring(0, idBuilder.length() - 1));
        sqlBuilder.append(" ) AND BJI_JOBGUID IN (SELECT BJ_JOBGUID FROM BI_JOBS GROUP BY BJ_JOBGUID) ");
        sql = sqlBuilder.toString();
        try (PreparedStatement ps = conn.prepareStatement(sql);){
            ps.setInt(1, type.getValue());
            ps.setLong(2, expireTimePoint);
            if (StringUtils.isNotEmpty((String)userGuid)) {
                ps.setString(3, userGuid);
            }
            try (ResultSet rs = ps.executeQuery();){
                while (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        }
        return count;
    }

    public static List<JobInstanceBean> getExpireScheduleInstance(Connection conn, JobType type, int topN, String userGuid, long expireTimePoint) throws SQLException {
        ArrayList<JobInstanceBean> instances = new ArrayList<JobInstanceBean>();
        String sql = StringUtils.isNotEmpty((String)userGuid) ? SQL_QUERY_EXPIRE_SCHEDULE_INSTANCE : SQL_QUERY_EXPIRE_SCHEDULE_INSTANCE_NO_USER;
        List<String> categoryIds = JobFactoryManager.getInstance().getCategoryIds();
        if (categoryIds == null || categoryIds.isEmpty()) {
            return instances;
        }
        StringBuilder idBuilder = new StringBuilder();
        categoryIds.forEach(id -> idBuilder.append("'").append((String)id).append("',"));
        sql = sql.replace("?CATEGORIES", idBuilder.substring(0, idBuilder.length() - 1));
        try (PreparedStatement ps = conn.prepareStatement(sql, 1003, 1007);){
            ps.setInt(1, type.getValue());
            ps.setLong(2, expireTimePoint);
            if (StringUtils.isNotEmpty((String)userGuid)) {
                ps.setString(3, userGuid);
            }
            try (ResultSet rs = ps.executeQuery();){
                while (rs.next()) {
                    if (instances.size() >= topN) {
                        break;
                    }
                    instances.add(JobInstancesDAO.fillBean(rs));
                }
            }
        }
        return instances;
    }

    public static int getExpireRemoteInstanceCount(Connection conn, String userGuid, long expireTimePoint) throws SQLException {
        int count = 0;
        String sql = StringUtils.isNotEmpty((String)userGuid) ? SQL_QUERY_EXPIRE_SCHEDULE_INSTANCE_COUNT : SQL_QUERY_EXPIRE_SCHEDULE_INSTANCE_COUNT_NO_USER;
        List<String> categoryIds = BaseJobFactoryManager.getInstance().getCategoryIds();
        if (categoryIds == null || categoryIds.isEmpty()) {
            return count;
        }
        StringBuilder sqlBuilder = new StringBuilder(sql);
        StringBuilder idBuilder = new StringBuilder();
        categoryIds.forEach(id -> idBuilder.append("'").append((String)id).append("',"));
        sqlBuilder.append(idBuilder.substring(0, idBuilder.length() - 1));
        sqlBuilder.append(" ) AND BJI_JOBGUID IN (SELECT BJ_JOBGUID FROM BI_JOBS GROUP BY BJ_JOBGUID) ");
        sql = sqlBuilder.toString();
        try (PreparedStatement ps = conn.prepareStatement(sql);){
            ps.setInt(1, JobType.MANUAL_JOB.getValue());
            ps.setLong(2, expireTimePoint);
            if (StringUtils.isNotEmpty((String)userGuid)) {
                ps.setString(3, userGuid);
            }
            try (ResultSet rs = ps.executeQuery();){
                while (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        }
        return count;
    }

    public static List<JobInstanceBean> getExpireRemoteInstance(Connection conn, int topN, String userGuid, long expireTimePoint) throws SQLException {
        ArrayList<JobInstanceBean> instances = new ArrayList<JobInstanceBean>();
        String sql = StringUtils.isNotEmpty((String)userGuid) ? SQL_QUERY_EXPIRE_SCHEDULE_INSTANCE : SQL_QUERY_EXPIRE_SCHEDULE_INSTANCE_NO_USER;
        List<String> categoryIds = BaseJobFactoryManager.getInstance().getCategoryIds();
        if (categoryIds == null || categoryIds.isEmpty()) {
            return instances;
        }
        StringBuilder idBuilder = new StringBuilder();
        categoryIds.forEach(id -> idBuilder.append("'").append((String)id).append("',"));
        sql = sql.replace("?CATEGORIES", idBuilder.substring(0, idBuilder.length() - 1));
        try (PreparedStatement ps = conn.prepareStatement(sql, 1003, 1007);){
            ps.setInt(1, JobType.MANUAL_JOB.getValue());
            ps.setLong(2, expireTimePoint);
            if (StringUtils.isNotEmpty((String)userGuid)) {
                ps.setString(3, userGuid);
            }
            try (ResultSet rs = ps.executeQuery();){
                while (rs.next()) {
                    if (instances.size() >= topN) {
                        break;
                    }
                    instances.add(JobInstancesDAO.fillBean(rs));
                }
            }
        }
        return instances;
    }

    public static int getExpireSimpleInstanceCount(Connection conn, String userGuid, long expireTimePoint) throws SQLException {
        int count = 0;
        String sql = StringUtils.isNotEmpty((String)userGuid) ? SQL_QUERY_EXPIRE_SIMPLE_INSTANCE_COUNT : SQL_QUERY_EXPIRE_SIMPLE_INSTANCE_COUNT_NO_USER;
        try (PreparedStatement ps = conn.prepareStatement(sql);){
            ps.setLong(1, expireTimePoint);
            if (StringUtils.isNotEmpty((String)userGuid)) {
                ps.setString(2, userGuid);
            }
            try (ResultSet rs = ps.executeQuery();){
                while (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        }
        return count;
    }

    public static List<JobInstanceBean> getExpireSimpleInstance(Connection conn, int topN, String userGuid, long expireTimePoint) throws SQLException {
        ArrayList<JobInstanceBean> instances = new ArrayList<JobInstanceBean>();
        String sql = StringUtils.isNotEmpty((String)userGuid) ? SQL_QUERY_EXPIRE_SIMPLE_INSTANCE : SQL_QUERY_EXPIRE_SIMPLE_INSTANCE_NO_USER;
        try (PreparedStatement ps = conn.prepareStatement(sql, 1003, 1007);){
            ps.setLong(1, expireTimePoint);
            if (StringUtils.isNotEmpty((String)userGuid)) {
                ps.setString(2, userGuid);
            }
            try (ResultSet rs = ps.executeQuery();){
                while (rs.next()) {
                    if (instances.size() >= topN) {
                        break;
                    }
                    instances.add(JobInstancesDAO.fillBean(rs));
                }
            }
        }
        return instances;
    }

    public static int queryRealtimeInstanceCount(Connection conn, String jobTitle, List<String> categorys, List<State> states, List<String> execNodes, String execUserTitle, String execUserGuid, Long startTime, Long endTime, String queryField1, String queryField2, String filter) throws SQLException {
        int count = 0;
        ArrayList<Object> objects = new ArrayList<Object>();
        String sqlConditionString = JobInstancesDAO.generateQueryInstanceCondition(true, jobTitle, categorys, states, execNodes, execUserTitle, execUserGuid, startTime, endTime, queryField1, queryField2, filter, objects);
        try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*)  FROM BI_JOBS_INSTANCES T1  LEFT JOIN BI_JOBS T2  ON T1.BJI_JOBGUID = T2.BJ_JOBGUID  WHERE T1.BJI_PARENT_INSTID IS NULL " + sqlConditionString);){
            JobInstancesDAO.setParams(objects, ps);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        }
        return count;
    }

    public static List<JobInstanceBean> queryRealtimeInstances(Connection conn, String jobTitle, List<String> categorys, List<State> states, List<String> execNodes, String execUserTitle, String execUserGuid, Long startTime, Long endTime, int start, int end, String queryField1, String queryField2, String filter) throws SQLException {
        ArrayList<JobInstanceBean> instances = new ArrayList<JobInstanceBean>();
        int selectCount = end - start;
        ArrayList<Object> objects = new ArrayList<Object>();
        String sqlConditionString = JobInstancesDAO.generateQueryInstanceCondition(true, jobTitle, categorys, states, execNodes, execUserTitle, execUserGuid, startTime, endTime, queryField1, queryField2, filter, objects);
        String sql = SQL_QUERY_REALTIME_INSTANCES_BY_CONDITION + sqlConditionString;
        IDatabase db = DatabaseManager.getInstance().findDatabaseByConnection(conn);
        try {
            IPagingSQLBuilder pageBuilder = db.createPagingSQLBuilder();
            OrderField orderField1 = new OrderField("STATE_ORDER");
            orderField1.setOrderMode("DESC");
            pageBuilder.getOrderFields().add(orderField1);
            OrderField orderField2 = new OrderField("STATE_ENDTIME");
            orderField2.setOrderMode("DESC");
            pageBuilder.getOrderFields().add(orderField2);
            pageBuilder.setRawSQL(sql);
            try {
                String buildSQL = pageBuilder.buildSQL(start, end);
                try (PreparedStatement stmt = conn.prepareStatement(buildSQL);){
                    JobInstancesDAO.setParams(objects, stmt);
                    try (ResultSet rs = stmt.executeQuery();){
                        while (rs.next()) {
                            JobInstanceBean bean = JobInstancesDAO.fillBean(rs);
                            instances.add(bean);
                        }
                    }
                }
            }
            catch (DBException e) {
                SQLException sqle = new SQLException(e.getMessage());
                sqle.setStackTrace(e.getStackTrace());
                throw sqle;
            }
        }
        catch (UnsupportPagingException | SQLException e) {
            logger.warn("\u8be5\u6570\u636e\u5e93\u4e0d\u652f\u6301\u5206\u9875\uff0c\u5c06\u4f1a\u4ee5\u5168\u91cf\u67e5\u8be2\u7684\u65b9\u5f0f\u8fdb\u884c\u67e5\u8be2\uff0c\u6570\u636e\u91cf\u5927\u65f6\u5c06\u4f1a\u5f71\u54cd\u6027\u80fd", e);
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery();){
                int count = 0;
                while (rs.next()) {
                    if (++count > start && count <= end) {
                        JobInstanceBean bean = JobInstancesDAO.fillBean(rs);
                        instances.add(bean);
                        continue;
                    }
                    if (count <= end) continue;
                }
            }
        }
        if (instances.size() == selectCount) {
            return instances;
        }
        ArrayList<JobInstanceBean> resultInstances = new ArrayList<JobInstanceBean>();
        for (int i = 0; i < instances.size() && i < selectCount; ++i) {
            resultInstances.add((JobInstanceBean)instances.get(i));
        }
        return resultInstances;
    }

    public static int getTriggersCount(Connection conn) throws SQLException {
        String sql = "SELECT COUNT(1) FROM BI_Q_TRIGGERS WHERE TRIGGER_STATE = 'WAITING' AND TRIGGER_TYPE = 'SIMPLE'";
        try (PreparedStatement stmt = conn.prepareStatement(sql);){
            int n;
            block16: {
                ResultSet rs;
                block14: {
                    int n2;
                    block15: {
                        rs = stmt.executeQuery();
                        try {
                            if (!rs.next()) break block14;
                            n2 = rs.getInt(1);
                            if (rs == null) break block15;
                        }
                        catch (Throwable throwable) {
                            if (rs != null) {
                                try {
                                    rs.close();
                                }
                                catch (Throwable throwable2) {
                                    throwable.addSuppressed(throwable2);
                                }
                            }
                            throw throwable;
                        }
                        rs.close();
                    }
                    return n2;
                }
                n = 0;
                if (rs == null) break block16;
                rs.close();
            }
            return n;
        }
    }

    public static Map<String, Integer> getFiredTriggersCount(Connection conn) throws SQLException {
        String sql = "SELECT INSTANCE_NAME,COUNT(1) NUMS FROM BI_Q_FIRED_TRIGGERS  GROUP BY INSTANCE_NAME";
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);){
            HashMap<String, Integer> hashMap;
            block13: {
                ResultSet rs = stmt.executeQuery();
                try {
                    while (rs.next()) {
                        String name = rs.getString("INSTANCE_NAME");
                        int nums = rs.getInt("NUMS");
                        result.put(name == null ? "" : name, nums);
                    }
                    hashMap = result;
                    if (rs == null) break block13;
                }
                catch (Throwable throwable) {
                    if (rs != null) {
                        try {
                            rs.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                rs.close();
            }
            return hashMap;
        }
    }

    public static List<JobGroupByInfo> getAllJobGroupByInfo(Connection conn, List<String> categorys) throws SQLException {
        ArrayList<JobGroupByInfo> result = new ArrayList<JobGroupByInfo>();
        StringBuilder sqlBuilder = new StringBuilder();
        ArrayList<Object> params = new ArrayList<Object>();
        if (categorys != null && !categorys.isEmpty()) {
            StringBuilder categoryBuilder = new StringBuilder();
            for (int i = 0; i < categorys.size(); ++i) {
                if (i > 0) {
                    categoryBuilder.append(",");
                }
                categoryBuilder.append("?");
            }
            sqlBuilder.append(String.format(SQL_QUERY_INSTANCE_COUNT_GROUP_BY.replace("?CATEGORY", "%s"), categoryBuilder));
            params.addAll(categorys);
        }
        try (PreparedStatement stmt = conn.prepareStatement(sqlBuilder.toString());){
            ArrayList<JobGroupByInfo> arrayList;
            block16: {
                JobInstancesDAO.setParams(params, stmt);
                ResultSet rs = stmt.executeQuery();
                try {
                    while (rs.next()) {
                        JobGroupByInfo groupByInfo = new JobGroupByInfo();
                        groupByInfo.setCount(rs.getInt(1));
                        groupByInfo.setCategoryId(rs.getString(2));
                        groupByInfo.setCategoryTitle(rs.getString(3));
                        groupByInfo.setExecNode(rs.getString(4));
                        groupByInfo.setPublishNode(rs.getString(5));
                        groupByInfo.setState(rs.getInt(6));
                        result.add(groupByInfo);
                    }
                    arrayList = result;
                    if (rs == null) break block16;
                }
                catch (Throwable throwable) {
                    if (rs != null) {
                        try {
                            rs.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                rs.close();
            }
            return arrayList;
        }
    }

    public static List<JobInstanceBean> getLastJobInfo(Connection conn, List<String> categorys) throws SQLException {
        ResultSet rs;
        ArrayList<Object> params;
        ArrayList<JobInstanceBean> result = new ArrayList<JobInstanceBean>();
        StringBuilder sqlBuilder = new StringBuilder();
        ArrayList<String> paramsBase = new ArrayList<String>();
        StringBuilder publishBuilder = new StringBuilder();
        if (categorys != null && !categorys.isEmpty()) {
            StringBuilder categoryBuilder = new StringBuilder();
            for (int i = 0; i < categorys.size(); ++i) {
                if (i > 0) {
                    categoryBuilder.append(",");
                }
                categoryBuilder.append("?");
            }
            sqlBuilder.append(String.format(SQL_QUERY_LAST_SUCCESS_INSTANCE.replace("?CATEGORY", "%s"), categoryBuilder));
            publishBuilder.append(String.format(SQL_QUERY_LAST_PUBLISH_INSTANCE.replace("?CATEGORY", "%s"), categoryBuilder));
            paramsBase.addAll(categorys);
        }
        try (PreparedStatement stmt = conn.prepareStatement(sqlBuilder.toString());){
            params = new ArrayList<Object>();
            params.add(State.FINISHED.getValue());
            params.addAll(paramsBase);
            JobInstancesDAO.setParams(params, stmt);
            rs = stmt.executeQuery();
            try {
                while (rs.next()) {
                    result.add(JobInstancesDAO.fillBean(rs));
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
            }
        }
        stmt = conn.prepareStatement(publishBuilder.toString());
        try {
            params = new ArrayList();
            params.add(State.RUNNING.getValue());
            params.addAll(paramsBase);
            JobInstancesDAO.setParams(params, stmt);
            rs = stmt.executeQuery();
            try {
                while (rs.next()) {
                    JobInstanceBean jobInstanceBean = JobInstancesDAO.fillBean(rs);
                    jobInstanceBean.setState(State.RUNNING.getValue());
                    result.add(jobInstanceBean);
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
            }
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return result;
    }
}

