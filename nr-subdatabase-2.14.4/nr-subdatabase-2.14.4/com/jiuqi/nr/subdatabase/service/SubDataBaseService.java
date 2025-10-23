/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.metadata.LogicPrimaryKey
 *  com.jiuqi.bi.database.metadata.LogicTable
 *  com.jiuqi.bi.database.statement.SqlStatement
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.internal.service.impl.RuntimeDataSchemeServiceImpl
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  com.jiuqi.util.OrderGenerator
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.organization.service.OrgCategoryService
 */
package com.jiuqi.nr.subdatabase.service;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicPrimaryKey;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.bi.database.statement.SqlStatement;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.internal.service.impl.RuntimeDataSchemeServiceImpl;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.subdatabase.config.SubDataBaseConfiguration;
import com.jiuqi.nr.subdatabase.dao.SubDataBaseDao;
import com.jiuqi.nr.subdatabase.dao.SubDataBaseLogicTableDao;
import com.jiuqi.nr.subdatabase.dao.TableHandleDao;
import com.jiuqi.nr.subdatabase.facade.ExecutedSubDataBaseTask;
import com.jiuqi.nr.subdatabase.facade.SubDataBase;
import com.jiuqi.nr.subdatabase.facade.SubDataBaseTaskParam;
import com.jiuqi.nr.subdatabase.facade.impl.SubDataBaseImpl;
import com.jiuqi.nr.subdatabase.provider.SubDataBaseCustomSystemTableProvider;
import com.jiuqi.nr.subdatabase.provider.SubDataBaseCustomTableProvider;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import com.jiuqi.util.OrderGenerator;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.organization.service.OrgCategoryService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
public class SubDataBaseService {
    private static final Logger logger = LoggerFactory.getLogger(SubDataBaseService.class);
    @Autowired
    private SubDataBaseDao subDataBaseDao;
    @Autowired
    private TableHandleDao tableHandleDao;
    @Autowired
    private RuntimeDataSchemeServiceImpl runtimeDataSchemeService;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private OrgCategoryClient orgCategoryClient;
    @Autowired
    private OrgCategoryService orgCategoryService;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired(required=false)
    private List<SubDataBaseCustomSystemTableProvider> systemTableProvider;
    @Autowired(required=false)
    private List<SubDataBaseCustomTableProvider> customTableProviders;
    @Autowired
    private SubDataBaseLogicTableDao logicTableDao;
    private NedisCacheManager cacheManager;
    @Autowired
    private SubDataBaseConfiguration subDataBaseConfiguration;
    @Autowired
    private AsyncThreadExecutor asyncThreadExecutor;
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @Autowired
    public void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager();
    }

    public void insertSubDataBase(SubDataBase subDataBase, boolean createOrgCateGory, String taskKey) throws Exception {
        OrgCategoryDO orgCateGory;
        String defaultOrgCateGoryName = subDataBase.getDefaultDBOrgCateGoryName();
        if (StringUtils.isEmpty((String)defaultOrgCateGoryName)) {
            defaultOrgCateGoryName = this.getOrgCateGoryByDataSchemeKey(subDataBase.getDataScheme());
            subDataBase.setDefaultDBOrgCateGoryName(defaultOrgCateGoryName);
        }
        if ((orgCateGory = this.getOrgCateGoryByEntityID(defaultOrgCateGoryName)) == null) {
            throw new RuntimeException("Failed to GET ORGCATEGORY");
        }
        subDataBase.getCreateTime();
        subDataBase.serOrgCateGoryName(defaultOrgCateGoryName + subDataBase.getCode());
        subDataBase.setDSDeployStatus(false);
        subDataBase.setSDSyncTime(subDataBase.getCreateTime());
        subDataBase.setSyncOrder(0);
        this.subDataBaseDao.insertSubDataBase(subDataBase);
        this.createTableWithSubDataBase(new SubDataBaseTaskParam(createOrgCateGory, defaultOrgCateGoryName, orgCateGory, subDataBase, taskKey));
    }

    private void createTableWithSubDataBase(SubDataBaseTaskParam subDataBaseTaskParam) throws Exception {
        Set<String> tableModelKey = this.getTableModelKey(subDataBaseTaskParam.getSubDataBase().getDataScheme());
        logger.info("\u5171\u6709\u8868\u6a21\u578b{}\u4e2a", (Object)tableModelKey.size());
        if (CollectionUtils.isEmpty(tableModelKey)) {
            throw new RuntimeException("Failed to get tableModelKey");
        }
        int threadPoolSize = this.subDataBaseConfiguration.getInsertSubDataBaseThreadSize();
        if (threadPoolSize == 1) {
            this.CreateWithoutParallel(subDataBaseTaskParam);
            return;
        }
        subDataBaseTaskParam.setThreadPoolSize(threadPoolSize);
        subDataBaseTaskParam.setDataScheme(null);
        subDataBaseTaskParam.setTableModelKey(tableModelKey);
        this.doSplitTaskAndBeginCreate(subDataBaseTaskParam);
    }

    public void doSplitTaskAndBeginCreate(SubDataBaseTaskParam taskParam) {
        Set<SubDataBaseTaskParam> subDataBaseTasks = this.splitCreateTableTasks(taskParam);
        ArrayList<String> allTaskIds = new ArrayList<String>();
        for (SubDataBaseTaskParam subDataBaseTaskParam : subDataBaseTasks) {
            NpRealTimeTaskInfo subJob = new NpRealTimeTaskInfo();
            subJob.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)subDataBaseTaskParam));
            subJob.setAbstractRealTimeJob((AbstractRealTimeJob)new ExecutedSubDataBaseTask());
            String taskId = this.asyncThreadExecutor.executeTask(subJob);
            allTaskIds.add(taskId);
        }
        this.calculateIfFinished(allTaskIds);
    }

    private Set<SubDataBaseTaskParam> splitCreateTableTasks(SubDataBaseTaskParam taskParam) {
        HashSet<SubDataBaseTaskParam> subDataBaseTasks = new HashSet<SubDataBaseTaskParam>();
        ArrayList<String> tableModelKey = new ArrayList<String>(taskParam.getTableModelKey());
        int threadSize = taskParam.getThreadPoolSize();
        int threadPoolSizeForDs = threadSize - 1;
        int last = taskParam.getTableModelKey().size() % threadPoolSizeForDs;
        int stepSize = (int)Math.floor((double)tableModelKey.size() / (double)threadPoolSizeForDs);
        if (stepSize == 0) {
            stepSize = tableModelKey.size();
            last = 0;
        }
        ArrayList tasksForThread = new ArrayList(stepSize + 1);
        for (int i = 0; i < tableModelKey.size(); ++i) {
            tasksForThread.add(tableModelKey.get(i));
            if (tasksForThread.size() != stepSize) continue;
            if (last != 0) {
                tasksForThread.add(tableModelKey.get(++i));
                --last;
            }
            SubDataBaseTaskParam subDataBaseTaskForTask = new SubDataBaseTaskParam(threadSize, true, new HashSet<String>(tasksForThread), taskParam.getSubDataBase(), taskParam.getTaskKey());
            subDataBaseTasks.add(subDataBaseTaskForTask);
            tasksForThread.clear();
        }
        SubDataBaseTaskParam subDataBaseTaskForTask = new SubDataBaseTaskParam(threadSize, false, null, taskParam.getSubDataBase(), taskParam.getTaskKey());
        subDataBaseTaskForTask.setCreateOrgCateGory(taskParam.isCreateOrgCateGory());
        subDataBaseTaskForTask.setOrgCateGoryName(taskParam.getOrgCateGoryName());
        subDataBaseTaskForTask.setOrgCategoryDO(taskParam.getOrgCategoryDO());
        subDataBaseTasks.add(subDataBaseTaskForTask);
        return subDataBaseTasks;
    }

    private void calculateIfFinished(List<String> taskIdList) {
        ArrayList<String> taskIds = new ArrayList<String>(taskIdList);
        while (!taskIds.isEmpty()) {
            Map<String, AsyncTask> tasks = this.queryTasks(taskIds);
            for (Map.Entry<String, AsyncTask> entry : tasks.entrySet()) {
                String taskId = entry.getKey();
                AsyncTask asyncTask = entry.getValue();
                if (asyncTask.getState() != TaskState.FINISHED && asyncTask.getState() != TaskState.ERROR) continue;
                taskIds.remove(taskId);
            }
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void CreateWithoutParallel(SubDataBaseTaskParam param) throws Exception {
        Set<String> tableModelKey;
        SubDataBase subDataBase = param.getSubDataBase();
        if (param.isCreateOrgCateGory()) {
            OrgCategoryDO orgCateGory = param.getOrgCategoryDO();
            orgCateGory.setId(null);
            orgCateGory.setName(param.getOrgCateGoryName() + subDataBase.getCode());
            R rs = this.orgCategoryService.add(orgCateGory);
            if (rs.getCode() != 0) {
                throw new RuntimeException("Failed to CREATE ORGCATEGORY");
            }
            ExecutedSubDataBaseTask.copyOrgVersion(subDataBase.getCode(), param.getOrgCateGoryName());
        }
        if (CollectionUtils.isEmpty(tableModelKey = this.getTableModelKey(subDataBase.getDataScheme()))) {
            throw new RuntimeException("Failed to get tableModelKey");
        }
        this.createAndDeploy(tableModelKey, subDataBase.getCode(), subDataBase.getDataScheme());
        if (!CollectionUtils.isEmpty(this.systemTableProvider)) {
            this.systemTableProvider.forEach(sysTemTable -> sysTemTable.createSystemCustomTable(subDataBase));
        }
        if (!CollectionUtils.isEmpty(this.customTableProviders)) {
            this.customTableProviders.forEach(customTable -> customTable.createCustomTable(param.getTaskKey(), subDataBase));
        }
    }

    private Map<String, AsyncTask> queryTasks(List<String> taskIds) {
        HashMap<String, AsyncTask> tasks = new HashMap<String, AsyncTask>();
        for (String taskId : taskIds) {
            AsyncTask asyncTask = this.asyncTaskManager.queryTask(taskId);
            tasks.put(taskId, asyncTask);
        }
        return tasks;
    }

    public String toSubDataBaseTableName(String tableName, SubDataBase subDataBase) {
        return subDataBase.getCode() + tableName;
    }

    public void deleteSubDataBase(SubDataBase subDataBase, boolean deleteOrgCateGory, String taskKey) {
        if (subDataBase != null) {
            Set<String> tableModelKeys = this.getTableModelKey(subDataBase.getDataScheme());
            if (CollectionUtils.isEmpty(tableModelKeys)) {
                throw new RuntimeException("Failed to get tableModelKeys");
            }
            try {
                this.deleteAndDeploy(tableModelKeys, subDataBase.getCode(), true);
            }
            catch (Exception e) {
                logger.error("\u5220\u9664\u5206\u5e93\u5b58\u50a8\u8868\u5931\u8d25", (Object)e.getMessage());
            }
            if (deleteOrgCateGory) {
                this.deleteOrgCategory(subDataBase.getOrgCateGoryName());
            }
            this.subDataBaseDao.deleteSubDataBase(subDataBase);
            if (!CollectionUtils.isEmpty(this.systemTableProvider)) {
                this.systemTableProvider.forEach(sysTemTable -> sysTemTable.deleteSystemCustomTable(subDataBase));
            }
            if (!CollectionUtils.isEmpty(this.customTableProviders)) {
                this.customTableProviders.forEach(customTable -> customTable.deleteCustomTable(taskKey, subDataBase));
            }
        }
    }

    public void deleteOrgCategory(String orgCategoryName) {
        OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
        orgCategoryDO.setName(orgCategoryName);
        try {
            R remove = this.orgCategoryService.remove(orgCategoryDO);
            try {
                this.tableHandleDao.DropTable(orgCategoryName);
            }
            catch (Exception e) {
                logger.warn("\u5220\u9664\u7ec4\u7ec7\u673a\u6784\u7269\u7406\u8868\u5931\u8d25\uff1a" + e.getMessage());
            }
            logger.info("\u5220\u9664\u7ec4\u7ec7\u673a\u6784" + orgCategoryName);
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
    }

    public void updateSubDataBase(SubDataBase subDataBase) throws Exception {
        this.subDataBaseDao.updateTitle(subDataBase);
    }

    public void updateSubDataBaseStatus(SubDataBase subDataBase) {
        this.subDataBaseDao.updateStatus(subDataBase);
    }

    public void batchUpdateSubDataBaseStatus(List<SubDataBase> subDataBase) {
        if (!CollectionUtils.isEmpty(subDataBase)) {
            this.subDataBaseDao.batchUpdateStatus(subDataBase);
        }
    }

    public Boolean paramTableExists(String dataSchemeKey, String code) throws Exception {
        Set<String> tableNames = this.getTablesFromDataScheme(dataSchemeKey);
        return this.tableHandleDao.tableExist(tableNames, code);
    }

    public boolean orgCateGoryExist(String orgCateGoryName, String code) throws Exception {
        return this.tableHandleDao.tableExist(orgCateGoryName + code);
    }

    public SubDataBase getSubDataBaseObjByCode(String dataSchemeKey, String code) {
        Assert.notNull((Object)dataSchemeKey, "dataSchemeKey must not be null.");
        Assert.notNull((Object)code, "code must not be null.");
        return this.subDataBaseDao.getSubDataBaseObjByCode(dataSchemeKey, code);
    }

    public List<SubDataBase> getSubDataBaseObjByDataScheme(String dataSchemeKey) {
        Assert.notNull((Object)dataSchemeKey, "dataSchemeKey must not be null.");
        return this.subDataBaseDao.getSubDataBaseObjByDataScheme(dataSchemeKey);
    }

    public List<SubDataBase> getAllSubDataBase() {
        return this.subDataBaseDao.getAllSubDataBases();
    }

    private String getOrgCateGoryByDataSchemeKey(String dataSchemeKey) {
        List dimensions = this.iRuntimeDataSchemeService.getDataSchemeDimension(dataSchemeKey);
        List unit = dimensions.stream().filter(dataDimension -> dataDimension.getDimensionType() == DimensionType.UNIT).collect(Collectors.toList());
        List unitScope = dimensions.stream().filter(dataDimension -> dataDimension.getDimensionType() == DimensionType.UNIT_SCOPE).collect(Collectors.toList());
        if (!unit.isEmpty()) {
            String entityId = !unitScope.isEmpty() ? ((DataDimension)unitScope.get(0)).getDimKey() : ((DataDimension)unit.get(0)).getDimKey();
            return entityId;
        }
        return null;
    }

    private OrgCategoryDO getOrgCateGoryByEntityID(String entityId) {
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
        OrgCategoryDO param = new OrgCategoryDO();
        param.setName(entityDefine.getCode());
        PageVO pageVO = this.orgCategoryClient.list(param);
        List list = new ArrayList();
        if (pageVO != null && pageVO.getTotal() > 0) {
            list = pageVO.getRows();
        }
        return (OrgCategoryDO)list.get(0);
    }

    public Set<String> getTablesFromDataScheme(String dataSchemeKey) {
        NedisCache paramTablesCache = this.cacheManager.getCache("SUBDATABASE_PARAM_TABLES");
        if (!paramTablesCache.exists(dataSchemeKey)) {
            HashSet<String> tableNames = new HashSet<String>();
            this.runtimeDataSchemeService.getDeployInfoBySchemeKey(dataSchemeKey).forEach(obj -> tableNames.add(obj.getTableName()));
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
            tableNames.add("NR_FILE_" + dataScheme.getBizCode());
            paramTablesCache.put(dataSchemeKey, tableNames);
            return tableNames;
        }
        Cache.ValueWrapper valueWrapper = paramTablesCache.get(dataSchemeKey);
        if (valueWrapper != null) {
            return (Set)valueWrapper.get();
        }
        return Collections.emptySet();
    }

    public Set<String> getTableModelKey(String dataSchemeKey) {
        HashSet<String> tableKeys = new HashSet<String>();
        this.runtimeDataSchemeService.getDeployInfoBySchemeKey(dataSchemeKey).forEach(obj -> tableKeys.add(obj.getTableModelKey()));
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByName("NR_FILE_" + dataScheme.getBizCode());
        if (tableModelDefine != null) {
            tableKeys.add(tableModelDefine.getID());
        }
        return tableKeys;
    }

    public String getNewKeys(String keys, String subDataBaseCode) {
        String[] split = keys.split(";");
        return Arrays.stream(split).map(a -> {
            a = a + subDataBaseCode;
            return a;
        }).collect(Collectors.joining(";"));
    }

    public List<SubDataBaseImpl> getAllSubDataBase(String fieldName, boolean isDesc) {
        return this.subDataBaseDao.getAllSubDataBases(fieldName, isDesc);
    }

    public List<SubDataBase> getSameTitleSDB(String title) {
        return this.subDataBaseDao.getSameTitleSDB(title);
    }

    public SubDataBase getSameTitleSubDataBase(String dataSchemeKey, String title) {
        return this.subDataBaseDao.getSameTitleSDBByDsAndTitle(dataSchemeKey, title);
    }

    public void createAndDeploy(Set<String> tableModelIDs, String subDataBaseCode, String dataSchemeKey) throws Exception {
        logger.info("\u5f00\u59cb\u521b\u5efa\u5206\u5e93\u3002");
        DataScheme dataScheme = this.iRuntimeDataSchemeService.getDataScheme(dataSchemeKey);
        for (String tableModelID : tableModelIDs) {
            List columnModelDefines = this.designDataModelService.getColumnModelDefinesByTable(tableModelID);
            String newTableID = tableModelID + subDataBaseCode;
            columnModelDefines.forEach(a -> {
                a.setTableID(newTableID);
                a.setID(a.getID() + subDataBaseCode);
            });
            this.designDataModelService.insertColumnModelDefines(columnModelDefines.toArray(new DesignColumnModelDefine[0]));
            DesignTableModelDefine tableModelDefine = this.designDataModelService.getTableModelDefine(tableModelID);
            logger.info("\u539f\u8868\u5355{},\u8868\u5355key{}", (Object)tableModelDefine.getTitle(), (Object)tableModelDefine.getID());
            String newTableTitle = subDataBaseCode + tableModelDefine.getTitle();
            String newTableCode = subDataBaseCode + tableModelDefine.getCode();
            String newTableName = subDataBaseCode + tableModelDefine.getName();
            String newKeys = this.getNewKeys(tableModelDefine.getKeys(), subDataBaseCode);
            String newBizKeys = this.getNewKeys(tableModelDefine.getBizKeys(), subDataBaseCode);
            tableModelDefine.setKeys(newKeys);
            tableModelDefine.setBizKeys(newBizKeys);
            tableModelDefine.setID(newTableID);
            tableModelDefine.setTitle(newTableTitle);
            tableModelDefine.setCode(newTableCode);
            tableModelDefine.setName(newTableName);
            tableModelDefine.setSupportNrdb(true);
            tableModelDefine.setStorageName(subDataBaseCode + dataScheme.getBizCode());
            this.designDataModelService.insertTableModelDefine(tableModelDefine);
            List indexsByTable = this.designDataModelService.getIndexsByTable(tableModelID);
            indexsByTable.forEach(a -> {
                String newIndexName = OrderGenerator.newOrder() + "_G_";
                this.designDataModelService.addIndexToTable(newTableID, this.getNewKeys(a.getFieldIDs(), subDataBaseCode).split(";"), newIndexName, a.getType());
            });
            logger.info("\u53d1\u5e03\u8868\u5355{},\u8868\u5355key\u4e3a{}", (Object)newTableTitle, (Object)newTableID);
            this.dataModelDeployService.deployTable(newTableID);
            logger.info("\u8868\u5355{},\u8868\u5355key{}\u53d1\u5e03\u5b8c\u6210", (Object)newTableTitle, (Object)newTableID);
        }
        logger.info("\u521b\u5efa\u5206\u5e93\u5b8c\u6210\u3002");
    }

    public void deleteAndDeploy(Set<String> tableModelIDs, String subDataBaseCode, boolean forceDelete) throws Exception {
        String[] gatherTableIDs;
        for (String gatherTableID : gatherTableIDs = (String[])tableModelIDs.stream().map(a -> {
            a = a + subDataBaseCode;
            return a;
        }).toArray(String[]::new)) {
            this.designDataModelService.deleteColumnModelDefineByTable(gatherTableID);
            this.designDataModelService.deleteIndexsByTable(gatherTableID);
        }
        this.designDataModelService.deleteTableModelDefines(gatherTableIDs);
        if (forceDelete) {
            for (String tableId : gatherTableIDs) {
                LogHelper.info((String)"\u5355\u673a\u7248\u5206\u5e93", (String)"\u5355\u673a\u7248\u5206\u5e93\u64cd\u4f5c", (String)("\u5220\u9664 " + tableId + " \u5b58\u50a8\u8868"));
                this.dataModelDeployService.deployTableUnCheck(tableId);
            }
        } else {
            this.dataModelDeployService.deployTables(gatherTableIDs);
        }
    }

    public void updateSyncTime(SubDataBase subDataBase) throws DBParaException {
        this.subDataBaseDao.updateSDSyncTime(subDataBase);
    }

    public LogicTable getLogicTable(String tableName) {
        return this.logicTableDao.getLogicTable(tableName);
    }

    public List<LogicField> getLogicFields(String tableName) {
        return this.logicTableDao.getLogicFields(tableName);
    }

    public LogicPrimaryKey getLogicPrimaryKey(String tableName) {
        return this.logicTableDao.getLogicPrimaryKey(tableName);
    }

    public void doCreateTable(SqlStatement sqlStmt) throws Exception {
        this.logicTableDao.createTable(sqlStmt);
    }

    public void doDropTable(String tableName) throws Exception {
        this.logicTableDao.dropTable(tableName);
    }
}

