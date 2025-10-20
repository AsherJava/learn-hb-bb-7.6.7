/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.period.util.JacksonUtils
 */
package com.jiuqi.nr.definition.upgrade;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.definition.option.core.TaskOption;
import com.jiuqi.nr.definition.option.dto.AuditSchemeDTO;
import com.jiuqi.nr.period.util.JacksonUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuditSchemeOptionUpdate
implements CustomClassExecutor {
    private final Logger log = LoggerFactory.getLogger(AuditSchemeOptionUpdate.class);
    public static final String TABLE_NAME = "NR_TASK_DEFINE_OPTION";
    public static final String SO_KEY = "SO_KEY";
    public static final String SO_TASK_KEY = "SO_TASK_KEY";
    public static final String SO_VALUE = "SO_VALUE";
    public static final String COL = "SO_KEY,SO_TASK_KEY,SO_VALUE";
    private static final String SQL_QUERY = String.format("SELECT %s FROM %s", "SO_KEY,SO_TASK_KEY,SO_VALUE", "NR_TASK_DEFINE_OPTION");
    private static final String INSERT = String.format("INSERT INTO %s VALUES (?,?,?)", "NR_TASK_DEFINE_OPTION");
    private static final String DELETE = String.format("DELETE FROM %s WHERE SO_KEY = ?", "NR_TASK_DEFINE_OPTION");

    public void execute(DataSource dataSource) throws Exception {
        block14: {
            this.log.info("\u4efb\u52a1\u9009\u9879\u4e0a\u62a5\u524d\u5ba1\u6838\u7c7b\u578b\u4e2a\u6027\u5316\u8bbe\u7f6e\u5347\u7ea7");
            Connection conn = null;
            try (Connection connection = dataSource.getConnection();){
                conn = connection;
                connection.setAutoCommit(false);
                List<TaskOption> taskOptions = this.getAllTaskOption(connection);
                List<AuditType> auditTypes = this.getAllAuditTypes(connection);
                List<TaskOption> newTaskOptions = this.merge(taskOptions, auditTypes);
                this.deleteOldOption(connection);
                this.insertNewOption(connection, newTaskOptions);
                connection.commit();
                this.log.info("\u4efb\u52a1\u9009\u9879\u4e0a\u62a5\u524d\u5ba1\u6838\u7c7b\u578b\u4e2a\u6027\u5316\u8bbe\u7f6e\u5347\u7ea7\u5b8c\u6210!");
            }
            catch (Exception e) {
                this.log.info("\u4efb\u52a1\u9009\u9879\u4e0a\u62a5\u524d\u5ba1\u6838\u7c7b\u578b\u4e2a\u6027\u5316\u8bbe\u7f6e\u5347\u7ea7\u5931\u8d25!");
                if (conn == null) break block14;
                conn.rollback();
            }
        }
    }

    private void insertNewOption(Connection connection, List<TaskOption> newTaskOptions) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT);){
            for (TaskOption option : newTaskOptions) {
                statement.setString(1, option.getKey());
                statement.setString(2, option.getTaskKey());
                statement.setString(3, option.getValue());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void deleteOldOption(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE);){
            statement.setString(1, "REVIEW_CONDITION");
            statement.addBatch();
            statement.setString(1, "REVIEW_ERROR");
            statement.addBatch();
            statement.setString(1, "REVIEW_WARN");
            statement.addBatch();
            statement.setString(1, "REVIEW_TIP");
            statement.addBatch();
            statement.executeBatch();
        }
        this.log.info("\u4efb\u52a1\u9009\u9879\u4e0a\u62a5\u524d\u5ba1\u6838\u7c7b\u578b\u4e2a\u6027\u5316\u8bbe\u7f6e\u5347\u7ea7, \u5220\u9664\u65e7\u4efb\u52a1\u9009\u9879");
    }

    /*
     * Exception decompiling
     */
    private List<AuditType> getAllAuditTypes(Connection connection) throws SQLException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private List<TaskOption> merge(List<TaskOption> taskOptions, List<AuditType> auditTypes) {
        ArrayList<TaskOption> res = new ArrayList<TaskOption>(5);
        Map<String, List<TaskOption>> map = taskOptions.stream().collect(Collectors.groupingBy(TaskOption::getTaskKey));
        for (Map.Entry<String, List<TaskOption>> entry : map.entrySet()) {
            Map keyValueMap = entry.getValue().stream().filter(x -> this.isAuditSchemeOldKey(x.getKey())).collect(HashMap::new, (m, v) -> m.put(v.getKey(), v.getValue()), (map1, map2) -> map1.putAll(map2));
            ArrayList<AuditSchemeDTO> list = new ArrayList<AuditSchemeDTO>(5);
            list.add(new AuditSchemeDTO("AUDIT_SCHEME_CONDITION", "\u9002\u7528\u6761\u4ef6", keyValueMap.getOrDefault("REVIEW_CONDITION", "")));
            block6: for (AuditType type : auditTypes) {
                switch (type.getType()) {
                    case 1: {
                        list.add(new AuditSchemeDTO(type.getCode(), type.getTitle(), keyValueMap.getOrDefault("REVIEW_TIP", "2")));
                        continue block6;
                    }
                    case 2: {
                        list.add(new AuditSchemeDTO(type.getCode(), type.getTitle(), keyValueMap.getOrDefault("REVIEW_WARN", "2")));
                        continue block6;
                    }
                    case 4: {
                        list.add(new AuditSchemeDTO(type.getCode(), type.getTitle(), keyValueMap.getOrDefault("REVIEW_ERROR", "2")));
                        continue block6;
                    }
                }
                list.add(new AuditSchemeDTO(type.getCode(), type.getTitle(), "2"));
            }
            TaskOption auditSchemeOption = new TaskOption();
            auditSchemeOption.setKey("AUDIT_SCHEME");
            auditSchemeOption.setTaskKey(entry.getKey());
            auditSchemeOption.setValue(JacksonUtils.objectToJson(list));
            res.add(auditSchemeOption);
        }
        return res;
    }

    private List<TaskOption> getAllTaskOption(Connection connection) throws SQLException {
        ArrayList<TaskOption> taskOptions = new ArrayList<TaskOption>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_QUERY);
             ResultSet resultSet = statement.executeQuery();){
            while (resultSet.next()) {
                String key = resultSet.getString(SO_KEY);
                String taskKey = resultSet.getString(SO_TASK_KEY);
                String value = resultSet.getString(SO_VALUE);
                taskOptions.add(new TaskOption(taskKey, key, value));
            }
        }
        return taskOptions;
    }

    private boolean isAuditSchemeOldKey(String key) {
        return "REVIEW_CONDITION".equals(key) || "REVIEW_TIP".equals(key) || "REVIEW_WARN".equals(key) || "REVIEW_ERROR".equals(key);
    }

    private static class AuditType {
        private int type;
        private String code;
        private String title;
        private String order;

        public AuditType(String code, String title, String order) {
            this.code = code;
            this.title = title;
            this.order = order;
            this.type = "1".equals(code) || "\u63d0\u793a\u578b".equals(title) ? 1 : ("2".equals(code) || "\u8b66\u544a\u578b".equals(title) ? 2 : ("4".equals(code) || "\u9519\u8bef\u578b".equals(title) ? 4 : 5));
        }

        public String getOrder() {
            return this.order;
        }

        public int getType() {
            return this.type;
        }

        public String getCode() {
            return this.code;
        }

        public String getTitle() {
            return this.title;
        }
    }
}

