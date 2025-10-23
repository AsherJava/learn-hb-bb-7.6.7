/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.fmdm.service.impl;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.domain.FMDMModifyDTO;
import com.jiuqi.nr.fmdm.exception.FMDMDataException;
import com.jiuqi.nr.fmdm.exception.FMDMUpdateException;
import com.jiuqi.nr.fmdm.internal.dto.QueryParamDTO;
import com.jiuqi.nr.fmdm.internal.provider.ModifyDataProvider;
import com.jiuqi.nr.fmdm.service.IRegionDataService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class RegionDataServiceImpl
implements IRegionDataService {
    private static final Logger logs = LoggerFactory.getLogger(RegionDataServiceImpl.class);
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    protected IEntityMetaService entityMetaService;
    @Autowired
    protected IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    @Override
    public List<IDataRow> queryRegionData(QueryParamDTO queryParamDTO, List<FieldDefine> fieldDefines) {
        ArrayList<IDataRow> result = new ArrayList<IDataRow>();
        if (CollectionUtils.isEmpty(fieldDefines)) {
            return result;
        }
        IDataQuery iDataQuery = this.dataAccessProvider.newDataQuery();
        HashMap<String, Integer> codeToIndex = new HashMap<String, Integer>(fieldDefines.size());
        int index = 0;
        for (FieldDefine fieldDefine : fieldDefines) {
            iDataQuery.addColumn(fieldDefine);
            codeToIndex.put(fieldDefine.getCode(), index++);
        }
        DimensionValueSet dimensionValueSet = queryParamDTO.getDimensionCombination().toDimensionValueSet();
        if (dimensionValueSet != null) {
            int size = dimensionValueSet.size();
            ArrayList<String> names = new ArrayList<String>();
            for (int i = 0; i < size; ++i) {
                Object value = dimensionValueSet.getValue(i);
                if (value != null && !"".equals(value)) continue;
                names.add(dimensionValueSet.getName(i));
            }
            names.forEach(arg_0 -> ((DimensionValueSet)dimensionValueSet).clearValue(arg_0));
        }
        iDataQuery.setMasterKeys(dimensionValueSet);
        String dimensionName = this.entityMetaService.getDimensionName(queryParamDTO.getEntityId());
        try {
            ExecutorContext executorContext = (ExecutorContext)queryParamDTO.getContext();
            executorContext.setAutoDataMasking(queryParamDTO.isDataMasking());
            IDataTable iDataTable = iDataQuery.executeQuery(executorContext);
            int totalCount = iDataTable.getTotalCount();
            for (int i = 0; i < totalCount; ++i) {
                Object masterKeysValue;
                IDataRow item = iDataTable.getItem(i);
                DimensionValueSet masterKeys = item.getRowKeys();
                if (masterKeys == null || (masterKeysValue = masterKeys.getValue(dimensionName)) == null) continue;
                result.add(item);
            }
        }
        catch (Exception e) {
            logs.error(e.getMessage(), e);
        }
        return result;
    }

    public void addRegionData(FMDMModifyDTO fmdmModifyDTO, List<FieldDefine> fieldDefines, QueryParamDTO queryParamDTO, ModifyDataProvider dataProvider) {
        IDataQuery iDataQuery = this.dataAccessProvider.newDataQuery();
        HashMap<String, Integer> codeToIndex = new HashMap<String, Integer>(fieldDefines.size());
        int index = 0;
        for (FieldDefine fieldDefine : fieldDefines) {
            iDataQuery.addColumn(fieldDefine);
            codeToIndex.put(fieldDefine.getCode(), index++);
        }
        Map<String, Object> modifyValueMap = dataProvider.getData(fmdmModifyDTO);
        iDataQuery.setMasterKeys(queryParamDTO.getDimensionCombination().toDimensionValueSet());
        try {
            IDataUpdator iDataUpdator = iDataQuery.openForUpdate((ExecutorContext)queryParamDTO.getContext());
            IDataRow iDataRow = iDataUpdator.addInsertedRow(fmdmModifyDTO.getDimensionCombination().toDimensionValueSet());
            modifyValueMap.forEach((key, value) -> {
                Integer columnIndex = (Integer)codeToIndex.get(key);
                if (columnIndex != null) {
                    iDataRow.setValue(columnIndex.intValue(), value);
                }
            });
            iDataUpdator.commitChanges(false);
        }
        catch (Exception e) {
            logs.error(e.getMessage(), e);
        }
    }

    @Override
    public void updateRegionData(FMDMModifyDTO fmdmModifyDTO, List<FieldDefine> fieldDefines, QueryParamDTO queryParamDTO, ModifyDataProvider dataProvider) {
        IDataQuery iDataQuery = this.dataAccessProvider.newDataQuery();
        HashMap<String, Integer> codeToIndex = new HashMap<String, Integer>(fieldDefines.size());
        int index = 0;
        for (FieldDefine fieldDefine : fieldDefines) {
            iDataQuery.addColumn(fieldDefine);
            codeToIndex.put(fieldDefine.getCode(), index++);
        }
        iDataQuery.setMasterKeys(queryParamDTO.getDimensionCombination().toDimensionValueSet());
        Map<String, Object> modifyValueMap = dataProvider.getData(fmdmModifyDTO);
        try {
            ExecutorContext executorContext = (ExecutorContext)queryParamDTO.getContext();
            executorContext.setAutoDataMasking(false);
            IDataTable iDataTable = iDataQuery.executeQuery(executorContext);
            IDataRow iDataRow = iDataTable.findRow(fmdmModifyDTO.getDimensionCombination().toDimensionValueSet());
            if (iDataRow == null) {
                iDataRow = iDataTable.appendRow(fmdmModifyDTO.getDimensionCombination().toDimensionValueSet());
            }
            Set<String> keySet = modifyValueMap.keySet();
            for (String key : keySet) {
                Integer columnIndex = (Integer)codeToIndex.get(key);
                if (columnIndex == null) continue;
                iDataRow.setValue(columnIndex.intValue(), modifyValueMap.get(key));
            }
            iDataTable.commitChanges(false);
        }
        catch (Exception e) {
            logs.error(e.getMessage(), e);
        }
    }

    public void deleteRegionData(QueryParamDTO queryParamDTO, List<FieldDefine> fieldDefines) throws FMDMUpdateException {
        IDataQuery iDataQuery = this.dataAccessProvider.newDataQuery();
        for (FieldDefine fieldDefine : fieldDefines) {
            iDataQuery.addColumn(fieldDefine);
        }
        iDataQuery.setMasterKeys(queryParamDTO.getDimensionCombination().toDimensionValueSet());
        try {
            IDataUpdator iDataUpdator = iDataQuery.openForUpdate((ExecutorContext)queryParamDTO.getContext());
            iDataUpdator.addDeletedRow(queryParamDTO.getDimensionCombination().toDimensionValueSet());
            iDataUpdator.commitChanges();
        }
        catch (Exception e) {
            logs.error(e.getMessage(), e);
            throw new FMDMUpdateException(e);
        }
    }

    @Override
    public void batchUpdateRegionData(QueryParamDTO queryParamDTO, List<FMDMModifyDTO> updateList, List<FieldDefine> fieldDefines, ModifyDataProvider dataProvider) {
        boolean isUnitInfo = this.isUnitInfoField(queryParamDTO.getFormSchemeKey(), fieldDefines.get(0).getCode());
        this.batchUpdateRegionData(queryParamDTO, updateList, fieldDefines, dataProvider, isUnitInfo);
    }

    @Override
    public void batchUpdateRegionData(QueryParamDTO queryParamDTO, List<FMDMModifyDTO> updateList, List<FieldDefine> fieldDefines, ModifyDataProvider dataProvider, boolean isUnitInfo) {
        IDataQuery iDataQuery = this.dataAccessProvider.newDataQuery();
        HashMap<String, Integer> codeToIndex = new HashMap<String, Integer>(fieldDefines.size());
        int index = 0;
        for (FieldDefine fieldDefine : fieldDefines) {
            iDataQuery.addColumn(fieldDefine);
            codeToIndex.put(fieldDefine.getCode(), index++);
        }
        String dimensionName = isUnitInfo ? this.entityMetaService.getDimensionName(queryParamDTO.getEntityId()) : null;
        DimensionValueSet dimensionValueSet = this.removeUselessDimension(queryParamDTO);
        iDataQuery.setMasterKeys(dimensionValueSet);
        try {
            ExecutorContext executorContext = (ExecutorContext)queryParamDTO.getContext();
            executorContext.setAutoDataMasking(false);
            IDataTable iDataTable = iDataQuery.executeQuery(executorContext);
            for (FMDMModifyDTO fmdmDataDO : updateList) {
                Map<String, Object> modifyValueMap = dataProvider.getData(fmdmDataDO);
                if (CollectionUtils.isEmpty(modifyValueMap)) continue;
                DimensionValueSet rowDim = this.dealDimension(fmdmDataDO, dimensionName, isUnitInfo);
                IDataRow iDataRow = iDataTable.findRow(rowDim);
                if (iDataRow == null) {
                    iDataRow = iDataTable.appendRow(rowDim);
                }
                Set<String> keySet = modifyValueMap.keySet();
                for (String key : keySet) {
                    Integer columnIndex = (Integer)codeToIndex.get(key);
                    if (columnIndex == null) continue;
                    iDataRow.setValue(columnIndex.intValue(), modifyValueMap.get(key));
                }
            }
            iDataTable.commitChanges(false);
        }
        catch (Exception e) {
            logs.error(e.getMessage(), e);
            throw new FMDMDataException("\u6279\u91cf\u66f4\u65b0" + (isUnitInfo ? "\u5355\u4f4d\u4fe1\u606f\u8868" : "\u6570\u636e\u8868") + "\u6307\u6807\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38\uff01:" + e.getMessage(), e);
        }
    }

    private DimensionValueSet removeUselessDimension(QueryParamDTO queryParamDTO) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        DimensionValueSet queryDimension = queryParamDTO.getDimensionCombination().toDimensionValueSet();
        dimensionValueSet.assign(queryDimension);
        int size = dimensionValueSet.size();
        for (int i = 0; i < size; ++i) {
            Object value = dimensionValueSet.getValue(i);
            if (!ObjectUtils.isEmpty(value)) continue;
            dimensionValueSet.clearValue(dimensionValueSet.getName(i));
        }
        return dimensionValueSet;
    }

    @Override
    public void batchAddRegionData(QueryParamDTO queryParamDTO, List<FMDMModifyDTO> updateList, List<FieldDefine> fieldDefines, ModifyDataProvider dataProvider) {
        boolean isUnitInfo = this.isUnitInfoField(queryParamDTO.getFormSchemeKey(), fieldDefines.get(0).getCode());
        this.batchAddRegionData(queryParamDTO, updateList, fieldDefines, dataProvider, isUnitInfo);
    }

    @Override
    public void batchAddRegionData(QueryParamDTO queryParamDTO, List<FMDMModifyDTO> updateList, List<FieldDefine> fieldDefines, ModifyDataProvider dataProvider, boolean isUnitInfo) {
        IDataQuery iDataQuery = this.dataAccessProvider.newDataQuery();
        HashMap<String, Integer> codeToIndex = new HashMap<String, Integer>(fieldDefines.size());
        int index = 0;
        for (FieldDefine fieldDefine : fieldDefines) {
            iDataQuery.addColumn(fieldDefine);
            codeToIndex.put(fieldDefine.getCode(), index++);
        }
        String dimensionName = isUnitInfo ? this.entityMetaService.getDimensionName(queryParamDTO.getEntityId()) : null;
        ArrayList<DimensionValueSet> dimensionValueSetList = new ArrayList<DimensionValueSet>(updateList.size());
        for (FMDMModifyDTO fmdmModifyDTO : updateList) {
            DimensionValueSet dimensionValueSet = this.dealDimension(fmdmModifyDTO, dimensionName, isUnitInfo);
            dimensionValueSetList.add(dimensionValueSet);
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(dimensionValueSetList);
        iDataQuery.setMasterKeys(dimensionValueSet);
        try {
            ExecutorContext executorContext = (ExecutorContext)queryParamDTO.getContext();
            executorContext.setAutoDataMasking(false);
            IDataTable iDataTable = iDataQuery.executeQuery(executorContext);
            for (FMDMModifyDTO fmdmDataDO : updateList) {
                Map<String, Object> modifyValueMap = dataProvider.getData(fmdmDataDO);
                if (CollectionUtils.isEmpty(modifyValueMap)) continue;
                DimensionValueSet rowDim = this.dealDimension(fmdmDataDO, dimensionName, isUnitInfo);
                IDataRow iDataRow = iDataTable.findRow(rowDim);
                if (iDataRow == null) {
                    iDataRow = iDataTable.appendRow(rowDim);
                }
                Set<String> keySet = modifyValueMap.keySet();
                for (String key : keySet) {
                    Integer columnIndex = (Integer)codeToIndex.get(key);
                    if (columnIndex == null) continue;
                    iDataRow.setValue(columnIndex.intValue(), modifyValueMap.get(key));
                }
            }
            iDataTable.commitChanges(false);
        }
        catch (Exception e) {
            logs.error(e.getMessage(), e);
            throw new FMDMDataException("\u6279\u91cf\u65b0\u589e" + (isUnitInfo ? "\u5355\u4f4d\u4fe1\u606f\u8868" : "\u6570\u636e\u8868") + "\u6307\u6807\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38\uff01:" + e.getMessage(), e);
        }
    }

    private DimensionValueSet dealDimension(FMDMModifyDTO fmdmDTO, String mainDimension, boolean isUnitInfo) {
        if (!isUnitInfo) {
            return fmdmDTO.getDimensionCombination().toDimensionValueSet();
        }
        DimensionValueSet dataDimension = new DimensionValueSet(fmdmDTO.getDimensionCombination().toDimensionValueSet());
        int size = dataDimension.size();
        for (int i = 0; i < size; ++i) {
            String dimensionName = dataDimension.getName(i);
            if (dimensionName.equals(mainDimension) || dimensionName.equals("DATATIME")) continue;
            dataDimension.clearValue(dimensionName);
            --i;
            --size;
        }
        return dataDimension;
    }

    private boolean isUnitInfoField(String formSchemeKey, String dataFieldCode) {
        if (!StringUtils.hasText(formSchemeKey) || !StringUtils.hasText(dataFieldCode)) {
            return false;
        }
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
        DataField dataField = this.runtimeDataSchemeService.getDataFieldFromMdInfoByCode(taskDefine.getDataScheme(), dataFieldCode);
        return dataField != null;
    }
}

