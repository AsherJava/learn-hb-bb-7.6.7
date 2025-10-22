/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.gcreport.offsetitem.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.offsetitem.dto.PaginationDto;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.dao.DataAccessException;

public interface GcOffSetItemAdjustCoreDao
extends IDbSqlGenericDAO<GcOffSetVchrItemAdjustEO, String> {
    @Deprecated
    public Serializable save(GcOffSetVchrItemAdjustEO var1);

    @Deprecated
    public List<GcOffSetVchrItemAdjustEO> saveAll(List<GcOffSetVchrItemAdjustEO> var1);

    @Deprecated
    public int update(GcOffSetVchrItemAdjustEO var1);

    @Deprecated
    public void updateAll(List<GcOffSetVchrItemAdjustEO> var1);

    public int addSelective(GcOffSetVchrItemAdjustEO var1);

    public int updateSelective(GcOffSetVchrItemAdjustEO var1);

    public int add(GcOffSetVchrItemAdjustEO var1) throws DataAccessException;

    public int[] addBatch(List<GcOffSetVchrItemAdjustEO> var1);

    public void deleteByMrecId(Collection<String> var1);

    public void deleteByOffsetGroupIds(Collection<String> var1, GcTaskBaseArguments var2);

    public void deleteByOffsetGroupIdsAndSrcType(Collection<String> var1, Integer var2, GcTaskBaseArguments var3);

    public void deleteByLockId(String var1);

    public void deleteByLockId(String var1, List<String> var2);

    public void deleteDataByOffsetSrcType(GcTaskBaseArguments var1, String var2);

    public void updateMemoById(String var1, String var2);

    public void updateDisabledFlag(List<String> var1, boolean var2);

    public void updateOffsetGroupId(String var1, String var2, Integer var3, GcTaskBaseArguments var4);

    public PaginationDto<Map<String, Object>> listWithFullGroup(QueryParamsDTO var1);

    public List<Map<String, Object>> listWithFullGroupByMrecids(QueryParamsDTO var1, Set<String> var2);

    public List<Map<String, Object>> listWithFullGroupBySrcOffsetGroupIdsAndSystemId(QueryParamsDTO var1, Set<String> var2);

    public List<GcOffSetVchrItemAdjustEO> listWithFullGroupByMrecids(Collection<String> var1);

    public PaginationDto<GcOffSetVchrItemAdjustEO> listEOWithFullGroup(QueryParamsDTO var1);

    public List<GcOffSetVchrItemAdjustEO> listWithOnlyItems(QueryParamsDTO var1);

    public List<GcOffSetVchrItemAdjustEO> listByWhere(String[] var1, Object[] var2);

    public Map<String, String> getMrecid2FetchSetIdByMrecids(Set<String> var1);

    public Collection<String> listMrecidsByRuleId(String var1, Set<String> var2, QueryParamsDTO var3);

    public Collection<String> listOffsetGroupIdsByMrecid(Collection<String> var1);

    public Collection<String> listOffsetGroupIdsById(Collection<String> var1);

    public List<GcOffSetVchrItemAdjustEO> listIdsByLockId(String var1);

    public List<GcOffSetVchrItemAdjustEO> listIdsByLockId(String var1, List<String> var2);

    public List<String> listExistsSubjectCodes(String var1, Set<String> var2);

    public int fillMrecids(QueryParamsDTO var1, Set<String> var2, Set<String> var3);

    public String getFillMrecidSql(QueryParamsDTO var1, String var2, List<Object> var3);

    public List<Map<String, Object>> sumOffsetValueGroupBy(QueryParamsDTO var1, String var2, String var3);

    public String mergeUnitRangeSql(QueryParamsDTO var1, List<Object> var2);
}

