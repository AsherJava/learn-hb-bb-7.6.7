/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.client.rpunitmapping.dto.Org2RpunitMappingSaveDTO
 *  com.jiuqi.dc.base.client.rpunitmapping.queryvo.Org2RpunitMappingQueryVO
 *  com.jiuqi.dc.base.client.rpunitmapping.result.Org2RpunitMappingReturnVO
 *  com.jiuqi.dc.base.client.rpunitmapping.result.Org2RpunitMappingVO
 */
package com.jiuqi.dc.base.impl.rpunitmapping.service;

import com.jiuqi.dc.base.client.rpunitmapping.dto.Org2RpunitMappingSaveDTO;
import com.jiuqi.dc.base.client.rpunitmapping.queryvo.Org2RpunitMappingQueryVO;
import com.jiuqi.dc.base.client.rpunitmapping.result.Org2RpunitMappingReturnVO;
import com.jiuqi.dc.base.client.rpunitmapping.result.Org2RpunitMappingVO;
import java.util.List;

public interface Org2RpunitMappingService {
    public Org2RpunitMappingReturnVO query(Org2RpunitMappingQueryVO var1);

    public List<Org2RpunitMappingVO> save(List<Org2RpunitMappingVO> var1);

    public Org2RpunitMappingVO saveOrUpdate(Org2RpunitMappingSaveDTO var1);

    public int deleteByIds(Org2RpunitMappingQueryVO var1);

    public String importExcel(Integer var1, List<Org2RpunitMappingVO> var2);

    public List<String> listUnitCodeByOrgCodeAndPeriod(String var1, int var2, int var3);

    public List<String> listOrgCode(int var1);

    public List<String> getAllOrgCodeByRpUnitCode(Org2RpunitMappingQueryVO var1);
}

