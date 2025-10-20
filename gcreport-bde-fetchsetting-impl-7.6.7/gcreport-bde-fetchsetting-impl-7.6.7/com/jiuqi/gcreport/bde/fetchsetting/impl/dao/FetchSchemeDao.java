/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dao;

import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSchemeEO;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface FetchSchemeDao {
    public List<FetchSchemeEO> listFetchSchemeByFormSchemeId(String var1);

    public void save(FetchSchemeEO var1);

    public int delete(FetchSchemeEO var1);

    public int update(FetchSchemeEO var1);

    public FetchSchemeEO selectById(String var1);

    public List<FetchSchemeEO> loadAllByBizType(String var1);

    public List<FetchSchemeEO> listFetchSchemeByIdList(List<String> var1);

    public int updateOrdinalById(String var1, BigDecimal var2);

    public List<FetchSchemeEO> selectByFormSchemesAndName(List<String> var1, Optional<String> var2);

    public int updateIncludeAdjustVoucherByFetchSchemeId(String var1, int var2);
}

