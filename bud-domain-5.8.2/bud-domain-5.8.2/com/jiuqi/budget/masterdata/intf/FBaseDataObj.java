/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.budget.common.domain.DefaultTenantDTO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 */
package com.jiuqi.budget.masterdata.intf;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.budget.common.domain.DefaultTenantDTO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public interface FBaseDataObj
extends Comparable<FBaseDataObj>,
DefaultTenantDTO {
    public String getKey();

    public String getId();

    public String getCode();

    default public String getShowCode() {
        return this.getCode();
    }

    public String getObjectCode();

    public String getName();

    public String getParent();

    public BigDecimal getOrderNum();

    public String getRemark();

    public String getTableName();

    public String getShortName();

    public String getUnitCode();

    public LocalDateTime getValidTime();

    public LocalDateTime getInValidTime();

    public BigDecimal getVer();

    public String getCreatorId();

    public LocalDateTime getCreateTime();

    public Boolean isStop();

    public String getParents();

    public Object getFieldVal(String var1);

    public String getShowTitle(String var1);

    public String getValAsString(String var1);

    public UUID getValAsUUID(String var1);

    public Boolean getValAsBoolean(String var1);

    public Date getValAsDate(String var1);

    public Integer getValAsInt(String var1);

    public Double getValAsDouble(String var1);

    @JsonIgnore
    public Map<String, DataModelColumn> getFieldDefineMap();

    public DataModelColumn getFieldDefine(String var1);
}

