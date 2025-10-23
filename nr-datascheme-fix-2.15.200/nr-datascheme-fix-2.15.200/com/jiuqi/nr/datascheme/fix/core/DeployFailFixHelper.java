/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DeployResult
 *  com.jiuqi.nr.datascheme.api.core.DeployStatusEnum
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.internal.deploy.DataSchemeDeployHelper
 *  com.jiuqi.nr.datascheme.internal.deploy.progress.DSProgressCacheService
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDO
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.ProgressItem
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 */
package com.jiuqi.nr.datascheme.fix.core;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DeployResult;
import com.jiuqi.nr.datascheme.api.core.DeployStatusEnum;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.fix.common.DeployExType;
import com.jiuqi.nr.datascheme.fix.common.DeployFixResultType;
import com.jiuqi.nr.datascheme.fix.common.DeployFixType;
import com.jiuqi.nr.datascheme.fix.core.DeployFailFixMethods;
import com.jiuqi.nr.datascheme.fix.core.DeployFixCheckExResult;
import com.jiuqi.nr.datascheme.fix.core.DeployFixDataTable;
import com.jiuqi.nr.datascheme.fix.core.DeployFixDataTableCheckResult;
import com.jiuqi.nr.datascheme.fix.core.DeployFixDsAndNvwaCheckResult;
import com.jiuqi.nr.datascheme.fix.core.DeployFixExtendParam;
import com.jiuqi.nr.datascheme.fix.core.DeployFixParamDTO;
import com.jiuqi.nr.datascheme.fix.core.DeployFixResultDTO;
import com.jiuqi.nr.datascheme.fix.core.DeployFixTableModel;
import com.jiuqi.nr.datascheme.fix.core.DeployFixTableModelCheckResult;
import com.jiuqi.nr.datascheme.fix.core.DeployFixTmCheckSummary;
import com.jiuqi.nr.datascheme.fix.entity.DeployFailFixLogDO;
import com.jiuqi.nr.datascheme.fix.progress.DSFixProgressCacheService;
import com.jiuqi.nr.datascheme.fix.progress.DSFixProgressUpdater;
import com.jiuqi.nr.datascheme.fix.service.IDataSchemeDeployFailAnalysis;
import com.jiuqi.nr.datascheme.fix.service.IDataSchemeDeployFixExtendService;
import com.jiuqi.nr.datascheme.fix.service.Impl.DataSchemeDeployFixLogServiceImpl;
import com.jiuqi.nr.datascheme.fix.utils.ParamModelBuilder;
import com.jiuqi.nr.datascheme.fix.utils.Tools;
import com.jiuqi.nr.datascheme.internal.deploy.DataSchemeDeployHelper;
import com.jiuqi.nr.datascheme.internal.deploy.progress.DSProgressCacheService;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.ProgressItem;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeployFailFixHelper {
    @Autowired
    private Tools tools;
    @Autowired
    private ParamModelBuilder builder;
    @Autowired
    private List<IDataSchemeDeployFailAnalysis> fixAnalysis;
    @Autowired
    private DeployFailFixMethods fixMethods;
    @Autowired
    private IDataSchemeDeployService dataTableDeploy;
    @Autowired
    private DSProgressCacheService progressCacheService;
    @Autowired
    private DSFixProgressCacheService fixProgressCacheService;
    @Autowired
    private DataModelDeployService dataModelDeploy;
    @Autowired
    private DataSchemeDeployFixLogServiceImpl fixLogService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired(required=false)
    private List<IDataSchemeDeployFixExtendService> fixExtendServices;

    public DeployFixDataTable modelCheck(DeployFixDataTable fixDataTable) {
        fixDataTable = this.builder.insertDataTable(fixDataTable);
        fixDataTable = this.builder.insertDataFields(fixDataTable);
        DeployFixDataTableCheckResult checkResult = DeployFailFixHelper.modelParamCheck(fixDataTable);
        fixDataTable.setCheckResult(checkResult);
        return fixDataTable;
    }

    private static DeployFixDataTableCheckResult modelParamCheck(DeployFixDataTable fixDataTable) {
        DeployFixDataTableCheckResult checkResult = new DeployFixDataTableCheckResult();
        checkResult.setDsTable(DeployFailFixHelper.compareDsTable(fixDataTable));
        checkResult.setDsField(DeployFailFixHelper.compareDsField(fixDataTable));
        checkResult.setDsFieldAndDfdi(DeployFailFixHelper.compareDsFieldAndDfdi(fixDataTable));
        checkResult.setDsFieldAttribute(DeployFailFixHelper.compareDsFieldAttribute(fixDataTable));
        return checkResult;
    }

    public List<DeployFixTableModel> modelCheck(List<DeployFixTableModel> deployFixTableModels) {
        ArrayList<DeployFixTableModel> fixTableModels = new ArrayList<DeployFixTableModel>();
        for (DeployFixTableModel fixTableModel : deployFixTableModels) {
            fixTableModel = this.builder.insertTableModel(fixTableModel);
            fixTableModel = this.builder.insertColumnModels(fixTableModel);
            DeployFixTableModelCheckResult checkResult = DeployFailFixHelper.modelParamCheck(fixTableModel);
            fixTableModel.setCheckResult(checkResult);
            fixTableModels.add(fixTableModel);
        }
        return fixTableModels;
    }

    private static DeployFixTableModelCheckResult modelParamCheck(DeployFixTableModel fixTableModel) {
        DeployFixTableModelCheckResult checkResult = new DeployFixTableModelCheckResult();
        checkResult.setNvwaTable(DeployFailFixHelper.compareNvwaTable(fixTableModel));
        checkResult.setNvwaTableAndPt(DeployFailFixHelper.compareNvwaTableAndPt(fixTableModel));
        checkResult.setNvwaColumns(DeployFailFixHelper.compareNvwaColumns(fixTableModel));
        checkResult.setNvwaColumnsAttribute(DeployFailFixHelper.compareNvwaColumnsAttribute(fixTableModel));
        checkResult.setNvwaColumnsAndLogicFields(DeployFailFixHelper.compareNvwaColumnsAndLf(fixTableModel));
        checkResult.setNvwaColumnsAndLogicFieldsAttribute(DeployFailFixHelper.compareNvwaColumnsAndLfAttribute(fixTableModel));
        return checkResult;
    }

    public DeployFixCheckExResult giveErrorType(DeployFixDataTable fixDataTable, List<DeployFixTableModel> fixTableModels) {
        DeployFixCheckExResult checkExResult;
        DeployExType exType = this.getExType(fixDataTable, fixTableModels);
        HashMap<String, String> tmKeyAndLtName = new HashMap<String, String>();
        for (DeployFixTableModel fixTableModel : fixTableModels) {
            String tableModelKey = fixTableModel.getTableModelKey();
            if (fixTableModel.getLogicTable() != null) {
                String logicTableName = fixTableModel.getLogicTable().getName();
                tmKeyAndLtName.put(tableModelKey, logicTableName);
                continue;
            }
            tmKeyAndLtName.put(tableModelKey, null);
        }
        if (exType != null) {
            checkExResult = new DeployFixCheckExResult(exType, tmKeyAndLtName);
        } else {
            exType = DeployExType.UNKNOW_SITUATION;
            checkExResult = new DeployFixCheckExResult(exType, tmKeyAndLtName);
        }
        return checkExResult;
    }

    protected DeployExType getExType(DeployFixDataTable dataTableCheckResult, List<DeployFixTableModel> tableModelCheckResult) {
        IDataSchemeDeployFailAnalysis doAnalysis;
        DeployExType exType = null;
        boolean checkData = this.tools.getCheckData(tableModelCheckResult);
        Iterator<IDataSchemeDeployFailAnalysis> iterator = this.fixAnalysis.iterator();
        while (iterator.hasNext() && (exType = (doAnalysis = iterator.next()).doAnalysis(dataTableCheckResult, tableModelCheckResult, checkData)) == null) {
        }
        if (exType == null) {
            exType = DeployExType.UNKNOW_SITUATION;
        }
        return exType;
    }

    public DeployFixResultDTO doFix(DeployFixParamDTO fixParam, Instant fixTime) {
        DeployFixResultDTO fixResult = this.beginFix(fixParam);
        fixResult.setDataSchemeKey(fixParam.getDataSchemeKey());
        fixResult.setDataTableKey(fixParam.getDataTableKey());
        fixResult.setDataTableCode(fixParam.getDataTableCode());
        fixResult.setDataTableTitle(fixParam.getDataTableTitle());
        fixResult.setExType(fixParam.getExType());
        fixResult.setFixType(fixParam.getFixType());
        fixResult.setTmKeyAndLtName(fixParam.getTmKeyAndLtName());
        DeployFailFixLogDO fixLog = new DeployFailFixLogDO(fixResult, fixTime);
        if (fixLog.getDataTableKey() != null && fixLog.getDataSchemeKey() != null) {
            this.fixLogService.insertFixLog(fixLog);
        }
        return fixResult;
    }

    public DeployFixTmCheckSummary getCheckSummary(List<DeployFixTableModel> fixTableModels) {
        DeployFixTmCheckSummary checkSummary = new DeployFixTmCheckSummary();
        for (DeployFixTableModel fixTableModel : fixTableModels) {
            if (!fixTableModel.getCheckResult().isNvwaColumns()) {
                checkSummary.setAllNvwaColumns(false);
            }
            if (!fixTableModel.getCheckResult().isNvwaColumnsAndLogicFields()) {
                checkSummary.setAllNvwaColumnsAndLogicFields(false);
            }
            if (!fixTableModel.getCheckResult().isNvwaColumnsAttribute()) {
                checkSummary.setAllNvwaColumnsAttribute(false);
            }
            if (fixTableModel.getCheckResult().isNvwaColumnsAndLogicFieldsAttribute()) continue;
            checkSummary.setAllNvwaColumnsAndLogicFieldsAttribute(false);
        }
        return checkSummary;
    }

    public DeployFixDsAndNvwaCheckResult getCheckResult(DeployFixDataTable fixDataTable, List<DeployFixTableModel> fixTableModels) {
        DeployFixDsAndNvwaCheckResult checkResult = new DeployFixDsAndNvwaCheckResult();
        ArrayList<DesignColumnModelDefine> columnModels = new ArrayList<DesignColumnModelDefine>();
        for (DeployFixTableModel fixTableModel : fixTableModels) {
            columnModels.addAll(fixTableModel.getDesColumnModels());
        }
        checkResult.setDsAndNvwaField(DeployFailFixHelper.compareDsAndNvwaField(fixDataTable.getDataFields(), columnModels));
        checkResult.setDsAndNvwaFieldAttribute(DeployFailFixHelper.compareDsAndNvwaFieldAttribute(fixDataTable.getDataFields(), columnModels));
        return checkResult;
    }

    private static boolean compareDsTable(DeployFixDataTable fixDataTable) {
        boolean isRight = fixDataTable.getDesDataTable() != null && fixDataTable.getRtdataTable() != null;
        return isRight;
    }

    private static boolean compareDsField(DeployFixDataTable fixDataTable) {
        Map<String, DesignDataField> desDataFieldMap = fixDataTable.getDesDataFields().stream().collect(Collectors.toMap(Basic::getKey, v -> v));
        Map<String, DataField> rtDataFieldMap = fixDataTable.getDataFields().stream().collect(Collectors.toMap(Basic::getKey, v -> v));
        Set<String> desDataFieldKeySet = desDataFieldMap.keySet();
        Set<String> rtdataFieldKeySet = rtDataFieldMap.keySet();
        Set addFieldKeySet = DataSchemeDeployHelper.diff(desDataFieldKeySet, rtdataFieldKeySet);
        Set deleteFieldKeySet = DataSchemeDeployHelper.diff(rtdataFieldKeySet, desDataFieldKeySet);
        boolean isRight = addFieldKeySet.size() == 0 && deleteFieldKeySet.size() == 0;
        return isRight;
    }

    private static boolean compareDsFieldAndDfdi(DeployFixDataTable fixDataTable) {
        Map<String, DataFieldDeployInfoDO> dataFieldDeployInfoMap = fixDataTable.getDeployInfos().stream().collect(Collectors.toMap(DataFieldDeployInfoDO::getDataFieldKey, v -> v));
        Map<String, DataField> dataFieldMap = fixDataTable.getDataFields().stream().collect(Collectors.toMap(Basic::getKey, v -> v));
        Set<String> dataFieldDeployInfoKeySet = dataFieldDeployInfoMap.keySet();
        Set<String> dataFieldKeySet = dataFieldMap.keySet();
        Set addFieldKeySet = DataSchemeDeployHelper.diff(dataFieldDeployInfoKeySet, dataFieldKeySet);
        Set deleteKeySet = DataSchemeDeployHelper.diff(dataFieldKeySet, dataFieldDeployInfoKeySet);
        boolean isRight = addFieldKeySet.size() == 0 && deleteKeySet.size() == 0;
        return isRight;
    }

    private static boolean compareDsFieldAttribute(DeployFixDataTable fixDataTable) {
        boolean isRight = true;
        Map<String, DesignDataField> desDataFieldMap = fixDataTable.getDesDataFields().stream().collect(Collectors.toMap(Basic::getKey, v -> v));
        Map<String, DataField> rtDataFieldMap = fixDataTable.getDataFields().stream().collect(Collectors.toMap(Basic::getKey, v -> v));
        Set<String> desDataFieldKeySet = desDataFieldMap.keySet();
        Set<String> rtdataFieldKeySet = rtDataFieldMap.keySet();
        Set unionFieldKeySet = DataSchemeDeployHelper.union(desDataFieldKeySet, rtdataFieldKeySet);
        for (String key : unionFieldKeySet) {
            DataField rtDataField = rtDataFieldMap.get(key);
            DesignDataField desDataField = desDataFieldMap.get(key);
            if (DeployFailFixHelper.compareDataFields(desDataField, rtDataField)) {
                isRight = true;
                continue;
            }
            isRight = false;
            break;
        }
        return isRight;
    }

    public static boolean compareNvwaTable(DeployFixTableModel fixTableModel) {
        boolean isRight = fixTableModel.getDesTableModelDefine() != null && fixTableModel.getTableModelDefine() != null;
        return isRight;
    }

    public static boolean compareNvwaTableAndPt(DeployFixTableModel fixTableModel) {
        boolean isRight = fixTableModel.getTableModelDefine() != null && fixTableModel.getLogicTable() != null;
        return isRight;
    }

    private static boolean compareNvwaColumns(DeployFixTableModel fixTableModel) {
        Map<String, ColumnModelDefine> rtColumnModelMap = fixTableModel.getColumnModels().stream().collect(Collectors.toMap(IModelDefineItem::getID, v -> v));
        Map<String, DesignColumnModelDefine> desColumnModelMap = fixTableModel.getDesColumnModels().stream().collect(Collectors.toMap(IModelDefineItem::getID, v -> v));
        Set<String> rtColumnModelKeySet = rtColumnModelMap.keySet();
        Set<String> desColumnModelKeySet = desColumnModelMap.keySet();
        Set difColumnModelKeySet = DataSchemeDeployHelper.diff(desColumnModelKeySet, rtColumnModelKeySet);
        Set difDesColumnModelKeySet = DataSchemeDeployHelper.diff(rtColumnModelKeySet, desColumnModelKeySet);
        boolean isRight = difColumnModelKeySet.size() == 0 && difDesColumnModelKeySet.size() == 0;
        return isRight;
    }

    private static boolean compareNvwaColumnsAttribute(DeployFixTableModel fixTableModel) {
        boolean isRight = true;
        Map<String, ColumnModelDefine> rtColumnModelMap = fixTableModel.getColumnModels().stream().collect(Collectors.toMap(IModelDefineItem::getID, v -> v));
        Map<String, DesignColumnModelDefine> desColumnModelMap = fixTableModel.getDesColumnModels().stream().collect(Collectors.toMap(IModelDefineItem::getID, v -> v));
        Set<String> rtColumnModelKeySet = rtColumnModelMap.keySet();
        Set<String> desColumnModelKeySet = desColumnModelMap.keySet();
        Set unionColumnModelKeySet = DataSchemeDeployHelper.union(desColumnModelKeySet, rtColumnModelKeySet);
        for (String key : unionColumnModelKeySet) {
            ColumnModelDefine columnModel = rtColumnModelMap.get(key);
            DesignColumnModelDefine desColumnModel = desColumnModelMap.get(key);
            if (DeployFailFixHelper.compareColumns(desColumnModel, columnModel)) {
                isRight = true;
                continue;
            }
            isRight = false;
            break;
        }
        return isRight;
    }

    private static boolean compareNvwaColumnsAndLf(DeployFixTableModel fixTableModel) {
        boolean isRight;
        Map<String, DesignColumnModelDefine> desColumnMap = fixTableModel.getDesColumnModels().stream().collect(Collectors.toMap(ColumnModelDefine::getName, v -> v));
        Map<String, ColumnModelDefine> columnModelMap = fixTableModel.getColumnModels().stream().collect(Collectors.toMap(ColumnModelDefine::getName, v -> v));
        if (fixTableModel.getLogicTable() != null) {
            Map<String, LogicField> logicFieldMap = fixTableModel.getLogicFields().stream().collect(Collectors.toMap(LogicField::getFieldName, v -> v));
            Set<String> columnModelKeySet = columnModelMap.keySet();
            Set<String> logicFieldKetSet = logicFieldMap.keySet();
            Set<String> desColumnModelKeySet = desColumnMap.keySet();
            Set difColumnModelKeySet = DataSchemeDeployHelper.diff(columnModelKeySet, logicFieldKetSet);
            Set difLogicFieldKeySet = DataSchemeDeployHelper.diff(logicFieldKetSet, columnModelKeySet);
            isRight = difColumnModelKeySet.size() == 0 && difLogicFieldKeySet.size() == 0 ? true : (difColumnModelKeySet.size() == 0 && difLogicFieldKeySet.size() != 0 ? logicFieldKetSet.size() > columnModelKeySet.size() && desColumnModelKeySet.size() > logicFieldKetSet.size() : false);
        } else {
            isRight = fixTableModel.getLogicFields() == null && fixTableModel.getColumnModels() == null;
        }
        return isRight;
    }

    private static boolean compareNvwaColumnsAndLfAttribute(DeployFixTableModel fixTableModel) {
        boolean isRight = true;
        Map<String, ColumnModelDefine> columnModelMap = fixTableModel.getColumnModels().stream().collect(Collectors.toMap(ColumnModelDefine::getName, v -> v));
        if (fixTableModel.getLogicFields() != null) {
            Map<String, LogicField> logicFieldMap = fixTableModel.getLogicFields().stream().collect(Collectors.toMap(LogicField::getFieldName, v -> v));
            Set<String> columnModelKeySet = columnModelMap.keySet();
            Set<String> logicFieldKetSet = logicFieldMap.keySet();
            Set unionKeySet = DataSchemeDeployHelper.union(columnModelKeySet, logicFieldKetSet);
            for (String name : unionKeySet) {
                ColumnModelDefine columnModel = columnModelMap.get(name);
                LogicField logicField = logicFieldMap.get(name);
                if (DeployFailFixHelper.compareColumnsAndLogicField(columnModel.getColumnType(), logicField.getDataTypeName())) {
                    isRight = true;
                    continue;
                }
                isRight = false;
                break;
            }
        } else {
            isRight = fixTableModel.getLogicFields() == null && fixTableModel.getColumnModels() == null;
        }
        return isRight;
    }

    private static boolean compareDsAndNvwaField(List<DataFieldDO> dataFields, List<DesignColumnModelDefine> columnModelDefines) {
        List filterColumnModel = columnModelDefines.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<DesignColumnModelDefine>(Comparator.comparing(u -> u.getCode()))), ArrayList::new));
        Map<String, DesignColumnModelDefine> columnModelMap = filterColumnModel.stream().collect(Collectors.toMap(IModelDefineItem::getCode, v -> v));
        Map<String, DataField> dataFieldMap = dataFields.stream().collect(Collectors.toMap(Basic::getCode, v -> v));
        Set<String> columnModelKeySet = columnModelMap.keySet();
        Set<String> dataFieldKeySet = dataFieldMap.keySet();
        Set addKeySet = DataSchemeDeployHelper.diff(columnModelKeySet, dataFieldKeySet);
        Set difKeySet = DataSchemeDeployHelper.diff(dataFieldKeySet, columnModelKeySet);
        boolean isRight = addKeySet.size() == 0 && difKeySet.size() == 0;
        return isRight;
    }

    private static boolean compareDsAndNvwaFieldAttribute(List<DataFieldDO> dataFields, List<DesignColumnModelDefine> columnModelDefines) {
        boolean isRight = true;
        List filterColumnModel = columnModelDefines.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<DesignColumnModelDefine>(Comparator.comparing(u -> u.getCode()))), ArrayList::new));
        Map<String, DesignColumnModelDefine> columnModelMap = filterColumnModel.stream().collect(Collectors.toMap(IModelDefineItem::getCode, v -> v));
        Map<String, DataField> dataFieldMap = dataFields.stream().collect(Collectors.toMap(Basic::getCode, v -> v));
        Set<String> columnModelKeySet = columnModelMap.keySet();
        Set<String> dataFieldKeySet = dataFieldMap.keySet();
        Set unionKeySet = DataSchemeDeployHelper.union(columnModelKeySet, dataFieldKeySet);
        for (String code : unionKeySet) {
            DesignColumnModelDefine columnModel = columnModelMap.get(code);
            DataField dataField = dataFieldMap.get(code);
            if (DeployFailFixHelper.compareDataFieldAndColumns(dataField, columnModel)) {
                isRight = true;
                continue;
            }
            isRight = false;
            break;
        }
        return isRight;
    }

    private static boolean compareBaseObj(Object obj1, Object obj2) {
        if (obj1 == obj2) {
            return true;
        }
        if (null == obj1 || null == obj2) {
            return false;
        }
        return obj1.equals(obj2);
    }

    private static boolean compareColumnsAndLogicField(ColumnModelType columnModelType, String logicFieldType) {
        if (columnModelType == ColumnModelType.STRING && logicFieldType.equals("VARCHAR")) {
            return true;
        }
        if (columnModelType == ColumnModelType.BIGDECIMAL && logicFieldType.equals("DECIMAL")) {
            return true;
        }
        if (columnModelType == ColumnModelType.INTEGER && logicFieldType.equals("INT")) {
            return true;
        }
        if (columnModelType == ColumnModelType.BOOLEAN && logicFieldType.equals("DECIMAL")) {
            return true;
        }
        if (columnModelType == ColumnModelType.DATETIME && logicFieldType.equals("DATETIME")) {
            return true;
        }
        if (columnModelType == ColumnModelType.ATTACHMENT && logicFieldType.equals("VARCHAR")) {
            return true;
        }
        return columnModelType == ColumnModelType.CLOB && logicFieldType.equals("LONGTEXT");
    }

    public static boolean compareDataFieldAndColumns(DataField dataField, DesignColumnModelDefine columnModel) {
        if (!DeployFailFixHelper.compareBaseObj(dataField.getCode(), columnModel.getCode())) {
            return false;
        }
        if (!DeployFailFixHelper.compareBaseObj(dataField.getPrecision(), columnModel.getPrecision())) {
            return false;
        }
        if (dataField.getDataFieldType().toColumnModelType() != columnModel.getColumnType()) {
            return false;
        }
        return DeployFailFixHelper.compareBaseObj(dataField.getNullable(), columnModel.isNullAble());
    }

    private static boolean compareColumns(DesignColumnModelDefine desColumnModel, ColumnModelDefine columnModel) {
        if (!DeployFailFixHelper.compareBaseObj(desColumnModel.getName(), columnModel.getName())) {
            return false;
        }
        if (!DeployFailFixHelper.compareBaseObj(desColumnModel.getPrecision(), columnModel.getPrecision())) {
            return false;
        }
        if (!DeployFailFixHelper.compareBaseObj(desColumnModel.getColumnType(), columnModel.getColumnType())) {
            return false;
        }
        return DeployFailFixHelper.compareBaseObj(desColumnModel.isNullAble(), columnModel.isNullAble());
    }

    public static boolean DeployFailCompareDataField(ColumnModelDefine ColumnModel, LogicField logicField) {
        if (!DeployFailFixHelper.compareBaseObj(ColumnModel.getName(), logicField.getFieldName())) {
            return false;
        }
        return DeployFailFixHelper.compareColumnsAndLogicField(ColumnModel.getColumnType(), logicField.getDataTypeName());
    }

    public static boolean compareDataFields(DesignDataField desDataField, DataField dataField) {
        if (!DeployFailFixHelper.compareBaseObj(desDataField.getCode(), dataField.getCode())) {
            return false;
        }
        if (!DeployFailFixHelper.compareBaseObj(desDataField.getPrecision(), dataField.getPrecision())) {
            return false;
        }
        if (!DeployFailFixHelper.compareBaseObj(desDataField.getDataFieldType(), dataField.getDataFieldType())) {
            return false;
        }
        return DeployFailFixHelper.compareBaseObj(desDataField.getNullable(), dataField.getNullable());
    }

    private static List<DeployFixType> fixTypeList() {
        ArrayList<DeployFixType> fixTypes = new ArrayList<DeployFixType>();
        fixTypes.add(DeployFixType.DELETE_PT);
        fixTypes.add(DeployFixType.RENAME_PT);
        fixTypes.add(DeployFixType.DELETE_PT_ONLINE);
        fixTypes.add(DeployFixType.DELETE_PARAMS);
        fixTypes.add(DeployFixType.DELETE_PT_NRT);
        fixTypes.add(DeployFixType.DELETE_PT_NRT_ONLINE);
        fixTypes.add(DeployFixType.RENAME_PT_DELETE_NRT);
        fixTypes.add(DeployFixType.DDT_COVER_DRT);
        fixTypes.add(DeployFixType.DELETE_NVWA_PARAMS);
        fixTypes.add(DeployFixType.DO_NOT_FIX);
        fixTypes.add(DeployFixType.CLEAN_GARBAGE_DATA);
        return fixTypes;
    }

    private static List<DeployExType> exTypeList() {
        ArrayList<DeployExType> exTypes = new ArrayList<DeployExType>();
        exTypes.add(DeployExType.MISS_DRT_NDT);
        exTypes.add(DeployExType.HAS_DATA_MISS_DRT_NDT);
        exTypes.add(DeployExType.MISS_PT);
        exTypes.add(DeployExType.MISS_NDT);
        exTypes.add(DeployExType.HAS_DATA_MISS_NDT);
        exTypes.add(DeployExType.MISS_NDT_PT);
        exTypes.add(DeployExType.MISS_NDT_NRT);
        exTypes.add(DeployExType.HAS_DATA_MISS_NDT_NRT);
        exTypes.add(DeployExType.MISS_NDT_NRT_PT);
        exTypes.add(DeployExType.MISS_DRF);
        exTypes.add(DeployExType.MISS_DRF_DFDI);
        exTypes.add(DeployExType.HAS_DATA_MISS_DRF_DFDI);
        exTypes.add(DeployExType.MISS_DRF_PT);
        exTypes.add(DeployExType.HAS_DATA_MISS_DRF_PT);
        exTypes.add(DeployExType.MISS_DRF_PT_DFDI);
        exTypes.add(DeployExType.HAS_DATA_MISS_DRF_PT_DFDI);
        exTypes.add(DeployExType.MISS_DRF_NRC);
        exTypes.add(DeployExType.HAS_DATA_MISS_DRF_NRC);
        exTypes.add(DeployExType.MISS_DRF_NRC_DFDI);
        exTypes.add(DeployExType.HAS_DATA_MISS_DRF_NRC_DFDI);
        exTypes.add(DeployExType.MISS_DRF_NRC_PT);
        exTypes.add(DeployExType.HAS_DATA_MISS_DRF_NRC_PT);
        exTypes.add(DeployExType.MISS_DRF_NRC_PT_DFDI);
        exTypes.add(DeployExType.HAS_DATA_MISS_DRF_NRC_PT_DFDI);
        exTypes.add(DeployExType.MISS_DRF_NDC_NRC);
        exTypes.add(DeployExType.HAS_DATA_MISS_DRF_NDC_NRC);
        exTypes.add(DeployExType.MISS_DRF_NDC_NRC_DFDI);
        exTypes.add(DeployExType.HAS_DATA_MISS_DRF_NDC_NRC_DFDI);
        exTypes.add(DeployExType.FIELD_ATTRIBUTE_SITUATION);
        exTypes.add(DeployExType.UNKNOW_SITUATION);
        return exTypes;
    }

    public List<DeployFixType> getFixParamType(DeployExType exType) {
        ArrayList<DeployFixType> fixParamsType = new ArrayList<DeployFixType>();
        switch (exType) {
            case MISS_DRT_NDT: {
                fixParamsType.add(DeployFixType.DELETE_PT_ONLINE);
                return fixParamsType;
            }
            case HAS_DATA_MISS_DRT_NDT: {
                fixParamsType.add(DeployFixType.RENAME_PT);
                fixParamsType.add(DeployFixType.DELETE_PT_ONLINE);
                return fixParamsType;
            }
            case MISS_PT: {
                fixParamsType.add(DeployFixType.DELETE_NVWA_PARAMS);
                return fixParamsType;
            }
            case MISS_NDT: {
                fixParamsType.add(DeployFixType.DELETE_PT_ONLINE);
                return fixParamsType;
            }
            case HAS_DATA_MISS_NDT: {
                fixParamsType.add(DeployFixType.RENAME_PT);
                fixParamsType.add(DeployFixType.DELETE_PT_ONLINE);
                return fixParamsType;
            }
            case MISS_NDT_PT: {
                fixParamsType.add(DeployFixType.DELETE_PARAMS);
                return fixParamsType;
            }
            case MISS_NDT_NRT: {
                fixParamsType.add(DeployFixType.DELETE_PT_ONLINE);
                return fixParamsType;
            }
            case HAS_DATA_MISS_NDT_NRT: {
                fixParamsType.add(DeployFixType.RENAME_PT);
                fixParamsType.add(DeployFixType.DELETE_PT_ONLINE);
                return fixParamsType;
            }
            case MISS_NDT_NRT_PT: {
                fixParamsType.add(DeployFixType.DELETE_PARAMS);
                return fixParamsType;
            }
            case MISS_DRF_NDC_NRC: {
                fixParamsType.add(DeployFixType.DELETE_PT_ONLINE);
                return fixParamsType;
            }
            case HAS_DATA_MISS_DRF_NDC_NRC: {
                fixParamsType.add(DeployFixType.RENAME_PT);
                fixParamsType.add(DeployFixType.DELETE_PT_ONLINE);
                return fixParamsType;
            }
            case MISS_DRF_NDC_NRC_DFDI: {
                fixParamsType.add(DeployFixType.DELETE_PT_NRT_ONLINE);
                return fixParamsType;
            }
            case HAS_DATA_MISS_DRF_NDC_NRC_DFDI: {
                fixParamsType.add(DeployFixType.RENAME_PT_DELETE_NRT);
                fixParamsType.add(DeployFixType.DELETE_PT_NRT_ONLINE);
                return fixParamsType;
            }
            case MISS_DRF_NRC: 
            case MISS_DRF_NRC_DFDI: {
                fixParamsType.add(DeployFixType.DELETE_PT_ONLINE);
                return fixParamsType;
            }
            case HAS_DATA_MISS_DRF_NRC: 
            case HAS_DATA_MISS_DRF_NRC_DFDI: {
                fixParamsType.add(DeployFixType.RENAME_PT);
                fixParamsType.add(DeployFixType.DELETE_PT_ONLINE);
                return fixParamsType;
            }
            case MISS_DRF_NRC_PT: 
            case MISS_DRF_NRC_PT_DFDI: {
                fixParamsType.add(DeployFixType.DELETE_PT_ONLINE);
                return fixParamsType;
            }
            case HAS_DATA_MISS_DRF_NRC_PT: 
            case HAS_DATA_MISS_DRF_NRC_PT_DFDI: {
                fixParamsType.add(DeployFixType.RENAME_PT);
                fixParamsType.add(DeployFixType.DELETE_PT_ONLINE);
                return fixParamsType;
            }
            case MISS_DRF: {
                fixParamsType.add(DeployFixType.DDT_COVER_DRT);
                return fixParamsType;
            }
            case MISS_DRF_DFDI: {
                fixParamsType.add(DeployFixType.DELETE_PT_ONLINE);
                return fixParamsType;
            }
            case HAS_DATA_MISS_DRF_DFDI: {
                fixParamsType.add(DeployFixType.RENAME_PT);
                fixParamsType.add(DeployFixType.DELETE_PT_ONLINE);
                return fixParamsType;
            }
            case MISS_DRF_PT: 
            case MISS_DRF_PT_DFDI: {
                fixParamsType.add(DeployFixType.DELETE_PT_ONLINE);
                return fixParamsType;
            }
            case HAS_DATA_MISS_DRF_PT: 
            case HAS_DATA_MISS_DRF_PT_DFDI: {
                fixParamsType.add(DeployFixType.RENAME_PT);
                fixParamsType.add(DeployFixType.DELETE_PT_ONLINE);
                return fixParamsType;
            }
            case FIELD_ATTRIBUTE_SITUATION: {
                fixParamsType.add(DeployFixType.RENAME_PT);
                return fixParamsType;
            }
            case GARBAGE_DATA: {
                fixParamsType.add(DeployFixType.CLEAN_GARBAGE_DATA);
                return fixParamsType;
            }
        }
        fixParamsType.add(DeployFixType.DO_NOT_FIX);
        return fixParamsType;
    }

    public EnumMap<DeployExType, DeployFixType> firstStrategy() {
        EnumMap<DeployExType, DeployFixType> fixTypeMap = new EnumMap<DeployExType, DeployFixType>(DeployExType.class);
        block10: for (DeployExType exType : DeployFailFixHelper.exTypeList()) {
            switch (exType) {
                case MISS_DRT_NDT: 
                case MISS_NDT: 
                case MISS_NDT_NRT: 
                case MISS_DRF_NDC_NRC: 
                case MISS_DRF_NRC: 
                case MISS_DRF_NRC_DFDI: 
                case MISS_DRF_NRC_PT: 
                case MISS_DRF_NRC_PT_DFDI: 
                case MISS_DRF_DFDI: 
                case MISS_DRF_PT: 
                case MISS_DRF_PT_DFDI: {
                    fixTypeMap.put(exType, DeployFixType.DELETE_PT);
                    continue block10;
                }
                case HAS_DATA_MISS_DRT_NDT: 
                case HAS_DATA_MISS_NDT: 
                case HAS_DATA_MISS_NDT_NRT: 
                case HAS_DATA_MISS_DRF_NDC_NRC: 
                case HAS_DATA_MISS_DRF_NRC: 
                case HAS_DATA_MISS_DRF_NRC_DFDI: 
                case HAS_DATA_MISS_DRF_NRC_PT: 
                case HAS_DATA_MISS_DRF_NRC_PT_DFDI: 
                case HAS_DATA_MISS_DRF_DFDI: 
                case HAS_DATA_MISS_DRF_PT: 
                case HAS_DATA_MISS_DRF_PT_DFDI: 
                case FIELD_ATTRIBUTE_SITUATION: {
                    fixTypeMap.put(exType, DeployFixType.RENAME_PT);
                    continue block10;
                }
                case MISS_PT: {
                    fixTypeMap.put(exType, DeployFixType.DELETE_NVWA_PARAMS);
                    continue block10;
                }
                case MISS_NDT_PT: 
                case MISS_NDT_NRT_PT: {
                    fixTypeMap.put(exType, DeployFixType.DELETE_PARAMS);
                    continue block10;
                }
                case MISS_DRF_NDC_NRC_DFDI: {
                    fixTypeMap.put(exType, DeployFixType.DELETE_PT_NRT);
                    continue block10;
                }
                case HAS_DATA_MISS_DRF_NDC_NRC_DFDI: {
                    fixTypeMap.put(exType, DeployFixType.RENAME_PT_DELETE_NRT);
                    continue block10;
                }
                case MISS_DRF: {
                    fixTypeMap.put(exType, DeployFixType.DDT_COVER_DRT);
                    continue block10;
                }
                case GARBAGE_DATA: {
                    fixTypeMap.put(exType, DeployFixType.CLEAN_GARBAGE_DATA);
                    continue block10;
                }
            }
            fixTypeMap.put(exType, DeployFixType.DO_NOT_FIX);
        }
        return fixTypeMap;
    }

    public static DeployFixType getFixType(int fixParamValue) {
        for (DeployFixType fixType : DeployFailFixHelper.fixTypeList()) {
            if (fixType.getValue() != fixParamValue) continue;
            return fixType;
        }
        return null;
    }

    protected DeployFixResultDTO beginFix(DeployFixParamDTO fixParam) {
        DeployFixResultDTO fixResult = new DeployFixResultDTO();
        Map<Object, Object> renameInfo = new HashMap();
        DeployFixResultType fixResultType = null;
        DeployFixExtendParam fixExtendParam = null;
        int ret = 0;
        switch (fixParam.getFixType()) {
            case DELETE_PT: {
                try {
                    this.fixMethods.deletePtMethod(fixParam.getDataSchemeKey(), fixParam.getDataTableKey(), fixParam.getTmKeyAndLtName(), fixParam.getDataTableCode());
                    fixExtendParam = new DeployFixExtendParam(fixParam.getDataSchemeKey(), fixParam.getDataTableKey(), fixParam.getTmKeyAndLtName().keySet(), false);
                    this.deployFixExtendPreAction(fixExtendParam);
                }
                catch (Exception e) {
                    ++ret;
                    fixResultType = DeployFixResultType.FIX_FAIL;
                }
                if (ret != 0) break;
                fixResultType = this.dataTableDeploy(fixParam.getDataSchemeKey(), fixParam.getDataTableKey(), fixParam.getDataTableCode());
                this.deployFixExtendAction(fixExtendParam);
                break;
            }
            case DELETE_PT_ONLINE: {
                renameInfo = this.fixMethods.renamePtMethod(fixParam.getDataSchemeKey(), fixParam.getDataTableKey(), fixParam.getTmKeyAndLtName(), fixParam.getDataTableCode());
                fixExtendParam = new DeployFixExtendParam(fixParam.getDataSchemeKey(), fixParam.getDataTableKey(), fixParam.getTmKeyAndLtName().keySet(), true);
                this.deployFixExtendPreAction(fixExtendParam);
                fixResultType = this.dataTableDeploy(fixParam.getDataSchemeKey(), fixParam.getDataTableKey(), fixParam.getDataTableCode());
                this.deployFixExtendAction(fixExtendParam);
                break;
            }
            case RENAME_PT: {
                renameInfo = this.fixMethods.renamePtMethod(fixParam.getDataSchemeKey(), fixParam.getDataTableKey(), fixParam.getTmKeyAndLtName(), fixParam.getDataTableCode());
                fixExtendParam = new DeployFixExtendParam(fixParam.getDataSchemeKey(), fixParam.getDataTableKey(), fixParam.getTmKeyAndLtName().keySet(), true);
                this.deployFixExtendPreAction(fixExtendParam);
                fixResultType = this.dataTableDeploy(fixParam.getDataSchemeKey(), fixParam.getDataTableKey(), fixParam.getDataTableCode());
                fixResult.setTransfer(this.fixMethods.doTransferData(renameInfo));
                this.deployFixExtendAction(fixExtendParam);
                break;
            }
            case DELETE_PARAMS: {
                this.fixMethods.deleteAllRuntimeParams(fixParam.getDataSchemeKey(), fixParam.getDataTableKey(), fixParam.getDataTableCode(), fixParam.getTmKeyAndLtName());
                fixExtendParam = new DeployFixExtendParam(fixParam.getDataSchemeKey(), fixParam.getDataTableKey(), fixParam.getTmKeyAndLtName().keySet(), false);
                this.deployFixExtendPreAction(fixExtendParam);
                fixResultType = this.dataTableDeploy(fixParam.getDataSchemeKey(), fixParam.getDataTableKey(), fixParam.getDataTableCode());
                this.deployFixExtendAction(fixExtendParam);
                break;
            }
            case DELETE_PT_NRT: {
                try {
                    this.fixMethods.deletePtAndNvwaRtParams(fixParam.getTmKeyAndLtName());
                    fixExtendParam = new DeployFixExtendParam(fixParam.getDataSchemeKey(), fixParam.getDataTableKey(), fixParam.getTmKeyAndLtName().keySet(), false);
                    this.deployFixExtendPreAction(fixExtendParam);
                }
                catch (Exception e) {
                    ++ret;
                    fixResultType = DeployFixResultType.FIX_FAIL;
                }
                if (ret != 0) break;
                fixResultType = this.tableModelDeploy(fixParam.getTmKeyAndLtName());
                this.deployFixExtendAction(fixExtendParam);
                break;
            }
            case DELETE_PT_NRT_ONLINE: {
                renameInfo = this.fixMethods.renamePtAndDeleteNvwaRtParams(fixParam.getTmKeyAndLtName());
                fixExtendParam = new DeployFixExtendParam(fixParam.getDataSchemeKey(), fixParam.getDataTableKey(), fixParam.getTmKeyAndLtName().keySet(), true);
                this.deployFixExtendPreAction(fixExtendParam);
                fixResultType = this.dataTableDeploy(fixParam.getDataSchemeKey(), fixParam.getDataTableKey(), fixParam.getDataTableCode());
                this.deployFixExtendAction(fixExtendParam);
                break;
            }
            case RENAME_PT_DELETE_NRT: {
                renameInfo = this.fixMethods.renamePtAndDeleteNvwaRtParams(fixParam.getTmKeyAndLtName());
                fixExtendParam = new DeployFixExtendParam(fixParam.getDataSchemeKey(), fixParam.getDataTableKey(), fixParam.getTmKeyAndLtName().keySet(), true);
                this.deployFixExtendPreAction(fixExtendParam);
                fixResultType = this.dataTableDeploy(fixParam.getDataSchemeKey(), fixParam.getDataTableKey(), fixParam.getDataTableCode());
                this.deployFixExtendAction(fixExtendParam);
                fixResult.setTransfer(this.fixMethods.doTransferData(renameInfo));
                break;
            }
            case DELETE_NVWA_PARAMS: {
                this.fixMethods.deleteAllNvwaRtParams(fixParam.getTmKeyAndLtName());
                fixResultType = this.tableModelDeploy(fixParam.getTmKeyAndLtName());
                break;
            }
            case DDT_COVER_DRT: {
                this.fixMethods.rtDfCoverDesDf(fixParam.getDataTableKey());
                fixResultType = this.dataTableDeploy(fixParam.getDataSchemeKey(), fixParam.getDataTableKey(), fixParam.getDataTableCode());
                break;
            }
            case CLEAN_GARBAGE_DATA: {
                try {
                    this.fixMethods.cleanGarbageData(fixParam.getDataSchemeKey(), fixParam.getDataTableKey(), fixParam.getTmKeyAndLtName(), fixParam.getDataTableCode());
                }
                catch (Exception e) {
                    ++ret;
                    fixResultType = DeployFixResultType.FIX_FAIL;
                }
                if (ret != 0) break;
                fixResultType = DeployFixResultType.FIX_SUCCESS;
                break;
            }
            case DO_NOT_FIX: {
                fixResultType = DeployFixResultType.NOT_FIXED;
                break;
            }
            default: {
                fixResultType = DeployFixResultType.UNKNOW_FIXSCHEME;
            }
        }
        fixResult.setFixResultType(fixResultType);
        if (renameInfo.keySet().isEmpty() || renameInfo.keySet() == null) {
            fixResult.setNewTableName(null);
        } else {
            fixResult.setNewTableName(this.toList(renameInfo.keySet()));
        }
        return fixResult;
    }

    public DSFixProgressUpdater getDSFixProgressUpdater(String dataSchemeKey, Consumer<ProgressItem> progressConsumer) {
        ProgressItem progressItem = this.fixProgressCacheService.getProgress(dataSchemeKey);
        if (null == progressItem || progressItem.isFinished() || progressItem.isFailed()) {
            progressItem = new ProgressItem();
            progressItem.setProgressId(dataSchemeKey);
            progressItem.addStepTitle("\u4fee\u590d\u4e2d");
            this.fixProgressCacheService.setProgress(progressItem.getProgressId(), progressItem);
        }
        return new DSFixProgressUpdater(progressItem, progressConsumer);
    }

    public DSFixProgressUpdater getAllDsFixProgressUpdater(String progressId, Consumer<ProgressItem> progressConsumer) {
        ProgressItem progressItem = this.fixProgressCacheService.getProgress(progressId);
        if (null == progressItem || progressItem.isFinished() || progressItem.isFailed()) {
            progressItem = new ProgressItem();
            progressItem.setProgressId(progressId);
            progressItem.addStepTitle("\u68c0\u67e5\u4e2d");
            progressItem.addStepTitle("\u4fee\u590d\u4e2d");
            this.fixProgressCacheService.setProgress(progressItem.getProgressId(), progressItem);
        }
        return new DSFixProgressUpdater(progressItem, progressConsumer);
    }

    public DeployFixResultType dataTableDeploy(String dataSchemeKey, String dataTableKey, String dataTableCode) {
        this.fixMethods.refreshCache(dataSchemeKey, dataTableKey, dataTableCode);
        DeployFixResultType fixResultType = null;
        int ret = 0;
        try {
            this.dataTableDeploy.deployDataTable(dataTableKey, true);
        }
        catch (JQException e) {
            ++ret;
            fixResultType = DeployFixResultType.FIX_FAIL;
        }
        if (ret == 0) {
            fixResultType = DeployFixResultType.FIX_SUCCESS;
        }
        return fixResultType;
    }

    public DeployFixResultType tableModelDeploy(Map<String, String> tmKeyAndLtName) {
        DeployFixResultType fixResultType = null;
        int ret = 0;
        for (String key : tmKeyAndLtName.keySet()) {
            try {
                this.dataModelDeploy.deployTable(key);
            }
            catch (Exception e) {
                ++ret;
                fixResultType = DeployFixResultType.FIX_FAIL;
                break;
            }
        }
        if (ret == 0) {
            fixResultType = DeployFixResultType.FIX_SUCCESS;
        }
        return fixResultType;
    }

    public DeployResult deployDataScheme(String dataSchemeKey, DSFixProgressUpdater fixProgressUpdater) {
        DeployResult deployResult = this.dataTableDeploy.deployDataScheme(dataSchemeKey, p -> this.updateProgressItem(dataSchemeKey, (ProgressItem)p), null);
        if (deployResult.getDeployState() == DeployStatusEnum.SUCCESS && deployResult.getCheckState()) {
            this.tools.updateDeployStatus(dataSchemeKey, DeployStatusEnum.SUCCESS, deployResult);
            fixProgressUpdater.nextStep();
            fixProgressUpdater.end(true);
        } else if (deployResult.getDeployState() == DeployStatusEnum.FAIL || !deployResult.getCheckState()) {
            this.tools.updateDeployStatus(dataSchemeKey, DeployStatusEnum.FAIL, deployResult);
            fixProgressUpdater.nextStep();
            fixProgressUpdater.end(false);
        }
        return deployResult;
    }

    public void deployDataScheme(Set<String> dataSchemeKeys, DSFixProgressUpdater fixProgressUpdater) {
        for (String dataSchemeKey : dataSchemeKeys) {
            DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(dataSchemeKey);
            fixProgressUpdater.update(String.format("%s:%s[%s]", "\u6b63\u5728\u53d1\u5e03\u6570\u636e\u65b9\u6848", dataScheme.getCode(), dataScheme.getTitle()), 99);
            DeployResult deployResult = this.dataTableDeploy.deployDataScheme(dataSchemeKey, p -> this.updateProgressItem(dataSchemeKey, (ProgressItem)p), null);
            if (deployResult.getDeployState() == DeployStatusEnum.FAIL || !deployResult.getCheckState()) {
                this.tools.updateDeployStatus(dataSchemeKey, DeployStatusEnum.FAIL, deployResult);
                fixProgressUpdater.nextStep();
                fixProgressUpdater.end(false);
                break;
            }
            this.tools.updateDeployStatus(dataSchemeKey, DeployStatusEnum.SUCCESS, deployResult);
        }
        if (this.tools.getAllDeployFailDsKey().size() == 0 && this.tools.getAllDeployFailDsKey().isEmpty()) {
            fixProgressUpdater.nextStep();
            fixProgressUpdater.end(true);
        }
    }

    public void updateProgressItem(String dataSchemeKey, ProgressItem progressItem) {
        if (progressItem == null) {
            return;
        }
        this.progressCacheService.setProgress(dataSchemeKey, progressItem);
    }

    private List<String> toList(Set<String> newTableName) {
        ArrayList<String> newTableNames = new ArrayList<String>();
        newTableNames.addAll(newTableName);
        return newTableNames;
    }

    public static String getFixSchemeDesc(int fixParamValue) {
        for (DeployFixType fixType : DeployFailFixHelper.fixTypeList()) {
            if (fixType.getValue() != fixParamValue) continue;
            return fixType.getTitle();
        }
        return null;
    }

    public static String getexDesxc(int exTypeValue) {
        for (DeployExType exType : DeployFailFixHelper.exTypeList()) {
            if (exType.getValue() != exTypeValue) continue;
            return exType.getTitle();
        }
        return null;
    }

    public void deployFixExtendPreAction(DeployFixExtendParam fixExtendParam) {
        if (this.fixExtendServices.size() > 0) {
            this.fixExtendServices.get(0).clearOldModel(fixExtendParam);
        }
    }

    public void deployFixExtendAction(DeployFixExtendParam fixExtendParam) {
        if (this.fixExtendServices.size() > 0) {
            fixExtendParam.setTableModelKey(this.tools.getTableModelKeyByDtKey(fixExtendParam.getDataTableKey()));
            this.fixExtendServices.get(0).reDeploy(fixExtendParam);
        }
    }
}

