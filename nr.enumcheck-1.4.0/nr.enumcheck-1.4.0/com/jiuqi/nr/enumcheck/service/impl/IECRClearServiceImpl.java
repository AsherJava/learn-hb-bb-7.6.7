/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacheckcommon.helper.DataQueryHelper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
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
package com.jiuqi.nr.enumcheck.service.impl;

import com.jiuqi.nr.datacheckcommon.helper.DataQueryHelper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.enumcheck.dao.EnumCheckRes;
import com.jiuqi.nr.enumcheck.dao.EnumCheckResDao;
import com.jiuqi.nr.enumcheck.service.IECRClearService;
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
import org.springframework.stereotype.Service;

@Service
public class IECRClearServiceImpl
implements IECRClearService {
    private static final Logger logger = LoggerFactory.getLogger(IECRClearServiceImpl.class);
    @Autowired
    private EnumCheckResDao enumCheckResDao;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataQueryHelper dataQueryHelper;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;

    @Override
    public void clearResult(int cleanDay) {
        if (cleanDay < 0) {
            cleanDay = 90;
        }
        long time = System.currentTimeMillis() - (long)(cleanDay * 24 * 60 * 60) * 1000L;
        Timestamp timestamp = new Timestamp(time);
        List<EnumCheckRes> results = this.enumCheckResDao.findByCreatedAt(timestamp);
        this.clearRes(results);
        this.enumCheckResDao.deleteByCreatedAt(timestamp);
    }

    private void clearRes(List<EnumCheckRes> results) {
        if (null == results || results.isEmpty()) {
            return;
        }
        HashMap<String, List> formSchemeKeyBatchIdsMap = new HashMap<String, List>();
        for (EnumCheckRes enumCheckRes : results) {
            List batchIds = formSchemeKeyBatchIdsMap.computeIfAbsent(enumCheckRes.getSchemeKey(), k -> new ArrayList());
            batchIds.add(enumCheckRes.getId());
        }
        for (String formSchemeKey : formSchemeKeyBatchIdsMap.keySet()) {
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
            if (null == formSchemeDefine) continue;
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
            String tableName = "NR_MJZDJCJG_" + formSchemeDefine.getFormSchemeCode();
            String libraryTableName = this.dataQueryHelper.getLibraryTableName(taskDefine.getKey(), tableName);
            TableModelDefine enumCheckResTable = this.dataModelService.getTableModelDefineByName(libraryTableName);
            if (null == enumCheckResTable) {
                logger.error("\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u4efb\u52a1");
                continue;
            }
            List batchIds = (List)formSchemeKeyBatchIdsMap.get(formSchemeKey);
            NvwaQueryModel queryModel = new NvwaQueryModel();
            List ecrFields = this.dataModelService.getColumnModelDefinesByTable(enumCheckResTable.getID());
            for (ColumnModelDefine ecrField : ecrFields) {
                queryModel.getColumns().add(new NvwaQueryColumn(ecrField));
                if (!ecrField.getCode().equals("MJZD_ASYNCTASKID")) continue;
                queryModel.getColumnFilters().put(ecrField, batchIds);
            }
            INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            try {
                INvwaDataUpdator nvwaDataUpdator = updatableDataAccess.openForUpdate(context);
                nvwaDataUpdator.deleteAll();
                nvwaDataUpdator.commitChanges(context);
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
    }
}

