/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  com.jiuqi.bi.core.jobs.JobFactory
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.bi.core.jobs.extension.IJobClassifier
 *  com.jiuqi.bi.core.jobs.extension.IJobClassifier$ClassifierContext
 *  com.jiuqi.bi.core.jobs.extension.IJobClassifier$Path
 *  com.jiuqi.bi.core.jobs.extension.IJobListener
 *  com.jiuqi.bi.core.jobs.extension.JobListenerContext
 *  com.jiuqi.bi.core.jobs.extension.item.FolderItem
 *  com.jiuqi.bi.core.jobs.extension.item.JobItem
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.data.logic.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.extension.IJobClassifier;
import com.jiuqi.bi.core.jobs.extension.IJobListener;
import com.jiuqi.bi.core.jobs.extension.JobListenerContext;
import com.jiuqi.bi.core.jobs.extension.item.FolderItem;
import com.jiuqi.bi.core.jobs.extension.item.JobItem;
import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.data.logic.common.CommonUtils;
import com.jiuqi.nr.data.logic.internal.service.ICKRRecService;
import com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class BatchCheckResultCleanJob
extends JobFactory
implements IJobAdvanceConfiguration {
    private static final String JOB_ID = "cf651bea-c2d0-b94d-d79d-ccf650c2e4cc";
    private static final String JOB_TITLE = "\u6279\u91cf\u5ba1\u6838\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664";
    private static final String JOB_CONFIG_PAGE = "job-cleanCkr";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final int CLEAN_DAYS_DEFAULT = 90;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ITempTableManager tempTableManager;
    @Autowired
    private NrdbHelper nrdbHelper;
    @Autowired
    private INvwaDataAccessProvider dataAccessProvider;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private ICKRRecService ckrRecService;
    private static final Logger logger = LoggerFactory.getLogger(BatchCheckResultCleanJob.class);

    public String getJobCategoryId() {
        return JOB_ID;
    }

    public String getJobCategoryTitle() {
        return JOB_TITLE;
    }

    public JobExecutor createJobExecutor(String s) throws JobsException {
        return new BatchCheckResultCleanExecutor();
    }

    public IJobClassifier getJobClassifier() {
        return new BatchCheckResultCleanJobClassifier();
    }

    public IJobListener getJobListener() {
        return new BatchCheckResultCleanJobListener();
    }

    public String getModelName() {
        return JOB_CONFIG_PAGE;
    }

    private static void executeInSql(String ckrTableName, List<String> delBatchIds, DataSource dataSource, String formSchemeCode) {
        StringBuilder sql = new StringBuilder(String.format("delete from %s where %s in (", ckrTableName, "CKR_BATCH_ID"));
        for (String ignored : delBatchIds) {
            sql.append("?,");
        }
        sql.setLength(sql.length() - 1);
        sql.append(")");
        try (Connection conn = DataSourceUtils.getConnection((DataSource)dataSource);){
            DataEngineUtil.executeUpdate((Connection)conn, (String)sql.toString(), (Object[])delBatchIds.toArray(new String[0]));
        }
        catch (Exception e) {
            logger.error(formSchemeCode + "\u62a5\u8868\u65b9\u6848\u6279\u91cf\u5ba1\u6838\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664\u5f02\u5e38:" + e.getMessage(), e);
        }
    }

    private void executeUseTempTable(List<String> delBatchIds, String ckrTableName, String formSchemeCode) {
        try (ITempTable tempTable = this.tempTableManager.getOneKeyTempTable();){
            ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
            for (String delBatchId : delBatchIds) {
                Object[] batchArray = new Object[]{delBatchId};
                batchValues.add(batchArray);
            }
            tempTable.insertRecords(batchValues);
            this.jdbcTemplate.execute(String.format("delete from %s where %s in (%s)", ckrTableName, "CKR_BATCH_ID", CommonUtils.getSelectSql(tempTable)));
        }
        catch (Exception e) {
            logger.error(formSchemeCode + "\u62a5\u8868\u65b9\u6848\u6279\u91cf\u5ba1\u6838\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664\u5f02\u5e38:" + e.getMessage(), e);
        }
    }

    static class JobParam {
        private int cleanDays = -1;

        JobParam() {
        }

        public int getCleanDays() {
            return this.cleanDays;
        }

        public void setCleanDays(int cleanDays) {
            this.cleanDays = cleanDays;
        }
    }

    static class BatchCheckResultCleanJobListener
    implements IJobListener {
        BatchCheckResultCleanJobListener() {
        }

        public void beforeJobDelete(JobListenerContext jobListenerContext) throws Exception {
        }

        public void afterJobDelete(JobListenerContext jobListenerContext) throws Exception {
        }
    }

    static class BatchCheckResultCleanJobClassifier
    implements IJobClassifier {
        BatchCheckResultCleanJobClassifier() {
        }

        public List<FolderItem> getFolders(String s, IJobClassifier.ClassifierContext classifierContext) throws Exception {
            return null;
        }

        public List<JobItem> getItems(String s, IJobClassifier.ClassifierContext classifierContext) throws Exception {
            return null;
        }

        public JobItem getJobItem(String s) throws Exception {
            return null;
        }

        public IJobClassifier.Path locate(FolderItem folderItem) throws Exception {
            return null;
        }

        public IJobClassifier.Path locate(JobItem jobItem) throws Exception {
            return null;
        }
    }

    class BatchCheckResultCleanExecutor
    extends JobExecutor {
        BatchCheckResultCleanExecutor() {
        }

        public void execute(JobContext jobContext) throws JobExecutionException {
            int cleanDays = this.getCleanDays(jobContext);
            long exeTime = System.currentTimeMillis();
            long cleanTime = exeTime - (long)cleanDays * 24L * 3600L * 1000L;
            DataSource dataSource = BatchCheckResultCleanJob.this.jdbcTemplate.getDataSource();
            assert (dataSource != null) : "\u83b7\u53d6\u6570\u636e\u6e90\u5f02\u5e38";
            this.cleanRecord(cleanTime);
            int maxInSize = this.getMaxInSize(dataSource);
            List allTaskDefines = BatchCheckResultCleanJob.this.runTimeViewController.getAllTaskDefines();
            if (!CollectionUtils.isEmpty(allTaskDefines)) {
                for (TaskDefine taskDefine : allTaskDefines) {
                    try {
                        List formSchemeDefines = BatchCheckResultCleanJob.this.runTimeViewController.queryFormSchemeByTask(taskDefine.getKey());
                        if (CollectionUtils.isEmpty(formSchemeDefines)) continue;
                        List<String> delBatchIds = BatchCheckResultCleanJob.this.ckrRecService.getBatchIdsBeforeTime(cleanTime);
                        if (CollectionUtils.isEmpty(delBatchIds)) {
                            return;
                        }
                        for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                            String formSchemeCode = formSchemeDefine.getFormSchemeCode();
                            String ckrTableName = CheckTableNameUtil.getCKRTableName(formSchemeCode);
                            if (BatchCheckResultCleanJob.this.nrdbHelper.isEnableNrdb()) {
                                NvwaQueryModel queryModel = new NvwaQueryModel();
                                TableModelDefine tableModel = BatchCheckResultCleanJob.this.dataModelService.getTableModelDefineByName(ckrTableName);
                                List columnModelDefinesByTable = BatchCheckResultCleanJob.this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
                                columnModelDefinesByTable.forEach(o -> queryModel.getColumns().add(new NvwaQueryColumn(o)));
                                ColumnModelDefine recidCol = BatchCheckResultCleanJob.this.dataModelService.getColumnModelDefineByCode(tableModel.getID(), "CKR_RECID");
                                queryModel.getColumnFilters().put(recidCol, delBatchIds);
                                INvwaUpdatableDataAccess updatableDataAccess = BatchCheckResultCleanJob.this.dataAccessProvider.createUpdatableDataAccess(queryModel);
                                DataAccessContext context = new DataAccessContext(BatchCheckResultCleanJob.this.dataModelService);
                                INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(context);
                                dataUpdator.deleteAll();
                                dataUpdator.commitChanges(context);
                                logger.info("{}\u62a5\u8868\u65b9\u6848\u6279\u91cf\u5ba1\u6838\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664\u6210\u529f", (Object)formSchemeCode);
                                continue;
                            }
                            if (CollectionUtils.isEmpty(delBatchIds)) continue;
                            if (delBatchIds.size() >= maxInSize) {
                                BatchCheckResultCleanJob.this.executeUseTempTable(delBatchIds, ckrTableName, formSchemeCode);
                            } else {
                                BatchCheckResultCleanJob.executeInSql(ckrTableName, delBatchIds, dataSource, formSchemeCode);
                            }
                            logger.info("{}\u62a5\u8868\u65b9\u6848\u6279\u91cf\u5ba1\u6838\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664\u6210\u529f", (Object)formSchemeCode);
                        }
                    }
                    catch (Exception e) {
                        logger.error(taskDefine.getKey() + "\u4efb\u52a1\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u5f02\u5e38:" + e.getMessage(), e);
                    }
                }
            }
            BatchCheckResultCleanJob.this.ckrRecService.deleteBeforeStartTime(cleanTime);
        }

        private int getCleanDays(JobContext jobContext) throws JobExecutionException {
            JobParam jobParam;
            String runnerParameter = jobContext.getJob().getExtendedConfig();
            if (!StringUtils.hasText(runnerParameter)) {
                logger.warn("\u6279\u91cf\u5ba1\u6838\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664\u8ba1\u5212\u4efb\u52a1\u672a\u914d\u7f6e\u6e05\u9664\u8303\u56f4\uff0c\u5c06\u4f7f\u7528\u9ed8\u8ba4\u914d\u7f6e\uff1a\u6e05\u9664\u5ba1\u6838\u6267\u884c\u65f6\u95f4\u5728{}\u5929\u4e4b\u524d", (Object)90);
                jobContext.getDefaultLogger().warn("\u6279\u91cf\u5ba1\u6838\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664\u8ba1\u5212\u4efb\u52a1\u672a\u914d\u7f6e\u6e05\u9664\u8303\u56f4\uff0c\u5c06\u4f7f\u7528\u9ed8\u8ba4\u914d\u7f6e\uff1a\u6e05\u9664\u5ba1\u6838\u6267\u884c\u65f6\u95f4\u572890\u5929\u4e4b\u524d");
                return 90;
            }
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                jobParam = (JobParam)objectMapper.readValue(runnerParameter, JobParam.class);
            }
            catch (JsonProcessingException e) {
                logger.error("\u6279\u91cf\u5ba1\u6838\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664\u8ba1\u5212\u4efb\u52a1\u53cd\u5e8f\u5217\u5316\u53c2\u6570\u5f02\u5e38:{}", (Object)e.getMessage(), (Object)e);
                jobContext.getDefaultLogger().error("\u6279\u91cf\u5ba1\u6838\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664\u8ba1\u5212\u4efb\u52a1\u53cd\u5e8f\u5217\u5316\u53c2\u6570\u5f02\u5e38:" + e.getMessage(), (Throwable)e);
                throw new JobExecutionException("\u6279\u91cf\u5ba1\u6838\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664\u8ba1\u5212\u4efb\u52a1\u53cd\u5e8f\u5217\u5316\u53c2\u6570\u5f02\u5e38:" + e.getMessage(), (Throwable)e);
            }
            if (jobParam == null || jobParam.getCleanDays() < 0) {
                logger.error("\u6279\u91cf\u5ba1\u6838\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664\u8ba1\u5212\u4efb\u52a1\u914d\u7f6e\u9519\u8bef:{}", (Object)runnerParameter);
                jobContext.getDefaultLogger().error("\u6279\u91cf\u5ba1\u6838\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664\u8ba1\u5212\u4efb\u52a1\u914d\u7f6e\u9519\u8bef:" + runnerParameter);
                throw new JobExecutionException("\u6279\u91cf\u5ba1\u6838\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664\u8ba1\u5212\u4efb\u52a1\u914d\u7f6e\u9519\u8bef:" + runnerParameter);
            }
            return jobParam.getCleanDays();
        }

        private void cleanRecord(long cleanTime) {
            String sql = String.format("delete from %s where %s<?", "NR_PARAM_REVIEW_INFO", "RWIF_DATETIME");
            BatchCheckResultCleanJob.this.jdbcTemplate.update(sql, new Object[]{cleanTime});
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private int getMaxInSize(DataSource dataSource) {
            try (Connection conn = DataSourceUtils.getConnection((DataSource)dataSource);){
                IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
                int n = DataEngineUtil.getMaxInSize((IDatabase)database);
                return n;
            }
            catch (SQLException e) {
                logger.error("\u83b7\u53d6\u6570\u636e\u5e93\u4fe1\u606f\u5f02\u5e38:" + e.getMessage(), e);
                return -1;
            }
        }
    }
}

