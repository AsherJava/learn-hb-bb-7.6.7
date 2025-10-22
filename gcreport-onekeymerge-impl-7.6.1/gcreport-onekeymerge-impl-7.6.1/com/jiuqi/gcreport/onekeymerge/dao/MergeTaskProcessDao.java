/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 */
package com.jiuqi.gcreport.onekeymerge.dao;

import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import com.jiuqi.gcreport.onekeymerge.entity.MergeTaskProcessEO;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import java.util.List;

public interface MergeTaskProcessDao
extends IBaseSqlGenericDAO<MergeTaskProcessEO> {
    public void updateProcess(Long var1, Double var2, String var3);

    public List<MergeTaskProcessEO> getTaskRecord(GcActionParamsVO var1, String var2);
}

