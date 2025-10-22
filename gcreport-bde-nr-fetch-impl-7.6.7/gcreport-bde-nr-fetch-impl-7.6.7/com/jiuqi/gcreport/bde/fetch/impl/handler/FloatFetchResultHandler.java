/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.FloatZbMappingVO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO
 *  com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO
 *  com.jiuqi.bde.common.dto.fetch.result.FloatRegionResultDTO
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.common.utils.BdeUUIDOrderUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.nrextracteditctrl.dto.NrSchemeConfigDTO
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.exception.UnknownReadWriteException
 *  com.jiuqi.np.dataengine.intf.IColumnModelFinder
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.engine.provider.DataSchemeColumnModelFinder
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.internal.env.ReportColumnModelImpl
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.gcreport.bde.fetch.impl.handler;

import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.FloatZbMappingVO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO;
import com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO;
import com.jiuqi.bde.common.dto.fetch.result.FloatRegionResultDTO;
import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.common.utils.BdeUUIDOrderUtils;
import com.jiuqi.gcreport.bde.fetch.impl.handler.AbstractFetchResultHandler;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.nrextracteditctrl.dto.NrSchemeConfigDTO;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.UnknownReadWriteException;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.engine.provider.DataSchemeColumnModelFinder;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.internal.env.ReportColumnModelImpl;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class FloatFetchResultHandler
extends AbstractFetchResultHandler {
    private FloatRegionResultDTO floatResult;
    private List<FieldDefine> fieldDefines = new ArrayList<FieldDefine>();
    private List<String> handFilledFieldList = new ArrayList<String>();
    private Map<String, List<Map<String, Object>>> originalDataRowMap = new HashMap<String, List<Map<String, Object>>>();
    private FieldDefine inputOrderField;

    public FloatFetchResultHandler(FetchRequestDTO fetchRequestDTO, FetchResultDTO fetchResponseDTO) {
        super(fetchRequestDTO, fetchResponseDTO);
        this.floatResult = fetchResponseDTO == null ? null : fetchResponseDTO.getFloatResults();
        this.initFieldDefineList(fetchRequestDTO.getFetchContext().getFetchSchemeId(), fetchRequestDTO.getFetchContext().getFormSchemeId(), fetchRequestDTO.getFetchContext().getFormId(), fetchRequestDTO.getFetchContext().getRegionId());
    }

    @Override
    public void save() {
        this.init();
        if (this.isConfigDimension(this.getDataRegion().getKey()) && !this.handFilledFieldList.isEmpty()) {
            this.doIncrementSave();
        } else {
            this.doFullSave();
        }
    }

    private void init() {
        try {
            String inputOrderFieldKey = this.getDataRegion().getInputOrderFieldKey();
            if (!StringUtils.isEmpty((String)inputOrderFieldKey)) {
                this.inputOrderField = this.dataDefinitionRuntimeController.queryFieldDefine(inputOrderFieldKey);
            }
        }
        catch (Exception e) {
            throw new BdeRuntimeException("\u67e5\u8be2\u8868\u5355\u201c" + this.getFormDefine().getTitle() + "\u201d\u6d6e\u52a8\u533a\u57df\u6392\u5e8f\u5b57\u6bb5\u5931\u8d25:" + e.getMessage(), (Throwable)e);
        }
    }

    private void initFieldDefineList(String fetchSchemeId, String formSchemeId, String formId, String regionId) {
        try {
            Set<String> fieldDefineIdSet;
            if (this.isConfigDimension(regionId)) {
                List fieldKeys = this.runTimeViewController.getFieldKeysInRegion(regionId);
                fieldDefineIdSet = new HashSet<String>(fieldKeys);
            } else {
                fieldDefineIdSet = this.getFieldIdSet(fetchSchemeId, formSchemeId, formId, regionId);
            }
            for (String fieldDefineId : fieldDefineIdSet) {
                FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldDefineId);
                this.fieldDefines.add(fieldDefine);
            }
            if (this.isConfigDimension(regionId)) {
                this.initHandField(fetchSchemeId, formSchemeId, formId, regionId);
            }
        }
        catch (Exception e) {
            throw new BdeRuntimeException("\u67e5\u8be2\u8868\u5355\u201c" + this.getFormDefine().getTitle() + "\u201d\u53d6\u6570\u6307\u6807\u5931\u8d25:" + e.getMessage(), (Throwable)e);
        }
    }

    private IDataTable buildDataTable() {
        IDataQuery dataQuery = this.newDataQuery(this.getDataRegion().getKey(), this.getFetchFields());
        if (this.inputOrderField != null) {
            dataQuery.addColumn(this.inputOrderField);
        }
        IDataTable dataTable = null;
        try {
            dataTable = dataQuery.executeQuery(this.getExecutorContext());
        }
        catch (Exception e) {
            throw new BdeRuntimeException("\u67e5\u8be2\u8868\u5355\u201c" + this.getFormDefine().getTitle() + "\u201d\u6d6e\u52a8\u533a\u57df\u6570\u636e\u5931\u8d25:" + e.getMessage(), (Throwable)e);
        }
        return dataTable;
    }

    private void doFullSave() {
        IDataTable dataTable = this.buildDataTable();
        try {
            dataTable.deleteAll();
            dataTable.commitChanges(false);
            if (this.floatResult != null && !CollectionUtils.isEmpty((Collection)this.floatResult.getRowDatas())) {
                Map floatColumns = this.floatResult.getFloatColumns();
                int rowSize = this.floatResult.getRowDatas().size();
                for (int i = 0; i < rowSize; ++i) {
                    Object[] rowData = (Object[])this.floatResult.getRowDatas().get(i);
                    DimensionValueSet rowKey = new DimensionValueSet(this.getDimensionValueSet());
                    rowKey.setValue("RECORDKEY", (Object)BdeUUIDOrderUtils.newUUIDStr());
                    IDataRow destDataRow = dataTable.appendRow(rowKey);
                    for (FieldDefine fieldDefine : this.getFetchFields()) {
                        if (!floatColumns.containsKey(fieldDefine.getKey())) continue;
                        Object value = rowData[(Integer)floatColumns.get(fieldDefine.getKey())];
                        if (StringUtils.isEmpty((String)fieldDefine.getEntityKey()) || value == null) {
                            destDataRow.setValue(fieldDefine, value);
                            continue;
                        }
                        Object isolationValue = this.doIsolationSave(fieldDefine, value);
                        destDataRow.setValue(fieldDefine, isolationValue);
                    }
                    if (this.inputOrderField == null) continue;
                    int floatorder = (i + 1) * 1000;
                    destDataRow.setValue(this.inputOrderField, (Object)floatorder);
                }
            }
            dataTable.commitChanges(true);
        }
        catch (Exception e) {
            String message = e.getMessage();
            if (e.getCause() != null) {
                message = message + e.getCause().getMessage();
            }
            throw new BdeRuntimeException(message, (Throwable)e);
        }
    }

    private void doIncrementSave() {
        IDataTable dataTable = this.buildDataTable();
        try {
            dataTable.deleteAll();
            dataTable.commitChanges(false);
            if (this.floatResult != null && !CollectionUtils.isEmpty((Collection)this.floatResult.getRowDatas())) {
                List dimensionFiledList = this.floatDimensionSettingService.getDimensionSettingFiledList(this.getDataRegion().getKey());
                Map floatColumns = this.floatResult.getFloatColumns();
                int rowSize = this.floatResult.getRowDatas().size();
                for (int i = 0; i < rowSize; ++i) {
                    Object isolationValue;
                    Object value;
                    Object[] rowData = (Object[])this.floatResult.getRowDatas().get(i);
                    DimensionValueSet rowKey = new DimensionValueSet(this.getDimensionValueSet());
                    rowKey.setValue("RECORDKEY", (Object)BdeUUIDOrderUtils.newUUIDStr());
                    IDataRow destDataRow = dataTable.appendRow(rowKey);
                    StringBuilder dimensionMark = new StringBuilder("|");
                    for (FieldDefine fieldDefine : this.getFetchFields()) {
                        if (!dimensionFiledList.contains(fieldDefine.getKey()) || floatColumns.get(fieldDefine.getKey()) == null || rowData[(Integer)floatColumns.get(fieldDefine.getKey())] == null) continue;
                        if (StringUtils.isEmpty((String)fieldDefine.getEntityKey())) {
                            dimensionMark.append(this.convertVal(rowData[(Integer)floatColumns.get(fieldDefine.getKey())])).append("|");
                            continue;
                        }
                        dimensionMark.append(this.doIsolationSave(fieldDefine, this.convertVal(rowData[(Integer)floatColumns.get(fieldDefine.getKey())]))).append("|");
                    }
                    if (this.originalDataRowMap.get(dimensionMark.toString()) != null && this.originalDataRowMap.get(dimensionMark.toString()).size() == 1) {
                        for (FieldDefine fieldDefine : this.getFetchFields()) {
                            value = null;
                            if (floatColumns.containsKey(fieldDefine.getKey())) {
                                value = rowData[(Integer)floatColumns.get(fieldDefine.getKey())];
                                if (StringUtils.isEmpty((String)fieldDefine.getEntityKey()) || value == null) {
                                    destDataRow.setValue(fieldDefine, value);
                                    continue;
                                }
                            } else {
                                value = this.originalDataRowMap.get(dimensionMark.toString()).get(0).get(fieldDefine.getKey());
                                if (StringUtils.isEmpty((String)fieldDefine.getEntityKey()) || value == null) {
                                    destDataRow.setValue(fieldDefine, value);
                                    continue;
                                }
                            }
                            isolationValue = this.doIsolationSave(fieldDefine, value);
                            destDataRow.setValue(fieldDefine, isolationValue);
                        }
                    } else {
                        for (FieldDefine fieldDefine : this.getFetchFields()) {
                            value = null;
                            if (!floatColumns.containsKey(fieldDefine.getKey())) continue;
                            value = rowData[(Integer)floatColumns.get(fieldDefine.getKey())];
                            if (StringUtils.isEmpty((String)fieldDefine.getEntityKey()) || value == null) {
                                destDataRow.setValue(fieldDefine, value);
                                continue;
                            }
                            isolationValue = this.doIsolationSave(fieldDefine, value);
                            destDataRow.setValue(fieldDefine, isolationValue);
                        }
                    }
                    if (this.inputOrderField == null) continue;
                    int floatorder = (i + 1) * 1000;
                    destDataRow.setValue(this.inputOrderField, (Object)floatorder);
                }
            }
            dataTable.commitChanges(true);
        }
        catch (Exception e) {
            String message = e.getMessage();
            if (e.getCause() != null) {
                message = message + e.getCause().getMessage();
            }
            throw new BdeRuntimeException(message, (Throwable)e);
        }
    }

    private Object doIsolationSave(FieldDefine fieldDefine, Object value) {
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(fieldDefine.getEntityKey());
        if (0 != entityDefine.getIsolation()) {
            String valueStr = value.toString();
            String entityKey = fieldDefine.getEntityKey();
            EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
            entityQueryByKeyInfo.setEntityViewKey(entityKey);
            JtableContext context = new JtableContext();
            ReportFmlExecEnvironment reportFmlExecEnvironment = (ReportFmlExecEnvironment)this.getExecutorContext().getEnv();
            String taskKey = reportFmlExecEnvironment.getTaskDefine().getKey();
            context.setTaskKey(taskKey);
            context.setFormSchemeKey(this.getFormSchemeId());
            Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)this.getDimensionValueSet());
            context.setDimensionSet(dimensionSet);
            entityQueryByKeyInfo.setContext(context);
            entityQueryByKeyInfo.setEntityKey(valueStr);
            EntityByKeyReturnInfo returnInfo = this.entityService.queryEntityDataByKey(entityQueryByKeyInfo);
            EntityData entity = returnInfo.getEntity();
            return entity.getId();
        }
        return value;
    }

    private Set<String> getFieldIdSet(String fetchSchemeId, String formSchemeId, String formId, String regionId) {
        HashSet<String> fieldDefineIdSet = new HashSet<String>();
        FetchSettingCond fetchSettingCond = new FetchSettingCond();
        fetchSettingCond.setFormSchemeId(formSchemeId);
        fetchSettingCond.setFetchSchemeId(fetchSchemeId);
        fetchSettingCond.setFormId(formId);
        fetchSettingCond.setRegionId(regionId);
        List fetchFloatSettingList = this.fetchFloatSettingService.listFetchFloatSettingByFormId(fetchSettingCond);
        if (CollectionUtils.isEmpty((Collection)fetchFloatSettingList)) {
            return fieldDefineIdSet;
        }
        for (FloatRegionConfigVO fetchFloatSettingVO : fetchFloatSettingList) {
            List mappingList;
            QueryConfigInfo queryConfigInfo = fetchFloatSettingVO.getQueryConfigInfo();
            if (queryConfigInfo == null || CollectionUtils.isEmpty((Collection)(mappingList = queryConfigInfo.getZbMapping()))) continue;
            for (FloatZbMappingVO zbMappingVO : mappingList) {
                fieldDefineIdSet.add(zbMappingVO.getFieldDefineId());
            }
        }
        return fieldDefineIdSet;
    }

    public List<FieldDefine> getFetchFields() {
        return this.fieldDefines;
    }

    private void initHandField(String fetchSchemeId, String formSchemeId, String formId, String regionId) throws UnknownReadWriteException {
        List fieldKeys;
        Set<String> fetchAndCalculateIdSet = this.getFieldIdSet(fetchSchemeId, formSchemeId, formId, regionId);
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(this.getDimensionValueSet());
        String orgId = (String)dimensionCombinationBuilder.getCombination().getValue("MD_ORG");
        HashMap assistDim = CollectionUtils.newHashMap();
        assistDim.put("DATATIME", (String)dimensionCombinationBuilder.getCombination().getValue("DATATIME"));
        assistDim.put("MD_GCORGTYPE", (String)dimensionCombinationBuilder.getCombination().getValue("MD_GCORGTYPE"));
        assistDim.put("MD_CURRENCY", (String)dimensionCombinationBuilder.getCombination().getValue("MD_CURRENCY"));
        DsContext dsContext = DsContextHolder.getDsContext();
        String entityId = dsContext.getContextEntityId();
        NrSchemeConfigDTO formulaSchemeConfigDTO = this.formulaSchemeConfigService.getSchemeByOrgId(formSchemeId, entityId, orgId, (Map)assistDim);
        List fetchAfterSchemeIdList = formulaSchemeConfigDTO.getFetchAfterSchemeId();
        if (fetchAfterSchemeIdList != null) {
            for (String fetchAfterSchemeId : fetchAfterSchemeIdList) {
                List parsedExpressionList = this.iFormulaRunTimeController.getParsedExpressionByForm(fetchAfterSchemeId, formId, DataEngineConsts.FormulaType.CALCULATE);
                block1: for (IParsedExpression parsedExpression : parsedExpressionList) {
                    IExpression iExpression = parsedExpression.getRealExpression();
                    for (IASTNode child : iExpression.getChild(0)) {
                        DynamicDataNode dataNode;
                        DataModelLinkColumn column;
                        if (!(child instanceof DynamicDataNode) || !Objects.nonNull(column = (dataNode = (DynamicDataNode)child).getDataModelLink()) || StringUtils.isEmpty((String)column.getRegion())) continue;
                        ColumnModelDefine columnModelDefine = this.getColumnModelDefine(column.getColumModel());
                        fetchAndCalculateIdSet.add(columnModelDefine.getID());
                        continue block1;
                    }
                }
            }
        }
        if ((fieldKeys = this.runTimeViewController.getFieldKeysInRegion(regionId)) == null || fieldKeys.isEmpty()) {
            return;
        }
        HashSet fieldKeySet = new HashSet(fieldKeys);
        fieldKeySet.removeAll(fetchAndCalculateIdSet);
        if (!fieldKeySet.isEmpty()) {
            this.handFilledFieldList = new ArrayList<String>(fieldKeySet);
            this.initOriginalDataRowMap(regionId);
        }
    }

    private void initOriginalDataRowMap(String regionId) {
        List dimensionFiledList = this.floatDimensionSettingService.getDimensionSettingFiledList(regionId);
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(this.getDimensionValueSet());
        QueryInfoBuilder queryInfoBuilder = QueryInfoBuilder.create((String)regionId, (DimensionCombination)dimensionCombinationBuilder.getCombination());
        List iRowDataList = this.iDataQueryService.queryRegionData(queryInfoBuilder.build()).getRowData();
        ((IMetaData)this.iDataQueryService.queryRegionData(queryInfoBuilder.build()).getMetaData().get(0)).getDataField().getCode();
        for (IRowData iRowData : iRowDataList) {
            ArrayList<HashMap> rowdataList = new ArrayList<HashMap>();
            StringBuilder dimensionMark = new StringBuilder("|");
            HashMap rowData = CollectionUtils.newHashMap();
            List linkDataValues = iRowData.getLinkDataValues();
            HashMap dimensionValueMap = CollectionUtils.newHashMap();
            for (IDataValue linkDataValue : linkDataValues) {
                if (dimensionFiledList.contains(linkDataValue.getMetaData().getDataField().getKey()) && linkDataValue.getAsObject() != null) {
                    dimensionValueMap.put(linkDataValue.getMetaData().getDataField().getKey(), this.convertVal(linkDataValue.getAsObject()) + "|");
                }
                rowData.put(linkDataValue.getMetaData().getDataField().getKey(), linkDataValue.getAsObject());
            }
            for (FieldDefine fieldDefine : this.getFetchFields()) {
                if (!dimensionValueMap.containsKey(fieldDefine.getKey())) continue;
                String value = (String)dimensionValueMap.get(fieldDefine.getKey());
                dimensionMark.append(value);
            }
            rowdataList.add(rowData);
            this.originalDataRowMap.put(String.valueOf(dimensionMark), rowdataList);
        }
    }

    private boolean isConfigDimension(String regionId) {
        List dimensionFiledList = this.floatDimensionSettingService.getDimensionSettingFiledList(regionId);
        return dimensionFiledList != null && !dimensionFiledList.isEmpty();
    }

    private String convertVal(Object val) {
        if (val instanceof BigDecimal) {
            Double doubleObject = ((BigDecimal)val).doubleValue();
            return doubleObject.toString();
        }
        return val.toString();
    }

    private ColumnModelDefine getColumnModelDefine(ColumnModelDefine columnModelDefine) {
        try {
            IColumnModelFinder iColumnModelFinder = (IColumnModelFinder)SpringContextUtils.getBean(DataSchemeColumnModelFinder.class);
            FieldDefine ZbFieldDefine = iColumnModelFinder.findFieldDefine(columnModelDefine);
            FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(ZbFieldDefine.getKey());
            return new ReportColumnModelImpl((DataField)fieldDefine);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u6307\u6807\u4fe1\u606f\u5f02\u5e38" + columnModelDefine.getName(), (Throwable)e);
        }
    }
}

