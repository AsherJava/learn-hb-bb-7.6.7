/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.gcreport.org.api.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.tree.INode;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.util.ObjectUtils;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GcOrgCacheVO
implements INode,
Serializable {
    private static final long serialVersionUID = -1050329055673592196L;
    private String code;
    private String title;
    private boolean leaf = true;
    @JsonProperty(value="parentcode")
    private String parentId;
    @JsonProperty(value="parents")
    private String parentStr;
    private double ordinal;
    @JsonProperty(value="stopflag")
    private boolean stopFlag;
    @JsonProperty(value="recoveryflag")
    private boolean recoveryFlag;
    @JsonProperty(value="kind")
    private GcOrgKindEnum orgKind = GcOrgKindEnum.SINGLE;
    private String key;
    private String bblx;
    private String currencyId;
    private String currencyIds;
    @JsonIgnore
    private String id;
    @JsonIgnore
    private String[] parents;
    @JsonIgnore
    private String[] gcParents;
    @JsonProperty(value="gcparents")
    private String gcParentStr;
    @JsonIgnore
    private String orgTypeId;
    @JsonIgnore
    private String diffUnitId;
    @JsonIgnore
    private String baseUnitId;
    @JsonIgnore
    private String mergeUnitId;
    @JsonIgnore
    private double scale;
    @JsonIgnore
    private String splitId;
    @JsonIgnore
    private boolean splitDiffFlag;
    @JsonIgnore
    private String iconKey;
    @JsonIgnore
    protected List<GcOrgCacheVO> children = CollectionUtils.newArrayList();
    @JsonIgnore
    protected Map<String, Object> fields;

    protected GcOrgCacheVO() {
    }

    public void setKey(String key) {
        this.key = key;
    }

    protected void setId(String id) {
        this.id = id;
    }

    protected void setRealKey(String realKey) {
        this.key = realKey;
    }

    protected void setParentId(String parentId) {
        this.parentId = parentId;
    }

    protected void setParents(String[] parents) {
        this.parents = parents;
    }

    protected void setGcParents(String[] gcparents) {
        this.gcParents = gcparents;
    }

    protected void setGcParentStr(String gcParentStr) {
        this.gcParentStr = gcParentStr;
    }

    public String[] getGcParents() {
        return this.gcParents;
    }

    public String getGcParentStr() {
        return this.gcParentStr;
    }

    protected void setParentStr(String parentStr) {
        this.parentStr = parentStr;
    }

    protected void setCode(String code) {
        this.code = code;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    protected void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    protected void setOrgTypeId(String orgTypeId) {
        this.orgTypeId = orgTypeId;
    }

    protected void setDiffUnitId(String diffUnitId) {
        this.diffUnitId = diffUnitId;
    }

    protected void setBaseUnitId(String baseUnitId) {
        this.baseUnitId = baseUnitId;
    }

    protected void setMergeUnitId(String mergeUnitId) {
        this.mergeUnitId = mergeUnitId;
    }

    protected void setOrgKind(GcOrgKindEnum orgKind) {
        this.orgKind = orgKind;
    }

    protected void setScale(double scale) {
        this.scale = scale;
    }

    protected void setSplitId(String splitId) {
        this.splitId = splitId;
    }

    protected void setSplitDiffFlag(boolean splitDiffFlag) {
        this.splitDiffFlag = splitDiffFlag;
    }

    protected void setOrdinal(double ordinal) {
        this.ordinal = ordinal;
    }

    protected void setStopFlag(boolean startFlag) {
        this.stopFlag = startFlag;
    }

    protected void setRecoveryFlag(boolean recoveryFlag) {
        this.recoveryFlag = recoveryFlag;
    }

    protected void setChildren(List<GcOrgCacheVO> children) {
        this.children = children;
    }

    protected void setBblx(String bblx) {
        this.bblx = bblx;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public String getIconKey() {
        return this.iconKey;
    }

    public void setIconKey(String iconKey) {
        this.iconKey = iconKey;
    }

    public String getId() {
        return this.id;
    }

    @JsonIgnore
    public String getRealKey() {
        return this.key;
    }

    public String getParentId() {
        return this.parentId;
    }

    public String[] getParents() {
        return this.parents;
    }

    public String getParentStr() {
        return this.parentStr;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public boolean isLeaf() {
        return this.leaf;
    }

    public String getOrgTypeId() {
        return this.orgTypeId;
    }

    public String getDiffUnitId() {
        return this.diffUnitId;
    }

    public String getBaseUnitId() {
        return this.baseUnitId;
    }

    public String getMergeUnitId() {
        return this.mergeUnitId;
    }

    public GcOrgKindEnum getOrgKind() {
        return this.orgKind;
    }

    public double getScale() {
        return this.scale;
    }

    public String getSplitId() {
        return this.splitId;
    }

    public boolean isSplitDiffFlag() {
        return this.splitDiffFlag;
    }

    public double getOrdinal() {
        return this.ordinal;
    }

    public boolean isStopFlag() {
        return this.stopFlag;
    }

    public boolean isRecoveryFlag() {
        return this.recoveryFlag;
    }

    public List<GcOrgCacheVO> getChildren() {
        return this.children;
    }

    public String getBblx() {
        return this.bblx;
    }

    public Map<String, Object> getFields() {
        return this.fields;
    }

    protected void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    @JsonIgnore
    public String getIdString() {
        return this.id;
    }

    public boolean equals(GcOrgCacheVO vo) {
        if (!ObjectUtils.nullSafeEquals(this.getId(), vo.getId())) {
            return false;
        }
        if (!ObjectUtils.nullSafeEquals(this.getCode(), vo.getCode())) {
            return false;
        }
        if (!ObjectUtils.nullSafeEquals(this.getTitle(), vo.getTitle())) {
            return false;
        }
        if (!ObjectUtils.nullSafeEquals(this.getOrgTypeId(), vo.getOrgTypeId())) {
            return false;
        }
        if (!ObjectUtils.nullSafeEquals(this.getParentId(), vo.getParentId())) {
            return false;
        }
        if (!ObjectUtils.nullSafeEquals(this.getParentStr(), vo.getParentStr())) {
            return false;
        }
        if (!ObjectUtils.nullSafeEquals(this.getRealKey(), vo.getRealKey())) {
            return false;
        }
        if (!ObjectUtils.nullSafeEquals(this.getBaseUnitId(), vo.getBaseUnitId())) {
            return false;
        }
        if (!ObjectUtils.nullSafeEquals(this.getDiffUnitId(), vo.getDiffUnitId())) {
            return false;
        }
        if (!ObjectUtils.nullSafeEquals(this.getScale(), vo.getScale())) {
            return false;
        }
        return ObjectUtils.nullSafeEquals(this.getSplitId(), vo.getSplitId());
    }

    public Object getBaseFieldValue(String field) {
        if (this.hasField(field)) {
            return this.getFieldValue(field);
        }
        GcOrgCacheVO datas = GcOrgPublicTool.getInstance().getBaseOrgByCode(this.getId());
        for (Map.Entry<String, Object> entry : datas.getFields().entrySet()) {
            if (this.hasField(entry.getKey())) continue;
            this.fields.put(entry.getKey(), entry.getValue());
        }
        return this.getFieldValue(field);
    }

    public Object getTypeFieldValue(String field) {
        if (this.hasField(field)) {
            return this.getFieldValue(field);
        }
        GcOrgCacheVO datas = GcOrgPublicTool.getInstance().getOrgByPrimaryID(this.getRealKey());
        if (datas != null && !datas.getFields().isEmpty()) {
            for (Map.Entry<String, Object> entry : datas.getFields().entrySet()) {
                this.addFieldValue(entry.getKey(), entry.getValue());
            }
        }
        return this.getFieldValue(field);
    }

    protected void addFieldValue(String field, Object value) {
        if (this.fields == null) {
            this.fields = CollectionUtils.newHashMap();
        }
        this.fields.put(field, value);
    }

    private Object getFieldValue(String field) {
        if (this.fields == null) {
            return null;
        }
        return this.fields.get(field);
    }

    private boolean hasField(String field) {
        if (this.fields == null) {
            this.fields = CollectionUtils.newHashMap();
            return false;
        }
        return this.fields.containsKey(field);
    }

    public String getCurrencyId() {
        return this.currencyId;
    }

    protected void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyIds() {
        return this.currencyIds;
    }

    protected void setCurrencyIds(String currencyIds) {
        this.currencyIds = currencyIds;
    }
}

