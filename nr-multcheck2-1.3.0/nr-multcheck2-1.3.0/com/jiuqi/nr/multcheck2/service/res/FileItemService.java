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
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.multcheck2.service.res;

import com.jiuqi.nr.multcheck2.bean.MultcheckResItem;
import com.jiuqi.nr.multcheck2.common.CheckRestultState;
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
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FileItemService {
    private static final Logger log = LogManager.getLogger(FileItemService.class);
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private FileResUtil util;
    @Autowired
    private INvwaDataAccessProvider dataAccessProvider;

    public void batchAdd(List<MultcheckResItem> items, String tableName) {
        HashMap<String, Integer> colIndexMap = new HashMap<String, Integer>();
        HashMap<String, ColumnModelDefine> colModelMap = new HashMap<String, ColumnModelDefine>();
        INvwaUpdatableDataAccess updatableDataAccess = this.util.getNvwaUpdatableDataAccess(tableName, colIndexMap, colModelMap);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(context);
            for (MultcheckResItem item : items) {
                INvwaDataRow row = dataUpdator.addInsertRow();
                row.setKeyValue((ColumnModelDefine)colModelMap.get("MRI_KEY"), (Object)item.getKey());
                row.setValue(((Integer)colIndexMap.get("MRR_KEY")).intValue(), (Object)item.getRecordKey());
                row.setValue(((Integer)colIndexMap.get("MS_KEY")).intValue(), (Object)item.getSchemeKey());
                row.setValue(((Integer)colIndexMap.get("MSI_KEY")).intValue(), (Object)item.getItemKey());
                row.setValue(((Integer)colIndexMap.get("MRI_STATE")).intValue(), (Object)item.getState().value());
                row.setValue(((Integer)colIndexMap.get("MRI_SUCCESS")).intValue(), (Object)item.getSuccess());
                row.setValue(((Integer)colIndexMap.get("MRI_FAILED")).intValue(), (Object)item.getFailed());
                row.setValue(((Integer)colIndexMap.get("MRI_IGNORE")).intValue(), (Object)item.getIgnore());
                row.setValue(((Integer)colIndexMap.get("MRI_BEGIN_TIME")).intValue(), (Object)new Timestamp(item.getBegin().getTime()));
                row.setValue(((Integer)colIndexMap.get("MRI_END_TIME")).intValue(), (Object)new Timestamp(item.getEnd().getTime()));
                row.setValue(((Integer)colIndexMap.get("MRI_RUN_CONFIG")).intValue(), (Object)item.getRunConfig());
            }
            dataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            log.error("batchAdd\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868scheme\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
    }

    public List<MultcheckResItem> getByRecord(String recordKey, String tableName) {
        ArrayList<MultcheckResItem> res = new ArrayList<MultcheckResItem>();
        HashMap<String, Integer> colIndexMap = new HashMap<String, Integer>();
        HashMap<String, ColumnModelDefine> colModelMap = new HashMap<String, ColumnModelDefine>();
        INvwaDataAccess dataAccess = this.util.getNvwaDataAccessByRecord(recordKey, tableName, colIndexMap, colModelMap, "MRR_KEY");
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataSet dataSet = dataAccess.executeQueryWithRowKey(context);
            int totalCount = dataSet.size();
            for (int i = 0; i < totalCount; ++i) {
                MultcheckResItem item = this.buildItem(colModelMap, dataSet.getRow(i));
                if (item == null) continue;
                res.add(item);
            }
        }
        catch (Exception e) {
            log.error("getByRecord\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868scheme\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
        return res;
    }

    public Set<String> filterRecordByItem(List<String> records, Set<String> itemInfoSet, String tableName) {
        HashSet<String> res = new HashSet<String>();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        ColumnModelDefine rkeyCol = null;
        for (ColumnModelDefine col : columns) {
            switch (col.getCode()) {
                case "MRR_KEY": {
                    rkeyCol = col;
                    queryModel.getColumns().add(new NvwaQueryColumn(col));
                    if (CollectionUtils.isEmpty(records)) break;
                    queryModel.getColumnFilters().put(col, records);
                    break;
                }
                case "MSI_KEY": {
                    if (CollectionUtils.isEmpty(itemInfoSet)) break;
                    if (itemInfoSet.size() == 1) {
                        queryModel.getColumnFilters().put(col, itemInfoSet.iterator().next());
                        break;
                    }
                    queryModel.getColumnFilters().put(col, new ArrayList<String>(itemInfoSet));
                }
            }
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataAccess dataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
        try {
            INvwaDataSet dataSet = dataAccess.executeQueryWithRowKey(context);
            int totalCount = dataSet.size();
            for (int i = 0; i < totalCount; ++i) {
                INvwaDataRow item = dataSet.getRow(i);
                res.add((String)item.getValue(rkeyCol));
            }
        }
        catch (Exception e) {
            log.error("filterRecordByItem\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868item\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
        return res;
    }

    public List<MultcheckResItem> getItemsBbyRecordAndKeys(String recordKey, Set<String> itemInfoSet, String tableName) {
        ArrayList<MultcheckResItem> res = new ArrayList<MultcheckResItem>();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        HashMap<String, ColumnModelDefine> colModelMap = new HashMap<String, ColumnModelDefine>();
        block10: for (int i = 0; i < columns.size(); ++i) {
            ColumnModelDefine col = (ColumnModelDefine)columns.get(i);
            colModelMap.put(col.getCode(), col);
            queryModel.getColumns().add(new NvwaQueryColumn(col));
            switch (col.getCode()) {
                case "MRR_KEY": {
                    if (!StringUtils.hasText(recordKey)) continue block10;
                    queryModel.getColumnFilters().put(col, recordKey);
                    continue block10;
                }
                case "MSI_KEY": {
                    if (CollectionUtils.isEmpty(itemInfoSet)) continue block10;
                    if (itemInfoSet.size() == 1) {
                        queryModel.getColumnFilters().put(col, itemInfoSet.iterator().next());
                        continue block10;
                    }
                    queryModel.getColumnFilters().put(col, new ArrayList<String>(itemInfoSet));
                }
            }
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataAccess dataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
        try {
            INvwaDataSet dataSet = dataAccess.executeQueryWithRowKey(context);
            int totalCount = dataSet.size();
            for (int i = 0; i < totalCount; ++i) {
                MultcheckResItem item = this.buildItem(colModelMap, dataSet.getRow(i));
                if (item == null) continue;
                res.add(item);
            }
        }
        catch (Exception e) {
            log.error("getByRecord\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868scheme\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
        return res;
    }

    public Map<String, List<MultcheckResItem>> getItemsBbyRecordsAndKeys(Set<String> records, Set<String> itemInfoSet, String tableName) {
        HashMap<String, List<MultcheckResItem>> res = new HashMap<String, List<MultcheckResItem>>();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        HashMap<String, ColumnModelDefine> colModelMap = new HashMap<String, ColumnModelDefine>();
        block10: for (int i = 0; i < columns.size(); ++i) {
            ColumnModelDefine col = (ColumnModelDefine)columns.get(i);
            colModelMap.put(col.getCode(), col);
            queryModel.getColumns().add(new NvwaQueryColumn(col));
            switch (col.getCode()) {
                case "MRR_KEY": {
                    if (CollectionUtils.isEmpty(records)) continue block10;
                    if (records.size() == 1) {
                        queryModel.getColumnFilters().put(col, records.iterator().next());
                        continue block10;
                    }
                    queryModel.getColumnFilters().put(col, new ArrayList<String>(records));
                    continue block10;
                }
                case "MSI_KEY": {
                    if (CollectionUtils.isEmpty(itemInfoSet)) continue block10;
                    if (itemInfoSet.size() == 1) {
                        queryModel.getColumnFilters().put(col, itemInfoSet.iterator().next());
                        continue block10;
                    }
                    queryModel.getColumnFilters().put(col, new ArrayList<String>(itemInfoSet));
                }
            }
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataAccess dataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
        try {
            INvwaDataSet dataSet = dataAccess.executeQueryWithRowKey(context);
            int totalCount = dataSet.size();
            for (int i = 0; i < totalCount; ++i) {
                MultcheckResItem item = this.buildItem(colModelMap, dataSet.getRow(i));
                ArrayList<MultcheckResItem> resItems = (ArrayList<MultcheckResItem>)res.get(item.getRecordKey());
                if (resItems == null) {
                    resItems = new ArrayList<MultcheckResItem>();
                    res.put(item.getRecordKey(), resItems);
                }
                resItems.add(item);
            }
        }
        catch (Exception e) {
            log.error("getByRecord\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868scheme\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
        return res;
    }

    public void cleanRecord(Date cleanDate, String tableName) {
        this.util.cleanRecord(cleanDate, tableName, "MRI_END_TIME");
    }

    private MultcheckResItem buildItem(Map<String, ColumnModelDefine> colModelMap, INvwaDataRow row) {
        if (row == null) {
            return null;
        }
        MultcheckResItem item = new MultcheckResItem();
        item.setKey((String)row.getValue(colModelMap.get("MRI_KEY")));
        item.setRecordKey((String)row.getValue(colModelMap.get("MRR_KEY")));
        item.setSchemeKey((String)row.getValue(colModelMap.get("MS_KEY")));
        item.setItemKey((String)row.getValue(colModelMap.get("MSI_KEY")));
        item.setState(CheckRestultState.fromValue((Integer)row.getValue(colModelMap.get("MRI_STATE"))));
        item.setSuccess((Integer)row.getValue(colModelMap.get("MRI_SUCCESS")));
        item.setFailed((Integer)row.getValue(colModelMap.get("MRI_FAILED")));
        item.setIgnore((Integer)row.getValue(colModelMap.get("MRI_IGNORE")));
        item.setBegin(((GregorianCalendar)row.getValue(colModelMap.get("MRI_BEGIN_TIME"))).getTime());
        item.setEnd(((GregorianCalendar)row.getValue(colModelMap.get("MRI_END_TIME"))).getTime());
        item.setRunConfig((String)row.getValue(colModelMap.get("MRI_RUN_CONFIG")));
        return item;
    }
}

