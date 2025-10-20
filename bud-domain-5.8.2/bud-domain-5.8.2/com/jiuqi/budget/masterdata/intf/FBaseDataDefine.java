/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.domain.DefaultTenantDTO
 *  com.jiuqi.va.basedata.common.DummyObjType
 */
package com.jiuqi.budget.masterdata.intf;

import com.jiuqi.budget.common.domain.DefaultTenantDTO;
import com.jiuqi.budget.masterdata.basedata.enums.BaseDataDefineShareType;
import com.jiuqi.budget.masterdata.basedata.enums.BaseDataDefineStructType;
import com.jiuqi.va.basedata.common.DummyObjType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface FBaseDataDefine
extends DefaultTenantDTO {
    public String getId();

    public String getCode();

    public String getName();

    public String getParentCode();

    public BigDecimal getOrderNum();

    public String getRemark();

    public String getCreator();

    public BaseDataDefineShareType getShareType();

    public BaseDataDefineStructType getStructType();

    public boolean isMultiVersion();

    public boolean isDimensionflag();

    public String getShareFieldName();

    public DummyObjType getDummyObjType();

    public String getDummySource();

    public List<Map<String, Object>> getDefaultShowColumns();

    public String getDefine();
}

