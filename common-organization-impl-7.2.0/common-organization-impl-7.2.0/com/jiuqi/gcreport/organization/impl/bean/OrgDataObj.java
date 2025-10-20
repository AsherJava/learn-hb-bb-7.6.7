/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.organization.impl.bean;

import com.jiuqi.gcreport.organization.impl.bean.FBaseDataObj;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OrgDataObj
implements FBaseDataObj {
    private String key;
    private String id;
    private String code;
    private String name;
    private String parent;
    private BigDecimal orderNum;
    private String shortName;
    private LocalDateTime validTime;
    private LocalDateTime inValidTime;
    private String creatorId;
    private Boolean stop = false;
    private boolean recoveryFlag;
    private String parentCode;
    private BigDecimal ver;
    private String parents;
    private LocalDateTime createTime;
    private String remark;
    private Map<String, Object> fieldValMap = new HashMap<String, Object>();
    private String categoryName;
    private LocalDateTime currentTime;
    private LocalDateTime versionTime;

    public LocalDateTime getVersionTime() {
        return this.versionTime;
    }

    public void setVersionTime(LocalDateTime versionTime) {
        this.versionTime = versionTime;
    }

    public LocalDateTime getCurrentTime() {
        return this.currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

    @Override
    public String getObjectCode() {
        return this.getCode();
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, Object> getFieldValMap() {
        return this.fieldValMap;
    }

    public void setFieldValMap(Map<String, Object> fieldValMap) {
        this.fieldValMap = fieldValMap;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String getShortName() {
        return this.shortName;
    }

    @Override
    public String getUnitCode() {
        return null;
    }

    @Override
    public BigDecimal getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(BigDecimal orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public LocalDateTime getValidTime() {
        return this.validTime;
    }

    public void setValidTime(LocalDateTime validTime) {
        this.validTime = validTime;
    }

    @Override
    public LocalDateTime getInValidTime() {
        return this.inValidTime;
    }

    public void setInValidTime(LocalDateTime inValidTime) {
        this.inValidTime = inValidTime;
    }

    @Override
    public String getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public LocalDateTime getCreateTime() {
        return this.createTime;
    }

    @Override
    public Boolean isStop() {
        return this.stop;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setStop(Boolean stop) {
        this.stop = stop;
    }

    @Override
    public String getParents() {
        return this.parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getRemark() {
        return this.remark;
    }

    @Override
    public String getTableName() {
        return this.categoryName;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setTableName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public Object getFieldVal(String fieldName) {
        return this.fieldValMap.get(fieldName);
    }

    @Override
    public String getValAsString(String fieldName) {
        Object objectVal = this.fieldValMap.get(fieldName);
        if (objectVal == null) {
            return null;
        }
        return objectVal.toString();
    }

    @Override
    public UUID getValAsUUID(String fieldName) {
        return null;
    }

    @Override
    public Boolean getValAsBoolean(String fieldName) {
        return null;
    }

    @Override
    public Date getValAsDate(String fieldName) {
        return null;
    }

    @Override
    public Integer getValAsInt(String fieldName) {
        return null;
    }

    @Override
    public Double getValAsDouble(String fieldName) {
        return null;
    }

    public boolean isRecoveryFlag() {
        return this.recoveryFlag;
    }

    public void setRecoveryFlag(boolean recoveryFlag) {
        this.recoveryFlag = recoveryFlag;
    }

    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    @Override
    public BigDecimal getVer() {
        return this.ver;
    }

    public void setVer(BigDecimal ver) {
        this.ver = ver;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public int compareTo(FBaseDataObj o) {
        return this.getOrderNum().compareTo(o.getOrderNum());
    }

    public String toString() {
        return "OrgDataObj{key='" + this.key + '\'' + ", id='" + this.id + '\'' + ", code='" + this.code + '\'' + ", name='" + this.name + '\'' + ", parent='" + this.parent + '\'' + ", orderNum=" + this.orderNum + ", shortName='" + this.shortName + '\'' + ", validTime=" + this.validTime + ", inValidTime=" + this.inValidTime + ", creatorId=" + this.creatorId + ", stop=" + this.stop + ", recoveryFlag=" + this.recoveryFlag + ", parentCode='" + this.parentCode + '\'' + ", ver=" + this.ver + ", parents='" + this.parents + '\'' + ", createTime=" + this.createTime + ", remark='" + this.remark + '\'' + ", fieldValMap=" + this.fieldValMap + ", categoryName='" + this.categoryName + '\'' + ", currentTime=" + this.currentTime + ", versionTime=" + this.versionTime + '}';
    }
}

