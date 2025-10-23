/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.entity.engine.result.EntityUpdateResult
 */
package com.jiuqi.nr.fmdm.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.entity.engine.result.EntityUpdateResult;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.domain.AbstractFMDMDataDO;
import com.jiuqi.nr.fmdm.domain.EntityInfoDO;
import com.jiuqi.nr.fmdm.domain.FMDMModifyDTO;
import com.jiuqi.nr.fmdm.domain.ZbDataDO;
import com.jiuqi.nr.fmdm.internal.dto.QueryParamDTO;
import com.jiuqi.nr.fmdm.service.IRegionDataService;
import com.jiuqi.nr.fmdm.service.impl.FormDataService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class UnitInfoDataService
extends FormDataService {
    private static final Logger log = LoggerFactory.getLogger(UnitInfoDataService.class);
    @Autowired
    private IRegionDataService regionDataService;

    @Override
    public void dataPreProcessing(FMDMModifyDTO modifyDTO, String formKey) {
        if (!modifyDTO.isCompatibility()) {
            return;
        }
        List<FieldDefine> fieldDefines = this.getFieldDefines(formKey);
        if (CollectionUtils.isEmpty(fieldDefines)) {
            super.dataPreProcessing(modifyDTO, formKey);
            return;
        }
        Set<String> dataCodes = this.getDataCodes(fieldDefines);
        this.innerSetData(modifyDTO.getModifyValueMap(), modifyDTO.getInfoModify(), dataCodes);
        super.dataPreProcessing(modifyDTO, formKey);
    }

    @Override
    public void batchDataPreProcessing(List<FMDMDataDTO> modifyDTO, String formKey) {
        List<FieldDefine> fieldDefines = this.getFieldDefines(formKey);
        if (CollectionUtils.isEmpty(fieldDefines)) {
            super.batchDataPreProcessing(modifyDTO, formKey);
            return;
        }
        Set<String> dataCodes = this.getDataCodes(fieldDefines);
        for (FMDMModifyDTO fMDMModifyDTO : modifyDTO) {
            this.innerSetData(fMDMModifyDTO.getModifyValueMap(), fMDMModifyDTO.getInfoModify(), dataCodes);
        }
        super.batchDataPreProcessing(modifyDTO, formKey);
    }

    public List<EntityInfoDO> list(QueryParamDTO queryParamDTO) {
        List<FieldDefine> fieldDefines;
        List<? extends ZbDataDO> entityData = super.list(queryParamDTO);
        ArrayList<EntityInfoDO> entityInfo = new ArrayList<EntityInfoDO>(entityData.size());
        if (!CollectionUtils.isEmpty(entityData)) {
            for (ZbDataDO zbDataDO : entityData) {
                entityInfo.add(new EntityInfoDO(zbDataDO));
            }
        }
        if (CollectionUtils.isEmpty(fieldDefines = this.zbQueryService.getAllUnitInfoFieldInForm(queryParamDTO))) {
            return entityInfo;
        }
        List<IDataRow> list = this.regionDataService.queryRegionData(queryParamDTO, fieldDefines);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, EntityInfoDO> entityInfoMap = entityInfo.stream().collect(Collectors.toMap(AbstractFMDMDataDO::getFMDMKey, e -> e, (e1, e2) -> e2));
            String dimensionName = this.entityMetaService.getDimensionName(queryParamDTO.getEntityId());
            HashMap<String, Integer> codeToIndex = new HashMap<String, Integer>(fieldDefines.size());
            int index = 0;
            for (FieldDefine fieldDefine : fieldDefines) {
                codeToIndex.put(fieldDefine.getCode(), index++);
            }
            for (IDataRow dataRow : list) {
                DimensionValueSet masterKeys = dataRow.getRowKeys();
                Object entityKeyData = masterKeys.getValue(dimensionName);
                EntityInfoDO entityDataDO = entityInfoMap.get(entityKeyData.toString());
                if (null == entityDataDO) continue;
                entityDataDO.setEntityInfoRow(dataRow);
            }
        }
        this.setCodeToIndex(fieldDefines, entityInfo);
        return new ArrayList<EntityInfoDO>(entityInfo);
    }

    protected void setCodeToIndex(List<FieldDefine> fieldDefines, List<EntityInfoDO> infoData) {
        if (!CollectionUtils.isEmpty(fieldDefines)) {
            HashMap<String, Integer> codeToIndex = new HashMap<String, Integer>(fieldDefines.size());
            for (int i = 0; i < fieldDefines.size(); ++i) {
                codeToIndex.put(fieldDefines.get(i).getCode(), i);
            }
            for (EntityInfoDO dataDO : infoData) {
                Map<String, Integer> index = dataDO.getEntityInfoIndex();
                if (index != null) continue;
                dataDO.setEntityInfoIndex(codeToIndex);
            }
        }
    }

    @Override
    public EntityUpdateResult add(QueryParamDTO queryParamDTO, FMDMModifyDTO fmdmData) {
        EntityUpdateResult entityCheckResult = super.add(queryParamDTO, fmdmData);
        List<Object> fieldDefines = this.getFieldDefines(queryParamDTO.getFormKey());
        if (CollectionUtils.isEmpty(fieldDefines) || !CollectionUtils.isEmpty(entityCheckResult.getCheckResult().getFailInfos())) {
            return entityCheckResult;
        }
        Set<String> keySet = fmdmData.getInfoModify().keySet();
        if (CollectionUtils.isEmpty(fieldDefines = fieldDefines.stream().filter(e -> keySet.contains(e.getCode())).collect(Collectors.toList()))) {
            return entityCheckResult;
        }
        String dimensionName = this.entityMetaService.getDimensionName(queryParamDTO.getEntityId());
        this.removeDimension(fmdmData, dimensionName);
        this.removeDimension(queryParamDTO, dimensionName);
        this.regionDataService.updateRegionData(fmdmData, fieldDefines, queryParamDTO, FMDMModifyDTO::getInfoModify);
        return entityCheckResult;
    }

    @Override
    public EntityUpdateResult update(QueryParamDTO queryParamDTO, FMDMModifyDTO fmdmData) {
        EntityUpdateResult entityUpdateResult = super.update(queryParamDTO, fmdmData);
        List<Object> fieldDefines = this.getFieldDefines(queryParamDTO.getFormKey());
        if (CollectionUtils.isEmpty(fieldDefines)) {
            return entityUpdateResult;
        }
        Set<String> keySet = fmdmData.getInfoModify().keySet();
        if (CollectionUtils.isEmpty(fieldDefines = fieldDefines.stream().filter(e -> keySet.contains(e.getCode())).collect(Collectors.toList()))) {
            return entityUpdateResult;
        }
        if (CollectionUtils.isEmpty(entityUpdateResult.getCheckResult().getFailInfos())) {
            String dimensionName = this.entityMetaService.getDimensionName(queryParamDTO.getEntityId());
            this.removeDimension(fmdmData, dimensionName);
            this.removeDimension(queryParamDTO, dimensionName);
            this.regionDataService.updateRegionData(fmdmData, fieldDefines, queryParamDTO, FMDMModifyDTO::getInfoModify);
        }
        return entityUpdateResult;
    }

    @Override
    public EntityUpdateResult delete(QueryParamDTO queryParamDTO) {
        return super.delete(queryParamDTO);
    }

    @Override
    public EntityUpdateResult batchAdd(QueryParamDTO queryParamDTO, List<FMDMModifyDTO> updateList) {
        EntityUpdateResult entityAddResult = super.batchAdd(queryParamDTO, updateList);
        List<FieldDefine> fieldDefines = this.getFieldDefines(queryParamDTO.getFormKey());
        if (CollectionUtils.isEmpty(fieldDefines)) {
            return entityAddResult;
        }
        if (CollectionUtils.isEmpty(entityAddResult.getCheckResult().getFailInfos())) {
            this.regionDataService.batchAddRegionData(queryParamDTO, updateList, fieldDefines, FMDMModifyDTO::getInfoModify, true);
        }
        return entityAddResult;
    }

    @Override
    public EntityUpdateResult batchUpdate(QueryParamDTO queryParamDTO, List<FMDMModifyDTO> updateList) {
        EntityUpdateResult entityUpdateResult = super.batchUpdate(queryParamDTO, updateList);
        List<FieldDefine> fieldDefines = this.getFieldDefines(queryParamDTO.getFormKey());
        if (CollectionUtils.isEmpty(fieldDefines)) {
            return entityUpdateResult;
        }
        this.regionDataService.batchUpdateRegionData(queryParamDTO, updateList, fieldDefines, FMDMModifyDTO::getInfoModify, true);
        return entityUpdateResult;
    }

    private List<FieldDefine> getFieldDefines(String formKey) {
        return this.zbQueryService.getAllUnitInfoFieldInForm(formKey);
    }

    private void removeDimension(FMDMModifyDTO fmdmDTO, String mainDimension) {
        DimensionValueSet dataDimension = fmdmDTO.getDimensionCombination().toDimensionValueSet();
        int size = dataDimension.size();
        for (int i = 0; i < size; ++i) {
            String dimensionName = dataDimension.getName(i);
            if (dimensionName.equals(mainDimension) || dimensionName.equals("DATATIME")) continue;
            dataDimension.clearValue(dimensionName);
            --i;
            --size;
        }
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dataDimension);
        fmdmDTO.setDimensionCombination(dimensionCombinationBuilder.getCombination());
    }
}

