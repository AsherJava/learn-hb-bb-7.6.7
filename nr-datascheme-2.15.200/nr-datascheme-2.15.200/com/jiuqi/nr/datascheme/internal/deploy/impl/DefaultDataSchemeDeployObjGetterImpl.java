/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.core.Grouped
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 */
package com.jiuqi.nr.datascheme.internal.deploy.impl;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.core.Grouped;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.internal.deploy.DataSchemeDeployHelper;
import com.jiuqi.nr.datascheme.internal.deploy.IDataSchemeDeployObjGetter;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataSchemeDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployType;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DefaultDataSchemeDeployObjGetterImpl
implements IDataSchemeDeployObjGetter {
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    @Qualifier(value="RuntimeDataSchemeNoCacheServiceImpl-NO_CACHE")
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    @Override
    public DataSchemeDeployObj doGet(String dataSchemeKey) {
        DesignDataScheme designDataScheme = this.designDataSchemeService.getDataScheme(dataSchemeKey);
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        if (null == designDataScheme && null == dataScheme) {
            return null;
        }
        DataSchemeDeployObj catalogDeployInfo = new DataSchemeDeployObj();
        List dataGroups = this.designDataSchemeService.getAllDataGroup(dataSchemeKey);
        if (null != designDataScheme && null == dataScheme) {
            catalogDeployInfo.setDataScheme((DataScheme)designDataScheme, DeployType.ADD);
            catalogDeployInfo.setDimState(DeployType.ADD);
            catalogDeployInfo.addAddDataGroup(dataGroups);
            return catalogDeployInfo;
        }
        List rtDataGroups = this.runtimeDataSchemeService.getAllDataGroup(dataSchemeKey);
        if (null == designDataScheme && null != dataScheme) {
            catalogDeployInfo.setDataScheme(dataScheme, DeployType.DELETE);
            catalogDeployInfo.setDimState(DeployType.DELETE);
            List<String> deleteGroupKeys = rtDataGroups.stream().map(Grouped::getKey).collect(Collectors.toList());
            catalogDeployInfo.addDeleteDataGroupKey(deleteGroupKeys);
            return catalogDeployInfo;
        }
        if (!DataSchemeDeployHelper.compareDataScheme((DataScheme)designDataScheme, dataScheme)) {
            catalogDeployInfo.setDataScheme(dataScheme, DeployType.UPDATE);
        } else {
            catalogDeployInfo.setDataScheme(dataScheme, DeployType.NONE);
        }
        this.compareDim(catalogDeployInfo, dataSchemeKey);
        this.compareDataGroup(catalogDeployInfo, dataGroups, rtDataGroups);
        return catalogDeployInfo;
    }

    private void compareDataGroup(DataSchemeDeployObj catalogDeployInfo, List<DesignDataGroup> dataGroups, List<DataGroup> rtDataGroups) {
        Map<String, DesignDataGroup> dataGroupMap = dataGroups.stream().collect(Collectors.toMap(Grouped::getKey, v -> v));
        Map<String, DataGroup> rtDataGroupMap = rtDataGroups.stream().collect(Collectors.toMap(Grouped::getKey, v -> v));
        Set<String> dataGroupKeySet = dataGroupMap.keySet();
        Set<String> rtDataGroupKeySet = rtDataGroupMap.keySet();
        Set<String> addDataGroupKeySet = DataSchemeDeployHelper.diff(dataGroupKeySet, rtDataGroupKeySet);
        Set<String> deleteDataGroupKeySet = DataSchemeDeployHelper.diff(rtDataGroupKeySet, dataGroupKeySet);
        Set<String> union = DataSchemeDeployHelper.union(rtDataGroupKeySet, dataGroupKeySet);
        catalogDeployInfo.addDeleteDataGroupKey(deleteDataGroupKeySet);
        if (null != addDataGroupKeySet && !addDataGroupKeySet.isEmpty()) {
            for (String key : addDataGroupKeySet) {
                catalogDeployInfo.addAddDataGroup((DataGroup)dataGroupMap.get(key));
            }
        }
        if (null != union && !union.isEmpty()) {
            for (String key : union) {
                if (DataSchemeDeployHelper.compareDataGroup((DataGroup)dataGroupMap.get(key), rtDataGroupMap.get(key))) continue;
                catalogDeployInfo.addUpdateDataGroup((DataGroup)dataGroupMap.get(key));
            }
        }
    }

    private void compareDim(DataSchemeDeployObj catalogDeployInfo, String dataSchemeKey) {
        List dimensions = this.designDataSchemeService.getDataSchemeDimension(dataSchemeKey);
        List rtDimensions = this.runtimeDataSchemeService.getDataSchemeDimension(dataSchemeKey);
        if (CollectionUtils.isEmpty(dimensions) ^ CollectionUtils.isEmpty(rtDimensions)) {
            catalogDeployInfo.setDimState(DeployType.UPDATE_NODPLOY);
            return;
        }
        int dim = DimensionType.DIMENSION.getValue() | DimensionType.CALIBRE.getValue();
        Map<String, DesignDataDimension> dimMap = dimensions.stream().filter(d -> (d.getDimensionType().getValue() & dim) > 0).collect(Collectors.toMap(DataDimension::getDimKey, v -> v));
        Map<String, DataDimension> rtDimMap = rtDimensions.stream().filter(d -> (d.getDimensionType().getValue() & dim) > 0).collect(Collectors.toMap(DataDimension::getDimKey, v -> v));
        HashSet<String> addDimKeys = new HashSet<String>(dimMap.keySet());
        addDimKeys.removeAll(rtDimMap.keySet());
        if (!addDimKeys.isEmpty()) {
            addDimKeys.remove("ADJUST");
            if (addDimKeys.isEmpty()) {
                catalogDeployInfo.setDimState(DeployType.UPDATE_NODPLOY);
            } else {
                catalogDeployInfo.setDimState(DeployType.UPDATE);
            }
            return;
        }
        HashSet<String> removeDimKeys = new HashSet<String>(rtDimMap.keySet());
        removeDimKeys.removeAll(dimMap.keySet());
        if (!removeDimKeys.isEmpty()) {
            catalogDeployInfo.setDimState(DeployType.UPDATE);
            return;
        }
        if (!this.compareDim(dimensions, rtDimensions)) {
            catalogDeployInfo.setDimState(DeployType.UPDATE_NODPLOY);
        }
    }

    private boolean compareDim(List<DesignDataDimension> dimensions, List<DataDimension> rtDimensions) {
        if (dimensions.size() != rtDimensions.size()) {
            return false;
        }
        Map<String, DesignDataDimension> dimMap = dimensions.stream().collect(Collectors.toMap(DataDimension::getDimKey, v -> v));
        Map<String, DataDimension> rtDimMap = rtDimensions.stream().collect(Collectors.toMap(DataDimension::getDimKey, v -> v));
        if (!DataSchemeDeployHelper.diff(dimMap.keySet(), rtDimMap.keySet()).isEmpty() || !DataSchemeDeployHelper.diff(rtDimMap.keySet(), dimMap.keySet()).isEmpty()) {
            return false;
        }
        for (DataDimension dataDimension : dimensions) {
            if (DataSchemeDeployHelper.compareDataDimension(dataDimension, rtDimMap.get(dataDimension.getDimKey()))) continue;
            return false;
        }
        return true;
    }
}

