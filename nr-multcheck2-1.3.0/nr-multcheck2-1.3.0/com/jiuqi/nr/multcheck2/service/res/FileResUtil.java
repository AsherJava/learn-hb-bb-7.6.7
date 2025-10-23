/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
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

import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileResUtil {
    private static final Logger log = LogManager.getLogger(FileResUtil.class);
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider dataAccessProvider;

    public INvwaUpdatableDataAccess getNvwaUpdatableDataAccess(String tableName, Map<String, Integer> colIndexMap, Map<String, ColumnModelDefine> colModelMap) {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        for (int i = 0; i < columns.size(); ++i) {
            ColumnModelDefine columnModelDefine = (ColumnModelDefine)columns.get(i);
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
            colIndexMap.put(columnModelDefine.getCode(), i);
            colModelMap.put(columnModelDefine.getCode(), columnModelDefine);
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
        return updatableDataAccess;
    }

    public INvwaDataAccess getNvwaDataAccessByRecord(String recordKey, String tableName, Map<String, Integer> colIndexMap, Map<String, ColumnModelDefine> colModelMap, String recordField) {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        INvwaDataAccess dataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        for (int i = 0; i < columns.size(); ++i) {
            ColumnModelDefine col = (ColumnModelDefine)columns.get(i);
            colIndexMap.put(col.getCode(), i);
            colModelMap.put(col.getCode(), col);
            queryModel.getColumns().add(new NvwaQueryColumn(col));
            if (!recordField.equals(col.getCode())) continue;
            queryModel.getColumnFilters().put(col, recordKey);
        }
        return dataAccess;
    }

    public void cleanRecord(Date cleanDate, String tableName, String dateFiled) {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        if (tableModel == null) {
            return;
        }
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        columns.forEach(o -> queryModel.getColumns().add(new NvwaQueryColumn(o)));
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String cleanDateStr = sdf.format(cleanDate);
        queryModel.setFilter(tableName + "[" + dateFiled + "] <= DateValue(\"" + cleanDateStr + "\",\"" + format + "\")");
        INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(context);
            dataUpdator.deleteAll();
            dataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            log.error("clean\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
    }

    public void cleanAllRecords(String tableName) {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        columns.forEach(o -> queryModel.getColumns().add(new NvwaQueryColumn(o)));
        INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(context);
            dataUpdator.deleteAll();
            dataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            log.error("clean\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
    }
}

