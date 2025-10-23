/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.table.df.BlockData
 *  com.jiuqi.nr.table.df.IKey
 *  com.jiuqi.nr.table.df.Index
 *  com.jiuqi.nvwa.core.automation.annotation.AutomationType
 *  com.jiuqi.nvwa.core.automation.annotation.ExportOperation
 *  com.jiuqi.nvwa.core.automation.annotation.ImportOperation
 *  com.jiuqi.nvwa.core.automation.annotation.MetaOperation
 *  com.jiuqi.nvwa.core.automation.annotation.QueryOperation
 *  com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationParameter
 *  com.jiuqi.nvwa.framework.automation.bean.ExecuteContext
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationParameterValueModeEnum
 *  com.jiuqi.nvwa.framework.automation.exception.AutomationException
 *  com.jiuqi.nvwa.framework.automation.exception.AutomationExecuteException
 *  com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker
 *  com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker
 *  com.jiuqi.nvwa.framework.automation.result.DatasetColumnInfo
 *  com.jiuqi.nvwa.framework.automation.result.DatasetResult
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.server.util.ParameterConvertor
 *  com.jiuqi.nvwa.framework.parameter.server.web.compatible.ParameterControlDataItemDTO
 *  com.jiuqi.nvwa.framework.parameter.server.web.compatible.ParameterControlDataReqDTO
 *  com.jiuqi.nvwa.framework.parameter.server.web.compatible.ParameterController
 *  org.json.JSONObject
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.dafafill.automation;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.dafafill.model.ConditionField;
import com.jiuqi.nr.dafafill.model.DFDimensionValue;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import com.jiuqi.nr.dafafill.model.DataFillDataQueryInfo;
import com.jiuqi.nr.dafafill.model.DataFillDataResult;
import com.jiuqi.nr.dafafill.model.DataFillModel;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.CellType;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.model.enums.ModelType;
import com.jiuqi.nr.dafafill.model.table.DataFillBaseCell;
import com.jiuqi.nr.dafafill.model.table.DataFillIndexData;
import com.jiuqi.nr.dafafill.model.table.DataFillIndexInfo;
import com.jiuqi.nr.dafafill.model.table.DataFillZBIndexData;
import com.jiuqi.nr.dafafill.service.IDFDimensionQueryFieldParser;
import com.jiuqi.nr.dafafill.service.IDataFillDataEnvImportExportService;
import com.jiuqi.nr.dafafill.service.IDataFillDataEnvService;
import com.jiuqi.nr.dafafill.service.IDataFillDataService;
import com.jiuqi.nr.dafafill.service.ParameterBuilderHelp;
import com.jiuqi.nr.table.df.BlockData;
import com.jiuqi.nr.table.df.IKey;
import com.jiuqi.nr.table.df.Index;
import com.jiuqi.nvwa.core.automation.annotation.AutomationType;
import com.jiuqi.nvwa.core.automation.annotation.ExportOperation;
import com.jiuqi.nvwa.core.automation.annotation.ImportOperation;
import com.jiuqi.nvwa.core.automation.annotation.MetaOperation;
import com.jiuqi.nvwa.core.automation.annotation.QueryOperation;
import com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationParameter;
import com.jiuqi.nvwa.framework.automation.bean.ExecuteContext;
import com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum;
import com.jiuqi.nvwa.framework.automation.enums.AutomationParameterValueModeEnum;
import com.jiuqi.nvwa.framework.automation.exception.AutomationException;
import com.jiuqi.nvwa.framework.automation.exception.AutomationExecuteException;
import com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker;
import com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker;
import com.jiuqi.nvwa.framework.automation.result.DatasetColumnInfo;
import com.jiuqi.nvwa.framework.automation.result.DatasetResult;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.server.util.ParameterConvertor;
import com.jiuqi.nvwa.framework.parameter.server.web.compatible.ParameterControlDataItemDTO;
import com.jiuqi.nvwa.framework.parameter.server.web.compatible.ParameterControlDataReqDTO;
import com.jiuqi.nvwa.framework.parameter.server.web.compatible.ParameterController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@AutomationType(category="nvwa-datafill", id="com.jiuqi.nr.datafill", title="\u81ea\u5b9a\u4e49\u5f55\u5165\u6a21\u677f", icon="icon16_SHU_A_NW_S2")
public class DataFillAutomationTestType {
    static final String IMPROT_DATA_PARAM_NAME = "DATASET";
    @Autowired
    private IDFDimensionQueryFieldParser dfDimensionQueryFieldParser;
    @Autowired
    private IDataFillDataService dataFillDataService;
    @Autowired
    private ParameterBuilderHelp parameterBuilderHelp;
    @Autowired
    private ParameterController parameterController;
    @Autowired
    private IDataFillDataEnvService dataFillDataEnvService;
    @Autowired
    private IDataFillDataEnvImportExportService dataFillDataEnvImportExportService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @QueryOperation
    public IOperationInvoker<DatasetResult> query() throws AutomationException {
        return this.getDatasetResultIOperationInvoker();
    }

