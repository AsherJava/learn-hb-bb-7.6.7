/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.penetrate.client.dto.GcFetchBatchPierceDTO
 *  com.jiuqi.gcreport.bde.penetrate.client.dto.GcFetchPierceDTO
 *  com.jiuqi.gcreport.bde.penetrate.client.vo.GcFetchPierceDataRegionParamVO
 *  com.jiuqi.gcreport.bde.penetrate.client.vo.GcFetchPierceParamVO
 *  com.jiuqi.gcreport.bde.penetrate.client.vo.NrLinkInfoVO
 *  com.jiuqi.gcreport.bde.penetrate.client.vo.QueryBblxParam
 */
package com.jiuqi.gcreport.bde.penetrate.impl.pierce.service;

import com.jiuqi.gcreport.bde.penetrate.client.dto.GcFetchBatchPierceDTO;
import com.jiuqi.gcreport.bde.penetrate.client.dto.GcFetchPierceDTO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.GcFetchPierceDataRegionParamVO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.GcFetchPierceParamVO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.NrLinkInfoVO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.QueryBblxParam;
import java.util.List;
import java.util.Map;

public interface GcFetchPierceService {
    public GcFetchPierceParamVO queryPierceParams(GcFetchPierceDTO var1);

    public Boolean enable(GcFetchPierceDTO var1);

    public GcFetchPierceDataRegionParamVO queryFormByRegionKey(String var1);

    public String queryBblx(QueryBblxParam var1);

    public List<NrLinkInfoVO> queryLinkInfo(List<String> var1);

    public Map<String, Map<String, Boolean>> queryBatchPierceParams(GcFetchBatchPierceDTO var1);
}

