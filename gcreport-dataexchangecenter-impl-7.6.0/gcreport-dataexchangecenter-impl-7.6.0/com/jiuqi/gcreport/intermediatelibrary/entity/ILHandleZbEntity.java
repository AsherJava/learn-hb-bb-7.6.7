/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.gcreport.intermediatelibrary.entity;

import com.jiuqi.gcreport.intermediatelibrary.entity.ILEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILExtractCondition;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILSyncCondition;
import com.jiuqi.gcreport.intermediatelibrary.entity.ZbDataEntity;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.List;
import java.util.Map;

public class ILHandleZbEntity {
    private Map<String, List<ILSyncCondition>> regionSimpleToFieldsMap;
    private Boolean pushType;
    private ILExtractCondition iCondition;
    private ILEntity iLEntity;
    private String orgId;
    private DimensionValueSet dimensionValueSet;
    private Map<String, List<ZbDataEntity>> zbType2ZbList;
    private Map<String, Integer> logger2ValueMap;
    private GcOrgCacheVO gcOrgCacheVO;

    public GcOrgCacheVO getGcOrgCacheVO() {
        return this.gcOrgCacheVO;
    }

    public void setGcOrgCacheVO(GcOrgCacheVO gcOrgCacheVO) {
        this.gcOrgCacheVO = gcOrgCacheVO;
    }

    public Map<String, List<ILSyncCondition>> getRegionSimpleToFieldsMap() {
        return this.regionSimpleToFieldsMap;
    }

    public void setRegionSimpleToFieldsMap(Map<String, List<ILSyncCondition>> regionSimpleToFieldsMap) {
        this.regionSimpleToFieldsMap = regionSimpleToFieldsMap;
    }

    public Boolean getPushType() {
        return this.pushType;
    }

    public void setPushType(Boolean pushType) {
        this.pushType = pushType;
    }

    public ILExtractCondition getiCondition() {
        return this.iCondition;
    }

    public void setiCondition(ILExtractCondition iCondition) {
        this.iCondition = iCondition;
    }

    public ILEntity getiLEntity() {
        return this.iLEntity;
    }

    public void setiLEntity(ILEntity iLEntity) {
        this.iLEntity = iLEntity;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public Map<String, List<ZbDataEntity>> getZbType2ZbList() {
        return this.zbType2ZbList;
    }

    public void setZbType2ZbList(Map<String, List<ZbDataEntity>> zbType2ZbList) {
        this.zbType2ZbList = zbType2ZbList;
    }

    public Map<String, Integer> getLogger2ValueMap() {
        return this.logger2ValueMap;
    }

    public void setLogger2ValueMap(Map<String, Integer> logger2ValueMap) {
        this.logger2ValueMap = logger2ValueMap;
    }
}

