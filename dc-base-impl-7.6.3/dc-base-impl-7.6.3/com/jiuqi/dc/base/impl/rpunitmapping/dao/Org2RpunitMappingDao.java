/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.client.rpunitmapping.queryvo.Org2RpunitMappingQueryVO
 */
package com.jiuqi.dc.base.impl.rpunitmapping.dao;

import com.jiuqi.dc.base.client.rpunitmapping.queryvo.Org2RpunitMappingQueryVO;
import com.jiuqi.dc.base.impl.rpunitmapping.entity.Org2RpunitMappingEntity;
import java.util.List;
import java.util.Map;

public interface Org2RpunitMappingDao {
    public List<String> listOrgCode(Org2RpunitMappingEntity var1);

    public List<Map<String, Object>> listAll(Org2RpunitMappingQueryVO var1);

    public List<Org2RpunitMappingEntity> listByOrgCodes(Org2RpunitMappingQueryVO var1);

    public int getListAllCount(Org2RpunitMappingQueryVO var1);

    public int deleteByIds(Org2RpunitMappingQueryVO var1);

    public List<String> getAllOrgCodeByRpUnitCode(Org2RpunitMappingQueryVO var1);

    public List<String> getAllOrgCode(Org2RpunitMappingQueryVO var1);

    public List<String> listUnitCodeByOrgCodeAndPeriod(Org2RpunitMappingQueryVO var1);

    public void updateByPrimaryKey(Org2RpunitMappingEntity var1);

    public void insert(Org2RpunitMappingEntity var1);

    public List<Org2RpunitMappingEntity> select(Org2RpunitMappingEntity var1);

    public void batchInsert(List<Org2RpunitMappingEntity> var1);

    public void batchUpdate(List<Org2RpunitMappingEntity> var1);
}

