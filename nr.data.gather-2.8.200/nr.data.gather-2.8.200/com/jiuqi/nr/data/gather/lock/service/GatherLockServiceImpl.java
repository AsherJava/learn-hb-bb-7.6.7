/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.exception.NotFoundTableDefineException
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.access.util.NvwaDataEngineQueryUtil
 *  com.jiuqi.nr.data.engine.gather.GatherLockService
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.data.gather.lock.service;

import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.exception.NotFoundTableDefineException;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.access.util.NvwaDataEngineQueryUtil;
import com.jiuqi.nr.data.engine.gather.GatherLockService;
import com.jiuqi.nr.data.gather.common.DataGatherConsts;
import com.jiuqi.nr.data.gather.exception.DataGatherException;
import com.jiuqi.nr.data.gather.lock.dao.GatherLockDao;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GatherLockServiceImpl
implements GatherLockService {
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private NvwaDataEngineQueryUtil nvwaDataEngineQueryUtil;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private GatherLockDao gatherLockDao;
    private static final Logger logger = LoggerFactory.getLogger(GatherLockServiceImpl.class);
    private static String serviceName;

    public boolean lockDataTable(List<DimensionValueSet> dimensions, String dataTableKey) {
        DataAccessContext dataContext = new DataAccessContext(this.dataModelService);
        TableModelDefine gatherLockTableModel = this.getGatherLockTableModel(dataTableKey);
        List<ColumnModelDefine> columnModelDefines = this.getColumnModelDefines(gatherLockTableModel.getID());
        List<String> columnCodes = columnModelDefines.stream().map(ColumnModelDefine::getName).collect(Collectors.toList());
        INvwaUpdatableDataSet updatableDataSet = this.getUpdateDataSet(dimensions, gatherLockTableModel, dataTableKey);
        for (DimensionValueSet dimensionValueSet : dimensions) {
            if (this.appendRow(updatableDataSet, columnCodes, dimensionValueSet, dataTableKey, gatherLockTableModel)) continue;
            return false;
        }
        try {
            updatableDataSet.commitChanges(dataContext);
        }
        catch (Exception e) {
            logger.error("\u9501\u5b9a\u5931\u8d25\uff01", e);
            return false;
        }
        return true;
    }

    public void unlockDataTable(List<DimensionValueSet> dimensions, String dataTableKey) {
        DataAccessContext dataContext = new DataAccessContext(this.dataModelService);
        TableModelDefine gatherLockTableModel = this.getGatherLockTableModel(dataTableKey);
        INvwaUpdatableDataSet updatableDataSet = this.getUpdateDataSet(dimensions, gatherLockTableModel, dataTableKey);
        updatableDataSet.deleteAll();
        try {
            updatableDataSet.commitChanges(dataContext);
        }
        catch (Exception e) {
            logger.error("\u89e3\u9501\u5931\u8d25\uff01", e);
        }
    }

    @Scheduled(cron="0 0/5 * * * ? ")
    protected void updateEffectTime() {
        DataAccessContext dataContext = new DataAccessContext(this.dataModelService);
        List allDataScheme = this.runtimeDataSchemeService.getAllDataScheme();
        for (DataScheme dataScheme : allDataScheme) {
            TableModelDefine gatherLockTableModel = this.getGatherLockTableModel(dataScheme);
            if (gatherLockTableModel == null) continue;
            List<ColumnModelDefine> columnModelDefines = this.getColumnModelDefines(gatherLockTableModel.getID());
            List columnCodes = columnModelDefines.stream().map(ColumnModelDefine::getName).collect(Collectors.toList());
            int heartTimeIndex = columnCodes.indexOf("HEART_TIME");
            INvwaUpdatableDataSet updateDataSetByServer = this.getUpdateDataSetByServer(gatherLockTableModel);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            for (INvwaDataRow nvwaDataRow : updateDataSetByServer) {
                nvwaDataRow.setValue(heartTimeIndex, (Object)timestamp);
            }
            try {
                updateDataSetByServer.commitChanges(dataContext);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Scheduled(cron="0 0/6 *  * * ? ")
    private void handleOverTimeLock() {
        List allDataScheme = this.runtimeDataSchemeService.getAllDataScheme();
        for (DataScheme dataScheme : allDataScheme) {
            this.gatherLockDao.deleteOverTimeLock(DataGatherConsts.getLockTableName(dataScheme.getBizCode()));
        }
    }

    private boolean appendRow(INvwaUpdatableDataSet updatableDataSet, List<String> columnCodes, DimensionValueSet dimensionValueSet, String dataTableKey, TableModelDefine tableModel) {
        DimensionValueSet tableDim = this.rebuildDimKey(dimensionValueSet, dataTableKey);
        try {
            ArrayKey arrayKey = this.convertMasterToArrayKey(tableModel.getName(), updatableDataSet.getRowKeyColumns(), tableDim);
            INvwaDataRow findRow = updatableDataSet.findRow(arrayKey);
            if (findRow != null) {
                return false;
            }
            INvwaDataRow appendRow = updatableDataSet.appendRow();
            this.nvwaDataEngineQueryUtil.setRowKey(tableModel.getName(), appendRow, tableDim);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            appendRow.setValue(columnCodes.indexOf("CREATE_TIME"), (Object)timestamp);
            appendRow.setValue(columnCodes.indexOf("HEART_TIME"), (Object)timestamp);
            appendRow.setValue(columnCodes.indexOf("SERVER_NAME"), (Object)this.getServiceName());
            return true;
        }
        catch (Exception e) {
            throw new DataGatherException("\u6dfb\u52a0\u6570\u636e\u884c\u5931\u8d25\uff01");
        }
    }

    private String getServiceName() {
        if (serviceName == null) {
            serviceName = DistributionManager.getInstance().self().getName();
        }
        return serviceName;
    }

    public ArrayKey convertMasterToArrayKey(String tableName, List<ColumnModelDefine> rowKeyColumns, DimensionValueSet masterKey) {
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        return dimensionChanger.getArrayKey(masterKey, rowKeyColumns);
    }

    private DimensionValueSet rebuildDimKey(DimensionValueSet masterKey, String dataTableKey) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet(masterKey);
        dimensionValueSet.setValue("DATA_TABLE_KEY", (Object)dataTableKey);
        return dimensionValueSet;
    }

    private TableModelDefine getGatherLockTableModel(String dataTableKey) {
        DataTable dataTable = this.runtimeDataSchemeService.getDataTable(dataTableKey);
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataTable.getDataSchemeKey());
        return this.getGatherLockTableModel(dataScheme);
    }

    private TableModelDefine getGatherLockTableModel(DataScheme dataScheme) {
        String lockTableName = DataGatherConsts.getLockTableName(dataScheme.getBizCode());
        try {
            return this.dataModelService.getTableModelDefineByCode(lockTableName);
        }
        catch (Exception e) {
            throw new NotFoundTableDefineException(new String[]{lockTableName});
        }
    }

    private List<ColumnModelDefine> getColumnModelDefines(String tableModelId) {
        List fields;
        try {
            fields = this.dataModelService.getColumnModelDefinesByTable(tableModelId);
        }
        catch (Exception e) {
            throw new NotFoundTableDefineException(ExceptionCodeCost.NOTFOUND_TABLEFIELD, new String[]{tableModelId});
        }
        return fields;
    }

    private INvwaUpdatableDataSet getUpdateDataSet(List<DimensionValueSet> dimensions, TableModelDefine tableModel, String dataTableKey) {
        DimensionValueSet masterKey = DimensionValueSetUtil.mergeDimensionValueSet(dimensions);
        List<ColumnModelDefine> columnModelDefines = this.getColumnModelDefines(tableModel.getID());
        INvwaDataSet gatherLockDataTable = this.getGatherLockDataSet(masterKey, tableModel, columnModelDefines, dataTableKey);
        return (INvwaUpdatableDataSet)gatherLockDataTable;
    }

    private INvwaUpdatableDataSet getUpdateDataSetByServer(TableModelDefine tableModel) {
        List<ColumnModelDefine> columnModelDefines = this.getColumnModelDefines(tableModel.getID());
        HashMap<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("SERVER_NAME", this.getServiceName());
        INvwaDataSet gatherLockDataTable = this.getGatherLockDataSet(tableModel, columnModelDefines, filterMap);
        return (INvwaUpdatableDataSet)gatherLockDataTable;
    }

    private INvwaDataSet getGatherLockDataSet(DimensionValueSet masterKeys, TableModelDefine tableModel, List<ColumnModelDefine> fields, String dataTableKey) {
        DimensionValueSet dimensionValueSet = this.rebuildDimKey(masterKeys, dataTableKey);
        return this.nvwaDataEngineQueryUtil.queryDataSet(tableModel.getName(), dimensionValueSet, fields, new ArrayList(), new HashMap(), new HashMap(), false);
    }

    private INvwaDataSet getGatherLockDataSet(TableModelDefine tableModel, List<ColumnModelDefine> fields, Map<String, Object> filterMap) {
        return this.nvwaDataEngineQueryUtil.queryDataSet(tableModel.getName(), new DimensionValueSet(), fields, new ArrayList(), filterMap, new HashMap(), false);
    }

    public void cleanUpDataByServer() {
        DataAccessContext dataContext = new DataAccessContext(this.dataModelService);
        List allDataScheme = this.runtimeDataSchemeService.getAllDataScheme();
        for (DataScheme dataScheme : allDataScheme) {
            TableModelDefine gatherLockTableModel = this.getGatherLockTableModel(dataScheme);
            if (gatherLockTableModel == null) continue;
            try {
                INvwaUpdatableDataSet updateDataSetByServer = this.getUpdateDataSetByServer(gatherLockTableModel);
                updateDataSetByServer.deleteAll();
                updateDataSetByServer.commitChanges(dataContext);
            }
            catch (Exception e) {
                logger.warn("\u6e05\u7406\u6570\u636e\u5931\u8d25", e);
            }
        }
    }
}

