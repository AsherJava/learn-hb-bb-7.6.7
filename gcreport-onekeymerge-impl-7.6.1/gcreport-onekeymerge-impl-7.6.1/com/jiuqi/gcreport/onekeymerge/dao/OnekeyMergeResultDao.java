/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 */
package com.jiuqi.gcreport.onekeymerge.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.onekeymerge.entity.GcOnekeyMergeResultEO;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import java.util.List;

public interface OnekeyMergeResultDao
extends IDbSqlGenericDAO<GcOnekeyMergeResultEO, String> {
    public List<GcOnekeyMergeResultEO> findPastThreeResult(GcActionParamsVO var1);
}

