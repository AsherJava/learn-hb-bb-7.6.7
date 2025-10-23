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
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.multcheck2.service.res;

import com.jiuqi.nr.multcheck2.bean.MCErrorDescription;
import com.jiuqi.nr.multcheck2.service.res.FileResUtil;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataSet;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FileErrorService {
    private static final Logger log = LogManager.getLogger(FileErrorService.class);
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider dataAccessProvider;
    @Autowired
    private FileResUtil util;

    public void add(String tableName, MCErrorDescription error, List<String> dims) {
        this.batchAdd(tableName, Arrays.asList(error), dims);
    }

    public void batchAdd(String tableName, List<MCErrorDescription> errorDescriptions, List<String> dims) {
        HashMap<String, Integer> colIndexMap = new HashMap<String, Integer>();
        HashMap<String, ColumnModelDefine> colModelMap = new HashMap<String, ColumnModelDefine>();
        INvwaUpdatableDataAccess updatableDataAccess = this.util.getNvwaUpdatableDataAccess(tableName, colIndexMap, colModelMap);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(context);
            for (MCErrorDescription error : errorDescriptions) {
                INvwaDataRow row = dataUpdator.addInsertRow();
                this.buildRow(dims, colIndexMap, colModelMap, error, row);
            }
            dataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            log.error("add\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868record\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
    }

    private void buildRow(List<String> dims, Map<String, Integer> colIndexMap, Map<String, ColumnModelDefine> colModelMap, MCErrorDescription error, INvwaDataRow row) {
        row.setKeyValue(colModelMap.get("MEI_KEY"), (Object)error.getKey());
        row.setValue(colIndexMap.get("MEI_TASK").intValue(), (Object)error.getTask());
        row.setValue(colIndexMap.get("MEI_PERIOD").intValue(), (Object)error.getPeriod());
        row.setValue(colIndexMap.get("MEI_ORG").intValue(), (Object)error.getOrg());
        row.setValue(colIndexMap.get("MSI_TYPE").intValue(), (Object)error.getItemType());
        row.setValue(colIndexMap.get("MEI_RESOURCE").intValue(), (Object)error.getResource());
        row.setValue(colIndexMap.get("MEI_DESCRIPTION").intValue(), (Object)error.getDescription());
        row.setValue(colIndexMap.get("MEI_UPDATE_TIME").intValue(), (Object)new Timestamp(error.getTime().getTime()));
        row.setValue(colIndexMap.get("MEI_USER").intValue(), (Object)error.getUser());
        if (!CollectionUtils.isEmpty(dims)) {
            Map<String, String> dimValueMap = error.getDims();
            for (String dim : dims) {
                row.setValue(colIndexMap.get(dim).intValue(), (Object)dimValueMap.get(dim));
            }
        }
    }

    public List<MCErrorDescription> getByResource(String tableName, MCErrorDescription item, List<String> dims) {
        MCErrorDescription param = new MCErrorDescription();
        BeanUtils.copyProperties(item, param);
        param.setOrg(null);
        return this.getErrors(tableName, param, dims, null, null);
    }

    public List<MCErrorDescription> getByOrg(String tableName, MCErrorDescription item, List<String> dims) {
        MCErrorDescription param = new MCErrorDescription();
        BeanUtils.copyProperties(item, param);
        param.setResource(null);
        return this.getErrors(tableName, param, dims, null, null);
    }

    public List<MCErrorDescription> getByResourceAndOrg(String tableName, MCErrorDescription item, List<String> dims) {
        MCErrorDescription param = new MCErrorDescription();
        BeanUtils.copyProperties(item, param);
        ArrayList<MCErrorDescription> res = new ArrayList<MCErrorDescription>();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        INvwaDataAccess dataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        HashMap<String, ColumnModelDefine> colModelMap = new HashMap<String, ColumnModelDefine>();
        for (int i = 0; i < columns.size(); ++i) {
            ColumnModelDefine col = (ColumnModelDefine)columns.get(i);
            String code = col.getCode();
            colModelMap.put(code, col);
            if ("MEI_TASK".equals(code) || "MEI_PERIOD".equals(code) || "MSI_TYPE".equals(code) || "MEI_ORG".equals(code) || "MEI_RESOURCE".equals(code)) continue;
            queryModel.getColumns().add(new NvwaQueryColumn(col));
        }
        queryModel.getColumnFilters().put(colModelMap.get("MEI_TASK"), item.getTask());
        queryModel.getColumnFilters().put(colModelMap.get("MEI_PERIOD"), item.getPeriod());
        queryModel.getColumnFilters().put(colModelMap.get("MSI_TYPE"), item.getItemType());
        queryModel.getColumnFilters().put(colModelMap.get("MEI_ORG"), item.getOrg());
        queryModel.getColumnFilters().put(colModelMap.get("MEI_RESOURCE"), item.getResource());
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataSet dataSet = dataAccess.executeQueryWithRowKey(context);
            int totalCount = dataSet.size();
            for (int i = 0; i < totalCount; ++i) {
                INvwaDataRow row = dataSet.getRow(i);
                if (row == null) continue;
                MCErrorDescription error = new MCErrorDescription();
                res.add(error);
                error.setKey((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_KEY")));
                error.setTask(item.getTask());
                error.setPeriod(item.getPeriod());
                error.setItemType(item.getItemType());
                error.setOrg(item.getOrg());
                error.setResource(item.getResource());
                error.setDescription((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_DESCRIPTION")));
                error.setTime(((GregorianCalendar)row.getValue((ColumnModelDefine)colModelMap.get("MEI_UPDATE_TIME"))).getTime());
                error.setUser((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_USER")));
                if (CollectionUtils.isEmpty(dims)) continue;
                HashMap<String, String> dimMap = new HashMap<String, String>();
                error.setDims(dimMap);
                for (String dim : dims) {
                    dimMap.put(dim, (String)row.getValue((ColumnModelDefine)colModelMap.get(dim)));
                }
            }
        }
        catch (Exception e) {
            log.error("\u5355\u5355\u4f4d\u5355\u8d44\u6e90::\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868record\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
        return res;
    }

    public List<MCErrorDescription> getByResourceAndOrgs(String tableName, MCErrorDescription item, List<String> dims, List<String> orgList) {
        MCErrorDescription param = new MCErrorDescription();
        BeanUtils.copyProperties(item, param);
        ArrayList<MCErrorDescription> res = new ArrayList<MCErrorDescription>();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        INvwaDataAccess dataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        HashMap<String, ColumnModelDefine> colModelMap = new HashMap<String, ColumnModelDefine>();
        for (int i = 0; i < columns.size(); ++i) {
            ColumnModelDefine col = (ColumnModelDefine)columns.get(i);
            String code = col.getCode();
            colModelMap.put(code, col);
            if ("MEI_TASK".equals(code) || "MEI_PERIOD".equals(code) || "MSI_TYPE".equals(code) || "MEI_RESOURCE".equals(code)) continue;
            queryModel.getColumns().add(new NvwaQueryColumn(col));
        }
        queryModel.getColumnFilters().put(colModelMap.get("MEI_TASK"), item.getTask());
        queryModel.getColumnFilters().put(colModelMap.get("MEI_PERIOD"), item.getPeriod());
        queryModel.getColumnFilters().put(colModelMap.get("MSI_TYPE"), item.getItemType());
        queryModel.getColumnFilters().put(colModelMap.get("MEI_ORG"), orgList);
        queryModel.getColumnFilters().put(colModelMap.get("MEI_RESOURCE"), item.getResource());
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataSet dataSet = dataAccess.executeQueryWithRowKey(context);
            int totalCount = dataSet.size();
            for (int i = 0; i < totalCount; ++i) {
                INvwaDataRow row = dataSet.getRow(i);
                if (row == null) continue;
                MCErrorDescription error = new MCErrorDescription();
                res.add(error);
                error.setKey((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_KEY")));
                error.setTask(item.getTask());
                error.setPeriod(item.getPeriod());
                error.setItemType(item.getItemType());
                error.setOrg((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_ORG")));
                error.setResource(item.getResource());
                error.setDescription((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_DESCRIPTION")));
                error.setTime(((GregorianCalendar)row.getValue((ColumnModelDefine)colModelMap.get("MEI_UPDATE_TIME"))).getTime());
                error.setUser((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_USER")));
                if (CollectionUtils.isEmpty(dims)) continue;
                HashMap<String, String> dimMap = new HashMap<String, String>();
                error.setDims(dimMap);
                for (String dim : dims) {
                    dimMap.put(dim, (String)row.getValue((ColumnModelDefine)colModelMap.get(dim)));
                }
            }
        }
        catch (Exception e) {
            log.error("\u591a\u5355\u4f4d\u5355\u8d44\u6e90\uff1a\uff1a\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868record\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
        return res;
    }

    public List<MCErrorDescription> getByResourcesAndOrg(String tableName, MCErrorDescription item, List<String> dims, List<String> resourceList) {
        MCErrorDescription param = new MCErrorDescription();
        BeanUtils.copyProperties(item, param);
        ArrayList<MCErrorDescription> res = new ArrayList<MCErrorDescription>();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        INvwaDataAccess dataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        HashMap<String, ColumnModelDefine> colModelMap = new HashMap<String, ColumnModelDefine>();
        for (int i = 0; i < columns.size(); ++i) {
            ColumnModelDefine col = (ColumnModelDefine)columns.get(i);
            String code = col.getCode();
            colModelMap.put(code, col);
            if ("MEI_TASK".equals(code) || "MEI_PERIOD".equals(code) || "MSI_TYPE".equals(code) || "MEI_ORG".equals(code)) continue;
            queryModel.getColumns().add(new NvwaQueryColumn(col));
        }
        queryModel.getColumnFilters().put(colModelMap.get("MEI_TASK"), item.getTask());
        queryModel.getColumnFilters().put(colModelMap.get("MEI_PERIOD"), item.getPeriod());
        queryModel.getColumnFilters().put(colModelMap.get("MSI_TYPE"), item.getItemType());
        queryModel.getColumnFilters().put(colModelMap.get("MEI_ORG"), item.getOrg());
        queryModel.getColumnFilters().put(colModelMap.get("MEI_RESOURCE"), resourceList);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataSet dataSet = dataAccess.executeQueryWithRowKey(context);
            int totalCount = dataSet.size();
            for (int i = 0; i < totalCount; ++i) {
                INvwaDataRow row = dataSet.getRow(i);
                if (row == null) continue;
                MCErrorDescription error = new MCErrorDescription();
                res.add(error);
                error.setKey((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_KEY")));
                error.setTask(item.getTask());
                error.setPeriod(item.getPeriod());
                error.setItemType(item.getItemType());
                error.setOrg(item.getOrg());
                error.setResource((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_RESOURCE")));
                error.setDescription((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_DESCRIPTION")));
                error.setTime(((GregorianCalendar)row.getValue((ColumnModelDefine)colModelMap.get("MEI_UPDATE_TIME"))).getTime());
                error.setUser((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_USER")));
                if (CollectionUtils.isEmpty(dims)) continue;
                HashMap<String, String> dimMap = new HashMap<String, String>();
                error.setDims(dimMap);
                for (String dim : dims) {
                    dimMap.put(dim, (String)row.getValue((ColumnModelDefine)colModelMap.get(dim)));
                }
            }
        }
        catch (Exception e) {
            log.error("\u5355\u5355\u4f4d\u591a\u8d44\u6e90\uff1a\uff1a\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868record\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
        return res;
    }

    public List<MCErrorDescription> getByResourcesAndOrgs(String tableName, MCErrorDescription item, List<String> dims, List<String> resourceList, List<String> orgList) {
        MCErrorDescription param = new MCErrorDescription();
        BeanUtils.copyProperties(item, param);
        ArrayList<MCErrorDescription> res = new ArrayList<MCErrorDescription>();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        INvwaDataAccess dataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        HashMap<String, ColumnModelDefine> colModelMap = new HashMap<String, ColumnModelDefine>();
        for (int i = 0; i < columns.size(); ++i) {
            ColumnModelDefine col = (ColumnModelDefine)columns.get(i);
            String code = col.getCode();
            colModelMap.put(code, col);
            if ("MEI_TASK".equals(code) || "MEI_PERIOD".equals(code) || "MSI_TYPE".equals(code)) continue;
            queryModel.getColumns().add(new NvwaQueryColumn(col));
        }
        queryModel.getColumnFilters().put(colModelMap.get("MEI_TASK"), item.getTask());
        queryModel.getColumnFilters().put(colModelMap.get("MEI_PERIOD"), item.getPeriod());
        queryModel.getColumnFilters().put(colModelMap.get("MSI_TYPE"), item.getItemType());
        queryModel.getColumnFilters().put(colModelMap.get("MEI_ORG"), orgList);
        queryModel.getColumnFilters().put(colModelMap.get("MEI_RESOURCE"), resourceList);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataSet dataSet = dataAccess.executeQueryWithRowKey(context);
            int totalCount = dataSet.size();
            for (int i = 0; i < totalCount; ++i) {
                INvwaDataRow row = dataSet.getRow(i);
                if (row == null) continue;
                MCErrorDescription error = new MCErrorDescription();
                res.add(error);
                error.setKey((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_KEY")));
                error.setTask(item.getTask());
                error.setPeriod(item.getPeriod());
                error.setItemType(item.getItemType());
                error.setOrg((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_ORG")));
                error.setResource((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_RESOURCE")));
                error.setDescription((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_DESCRIPTION")));
                error.setTime(((GregorianCalendar)row.getValue((ColumnModelDefine)colModelMap.get("MEI_UPDATE_TIME"))).getTime());
                error.setUser((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_USER")));
                if (CollectionUtils.isEmpty(dims)) continue;
                HashMap<String, String> dimMap = new HashMap<String, String>();
                error.setDims(dimMap);
                for (String dim : dims) {
                    dimMap.put(dim, (String)row.getValue((ColumnModelDefine)colModelMap.get(dim)));
                }
            }
        }
        catch (Exception e) {
            log.error("\u591a\u5355\u4f4d\u591a\u8d44\u6e90\uff1a\uff1a\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868record\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
        return res;
    }

    @Deprecated
    private List<MCErrorDescription> getErrors(String tableName, MCErrorDescription item, List<String> dims, List<String> orgList, List<String> resourceList) {
        ArrayList<MCErrorDescription> res = new ArrayList<MCErrorDescription>();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        INvwaDataAccess dataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        HashMap<String, Integer> colIndexMap = new HashMap<String, Integer>();
        HashMap<String, ColumnModelDefine> colModelMap = new HashMap<String, ColumnModelDefine>();
        block16: for (int i = 0; i < columns.size(); ++i) {
            ColumnModelDefine col = (ColumnModelDefine)columns.get(i);
            colIndexMap.put(col.getCode(), i);
            colModelMap.put(col.getCode(), col);
            queryModel.getColumns().add(new NvwaQueryColumn(col));
            switch (col.getCode()) {
                case "MEI_TASK": {
                    queryModel.getColumnFilters().put(col, item.getTask());
                    continue block16;
                }
                case "MEI_PERIOD": {
                    queryModel.getColumnFilters().put(col, item.getPeriod());
                    continue block16;
                }
                case "MSI_TYPE": {
                    queryModel.getColumnFilters().put(col, item.getItemType());
                    continue block16;
                }
                case "MEI_RESOURCE": {
                    if (StringUtils.hasText(item.getResource())) {
                        queryModel.getColumnFilters().put(col, item.getResource());
                        continue block16;
                    }
                    if (CollectionUtils.isEmpty(resourceList)) continue block16;
                    queryModel.getColumnFilters().put(col, resourceList);
                    continue block16;
                }
                case "MEI_ORG": {
                    if (StringUtils.hasText(item.getOrg())) {
                        queryModel.getColumnFilters().put(col, item.getOrg());
                        continue block16;
                    }
                    if (CollectionUtils.isEmpty(orgList)) continue block16;
                    queryModel.getColumnFilters().put(col, orgList);
                    continue block16;
                }
                default: {
                    continue block16;
                }
            }
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataSet dataSet = dataAccess.executeQueryWithRowKey(context);
            int totalCount = dataSet.size();
            for (int i = 0; i < totalCount; ++i) {
                INvwaDataRow row = dataSet.getRow(i);
                if (row == null) continue;
                MCErrorDescription error = new MCErrorDescription();
                res.add(error);
                error.setKey((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_KEY")));
                error.setTask((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_TASK")));
                error.setPeriod((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_PERIOD")));
                error.setOrg((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_ORG")));
                error.setItemType((String)row.getValue((ColumnModelDefine)colModelMap.get("MSI_TYPE")));
                error.setResource((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_RESOURCE")));
                error.setDescription((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_DESCRIPTION")));
                error.setTime(((GregorianCalendar)row.getValue((ColumnModelDefine)colModelMap.get("MEI_UPDATE_TIME"))).getTime());
                error.setUser((String)row.getValue((ColumnModelDefine)colModelMap.get("MEI_USER")));
                if (CollectionUtils.isEmpty(dims)) continue;
                HashMap<String, String> dimMap = new HashMap<String, String>();
                error.setDims(dimMap);
                for (String dim : dims) {
                    dimMap.put(dim, (String)row.getValue((ColumnModelDefine)colModelMap.get(dim)));
                }
            }
        }
        catch (Exception e) {
            log.error("getRecordByTaskPeriod\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868record\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
        return res;
    }

    public void modify(String tableName, MCErrorDescription errorDescription) {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        HashMap<String, Integer> colIndexMap = new HashMap<String, Integer>();
        HashMap<String, ColumnModelDefine> colModelMap = new HashMap<String, ColumnModelDefine>();
        for (int i = 0; i < columns.size(); ++i) {
            ColumnModelDefine col = (ColumnModelDefine)columns.get(i);
            queryModel.getColumns().add(new NvwaQueryColumn(col));
            if ("MEI_KEY".equals(col.getCode())) {
                queryModel.getColumnFilters().put(col, errorDescription.getKey());
            }
            colIndexMap.put(col.getCode(), i);
            colModelMap.put(col.getCode(), col);
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
        try {
            INvwaUpdatableDataSet dataRows = updatableDataAccess.executeQueryForUpdate(context);
            if (dataRows.size() > 0) {
                INvwaDataRow row = dataRows.getRow(0);
                row.setValue(((Integer)colIndexMap.get("MEI_DESCRIPTION")).intValue(), (Object)errorDescription.getDescription());
                dataRows.commitChanges(context);
            }
        }
        catch (Exception e) {
            log.error("add\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868record\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
    }

    public void deleteByKeys(String tableName, List<String> keys) {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        for (ColumnModelDefine col : columns) {
            queryModel.getColumns().add(new NvwaQueryColumn(col));
            if (!"MEI_KEY".equals(col.getCode())) continue;
            queryModel.getColumnFilters().put(col, keys);
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(context);
            dataUpdator.deleteAll();
            dataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            log.error("add\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868record\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
    }

    public void deleteByOrgAndModel(String tableName, String task, String period, List<String> modelKeys, List<String> orgCodes, String type) {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        for (ColumnModelDefine col : columns) {
            queryModel.getColumns().add(new NvwaQueryColumn(col));
            if ("MEI_ORG".equals(col.getCode())) {
                queryModel.getColumnFilters().put(col, orgCodes);
                continue;
            }
            if ("MEI_RESOURCE".equals(col.getCode())) {
                queryModel.getColumnFilters().put(col, modelKeys);
                continue;
            }
            if ("MEI_TASK".equals(col.getCode())) {
                queryModel.getColumnFilters().put(col, task);
                continue;
            }
            if ("MEI_PERIOD".equals(col.getCode())) {
                queryModel.getColumnFilters().put(col, period);
                continue;
            }
            if (!"MSI_TYPE".equals(col.getCode())) continue;
            queryModel.getColumnFilters().put(col, type);
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(context);
            dataUpdator.deleteAll();
            dataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            log.error("add\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868record\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
    }

    public void deleteByKey(String tableName, String key) {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        for (ColumnModelDefine col : columns) {
            queryModel.getColumns().add(new NvwaQueryColumn(col));
            if (!"MEI_KEY".equals(col.getCode())) continue;
            queryModel.getColumnFilters().put(col, key);
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(context);
            dataUpdator.deleteAll();
            dataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            log.error("add\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868record\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
    }

    public void cleanRecord(Date cleanDate, String tableName) {
        this.util.cleanRecord(cleanDate, tableName, "MEI_UPDATE_TIME");
    }
}