    @ExportOperation
    public IOperationInvoker<DatasetResult> export() {
        return this.getDatasetResultIOperationInvoker();
    }

    @ImportOperation
    public IOperationInvoker<DatasetResult> importData() {
        return (automationInstance, executeContext) -> {
            try {
                MultipartFile file = executeContext.getParameterValueAsMultipartFile(IMPROT_DATA_PARAM_NAME);
                DataFillModel model = this.dataFillDataService.getModelByDefinition(automationInstance.getGuid(), "zh-CN");
                if (model != null) {
                    TaskState state;
                    DataFillDataQueryInfo queryInfo = this.getQueryInfoFromModel(executeContext, model);
                    AsyncTaskInfo taskInfo = this.dataFillDataEnvImportExportService.importData(queryInfo, file);
                    while ((state = this.asyncTaskManager.queryTaskState(taskInfo.getId())) == TaskState.PROCESSING || state == TaskState.WAITING) {
                        Thread.sleep(200L);
                    }
                    switch (state) {
                        case ERROR: 
                        case CANCELED: 
                        case CANCELING: 
                        case OVERTIME: {
                            throw new AutomationExecuteException("\u5bfc\u5165\u5931\u8d25");
                        }
                    }
                    DataFillDataResult dataFillDataResult = this.dataFillDataEnvService.query(queryInfo);
                    if (dataFillDataResult.isSuccess()) {
                        MemoryDataSet<DatasetColumnInfo> memory = this.dataFillData2Memory(dataFillDataResult);
                        return new DatasetResult(memory);
                    }
                    throw new AutomationExecuteException("\u5bfc\u5165\u540e\u67e5\u8be2\u5931\u8d25");
                }
                throw new AutomationExecuteException("\u6a21\u578b\u4e3a\u7a7a");
            }
            catch (Exception e) {
                throw new AutomationExecuteException((Throwable)e);
            }
        };
    }

    private DataFillDataQueryInfo getQueryInfoFromModel(ExecuteContext executeContext, DataFillModel model) throws Exception {
        DataFillDataQueryInfo queryInfo = new DataFillDataQueryInfo();
        DataFillContext dataFillContext = new DataFillContext();
        dataFillContext.setModel(model);
        DataFillContext context = new DataFillContext();
        context.setModel(model);
        ArrayList<DFDimensionValue> dimensionValues = new ArrayList<DFDimensionValue>();
        List<ConditionField> conditionFieldList = model.getConditionFields();
        List<QueryField> queryFieldList = this.dfDimensionQueryFieldParser.getAllQueryFields(context);
        List<ParameterModel> parameterModels = this.parameterBuilderHelp.getParameterModels(dataFillContext);
        for (int i = 0; i < conditionFieldList.size(); ++i) {
            for (QueryField queryField : queryFieldList) {
                if (!conditionFieldList.get(i).getFullCode().equals(queryField.getFullCode())) continue;
                if (model.getModelType() == ModelType.TASK && !this.dfDimensionQueryFieldParser.isMasterMultipleVersion(context) && queryField.getFieldType() == FieldType.PERIOD) {
                    conditionFieldList.remove(i);
                    continue;
                }
                String paramValue = executeContext.getParameterValue(parameterModels.get(i).getName());
                DFDimensionValue dimensionValue = new DFDimensionValue();
                dimensionValue.setValues(paramValue);
                dimensionValue.setName(queryField.getCode());
                dimensionValues.add(dimensionValue);
            }
        }
        context.setDimensionValues(dimensionValues);
        queryInfo.setContext(context);
        return queryInfo;
    }

