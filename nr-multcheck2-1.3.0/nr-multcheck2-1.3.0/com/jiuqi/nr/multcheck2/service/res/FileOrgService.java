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

import com.jiuqi.nr.multcheck2.bean.MultcheckResOrg;
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
public class FileOrgService {
    private static final Logger log = LogManager.getLogger(FileOrgService.class);
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider dataAccessProvider;
    @Autowired
    private FileResUtil util;

    public void batchAdd(List<MultcheckResOrg> orgs, List<String> dims, String tableName) {
        HashMap<String, Integer> colIndexMap = new HashMap<String, Integer>();
        HashMap<String, ColumnModelDefine> colModelMap = new HashMap<String, ColumnModelDefine>();
        INvwaUpdatableDataAccess updatableDataAccess = this.util.getNvwaUpdatableDataAccess(tableName, colIndexMap, colModelMap);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(context);
            Timestamp now = new Timestamp(System.currentTimeMillis());
            for (MultcheckResOrg org : orgs) {
                INvwaDataRow row = dataUpdator.addInsertRow();
                row.setKeyValue((ColumnModelDefine)colModelMap.get("MRIO_KEY"), (Object)org.getKey());
                row.setValue(((Integer)colIndexMap.get("MRR_KEY")).intValue(), (Object)org.getRecordKey());
                row.setValue(((Integer)colIndexMap.get("MSI_KEY")).intValue(), (Object)org.getItemKey());
                row.setValue(((Integer)colIndexMap.get("MSI_TYPE")).intValue(), (Object)org.getItemType());
                row.setValue(((Integer)colIndexMap.get("MRIO_ORG")).intValue(), (Object)org.getOrg());
                row.setValue(((Integer)colIndexMap.get("MRIO_RESOURCE")).intValue(), (Object)org.getResource());
                row.setValue(((Integer)colIndexMap.get("MRIO_RESULT")).intValue(), (Object)org.getResult());
                row.setValue(((Integer)colIndexMap.get("MRIO_UPDATE_TIME")).intValue(), (Object)(org.getTime() == null ? now : new Timestamp(org.getTime().getTime())));
                if (CollectionUtils.isEmpty(dims)) continue;
                Map<String, String> dimValueMap = org.getDims();
                for (String dim : dims) {
                    row.setValue(((Integer)colIndexMap.get(dim)).intValue(), (Object)dimValueMap.get(dim));
                }
            }
            dataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            log.error("add\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868record\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
    }

    public List<MultcheckResOrg> getMultcheckResOrg(String tableName, MultcheckResOrg param, List<String> dims) {
        ArrayList<MultcheckResOrg> res = new ArrayList<MultcheckResOrg>();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        INvwaDataAccess dataAccess = this.dataAccessProvider.createReadOnlyDataAccess(queryModel);
        HashMap<String, ColumnModelDefine> colModelMap = new HashMap<String, ColumnModelDefine>();
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        for (int i = 0; i < columns.size(); ++i) {
            ColumnModelDefine col = (ColumnModelDefine)columns.get(i);
            String code = col.getCode();
            colModelMap.put(code, col);
            if ("MRR_KEY".equals(code) || "MSI_KEY".equals(code) || "MSI_TYPE".equals(code)) continue;
            queryModel.getColumns().add(new NvwaQueryColumn(col));
        }
        queryModel.getColumnFilters().put(colModelMap.get("MRR_KEY"), param.getRecordKey());
        queryModel.getColumnFilters().put(colModelMap.get("MSI_KEY"), param.getItemKey());
        if (StringUtils.hasText(param.getOrg())) {
            queryModel.getColumnFilters().put(colModelMap.get("MRIO_ORG"), param.getOrg());
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataSet dataSet = dataAccess.executeQueryWithRowKey(context);
            int totalCount = dataSet.size();
            for (int i = 0; i < totalCount; ++i) {
                INvwaDataRow item = dataSet.getRow(i);
                if (item == null) continue;
                MultcheckResOrg org = new MultcheckResOrg();
                res.add(org);
                org.setKey((String)item.getValue((ColumnModelDefine)colModelMap.get("MRIO_KEY")));
                org.setRecordKey(param.getRecordKey());
                org.setItemKey(param.getItemKey());
                org.setOrg((String)item.getValue((ColumnModelDefine)colModelMap.get("MRIO_ORG")));
                org.setResource((String)item.getValue((ColumnModelDefine)colModelMap.get("MRIO_RESOURCE")));
                org.setResult((String)item.getValue((ColumnModelDefine)colModelMap.get("MRIO_RESULT")));
                org.setTime(((GregorianCalendar)item.getValue((ColumnModelDefine)colModelMap.get("MRIO_UPDATE_TIME"))).getTime());
                if (CollectionUtils.isEmpty(dims)) continue;
                HashMap<String, String> dimMap = new HashMap<String, String>();
                org.setDims(dimMap);
                for (String dim : dims) {
                    if (!colModelMap.containsKey(dim)) continue;
                    dimMap.put(dim, (String)item.getValue((ColumnModelDefine)colModelMap.get(dim)));
                }
            }
        }
        catch (Exception e) {
            log.error("getByOrg\u7efc\u5408\u5ba1\u6838\u83b7\u53d6\u52a8\u6001\u8d44\u6e90\u5ba1\u6838\u7ed3\u679c\u5f02\u5e38\uff1a\uff1a", (Throwable)e);
        }
        return res;
    }

    public void cleanRecord(Date cleanDate, String tableName) {
        this.util.cleanRecord(cleanDate, tableName, "MRIO_UPDATE_TIME");
    }
}

