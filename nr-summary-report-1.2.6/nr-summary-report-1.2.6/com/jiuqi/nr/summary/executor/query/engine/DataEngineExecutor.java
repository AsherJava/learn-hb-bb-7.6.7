/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.query.result.ColumnInfo
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.xlib.utils.StringUtils
 */
package com.jiuqi.nr.summary.executor.query.engine;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.summary.executor.query.ds.SummaryDSField;
import com.jiuqi.nr.summary.executor.query.ds.SummaryDSModel;
import com.jiuqi.nr.summary.executor.query.engine.EngineQueryParam;
import com.jiuqi.nr.summary.model.PageInfo;
import com.jiuqi.xlib.utils.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class DataEngineExecutor {
    private final IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringBeanUtils.getBean(IDataAccessProvider.class);
    private final IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringBeanUtils.getBean(IDataDefinitionRuntimeController.class);
    private final IRuntimeDataSchemeService iRuntimeDataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);

    public IReadonlyTable getRangeData(DimensionValueSet dimValue, EngineQueryParam queryInfo) throws Exception {
        ExecutorContext context = this.getExecutorContext(dimValue, null);
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery();
        dataQuery.setMasterKeys(dimValue);
        this.addQueryCol(dataQuery, queryInfo);
        PageInfo pagerInfo = queryInfo.getPagerInfo();
        if (!ObjectUtils.isEmpty(pagerInfo) && pagerInfo.getPageSize() > 0 && pagerInfo.getPageIndex() > 0) {
            dataQuery.setPagingInfo(pagerInfo.getPageSize(), pagerInfo.getPageIndex());
        }
        return dataQuery.executeReader(context);
    }

    private ExecutorContext getExecutorContext(DimensionValueSet dimensionCombination, Map<String, Object> var) {
        VariableManager variableManager;
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setJQReportModel(true);
        executorContext.setVarDimensionValueSet(dimensionCombination);
        if (!CollectionUtils.isEmpty(var) && (variableManager = executorContext.getVariableManager()) != null) {
            for (Map.Entry<String, Object> variableEnt : var.entrySet()) {
                Variable variable = new Variable(variableEnt.getKey(), 6);
                variable.setVarValue(variableEnt.getValue());
                variableManager.add(variable);
            }
        }
        return executorContext;
    }

    private void addQueryCol(IDataQuery dataQuery, EngineQueryParam queryInfo) {
        SummaryDSModel dsModel = queryInfo.getDsModel();
        HashMap<String, Integer> columnMap = new HashMap<String, Integer>();
        Metadata metadata = new Metadata();
        List<String> dimFields = dsModel.getPublicDimFields();
        this.processFields(dataQuery, dsModel, (Metadata<ColumnInfo>)metadata, columnMap, dimFields);
        List<String> measureFields = dsModel.getQueryMeasureFields();
        this.processFields(dataQuery, dsModel, (Metadata<ColumnInfo>)metadata, columnMap, measureFields);
        queryInfo.setColumnMap(columnMap);
        queryInfo.setMetadata((Metadata<ColumnInfo>)metadata);
    }

    private void processFields(IDataQuery dataQuery, SummaryDSModel dsModel, Metadata<ColumnInfo> metadata, Map<String, Integer> columnMap, List<String> fields) {
        fields.forEach(name -> {
            SummaryDSField field = (SummaryDSField)dsModel.findField((String)name);
            String queryFieldName = this.getQueryFieldName(field);
            if (!columnMap.containsKey(queryFieldName)) {
                DataField dataField = null;
                Column column = null;
                int index = -1;
                if (StringUtils.hasText((String)field.getZbKey())) {
                    dataField = this.iRuntimeDataSchemeService.getDataField(field.getZbKey());
                } else if (StringUtils.hasText((String)field.getExpression())) {
                    index = dataQuery.addExpressionColumn(field.getExpression());
                    column = new Column(queryFieldName, field.getValType());
                } else {
                    String[] split = field.getMessageAlias().split("\\.");
                    if (split.length > 1) {
                        DataTable dataTable = this.iRuntimeDataSchemeService.getDataTableByCode(split[0]);
                        dataField = this.iRuntimeDataSchemeService.getDataFieldByTableKeyAndCode(dataTable.getKey(), split[1]);
                    }
                }
                if (dataField != null) {
                    index = dataQuery.addColumn((FieldDefine)((DataFieldDTO)dataField));
                    column = new Column(queryFieldName, dataField.getDataFieldType().getValue());
                }
                if (index >= 0) {
                    metadata.addColumn(column);
                    columnMap.put(queryFieldName, index);
                }
            }
        });
    }

    private String getQueryFieldName(DSField field) {
        return field.getName();
    }
}