    private IOperationInvoker<DatasetResult> getDatasetResultIOperationInvoker() {
        return (automationInstance, executeContext) -> {
            try {
                DataFillModel model = this.dataFillDataService.getModelByDefinition(automationInstance.getGuid(), "zh-CN");
                if (model != null) {
                    DataFillDataQueryInfo queryInfo = this.getQueryInfoFromModel(executeContext, model);
                    DataFillDataResult dataFillDataResult = this.dataFillDataEnvService.query(queryInfo);
                    if (dataFillDataResult.isSuccess()) {
                        MemoryDataSet<DatasetColumnInfo> memory = this.dataFillData2Memory(dataFillDataResult);
                        return new DatasetResult(memory);
                    }
                    throw new AutomationExecuteException("\u67e5\u8be2\u5931\u8d25");
                }
                throw new AutomationExecuteException("\u6a21\u578b\u4e3a\u7a7a");
            }
            catch (Exception e) {
                throw new AutomationExecuteException(e.getMessage(), (Throwable)e);
            }
        };
    }

    private MemoryDataSet<DatasetColumnInfo> dataFillData2Memory(DataFillDataResult fillDataResult) {
        Column column;
        int i;
        MemoryDataSet dataset = new MemoryDataSet(DatasetColumnInfo.class);
        Index columns = fillDataResult.getTable().columns();
        Index rows = fillDataResult.getTable().rows();
        Object[] colHeaders = rows.getSources();
        HashSet<Integer> removedCols = new HashSet<Integer>();
        for (i = 0; i < colHeaders.length; ++i) {
            DatasetColumnInfo info = new DatasetColumnInfo();
            info.setFormatter("1,234.56");
            DataFillIndexInfo colHeader = (DataFillIndexInfo)colHeaders[i];
            if (colHeader.getHide() == null || !colHeader.getHide().booleanValue()) {
                column = new Column(colHeader.getName(), 6, (Object)info);
                dataset.getMetadata().addColumn(column);
                continue;
            }
            removedCols.add(i);
        }
        for (i = 0; i < columns.size(); ++i) {
            DatasetColumnInfo info;
            Object col = columns.getKey(Integer.valueOf(i));
            if (col instanceof DataFillZBIndexData) {
                if (((DataFillZBIndexData)col).getHide() == null || !((DataFillZBIndexData)col).getHide().booleanValue()) {
                    info = new DatasetColumnInfo();
                    info.setFormatter("1,234.56");
                    column = new Column(((DataFillZBIndexData)col).getTitle(), this.CellType2DataTypes(((DataFillZBIndexData)col).getCellType()), ((DataFillZBIndexData)col).getTitle(), (Object)info);
                    dataset.getMetadata().addColumn(column);
                    continue;
                }
                removedCols.add(colHeaders.length + i);
                continue;
            }
            if (!(col instanceof DataFillIndexData)) continue;
            info = new DatasetColumnInfo();
            info.setFormatter("1,234.56");
            column = new Column(((DataFillIndexData)col).getTitle(), this.FieldType2DataTypes(((DataFillIndexData)col).getType()), ((DataFillIndexData)col).getTitle(), (Object)info);
            dataset.getMetadata().addColumn(column);
        }
        int size = colHeaders.length + columns.size() - removedCols.size();
        int colAdj = 0;
        BlockData data = fillDataResult.getTable().getData();
        for (int i2 = 0; i2 < data.size(); ++i2) {
            Object[] dataBuf = new Object[size];
            Object row = rows.getKey(Integer.valueOf(i2));
            if (row instanceof IKey) {
                for (int j = 0; j < ((IKey)row).length(); ++j) {
                    Object element = ((IKey)row).getElement(j);
                    if (!(element instanceof DataFillIndexData)) continue;
                    if (!removedCols.contains(j)) {
                        dataBuf[j - colAdj] = ((DataFillIndexData)element).getTitle();
                        continue;
                    }
                    ++colAdj;
                }
            } else if (row instanceof DataFillIndexData) {
                if (!removedCols.contains(0)) {
                    dataBuf[0] = ((DataFillIndexData)row).getTitle();
                } else {
                    ++colAdj;
                }
            }
            List dataRow = fillDataResult.getTable().getData().getRow(i2);
            for (int j = 0; j < columns.size(); ++j) {
                if (removedCols.contains(j + colHeaders.length)) {
                    ++colAdj;
                    continue;
                }
                dataBuf[j + colHeaders.length - colAdj] = j < dataRow.size() && dataRow.get(j) != null ? ((DataFillBaseCell)dataRow.get(j)).getValue() : "";
            }
            try {
                dataset.add(dataBuf);
            }
            catch (DataSetException e) {
                throw new RuntimeException(e);
            }
            colAdj = 0;
        }
        return dataset;
    }

