/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.basedata.impl.bean;

import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class GcBaseDataDO
implements GcBaseData {
    private String key;
    private String id;
    private String code;
    private String title;
    private String parentid;
    private BigDecimal ordinal;
    private String parents;
    private String tableName;
    private String objectCode;
    private String shortName;
    private String unitCode;
    private Boolean stop = false;
    private Boolean recovery = false;
    private LocalDateTime validTime;
    private LocalDateTime inValidTime;
    private String creatorId;
    private LocalDateTime createTime;
    private BigDecimal ver;
    private LocalDate versionDate;
    private Boolean leaf;
    private final Map<String, Object> fieldValMap = new HashMap<String, Object>();

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
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
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getParentid() {
        return this.parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    @Override
    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }

    @Override
    public String getParents() {
        return this.parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    @Override
    public String getObjectCode() {
        return this.objectCode;
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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
    public Boolean isStop() {
        return this.stop;
    }

    public void setStop(Boolean stop) {
        this.stop = stop;
    }

    @Override
    public Boolean isRecovery() {
        return this.recovery;
    }

    public void setRecovery(Boolean recovery) {
        this.recovery = recovery;
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
    public BigDecimal getVer() {
        return this.ver;
    }

    public void setVer(BigDecimal ver) {
        this.ver = ver;
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

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDate getVersionDate() {
        return this.versionDate;
    }

    public void setVersionDate(LocalDate versionDate) {
        this.versionDate = versionDate;
    }

    public Map<String, Object> getFieldValMap() {
        return this.fieldValMap;
    }

    public void setFieldVal(String fieldName, Object fieldVal) {
        this.fieldValMap.put(fieldName, fieldVal);
    }

    public void setFieldValMap(Map<String, Object> fieldValMap) {
        this.fieldValMap.putAll(fieldValMap);
    }

    @Override
    public Object getFieldVal(String fieldName) {
        return this.fieldValMap.get(fieldName);
    }

    @Override
    public int compareTo(GcBaseData o) {
        return this.getOrdinal().compareTo(o.getOrdinal());
    }

    @Override
    public Boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }
}

