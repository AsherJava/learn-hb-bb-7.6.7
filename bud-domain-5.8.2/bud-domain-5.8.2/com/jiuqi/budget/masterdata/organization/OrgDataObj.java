/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.budget.common.exception.BudgetException
 *  com.jiuqi.budget.common.utils.DateTimeCenter
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 */
package com.jiuqi.budget.masterdata.organization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.budget.common.exception.BudgetException;
import com.jiuqi.budget.common.utils.DateTimeCenter;
import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.masterdata.intf.FBaseDataObj;
import com.jiuqi.budget.masterdata.intf.StopRange;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
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
    private Date validDate;
    private LocalDateTime inValidTime;
    private Date inValidDate;
    private String creatorId;
    private StopRange stopRange = StopRange.ALL_SYS;
    private boolean recoveryFlag;
    private String parentCode;
    private BigDecimal ver;
    private String parents;
    private LocalDateTime createTime;
    private Date createDate;
    private String remark;
    private Map<String, Object> fieldValMap = new HashMap<String, Object>();
    private String categoryName;
    private DataPeriod versionPeriod;
    private String mainTaskId;
    private LocalDateTime versionTime;

    public LocalDateTime getVersionTime() {
        return this.versionTime;
    }

    public void setVersionTime(LocalDateTime versionTime) {
        this.versionTime = versionTime;
    }

    public DataPeriod getVersionPeriod() {
        return this.versionPeriod;
    }

    public void setVersionPeriod(DataPeriod versionPeriod) {
        this.versionPeriod = versionPeriod;
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
        if (this.validDate != null) {
            this.validTime = DateTimeCenter.convertDateToLDT((Date)this.validDate);
            this.validDate = null;
        }
        return this.validTime;
    }

    public void setValidTime(LocalDateTime validTime) {
        this.validTime = validTime;
    }

    @JsonIgnore
    public void setValidTime(Date validDate) {
        this.validDate = validDate;
    }

    @Override
    public LocalDateTime getInValidTime() {
        if (this.inValidDate != null) {
            this.inValidTime = DateTimeCenter.convertDateToLDT((Date)this.inValidDate);
            this.inValidDate = null;
        }
        return this.inValidTime;
    }

    public void setInValidTime(LocalDateTime inValidTime) {
        this.inValidTime = inValidTime;
    }

    @JsonIgnore
    public void setInValidTime(Date inValidDate) {
        this.inValidDate = inValidDate;
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
        if (this.createDate != null) {
            this.createTime = DateTimeCenter.convertDateToLDT((Date)this.createDate);
            this.createDate = null;
        }
        return this.createTime;
    }

    @Override
    public Boolean isStop() {
        throw new BudgetException("not support");
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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

    @JsonIgnore
    public void setCreateTime(Date date) {
        this.createDate = date;
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
    public String getShowTitle(String fieldName) {
        throw new BudgetException("not support!");
    }

    @Override
    public String getValAsString(String fieldName) {
        throw new BudgetException("not support !");
    }

    @Override
    public UUID getValAsUUID(String fieldName) {
        throw new BudgetException("not support !");
    }

    @Override
    public Boolean getValAsBoolean(String fieldName) {
        throw new BudgetException("not support !");
    }

    @Override
    public Date getValAsDate(String fieldName) {
        throw new BudgetException("not support !");
    }

    @Override
    public Integer getValAsInt(String fieldName) {
        throw new BudgetException("not support !");
    }

    @Override
    public Double getValAsDouble(String fieldName) {
        throw new BudgetException("not support !");
    }

    @Override
    public Map<String, DataModelColumn> getFieldDefineMap() {
        return null;
    }

    @Override
    public DataModelColumn getFieldDefine(String fieldName) {
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

    public String getMainTaskId() {
        return this.mainTaskId;
    }

    public void setMainTaskId(String mainTaskId) {
        this.mainTaskId = mainTaskId;
    }

    public StopRange getStopRange() {
        return this.stopRange;
    }

    public void setStopRange(StopRange stopRange) {
        this.stopRange = stopRange;
    }

    @Override
    public int compareTo(FBaseDataObj o) {
        return this.getOrderNum().compareTo(o.getOrderNum());
    }

    public String toString() {
        return "OrgDataObj{key='" + this.key + '\'' + ", id='" + this.id + '\'' + ", code='" + this.code + '\'' + ", name='" + this.name + '\'' + ", parent='" + this.parent + '\'' + ", orderNum=" + this.orderNum + ", shortName='" + this.shortName + '\'' + ", validTime=" + this.validTime + ", inValidTime=" + this.inValidTime + ", creatorId=" + this.creatorId + ", recoveryFlag=" + this.recoveryFlag + ", parentCode='" + this.parentCode + '\'' + ", ver=" + this.ver + ", parents='" + this.parents + '\'' + ", createTime=" + this.createTime + ", remark='" + this.remark + '\'' + ", fieldValMap=" + this.fieldValMap + ", categoryName='" + this.categoryName + '\'' + ", versionPeriod=" + this.versionPeriod + ", versionTime=" + this.versionTime + '}';
    }
}

