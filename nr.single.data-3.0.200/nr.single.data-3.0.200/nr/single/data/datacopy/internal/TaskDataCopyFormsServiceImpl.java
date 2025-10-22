/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.definition.common.Consts$EntityField
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.io.dataset.IRegionDataSet
 *  com.jiuqi.nr.io.params.base.RegionData
 *  com.jiuqi.nr.io.params.base.TableContext
 *  com.jiuqi.nr.io.params.input.ExpViewFields
 *  com.jiuqi.nr.io.params.output.ExportFieldDefine
 *  javax.annotation.Resource
 */
package nr.single.data.datacopy.internal;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.io.dataset.IRegionDataSet;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.ExpViewFields;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import nr.single.data.bean.TaskCopyContext;
import nr.single.data.datacopy.ITaskDataCopyFormsService;
import nr.single.data.datain.service.ITaskFileBatchImportDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskDataCopyFormsServiceImpl
implements ITaskDataCopyFormsService {
    private static final Logger logger = LoggerFactory.getLogger(TaskDataCopyFormsServiceImpl.class);
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDataDefinitionRuntimeController dataRuntimeController;
    @Resource
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Autowired
    private ITaskFileBatchImportDataService batchImportService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String copyFormDatas(TaskCopyContext context, String formSchemeKey, String periodCode, String oldformScheme, String oldPeriod, String copyDataforms, String copyErrorInfoforms, AsyncTaskMonitor monitor) throws Exception {
        String errorInfo = "";
        FormSchemeDefine formScheme = this.runTimeAuthViewController.getFormScheme(formSchemeKey);
        TaskDefine task = this.runTimeAuthViewController.queryTaskDefine(formScheme.getTaskKey());
        String entityDwDimName = EntityUtils.getId((String)this.getUnitEntityId(task.getDataScheme()));
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(task.getDw());
        entityDwDimName = entityDefine.getDimensionName();
        context.setEntityCompanyType(entityDwDimName);
        context.setEntityId(task.getDw());
        FormSchemeDefine oldFormScheme = this.runTimeAuthViewController.getFormScheme(oldformScheme);
        TaskDefine oldTask = this.runTimeAuthViewController.queryTaskDefine(oldFormScheme.getTaskKey());
        String oldEntityDwDimName = EntityUtils.getId((String)this.getUnitEntityId(oldTask.getDataScheme()));
        IEntityDefine oldEntityDefine = this.entityMetaService.queryEntity(oldTask.getDw());
        oldEntityDwDimName = oldEntityDefine.getDimensionName();
        context.setOldEntityCompanyType(oldEntityDwDimName);
        context.setOldEntityId(oldTask.getDw());
        ArrayList copyForms = new ArrayList();
        List allForms = this.runTimeAuthViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        HashMap<String, FormDefine> formMap = new HashMap<String, FormDefine>();
        for (FormDefine form : allForms) {
            formMap.put(form.getFormCode(), form);
        }
        List oldAllForms = this.runTimeAuthViewController.queryAllFormDefinesByFormScheme(oldformScheme);
        HashMap<String, FormDefine> oldFormMap = new HashMap<String, FormDefine>();
        for (FormDefine form : oldAllForms) {
            oldFormMap.put(form.getFormCode(), form);
        }
        if (StringUtils.isNotEmpty((String)copyDataforms)) {
            FormDefine forms;
            for (String formCode : forms = copyDataforms.split(";")) {
                if (!formMap.containsKey(formCode)) continue;
                copyForms.add(formMap.get(formCode));
            }
        } else {
            copyForms.addAll(allForms);
        }
        this.openTempDataTable(context);
        try {
            for (int i = 0; i < copyForms.size(); ++i) {
                FormDefine form;
                form = (FormDefine)copyForms.get(i);
                if (monitor != null) {
                    monitor.progressAndMessage(0.2 + 0.6 / (double)copyForms.size(), "\u8868\u5355\uff1a" + form.getFormCode());
                }
                if (form.getFormType() == FormType.FORM_TYPE_NEWFMDM || form.getFormType() == FormType.FORM_TYPE_ENTITY || !oldFormMap.containsKey(form.getFormCode())) continue;
                FormDefine oldForm = (FormDefine)oldFormMap.get(form.getFormCode());
                try {
                    this.copyFormData(context, form, periodCode, oldForm, oldPeriod, monitor);
                    continue;
                }
                catch (Exception ex) {
                    errorInfo = "\u590d\u5236\u8868\u5355\u51fa\u9519\uff1a" + form.getFormCode() + "" + ex.getMessage();
                    logger.error(errorInfo, ex);
                }
            }
        }
        finally {
            this.closeTempDataTable(context);
        }
        return errorInfo;
    }

    private String copyFormData(TaskCopyContext context, FormDefine formDefine, String periodCode, FormDefine oldformDefine, String oldPeriod, AsyncTaskMonitor monitor) throws Exception {
        String errorInfo = "";
        List regions = this.runTimeAuthViewController.getAllRegionsInForm(formDefine.getKey());
        List oldRegions = this.runTimeAuthViewController.getAllRegionsInForm(oldformDefine.getKey());
        HashMap<Integer, DataRegionDefine> oldRegionsMap = new HashMap<Integer, DataRegionDefine>();
        for (DataRegionDefine region : oldRegions) {
            if (region.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
                oldRegionsMap.put(-1, region);
                continue;
            }
            if (region.getRegionKind() == DataRegionKind.DATA_REGION_ROW_LIST) {
                oldRegionsMap.put(region.getRegionTop(), region);
                continue;
            }
            if (region.getRegionKind() != DataRegionKind.DATA_REGION_COLUMN_LIST) continue;
            oldRegionsMap.put(region.getRegionLeft(), region);
        }
        for (DataRegionDefine region : regions) {
            DataRegionDefine oldRegion = null;
            if (region.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
                oldRegion = (DataRegionDefine)oldRegionsMap.get(-1);
            } else if (region.getRegionKind() == DataRegionKind.DATA_REGION_ROW_LIST) {
                oldRegion = (DataRegionDefine)oldRegionsMap.get(region.getRegionTop());
            } else if (region.getRegionKind() == DataRegionKind.DATA_REGION_COLUMN_LIST) {
                oldRegion = (DataRegionDefine)oldRegionsMap.get(region.getRegionLeft());
            }
            if (oldRegion == null) continue;
            this.copyRegionData(context, formDefine, region, periodCode, oldformDefine, oldRegion, oldPeriod, monitor);
        }
        return errorInfo;
    }

    private String copyRegionData(TaskCopyContext context, FormDefine formDefine, DataRegionDefine region, String periodCode, FormDefine oldformDefine, DataRegionDefine oldRegion, String oldPeriod, AsyncTaskMonitor monitor) throws Exception {
        String errorInfo = "";
        ArrayList<String> fieldsArr = new ArrayList<String>();
        ArrayList<String> keyFieldList = new ArrayList<String>();
        if (context.getCopyParam().getDimensionSet() != null && context.getCopyParam().getDimensionSet().size() > 0) {
            for (String entityName : context.getCopyParam().getDimensionSet().keySet()) {
                fieldsArr.add(entityName);
            }
        } else {
            fieldsArr.add(context.getEntityCompanyType());
            fieldsArr.add("DATATIME");
        }
        keyFieldList.addAll(fieldsArr);
        FormDefine dstformDefine = formDefine;
        IRegionDataSet srcDataSet = this.getExportRegionData(context, oldformDefine, oldRegion, oldPeriod, monitor);
        HashMap<String, Integer> srcFieldIdMap = new HashMap<String, Integer>();
        HashMap<String, Object> srcFieldMap = new HashMap<String, Object>();
        for (int i = 0; i < srcDataSet.getFieldDataList().size(); ++i) {
            ExportFieldDefine field = (ExportFieldDefine)srcDataSet.getFieldDataList().get(i);
            String fieldCode = this.getTrimFieldCode(field.getCode());
            srcFieldIdMap.put(fieldCode, i);
            srcFieldMap.put(fieldCode, field);
        }
        List fieldKeys = this.runTimeAuthViewController.getAllFieldsByLinksInRegion(region.getKey());
        for (String fieldKey : fieldKeys) {
            FieldDefine field = this.dataRuntimeController.queryFieldDefine(fieldKey);
            TableDefine table = this.dataRuntimeController.queryTableDefine(field.getOwnerTableKey());
            String fieldCode = table.getCode() + "." + field.getCode();
            if (!srcFieldIdMap.containsKey(fieldCode = this.getTrimFieldCode(fieldCode))) continue;
            fieldsArr.add(table.getCode() + "." + field.getCode());
        }
        IRegionDataSet dstDataSet = this.getImportRegionData(context, formDefine, region, periodCode, fieldsArr, monitor);
        ArrayList<ExportFieldDefine> copyFields = new ArrayList<ExportFieldDefine>();
        HashMap<String, ExportFieldDefine> copyFieldMap = new HashMap<String, ExportFieldDefine>();
        for (ExportFieldDefine dstfield : dstDataSet.getFieldDataList()) {
            String fieldCode = this.getTrimFieldCode(dstfield.getCode());
            if (!srcFieldIdMap.containsKey(fieldCode)) continue;
            copyFields.add(dstfield);
            copyFieldMap.put(dstfield.getCode(), dstfield);
        }
        int copyRowCount = 0;
        if (copyFields.size() > 0) {
            while (srcDataSet.hasNext()) {
                List floatDatas2 = (List)srcDataSet.next();
                if (floatDatas2.size() == 0) {
                    logger.info("\u590d\u5236\u6570\u636e\uff1a\u8868\u5355" + dstformDefine.getFormCode() + ",\u6570\u636e\u533a\u57df" + region.getTitle() + "\u6570\u636e\u96c6\u65e0\u6570\u636e\uff1a");
                    break;
                }
                if (srcFieldIdMap.containsKey("DW")) {
                    FormDefine newForm;
                    String orgCode = (String)floatDatas2.get((Integer)srcFieldIdMap.get("DW"));
                    FormDefine oldForm = this.runTimeAuthViewController.queryFormById(oldRegion.getFormKey(), orgCode, context.getOldEntityId());
                    if (oldForm == null || (newForm = this.runTimeAuthViewController.queryFormById(region.getFormKey(), orgCode, context.getEntityId())) == null) continue;
                }
                ArrayList<String> listRow = new ArrayList<String>();
                for (String fieldCode : fieldsArr) {
                    if (keyFieldList.indexOf(fieldCode = this.getTrimFieldCode(fieldCode)) < 0 && !copyFieldMap.containsKey(fieldCode) && !srcFieldIdMap.containsKey(fieldCode)) {
                        listRow.add(null);
                        continue;
                    }
                    String fieldValue = null;
                    if (srcFieldIdMap.containsKey(fieldCode)) {
                        fieldValue = floatDatas2.get((Integer)srcFieldIdMap.get(fieldCode));
                    } else if (fieldCode.equalsIgnoreCase("DATATIME")) {
                        fieldValue = periodCode;
                    } else if (fieldCode.equalsIgnoreCase(context.getEntityCompanyType())) {
                        if (srcFieldIdMap.containsKey(context.getOldEntityCompanyType())) {
                            fieldValue = floatDatas2.get((Integer)srcFieldIdMap.get(context.getOldEntityCompanyType()));
                        } else if (srcFieldIdMap.containsKey("MDCODE")) {
                            fieldValue = floatDatas2.get((Integer)srcFieldIdMap.get("MDCODE"));
                        } else if (srcFieldIdMap.containsKey("DW")) {
                            fieldValue = floatDatas2.get((Integer)srcFieldIdMap.get("DW"));
                        }
                    }
                    listRow.add(fieldValue);
                }
                DimensionValueSet dimSet = dstDataSet.importDatas(listRow);
                ++copyRowCount;
            }
        } else {
            logger.info("\u8be5\u6570\u636e\u533a\u57df\u672a\u5339\u914d\u5230\u540c\u6807\u8bc6\u7684\u6307\u6807:" + dstformDefine.getFormCode() + "," + region.getTitle());
        }
        if (copyRowCount > 0) {
            dstDataSet.commit();
            logger.info("\u590d\u5236\u6570\u636e\uff1a\u63d0\u4ea4\u884c\u6570:" + copyRowCount + "," + region.getTitle() + ",\u5b57\u6bb5\u6570:" + copyFields.size());
        }
        return errorInfo;
    }

    private String getTrimFieldCode(String srcFieldCode) {
        String fieldCode = srcFieldCode;
        int idPos = fieldCode.indexOf(".");
        if (idPos >= 0) {
            String tableName = fieldCode.substring(0, idPos);
            if ((idPos = (fieldCode = fieldCode.substring(idPos + 1, fieldCode.length())).indexOf(tableName)) >= 0) {
                fieldCode = fieldCode.substring(idPos + tableName.length() + 1, fieldCode.length());
            }
        }
        return fieldCode;
    }

    private IRegionDataSet getImportRegionData(TaskCopyContext context, FormDefine formDefine, DataRegionDefine region, String periodCode, List<String> fieldsArr, AsyncTaskMonitor monitor) throws Exception {
        FormSchemeDefine formScheme = this.runTimeAuthViewController.getFormScheme(formDefine.getFormScheme());
        TaskDefine task = this.runTimeAuthViewController.queryTaskDefine(formScheme.getTaskKey());
        boolean isFMDM = formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM;
        String entityDwDimName = context.getEntityCompanyType();
        HashMap<String, DimensionValue> dimensionValueMap = new HashMap<String, DimensionValue>();
        String units = this.getUnitsFromList(context);
        if (StringUtils.isNotEmpty((String)units)) {
            DimensionValue newDim = new DimensionValue();
            newDim.setName(entityDwDimName);
            newDim.setValue(units);
            dimensionValueMap.put(entityDwDimName, newDim);
        }
        DimensionValue dateDim = new DimensionValue();
        dateDim.setName("DATATIME");
        dateDim.setValue(periodCode);
        dimensionValueMap.put("DATATIME", dateDim);
        TableContext tableContext = this.batchImportService.getTableContex(dimensionValueMap, task.getKey(), formDefine.getFormScheme(), formDefine.getKey(), monitor.getTaskId());
        if (context.getIntfObjects().containsKey("TempAssistantTable")) {
            TempAssistantTable tempTable = (TempAssistantTable)context.getIntfObjects().get("TempAssistantTable");
            tableContext.setTempAssistantTable(context.getEntityCompanyType(), tempTable);
        }
        if (context.getCopyParam().getCopyMode() == 1) {
            tableContext.setFloatImpOpt(0);
        }
        RegionData regionData = new RegionData();
        regionData.initialize(region);
        IRegionDataSet bathDataSet = this.batchImportService.getImportBatchRegionDataSet(tableContext, regionData.getRegionTop(), fieldsArr);
        return bathDataSet;
    }

    private IRegionDataSet getExportRegionData(TaskCopyContext context, FormDefine formDefine, DataRegionDefine region, String periodCode, AsyncTaskMonitor monitor) throws Exception {
        FormSchemeDefine formScheme = this.runTimeAuthViewController.getFormScheme(formDefine.getFormScheme());
        TaskDefine task = this.runTimeAuthViewController.queryTaskDefine(formScheme.getTaskKey());
        boolean isFMDM = formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM;
        String entityDwDimName = context.getEntityCompanyType();
        HashMap<String, DimensionValue> dimensionValueMap = new HashMap<String, DimensionValue>();
        String units = this.getUnitsFromList(context);
        if (StringUtils.isNotEmpty((String)units)) {
            DimensionValue newDim = new DimensionValue();
            newDim.setName(entityDwDimName);
            newDim.setValue(units);
            dimensionValueMap.put(entityDwDimName, newDim);
        }
        DimensionValue dateDim = new DimensionValue();
        dateDim.setName("DATATIME");
        dateDim.setValue(periodCode);
        dimensionValueMap.put("DATATIME", dateDim);
        String syncTaskID = null;
        if (monitor != null) {
            syncTaskID = monitor.getTaskId();
        }
        TableContext tableContext = this.batchImportService.getTableContex(dimensionValueMap, task.getKey(), formScheme.getKey(), region.getFormKey(), syncTaskID);
        tableContext.setExportBizkeyorder(true);
        tableContext.setExpEntryFields(ExpViewFields.KEY);
        if (isFMDM) {
            ArrayList<String> sortFields = new ArrayList<String>();
            sortFields.add(Consts.EntityField.ENTITY_FIELD_ORDINAL.fieldKey);
            tableContext.setSortFields(sortFields);
        }
        if (context.getIntfObjects().containsKey("TempAssistantTable")) {
            TempAssistantTable tempTable = (TempAssistantTable)context.getIntfObjects().get("TempAssistantTable");
            tableContext.setTempAssistantTable(entityDwDimName, tempTable);
        }
        RegionData regionData = new RegionData();
        regionData.initialize(region);
        IRegionDataSet bathDataSet = this.batchImportService.getBatchExportRegionDataSet(tableContext, regionData);
        return bathDataSet;
    }

    private String getUnitsFromList(TaskCopyContext context) {
        StringBuilder units = new StringBuilder();
        for (String code : context.getCopyUnitCodes()) {
            units.append(code);
            units.append(",");
        }
        if (units.length() > 0) {
            units.delete(units.length() - 1, units.length());
        }
        return units.toString();
    }

    private String getUnitEntityId(String dataSchemekey) {
        List dimList = this.dataSchemeSevice.getDataSchemeDimension(dataSchemekey);
        for (DataDimension dim : dimList) {
            if (dim.getDimensionType() != DimensionType.UNIT) continue;
            return dim.getDimKey();
        }
        return null;
    }

    private void openTempDataTable(TaskCopyContext context) {
        this.closeTempDataTable(context);
        if (context.getCopyUnitCodes().size() > 499 && context.getCopyUnitCodes().size() < 5000) {
            TempAssistantTable tempTable = new TempAssistantTable(context.getCopyUnitCodes(), 6);
            try {
                tempTable.createTempTable();
                tempTable.insertIntoTempTable();
                context.getIntfObjects().put("TempAssistantTable", tempTable);
                logger.info("\u5bfc\u5165JIO\u6570\u636e\uff1a\u521b\u5efa\u4e3b\u4f53\u4e34\u65f6\u8868,\u65f6\u95f4:" + new Date().toString() + "," + tempTable.getSelectSql());
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void closeTempDataTable(TaskCopyContext context) {
        if (context.getIntfObjects().containsKey("TempAssistantTable")) {
            TempAssistantTable tempTable = (TempAssistantTable)context.getIntfObjects().get("TempAssistantTable");
            try {
                tempTable.close();
                context.getIntfObjects().remove("TempAssistantTable");
                logger.info("\u5bfc\u5165JIO\u6570\u636e\uff1a\u91ca\u653e\u4e3b\u4f53\u4e34\u65f6\u8868,\u65f6\u95f4:" + new Date().toString() + "," + tempTable.getSelectSql());
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}

