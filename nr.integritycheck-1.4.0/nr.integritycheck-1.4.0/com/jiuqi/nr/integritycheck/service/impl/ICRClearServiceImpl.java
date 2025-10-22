/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
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
package com.jiuqi.nr.integritycheck.service.impl;

import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.integritycheck.dao.IntegrityCheckRes;
import com.jiuqi.nr.integritycheck.dao.IntegrityCheckResDao;
import com.jiuqi.nr.integritycheck.helper.ICSplitTableHelper;
import com.jiuqi.nr.integritycheck.service.ICRClearService;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
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
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ICRClearServiceImpl
implements ICRClearService {
    private static final Logger logger = LoggerFactory.getLogger(ICRClearServiceImpl.class);
    @Autowired
    private IntegrityCheckResDao integrityCheckResDao;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private ICSplitTableHelper icSplitTableHelper;
    @Value(value="${jiuqi.nr.icr.clearDay:90}")
    private int icrClearDay;

    @Override
    public void clearResult() {
        long time = System.currentTimeMillis() - (long)(this.icrClearDay * 24 * 60 * 60) * 1000L;
        Timestamp timestamp = new Timestamp(time);
        List<IntegrityCheckRes> results = this.integrityCheckResDao.findByCreatedAt(timestamp);
        this.clearRes(results);
        this.integrityCheckResDao.deleteByCreatedAt(timestamp);
    }

    @Override
    public void clearResult(int cleanDay) {
        if (cleanDay < 0) {
            cleanDay = this.icrClearDay;
        }
        long time = System.currentTimeMillis() - (long)(cleanDay * 24 * 60 * 60) * 1000L;
        Timestamp timestamp = new Timestamp(time);
        List<IntegrityCheckRes> results = this.integrityCheckResDao.findByCreatedAt(timestamp);
        this.clearRes(results);
        this.integrityCheckResDao.deleteByCreatedAt(timestamp);
    }

    @Override
    public void clearResult(String dataSchemeKey, String batchId) {
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        String tableName = this.icSplitTableHelper.getICRSplitTableName(dataScheme);
        TableModelDefine icrTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == icrTable) {
            logger.error("\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7ed3\u679c\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848");
            return;
        }
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List icrFields = this.dataModelService.getColumnModelDefinesByTable(icrTable.getID());
        for (ColumnModelDefine icrField : icrFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(icrField));
            if (!icrField.getCode().equals("BATCHID")) continue;
            queryModel.getColumnFilters().put(icrField, batchId);
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        try {
            INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
            iNvwaDataUpdator.deleteAll();
            iNvwaDataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    @Override
    public void clearAllResult(String dataSchemeKey) {
        List<IntegrityCheckRes> results = this.integrityCheckResDao.findBySchemeKey(dataSchemeKey);
        this.clearRes(results);
        this.integrityCheckResDao.deleteBySchemeKey(dataSchemeKey);
    }

    @Override
    public void clearRecordTable(String id) {
        this.integrityCheckResDao.delete(id);
    }

    private void clearRes(List<IntegrityCheckRes> results) {
        if (null == results || results.isEmpty()) {
            return;
        }
        HashMap<String, List> dataSchemeKeyBatchIdsMap = new HashMap<String, List>();
        for (IntegrityCheckRes integrityCheckRes : results) {
            List batchIds = dataSchemeKeyBatchIdsMap.computeIfAbsent(integrityCheckRes.getSchemeKey(), k -> new ArrayList());
            batchIds.add(integrityCheckRes.getId());
        }
        for (String dataSchemeKey : dataSchemeKeyBatchIdsMap.keySet()) {
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
            if (null == dataScheme) continue;
            String tableName = this.icSplitTableHelper.getICRSplitTableName(dataScheme);
            TableModelDefine icrTable = this.dataModelService.getTableModelDefineByName(tableName);
            if (null == icrTable) {
                logger.error("\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7ed3\u679c\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848");
                continue;
            }
            List batchIds = (List)dataSchemeKeyBatchIdsMap.get(dataSchemeKey);
            NvwaQueryModel queryModel = new NvwaQueryModel();
            List icrFields = this.dataModelService.getColumnModelDefinesByTable(icrTable.getID());
            for (ColumnModelDefine icrField : icrFields) {
                queryModel.getColumns().add(new NvwaQueryColumn(icrField));
                if (!icrField.getCode().equals("BATCHID")) continue;
                queryModel.getColumnFilters().put(icrField, batchIds);
            }
            INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            try {
                INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
                iNvwaDataUpdator.deleteAll();
                iNvwaDataUpdator.commitChanges(context);
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
    }
}

