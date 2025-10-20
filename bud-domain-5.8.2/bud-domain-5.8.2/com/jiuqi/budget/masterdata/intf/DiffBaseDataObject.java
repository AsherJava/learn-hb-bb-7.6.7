/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 */
package com.jiuqi.budget.masterdata.intf;

import com.jiuqi.budget.masterdata.intf.FBaseDataObj;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class DiffBaseDataObject
implements FBaseDataObj {
    public static final FBaseDataObj instance = new DiffBaseDataObject();

    private DiffBaseDataObject() {
    }

    @Override
    public String getKey() {
        return "*0000000";
    }

    @Override
    public String getObjectCode() {
        return this.getCode();
    }

    @Override
    public String getId() {
        return "00000000-0000-0000-0000-000000000000";
    }

    @Override
    public String getCode() {
        return "*0000000";
    }

    @Override
    public String getName() {
        return "\u5dee\u989d";
    }

    @Override
    public String getParent() {
        return null;
    }

    @Override
    public BigDecimal getOrderNum() {
        return null;
    }

    @Override
    public String getRemark() {
        return null;
    }

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public String getShortName() {
        return null;
    }

    @Override
    public String getUnitCode() {
        return null;
    }

    @Override
    public LocalDateTime getValidTime() {
        return null;
    }

    @Override
    public LocalDateTime getInValidTime() {
        return null;
    }

    @Override
    public BigDecimal getVer() {
        return null;
    }

    @Override
    public String getCreatorId() {
        return null;
    }

    @Override
    public LocalDateTime getCreateTime() {
        return null;
    }

    @Override
    public Boolean isStop() {
        return false;
    }

    @Override
    public String getParents() {
        return null;
    }

    @Override
    public Object getFieldVal(String fieldName) {
        return null;
    }

    @Override
    public String getShowTitle(String fieldName) {
        return null;
    }

    @Override
    public String getValAsString(String fieldName) {
        return null;
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

    @Override
    public Map<String, DataModelColumn> getFieldDefineMap() {
        return null;
    }

    @Override
    public DataModelColumn getFieldDefine(String fieldName) {
        return null;
    }

    @Override
    public int compareTo(FBaseDataObj o) {
        return -1;
    }
}

