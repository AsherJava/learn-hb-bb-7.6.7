/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.data.logic.schedule;

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
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class AllCheckResultCleanJob
extends JobFactory {
    private static final String JOB_ID = "a8c6eb7d-12ce-972a-2b93-1ec05e10fa88";
    private static final String JOB_TITLE = "\u5168\u5ba1\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private NrdbHelper nrdbHelper;
    @Autowired
    private INvwaDataAccessProvider dataAccessProvider;
    @Autowired
    private DataModelService dataModelService;
    private static final Logger logger = LoggerFactory.getLogger(AllCheckResultCleanJob.class);

    public String getJobCategoryId() {
        return JOB_ID;
    }

    public String getJobCategoryTitle() {
        return JOB_TITLE;
    }

    public JobExecutor createJobExecutor(String s) throws JobsException {
        return new AllCheckResultCleanExecutor();
    }

    public IJobClassifier getJobClassifier() {
        return new AllCheckResultCleanJobClassifier();
    }

    public IJobListener getJobListener() {
        return new AllCheckResultCleanJobListener();
    }

    private void executeByFormScheme(FormSchemeDefine formSchemeDefine) {
        String formSchemeCode = formSchemeDefine.getFormSchemeCode();
        String allCKRTableName = CheckTableNameUtil.getAllCKRTableName(formSchemeCode);
        try {
            if (this.nrdbHelper.isEnableNrdb()) {
                NvwaQueryModel queryModel = new NvwaQueryModel();
                TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(allCKRTableName);
                List columnModelDefinesByTable = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
                columnModelDefinesByTable.forEach(o -> queryModel.getColumns().add(new NvwaQueryColumn(o)));
                INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
                DataAccessContext context = new DataAccessContext(this.dataModelService);
                INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(context);
                dataUpdator.deleteAll();
                dataUpdator.commitChanges(context);
            } else {
                this.jdbcTemplate.execute("TRUNCATE TABLE " + allCKRTableName);
            }
            logger.info("{}\u62a5\u8868\u65b9\u6848\u5168\u5ba1\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664\u6210\u529f", (Object)formSchemeCode);
        }
        catch (Exception e) {
            logger.error(formSchemeCode + "\u62a5\u8868\u65b9\u6848\u5168\u5ba1\u7ed3\u679c\u8868\u6570\u636e\u6e05\u9664\u5931\u8d25:" + e.getMessage(), e);
        }
    }

    static class AllCheckResultCleanJobListener
    implements IJobListener {
        AllCheckResultCleanJobListener() {
        }

        public void beforeJobDelete(JobListenerContext jobListenerContext) throws Exception {
        }

        public void afterJobDelete(JobListenerContext jobListenerContext) throws Exception {
        }
    }

    static class AllCheckResultCleanJobClassifier
    implements IJobClassifier {
        AllCheckResultCleanJobClassifier() {
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

    class AllCheckResultCleanExecutor
    extends JobExecutor {
        AllCheckResultCleanExecutor() {
        }

        public void execute(JobContext jobContext) throws JobExecutionException {
            DataSource dataSource = AllCheckResultCleanJob.this.jdbcTemplate.getDataSource();
            assert (dataSource != null) : "\u83b7\u53d6\u6570\u636e\u6e90\u5f02\u5e38";
            List allTaskDefines = AllCheckResultCleanJob.this.runTimeViewController.getAllTaskDefines();
            if (!CollectionUtils.isEmpty(allTaskDefines)) {
                List taskKeys = allTaskDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
                for (String taskKey : taskKeys) {
                    try {
                        List formSchemeDefines = AllCheckResultCleanJob.this.runTimeViewController.queryFormSchemeByTask(taskKey);
                        if (CollectionUtils.isEmpty(formSchemeDefines)) continue;
                        for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                            AllCheckResultCleanJob.this.executeByFormScheme(formSchemeDefine);
                        }
                    }
                    catch (Exception e) {
                        logger.error(taskKey + "\u4efb\u52a1\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u5f02\u5e38:" + e.getMessage(), e);
                    }
                }
            }
        }
    }
}

