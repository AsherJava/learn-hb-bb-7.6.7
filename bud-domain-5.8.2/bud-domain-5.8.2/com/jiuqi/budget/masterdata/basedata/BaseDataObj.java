/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.budget.common.exception.BudgetException
 *  com.jiuqi.budget.common.utils.DateTimeCenter
 *  com.jiuqi.budget.thread.service.ThreadSafeService
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.budget.masterdata.basedata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.budget.common.exception.BudgetException;
import com.jiuqi.budget.common.utils.DateTimeCenter;
import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.masterdata.intf.FBaseDataObj;
import com.jiuqi.budget.masterdata.intf.StopRange;
import com.jiuqi.budget.thread.service.ThreadSafeService;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BaseDataObj
implements FBaseDataObj {
    private String key;
    private String id;
    private String code;
    private String objectCode;
    private String name;
    private String parent;
    private String tableName;
    private String shortName;
    private String unitCode;
    private BigDecimal orderNum;
    private LocalDateTime validTime;
    private Date validDate;
    private LocalDateTime inValidTime;
    private Date inValidDate;
    private String creatorId;
    private LocalDateTime createTime;
    private Date createDate;
    private StopRange stopRange = StopRange.ALL_SYS;
    private String remark;
    private String parents;
    private BigDecimal ver;
    private LocalDate versionDate;
    private Boolean cacheSyncDisable = false;
    private Map<String, Object> fieldValMap;
    @JsonIgnore
    private NumberFormat numberFormat;
    @JsonIgnore
    private SimpleDateFormat dateTimeFormat;
    @JsonIgnore
    private SimpleDateFormat dateFormat;
    private DataPeriod versionPeriod;
    private String mainTaskId;
    private Boolean forceUpdateHistoryVersionData;

    public void setNumberFormat(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }

    public void setDateTimeFormat(SimpleDateFormat simpleDateFormat) {
        this.dateTimeFormat = simpleDateFormat;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public SimpleDateFormat getDateTimeFormat() {
        if (this.dateTimeFormat == null) {
            ThreadSafeService threadSafeService = (ThreadSafeService)ApplicationContextRegister.getBean(ThreadSafeService.class);
            this.dateTimeFormat = threadSafeService.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        return this.dateTimeFormat;
    }

    public SimpleDateFormat getDateFormat() {
        if (this.dateFormat == null) {
            ThreadSafeService threadSafeService = (ThreadSafeService)ApplicationContextRegister.getBean(ThreadSafeService.class);
            this.dateFormat = threadSafeService.getSimpleDateFormat("yyyy-MM-dd");
        }
        return this.dateFormat;
    }

    public NumberFormat getNumberFormat() {
        if (this.numberFormat == null) {
            this.numberFormat = ((ThreadSafeService)ApplicationContextRegister.getBean(ThreadSafeService.class)).getNumberFormat();
        }
        return this.numberFormat;
    }

    @Override
    public String getObjectCode() {
        return this.objectCode;
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getParent() {
        return this.parent;
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    @Override
    public String getParents() {
        return this.parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    @Override
    public BigDecimal getOrderNum() {
        return this.orderNum;
    }

    @Override
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setOrderNum(BigDecimal orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public LocalDateTime getValidTime() {
        if (this.validDate != null) {
            this.validTime = DateTimeCenter.convertDateToLDTByCache((Date)this.validDate);
            this.validDate = null;
        }
        return this.validTime;
    }

    public void setValidTime(LocalDateTime validTime) {
        this.validTime = validTime;
    }

    @Override
    public LocalDateTime getInValidTime() {
        if (this.inValidDate != null) {
            this.inValidTime = DateTimeCenter.convertDateToLDTByCache((Date)this.inValidDate);
            this.inValidDate = null;
        }
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
        if (this.createDate != null) {
            this.createTime = DateTimeCenter.convertDateToLDT((Date)this.createDate);
            this.createDate = null;
        }
        return this.createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public Boolean isStop() {
        throw new BudgetException("not support");
    }

    public StopRange getStopRange() {
        return this.stopRange;
    }

    public void setStopRange(StopRange stopRange) {
        if (stopRange == null) {
            throw new BudgetException("stopRange \u4e0d\u53ef\u4ee5\u4e3a\u7a7a");
        }
        this.stopRange = stopRange;
    }

    @Override
    public BigDecimal getVer() {
        return this.ver;
    }

    public void setVer(BigDecimal ver) {
        this.ver = ver;
    }

    public Map<String, Object> getFieldValMap() {
        return this.fieldValMap;
    }

    @Override
    public Object getFieldVal(String fieldName) {
        if (this.fieldValMap == null) {
            return null;
        }
        return this.fieldValMap.get(fieldName);
    }

    @Override
    public String getShowTitle(String fieldName) {
        return null;
    }

    @Override
    public String getValAsString(String fieldName) {
        throw new BudgetException("not support\uff01");
    }

    @Override
    public UUID getValAsUUID(String fieldName) {
        throw new BudgetException("not support\uff01");
    }

    @Override
    public Boolean getValAsBoolean(String fieldName) {
        throw new BudgetException("not support\uff01");
    }

    @Override
    public Date getValAsDate(String fieldName) {
        throw new BudgetException("not support\uff01");
    }

    @Override
    public Integer getValAsInt(String fieldName) {
        throw new BudgetException("not support\uff01");
    }

    @Override
    public Double getValAsDouble(String fieldName) {
        throw new BudgetException("not support\uff01");
    }

    @Override
    public Map<String, DataModelColumn> getFieldDefineMap() {
        return null;
    }

    @Override
    public DataModelColumn getFieldDefine(String fieldName) {
        return null;
    }

    public void setFieldVal(String fieldName, Object fieldVal) {
        if (this.fieldValMap == null) {
            this.fieldValMap = new HashMap<String, Object>();
        }
        this.fieldValMap.put(fieldName, fieldVal);
    }

    public void setFieldValMap(Map<String, Object> fieldValMap) {
        this.fieldValMap = fieldValMap;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public LocalDate getVersionDate() {
        return this.versionDate;
    }

    public void setVersionDate(LocalDate versionDate) {
        this.versionDate = versionDate;
    }

    public Boolean getCacheSyncDisable() {
        return this.cacheSyncDisable;
    }

    public void setCacheSyncDisable(Boolean cacheSyncDisable) {
        this.cacheSyncDisable = cacheSyncDisable;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public void setInValidDate(Date inValidDate) {
        this.inValidDate = inValidDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public DataPeriod getVersionPeriod() {
        return this.versionPeriod;
    }

    public void setVersionPeriod(DataPeriod versionPeriod) {
        this.versionPeriod = versionPeriod;
    }

    public Boolean getForceUpdateHistoryVersionData() {
        return this.forceUpdateHistoryVersionData;
    }

    public void setForceUpdateHistoryVersionData(Boolean forceUpdateHistoryVersionData) {
        this.forceUpdateHistoryVersionData = forceUpdateHistoryVersionData;
    }

    public String getMainTaskId() {
        return this.mainTaskId;
    }

    public void setMainTaskId(String mainTaskId) {
        this.mainTaskId = mainTaskId;
    }

    @Override
    public int compareTo(FBaseDataObj o) {
        BigDecimal o1 = this.getOrderNum();
        BigDecimal o2 = o.getOrderNum();
        if (o1 == null) {
            o1 = new BigDecimal(0);
        }
        if (o2 == null) {
            o2 = new BigDecimal(0);
        }
        return o1.compareTo(o2);
    }
}

