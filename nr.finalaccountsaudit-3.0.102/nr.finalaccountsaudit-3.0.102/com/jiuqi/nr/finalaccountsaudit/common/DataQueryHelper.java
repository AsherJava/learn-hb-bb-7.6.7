/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.intf.SplitTableHelper
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.finalaccountsaudit.common;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.intf.SplitTableHelper;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.util.StringUtils;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataQueryHelper {
    private static final String PERIOD_DIM = "DATATIME";
    private static final Logger logger = LoggerFactory.getLogger(DataQueryHelper.class);
    @Resource
    private IEntityDataService entityDataService;
    @Resource
    private IEntityMetaService metaService;
    @Resource
    private IDataDefinitionRuntimeController dataCtrl;
    @Resource
    private IRunTimeViewController viewCtrl;
    @Resource
    private IEntityViewRunTimeController entityViewCtrl;
    @Autowired
    private IFMDMAttributeService fmdmService;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private SubDatabaseTableNamesProvider subTableNamesProvider;
    @Autowired(required=false)
    private SplitTableHelper splitTableHelper;

    public IDataQuery getDataQuery(String period) {
        IDataQuery result = this.dataAccessProvider.newDataQuery();
        if (StringUtils.isNotEmpty((String)period)) {
            DimensionValueSet valueSet = new DimensionValueSet();
            valueSet.setValue(PERIOD_DIM, (Object)period);
            result.setMasterKeys(valueSet);
        }
        return result;
    }

    public IReadonlyTable buildReader(IDataQuery dataQuery) throws Exception {
        com.jiuqi.nr.entity.engine.executors.ExecutorContext context = new com.jiuqi.nr.entity.engine.executors.ExecutorContext(this.dataCtrl);
        return dataQuery.executeReader((ExecutorContext)context);
    }

    public IDataTable buildUpdater(IDataQuery dataQuery) throws Exception {
        com.jiuqi.nr.entity.engine.executors.ExecutorContext context = new com.jiuqi.nr.entity.engine.executors.ExecutorContext(this.dataCtrl);
        return dataQuery.executeQuery((ExecutorContext)context);
    }

    public String getLibraryTableName(String taskKey, String tableName) {
        try {
            String libraryTableName;
            if (this.subTableNamesProvider != null && !(libraryTableName = this.subTableNamesProvider.getSubDatabaseTableName(taskKey, tableName)).isEmpty()) {
                return libraryTableName;
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return tableName;
    }
}

