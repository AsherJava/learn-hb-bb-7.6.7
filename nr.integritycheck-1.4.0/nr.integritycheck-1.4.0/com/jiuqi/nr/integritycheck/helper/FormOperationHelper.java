/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datacheckcommon.helper.EntityQueryHelper
 *  com.jiuqi.nr.dataentry.internal.service.FormGroupProvider
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.exception.NotFoundEntityException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.integritycheck.helper;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datacheckcommon.helper.EntityQueryHelper;
import com.jiuqi.nr.dataentry.internal.service.FormGroupProvider;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.integritycheck.asynctask.CanceledInfo;
import com.jiuqi.nr.integritycheck.common.IntegrityCheckParam;
import com.jiuqi.nr.integritycheck.common.IntegrityZeroThread;
import com.jiuqi.nr.integritycheck.common.MapWrapper;
import com.jiuqi.nr.integritycheck.helper.EmptyZeroCheckHelper;
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
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FormOperationHelper {
    public String entityTableMapKey = "entityTable";
    public String masterEntityViewKey = "masterEntityView";
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private FormGroupProvider formGroupProvider;
    @Resource
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private EmptyZeroCheckHelper emptyZeroCheckHelper;
    @Autowired
    private EntityQueryHelper entityQueryHelper;
    @Autowired
    private JdbcTemplate jdbcTpl;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private IEntityMetaService metaService;
    @Value(value="${jiuqi.nr.icr.checkNum:1000}")
    private int icrCheckNum;

    public Map<String, Map<MapWrapper, String>> queryFormIsEmptyZero(String tempTableName, IntegrityCheckParam param, String masterDimName, String period, AsyncTaskMonitor asyncTaskMonitor, double curProgress, double ratio) throws Exception {
        HashMap<String, Map<MapWrapper, String>> formUnits = new HashMap<String, Map<MapWrapper, String>>();
        HashMap<String, HashMap<String, List<DataField>>> dicTablesByForm = new HashMap<String, HashMap<String, List<DataField>>>();
        List<String> selectedForms = param.getFormKeys();
        LinkedHashMap<String, String> tmpFieldAndDbFieldNameWrapper = new LinkedHashMap<String, String>();
        for (String formKey : selectedForms) {
            List fdKeyList = this.runTimeViewController.getFieldKeysInForm(formKey);
            HashMap<String, List> dicTablesInForm = new HashMap<String, List>();
            for (String fdKey : fdKeyList) {
                DataTable dataTable;
                String[] bizFdKeys;
                DataField dataField = this.runtimeDataSchemeService.getDataField(fdKey);
                if (dataField == null) continue;
                List fdList = dicTablesInForm.computeIfAbsent(dataField.getDataTableKey(), k -> new ArrayList());
                fdList.add(dataField);
                if (!tmpFieldAndDbFieldNameWrapper.isEmpty() || 0 == (bizFdKeys = (dataTable = this.dataSchemeService.getDataTable(dataField.getDataTableKey())).getBizKeys()).length) continue;
                for (String bizFdKey : bizFdKeys) {
                    String dimName;
                    DataField keyField = this.dataSchemeService.getDataField(bizFdKey);
                    String string = "DATATIME".equals(keyField.getCode()) ? "DATATIME" : (dimName = StringUtils.isNotEmpty((String)keyField.getRefDataEntityKey()) ? this.metaService.getDimensionName(keyField.getRefDataEntityKey()) : null);
                    if (null == dimName) continue;
                    if (masterDimName.equals(dimName) || "DATATIME".equals(dimName)) {
                        tmpFieldAndDbFieldNameWrapper.put(dimName, keyField.getCode());
                        continue;
                    }
                    tmpFieldAndDbFieldNameWrapper.put(dimName, keyField.getCode());
                }
            }
            dicTablesByForm.put(formKey, dicTablesInForm);
        }
        int formIdx = 0;
        String taskKey = param.getTaskKey();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 20, 30L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10, false), new ThreadPoolExecutor.CallerRunsPolicy());
        for (String formKey : dicTablesByForm.keySet()) {
            if (asyncTaskMonitor.isCancel()) {
                threadPool.shutdown();
                CanceledInfo canceledInfo = new CanceledInfo();
                canceledInfo.setFormNum(formIdx);
                asyncTaskMonitor.canceling("task_cancel_info", (Object)canceledInfo);
                return formUnits;
            }
            threadPool.execute(new IntegrityZeroThread(formIdx, this.jdbcTpl, this.runtimeDataSchemeService, this.emptyZeroCheckHelper, taskKey, formKey, period, tempTableName, masterDimName, dicTablesByForm, formUnits, tmpFieldAndDbFieldNameWrapper));
            double p = this.getProgressRatio(++formIdx, dicTablesByForm.size(), ratio);
            asyncTaskMonitor.progressAndMessage(curProgress + p, "\u5b8c\u6574\u6027\u68c0\u67e5\u4e2d");
        }
        threadPool.shutdown();
        if (!threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS)) {
            threadPool.shutdownNow();
        }
        return formUnits;
    }

    public Map<String, Map<MapWrapper, String>> queryFormIsEmptyZero(IntegrityCheckParam param, List<DimensionCombination> dimensionCombinations, AsyncTaskMonitor asyncTaskMonitor, double curProgress, double ratio) throws Exception {
        HashMap<String, Map<MapWrapper, String>> formUnitsZero = new HashMap<String, Map<MapWrapper, String>>();
        List<String> forms = param.getFormKeys();
        for (String form : forms) {
            HashMap<MapWrapper, String> dimsResMap = new HashMap<MapWrapper, String>();
            for (DimensionCombination dimensionCombination : dimensionCombinations) {
                HashMap<String, String> dimNameValueMap = new HashMap<String, String>();
                for (String dimName : dimensionCombination.getNames()) {
                    dimNameValueMap.put(dimName, (String)dimensionCombination.getValue(dimName));
                }
                dimsResMap.put(new MapWrapper(dimNameValueMap), "\u7a7a\u8868");
            }
            formUnitsZero.put(form, dimsResMap);
        }
        for (int i = 0; i < forms.size(); ++i) {
            int batchindex = dimensionCombinations.size() / this.icrCheckNum + 1;
            for (int batch = 0; batch < batchindex; ++batch) {
                ArrayList<DimensionCombination> batchDims = new ArrayList<DimensionCombination>();
                if ((batch + 1) * this.icrCheckNum > dimensionCombinations.size()) {
                    batchDims.addAll(dimensionCombinations.subList(batch * this.icrCheckNum, dimensionCombinations.size()));
                } else {
                    batchDims.addAll(dimensionCombinations.subList(batch * this.icrCheckNum, (batch + 1) * this.icrCheckNum));
                }
                HashSet<MapWrapper> allSelectMapWrapper = new HashSet<MapWrapper>();
                DimensionValueSet dimension = new DimensionValueSet();
                for (DimensionCombination batchDim : batchDims) {
                    HashMap<String, String> dimNameValueMap = new HashMap<String, String>();
                    for (String dimName : batchDim.getNames()) {
                        String dimValue = (String)batchDim.getValue(dimName);
                        dimNameValueMap.put(dimName, dimValue);
                        ArrayList<String> dimValues = (ArrayList<String>)dimension.getValue(dimName);
                        if (null == dimValues) {
                            dimValues = new ArrayList<String>();
                            dimension.setValue(dimName, dimValues);
                            dimValues.add(dimValue);
                            continue;
                        }
                        if (dimValues.contains(dimValue)) continue;
                        dimValues.add(dimValue);
                    }
                    allSelectMapWrapper.add(new MapWrapper(dimNameValueMap));
                }
                QueryEnvironment queryEnvironment = new QueryEnvironment();
                com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = new com.jiuqi.np.dataengine.executors.ExecutorContext(this.dataDefinitionRuntimeController);
                ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, param.getFormSchemeKey());
                executorContext.setEnv((IFmlExecEnvironment)environment);
                if (asyncTaskMonitor.isCancel()) {
                    CanceledInfo canceledInfo = new CanceledInfo();
                    canceledInfo.setFormNum(i);
                    asyncTaskMonitor.canceling("task_cancel_info", (Object)canceledInfo);
                    return formUnitsZero;
                }
                this.checkForm(formUnitsZero, forms.get(i), allSelectMapWrapper, dimension, queryEnvironment, executorContext);
                double p = this.getProgressRatio(batch * forms.size() + i + 1, (batch + 1) * forms.size(), ratio);
                asyncTaskMonitor.progressAndMessage(curProgress + p, "\u5b8c\u6574\u6027\u68c0\u67e5\u4e2d");
            }
        }
        return formUnitsZero;
    }

    private void checkForm(Map<String, Map<MapWrapper, String>> formUnitsZero, String formKey, Set<MapWrapper> allSelectMapWrapper, DimensionValueSet dimension, QueryEnvironment queryEnvironment, com.jiuqi.np.dataengine.executors.ExecutorContext executorContext) throws Exception {
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
        ArrayList<FieldDefine> fieldDefines = new ArrayList<FieldDefine>();
        List fieldKeys = this.runTimeViewController.getFieldKeysInForm(formKey);
        if (null == fieldKeys || fieldKeys.isEmpty()) {
            return;
        }
        for (String fieldKey : fieldKeys) {
            FieldDefine fieldDefine = this.runTimeViewController.queryFieldDefine(fieldKey);
            fieldDefines.add(fieldDefine);
            dataQuery.addColumn(fieldDefine);
        }
        dataQuery.setMasterKeys(dimension);
        IReadonlyTable readonlyTable = dataQuery.executeReader(executorContext);
        HashMap<MapWrapper, List> dwDataRowMap = new HashMap<MapWrapper, List>();
        for (int i = 0; i < readonlyTable.getCount(); ++i) {
            IDataRow item = readonlyTable.getItem(i);
            DimensionValueSet rowKeys = item.getRowKeys();
            HashMap<String, String> dimNameValueMap = new HashMap<String, String>();
            for (int j = 0; j < rowKeys.size(); ++j) {
                dimNameValueMap.put(rowKeys.getName(j), (String)rowKeys.getValue(j));
            }
            MapWrapper mapWrapper = new MapWrapper(dimNameValueMap);
            if (!allSelectMapWrapper.contains(mapWrapper)) continue;
            List dataRows = dwDataRowMap.computeIfAbsent(mapWrapper, k -> new ArrayList());
            dataRows.add(item);
        }
        for (MapWrapper mapWrapper : dwDataRowMap.keySet()) {
            boolean zero = true;
            List dataRows = (List)dwDataRowMap.get(mapWrapper);
            for (IDataRow item : dataRows) {
                for (FieldDefine fieldDefine : fieldDefines) {
                    AbstractData value = item.getValue(fieldDefine);
                    if (value.isNull) continue;
                    DataFieldType dataFieldType = ((DataField)fieldDefine).getDataFieldType();
                    if (dataFieldType == DataFieldType.BIGDECIMAL || dataFieldType == DataFieldType.INTEGER) {
                        double v = value.getAsCurrency().doubleValue();
                        if (v == 0.0) continue;
                        zero = false;
                        break;
                    }
                    String v = value.getAsString();
                    if (v.length() <= 0) continue;
                    zero = false;
                    break;
                }
                if (zero) continue;
                break;
            }
            String res = formUnitsZero.get(formKey).get(mapWrapper);
            if (zero) {
                formUnitsZero.get(formKey).replace(mapWrapper, "\u96f6\u8868");
                continue;
            }
            if (null == res) continue;
            formUnitsZero.get(formKey).remove(mapWrapper);
        }
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
            if (null == asyncTaskMonitor) continue;
            double p = this.getProgressRatio(++formGroupIdx, allFormGroups.size(), 0.05);
            asyncTaskMonitor.progressAndMessage(0.1 + p, "\u5b8c\u6574\u6027\u68c0\u67e5\u4e2d");
        }
        formDic.clear();
        return result;
    }

    public Map<String, Object> getEntityTable(String formSchemeKey, String period, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        HashMap<String, Object> result = new HashMap<String, Object>();
        EntityViewData masterEntityView = this.jtableParamService.getDwEntity(formSchemeKey);
        asyncTaskMonitor.progressAndMessage(0.02, "running");
        if (null == masterEntityView) {
            throw new NotFoundEntityException(new String[]{"\u672a\u627e\u5230\u5355\u4f4d\u4e3b\u4f53"});
        }
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        IEntityTable entityTable = this.getEntityTable(formSchemeKey, period, executorContext);
        asyncTaskMonitor.progressAndMessage(0.04, "running");
        result.put(this.entityTableMapKey, entityTable);
        result.put(this.masterEntityViewKey, masterEntityView);
        return result;
    }

    private IEntityTable getEntityTable(String formSchemeKey, String period, ExecutorContext executorContext) throws Exception {
        IEntityQuery query = this.entityQueryHelper.getEntityQuery(formSchemeKey, period);
        return this.entityQueryHelper.buildEntityTable(query, formSchemeKey, false);
    }

    public double getProgressRatio(int current, int total, double ratio) {
        double p = (double)current / (double)total * ratio;
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(p));
    }
}

