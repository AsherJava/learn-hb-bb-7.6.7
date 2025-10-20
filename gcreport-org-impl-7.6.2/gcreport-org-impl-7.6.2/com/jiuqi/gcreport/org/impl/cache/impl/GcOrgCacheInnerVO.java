/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.org.impl.cache.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.List;

public class GcOrgCacheInnerVO
extends GcOrgCacheVO {
    private static final long serialVersionUID = 8274694498899788077L;

    public void setId(String id) {
        super.setId(id);
    }

    public void setRealKey(String realKey) {
        super.setRealKey(realKey);
    }

    public void setParentId(String parentId) {
        super.setParentId(parentId);
    }

    public void setParents(String[] parents) {
        super.setParents(parents);
    }

    public void setParentStr(String parentStr) {
        super.setParentStr(parentStr);
    }

    public void setGcParents(String[] gcparents) {
        super.setGcParents(gcparents);
    }

    public void setGcParentStr(String parentStr) {
        super.setGcParentStr(parentStr);
    }

    public void setCode(String code) {
        super.setCode(code);
    }

    public void setTitle(String title) {
        super.setTitle(title);
    }

    public void setLeaf(boolean leaf) {
        super.setLeaf(leaf);
    }

    public void setOrgTypeId(String orgTypeId) {
        super.setOrgTypeId(orgTypeId);
    }

    public void setDiffUnitId(String diffUnitId) {
        super.setDiffUnitId(diffUnitId);
    }

    public void setBaseUnitId(String baseUnitId) {
        super.setBaseUnitId(baseUnitId);
    }

    public void setMergeUnitId(String mergeUnitId) {
        super.setMergeUnitId(mergeUnitId);
    }

    public void setOrgKind(GcOrgKindEnum orgKind) {
        super.setOrgKind(orgKind);
    }

    public void setScale(double scale) {
        super.setScale(scale);
    }

    public void setSplitId(String splitId) {
        super.setSplitId(splitId);
    }

    protected void setSplitDiffFlag(boolean splitDiffFlag) {
        super.setSplitDiffFlag(splitDiffFlag);
    }

    public void setOrdinal(double ordinal) {
        super.setOrdinal(ordinal);
    }

    public void setStopFlag(boolean startFlag) {
        super.setStopFlag(startFlag);
    }

    public void setRecoveryFlag(boolean recoveryFlag) {
        super.setRecoveryFlag(recoveryFlag);
    }

    public void setBblx(String bblx) {
        super.setBblx(bblx);
    }

    public void setCurrencyId(String currencyId) {
        super.setCurrencyId(currencyId);
    }

    public void setCurrencyIds(String currencyIds) {
        super.setCurrencyIds(currencyIds);
    }

    public void setChildren(List<GcOrgCacheVO> children) {
        super.setChildren(children);
    }

    public void addChildren(GcOrgCacheVO vo) {
        if (this.children == null) {
            this.children = CollectionUtils.newArrayList();
        }
        this.children.add(vo);
    }

    public void addFieldValue(String field, Object value) {
        super.addFieldValue(field, value);
    }

    public GcOrgCacheVO assignTo(GcOrgCacheInnerVO vo) {
        vo.setOrgKind(this.getOrgKind());
        vo.setMergeUnitId(this.getMergeUnitId());
        vo.setCode(this.getCode());
        vo.setId(this.getId());
        vo.setTitle(this.getTitle());
        vo.setOrgTypeId(this.getOrgTypeId());
        vo.setParentId(this.getParentId());
        vo.setRealKey(this.getRealKey());
        vo.setParents(this.getParents());
        vo.setGcParents(this.getGcParents());
        vo.setBaseUnitId(this.getBaseUnitId());
        vo.setDiffUnitId(this.getDiffUnitId());
        vo.setScale(this.getScale());
        vo.setSplitId(this.getSplitId());
        vo.setSplitDiffFlag(this.isSplitDiffFlag());
        vo.setParentStr(this.getParentStr());
        vo.setGcParentStr(this.getGcParentStr());
        vo.setStopFlag(this.isStopFlag());
        vo.setRecoveryFlag(this.isRecoveryFlag());
        vo.setBblx(this.getBblx());
        vo.setCurrencyId(this.getCurrencyId());
        vo.setCurrencyIds(this.getCurrencyIds());
        vo.setIconKey(this.getIconKey());
        vo.setOrdinal(this.getOrdinal());
        vo.setChildren(CollectionUtils.newArrayList());
        vo.setFields(this.getFields());
        return vo;
    }
}

