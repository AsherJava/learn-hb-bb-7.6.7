/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service;

import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO;
import java.util.List;

public interface FetchSchemeNrService {
    public List<FetchSchemeVO> listFetchScheme(String var1);

    public void saveFetchScheme(FetchSchemeVO var1);

    public void deleteFetchScheme(FetchSchemeVO var1);

    public int updateFetchScheme(FetchSchemeVO var1);

    public FetchSchemeVO getFetchScheme(String var1);

    public String copyFetchScheme(String var1, String var2);

    public List<FetchSchemeVO> listFetchSchemeByIdList(List<String> var1);

    public void fetchSchemeCacheClear();

    public List<FetchSchemeVO> listFetchSchemeByBizType(String var1);

    public List<FetchSchemeVO> listFetchSchemeByTaskCodeAndName(String var1, String var2);

    public Boolean canEditFetchScheme(String var1);

    public Boolean exchangeOrdinal(String var1, String var2);

    public Boolean queryIncludeAdjustVoucherByFetchSchemeId(String var1);

    public void updateIncludeAdjustVoucherByFetchSchemeId(String var1, Integer var2);
}

