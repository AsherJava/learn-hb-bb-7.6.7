/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.DataRegionTypeEnum
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service.impl;

import com.jiuqi.bde.common.constant.DataRegionTypeEnum;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.DimensionSettingDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.DimensionSettingEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.DimensionSettingService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DimensionSettingServiceImpl
implements DimensionSettingService {
    @Autowired
    private DimensionSettingDao dimensionSettingDao;

    @Override
    public void saveDimensionSetting(List<DimensionSettingEO> dimensionSettings) {
        ArrayList<List<Object>> deleteWhereValues = new ArrayList<List<Object>>();
        dimensionSettings.stream().forEach(dimensionSetting -> deleteWhereValues.add(Arrays.asList(dimensionSetting.getFormSchemeId(), dimensionSetting.getFetchSchemeId(), dimensionSetting.getFormId(), dimensionSetting.getDirectionType(), dimensionSetting.getPositionNum())));
        this.dimensionSettingDao.deleteBatchDimensionSetting(deleteWhereValues);
        this.dimensionSettingDao.addBatch(dimensionSettings);
    }

    @Override
    public Map<String, Object> getDimensionSettingMapping(FetchSettingCond fetchSettingCond) {
        List<DimensionSettingEO> dimensionSettings = this.dimensionSettingDao.listDimensionSetting(fetchSettingCond);
        Map<String, List<DimensionSettingEO>> dimSettingGroupByDirectionTypes = dimensionSettings.stream().filter(dimensionSettingEO -> !StringUtils.isEmpty((String)dimensionSettingEO.getDirectionType())).collect(Collectors.groupingBy(DimensionSettingEO::getDirectionType));
        HashMap<String, Object> dimSettingMapping = new HashMap<String, Object>(16);
        if (!CollectionUtils.isEmpty(dimensionSettings)) {
            DimensionSettingEO dimensionSetting = dimensionSettings.get(0);
            dimSettingMapping.put("globalDim", dimensionSetting.getGlobalDim());
            dimSettingMapping.put("globalDimValue", dimensionSetting.getGlobalDimValue());
            dimSettingMapping.put("rowDim", dimensionSetting.getRowDim());
            dimSettingMapping.put("colDim", dimensionSetting.getColDim());
        }
        HashMap<String, String> colDimSetting = new HashMap<String, String>(16);
        HashMap<String, String> rowDimSetting = new HashMap<String, String>(16);
        for (List<DimensionSettingEO> dimensionSettingDirectionTypes : dimSettingGroupByDirectionTypes.values()) {
            Map<String, List<DimensionSettingEO>> dimSettingGroupByPositionNum = dimensionSettingDirectionTypes.stream().collect(Collectors.groupingBy(DimensionSettingEO::getPositionNum));
            for (List<DimensionSettingEO> dimensionSettingPositionNums : dimSettingGroupByPositionNum.values()) {
                for (DimensionSettingEO dimensionSetting : dimensionSettingPositionNums) {
                    if (DataRegionTypeEnum.COL.getCode().equals(dimensionSetting.getDirectionType())) {
                        colDimSetting.put(dimensionSetting.getPositionNum(), dimensionSetting.getDimSetting());
                        continue;
                    }
                    rowDimSetting.put(dimensionSetting.getPositionNum(), dimensionSetting.getDimSetting());
                }
            }
        }
        if (!colDimSetting.isEmpty()) {
            dimSettingMapping.put("colDimSetting", colDimSetting);
        }
        if (!rowDimSetting.isEmpty()) {
            dimSettingMapping.put("rowDimSetting", rowDimSetting);
        }
        return dimSettingMapping;
    }
}

