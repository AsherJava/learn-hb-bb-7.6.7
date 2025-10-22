/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ResultErrorInfo
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.var.PageCondition
 *  com.jiuqi.nr.fmdm.BatchFMDMDTO
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  com.jiuqi.nr.fmdm.IFMDMUpdateResult
 *  com.jiuqi.nr.fmdm.exception.FMDMUpdateException
 *  com.jiuqi.nr.fmdm.internal.check.CheckNodeInfo
 *  com.jiuqi.nr.fmdm.internal.check.FMDMCheckFailNodeInfo
 *  com.jiuqi.nr.fmdm.internal.check.FMDMCheckResult
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.dafafill.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ResultErrorInfo;
import com.jiuqi.nr.dafafill.exception.DataFillRuntimeException;
import com.jiuqi.nr.dafafill.model.DFDimensionValue;
import com.jiuqi.nr.dafafill.model.DFDimensionValueGetService;
import com.jiuqi.nr.dafafill.model.DataFieldWriteAccessResultInfo;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import com.jiuqi.nr.dafafill.model.DataFillDataDeleteRow;
import com.jiuqi.nr.dafafill.model.DataFillDataQueryInfo;
import com.jiuqi.nr.dafafill.model.DataFillDataSaveInfo;
import com.jiuqi.nr.dafafill.model.DataFillDataSaveRow;
import com.jiuqi.nr.dafafill.model.DataFillQueryResult;
import com.jiuqi.nr.dafafill.model.DataFillResult;
import com.jiuqi.nr.dafafill.model.DataFillSaveErrorDataInfo;
import com.jiuqi.nr.dafafill.model.FieldReadWriteAccessResultInfo;
import com.jiuqi.nr.dafafill.model.PageInfo;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.model.enums.TableType;
import com.jiuqi.nr.dafafill.service.IDataFillEnvBaseDataService;
import com.jiuqi.nr.dafafill.util.NrDataFillI18nUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.var.PageCondition;
import com.jiuqi.nr.fmdm.BatchFMDMDTO;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.fmdm.IFMDMUpdateResult;
import com.jiuqi.nr.fmdm.exception.FMDMUpdateException;
import com.jiuqi.nr.fmdm.internal.check.CheckNodeInfo;
import com.jiuqi.nr.fmdm.internal.check.FMDMCheckFailNodeInfo;
import com.jiuqi.nr.fmdm.internal.check.FMDMCheckResult;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataFillFMDMDataServiceImpl
extends IDataFillEnvBaseDataService {
    private static Logger logger = LoggerFactory.getLogger(DataFillFMDMDataServiceImpl.class);
    @Autowired
    private IFMDMDataService fmdmDataService;
    @Autowired
    private DFDimensionValueGetService dfDimensionValueGetService;
    @Autowired
    private IFMDMAttributeService attributeService;

    @Override
    public TableType getTableType() {
        return TableType.FMDM;
    }

    @Override
    public DataFillQueryResult query(DataFillDataQueryInfo queryInfo) {
        LinkedHashMap<DimensionValueSet, List<com.jiuqi.np.dataengine.data.AbstractData>> resultMap = new LinkedHashMap<DimensionValueSet, List<com.jiuqi.np.dataengine.data.AbstractData>>();
        DataFillContext context = queryInfo.getContext();
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
        DimensionValueSet entityDimensionValueSet = this.dFDimensionParser.parserGetEntityDimensionValueSet(context);
        QueryField dwQueryField = fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0);
        Optional<DFDimensionValue> findDw = context.getDimensionValues().stream().filter(e -> e.getName().equals(dwQueryField.getSimplifyFullCode())).findAny();
        String dwValue = "";
        if (findDw.isPresent()) {
            DFDimensionValue dfDimensionValue = findDw.get();
            dwValue = this.dfDimensionValueGetService.getValues(dfDimensionValue, context.getModel());
        }
        String dwDimensionName = dwQueryField.getSimplifyFullCode();
        Map<String, String> extendedData = context.getModel().getExtendedData();
        String formSchemeKey = extendedData.get("FORMSCHEMEKEY");
        FMDMDataDTO fMDMDataDTO = new FMDMDataDTO();
        fMDMDataDTO.setFormSchemeKey(formSchemeKey);
        DimensionValueSet sqlDimension = this.dFDimensionParser.entityIdToSqlDimension(context, entityDimensionValueSet);
        fMDMDataDTO.setDimensionValueSet(sqlDimension);
        fMDMDataDTO.setSortedByQuery(false);
        fMDMDataDTO.setSorted(true);
        fMDMDataDTO.setDataMasking(true);
        PageInfo pagerInfo = queryInfo.getPagerInfo();
        if (pagerInfo != null) {
            PageCondition pageCondition = new PageCondition();
            pageCondition.setPageIndex(Integer.valueOf(pagerInfo.getOffset() * pagerInfo.getLimit()));
            pageCondition.setPageSize(Integer.valueOf(pagerInfo.getLimit()));
            fMDMDataDTO.setPageCondition(pageCondition);
        }
        List list = this.fmdmDataService.list(fMDMDataDTO);
        List<QueryField> fields = this.dFDimensionParser.getDisplayColsQueryFields(context);
        for (IFMDMData fmdmData : list) {
            ArrayList<com.jiuqi.np.dataengine.data.AbstractData> values = new ArrayList<com.jiuqi.np.dataengine.data.AbstractData>();
            DimensionValueSet rowDimensionValueSet = new DimensionValueSet();
            rowDimensionValueSet.assign(entityDimensionValueSet);
            rowDimensionValueSet.setValue(dwDimensionName, (Object)fmdmData.getFMDMKey());
            for (QueryField field : fields) {
                if (field.getFieldType() != FieldType.ZB) continue;
                AbstractData nrData = fmdmData.getValue(field.getSimplifyFullCode());
                com.jiuqi.np.dataengine.data.AbstractData npData = com.jiuqi.np.dataengine.data.AbstractData.valueOf((Object)nrData.getAsObject(), (int)nrData.dataType);
                values.add(npData);
            }
            resultMap.put(rowDimensionValueSet, values);
        }
        DataFillQueryResult dataFillQueryResult = new DataFillQueryResult();
        dataFillQueryResult.setDatas(resultMap);
        if (pagerInfo != null) {
            dataFillQueryResult.setPageInfo(pagerInfo);
            dataFillQueryResult.setTotalCount(dwValue.split(";").length);
        }
        return dataFillQueryResult;
    }

    @Override
    public DataFillResult save(DataFillDataSaveInfo saveInfo) {
        List<DataFillDataSaveRow> adds = saveInfo.getAdds();
        List<DataFillDataSaveRow> modifys = saveInfo.getModifys();
        List<DataFillDataDeleteRow> deletes = saveInfo.getDeletes();
        if (null != adds && adds.size() > 0) {
            throw new DataFillRuntimeException("\u5c01\u9762\u4ee3\u7801\u6682\u4e0d\u652f\u6301\u4ece\u6b64\u5904\u65b0\u589e\uff01");
        }
        if (null != deletes && deletes.size() > 0) {
            throw new DataFillRuntimeException("\u5c01\u9762\u4ee3\u7801\u6682\u4e0d\u652f\u6301\u4ece\u6b64\u5904\u5220\u9664\uff01");
        }
        DataFillResult dataFillResult = new DataFillResult();
        ArrayList<DataFillSaveErrorDataInfo> errors = new ArrayList<DataFillSaveErrorDataInfo>();
        dataFillResult.setErrors(errors);
        dataFillResult.setSuccess(true);
        dataFillResult.setMessage(NrDataFillI18nUtil.buildCode("nvwa.base.saveSuccess"));
        DataFillContext context = saveInfo.getContext();
        if (null != modifys && modifys.size() > 0) {
            Map<String, String> extendedData = context.getModel().getExtendedData();
            String formSchemeKey = extendedData.get("FORMSCHEMEKEY");
            Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
            QueryField periodField = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
            QueryField masterField = fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0);
            String masterName = masterField.getSimplifyFullCode();
            List<QueryField> displayCols = this.dFDimensionParser.getDisplayColsQueryFields(context);
            List zbQueryFields = displayCols.stream().filter(e -> e.getFieldType() == FieldType.ZB || e.getFieldType() == FieldType.FIELD || e.getFieldType() == FieldType.TABLEDIMENSION || e.getFieldType() == FieldType.EXPRESSION).collect(Collectors.toList());
            HashMap<String, Object> zbIdQueryField = new HashMap<String, Object>();
            HashMap<String, Object> zbCodeQueryField = new HashMap<String, Object>();
            for (Object queryField : zbQueryFields) {
                zbIdQueryField.put(((QueryField)queryField).getId(), queryField);
                zbCodeQueryField.put(((QueryField)queryField).getSimplifyFullCode(), queryField);
            }
            HashMap<String, ArrayList<DataFillDataSaveRow>> entityIdMap = new HashMap<String, ArrayList<DataFillDataSaveRow>>();
            for (DataFillDataSaveRow dataFillDataSaveRow : modifys) {
                List<DFDimensionValue> dimensionValues = dataFillDataSaveRow.getDimensionValues();
                Optional<DFDimensionValue> findPeriod = dimensionValues.stream().filter(e -> e.getName().equals(periodField.getSimplifyFullCode())).findAny();
                if (!findPeriod.isPresent()) continue;
                DFDimensionValue dfDimensionValue = findPeriod.get();
                String periodValue = this.dfDimensionValueGetService.getValues(dfDimensionValue, context.getModel());
                ArrayList<DataFillDataSaveRow> list = (ArrayList<DataFillDataSaveRow>)entityIdMap.get(periodValue);
                if (null == list) {
                    list = new ArrayList<DataFillDataSaveRow>();
                    entityIdMap.put(periodValue, list);
                }
                list.add(dataFillDataSaveRow);
            }
            ArrayList<DFDimensionValue> hideDFDimensionValues = null;
            List<QueryField> hideQueryFields = this.dFDimensionParser.getHideQueryFields(periodField.getDataSchemeCode());
            if (null != hideQueryFields && hideQueryFields.size() > 0) {
                hideDFDimensionValues = new ArrayList<DFDimensionValue>();
                for (QueryField queryField : hideQueryFields) {
                    String simplifyFullCode = queryField.getSimplifyFullCode();
                    DFDimensionValue temp = new DFDimensionValue();
                    temp.setName(simplifyFullCode);
                    hideDFDimensionValues.add(temp);
                    context.getModel().getAssistFields().add(queryField);
                }
            }
            Collection modifyLists = entityIdMap.values();
            FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
            fmdmAttributeDTO.setFormSchemeKey(formSchemeKey);
            List listShowAttribute = this.attributeService.listShowAttribute(fmdmAttributeDTO);
            HashMap<String, IFMDMAttribute> codeAttribute = new HashMap<String, IFMDMAttribute>();
            for (IFMDMAttribute fMDMAttribute : listShowAttribute) {
                codeAttribute.put(fMDMAttribute.getCode(), fMDMAttribute);
            }
            for (List rows : modifyLists) {
                DataFillSaveErrorDataInfo saveErrorDataInfo;
                Object queryField;
                HashMap<String, String> authMap = new HashMap<String, String>();
                FieldReadWriteAccessResultInfo accessResult = this.getAccess(saveInfo, rows, authMap);
                BatchFMDMDTO batchFMDMDTO = new BatchFMDMDTO();
                DimensionValueSet batchRowValueSet = new DimensionValueSet();
                batchFMDMDTO.setDimensionValueSet(batchRowValueSet);
                batchFMDMDTO.setFormSchemeKey(formSchemeKey);
                ArrayList<Object> updateDatas = new ArrayList<Object>();
                batchFMDMDTO.setFmdmList(updateDatas);
                int rowIdx = 0;
                for (DataFillDataSaveRow row : rows) {
                    List<String> zbs = row.getZbs();
                    List<String> values = row.getValues();
                    if (null != zbs && !zbs.isEmpty()) {
                        int i;
                        FMDMDataDTO fmdmDataDTO = new FMDMDataDTO();
                        ArrayList<DFDimensionValue> temp = new ArrayList<DFDimensionValue>();
                        temp.addAll(row.getDimensionValues());
                        if (null != hideDFDimensionValues && !hideDFDimensionValues.isEmpty()) {
                            temp.addAll(hideDFDimensionValues);
                        }
                        DimensionValueSet sqlRowValueSet = this.dFDimensionParser.entityIdToSqlDimension(context, temp);
                        DimensionCombinationBuilder combinationBuilder = new DimensionCombinationBuilder();
                        for (i = 0; i < sqlRowValueSet.size(); ++i) {
                            batchRowValueSet.setValue(sqlRowValueSet.getName(i), sqlRowValueSet.getValue(i));
                            combinationBuilder.setValue(sqlRowValueSet.getName(i), sqlRowValueSet.getValue(i));
                        }
                        fmdmDataDTO.setDimensionCombination(combinationBuilder.getCombination());
                        for (i = 0; i < zbs.size(); ++i) {
                            DataFieldWriteAccessResultInfo access;
                            String zbId = zbs.get(i);
                            queryField = (QueryField)zbIdQueryField.get(zbId);
                            if (null == queryField || ((QueryField)queryField).getFieldType() == FieldType.EXPRESSION || !(access = accessResult.getAccess(sqlRowValueSet, (String)authMap.get(((QueryField)queryField).getId()), context)).haveAccess()) continue;
                            String code = ((QueryField)queryField).getCode();
                            String zbValue = values.get(i);
                            IFMDMAttribute ifmdmAttribute = (IFMDMAttribute)codeAttribute.get(code);
                            saveErrorDataInfo = this.toSqlValue(zbValue, (QueryField)queryField, context, row.getDimensionValues(), (ColumnModelDefine)ifmdmAttribute, null, null);
                            if (null != saveErrorDataInfo.getDataError()) {
                                saveErrorDataInfo.setErrorLocY(rowIdx + 2);
                                saveErrorDataInfo.setErrorLocX(i + 2);
                                errors.add(saveErrorDataInfo);
                                continue;
                            }
                            fmdmDataDTO.setValue(code, null == saveErrorDataInfo.getValue() ? "" : saveErrorDataInfo.getValue());
                        }
                        updateDatas.add(fmdmDataDTO);
                    }
                    ++rowIdx;
                }
                try {
                    if (errors.isEmpty()) {
                        IFMDMUpdateResult batchUpdateFMDM;
                        List updateCodes;
                        if (updateDatas.isEmpty() || (updateCodes = (batchUpdateFMDM = this.fmdmDataService.batchUpdateFMDM(batchFMDMDTO)).getUpdateCodes()).size() == updateDatas.size()) continue;
                        dataFillResult.setSuccess(false);
                        dataFillResult.setMessage(NrDataFillI18nUtil.buildCode("nvwa.base.saveError") + "\uff01");
                        List updateDimensions = batchUpdateFMDM.getUpdateDimensions();
                        ArrayList<DataFillDataSaveRow> errorRows = new ArrayList<DataFillDataSaveRow>();
                        for (DataFillDataSaveRow row : rows) {
                            List<DFDimensionValue> dimensionValues = row.getDimensionValues();
                            Optional<DFDimensionValue> findMaster = dimensionValues.stream().filter(e -> e.getName().equals(masterName)).findAny();
                            if (!findMaster.isPresent()) continue;
                            String masterValue = this.dfDimensionValueGetService.getValues(findMaster.get(), context.getModel());
                            boolean find = false;
                            for (DimensionValueSet dimensionValueSet : updateDimensions) {
                                String orgValue = (String)dimensionValueSet.getValue("MD_ORG");
                                if (!orgValue.equals(masterValue)) continue;
                                find = true;
                                break;
                            }
                            if (find) continue;
                            errorRows.add(row);
                        }
                        FMDMCheckResult fmdmCheckResult = batchUpdateFMDM.getFMDMCheckResult();
                        List results = fmdmCheckResult.getResults();
                        int i = 0;
                        for (FMDMCheckFailNodeInfo fMDMCheckFailNodeInfo : results) {
                            String fieldCode = fMDMCheckFailNodeInfo.getFieldCode();
                            queryField = (QueryField)zbCodeQueryField.get(fieldCode);
                            List nodes = fMDMCheckFailNodeInfo.getNodes();
                            for (CheckNodeInfo node : nodes) {
                                ResultErrorInfo resultErrorInfo = new ResultErrorInfo();
                                resultErrorInfo.setErrorCode(ErrorCode.DATAERROR);
                                resultErrorInfo.setErrorInfo(node.getContent());
                                saveErrorDataInfo = new DataFillSaveErrorDataInfo();
                                saveErrorDataInfo.setDataError(resultErrorInfo);
                                saveErrorDataInfo.setZb(null != queryField ? ((QueryField)queryField).getId() : "");
                                if (errorRows.size() > i) {
                                    saveErrorDataInfo.setDimensionValues(((DataFillDataSaveRow)errorRows.get(i)).getDimensionValues());
                                    ++i;
                                }
                                errors.add(saveErrorDataInfo);
                            }
                        }
                        continue;
                    }
                    dataFillResult.setSuccess(false);
                    dataFillResult.setMessage(NrDataFillI18nUtil.buildCode("nvwa.base.saveError") + "\uff01");
                    break;
                }
                catch (FMDMUpdateException e2) {
                    logger.error("\u5c01\u9762\u4ee3\u7801\u66f4\u65b0\u5931\u8d25\uff01", e2);
                    throw new DataFillRuntimeException("\u5c01\u9762\u4ee3\u7801\u66f4\u65b0\u5931\u8d25\uff01");
                }
            }
        }
        return dataFillResult;
    }
}

