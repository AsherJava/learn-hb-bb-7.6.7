/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.UniversalFieldDefine
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.result.EntityUpdateResult
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.fmdm.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.UniversalFieldDefine;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.result.EntityUpdateResult;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.domain.AbstractFMDMDataDO;
import com.jiuqi.nr.fmdm.domain.EntityDataDO;
import com.jiuqi.nr.fmdm.domain.FMDMModifyDTO;
import com.jiuqi.nr.fmdm.domain.ZbDataDO;
import com.jiuqi.nr.fmdm.exception.FMDMFormException;
import com.jiuqi.nr.fmdm.internal.dto.QueryParamDTO;
import com.jiuqi.nr.fmdm.service.IRegionDataService;
import com.jiuqi.nr.fmdm.service.impl.EntityDataService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class FormDataService
extends EntityDataService {
    private static final Logger log = LoggerFactory.getLogger(FormDataService.class);
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IRegionDataService regionDataService;

    @Override
    public void dataPreProcessing(FMDMModifyDTO modifyDTO, String formKey) {
        if (!modifyDTO.isCompatibility()) {
            return;
        }
        List<FieldDefine> fieldDefines = this.zbQueryService.getAllFieldDefineInForm(formKey);
        if (CollectionUtils.isEmpty(fieldDefines)) {
            super.dataPreProcessing(modifyDTO, formKey);
            return;
        }
        Set<String> dataCodes = this.getDataCodes(fieldDefines);
        this.innerSetData(modifyDTO.getModifyValueMap(), modifyDTO.getDataModify(), dataCodes);
        super.dataPreProcessing(modifyDTO, formKey);
    }

    @Override
    public void batchDataPreProcessing(List<FMDMDataDTO> modifyDTO, String formKey) {
        List<FieldDefine> fieldDefines = this.zbQueryService.getAllFieldDefineInForm(formKey);
        if (CollectionUtils.isEmpty(fieldDefines)) {
            super.batchDataPreProcessing(modifyDTO, formKey);
            return;
        }
        Set<String> dataCodes = this.getDataCodes(fieldDefines);
        for (FMDMModifyDTO fMDMModifyDTO : modifyDTO) {
            this.innerSetData(fMDMModifyDTO.getModifyValueMap(), fMDMModifyDTO.getDataModify(), dataCodes);
        }
        super.batchDataPreProcessing(modifyDTO, formKey);
    }

    private boolean existForm(QueryParamDTO queryParamDTO) {
        return StringUtils.hasText(queryParamDTO.getFormKey());
    }

    public List<? extends ZbDataDO> list(QueryParamDTO queryParamDTO) {
        List<IDataRow> formDataList;
        List<? extends EntityDataDO> entityData = super.list(queryParamDTO);
        ArrayList<ZbDataDO> formData = new ArrayList<ZbDataDO>(entityData.size());
        for (EntityDataDO entityDataDO : entityData) {
            ZbDataDO zbDataDO = new ZbDataDO(entityDataDO.getEntityRow());
            formData.add(zbDataDO);
        }
        String formSchemeKey = queryParamDTO.getFormSchemeKey();
        if (!this.existForm(queryParamDTO)) {
            return formData;
        }
        List<FieldDefine> list = this.zbQueryService.getAllFieldDefineInForm(queryParamDTO);
        if (CollectionUtils.isEmpty(list)) {
            return formData;
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        DimensionValueSet queryDimension = queryParamDTO.getDimensionCombination().toDimensionValueSet();
        List<String> emptyValueDimension = this.getEmptyValueDimension(queryDimension);
        HashMap<String, List<String>> dimensionValues = new HashMap<String, List<String>>();
        if (!CollectionUtils.isEmpty(emptyValueDimension)) {
            IEntityModel entityModel = this.entityMetaService.getEntityModel(queryParamDTO.getEntityId());
            String dataSchemeKey = this.getDataSchemeKey(formScheme);
            Map<String, String> dimensionAttributeMap = this.getDimensionAttributeMap(dataSchemeKey, entityModel);
            for (String dimensionName : emptyValueDimension) {
                this.mappingEntityKey(entityData, dimensionValues, dimensionAttributeMap, dimensionName);
                this.fillDimensionValues(dimensionValues, queryDimension, dimensionName);
            }
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(queryDimension);
            queryParamDTO.setDimensionCombination(dimensionCombinationBuilder.getCombination());
        }
        if (!CollectionUtils.isEmpty(formDataList = this.regionDataService.queryRegionData(queryParamDTO, list))) {
            Map<String, ZbDataDO> entityKeyMap = formData.stream().collect(Collectors.toMap(AbstractFMDMDataDO::getFMDMKey, e -> e, (e1, e2) -> e2));
            String dimensionName = this.entityMetaService.getDimensionName(queryParamDTO.getEntityId());
            HashMap<String, Integer> codeToIndex = new HashMap<String, Integer>(list.size());
            int index = 0;
            for (FieldDefine fieldDefine : list) {
                codeToIndex.put(fieldDefine.getCode(), index++);
            }
            for (IDataRow dataRow : formDataList) {
                DimensionValueSet masterKeys = dataRow.getRowKeys();
                Object entityKeyData = masterKeys.getValue(dimensionName);
                ZbDataDO entityDataDO = entityKeyMap.get(entityKeyData.toString());
                if (null == entityDataDO) continue;
                entityDataDO.setDataRow(dataRow);
            }
        }
        this.setCodeToIndex(list, formData);
        return new ArrayList<ZbDataDO>(formData);
    }

    private List<String> getEmptyValueDimension(DimensionValueSet dimensionValueSet) {
        ArrayList<String> dimensionNames = new ArrayList<String>();
        int size = dimensionValueSet.size();
        for (int i = 0; i < size; ++i) {
            Object value = dimensionValueSet.getValue(i);
            if (value != null && !"".equals(value)) continue;
            dimensionNames.add(dimensionValueSet.getName(i));
        }
        return dimensionNames;
    }

    private String getDataSchemeKey(FormSchemeDefine formScheme) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        return taskDefine.getDataScheme();
    }

    private void fillDimensionValues(Map<String, List<String>> dimensionValues, DimensionValueSet queryDimension, String dimensionName) {
        Set<String> keySet = dimensionValues.keySet();
        ArrayList<String> attributeValue = new ArrayList<String>(keySet);
        if (attributeValue.size() > 1) {
            queryDimension.setValue(dimensionName, attributeValue);
        } else if (attributeValue.size() == 1) {
            queryDimension.setValue(dimensionName, attributeValue.get(0));
        }
    }

    private void mappingEntityKey(List<? extends EntityDataDO> entityData, Map<String, List<String>> dimensionValues, Map<String, String> dimensionAttributeMap, String dimensionName) {
        String attribute = dimensionAttributeMap.get(dimensionName);
        if (!StringUtils.hasText(attribute)) {
            log.error("\u67e5\u8be2\u5c01\u9762\u4ee3\u7801\u6570\u636e\u8868\u6307\u6807\u6570\u636e\u65f6\u53d1\u751f\u9519\u8bef\uff1a\u627e\u4e0d\u5230\u7ef4\u5ea6\u201c{}\u201d\u5bf9\u5e94\u7684\u5b9e\u4f53\u5c5e\u6027", (Object)dimensionName);
            throw new FMDMFormException(String.format("\u627e\u4e0d\u5230\u7ef4\u5ea6'%s'\u5bf9\u5e94\u7684\u5b9e\u4f53\u5c5e\u6027", dimensionName));
        }
        for (EntityDataDO entityDataDO : entityData) {
            AbstractData value = entityDataDO.getValue(attribute);
            if (value.isNull) continue;
            dimensionValues.computeIfAbsent(value.getAsString(), key -> new ArrayList()).add(entityDataDO.getFMDMKey());
        }
    }

    @NotNull
    private Map<String, String> getDimensionAttributeMap(String dataSchemeKey, IEntityModel entityModel) {
        List dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.DIMENSION);
        HashMap<String, String> dimensionMap = new HashMap<String, String>(dataSchemeDimension.size());
        for (DataDimension dimension : dataSchemeDimension) {
            IEntityAttribute attribute;
            String dimAttribute = dimension.getDimAttribute();
            if (!StringUtils.hasText(dimAttribute) || (attribute = entityModel.getAttribute(dimAttribute)) == null || attribute.isMultival()) continue;
            String dimensionName = this.entityMetaService.getDimensionName(dimension.getDimKey());
            dimensionMap.put(dimensionName, dimAttribute);
        }
        return dimensionMap;
    }

    private void setCodeToIndex(List<FieldDefine> fieldDefines, List<ZbDataDO> zbDataDOS) {
        if (!CollectionUtils.isEmpty(fieldDefines)) {
            HashMap<String, Integer> codeToIndex = new HashMap<String, Integer>(fieldDefines.size());
            for (int i = 0; i < fieldDefines.size(); ++i) {
                codeToIndex.put(fieldDefines.get(i).getCode(), i);
            }
            for (ZbDataDO dataDO : zbDataDOS) {
                Map<String, Integer> index = dataDO.getCodeToIndex();
                if (index != null) continue;
                dataDO.setCodeToIndex(codeToIndex);
            }
        }
    }

    @Override
    public EntityUpdateResult add(QueryParamDTO queryParamDTO, FMDMModifyDTO fmdmData) {
        if (!this.existForm(queryParamDTO)) {
            return super.add(queryParamDTO, fmdmData);
        }
        EntityUpdateResult entityCheckResult = super.add(queryParamDTO, fmdmData);
        if (!CollectionUtils.isEmpty(entityCheckResult.getCheckResult().getFailInfos())) {
            return entityCheckResult;
        }
        this.buildDefaultDimensionValue(queryParamDTO.getEntityId(), fmdmData, entityCheckResult);
        List<FieldDefine> fieldDefines = this.zbQueryService.getAllFieldDefineInForm(queryParamDTO.getFormKey());
        if (CollectionUtils.isEmpty(fieldDefines)) {
            return entityCheckResult;
        }
        this.regionDataService.updateRegionData(fmdmData, fieldDefines, queryParamDTO, FMDMModifyDTO::getDataModify);
        return entityCheckResult;
    }

    private void buildDefaultDimensionValue(String entityId, FMDMModifyDTO fmdmData, EntityUpdateResult result) {
        DimensionValueSet dimensionValueSet = fmdmData.getDimensionCombination().toDimensionValueSet();
        if (dimensionValueSet == null) {
            log.error("\u5c01\u9762\u4ee3\u7801\u65b0\u589e\u6570\u636e\u8868\u6307\u6807\u6570\u636e\u65f6\u51fa\u9519\uff1a\u672a\u8bbe\u7f6e\u7ef4\u5ea6\u4fe1\u606f");
            throw new FMDMFormException("\u672a\u8bbe\u7f6e\u7ef4\u5ea6\u4fe1\u606f");
        }
        Object periodValue = dimensionValueSet.getValue("DATATIME");
        if (periodValue == null || !StringUtils.hasText(periodValue.toString())) {
            log.error("\u5c01\u9762\u4ee3\u7801\u65b0\u589e\u6570\u636e\u8868\u6307\u6807\u6570\u636e\u65f6\u51fa\u9519\uff1a\u672a\u8bbe\u7f6e\u65f6\u671f\u7ef4\u5ea6\u4fe1\u606f");
            throw new FMDMFormException("\u672a\u8bbe\u7f6e\u65f6\u671f\u7ef4\u5ea6\u4fe1\u606f");
        }
        String bizDimension = this.entityMetaService.getDimensionName(entityId);
        Object value = dimensionValueSet.getValue(bizDimension);
        if (value == null) {
            IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
            String codeField = entityModel.getCodeField().getCode();
            String insertId = result.getSuccessKey(this.getFieldValue(fmdmData.getEntityModify(), codeField).toString());
            if (insertId != null) {
                dimensionValueSet.setValue(bizDimension, (Object)insertId);
            }
        }
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
        fmdmData.setDimensionCombination(dimensionCombinationBuilder.getCombination());
    }

    private Object getFieldValue(Map<String, Object> modifyValueMap, String fieldCode) {
        return modifyValueMap.get(fieldCode);
    }

    @Override
    public EntityUpdateResult update(QueryParamDTO queryParamDTO, FMDMModifyDTO fmdmData) {
        if (!this.existForm(queryParamDTO)) {
            return super.update(queryParamDTO, fmdmData);
        }
        List<Object> fieldDefines = this.zbQueryService.getAllFieldDefineInForm(queryParamDTO.getFormKey());
        EntityUpdateResult entityUpdateResult = super.update(queryParamDTO, fmdmData);
        if (CollectionUtils.isEmpty(fieldDefines)) {
            return entityUpdateResult;
        }
        Map<String, Object> modifyValueMap = fmdmData.getDataModify();
        Set<String> keySet = modifyValueMap.keySet();
        if (CollectionUtils.isEmpty(fieldDefines = fieldDefines.stream().filter(e -> keySet.contains(e.getCode())).collect(Collectors.toList()))) {
            return entityUpdateResult;
        }
        if (entityUpdateResult == null || entityUpdateResult.getSuccessKeys() == null) {
            List<? extends EntityDataDO> entityData = super.list(queryParamDTO);
            IEntityModel entityModel = this.entityMetaService.getEntityModel(queryParamDTO.getEntityId());
            if (entityData.size() > 0) {
                EntityDataDO entityRow = entityData.get(0);
                AbstractData codeValue = entityRow.getEntityValue(entityModel.getCodeField().getCode());
                entityUpdateResult = new EntityUpdateResult();
                if (!codeValue.isNull) {
                    entityUpdateResult.addCodeToKey(codeValue.getAsString(), entityRow.getFMDMKey());
                }
            } else {
                log.error("\u5c01\u9762\u4ee3\u7801\u66f4\u65b0\u6570\u636e\u8868\u6307\u6807\u6570\u636e\u65f6\u51fa\u9519\uff1a\u627e\u4e0d\u5230\u7684\u7ef4\u5ea6\u201c{}s\u5bf9\u5e94\u7684\u5b9e\u4f53\u6570\u636e\u201d", (Object)queryParamDTO.getDimensionCombination().toDimensionValueSet().toString());
                throw new FMDMFormException(String.format("\u627e\u4e0d\u5230\u7684\u7ef4\u5ea6\uff1a%s\u5bf9\u5e94\u7684\u5b9e\u4f53\u6570\u636e", queryParamDTO.getDimensionCombination().toDimensionValueSet().toString()));
            }
        }
        if (CollectionUtils.isEmpty(entityUpdateResult.getCheckResult().getFailInfos())) {
            this.regionDataService.updateRegionData(fmdmData, fieldDefines, queryParamDTO, FMDMModifyDTO::getDataModify);
        }
        return entityUpdateResult;
    }

    @Override
    public EntityUpdateResult delete(QueryParamDTO queryParamDTO) {
        return super.delete(queryParamDTO);
    }

    private void resetSuccessDeleteEntity(String entityId, QueryParamDTO queryParamDTO, List<String> successKeys) {
        String dimensionName = this.entityMetaService.getDimensionName(entityId);
        DimensionValueSet dimensionValueSet = queryParamDTO.getDimensionCombination().toDimensionValueSet();
        dimensionValueSet.setValue(dimensionName, successKeys);
    }

    @Override
    public EntityUpdateResult batchAdd(QueryParamDTO queryParamDTO, List<FMDMModifyDTO> updateList) {
        if (!this.existForm(queryParamDTO)) {
            return super.batchAdd(queryParamDTO, updateList);
        }
        List<FieldDefine> fieldDefines = this.zbQueryService.getAllFieldDefineInForm(queryParamDTO.getFormKey());
        EntityUpdateResult entityAddResult = super.batchAdd(queryParamDTO, updateList);
        if (CollectionUtils.isEmpty(fieldDefines)) {
            List<FieldDefine> unitFieldDefines;
            if (CollectionUtils.isEmpty(entityAddResult.getCheckResult().getFailInfos()) && !CollectionUtils.isEmpty(unitFieldDefines = this.zbQueryService.getAllUnitInfoFieldInForm(queryParamDTO.getFormKey()))) {
                FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(queryParamDTO.getFormSchemeKey());
                String dataSchemeKey = this.getDataSchemeKey(formScheme);
                List<FMDMModifyDTO> nullSceneDataList = this.batchBuildDimensionValue(queryParamDTO, updateList, entityAddResult, dataSchemeKey);
                updateList.addAll(nullSceneDataList);
            }
            return entityAddResult;
        }
        if (CollectionUtils.isEmpty(entityAddResult.getCheckResult().getFailInfos())) {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(queryParamDTO.getFormSchemeKey());
            String dataSchemeKey = this.getDataSchemeKey(formScheme);
            List<FMDMModifyDTO> nullSceneDataList = this.batchBuildDimensionValue(queryParamDTO, updateList, entityAddResult, dataSchemeKey);
            this.regionDataService.batchAddRegionData(queryParamDTO, updateList, fieldDefines, FMDMModifyDTO::getDataModify, false);
            updateList.addAll(nullSceneDataList);
        }
        return entityAddResult;
    }

    /*
     * Enabled aggressive block sorting
     */
    private List<FMDMModifyDTO> batchBuildDimensionValue(QueryParamDTO queryParamDTO, List<FMDMModifyDTO> updateList, EntityUpdateResult result, String dataSchemeKey) {
        ArrayList<FMDMModifyDTO> nullSceneDataList = new ArrayList<FMDMModifyDTO>();
        DimensionValueSet dimensionValueSet = queryParamDTO.getDimensionCombination().toDimensionValueSet();
        String entityId = queryParamDTO.getEntityId();
        String entityDimensionName = this.entityMetaService.getDimensionName(entityId);
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        Map<String, String> dimensionMap = this.getDimensionAttributeMap(dataSchemeKey, entityModel);
        Map<String, EntityDataDO> rowKeyMap = this.listData(queryParamDTO, updateList, entityDimensionName);
        int i = 0;
        while (true) {
            block8: {
                String rowKey;
                DimensionValueSet rowDimension;
                Map<String, Object> entityModify;
                FMDMModifyDTO fmdmDataDO;
                block11: {
                    Object codeValue;
                    block9: {
                        Object dimensionValue;
                        block10: {
                            if (i >= updateList.size()) {
                                return nullSceneDataList;
                            }
                            fmdmDataDO = updateList.get(i);
                            entityModify = fmdmDataDO.getEntityModify();
                            rowDimension = fmdmDataDO.getDimensionCombination().toDimensionValueSet();
                            if (rowDimension == null) {
                                rowDimension = new DimensionValueSet();
                                rowDimension.assign(dimensionValueSet);
                                DimensionCombinationImpl rowDimensionCombination = new DimensionCombinationImpl();
                                rowDimensionCombination.assignFrom(queryParamDTO.getDimensionCombination());
                                fmdmDataDO.setDimensionCombination((DimensionCombination)rowDimensionCombination);
                            } else {
                                this.mergeDimension(rowDimension, dimensionValueSet);
                            }
                            codeValue = this.getFieldValue(entityModify, entityModel.getCodeField().getCode());
                            if (codeValue != null) break block9;
                            dimensionValue = rowDimension.getValue(entityDimensionName);
                            if (dimensionValue != null && !"".equals(dimensionValue)) break block10;
                            updateList.remove(i);
                            --i;
                            break block8;
                        }
                        rowKey = dimensionValue.toString();
                        break block11;
                    }
                    rowKey = result.getSuccessKey(codeValue.toString());
                    if (StringUtils.hasText(rowKey)) break block11;
                    updateList.remove(i);
                    --i;
                    break block8;
                }
                rowDimension.setValue(entityDimensionName, (Object)rowKey);
                DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(rowDimension);
                fmdmDataDO.setDimensionCombination(dimensionCombinationBuilder.getCombination());
                int size = rowDimension.size();
                EntityDataDO queryRow = rowKeyMap.get(rowKey);
                for (int j = 0; j < size; ++j) {
                    String attribute;
                    Object value;
                    block12: {
                        Object entityRowValue;
                        String dimensionName;
                        block13: {
                            block14: {
                                value = rowDimension.getValue(j);
                                dimensionName = rowDimension.getName(j);
                                attribute = dimensionMap.get(dimensionName);
                                if (attribute == null) break block12;
                                entityRowValue = entityModify.get(attribute);
                                if (null != entityRowValue && !"".equals(entityRowValue)) break block13;
                                if (queryRow == null) break block14;
                                AbstractData attributeValue = queryRow.getValue(attribute);
                                if (!attributeValue.isNull) {
                                    rowDimension.setValue(dimensionName, attributeValue.getAsObject());
                                    break block12;
                                } else {
                                    nullSceneDataList.add(updateList.get(i));
                                    updateList.remove(i);
                                    --i;
                                    break block8;
                                }
                            }
                            nullSceneDataList.add(updateList.get(i));
                            updateList.remove(i);
                            --i;
                            break block8;
                        }
                        if (!value.equals(entityRowValue)) {
                            rowDimension.setValue(dimensionName, entityRowValue);
                        }
                    }
                    if (attribute != null || !"".equals(value)) continue;
                    nullSceneDataList.add(updateList.get(i));
                    updateList.remove(i);
                    --i;
                    break block8;
                }
                dimensionCombinationBuilder = new DimensionCombinationBuilder(rowDimension);
                fmdmDataDO.setDimensionCombination(dimensionCombinationBuilder.getCombination());
            }
            ++i;
        }
    }

    private Map<String, EntityDataDO> listData(QueryParamDTO queryParamDTO, List<FMDMModifyDTO> updateList, String entityDimensionName) {
        DimensionValueSet queryDimension = new DimensionValueSet();
        queryDimension.assign(queryParamDTO.getDimensionCombination().toDimensionValueSet());
        ArrayList<String> rowKeys = new ArrayList<String>();
        for (FMDMModifyDTO fmdmDataDO : updateList) {
            DimensionValueSet rowDimension = fmdmDataDO.getDimensionCombination().toDimensionValueSet();
            Object rowKey = rowDimension.getValue(entityDimensionName);
            if (null == rowKey || "".equals(rowKey)) continue;
            rowKeys.add(rowKey.toString());
        }
        queryDimension.setValue(entityDimensionName, rowKeys);
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(queryDimension);
        QueryParamDTO instance = QueryParamDTO.getInstance(queryParamDTO);
        instance.setEntityId(queryParamDTO.getEntityId());
        instance.setDimensionCombination(dimensionCombinationBuilder.getCombination());
        List<? extends EntityDataDO> entityDataRows = super.list(instance);
        return entityDataRows.stream().collect(Collectors.toMap(AbstractFMDMDataDO::getFMDMKey, e -> e));
    }

    private void mergeDimension(DimensionValueSet rowDimension, DimensionValueSet batchDimension) {
        int batchSize = batchDimension.size();
        for (int i = 0; i < batchSize; ++i) {
            String dimensionName = batchDimension.getName(i);
            Object dimensionValue = rowDimension.getValue(dimensionName);
            if (dimensionValue != null && !"".equals(dimensionValue)) continue;
            rowDimension.setValue(dimensionName, batchDimension.getValue(i));
        }
    }

    @Override
    public EntityUpdateResult batchUpdate(QueryParamDTO queryParamDTO, List<FMDMModifyDTO> updateList) {
        if (!this.existForm(queryParamDTO)) {
            return super.batchUpdate(queryParamDTO, updateList);
        }
        List<FieldDefine> fieldDefines = this.zbQueryService.getAllFieldDefineInForm(queryParamDTO.getFormKey());
        EntityUpdateResult entityUpdateResult = super.batchUpdate(queryParamDTO, updateList);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(queryParamDTO.getFormSchemeKey());
        String dataSchemeKey = this.getDataSchemeKey(formScheme);
        if (CollectionUtils.isEmpty(fieldDefines)) {
            List<FieldDefine> unitFieldDefines = this.zbQueryService.getAllUnitInfoFieldInForm(queryParamDTO.getFormKey());
            if (!CollectionUtils.isEmpty(unitFieldDefines)) {
                List<FMDMModifyDTO> nullSceneDataList = this.batchBuildDimensionValue(queryParamDTO, updateList, entityUpdateResult, dataSchemeKey);
                updateList.addAll(nullSceneDataList);
            }
            return entityUpdateResult;
        }
        List<FMDMModifyDTO> nullSceneDataList = this.batchBuildDimensionValue(queryParamDTO, updateList, entityUpdateResult, dataSchemeKey);
        this.regionDataService.batchUpdateRegionData(queryParamDTO, updateList, fieldDefines, FMDMModifyDTO::getDataModify, false);
        updateList.addAll(nullSceneDataList);
        return entityUpdateResult;
    }

    protected void innerSetData(Map<String, Object> modifyValue, Map<String, Object> updateData, Set<String> dataCodes) {
        Set<String> updateKeys = modifyValue.keySet();
        ArrayList<String> keys = new ArrayList<String>(updateKeys);
        for (int i = 0; i < keys.size(); ++i) {
            String updateKey = (String)keys.get(i);
            Object value = modifyValue.get(updateKey);
            if (!dataCodes.contains(updateKey)) continue;
            updateData.put(updateKey, value);
            modifyValue.remove(updateKey);
            --i;
            keys.remove(updateKey);
        }
    }

    protected Set<String> getDataCodes(List<FieldDefine> fieldDefines) {
        if (fieldDefines == null) {
            return Collections.emptySet();
        }
        return fieldDefines.stream().map(UniversalFieldDefine::getCode).collect(Collectors.toSet());
    }
}

