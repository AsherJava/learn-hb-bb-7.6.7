/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.query.GcOffsetItemQueryCondi
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.offsetitem.init.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.gcreport.offsetitem.init.entity.GcOffSetVchrItemInitEO;
import com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.query.GcOffsetItemQueryCondi;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GcOffSetVchrItemInitDao
extends IDbSqlGenericDAO<GcOffSetVchrItemInitEO, String> {
    public Pagination<Map<String, Object>> queryInitOffsetEntry(OffsetItemInitQueryParamsVO var1);

    public List<Map<String, Object>> queryInitOffsetPartFieldEntry(OffsetItemInitQueryParamsVO var1);

    public int queryMrecids(OffsetItemInitQueryParamsVO var1, Set<String> var2);

    public void deleteByMrecid(Collection<String> var1, Integer var2, String var3);

    public Collection<String> getMrecidsBySameSrcId(Collection<String> var1);

    public Map<String, Integer> countEveryBusinessType(OffsetItemInitQueryParamsVO var1);

    public List<GcOffSetVchrItemInitEO> findByMrecidOrderBySortOrder(String var1);

    public void deleteBySrcOffsetGroupIds(List<String> var1, int var2, String var3, int var4, String var5);

    public List<GcOffSetVchrItemInitEO> getInvestmentOffsetItemByMrecids(List<String> var1);

    public List<GcOffSetVchrItemInitEO> findByInvestment(GcOffsetItemQueryCondi var1, List<DimensionVO> var2);

    public void deleteByInvestment(String var1, String var2, int var3);

    public Pagination<GcOffSetVchrItemInitEO> queryOffsetingEntryEO(OffsetItemInitQueryParamsVO var1);

    public List<GcOffSetVchrItemInitEO> queryOffsetingEntryEO(Collection<String> var1);

    public int deleteCarryOver(int var1);

    public Collection<String> getSrcOffsetGroupIdsByMrecid(Collection<String> var1, String var2, Integer var3, Integer var4, String var5, String var6);

    public Collection<String> getMrecidsBySrcOffsetGroupId(Collection<String> var1, String var2, Integer var3, Integer var4, String var5, String var6, String var7);

    public boolean hasOffsetRecordByUnitAndRuleType(String var1, Integer var2, boolean var3);

    public void changeAssetTitle(String var1, String var2, Integer var3, String var4, String var5);

    public int deleteCarryOverByMergeCodes(int var1, String var2, List<GcOrgCacheVO> var3, String var4, String var5);

    public void updateOffsetInitDisabledFlag(List<String> var1, boolean var2);

    public void deleteOffsetOfFvchBill(String var1, String var2, Integer var3);

    public List<GcOffSetVchrItemInitEO> listBySystemId(String var1);
}

