/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.definition.upgrade;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.util.OrderGenerator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class TaskLinkOrgMappingUpdate
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(TaskLinkOrgMappingUpdate.class);
    private static final String TASK_LINK_TABLE_DES = "NR_PARAM_TASKLINK_DES";
    private static final String TASK_LINK_TABLE = "NR_PARAM_TASKLINK";
    private static final String TASK_LINK_KEY = "TR_KEY";
    private static final String TASK_LINK_SOURCE_FC = "TR_RELATED_FORM_SCHEME_KEY";
    private static final String TASK_LINK_TARGET_FC = "TR_CURRENT_FORM_SCHEME_KEY";
    private static final String TASK_LINK_SOURCE_TK_CODE = "TR_RELATED_TASK_CODE";
    private static final String TASK_LINK_MATCHING_TYPE = "TR_MATCHINGTYPE";
    private static final String TASK_LINK_TARGET_FORMULA = "TR_CURRENT_FORMULA";
    private static final String TASK_LINK_SOURCE_FORMULA = "TR_RELATED_FORMULA";
    private static final String TASK_LINK_EXPRESSION_TYPE = "TR_EXPRESSIONTYPE";
    private static final String TASK_LINK_INFO_QUERY_SQL = "SELECT TR_KEY,TR_RELATED_FORM_SCHEME_KEY,TR_CURRENT_FORM_SCHEME_KEY,TR_RELATED_TASK_CODE,TR_MATCHINGTYPE,TR_CURRENT_FORMULA,TR_RELATED_FORMULA,TR_EXPRESSIONTYPE FROM ";
    private static final String TASK_TABLE = "NR_PARAM_TASK";
    private static final String TASK_CODE_KEY = "TK_CODE";
    private static final String TASK_KEY = "TK_KEY";
    private static final String TASK_DW_KEY = "TK_DW";
    private static final String QUERY_TASK_DW_SQL = "SELECT TK_KEY,TK_DW FROM NR_PARAM_TASK";
    private static final String QUERY_TASK_KEY_BY_CODE_SQL = "SELECT TK_KEY FROM NR_PARAM_TASK WHERE TK_CODE = ? ";
    private static final String FORM_SCHEME_TABLE_DES = "NR_PARAM_FORMSCHEME_DES";
    private static final String FORM_SCHEME_TASK_KEY = "FC_TASK_KEY";
    private static final String FORM_SCHEME_KEY = "FC_KEY";
    private static final String QUERY_TASK_BY_FC_SQL_DES = "SELECT FC_TASK_KEY FROM NR_PARAM_FORMSCHEME_DES WHERE FC_KEY = ? ";
    private static final String TASK_LINK_ORG_MAPPING_TABLE_DES = "NR_PARAM_TASKLINK_ORG_DES";
    private static final String TASK_LINK_ORG_MAPPING_TABLE = "NR_PARAM_TASKLINK_ORG";
    private static final String TASK_LINK_ORG_MAPPING_INSERT_SQL_DES = "INSERT INTO NR_PARAM_TASKLINK_ORG_DES values (?,?,?,?,?,?,?,?)";
    private static final String TASK_LINK_ORG_MAPPING_INSERT_SQL = "INSERT INTO NR_PARAM_TASKLINK_ORG values (?,?,?,?,?,?,?,?)";
    public static Map<String, String> taskAndDwMap = new HashMap<String, String>();

    public void execute(DataSource dataSource) throws Exception {
        logger.info("\u5f00\u59cb\u5347\u7ea7\u5173\u8054\u4efb\u52a1\uff0c\u652f\u6301\u591a\u53e3\u5f84\u4efb\u52a1\u914d\u7f6e\u5173\u8054\u4efb\u52a1");
        logger.info("\u5f00\u59cb\u5347\u7ea7\u5173\u8054\u4efb\u52a1\u8bbe\u8ba1\u671f\u8868");
        Map<String, TaskLinkInfo> desTaskLinkInfos = this.getTaskLinkInfos(dataSource.getConnection(), true);
        this.appendTaskDW(dataSource.getConnection(), desTaskLinkInfos);
        this.doInsert(dataSource.getConnection(), desTaskLinkInfos, true);
        logger.info("\u5173\u8054\u4efb\u52a1\u8bbe\u8ba1\u671f\u8868\u5347\u7ea7\u5b8c\u6210");
        logger.info("\u5f00\u59cb\u5347\u7ea7\u5173\u8054\u4efb\u52a1\u8fd0\u884c\u671f\u8868");
        Map<String, TaskLinkInfo> runTaskLinkInfos = this.getTaskLinkInfos(dataSource.getConnection(), false);
        this.appendTaskDW(dataSource.getConnection(), runTaskLinkInfos);
        this.doInsert(dataSource.getConnection(), runTaskLinkInfos, false);
        logger.info("\u5173\u8054\u4efb\u52a1\u8fd0\u884c\u671f\u8868\u5347\u7ea7\u5b8c\u6210");
        logger.info("\u5173\u8054\u4efb\u52a1\u5347\u7ea7\u5b8c\u6210");
    }

    public Map<String, TaskLinkInfo> getTaskLinkInfos(Connection connection, boolean isDes) {
        HashMap<String, TaskLinkInfo> taskLinkInfoMap = new HashMap<String, TaskLinkInfo>();
        String executeSql = isDes ? "SELECT TR_KEY,TR_RELATED_FORM_SCHEME_KEY,TR_CURRENT_FORM_SCHEME_KEY,TR_RELATED_TASK_CODE,TR_MATCHINGTYPE,TR_CURRENT_FORMULA,TR_RELATED_FORMULA,TR_EXPRESSIONTYPE FROM NR_PARAM_TASKLINK_DES" : "SELECT TR_KEY,TR_RELATED_FORM_SCHEME_KEY,TR_CURRENT_FORM_SCHEME_KEY,TR_RELATED_TASK_CODE,TR_MATCHINGTYPE,TR_CURRENT_FORMULA,TR_RELATED_FORMULA,TR_EXPRESSIONTYPE FROM NR_PARAM_TASKLINK";
        try (PreparedStatement preparedStatement = connection.prepareStatement(executeSql);
             ResultSet resultSet = preparedStatement.executeQuery();){
            while (resultSet.next()) {
                String taskLinkKey = resultSet.getString(TASK_LINK_KEY);
                TaskLinkInfo taskLinkInfo = new TaskLinkInfo();
                taskLinkInfo.setTaskLinkKey(taskLinkKey);
                taskLinkInfo.setSourceFormSchemeKey(resultSet.getString(TASK_LINK_SOURCE_FC));
                taskLinkInfo.setTargetFormSchemeKey(resultSet.getString(TASK_LINK_TARGET_FC));
                taskLinkInfo.setSourceTaskCode(resultSet.getString(TASK_LINK_SOURCE_TK_CODE));
                taskLinkInfo.setSourceFormula(resultSet.getString(TASK_LINK_SOURCE_FORMULA));
                taskLinkInfo.setTargetFormula(resultSet.getString(TASK_LINK_TARGET_FORMULA));
                taskLinkInfo.setMatchingType(resultSet.getInt(TASK_LINK_MATCHING_TYPE));
                taskLinkInfo.setExpressionType(resultSet.getInt(TASK_LINK_EXPRESSION_TYPE));
                taskLinkInfoMap.put(taskLinkKey, taskLinkInfo);
            }
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5173\u8054\u4efb\u52a1\u4fe1\u606f\u5931\u8d25", e);
        }
        return taskLinkInfoMap;
    }

    public void appendTaskDW(Connection connection, Map<String, TaskLinkInfo> taskLinkInfoMap) {
        if (taskAndDwMap.isEmpty()) {
            this.buildTaskAndDwMap(connection);
        }
        for (String taskLinkInfoKey : taskLinkInfoMap.keySet()) {
            TaskLinkInfo taskLinkInfo = taskLinkInfoMap.get(taskLinkInfoKey);
            String sourceTaskKey = StringUtils.hasText(taskLinkInfo.getSourceFormSchemeKey()) ? this.getTaskKeyByFCKey(connection, taskLinkInfo.getSourceFormSchemeKey()) : this.getTaskKeyByTKCode(connection, taskLinkInfo.getSourceTaskCode());
            String targetTaskKey = this.getTaskKeyByFCKey(connection, taskLinkInfo.getTargetFormSchemeKey());
            if (taskAndDwMap.get(sourceTaskKey) == null || taskAndDwMap.get(targetTaskKey) == null) continue;
            taskLinkInfo.setSourceEntity(taskAndDwMap.get(sourceTaskKey));
            taskLinkInfo.setTargetEntity(taskAndDwMap.get(targetTaskKey));
        }
    }

    private String getTaskKeyByFCKey(Connection connection, String formSchemeKey) {
        String taskKey = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_TASK_BY_FC_SQL_DES);){
            preparedStatement.setString(1, formSchemeKey);
            try (ResultSet resultSet = preparedStatement.executeQuery();){
                while (resultSet.next()) {
                    taskKey = resultSet.getString(FORM_SCHEME_TASK_KEY);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u4efb\u52a1\u4e3b\u952e\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570", e);
        }
        return taskKey;
    }

    private String getTaskKeyByTKCode(Connection connection, String taskCode) {
        String taskKey = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_TASK_KEY_BY_CODE_SQL);){
            preparedStatement.setString(1, taskCode);
            try (ResultSet resultSet = preparedStatement.executeQuery();){
                while (resultSet.next()) {
                    taskKey = resultSet.getString(TASK_KEY);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u4efb\u52a1\u4e3b\u952e\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570", e);
        }
        return taskKey;
    }

    private void buildTaskAndDwMap(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_TASK_DW_SQL);
             ResultSet resultSet = preparedStatement.executeQuery();){
            while (resultSet.next()) {
                taskAndDwMap.put(resultSet.getString(TASK_KEY), resultSet.getString(TASK_DW_KEY));
            }
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u4efb\u52a1\u4e3b\u952e\u53ca\u4e3b\u7ef4\u5ea6\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570", e);
        }
    }

    public void doInsert(Connection connection, Map<String, TaskLinkInfo> orgMappingMap, boolean isDes) {
        String executeSQL = isDes ? TASK_LINK_ORG_MAPPING_INSERT_SQL_DES : TASK_LINK_ORG_MAPPING_INSERT_SQL;
        try (PreparedStatement preparedStatement = connection.prepareStatement(executeSQL);){
            for (Map.Entry<String, TaskLinkInfo> taskLinkInfo : orgMappingMap.entrySet()) {
                String sourceEntity = taskLinkInfo.getValue().getSourceEntity();
                String targetEntity = taskLinkInfo.getValue().getTargetEntity();
                if (!StringUtils.hasText(sourceEntity) || !StringUtils.hasText(targetEntity)) continue;
                logger.info("\u5347\u7ea7\u5173\u8054\u4efb\u52a1[" + taskLinkInfo.getKey() + "]");
                logger.info("\u6765\u6e90\u4e3b\u7ef4\u5ea6\u4e3a[" + taskLinkInfo.getValue().getSourceEntity() + "]");
                logger.info("\u76ee\u6807\u4e3b\u7ef4\u5ea6\u4e3a[" + taskLinkInfo.getValue().getTargetEntity() + "]");
                preparedStatement.setString(1, taskLinkInfo.getValue().getTaskLinkKey());
                preparedStatement.setString(2, sourceEntity);
                preparedStatement.setString(3, targetEntity);
                preparedStatement.setInt(4, taskLinkInfo.getValue().getMatchingType());
                preparedStatement.setString(5, taskLinkInfo.getValue().getTargetFormula());
                preparedStatement.setString(6, taskLinkInfo.getValue().getSourceFormula());
                preparedStatement.setInt(7, taskLinkInfo.getValue().getExpressionType());
                preparedStatement.setString(8, OrderGenerator.newOrder());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
        catch (Exception e) {
            logger.error("\u5173\u8054\u4efb\u52a1\u5347\u7ea7\u5931\u8d25", e);
            logger.info("\u9700\u8981\u65b0\u589e\u7684\u8bb0\u5f55\u6709:" + orgMappingMap.values().toString());
        }
    }

    class TaskLinkInfo {
        private String taskLinkKey;
        private String sourceFormSchemeKey;
        private String sourceTaskCode;
        private String targetFormSchemeKey;
        private String sourceEntity;
        private String targetEntity;
        private Integer matchingType;
        private String targetFormula;
        private String sourceFormula;
        private Integer expressionType;

        TaskLinkInfo() {
        }

        public String getTaskLinkKey() {
            return this.taskLinkKey;
        }

        public void setTaskLinkKey(String taskLinkKey) {
            this.taskLinkKey = taskLinkKey;
        }

        public String getSourceTaskCode() {
            return this.sourceTaskCode;
        }

        public void setSourceTaskCode(String sourceTaskCode) {
            this.sourceTaskCode = sourceTaskCode;
        }

        public String getSourceFormSchemeKey() {
            return this.sourceFormSchemeKey;
        }

        public void setSourceFormSchemeKey(String sourceFormSchemeKey) {
            this.sourceFormSchemeKey = sourceFormSchemeKey;
        }

        public String getTargetFormSchemeKey() {
            return this.targetFormSchemeKey;
        }

        public void setTargetFormSchemeKey(String targetFormSchemeKey) {
            this.targetFormSchemeKey = targetFormSchemeKey;
        }

        public String getSourceEntity() {
            return this.sourceEntity;
        }

        public void setSourceEntity(String sourceEntity) {
            this.sourceEntity = sourceEntity;
        }

        public String getTargetEntity() {
            return this.targetEntity;
        }

        public void setTargetEntity(String targetEntity) {
            this.targetEntity = targetEntity;
        }

        public Integer getMatchingType() {
            return this.matchingType;
        }

        public void setMatchingType(Integer matchingType) {
            this.matchingType = matchingType;
        }

        public String getTargetFormula() {
            return this.targetFormula;
        }

        public void setTargetFormula(String targetFormula) {
            this.targetFormula = targetFormula;
        }

        public String getSourceFormula() {
            return this.sourceFormula;
        }

        public void setSourceFormula(String sourceFormula) {
            this.sourceFormula = sourceFormula;
        }

        public Integer getExpressionType() {
            return this.expressionType;
        }

        public void setExpressionType(Integer expressionType) {
            this.expressionType = expressionType;
        }
    }
}

