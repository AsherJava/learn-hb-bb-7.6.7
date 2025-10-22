/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.financialcheckImpl.offset.dataquery.dto.RelationToMergeArgDTO;
import com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface OffsetRelatedItemDao
extends IDbSqlGenericDAO<GcOffsetRelatedItemEO, String> {
    public void batchDeleteByOffsetGroupId(Collection<String> var1);

    public void batchClearOffsetGroupId(Collection<String> var1);

    public Set<String> filterByUnChecked(Collection<String> var1);

    public List<GcOffsetRelatedItemEO> listByRelatedId(Collection<String> var1);

    public List<GcOffsetRelatedItemEO> listById(Collection<String> var1);

    public List<GcOffsetRelatedItemEO> listByOffsetGroupId(Collection<String> var1);

    public int[] mergeOffsetGroupId(List<GcOffsetRelatedItemEO> var1);

    public long updateItemRuleInfo(List<GcOffsetRelatedItemEO> var1);

    public long updateOffsetRelatedItemInfo(List<GcOffsetRelatedItemEO> var1);

    public void batchDeleteByRelatedItemId(Collection<String> var1);

    public List<GcOffsetRelatedItemEO> listByOffsetCondition(RelationToMergeArgDTO var1);

    public Set<String> listOffsetDataRelatedIds(Collection<String> var1, String var2, String var3, String var4);
}

