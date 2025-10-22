/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 */
package com.jiuqi.nr.finalaccountsaudit.integritycheck.internal.controller;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper;
import com.jiuqi.nr.finalaccountsaudit.common.TmpTableUtils;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.common.FormOperationHelper;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.common.IntegrityCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.common.IntegrityDataInfo;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.controller.IIntegrityCheckController;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntegrityCheckController
implements IIntegrityCheckController {
    private static final Logger logger = LoggerFactory.getLogger(IntegrityCheckController.class);
    @Autowired
    private FormOperationHelper fHelper;
    @Autowired
    private TmpTableUtils tempDao;
    @Autowired
    private EntityQueryHelper entityQueryHelper;

    @Override
    public IntegrityDataInfo integrityCheck(IntegrityCheckInfo integrityCheckInfo, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        IntegrityDataInfo integrityDataInfo = new IntegrityDataInfo();
        if (asyncTaskMonitor == null) {
            return integrityDataInfo;
        }
        asyncTaskMonitor.progressAndMessage(0.01, "running");
        ITempTable tempTable = null;
        try {
            tempTable = this.tempDao.createTempTable();
            Map<String, Object> entityTableMap = this.fHelper.getEntityTable(integrityCheckInfo.getFormSchemeKey(), integrityCheckInfo.getperiod(), asyncTaskMonitor);
            EntityViewData masterEntityView = (EntityViewData)entityTableMap.get(this.fHelper.masterEntityViewKey);
            String masterDimName = masterEntityView.getDimensionName();
            IEntityTable entityTable = (IEntityTable)entityTableMap.get(this.fHelper.entityTableMapKey);
            List<String> dwKeys = this.getDwKeys(integrityCheckInfo, entityTable, asyncTaskMonitor);
            List<String> forms = this.getForms(integrityCheckInfo, asyncTaskMonitor);
            integrityDataInfo.setDwKeyList(dwKeys);
            integrityDataInfo.setfmKeyList(forms);
            integrityDataInfo.setHeaderList(this.fHelper.getHeader(forms));
            this.tempDao.prepareTempTableData(tempTable, dwKeys);
            asyncTaskMonitor.progressAndMessage(0.16, "running");
            Map<String, Map<String, String>> formUnitsZero = this.fHelper.queryFormIsEmptyZero(tempTable.getTableName(), integrityCheckInfo.getTaskKey(), integrityCheckInfo.getFormSchemeKey(), integrityCheckInfo.getperiod(), forms, masterDimName, asyncTaskMonitor);
            Map<String, Map<String, String>> formUnits = this.fHelper.queryFormIsEmptyByEmptyZero(formUnitsZero);
            asyncTaskMonitor.progressAndMessage(0.97, "running");
            this.processCheckTableData(integrityDataInfo, formUnits, formUnitsZero, dwKeys, forms, entityTableMap);
            asyncTaskMonitor.progressAndMessage(0.98, "running");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        finally {
            this.dropTempTable(tempTable);
            asyncTaskMonitor.progressAndMessage(0.99, "running");
        }
        return integrityDataInfo;
    }

    private List<String> getDwKeys(IntegrityCheckInfo integrityCheckInfo, IEntityTable entityTable, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        List<String> dwKeys = integrityCheckInfo.getEntityKeys();
        asyncTaskMonitor.progressAndMessage(0.05, "running");
        if (dwKeys.isEmpty()) {
            dwKeys = this.fHelper.getDWkeys(dwKeys, entityTable, asyncTaskMonitor);
        }
        HashMap<String, Integer> untityOrderDic = this.entityQueryHelper.entityOrderByKey(entityTable);
        Collections.sort(dwKeys, Comparator.comparingInt(o -> untityOrderDic.getOrDefault(o, 0)));
        asyncTaskMonitor.progressAndMessage(0.1, "running");
        return dwKeys;
    }

    private List<String> getForms(IntegrityCheckInfo integrityCheckInfo, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        List<String> selectedForms = integrityCheckInfo.getFormKeys();
        List<String> allForms = this.fHelper.getFormsAllList(integrityCheckInfo.getFormSchemeKey(), asyncTaskMonitor);
        List<String> forms = selectedForms.isEmpty() ? allForms : allForms.stream().filter(selectedForms::contains).collect(Collectors.toList());
        asyncTaskMonitor.progressAndMessage(0.15, "running");
        return forms;
    }

    private void processCheckTableData(IntegrityDataInfo integrityDataInfo, Map<String, Map<String, String>> formUnits, Map<String, Map<String, String>> formUnitsZero, List<String> dwKeys, List<String> forms, Map<String, Object> entityTableMap) throws Exception {
        List<Map<String, List<String>>> checkTableData = this.fHelper.getCheckTableData(formUnits, dwKeys, forms, entityTableMap);
        integrityDataInfo.setRowData(checkTableData.get(0));
        integrityDataInfo.setEmptyTabCount(checkTableData.get(0).size());
        integrityDataInfo.setRowDataDiff(checkTableData.get(1));
        integrityDataInfo.setEmptyTabCountDiff(checkTableData.get(1).size());
        integrityDataInfo.setDwKeyDefectList(checkTableData.get(2).get("dwKeyList"));
        integrityDataInfo.setDwKeyDefectDiffList(checkTableData.get(2).get("dwKeyDiffList"));
        List<Map<String, List<String>>> checkTableDataZero = this.fHelper.getCheckTableData(formUnitsZero, dwKeys, forms, entityTableMap);
        integrityDataInfo.setRowDataZero(checkTableDataZero.get(0));
        integrityDataInfo.setEemptyTabCountZero(checkTableDataZero.get(0).size());
        integrityDataInfo.setRowDataZeroDiff(checkTableDataZero.get(1));
        integrityDataInfo.setEmptyTabCountZeroDiff(checkTableDataZero.get(1).size());
        integrityDataInfo.setDwKeyDefectZeroList(checkTableDataZero.get(2).get("dwKeyList"));
        integrityDataInfo.setDwKeyDefectZeroDiffList(checkTableDataZero.get(2).get("dwKeyDiffList"));
    }

    private void dropTempTable(ITempTable tempTable) {
        if (tempTable != null) {
            try {
                this.tempDao.dropTempTable(tempTable);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}

