/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.access.param.EntityDimData
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.data.engine.gather.GatherEntityItem
 *  com.jiuqi.nr.data.engine.gather.GatherEntityValue
 *  com.jiuqi.nr.data.logic.facade.param.input.CKDTransferMap
 *  com.jiuqi.nr.data.logic.facade.param.input.CKDTransferPar
 *  com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.gather.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.param.EntityDimData;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.engine.gather.GatherEntityItem;
import com.jiuqi.nr.data.engine.gather.GatherEntityValue;
import com.jiuqi.nr.data.logic.facade.param.input.CKDTransferMap;
import com.jiuqi.nr.data.logic.facade.param.input.CKDTransferPar;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ErrorItemListGatherService {
    @Autowired
    private ICheckErrorDescriptionService checkErrorDescriptionService;
    @Autowired
    private DimensionCollectionUtil dimCollectionBuildUtil;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;

    public void doErrorItemListGather(String formSchemeKey, List<String> accessFormKeys, DimensionValueSet dimensionValueSet, Map<Integer, Map<String, String>> bizKeyOrderMappings, GatherEntityValue gatherEntityValue, Map<String, String> gatherSingleDims) throws Exception {
        if (CollectionUtils.isEmpty(bizKeyOrderMappings)) {
            return;
        }
        EntityDimData dwEntityDimData = this.dataAccesslUtil.getDwEntityDimData(formSchemeKey);
        CKDTransferPar ckdTransferPar = new CKDTransferPar();
        ckdTransferPar.setFormSchemeKey(formSchemeKey);
        ckdTransferPar.setFormKeys(accessFormKeys);
        List collect = bizKeyOrderMappings.keySet().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        Map<Integer, List<GatherEntityItem>> entities = this.dealGatherEntity(gatherEntityValue);
        ArrayList<String> strings = new ArrayList<String>(gatherSingleDims.keySet());
        for (String dim : strings) {
            dimensionValueSet.setValue(dim, (Object)"");
        }
        for (Integer level : collect) {
            List<GatherEntityItem> gatherEntityItems;
            Map<String, String> stringStringMap = bizKeyOrderMappings.get(level);
            if (CollectionUtils.isEmpty(stringStringMap) || CollectionUtils.isEmpty(gatherEntityItems = entities.get(level))) continue;
            Map<String, String> entityMap = gatherEntityItems.stream().collect(Collectors.toMap(GatherEntityItem::getId, GatherEntityItem::getPid));
            dimensionValueSet.setValue(dwEntityDimData.getDimensionName(), new ArrayList<String>(entityMap.keySet()));
            DimensionCollection dimensionCollection = this.dimCollectionBuildUtil.getDimensionCollection(dimensionValueSet, formSchemeKey);
            ckdTransferPar.setSrcDimensionCollection(dimensionCollection);
            CKDTransferMap ckdTransferMap = new CKDTransferMap();
            ckdTransferMap.setBizKeyOrderMap(stringStringMap);
            ckdTransferMap.setMainDimValueMap(entityMap);
            ckdTransferPar.setTransferMap(ckdTransferMap);
            ckdTransferPar.setIgnDimNames(strings);
            this.checkErrorDescriptionService.transferCheckDes(ckdTransferPar);
        }
    }

    private Map<Integer, List<GatherEntityItem>> dealGatherEntity(GatherEntityValue gatherEntityValue) {
        ArrayList<GatherEntityItem> batchValues = new ArrayList<GatherEntityItem>();
        int entityCount = gatherEntityValue.getIdValues().size();
        for (int index = 0; index < entityCount; ++index) {
            GatherEntityItem rowValue = new GatherEntityItem();
            rowValue.setId((String)gatherEntityValue.getIdValues().get(index));
            rowValue.setPid((String)gatherEntityValue.getPidValues().get(index));
            rowValue.setLevel((Integer)gatherEntityValue.getLevelValues().get(index));
            batchValues.add(rowValue);
        }
        return batchValues.stream().collect(Collectors.groupingBy(GatherEntityItem::getLevel));
    }
}

