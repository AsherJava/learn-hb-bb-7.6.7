/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.internal.springcache.CacheProvider
 *  com.jiuqi.np.dataengine.IConnectionProvider
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.data.engine.gather.IDataGatherProvider
 *  com.jiuqi.nr.data.engine.grouping.IGroupingAccessProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.gcreport.common.np;

import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.internal.springcache.CacheProvider;
import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.data.engine.gather.IDataGatherProvider;
import com.jiuqi.nr.data.engine.grouping.IGroupingAccessProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NpReportQueryProvider {
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IDataGatherProvider dataGatherProvider;
    @Autowired
    private IGroupingAccessProvider iGroupingAccessProvider;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IConnectionProvider jdbcTemplate;
    @Autowired
    private IDataDefinitionDesignTimeController designTimeController;
    private NedisCacheManager cacheManager;

    @Autowired
    public void setCacheManager(CacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager();
    }

    public QueryParam getQueryParam() {
        return new QueryParam(this.jdbcTemplate, this.runtimeController, this.designTimeController, this.entityViewRunTimeController);
    }

    public TableDefine getTableDefine(String tableName) {
        try {
            return this.runtimeController.queryTableDefineByCode(tableName);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<FormSchemeDefine> queryFormSchemeByTask(String taskKey) throws Exception {
        List schemes = this.getRunTimeViewController().queryFormSchemeByTask(taskKey);
        if (schemes == null) {
            return null;
        }
        return new ArrayList<FormSchemeDefine>(schemes);
    }

    public List<TaskDefine> getAllTaskDefines() {
        List tasks = this.getRunTimeViewController().getAllTaskDefines();
        if (tasks == null) {
            return null;
        }
        return new ArrayList<TaskDefine>(tasks);
    }

    public IDataAccessProvider getDataAccessProvider() {
        return this.dataAccessProvider;
    }

    public IDataGatherProvider getDataGatherProvider() {
        return this.dataGatherProvider;
    }

    public IGroupingAccessProvider getiGroupingAccessProvider() {
        return this.iGroupingAccessProvider;
    }

    public IRunTimeViewController getRunTimeViewController() {
        return this.runTimeViewController;
    }

    public IDataDefinitionRuntimeController getRuntimeController() {
        return this.runtimeController;
    }

    public IEntityViewRunTimeController getEntityViewRunTimeController() {
        return this.entityViewRunTimeController;
    }

    public IConnectionProvider getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Deprecated
    public IDataDefinitionDesignTimeController getDesignTimeController() {
        return this.designTimeController;
    }

    public NedisCacheManager getCacheManager() {
        return this.cacheManager;
    }
}