    private int CellType2DataTypes(CellType cellType) {
        switch (cellType) {
            case NUMBER: 
            case EXPRESSION: {
                return 3;
            }
        }
        return 6;
    }

    private int FieldType2DataTypes(FieldType fieldType) {
        switch (fieldType) {
            case FLOAT_ID: 
            case EXPRESSION: {
                return 3;
            }
        }
        return 6;
    }

    @MetaOperation
    public IMetaInvoker meta() {
        return element -> {
            AutomationMetaInfo metaInfo = new AutomationMetaInfo();
            ArrayList<AutomationParameter> list = new ArrayList<AutomationParameter>();
            try {
                DataFillModel model = this.dataFillDataService.getModelByDefinition(element.getGuid(), "zh-CN");
                if (model != null) {
                    DataFillContext dataFillContext = new DataFillContext();
                    dataFillContext.setModel(model);
                    List<ParameterModel> parameterModels = this.parameterBuilderHelp.getParameterModels(dataFillContext);
                    for (ParameterModel paramModel : parameterModels) {
                        JSONObject json = ParameterConvertor.toJson(null, (ParameterModel)paramModel, (boolean)false);
                        ParameterControlDataReqDTO dataReqDTO = new ParameterControlDataReqDTO();
                        dataReqDTO.setConfig(json.toString());
                        List defaultValues = this.parameterController.getDefaultValues(dataReqDTO);
                        AutomationParameter parameter = new AutomationParameter();
                        parameter.setName(paramModel.getName());
                        parameter.setTitle(paramModel.getTitle());
                        switch (paramModel.getDataType()) {
                            case 6: {
                                parameter.setDataType(AutomationParameterDataTypeEnum.STRING);
                                break;
                            }
                            case 1: {
                                parameter.setDataType(AutomationParameterDataTypeEnum.BOOLEAN);
                                break;
                            }
                            case 3: {
                                parameter.setDataType(AutomationParameterDataTypeEnum.DOUBLE);
                                break;
                            }
                            case 2: {
                                parameter.setDataType(AutomationParameterDataTypeEnum.DATETIME);
                                break;
                            }
                            case 5: {
                                parameter.setDataType(AutomationParameterDataTypeEnum.INTEGER);
                                break;
                            }
                            case 11: {
                                parameter.setDataType(AutomationParameterDataTypeEnum.DATASET);
                                break;
                            }
                            default: {
                                throw new AutomationExecuteException("\u7ef4\u5ea6\u7c7b\u578b\u672a\u77e5");
                            }
                        }
                        parameter.setValueMode(AutomationParameterValueModeEnum.DEFAULT);
                        for (int i = 0; i < defaultValues.size(); ++i) {
                            if (i != 0) continue;
                            parameter.setDefaultValue(((ParameterControlDataItemDTO)defaultValues.get(i)).getId().toString());
                        }
                        list.add(parameter);
                    }
                }
            }
            catch (JQException e) {
                throw new AutomationExecuteException(e.getMessage(), (Throwable)e);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            AutomationParameter datasetParameter = new AutomationParameter(IMPROT_DATA_PARAM_NAME, "\u5bfc\u5165\u6587\u4ef6", AutomationParameterDataTypeEnum.FILE, null);
            datasetParameter.setScopes(new HashSet<String>(Collections.singletonList("import")));
            list.add(datasetParameter);
            metaInfo.setParameterList(list);
            return metaInfo;
        };
    }
}

