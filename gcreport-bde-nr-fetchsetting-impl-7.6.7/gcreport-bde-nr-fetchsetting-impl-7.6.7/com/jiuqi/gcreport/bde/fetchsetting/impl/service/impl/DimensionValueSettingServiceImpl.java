/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.DataRegionTypeEnum
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service.impl;

import com.jiuqi.bde.common.constant.DataRegionTypeEnum;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.DimensionValueSettingDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.DimensionValueSettingEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.DimensionValueSettingService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DimensionValueSettingServiceImpl
implements DimensionValueSettingService {
    @Autowired
    private DimensionValueSettingDao dimensionValueSettingDao;

    @Override
    public void saveDimensionValueSetting(List<DimensionValueSettingEO> dimensionValueSettings) {
        ArrayList<List<Object>> deleteWhereValues = new ArrayList<List<Object>>();
        dimensionValueSettings.stream().forEach(dimensionSetting -> deleteWhereValues.add(Arrays.asList(dimensionSetting.getFormSchemeId(), dimensionSetting.getFetchSchemeId(), dimensionSetting.getFormId(), dimensionSetting.getDirectionType(), dimensionSetting.getPositionNum())));
        this.dimensionValueSettingDao.deleteBatchDimensionValueSetting(deleteWhereValues);
        this.dimensionValueSettingDao.addBatch(dimensionValueSettings);
    }

    @Override
    public Map<String, Object> getDimensionValueSettingMapping(FetchSettingCond fetchSettingCond) {
        List<DimensionValueSettingEO> dimensionValueSettings = this.dimensionValueSettingDao.listDimensionSetting(fetchSettingCond);
        HashMap colDimValueSetting = new HashMap(16);
        HashMap rowDimValueSetting = new HashMap(16);
        Map<String, List<DimensionValueSettingEO>> dimValueSettingMappingGroupByDirectionType = dimensionValueSettings.stream().collect(Collectors.groupingBy(DimensionValueSettingEO::getDirectionType));
        for (List<DimensionValueSettingEO> dimValueSettingGroupByDirectionTypes : dimValueSettingMappingGroupByDirectionType.values()) {
            Map<String, List<DimensionValueSettingEO>> dimValueSettingMappingGroupByPositionNum = dimValueSettingGroupByDirectionTypes.stream().collect(Collectors.groupingBy(DimensionValueSettingEO::getPositionNum));
            for (List<DimensionValueSettingEO> dimValueSettingGroupByPositionNums : dimValueSettingMappingGroupByPositionNum.values()) {
                Map<String, List<DimensionValueSettingEO>> dimValueSettingMappingGroupByRowGroupId = dimValueSettingGroupByPositionNums.stream().collect(Collectors.groupingBy(DimensionValueSettingEO::getRowGroupId));
                ArrayList dimValueData = new ArrayList();
                for (List<DimensionValueSettingEO> dimValueSettingGroupByRowGroupIds : dimValueSettingMappingGroupByRowGroupId.values()) {
                    HashMap<String, String> dimValueMapping = new HashMap<String, String>(16);
                    for (DimensionValueSettingEO dimensionValueSetting : dimValueSettingGroupByRowGroupIds) {
                        dimValueMapping.put(dimensionValueSetting.getDimType(), dimensionValueSetting.getDimValue());
                    }
                    dimValueData.add(dimValueMapping);
                }
                if (DataRegionTypeEnum.COL.getCode().equals(dimValueSettingGroupByDirectionTypes.get(0).getDirectionType())) {
                    colDimValueSetting.put(dimValueSettingGroupByPositionNums.get(0).getPositionNum(), dimValueData);
                    continue;
                }
                rowDimValueSetting.put(dimValueSettingGroupByPositionNums.get(0).getPositionNum(), dimValueData);
            }
        }
        HashMap<String, Object> dimValueSettingMapping = new HashMap<String, Object>(16);
        if (!colDimValueSetting.isEmpty()) {
            dimValueSettingMapping.put("colDimValueSetting", colDimValueSetting);
        }
        if (!rowDimValueSetting.isEmpty()) {
            dimValueSettingMapping.put("rowDimValueSetting", rowDimValueSetting);
        }
        return dimValueSettingMapping;
    }
}

