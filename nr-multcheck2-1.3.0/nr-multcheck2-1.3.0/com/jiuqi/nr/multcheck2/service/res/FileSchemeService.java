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

import com.jiuqi.nr.multcheck2.bean.MultcheckResScheme;
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
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FileSchemeService {
    private static final Logger log = LogManager.getLogger(FileSchemeService.class);
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider dataAccessProvider;
    @Autowired
    private FileResUtil util;

    public void batchAdd(List<MultcheckResScheme> resSchemes, String tableName) {
        HashMap<String, Integer> colIndexMap = new HashMap<String, Integer>();
        HashMap<String, ColumnModelDefine> colModelMap = new HashMap<String, ColumnModelDefine>();
        INvwaUpdatableDataAccess updatableDataAccess = this.util.getNvwaUpdatableDataAccess(tableName, colIndexMap, colModelMap);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(context);
            for (MultcheckResScheme r : resSchemes) {
                INvwaDataRow row = dataUpdator.addInsertRow();
                row.setKeyValue((ColumnModelDefine)colModelMap.get("MRS_KEY"), (Object)r.getKey());
                row.setValue(((Integer)colIndexMap.get("MRR_KEY")).intValue(), (Object)r.getRecordKey());
                row.setValue(((Integer)colIndexMap.get("MS_KEY")).intValue(), (Object)r.getSchemeKey());
                row.setValue(((Integer)colIndexMap.get("MRS_BEGIN_TIME")).intValue(), (Object)new Timestamp(r.getBegin().getTime()));
                row.setValue(((Integer)colIndexMap.get("MRS_END_TIME")).intValue(), (Object)new Timestamp(r.getEnd().getTime()));
                row.setValue(((Integer)colIndexMap.get("MRS_ORGS")).intValue(), (Object)r.getOrgs());
            }
            dataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            log.error("batchAdd\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868scheme\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
    }

    public List<MultcheckResScheme> getByRecord(String recordKey, String tableName) {
        ArrayList<MultcheckResScheme> res = new ArrayList<MultcheckResScheme>();
        HashMap<String, Integer> colIndexMap = new HashMap<String, Integer>();
        HashMap<String, ColumnModelDefine> colModelMap = new HashMap<String, ColumnModelDefine>();
        INvwaDataAccess dataAccess = this.util.getNvwaDataAccessByRecord(recordKey, tableName, colIndexMap, colModelMap, "MRR_KEY");
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataSet dataSet = dataAccess.executeQueryWithRowKey(context);
            int totalCount = dataSet.size();
            for (int i = 0; i < totalCount; ++i) {
                MultcheckResScheme scheme = this.buildScheme(colModelMap, dataSet.getRow(i));
                if (scheme == null) continue;
                res.add(scheme);
            }
        }
        catch (Exception e) {
            log.error("getByRecord\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868scheme\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
        return res;
    }

    @Deprecated
    public MultcheckResScheme getByRecordScheme(String recordKey, String schemeKey, String tableName) {
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
                case "MS_KEY": {
                    if (!StringUtils.hasText(schemeKey)) continue block10;
                    queryModel.getColumnFilters().put(col, schemeKey);
                }
            }
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataAccess dataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
        try {
            INvwaDataSet dataSet = dataAccess.executeQueryWithRowKey(context);
            int totalCount = dataSet.size();
            for (int i = 0; i < totalCount; ++i) {
                MultcheckResScheme scheme = this.buildScheme(colModelMap, dataSet.getRow(i));
                if (scheme == null) continue;
                return scheme;
            }
        }
        catch (Exception e) {
            log.error("getByRecordScheme\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868scheme\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
        return null;
    }

    public Map<String, MultcheckResScheme> getByRecordsScheme(List<String> records, String schemeKey, String tableName) {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        INvwaDataAccess dataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        HashMap<String, ColumnModelDefine> colModelMap = new HashMap<String, ColumnModelDefine>();
        block10: for (int i = 0; i < columns.size(); ++i) {
            ColumnModelDefine col = (ColumnModelDefine)columns.get(i);
            colModelMap.put(col.getCode(), col);
            queryModel.getColumns().add(new NvwaQueryColumn(col));
            switch (col.getCode()) {
                case "MRR_KEY": {
                    if (CollectionUtils.isEmpty(records)) continue block10;
                    queryModel.getColumnFilters().put(col, records);
                    continue block10;
                }
                case "MS_KEY": {
                    if (!StringUtils.hasText(schemeKey)) continue block10;
                    queryModel.getColumnFilters().put(col, schemeKey);
                }
            }
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        HashMap<String, MultcheckResScheme> schemeMap = new HashMap<String, MultcheckResScheme>();
        try {
            INvwaDataSet dataSet = dataAccess.executeQueryWithRowKey(context);
            int totalCount = dataSet.size();
            for (int i = 0; i < totalCount; ++i) {
                MultcheckResScheme scheme = this.buildScheme(colModelMap, dataSet.getRow(i));
                if (scheme == null) continue;
                schemeMap.put(scheme.getRecordKey(), scheme);
            }
        }
        catch (Exception e) {
            log.error("getByRecordsScheme\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868scheme\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
        return schemeMap;
    }

    private MultcheckResScheme buildScheme(Map<String, ColumnModelDefine> colModelMap, INvwaDataRow item) {
        if (item == null) {
            return null;
        }
        MultcheckResScheme scheme = new MultcheckResScheme();
        scheme.setKey((String)item.getValue(colModelMap.get("MRS_KEY")));
        scheme.setRecordKey((String)item.getValue(colModelMap.get("MRR_KEY")));
        scheme.setSchemeKey((String)item.getValue(colModelMap.get("MS_KEY")));
        scheme.setBegin(((GregorianCalendar)item.getValue(colModelMap.get("MRS_BEGIN_TIME"))).getTime());
        scheme.setEnd(((GregorianCalendar)item.getValue(colModelMap.get("MRS_END_TIME"))).getTime());
        scheme.setOrgs((String)item.getValue(colModelMap.get("MRS_ORGS")));
        return scheme;
    }

    public void cleanRecord(Date cleanDate, String tableName) {
        this.util.cleanRecord(cleanDate, tableName, "MRS_END_TIME");
    }
}

