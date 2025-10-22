/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.dataentry.internal.service.FormGroupProvider
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.exception.NotFoundEntityException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.finalaccountsaudit.integritycheck.common;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.dataentry.internal.service.FormGroupProvider;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.common.DBTypeUtil;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.common.EmptyZeroCheckHelper;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.common.IntegrityThread;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.common.IntegrityZeroThread;
import com.jiuqi.nr.jtable.exception.NotFoundEntityException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FormOperationHelper {
    public String entityTableMapKey = "entityTable";
    public String masterEntityViewKey = "masterEntityView";
    @Autowired
    RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    FormGroupProvider formGroupProvider;
    @Resource
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    IDataAccessProvider dataAccessProvider;
    @Autowired
    IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    IJtableParamService jtableParamService;
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    EmptyZeroCheckHelper emptyZeroCheckHelper;
    @Autowired
    EntityQueryHelper entityQueryHelper;
    @Autowired
    DBTypeUtil dbTypeUtil;
    @Autowired
    private JdbcTemplate jdbcTpl;
    @Autowired
    private IEntityMetaService entityMetaService;

    public Map<String, Map<String, String>> queryFormIsEmpty(List<String> dwKeys, String tempTableName, String taskKey, String formSchemeKey, String period, List<String> selectedForms, String masterDimName, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        HashMap<String, Map<String, String>> formUnits = new HashMap<String, Map<String, String>>();
        int formIdx = 0;
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 20, 30L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10, false), new ThreadPoolExecutor.CallerRunsPolicy());
        for (String formKey : selectedForms) {
            threadPool.execute(new IntegrityThread(dwKeys, formIdx, this.runtimeDataSchemeService, this.runTimeViewController, this.dataDefinitionRuntimeController, this.jdbcTpl, this.emptyZeroCheckHelper, this.dbTypeUtil, taskKey, formKey, period, tempTableName, masterDimName, formUnits));
            double p = this.getProgressRatio(++formIdx, selectedForms.size(), 0.35);
            asyncTaskMonitor.progressAndMessage(0.15 + p, "\u5b8c\u6574\u6027\u68c0\u67e5\u4e2d");
        }
        threadPool.shutdown();
        if (!threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS)) {
            threadPool.shutdownNow();
        }
        return formUnits;
    }

    public Map<String, Map<String, String>> queryFormIsEmptyZero(String tempTableName, String taskKey, String formSchemeKey, String period, List<String> selectedForms, String masterDimName, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        HashMap<String, Map<String, String>> formUnits = new HashMap<String, Map<String, String>>();
        HashMap<String, HashMap<String, List<DataField>>> dicTablesByForm = new HashMap<String, HashMap<String, List<DataField>>>();
        for (int i = 0; i < selectedForms.size(); ++i) {
            String formKey = selectedForms.get(i);
            List fdKeyList = this.runTimeViewController.getFieldKeysInForm(formKey);
            HashMap dicTablesInForm = new HashMap();
            for (String fdKey : fdKeyList) {
                List<DataField> fdList;
                DataField dataField = this.runtimeDataSchemeService.getDataField(fdKey);
                if (dataField == null) continue;
                if (!dicTablesInForm.containsKey(dataField.getDataTableKey())) {
                    fdList = new ArrayList();
                    dicTablesInForm.put(dataField.getDataTableKey(), fdList);
                } else {
                    fdList = (List)dicTablesInForm.get(dataField.getDataTableKey());
                }
                fdList.add(dataField);
            }
            dicTablesByForm.put(formKey, dicTablesInForm);
        }
        int formIdx = 0;
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 20, 30L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10, false), new ThreadPoolExecutor.CallerRunsPolicy());
        for (String formKey : dicTablesByForm.keySet()) {
            threadPool.execute(new IntegrityZeroThread(formIdx, this.jdbcTpl, this.runtimeDataSchemeService, this.emptyZeroCheckHelper, taskKey, formKey, period, tempTableName, masterDimName, dicTablesByForm, formUnits));
            double p = this.getProgressRatio(++formIdx, dicTablesByForm.size(), 0.83);
            asyncTaskMonitor.progressAndMessage(0.15 + p, "\u5b8c\u6574\u6027\u68c0\u67e5\u4e2d");
        }
        threadPool.shutdown();
        if (!threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS)) {
            threadPool.shutdownNow();
        }
        return formUnits;
    }

    public Map<String, Map<String, String>> queryFormIsEmptyByEmptyZero(Map<String, Map<String, String>> formUnitsZero) throws Exception {
        HashMap<String, Map<String, String>> formUnits = new HashMap<String, Map<String, String>>();
        for (Map.Entry<String, Map<String, String>> entry : formUnitsZero.entrySet()) {
            HashMap<String, String> map = new HashMap<String, String>();
            for (Map.Entry<String, String> mapEntry : entry.getValue().entrySet()) {
                if (mapEntry.getValue() != "\u7a7a\u8868") continue;
                map.put(mapEntry.getKey(), mapEntry.getValue());
            }
            formUnits.put(entry.getKey(), map);
        }
        return formUnits;
    }

    public List<String> getHeader(List<String> selectedForms) throws Exception {
        ArrayList<String> columns = new ArrayList<String>();
        columns.add("\u5355\u4f4d\u4ee3\u7801 | \u5355\u4f4d\u540d\u79f0");
        columns.add("\u7f3a\u8868\u6570\u91cf");
        for (String key : selectedForms) {
            FormDefine formDefine = this.runTimeViewController.queryFormById(key);
            if (formDefine != null) {
                columns.add(formDefine.getFormCode() + " | " + formDefine.getTitle());
                continue;
            }
            columns.add(key);
        }
        return columns;
    }

    public List<String> getDWTitleList(List<String> dwKeys, IEntityTable entityTable) throws Exception {
        ArrayList<String> DWTitle = new ArrayList<String>();
        for (String dmKey : dwKeys) {
            IEntityRow byCode;
            StringBuffer dwMC = new StringBuffer();
            if (entityTable != null && (byCode = entityTable.findByEntityKey(dmKey)) != null) {
                dwMC.append(byCode.getCode()).append(" | ").append(byCode.getTitle());
            }
            DWTitle.add(dwMC.toString());
        }
        return DWTitle;
    }

    public List<String> getDWkeys(List<String> dwKeys, IEntityTable entityTable, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        if (dwKeys.size() == 0) {
            List AllEntityRow = entityTable.getAllRows();
            for (IEntityRow e : AllEntityRow) {
                dwKeys.add(e.getEntityKeyData());
                double p = this.getProgressRatio(dwKeys.size(), AllEntityRow.size(), 0.05);
                asyncTaskMonitor.progressAndMessage(0.05 + p, "\u5b8c\u6574\u6027\u68c0\u67e5\u4e2d");
            }
        }
        return dwKeys;
    }

    public List<String> getFormsAllList(String formSchemeKey, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        HashSet<String> formDic = new HashSet<String>();
        List allFormGroups = this.runTimeViewController.getAllFormGroupsInFormScheme(formSchemeKey);
        int formGroupIdx = 0;
        for (FormGroupDefine formGroup : allFormGroups) {
            List allFormsInGroup = this.runTimeViewController.getAllFormsInGroup(formGroup.getKey());
            for (FormDefine fd : allFormsInGroup) {
                if (fd.getFormType() != FormType.FORM_TYPE_FIX && fd.getFormType() != FormType.FORM_TYPE_FLOAT || formDic.contains(fd.getKey())) continue;
                result.add(fd.getKey());
                formDic.add(fd.getKey());
            }
            double p = this.getProgressRatio(++formGroupIdx, allFormGroups.size(), 0.05);
            asyncTaskMonitor.progressAndMessage(0.1 + p, "\u5b8c\u6574\u6027\u68c0\u67e5\u4e2d");
        }
        formDic.clear();
        return result;
    }

    public List<Map<String, List<String>>> getCheckTableData(Map<String, Map<String, String>> formUnitstemp, List<String> dwKeys, List<String> selectedForms, Map<String, Object> entityTableMap) throws Exception {
        ArrayList<Map<String, List<String>>> result = new ArrayList<Map<String, List<String>>>();
        LinkedHashMap rowData = new LinkedHashMap();
        LinkedHashMap rowDataDiff = new LinkedHashMap();
        LinkedHashMap dwKeyDefect = new LinkedHashMap();
        int rowDataIndex = 1;
        int rowDataDiffIndex = 1;
        ArrayList<String> dwKeyDefectList = new ArrayList<String>();
        ArrayList<String> dwKeyDefectDiffList = new ArrayList<String>();
        IEntityTable entityTable = (IEntityTable)entityTableMap.get(this.entityTableMapKey);
        EntityViewData masterEntityView = (EntityViewData)entityTableMap.get(this.masterEntityViewKey);
        IEntityModel entityModel = this.entityMetaService.getEntityModel(masterEntityView.getKey());
        IEntityAttribute bblxField = entityModel.getBblxField();
        for (String dmKey : dwKeys) {
            ArrayList<String> rows = new ArrayList<String>();
            ArrayList<String> tempRows = new ArrayList<String>();
            Integer rCount = 0;
            for (String fromKey : selectedForms) {
                if (formUnitstemp.containsKey(fromKey) && formUnitstemp.get(fromKey).containsKey(dmKey)) {
                    String vaule = formUnitstemp.get(fromKey).get(dmKey);
                    tempRows.add(vaule);
                    Integer n = rCount;
                    Integer n2 = rCount = Integer.valueOf(rCount + 1);
                    continue;
                }
                tempRows.add(null);
            }
            if (rCount <= 0) continue;
            StringBuffer dwMC = new StringBuffer();
            String bblx = "";
            if (entityTable != null) {
                IEntityRow entityRow = entityTable.findByEntityKey(dmKey);
                if (entityRow == null) continue;
                if (!bblxField.getCode().isEmpty() && entityRow.getAsString(bblxField.getCode()) != null) {
                    bblx = entityRow.getAsString(bblxField.getCode());
                }
                dwMC.append(entityRow.getCode()).append(" | ").append(entityRow.getTitle());
            }
            rows.add(dwMC.toString());
            rows.add(rCount.toString());
            rows.addAll(tempRows);
            if (!bblx.equals("1")) {
                dwKeyDefectList.add(dmKey);
                rowData.put(Integer.toString(rowDataIndex), rows);
                ++rowDataIndex;
            }
            rowDataDiff.put(Integer.toString(rowDataDiffIndex), rows);
            ++rowDataDiffIndex;
            dwKeyDefectDiffList.add(dmKey);
        }
        dwKeyDefect.put("dwKeyList", dwKeyDefectList);
        dwKeyDefect.put("dwKeyDiffList", dwKeyDefectDiffList);
        result.add(rowData);
        result.add(rowDataDiff);
        result.add(dwKeyDefect);
        return result;
    }

    public Map<String, Object> getEntityTable(String formSchemeKey, String period, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        HashMap<String, Object> result = new HashMap<String, Object>();
        IEntityTable entityTable = null;
        EntityViewData masterEntityView = this.jtableParamService.getDwEntity(formSchemeKey);
        asyncTaskMonitor.progressAndMessage(0.02, "\u5b8c\u6574\u6027\u68c0\u67e5\u4e2d");
        if (null == masterEntityView) {
            throw new NotFoundEntityException(new String[]{"\u672a\u627e\u5230\u5355\u4f4d\u4e3b\u4f53"});
        }
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        entityTable = this.getEntityTable(formSchemeKey, period, executorContext);
        asyncTaskMonitor.progressAndMessage(0.04, "\u5b8c\u6574\u6027\u68c0\u67e5\u4e2d");
        result.put(this.entityTableMapKey, entityTable);
        result.put(this.masterEntityViewKey, masterEntityView);
        return result;
    }

    private IEntityTable getEntityTable(String formSchemeKey, String period, ExecutorContext executorContext) throws Exception {
        IEntityQuery query = this.entityQueryHelper.getEntityQuery(formSchemeKey, period);
        return this.entityQueryHelper.buildEntityTable(query, formSchemeKey, false);
    }

    public double getProgressRatio(int current, int total, double ratio) {
        double p = Double.valueOf(current) / Double.valueOf(total) * ratio;
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(p));
    }
}

