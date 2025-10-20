/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.asset.assetbill.dao;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CombinedAssetBillDao {
    public List<Map<String, Object>> listAllBillList(String var1);

    public void batchDeleteByIdList(List<String> var1);

    public int countAssetBills(TempTableCondition var1, Map<String, Object> var2);

    public Set<String> listAssetBillCodesByPaging(TempTableCondition var1, Map<String, Object> var2, int var3, int var4);

    public void batchUnDisposalByIdList(List<String> var1);

    public List<Map<String, Object>> listAssetBillsByBillCodes(Collection<String> var1);

    public void transfer2FixedAsset(String var1);

    public List<String> listIdsByBillCode(String var1, String var2);

    public List<DefaultTableEntity> getFixedAssetItemsDatas(GcCalcEnvContext var1, GcOrgCacheVO var2);

    public List<DefaultTableEntity> getFixedAssetsDatas(GcCalcEnvContext var1, Set<String> var2);

    public Set<String> listAssetBillCodes(TempTableCondition var1, Map<String, Object> var2);

    public List<Map<String, Object>> listItemAssetBillsByBillCodes(Set<String> var1, List<String> var2, Set<String> var3);
}

