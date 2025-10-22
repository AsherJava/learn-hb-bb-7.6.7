/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil
 *  com.jiuqi.nr.datascheme.internal.service.impl.RuntimeDataSchemeServiceImpl
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.dao.TableCheckDao
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.batch.summary.database.service.impl;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.batch.summary.database.dao.GatherDataBaseDao;
import com.jiuqi.nr.batch.summary.database.intf.GatherDataBase;
import com.jiuqi.nr.batch.summary.database.intf.impl.GatherDataBaseImpl;
import com.jiuqi.nr.batch.summary.database.service.GatherDataBaseService;
import com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil;
import com.jiuqi.nr.datascheme.internal.service.impl.RuntimeDataSchemeServiceImpl;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.dao.TableCheckDao;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import com.jiuqi.util.OrderGenerator;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
public class GatherDataBaseServiceImpl
implements GatherDataBaseService {
    private static final Logger logger = LoggerFactory.getLogger(GatherDataBaseServiceImpl.class);
    @Autowired
    private GatherDataBaseDao gatherDataBaseDao;
    @Autowired
    private RuntimeDataSchemeServiceImpl runtimeDataSchemeService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private RuntimeViewController runtimeViewController;
    @Autowired
    private TableCheckDao tableCheckDao;
    private NedisCacheManager cacheManager;

    @Autowired
    public void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager();
    }

    @Override
    public void creatGatherDataBase(String dataScheme) throws Exception {
        Assert.notNull((Object)dataScheme, "dataScheme must not be null.");
        GatherDataBaseImpl obj = new GatherDataBaseImpl(dataScheme);
        obj.getCreateTime();
        this.gatherDataBaseDao.insertGatherDataBase(obj);
        Set<String> tableModelIDs = this.getTableModelKey(dataScheme);
        this.createAndDeploy(tableModelIDs);
    }

    @Override
    public void createCheckTable(String taskKey) throws Exception {
        Assert.notNull((Object)taskKey, "taskKey must not be null.");
        Set<String> checkTables = this.getCheckTables(taskKey);
        HashSet<DesignTableModelDefine> tableModelDefines = new HashSet<DesignTableModelDefine>();
        for (String checkTableName : checkTables) {
            DesignTableModelDefine tableModelDefine = this.designDataModelService.getTableModelDefineByCode(checkTableName);
            if (tableModelDefine == null) continue;
            tableModelDefines.add(tableModelDefine);
        }
        Set<String> checkTableIDs = tableModelDefines.stream().map(IModelDefineItem::getID).collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(checkTableIDs)) {
            this.createAndDeploy(checkTableIDs);
        }
    }

    @Override
    public GatherDataBase query(String dataScheme) {
        return this.gatherDataBaseDao.getGatherDataBase(dataScheme);
    }

    @Override
    public void delete(String dataScheme) throws Exception {
        this.gatherDataBaseDao.deleteGatherDataBase(dataScheme);
        Set<String> tableModelIDs = this.getTableModelKey(dataScheme);
        this.deleteAndDeploy(tableModelIDs, false);
    }

    @Override
    public void delete(String dataScheme, boolean forceDelete) throws Exception {
        this.gatherDataBaseDao.deleteGatherDataBase(dataScheme);
        Set<String> tableModelIDs = this.getTableModelKey(dataScheme);
        this.deleteAndDeploy(tableModelIDs, forceDelete);
    }

    @Override
    public void deleteCheckTable(String taskKey) throws Exception {
        Set<String> checkTables = this.getCheckTables(taskKey);
        HashSet<DesignTableModelDefine> tableModelDefines = new HashSet<DesignTableModelDefine>();
        for (String checkTableName : checkTables) {
            DesignTableModelDefine tableModelDefine = this.designDataModelService.getTableModelDefineByCode(checkTableName);
            if (tableModelDefine == null) continue;
            tableModelDefines.add(tableModelDefine);
        }
        Set<String> checkTableIDs = tableModelDefines.stream().map(IModelDefineItem::getID).collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(checkTableIDs)) {
            this.deleteAndDeploy(checkTableIDs, false);
        }
    }

    @Override
    public void deleteCheckTable(String taskKey, boolean forceDelete) throws Exception {
        Set<String> checkTables = this.getCheckTables(taskKey);
        HashSet<DesignTableModelDefine> tableModelDefines = new HashSet<DesignTableModelDefine>();
        for (String checkTableName : checkTables) {
            DesignTableModelDefine tableModelDefine = this.designDataModelService.getTableModelDefineByCode(checkTableName);
            if (tableModelDefine == null) continue;
            tableModelDefines.add(tableModelDefine);
        }
        Set<String> checkTableIDs = tableModelDefines.stream().map(IModelDefineItem::getID).collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(checkTableIDs)) {
            this.deleteAndDeploy(checkTableIDs, forceDelete);
        }
    }

    @Override
    public boolean isExistGatherDataBase(String dataScheme) {
        GatherDataBase query = this.query(dataScheme);
        return query != null;
    }

    @Override
    public boolean isExistDataInGB(String dataSchemeKey) throws Exception {
        Set<String> tableNames = this.getTablesFromDataScheme(dataSchemeKey);
        for (String tableName : tableNames) {
            if (!this.tableCheckDao.checkTableExist(tableName + "_G_") || !this.tableCheckDao.checkTableExistData(tableName + "_G_")) continue;
            return true;
        }
        return false;
    }

    public void createAndDeploy(Set<String> tableModelIDs) throws Exception {
        logger.info("\u5f00\u59cb\u521b\u5efa\u6c47\u603b\u5206\u5e93\u3002");
        DesignColumnModelDefine gatherColumnModel = this.designDataModelService.createColumnModelDefine();
        gatherColumnModel.setCode("GATHER_SCHEME_CODE");
        gatherColumnModel.setTitle("\u6c47\u603b\u65b9\u6848\u6807\u8bc6");
        gatherColumnModel.setColumnType(ColumnModelType.STRING);
        gatherColumnModel.setPrecision(50);
        for (String tableModelID : tableModelIDs) {
            List columnModelDefines = this.designDataModelService.getColumnModelDefinesByTable(tableModelID);
            String newTableID = tableModelID + "_g_";
            columnModelDefines.forEach(a -> {
                a.setTableID(newTableID);
                a.setID(a.getID() + "_g_");
            });
            gatherColumnModel.setID(UUIDUtils.getKey() + "_g_");
            gatherColumnModel.setTableID(newTableID);
            columnModelDefines.add(gatherColumnModel);
            this.designDataModelService.insertColumnModelDefines(columnModelDefines.toArray(new DesignColumnModelDefine[0]));
            DesignTableModelDefine tableModelDefine = this.designDataModelService.getTableModelDefine(tableModelID);
            String newTableTitle = tableModelDefine.getTitle() + "_\u6c47\u603b\u5e93";
            String newTableCode = tableModelDefine.getCode() + "_G_";
            String newTableName = tableModelDefine.getName() + "_G_";
            String newKeys = this.getNewKeys(tableModelDefine.getKeys()) + ";" + gatherColumnModel.getID();
            String newBizKeys = this.getNewKeys(tableModelDefine.getBizKeys());
            tableModelDefine.setKeys(newKeys);
            tableModelDefine.setBizKeys(newBizKeys);
            tableModelDefine.setID(newTableID);
            tableModelDefine.setTitle(newTableTitle);
            tableModelDefine.setCode(newTableCode);
            tableModelDefine.setName(newTableName);
            this.designDataModelService.insertTableModelDefine(tableModelDefine);
            List indexsByTable = this.designDataModelService.getIndexsByTable(tableModelID);
            indexsByTable.forEach(a -> {
                String newIndexName = OrderGenerator.newOrder() + "_G_";
                String fieldIDs = this.getNewKeys(a.getFieldIDs());
                if (a.getType() == IndexModelType.UNIQUE) {
                    fieldIDs = fieldIDs + ";" + gatherColumnModel.getID();
                }
                this.designDataModelService.addIndexToTable(newTableID, fieldIDs.split(";"), newIndexName, a.getType());
            });
            this.dataModelDeployService.deployTable(newTableID);
        }
        logger.info("\u521b\u5efa\u6c47\u603b\u5206\u5e93\u5b8c\u6210");
    }

    public void deleteAndDeploy(Set<String> tableModelIDs, boolean forceDelete) throws Exception {
        String[] gatherTableIDs;
        for (String gatherTableID : gatherTableIDs = (String[])tableModelIDs.stream().map(a -> {
            a = a + "_g_";
            return a;
        }).toArray(String[]::new)) {
            this.designDataModelService.deleteColumnModelDefineByTable(gatherTableID);
            this.designDataModelService.deleteIndexsByTable(gatherTableID);
        }
        this.designDataModelService.deleteTableModelDefines(gatherTableIDs);
        if (forceDelete) {
            for (String tableId : gatherTableIDs) {
                LogHelper.info((String)"\u6279\u91cf\u6c47\u603b\u5206\u5e93", (String)"\u6c47\u603b\u5206\u5e93\u64cd\u4f5c", (String)("\u5220\u9664 " + tableId + " \u5b58\u50a8\u8868"));
                this.dataModelDeployService.deployTableUnCheck(tableId);
            }
        } else {
            this.dataModelDeployService.deployTables(gatherTableIDs);
        }
    }

    public String getNewKeys(String keys) {
        String[] split = keys.split(";");
        return Arrays.stream(split).map(a -> {
            a = a + "_g_";
            return a;
        }).collect(Collectors.joining(";"));
    }

    public String getNewIndexName(String indexName) {
        if (indexName.indexOf("SYS_ALLCKR_") >= 0) {
            return indexName.replace("SYS", "G");
        }
        return indexName + "_G_";
    }

    private Set<String> getTableModelKey(String dataSchemeKey) {
        HashSet<String> tableKeys = new HashSet<String>();
        this.runtimeDataSchemeService.getDeployInfoBySchemeKey(dataSchemeKey).forEach(obj -> tableKeys.add(obj.getTableModelKey()));
        return tableKeys;
    }

    public Set<String> getCheckTables(String taskKey) {
        List formSchemeDefines;
        HashSet<String> result = new HashSet<String>();
        try {
            formSchemeDefines = this.runtimeViewController.queryFormSchemeByTask(taskKey);
        }
        catch (Exception e) {
            throw new RuntimeException("\u83b7\u53d6\u4efb\u52a1\u4e0b\u62a5\u8868\u65b9\u6848\u5931\u8d25\uff01");
        }
        for (FormSchemeDefine define : formSchemeDefines) {
            Set<String> checkTablesByFS = this.getCheckTablesByFS(define.getFormSchemeCode());
            result.addAll(checkTablesByFS);
        }
        return result;
    }

    public Set<String> getCheckTablesByFS(String formSchemeCode) {
        HashSet<String> result = new HashSet<String>();
        String ckdTableName = CheckTableNameUtil.getCKDTableName((String)formSchemeCode);
        String ckrTableName = CheckTableNameUtil.getCKRTableName((String)formSchemeCode);
        String allCKRTableName = CheckTableNameUtil.getAllCKRTableName((String)formSchemeCode);
        result.add(ckdTableName);
        result.add(ckrTableName);
        result.add(allCKRTableName);
        return result;
    }

    public Set<String> getTablesFromDataScheme(String dataSchemeKey) {
        NedisCache paramTablesCache = this.cacheManager.getCache("GATHER_PARAM_TABLES");
        if (!paramTablesCache.exists(dataSchemeKey)) {
            HashSet<String> tableNames = new HashSet<String>();
            this.runtimeDataSchemeService.getDeployInfoBySchemeKey(dataSchemeKey).forEach(obj -> tableNames.add(obj.getTableName()));
            paramTablesCache.put(dataSchemeKey, tableNames);
            return tableNames;
        }
        Cache.ValueWrapper valueWrapper = paramTablesCache.get(dataSchemeKey);
        if (valueWrapper != null) {
            return (Set)valueWrapper.get();
        }
        return Collections.emptySet();
    }
}

