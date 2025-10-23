/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataSet
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.dataengine.model.OrderByItem
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.multcheck2.service.res;

import com.jiuqi.nr.multcheck2.bean.MultcheckResRecord;
import com.jiuqi.nr.multcheck2.common.CheckSource;
import com.jiuqi.nr.multcheck2.common.SerializeUtil;
import com.jiuqi.nr.multcheck2.service.res.FileResUtil;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.dataengine.model.OrderByItem;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FileRecordService {
    private static final Logger log = LogManager.getLogger(FileRecordService.class);
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider dataAccessProvider;
    @Autowired
    private FileResUtil util;

    @Deprecated
    public void add(String tableName, MultcheckResRecord record, List<String> dims) {
        this.add(tableName, record);
    }

    public void add(String tableName, MultcheckResRecord record) {
        HashMap<String, Integer> colIndexMap = new HashMap<String, Integer>();
        HashMap<String, ColumnModelDefine> colModelMap = new HashMap<String, ColumnModelDefine>();
        INvwaUpdatableDataAccess updatableDataAccess = this.util.getNvwaUpdatableDataAccess(tableName, colIndexMap, colModelMap);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            String dim = null;
            if (!CollectionUtils.isEmpty(record.getDims())) {
                dim = SerializeUtil.serializeToJson(record.getDims());
            }
            INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(context);
            INvwaDataRow row = dataUpdator.addInsertRow();
            row.setKeyValue((ColumnModelDefine)colModelMap.get("MRR_KEY"), (Object)record.getKey());
            row.setValue(((Integer)colIndexMap.get("MRR_TASK")).intValue(), (Object)record.getTask());
            row.setValue(((Integer)colIndexMap.get("MRR_PERIOD")).intValue(), (Object)record.getPeriod());
            row.setValue(((Integer)colIndexMap.get("MRR_SOURCE")).intValue(), (Object)record.getSource().value());
            row.setValue(((Integer)colIndexMap.get("MS_KEY")).intValue(), (Object)record.getSchemeKey());
            row.setValue(((Integer)colIndexMap.get("MRR_DIM")).intValue(), (Object)dim);
            row.setValue(((Integer)colIndexMap.get("MRR_SUCCESS")).intValue(), (Object)record.getSuccess());
            row.setValue(((Integer)colIndexMap.get("MRR_FAILED")).intValue(), (Object)record.getFailed());
            row.setValue(((Integer)colIndexMap.get("MRR_BEGIN_TIME")).intValue(), (Object)new Timestamp(record.getBegin().getTime()));
            row.setValue(((Integer)colIndexMap.get("MRR_END_TIME")).intValue(), (Object)new Timestamp(record.getEnd().getTime()));
            row.setValue(((Integer)colIndexMap.get("MRR_USER")).intValue(), (Object)record.getUser());
            dataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            log.error("add\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868record\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
    }

    public MultcheckResRecord getRecordByKey(String tableName, String key, List<String> dims) {
        MultcheckResRecord record = null;
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        INvwaDataAccess dataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        ColumnModelDefine keyCol = null;
        HashMap<String, ColumnModelDefine> colModelMap = new HashMap<String, ColumnModelDefine>();
        for (int i = 0; i < columns.size(); ++i) {
            ColumnModelDefine col = (ColumnModelDefine)columns.get(i);
            if ("MRR_KEY".equals(col.getCode())) {
                keyCol = col;
            }
            colModelMap.put(col.getCode(), col);
            queryModel.getColumns().add(new NvwaQueryColumn(col));
        }
        queryModel.getColumnFilters().put(keyCol, key);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataSet dataSet = dataAccess.executeQueryWithRowKey(context);
            int totalCount = dataSet.size();
            for (int i = 0; i < totalCount; ++i) {
                INvwaDataRow item = dataSet.getRow(i);
                if (item == null) continue;
                record = new MultcheckResRecord();
                record.setKey((String)item.getValue((ColumnModelDefine)colModelMap.get("MRR_KEY")));
                record.setTask((String)item.getValue((ColumnModelDefine)colModelMap.get("MRR_TASK")));
                record.setPeriod((String)item.getValue((ColumnModelDefine)colModelMap.get("MRR_PERIOD")));
                record.setSource(CheckSource.fromValue((Integer)item.getValue((ColumnModelDefine)colModelMap.get("MRR_SOURCE"))));
                record.setSchemeKey((String)item.getValue((ColumnModelDefine)colModelMap.get("MS_KEY")));
                record.setSuccess((Integer)item.getValue((ColumnModelDefine)colModelMap.get("MRR_SUCCESS")));
                record.setFailed((Integer)item.getValue((ColumnModelDefine)colModelMap.get("MRR_FAILED")));
                record.setBegin(((GregorianCalendar)item.getValue((ColumnModelDefine)colModelMap.get("MRR_BEGIN_TIME"))).getTime());
                record.setEnd(((GregorianCalendar)item.getValue((ColumnModelDefine)colModelMap.get("MRR_END_TIME"))).getTime());
                record.setUser((String)item.getValue((ColumnModelDefine)colModelMap.get("MRR_USER")));
                String dimClob = (String)item.getValue((ColumnModelDefine)colModelMap.get("MRR_DIM"));
                if (!CollectionUtils.isEmpty(dims) && StringUtils.hasText(dimClob)) {
                    Map dimMap = SerializeUtil.deserializeFromJson(dimClob, Map.class);
                    record.setDims(dimMap);
                }
                return record;
            }
        }
        catch (Exception e) {
            log.error("getRecordByKey\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868record\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
        return record;
    }

    public List<String> getRecordByTaskPeriod(String tableName, String task, String period, String userName, CheckSource source) {
        return this.getRecordByTaskPeriodScheme(tableName, task, period, null, userName, source);
    }

    public List<String> getRecordByTaskPeriodScheme(String tableName, String task, String period, String scheme, String userName, CheckSource source) {
        ArrayList<String> res = new ArrayList<String>();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        INvwaDataAccess dataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        ColumnModelDefine keyCol = null;
        block20: for (ColumnModelDefine col : columns) {
            switch (col.getCode()) {
                case "MRR_KEY": {
                    keyCol = col;
                    queryModel.getColumns().add(new NvwaQueryColumn(col));
                    continue block20;
                }
                case "MRR_TASK": {
                    queryModel.getColumnFilters().put(col, task);
                    continue block20;
                }
                case "MRR_PERIOD": {
                    queryModel.getColumnFilters().put(col, period);
                    continue block20;
                }
                case "MRR_USER": {
                    if (!StringUtils.hasText(userName)) continue block20;
                    queryModel.getColumnFilters().put(col, userName);
                    continue block20;
                }
                case "MRR_SOURCE": {
                    if (source == null) continue block20;
                    queryModel.getColumnFilters().put(col, source.value());
                    continue block20;
                }
                case "MS_KEY": {
                    if (!StringUtils.hasText(scheme)) continue block20;
                    queryModel.getColumnFilters().put(col, scheme);
                    continue block20;
                }
                case "MRR_END_TIME": {
                    OrderByItem orderItemTime = new OrderByItem(col, true);
                    queryModel.getOrderByItems().add(orderItemTime);
                    continue block20;
                }
            }
        }
        try {
            INvwaDataSet dataSet = dataAccess.executeQueryWithRowKey(context);
            int totalCount = dataSet.size();
            for (int i = 0; i < totalCount; ++i) {
                INvwaDataRow item = dataSet.getRow(i);
                res.add((String)item.getValue(keyCol));
            }
        }
        catch (Exception e) {
            log.error("getRecordByTaskPeriod\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868record\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
        return res;
    }

    public void cleanRecord(Date cleanDate, String tableName) {
        this.util.cleanRecord(cleanDate, tableName, "MRR_END_TIME");
    }
}

