/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ENameSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.access.DataPermission
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionResource
 *  com.jiuqi.nr.dataservice.core.access.inner.DimResources
 *  com.jiuqi.nr.dataservice.core.access.inner.UnitDimensionMerger
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 */
package com.jiuqi.nr.data.access.util;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.access.DataPermission;
import com.jiuqi.nr.dataservice.core.access.DataPermissionResource;
import com.jiuqi.nr.dataservice.core.access.inner.DimResources;
import com.jiuqi.nr.dataservice.core.access.inner.UnitDimensionMerger;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class DataPermissionUtil {
    public static DimensionAccessFormInfo toBatchAccessForms(DataPermission dataPermission, String dimensionName) {
        DimensionAccessFormInfo dimensionAccessFormInfo = new DimensionAccessFormInfo();
        dimensionAccessFormInfo.getAllFormKeys().addAll(dataPermission.getResourceIds());
        List<DimensionAccessFormInfo.AccessFormInfo> accessForms = dimensionAccessFormInfo.getAccessForms();
        List<DimensionAccessFormInfo.NoAccessFormInfo> noAccessForms = dimensionAccessFormInfo.getNoAccessForms();
        Collection accessResources = dataPermission.getAccessResources();
        Optional first = accessResources.stream().findFirst();
        if (first.isPresent()) {
            DataPermissionResource dataPermissionResource = (DataPermissionResource)first.get();
            DimensionCombination dimensionCombination = dataPermissionResource.getDimensionCombination();
            ENameSet eNameSet = new ENameSet();
            for (FixedDimensionValue fixedDimensionValue : dimensionCombination) {
                eNameSet.add(fixedDimensionValue.getName());
            }
            UnitDimensionMerger accessMerger = new UnitDimensionMerger(new ArrayList(dataPermission.getResourceIds()), eNameSet, dimensionName);
            HashMap<DimensionCombination, List> dimFormKeys = new HashMap<DimensionCombination, List>();
            for (DataPermissionResource dataPermissionResource2 : accessResources) {
                String resourceId = dataPermissionResource2.getResourceId();
                DimensionCombination combination = dataPermissionResource2.getDimensionCombination();
                dimFormKeys.computeIfAbsent(combination, r -> new ArrayList()).add(resourceId);
            }
            for (Map.Entry entry : dimFormKeys.entrySet()) {
                DimensionCombination key = (DimensionCombination)entry.getKey();
                List formKeys = (List)entry.getValue();
                accessMerger.addUnitDimension(key.toDimensionValueSet(), formKeys);
            }
            dataPermission.setAccessResources(null);
            List dimForms = accessMerger.getDimForms();
            eNameSet.remove(dimensionName);
            for (DimResources dimForm : dimForms) {
                HashMap<String, DimensionValue> masterKeys = new HashMap<String, DimensionValue>();
                List mergeValues = dimForm.getMergeValues();
                DimensionValue dimensionValue = new DimensionValue();
                dimensionValue.setName(dimensionName);
                dimensionValue.setValue(String.join((CharSequence)";", mergeValues));
                masterKeys.put(dimensionName, dimensionValue);
                for (int i = 0; i < dimForm.getOtherValues().size(); ++i) {
                    String otherDimensionName = eNameSet.get(i);
                    String otherValue = (String)dimForm.getOtherValues().get(i);
                    DimensionValue otherDimValue = new DimensionValue();
                    otherDimValue.setName(otherDimensionName);
                    otherDimValue.setValue(otherValue);
                    masterKeys.put(otherDimensionName, otherDimValue);
                }
                DimensionAccessFormInfo.AccessFormInfo accessFormInfo = new DimensionAccessFormInfo.AccessFormInfo(masterKeys, dimForm.getForms());
                accessForms.add(accessFormInfo);
            }
        }
        dimensionAccessFormInfo.setAccessForms(accessForms);
        for (DataPermissionResource unAccessResource : dataPermission.getUnAccessResources()) {
            Map<String, DimensionValue> dimensonMap = DimensionValueSetUtil.getDimensionSet(unAccessResource.getDimensionCombination().toDimensionValueSet());
            DimensionAccessFormInfo.NoAccessFormInfo noAccessFormInfo = new DimensionAccessFormInfo.NoAccessFormInfo(dimensonMap, unAccessResource.getResourceId(), unAccessResource.getMessage());
            noAccessForms.add(noAccessFormInfo);
        }
        return dimensionAccessFormInfo;
    }

    public static void mergeDimensionValue(DimensionAccessFormInfo dimensionAccessFormInfo, String dimensionName) {
        List accessFormInfos;
        LinkedHashMap<String, List> dimensionMap = new LinkedHashMap<String, List>();
        for (DimensionAccessFormInfo.AccessFormInfo accessFormInfo : dimensionAccessFormInfo.getAccessForms()) {
            List<String> formKeys = accessFormInfo.getFormKeys();
            String formKey = formKeys.get(0);
            Map<String, DimensionValue> dimMap = accessFormInfo.getDimensions();
            DimensionValueSet orgDim = DimensionValueSetUtil.getDimensionValueSet(dimMap);
            orgDim.clearValue(dimensionName);
            orgDim.clearValue("DATATIME");
            String key = orgDim + "-" + formKey;
            List list = dimensionMap.computeIfAbsent(key, r -> new ArrayList());
            list.add(accessFormInfo);
        }
        dimensionAccessFormInfo.getAccessForms().clear();
        for (Map.Entry entry : dimensionMap.entrySet()) {
            accessFormInfos = (List)entry.getValue();
            List<Map<String, DimensionValue>> dimMap = accessFormInfos.stream().map(DimensionAccessFormInfo.AccessFormInfo::getDimensions).collect(Collectors.toList());
            Map<String, DimensionValue> mergeDims = DimensionValueSetUtil.mergeDimensionSetMap(dimMap);
            String formKey = ((DimensionAccessFormInfo.AccessFormInfo)accessFormInfos.get(0)).getFormKeys().get(0);
            List<String> formKeys = Collections.singletonList(formKey);
            DimensionAccessFormInfo.AccessFormInfo accessInfo = new DimensionAccessFormInfo.AccessFormInfo(mergeDims, formKeys);
            dimensionAccessFormInfo.getAccessForms().add(accessInfo);
        }
        dimensionMap.clear();
        for (DimensionAccessFormInfo.AccessFormInfo accessFormInfo : dimensionAccessFormInfo.getAccessForms()) {
            Map<String, DimensionValue> dimensions = accessFormInfo.getDimensions();
            DimensionValueSet orgDim = DimensionValueSetUtil.getDimensionValueSet(dimensions);
            List list = dimensionMap.computeIfAbsent(orgDim.toString(), r -> new ArrayList());
            list.add(accessFormInfo);
        }
        dimensionAccessFormInfo.getAccessForms().clear();
        for (Map.Entry entry : dimensionMap.entrySet()) {
            accessFormInfos = (List)entry.getValue();
            List<String> formKeys = accessFormInfos.stream().map(DimensionAccessFormInfo.AccessFormInfo::getFormKeys).flatMap(Collection::stream).distinct().collect(Collectors.toList());
            DimensionAccessFormInfo.AccessFormInfo accessFormInfo = (DimensionAccessFormInfo.AccessFormInfo)accessFormInfos.get(0);
            Map<String, DimensionValue> dimensions = accessFormInfo.getDimensions();
            DimensionAccessFormInfo.AccessFormInfo accessInfo = new DimensionAccessFormInfo.AccessFormInfo(dimensions, formKeys);
            dimensionAccessFormInfo.getAccessForms().add(accessInfo);
        }
    }

    public static Collection<DimensionValueSet> mergeDimensionValue(Collection<DimensionValueSet> dimensionValueSet, String dimensionName) {
        return DimensionValueSetUtil.mergeDimensionValue(dimensionValueSet, dimensionName);
    }

    public static Collection<DimensionValueSet> groupByDwSize(Collection<DimensionValueSet> dimensionValueSets, String dimensionName, int batchSize) {
        ArrayList<DimensionValueSet> result = new ArrayList<DimensionValueSet>();
        for (DimensionValueSet dimensionValueSet : dimensionValueSets) {
            Object value = dimensionValueSet.getValue(dimensionName);
            if (value instanceof List) {
                List collection = (List)value;
                if (collection.size() > batchSize) {
                    List dwsList = DataPermissionUtil.groupBySize(collection, batchSize);
                    for (List dws : dwsList) {
                        DimensionValueSet copy = new DimensionValueSet(dimensionValueSet);
                        copy.clearValue(dimensionName);
                        copy.setValue(dimensionName, dws);
                        result.add(copy);
                    }
                    continue;
                }
                result.add(dimensionValueSet);
                continue;
            }
            result.add(dimensionValueSet);
        }
        return result;
    }

    public static <T> List<List<T>> groupBySize(Collection<T> list, int splitSize) {
        if (list == null || list.isEmpty() || splitSize <= 0) {
            return Collections.emptyList();
        }
        int totalBatches = (list.size() + splitSize - 1) / splitSize;
        ArrayList<List<T>> result = new ArrayList<List<T>>(totalBatches);
        int currentIndex = 0;
        ArrayList<T> currentBatch = null;
        for (T item : list) {
            if (currentIndex % splitSize == 0) {
                currentBatch = new ArrayList<T>(splitSize);
                result.add(currentBatch);
            }
            currentBatch.add(item);
            ++currentIndex;
        }
        return result;
    }

    public static DataPermission createTestDataPermission() {
        DataPermission dataPermission = new DataPermission();
        ArrayList<String> formKyes = new ArrayList<String>();
        for (int i = 0; i < 100; ++i) {
            formKyes.add(UUID.randomUUID().toString());
        }
        dataPermission.setResourceIds(formKyes);
        ArrayList<DataPermissionResource> accessResources = new ArrayList<DataPermissionResource>();
        dataPermission.setAccessResources(accessResources);
        for (int i = 0; i < 80000; ++i) {
            DimensionCombinationBuilder builder = new DimensionCombinationBuilder();
            builder.setValue("MD_ORG", (Object)("MD1000000000" + i));
            builder.setValue("MD_DW", (Object)"2025Y0007");
            DimensionCombination combination = builder.getCombination();
            for (int i1 = 0; i1 < 100; ++i1) {
                DataPermissionResource resource = new DataPermissionResource();
                resource.setDimensionCombination(combination);
                resource.setResourceId((String)formKyes.get(i1));
                accessResources.add(resource);
            }
        }
        dataPermission.setUnAccessResources(new ArrayList());
        return dataPermission;
    }
}

