/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil
 *  com.jiuqi.nr.datascheme.internal.service.impl.RuntimeDataSchemeServiceImpl
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nr.subdatabase.controller.SubDataBaseController
 *  com.jiuqi.nr.subdatabase.facade.SubDataBase
 *  com.jiuqi.nr.subdatabase.service.SubDataBaseService
 */
package com.jiuqi.nr.batch.summary.database.service.impl;

import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.batch.summary.database.dao.IGatherDataBaseDao;
import com.jiuqi.nr.batch.summary.database.dao.IGatherTableDao;
import com.jiuqi.nr.batch.summary.database.intf.GatherSubDataBase;
import com.jiuqi.nr.batch.summary.database.intf.impl.GatherSubDataBaseImpl;
import com.jiuqi.nr.batch.summary.database.service.GatherSubDataBaseService;
import com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil;
import com.jiuqi.nr.datascheme.internal.service.impl.RuntimeDataSchemeServiceImpl;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.subdatabase.controller.SubDataBaseController;
import com.jiuqi.nr.subdatabase.facade.SubDataBase;
import com.jiuqi.nr.subdatabase.service.SubDataBaseService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
public class GatherSubDataBaseServiceImpl
implements GatherSubDataBaseService {
    private static final Logger logger = LoggerFactory.getLogger(GatherSubDataBaseServiceImpl.class);
    @Autowired
    private IGatherDataBaseDao gatherDataBaseDao;
    @Autowired
    private RuntimeDataSchemeServiceImpl runtimeDataSchemeService;
    @Autowired
    private IGatherTableDao gatherTableDao;
    @Autowired
    private SubDataBaseController subDataBaseController;
    @Autowired
    private SubDataBaseService subDataBaseService;
    @Autowired
    private RuntimeViewController runtimeViewController;
    private NedisCacheManager cacheManager;

    @Autowired
    public void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager();
    }

    @Override
    public void creatGatherSubDataBase(String dataScheme, String subDataBaseCode, boolean isSubDataBase) throws Exception {
        Assert.notNull((Object)dataScheme, "dataScheme must not be null.");
        Assert.notNull((Object)isSubDataBase, "isSubDataBase must not be null.");
        GatherSubDataBaseImpl obj = new GatherSubDataBaseImpl(subDataBaseCode, dataScheme);
        obj.getCreateTime();
        Set<String> tablesModelIDs = this.getTableModelKey(dataScheme);
        this.gatherTableDao.doCopyTableAndAddCol(tablesModelIDs, subDataBaseCode);
        this.gatherDataBaseDao.insertGatherSubDataBase(obj);
    }

    @Override
    public void copyCheckTables(String taskKey, String subDataBaseCode) throws Exception {
        Set<String> checkTableName = this.getCheckTables(taskKey);
        this.gatherTableDao.doCopyCheckTableAndAddCol(checkTableName, subDataBaseCode);
    }

    @Override
    public GatherSubDataBase query(String dataScheme, String subDataBaseCode) {
        return this.gatherDataBaseDao.getGatherSubDataBase(dataScheme, subDataBaseCode);
    }

    @Override
    public void delete(String dataScheme, String subDataBaseCode) throws Exception {
        Assert.notNull((Object)dataScheme, "dataScheme must not be null.");
        Set<String> tablesFromDataScheme = this.getTableModelKey(dataScheme);
        this.gatherTableDao.deleteCopyTable(tablesFromDataScheme, subDataBaseCode, false);
        this.gatherDataBaseDao.deleteGatherDataBase(dataScheme, subDataBaseCode);
    }

    @Override
    public void delete(String dataScheme, String subDataBaseCode, boolean forceDelete) throws Exception {
        Set<String> tablesFromDataScheme = this.getTableModelKey(dataScheme);
        this.gatherTableDao.deleteCopyTable(tablesFromDataScheme, subDataBaseCode, forceDelete);
        this.gatherDataBaseDao.deleteGatherDataBase(dataScheme, subDataBaseCode);
    }

    @Override
    public void deleteCheckTables(String taskKey, String subDataBaseCode) throws Exception {
        Set<String> tables = this.getCheckTables(taskKey);
        this.gatherTableDao.deleteCopyCheckTable(tables, subDataBaseCode, false);
    }

    @Override
    public void deleteCheckTables(String taskKey, String subDataBaseCode, boolean forceDelete) throws Exception {
        Set<String> tables = this.getCheckTables(taskKey);
        this.gatherTableDao.deleteCopyCheckTable(tables, subDataBaseCode, forceDelete);
    }

    @Override
    public void deleteAndCreateGatherDataBase(String dataScheme, String subDataBaseCode) {
        Assert.notNull((Object)dataScheme, "dataScheme must not be null.");
        Assert.notNull((Object)subDataBaseCode, "subDataBaseCode must not be null.");
        SubDataBase subDataBaseObj = this.subDataBaseController.getSubDataBaseObjByCode(dataScheme, subDataBaseCode);
        if (subDataBaseObj.getDSDeployStatus().booleanValue()) {
            subDataBaseObj.setDSDeployStatus(Boolean.valueOf(false));
            GatherSubDataBase query = this.query(dataScheme, subDataBaseCode);
            if (query != null) {
                try {
                    this.delete(query.getDataScheme(), subDataBaseCode);
                    this.creatGatherSubDataBase(query.getDataScheme(), subDataBaseCode, true);
                    this.subDataBaseService.updateSubDataBaseStatus(subDataBaseObj);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public boolean isExistGatherDataBase(String dataScheme, String subDataBaseCode) {
        GatherSubDataBase query = this.query(dataScheme, subDataBaseCode);
        return query != null;
    }

    public Set<String> getCheckTables(String taskKey) {
        HashSet<String> result = new HashSet<String>();
        List formSchemeDefines = null;
        try {
            formSchemeDefines = this.runtimeViewController.queryFormSchemeByTask(taskKey);
        }
        catch (Exception e) {
            throw new RuntimeException("\u83b7\u53d6\u4efb\u52a1\u4e0b\u62a5\u8868\u65b9\u6848\u5931\u8d25\uff01");
        }
        for (FormSchemeDefine define : formSchemeDefines) {
            String ckdTableName = CheckTableNameUtil.getCKDTableName((String)define.getFormSchemeCode());
            String ckrTableName = CheckTableNameUtil.getCKRTableName((String)define.getFormSchemeCode());
            String allCKRTableName = CheckTableNameUtil.getAllCKRTableName((String)define.getFormSchemeCode());
            result.add(ckdTableName);
            result.add(ckrTableName);
            result.add(allCKRTableName);
        }
        return result;
    }

    @Override
    public Set<String> getTables(String code, String dataSchemeKey) {
        Set<String> tablesFromDataScheme = this.getTablesFromDataScheme(dataSchemeKey);
        if (CollectionUtils.isEmpty(tablesFromDataScheme)) {
            throw new RuntimeException("Failed to get tableNames");
        }
        tablesFromDataScheme.forEach(item -> {
            item = code + item;
        });
        return tablesFromDataScheme;
    }

    private Set<String> getTableModelKey(String dataSchemeKey) {
        HashSet<String> tableKeys = new HashSet<String>();
        this.runtimeDataSchemeService.getDeployInfoBySchemeKey(dataSchemeKey).forEach(obj -> tableKeys.add(obj.getTableModelKey()));
        return tableKeys;
    }

    public Set<String> getTablesFromDataScheme(String dataSchemeKey) {
        HashSet<String> tableNames = new HashSet<String>();
        this.runtimeDataSchemeService.getDeployInfoBySchemeKey(dataSchemeKey).forEach(obj -> tableNames.add(obj.getTableName()));
        return tableNames;
    }
}

