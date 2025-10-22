/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaConditionVO
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.multicriteria.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaConditionVO;
import com.jiuqi.gcreport.multicriteria.entity.GcMultiCriteriaEO;
import java.util.List;
import java.util.Set;
import org.springframework.web.bind.annotation.RequestBody;

public interface GcMultiCriteriaDao
extends IDbSqlGenericDAO<GcMultiCriteriaEO, String> {
    public List<GcMultiCriteriaEO> querySubjectMapping(GcMultiCriteriaConditionVO var1);

    public void deleteSubjectMapping(@RequestBody List<String> var1);

    public List<GcMultiCriteriaEO> querySubjectMappingByIds(Set<String> var1);
}

