/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.bde.common.vo.FetchSettingVO
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dto;

import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.bde.common.vo.FetchSettingVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FetchSettingRepairDTO {
    private List<FloatRegionConfigVO> deleteFloatSettingList = new ArrayList<FloatRegionConfigVO>(32);
    private List<FloatRegionConfigVO> updateFloatSettingList = new ArrayList<FloatRegionConfigVO>(32);
    private List<FetchSettingVO> deleteFetchSettingList = new ArrayList<FetchSettingVO>(32);
    private List<FetchSettingVO> updateFetchSettingList = new ArrayList<FetchSettingVO>(32);
    private Set<String> repairFetchSchemeIdSet = new HashSet<String>(16);

    public void addFetchSettingRepair(FetchSettingRepairDTO fetchSettingRepair) {
        Assert.isNotNull((Object)fetchSettingRepair);
        this.deleteFloatSettingList.addAll((Collection<FloatRegionConfigVO>)(fetchSettingRepair.getDeleteFloatSettingList() == null ? CollectionUtils.newArrayList() : fetchSettingRepair.getDeleteFloatSettingList()));
        this.updateFloatSettingList.addAll((Collection<FloatRegionConfigVO>)(fetchSettingRepair.getUpdateFloatSettingList() == null ? CollectionUtils.newArrayList() : fetchSettingRepair.getUpdateFloatSettingList()));
        this.deleteFetchSettingList.addAll((Collection<FetchSettingVO>)(fetchSettingRepair.getDeleteFetchSettingList() == null ? CollectionUtils.newArrayList() : fetchSettingRepair.getDeleteFetchSettingList()));
        this.updateFetchSettingList.addAll((Collection<FetchSettingVO>)(fetchSettingRepair.getUpdateFetchSettingList() == null ? CollectionUtils.newArrayList() : fetchSettingRepair.getUpdateFetchSettingList()));
        this.repairFetchSchemeIdSet.addAll((Collection<String>)(fetchSettingRepair.getRepairFetchSchemeIdSet() == null ? CollectionUtils.newHashSet() : fetchSettingRepair.getRepairFetchSchemeIdSet()));
    }

    public List<FloatRegionConfigVO> getDeleteFloatSettingList() {
        return this.deleteFloatSettingList;
    }

    public List<String> getDeleteFloatSettingIdList() {
        return this.deleteFloatSettingList.stream().map(FloatRegionConfigVO::getId).collect(Collectors.toList());
    }

    public void addDeleteFloatSetting(FloatRegionConfigVO deleteFloatSetting) {
        this.deleteFloatSettingList.add(deleteFloatSetting);
        this.repairFetchSchemeIdSet.add(deleteFloatSetting.getFetchSchemeId());
    }

    public List<FloatRegionConfigVO> getUpdateFloatSettingList() {
        return this.updateFloatSettingList;
    }

    public void addUpdateFloatSetting(FloatRegionConfigVO updateFloatSetting) {
        this.updateFloatSettingList.add(updateFloatSetting);
        this.repairFetchSchemeIdSet.add(updateFloatSetting.getFetchSchemeId());
    }

    public List<FetchSettingVO> getDeleteFetchSettingList() {
        return this.deleteFetchSettingList;
    }

    public List<String> getDeleteFetchSettingIdList() {
        return this.deleteFetchSettingList.stream().map(FetchSettingVO::getId).collect(Collectors.toList());
    }

    public void addDeleteFetchSetting(FetchSettingVO deleteFetchSetting) {
        this.deleteFetchSettingList.add(deleteFetchSetting);
        this.repairFetchSchemeIdSet.add(deleteFetchSetting.getFetchSchemeId());
    }

    public List<FetchSettingVO> getUpdateFetchSettingList() {
        return this.updateFetchSettingList;
    }

    public void AddUpdateFetchSetting(FetchSettingVO updateFetchSetting) {
        this.updateFetchSettingList.add(updateFetchSetting);
        this.repairFetchSchemeIdSet.add(updateFetchSetting.getFetchSchemeId());
    }

    public Set<String> getRepairFetchSchemeIdSet() {
        return this.repairFetchSchemeIdSet;
    }

    public String toString() {
        return "FetchSettingRepairDTO{deleteFloatSettingList=" + this.deleteFloatSettingList + ", updateFloatSettingList=" + this.updateFloatSettingList + ", deleteFetchSettingList=" + this.deleteFetchSettingList + ", updateFetchSettingList=" + this.updateFetchSettingList + ", repairFetchSchemeIdSet=" + this.repairFetchSchemeIdSet + '}';
    }
}

